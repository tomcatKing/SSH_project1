package com.mt.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mt.common.AliPayService;
import com.mt.common.Const;
import com.mt.config.AlipayConfig;
import com.mt.dao.ICartDao;
import com.mt.dao.IFoodDao;
import com.mt.dao.IOrderDao;
import com.mt.dao.IOrderItemDao;
import com.mt.dao.IPayInfoDao;
import com.mt.dao.IUserDao;
import com.mt.pojo.Cart;
import com.mt.pojo.Food;
import com.mt.pojo.Order;
import com.mt.pojo.OrderItem;
import com.mt.pojo.PayInfo;
import com.mt.pojo.Room;
import com.mt.pojo.User;
import com.mt.result.JsonResult;
import com.mt.service.IOrderService;
import com.mt.util.BigDecimalUtil;
import com.mt.util.DateUtil;
import com.mt.util.Page;
import com.mt.vo.OrderItemVo;
import com.mt.vo.OrderVo;
import com.mt.vo.RoomVo;

import lombok.extern.log4j.Log4j;
@Service
@Transactional
@Log4j
public class OrderServiceImpl implements IOrderService {
	@Autowired
	private ICartDao iCartDao;
	
	@Autowired
	private IFoodDao iFoodDao;
	
	@Autowired
	private IUserDao iUserDao;
	
	@Autowired
	private IOrderDao iOrderDao;
	
	@Autowired
	private IOrderItemDao iOrderItemDao;
	
	@Autowired
	private IPayInfoDao iPayInfoDao;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	/**创建订单*/
	public JsonResult createOrder(Integer userId) {
		log.info("生成订单的服务启动了,userId->:"+userId);
		User user=iUserDao.getUserById(userId);
		Room room=user.getRoomId();
		if(room==null) {
			return JsonResult.errorMsg("当前用户没有选择房间,不能创建订单");
		}
		
		//1.获取用户的所有被勾选的购物车
		List<Cart> cartList=iCartDao.getCartByChecked(userId);
		
		//2.创建订单(先获取总金额)
		JsonResult jsonResult=this.getCartOrderItem(user,cartList);
		if(!jsonResult.isOK()) {
			return jsonResult;
		}
		
		//3.创建订单详情
		List<OrderItem> orderItemList=(List<OrderItem>) jsonResult.getData();
		if(CollectionUtils.isEmpty(orderItemList)) {
			return JsonResult.errorMsg("购物车为空");
		}
		
		//获取订单总价
		BigDecimal paymail=getOrderTotalPrice(orderItemList);
		
		//4.生成订单
		Order order=this.accessOrder(user,room,paymail);
		iOrderDao.addOrder(order);
		if(order.getOrderId()==null) {
			return JsonResult.errorMsg("订单生成失败,服务器累了.请稍后试试");
		}
		//更新订单详情表中的订单编号
		for(OrderItem orderItem:orderItemList) {
			orderItem.setOrderNo(order.getOrderNo());
			//插入订单详情
			iOrderItemDao.addOrderItem(orderItem);
		}
		
		//5.清空购物车
		for(Cart cart:cartList) {
			//1个1个的删除购物车
			iCartDao.remove(userId, cart.getCartId());
		}
		
		//6.返回Order的orderNo给前端
		return JsonResult.ok(order.getOrderNo()); 
	}
	
	//创建Order
	private Order accessOrder(User user, Room room, BigDecimal paymail) {
		//这里的总价还得加上房钱
		Order order=new Order();
		long orderNo=this.createOrderNo();
		order.setOrderNo(orderNo);
		order.setUserId(user);
		order.setRoomId(room);
		//订单总价
		paymail=BigDecimalUtil.add(paymail.doubleValue(),room.getRoomPrice().doubleValue());
		order.setPayment(paymail);
		order.setPaymentType(Const.PaymentTypeEnum.ZHIFUBAO.getCode());
		order.setStatus(Const.OrderStatusEnum.NOPAY.getCode());
		order.setOrderDesc("该用户什么都没有留下...");
		order.setCreateTime(new Date());
		order.setUpdateTime(new Date());
		return order;
	}

	//生成OrderNo
	private Long createOrderNo() {
		return System.currentTimeMillis()+new Random().nextInt(2<<16);
	}
	
	
	//获取订单总价
	private BigDecimal getOrderTotalPrice(List<OrderItem> orderItemList) {
		BigDecimal totalPrice=new BigDecimal("0");
		for(OrderItem orderItem:orderItemList) {
			totalPrice=BigDecimalUtil.add(totalPrice.doubleValue(),orderItem.getTotalPrice().doubleValue());
		}
		return totalPrice;
	}

