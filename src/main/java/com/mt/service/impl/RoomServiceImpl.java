package com.mt.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mt.common.Const;
import com.mt.dao.IOrderDao;
import com.mt.dao.IOrderItemDao;
import com.mt.dao.IRoomDao;
import com.mt.dao.IUserDao;
import com.mt.pojo.Order;
import com.mt.pojo.OrderItem;
import com.mt.pojo.Room;
import com.mt.pojo.User;
import com.mt.result.JsonResult;
import com.mt.service.IRoomService;
import com.mt.util.Page;
import com.mt.vo.RoomVo;

import lombok.extern.log4j.Log4j;

@Service
@Transactional
@Log4j
public class RoomServiceImpl implements IRoomService{
	@Autowired
	private IRoomDao iRoomDao;
	
	@Autowired
	private IUserDao iUserDao;
	
	@Autowired
	private IOrderDao iOrderDao;
	
	@Autowired
	private IOrderItemDao iOrderItemDao;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public JsonResult selectRoom(Integer userId, Integer roomId) {
		log.info("选择房间的服务启动了,userId->:"+userId+",roomId->:"+roomId);
		if(userId==null || roomId==null) {
			return JsonResult.errorMsg("传入参数错误");
		}
		User user=iUserDao.getUserById(userId);
		if(user==null) {
			return JsonResult.errorMsg("用户不存在");
		}
		Room room=iRoomDao.getRoomById(roomId);
		if(room==null) {
			return JsonResult.errorMsg("房间不存在");
		}
		//如果房间状态为不可使用
		if(room.getRoomStatus()!=Const.RoomStatusEnum.KESHIYON.getCode()) {
			return JsonResult.errorMsg("当前房间不可选择");
		}
		//判断当前用户是否已经选择房间
		if(user.getRoomId()!=null) {
			return JsonResult.errorMsg("当前用户已经存在可使用房间");
		}
		
		user.setRoomId(room);
		iUserDao.update(user);
		room.setRoomStatus(Const.RoomStatusEnum.YISHIYON.getCode());
		iRoomDao.update(room);
		
		return JsonResult.ok();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public JsonResult removeRoom(Integer userId) {
		log.info("取消房间的服务启动了,userId->:"+userId);
		if(userId==null) {
			return JsonResult.errorMsg("传入参数错误");
		}
		User user=iUserDao.getUserById(userId);
		if(user.getRoomId()==null) {
			return JsonResult.errorMsg("用户已经没有房间了");
		}
		/**1.删除用户的未支付订单*/
		long count=iOrderDao.list(userId);
		if(count!=0) {	
			Page p=new Page();
			p.setCurrentPage(1);
			p.setPageRows((int)count);
			Map<String,Integer> params=Maps.newHashMap();
			params.put(":userId", userId);
			params.put(":status", Const.OrderStatusEnum.NOPAY.getCode());
			Page page=iOrderDao.getOrderListByStatus(p, params);
			List<Order> orderList=(List<Order>) page.getList();
			for (Order order : orderList) {
				//修改订单状态为已经取消
				order.setStatus(Const.OrderStatusEnum.CANCEL.getCode());
				iOrderDao.updateOrder(order);
			}
		}
		Room room=user.getRoomId();
		//这里应该修改房间状态未不可使用,好让工作人员去收拾
		room.setRoomStatus(Const.RoomStatusEnum.NOSHIYON.getCode());
		iRoomDao.update(room);
		
		//设置用户关联的房间为null
		user.setRoomId(null);
		iUserDao.update(user);
		return JsonResult.ok();
	}

	@Override
	@Transactional(readOnly=true)
	public JsonResult Rooms(int pageNum, int pageSize) {
		log.info("查看房间的服务启动了");
		Page p=new Page();
		p.setCurrentPage(pageNum);
		p.setPageRows(pageSize);
		StringBuilder sb=new StringBuilder("select new Room(r.roomId,r.roomName,r.roomAddress,r.roomPrice,r.roomType) from Room r where r.roomStatus=:roomStatus");
		p.setHql(sb.toString());
		Map<String,Integer> params=Maps.newHashMap();
		params.put(":roomStatus", Const.RoomStatusEnum.KESHIYON.getCode());
		
		Page page=iRoomDao.list(p,params);
		List<Room> roomList=(List<Room>) page.getList();
		List<RoomVo> roomVoList=Lists.newArrayList();
		for (Room room : roomList) {
			roomVoList.add(RoomVo.accessRoomVo(room));
		}
		page.setList(roomVoList);
		return JsonResult.ok(page);
	}

	@Override
	@Transactional(readOnly=true)
	public JsonResult RoomVo(Integer userId) {
		log.info("查看房间的详细信息的服务启动了,userId->:"+userId);
		//1.获取用户
		User user=iUserDao.getUserById(userId);
		if(user==null) {
			return JsonResult.errorMsg("当前用户未登录!!");
		}
		//2.获取房间
		Room room=user.getRoomId();
		if(room==null) {
			return JsonResult.errorMsg("当前用户未选择房间");
		}
		//3.获取订单
		Integer roomId=room.getRoomId();
		List<Order> orderList=iOrderDao.getOrdersByUserIdAndRoomId(userId,roomId);
		List<OrderItem> orderItemList1=Lists.newArrayList();
		//获取了所有的订单详情
		for (Order order : orderList) {
			List<OrderItem> orderItemList=iOrderItemDao.getOrderItemsByOrderNo(order.getOrderNo());
			for(OrderItem orderItem:orderItemList) {
				orderItemList1.add(orderItem);
			}
		}
		
		//4.包装数据
		RoomVo roomVo=RoomVo.transRoomVo(room, orderItemList1);
		return JsonResult.ok(roomVo);
	}
	

}