	private JsonResult getCartOrderItem(User user, List<Cart> cartList) {
		List<OrderItem> orderItemList=Lists.newArrayList();
		if(CollectionUtils.isEmpty(cartList)) {
			return JsonResult.errorMsg("购物车为空!!");
		}
		//效验购物车的数据,包括产品的状态和数量
		for(Cart cartItem:cartList) {
			OrderItem orderItem=new OrderItem();
			Food food=cartItem.getFoodId();
			//如果美食不是在售状态
			if(food.getFoodStatus()!=Const.FoodStatusEnum.ZAISHOU.getCode()) {
				return JsonResult.errorMsg("产品:"+food.getFoodName()+"信息已过期");
			}
			
			orderItem.setUserId(user);
			orderItem.setFoodId(food);
			orderItem.setFoodNum(cartItem.getFoodNum());
			orderItem.setTotalPrice(BigDecimalUtil.mul(food.getFoodPrice().doubleValue(),cartItem.getFoodNum()));
			orderItem.setCreateTime(new Date());
			orderItem.setUpdateTime(new Date());
			orderItemList.add(orderItem);
		}
		return JsonResult.ok(orderItemList);
		
	}

	@Override
	/**取消订单*/
	@Transactional(propagation=Propagation.REQUIRED)
	public JsonResult cancelOrder(Integer userId, Long orderNo) {
		log.info("取消订单的服务启动了,userId->:"+userId+",orderNo->:"+orderNo);
		if(userId==null || orderNo==null || userId<1 || orderNo<1) {
			return JsonResult.errorMsg("传入参数错误!!");
		}
		Order order=iOrderDao.getOrderByUserIdAndOrderNo(userId, orderNo);
		if(order==null) {
			return JsonResult.errorMsg("当前用户没有这个订单");
		}
		//如果订单状态不是未支付,那么就取消订单
		if(order.getStatus()!=Const.OrderStatusEnum.NOPAY.getCode()) {
			return JsonResult.errorMsg("当前订单已经无法取消");
		}
		order.setStatus(Const.OrderStatusEnum.CANCEL.getCode());
		iOrderDao.updateOrder(order);
		return JsonResult.ok();
	}

	@Override
	@Transactional(readOnly=true)
	public JsonResult pay(Integer userId, Long orderNo) {
		log.info("支付订单的服务启动了,userId->:"+userId+",orderNo->:"+orderNo);
		if(userId==null || orderNo==null || userId<1 || orderNo<1) {
			return JsonResult.errorMsg("传入参数错误!!");
		}
		Order order=iOrderDao.getOrderByUserIdAndOrderNoSuper(userId, orderNo);
		if(order==null) {
			return JsonResult.errorMsg("当前用户没有这个订单");
		}
		//判断这个订单的状态,如果这个订单的状态不是未支付
		if(order.getStatus()!=Const.OrderStatusEnum.NOPAY.getCode()) {
			return JsonResult.errorMsg("当前订单无法进行支付操作");
		}
		JsonResult payResult=AliPayService.payService(order);
		return payResult;
	}
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public JsonResult detail(Integer userId,Long orderNo) {
		log.info("订单详情的服务启动了,userId->:"+userId+",orderNo->:"+orderNo);
		if(userId==null || orderNo==null || userId<1 || orderNo<1) {
			return JsonResult.errorMsg("传入参数错误!!");
		}
		// TODO Auto-generated method stub
		Order order=iOrderDao.getOrderByUserIdAndOrderNoSuper(userId, orderNo);
		if(order==null) {
			return JsonResult.errorMsg("当前用户没有这个订单");
		}
		//RoomVo
		Room room=order.getRoomId();
		RoomVo roomVo=RoomVo.accessRoomVo(room);
		
		//OrderItemVo
		List<OrderItem> orderItemList=iOrderItemDao.getOrderItemsByOrderNo(order.getOrderNo());
		List<OrderItemVo> orderItemVoList=Lists.newArrayList();
		for (OrderItem orderItem : orderItemList) {
			orderItemVoList.add(OrderItemVo.accessOrderItemVo(orderItem));
		}
		OrderVo orderVo=OrderVo.accessOrderVo(order, orderItemVoList, roomVo);
		return JsonResult.ok(orderVo);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public JsonResult alipay_notify_back(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException {
		log.info("支付宝异步回调了");
		//获取支付宝POST过来反馈信息
		Map<String,String> params =Maps.newHashMap();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		
		boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

		//——请在这里编写您的程序（以下代码仅作参考）——
		
		/* 实际验证过程建议商户务必添加以下校验：
		1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
		2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
		3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
		4、验证app_id是否为该商户本身。
		*/
		if(signVerified) {//验证成功
			//商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			
			//支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
		
			//交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
			
			//回调成功,这里要做的就是修改订单状态,添加支付成功信息
			if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
				//1.先获取订单号
				Long orderNo=Long.parseLong(out_trade_no);
				//2.通过订单号获取订单
				Order order=iOrderDao.getOrderByOrderNo(orderNo);
				//3.判断订单是否已经支付完成
				if(order.getStatus()!=Const.OrderStatusEnum.ISPAY.getCode()) {
					//if==>4.修改订单状态,插入支付数据
					PayInfo payInfo=new PayInfo();
					payInfo.setOrderNo(orderNo);
					payInfo.setPayPlatform(Const.PaymentTypeEnum.ZHIFUBAO.getCode());
					payInfo.setPlatformNumber(trade_no);
					payInfo.setPlatformStatus(trade_status);
					payInfo.setCreateTime(new Date());
					payInfo.setUpdateTime(new Date());
					payInfo.setUserId(order.getUserId());
					
					iPayInfoDao.addPayInfo(payInfo);
					if(payInfo.getPayId()>0) {
						order.setPaymentTime(new Date());
						order.setUpdateTime(new Date());
						iOrderDao.updateOrder(order);
						return JsonResult.ok("支付成功");
					}else {
						return JsonResult.errorMsg("系统错误,请稍后重试!!");
					}
				}else {
					//else==>4.返回支付宝重复调用
					return JsonResult.errorMsg("支付宝重复调用");
				}
			}
			//到这里就说明完成
			return JsonResult.ok();
		}else {//验证失败
			return JsonResult.errorMsg("非法请求,验证不通过!!");
		}
	}

	@Override
	public JsonResult alipay_return_back(HttpServletRequest request) throws AlipayApiException, UnsupportedEncodingException {
		log.info("支付宝同步回调了");
		Map<String,String> params = Maps.newHashMap();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		
		boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

		//——请在这里编写您的程序（以下代码仅作参考）——
		if(signVerified) {
			//商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		
			//支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
		
			//付款金额
			String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
			
			Long orderNo=Long.parseLong(out_trade_no);
			Order order=iOrderDao.getOrderByOrderNo(orderNo);
			
			//orderNo,totalPrice
			List result=Lists.newArrayList();
			result.add(order.getOrderNo());
			result.add(order.getPayment());
			
			return JsonResult.ok(result);	
		}else {
			return JsonResult.errorMsg("支付失败!!");
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public JsonResult list(Integer userId,int pageNum,int pageSize) {
		log.info("获取订单列表的服务启动了,userId->:"+userId);
		Page p=new Page();
		p.setHql("from Order where userId.userId=:userId");
		p.setCurrentPage(pageNum);
		p.setPageRows(pageSize);
		Map<String,Integer> params=Maps.newHashMap();
		params.put(":userId", userId);
		Page result=iOrderDao.list(p,params);
		List<OrderVo> orderVoList=Lists.newArrayList();
		List<Order> orderList=(List<Order>) result.getList();
		for (Order order : orderList) {
			
			List<OrderItem> orderItemList=iOrderItemDao.getOrderItemsByOrderNo(order.getOrderNo());
			List<OrderItemVo> orderItemVoList=Lists.newArrayList();
			for (OrderItem orderItem : orderItemList) {
				OrderItemVo orderItemVo=OrderItemVo.accessOrderItemVo(orderItem);
				orderItemVoList.add(orderItemVo);
			}
			
			RoomVo roomVo=RoomVo.accessRoomVo(order.getRoomId());
			
			OrderVo orderVo=OrderVo.accessOrderVo(order, orderItemVoList, roomVo);
			orderVoList.add(orderVo);
		}
		result.setList(orderVoList);
		return JsonResult.ok(result);
	}

	@Override
	@Transactional(readOnly=true)
	public JsonResult nopay(Integer userId, int pageNum, int pageSize) {
		log.info("获取未支付订单列表的服务启动了,userId->:"+userId);
		Page page=new Page();
		page.setCurrentPage(pageNum);
		page.setPageRows(pageSize);
		
		Map<String,Integer> params=Maps.newHashMap();
		params.put(":userId", userId);
		params.put(":status", Const.OrderStatusEnum.NOPAY.getCode());
		Page p=iOrderDao.getOrderListByStatus(page, params);
		List<OrderVo> orderVoList=Lists.newArrayList();
		List<Order> orderList=(List<Order>)p.getList(); 
		for (Order order : orderList) {
			
			List<OrderItem> orderItemList=iOrderItemDao.getOrderItemsByOrderNo(order.getOrderNo());
			List<OrderItemVo> orderItemVoList=Lists.newArrayList();
			for (OrderItem orderItem : orderItemList) {
				OrderItemVo orderItemVo=OrderItemVo.accessOrderItemVo(orderItem);
				orderItemVoList.add(orderItemVo);
			}
			
			RoomVo roomVo=RoomVo.accessRoomVo(order.getRoomId());
			
			OrderVo orderVo=OrderVo.accessOrderVo(order, orderItemVoList, roomVo);
			orderVoList.add(orderVo);
		}
		p.setList(orderVoList);
		return JsonResult.ok(p);
	}

	@Override
	@Transactional(readOnly=true)
	public JsonResult inpay(Integer userId, int pageNum, int pageSize) {
		log.info("获取已支付订单列表的服务启动了,userId->:"+userId);
		Page page=new Page();
		page.setCurrentPage(pageNum);
		page.setPageRows(pageSize);
		
		Map<String,Integer> params=Maps.newHashMap();
		params.put(":userId", userId);
		params.put(":status", Const.OrderStatusEnum.ISPAY.getCode());
		Page p=iOrderDao.getOrderListByStatus(page, params);
		List<OrderVo> orderVoList=Lists.newArrayList();
		List<Order> orderList=(List<Order>)p.getList(); 
		for (Order order : orderList) {
			
			List<OrderItem> orderItemList=iOrderItemDao.getOrderItemsByOrderNo(order.getOrderNo());
			List<OrderItemVo> orderItemVoList=Lists.newArrayList();
			for (OrderItem orderItem : orderItemList) {
				OrderItemVo orderItemVo=OrderItemVo.accessOrderItemVo(orderItem);
				orderItemVoList.add(orderItemVo);
			}
			
			RoomVo roomVo=RoomVo.accessRoomVo(order.getRoomId());
			
			OrderVo orderVo=OrderVo.accessOrderVo(order, orderItemVoList, roomVo);
			orderVoList.add(orderVo);
		}
		p.setList(orderVoList);
		return JsonResult.ok(p);
	}


	@Override
	@Transactional(readOnly=true)
	public JsonResult success(Integer userId, int pageNum, int pageSize) {
		log.info("获取已完成订单列表的服务启动了,userId->:"+userId);
		Page page=new Page();
		page.setCurrentPage(pageNum);
		page.setPageRows(pageSize);
		
		Map<String,Integer> params=Maps.newHashMap();
		params.put(":userId", userId);
		params.put(":status", Const.OrderStatusEnum.SUCCESS.getCode());
		Page p=iOrderDao.getOrderListByStatus(page, params);
		List<OrderVo> orderVoList=Lists.newArrayList();
		List<Order> orderList=(List<Order>)p.getList(); 
		for (Order order : orderList) {
			
			List<OrderItem> orderItemList=iOrderItemDao.getOrderItemsByOrderNo(order.getOrderNo());
			List<OrderItemVo> orderItemVoList=Lists.newArrayList();
			for (OrderItem orderItem : orderItemList) {
				OrderItemVo orderItemVo=OrderItemVo.accessOrderItemVo(orderItem);
				orderItemVoList.add(orderItemVo);
			}
			
			RoomVo roomVo=RoomVo.accessRoomVo(order.getRoomId());
			
			OrderVo orderVo=OrderVo.accessOrderVo(order, orderItemVoList, roomVo);
			orderVoList.add(orderVo);
		}
		p.setList(orderVoList);
		return JsonResult.ok(p);
	}

	@Override
	@Transactional(readOnly=true)
	public JsonResult status(Integer userId, Long orderNo) {
		log.info("获取订单的状态的服务启动了,userId->:"+userId+",orderNo->:"+orderNo);
		Order order=iOrderDao.getOrderByUserIdAndOrderNo(userId, orderNo);
		System.out.println(order);
		if(order==null) {
			return JsonResult.errorMsg("当前用户没有这个订单");
		}
		if(order.getStatus()==Const.OrderStatusEnum.ISPAY.getCode() || order.getStatus()==Const.OrderStatusEnum.SUCCESS.getCode()) {
			return JsonResult.ok();
		}
		return JsonResult.errorMsg("订单没有支付成功!!");
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public JsonResult closeOrder() {
		log.info("关闭未支付订单的服务启动了");
		List<Order> orderList=iOrderDao.getOrders();
		Date now=new Date();
		Long nowTime=now.getTime();
		for(Order order:orderList) {
			if(order.getStatus()==Const.OrderStatusEnum.NOPAY.getCode()) {
				//如果是未支付,并且与当前时间相关1个小时以上
				Date createTime=order.getCreateTime();
				if(DateUtil.dateAnddateIsTrue(createTime.getTime(),nowTime)) {
					order.setStatus(Const.OrderStatusEnum.CANCEL.getCode());
					//该订单需要被关闭
					iOrderDao.updateOrder(order);
				}
			}
		}
		return JsonResult.ok();
	}

	
}
