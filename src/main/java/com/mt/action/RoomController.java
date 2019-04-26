package com.mt.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.mt.common.Const;
import com.mt.pojo.User;
import com.mt.result.JsonResult;
import com.mt.service.IRoomService;

import lombok.Getter;
import lombok.Setter;

@Controller
@Scope("prototype")
public class RoomController extends SuperAction{
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private IRoomService iRoomService;
	
	@Getter
	private JsonResult jsonResult;
	
	@Setter
	private int pageNum=1;
	
	@Setter
	private int pageSize=10;
	
	@Setter
	private Integer roomId;
	
	@Getter
	private JsonResult errorResult=JsonResult.errorMsg("请求出错了");
	
	/**获取所有的房间列表*/
	public String list() {
		jsonResult=iRoomService.Rooms(pageNum, pageSize);
		return SUCCESS;
	}
	
	/**添加房间*/
	public String add() {
		User user=(User)session.getAttribute(Const.CURRENT_USER);
		if(user==null) {
			jsonResult=JsonResult.errorMsg("请登录后再进行操作!!");
		}else {
			jsonResult=iRoomService.selectRoom(user.getUserId(), roomId);
		}
		return SUCCESS;
	}
	
	/**取消房间(这里要删除用户在这个房间的所有未支付的订单)*/
	public String cancel() {
		User user=(User)session.getAttribute(Const.CURRENT_USER);
		if(user==null) {
			jsonResult=JsonResult.errorMsg("请登录后再进行操作!!");
		}else {
			jsonResult=iRoomService.removeRoom(user.getUserId());
		}
		return SUCCESS;
	}
	
	/**获取当前用户的房间信息,包括已支付订单*/
	public String roomVo() {
		User user=(User)session.getAttribute(Const.CURRENT_USER);
		if(user==null) {
			jsonResult=JsonResult.errorMsg("请登录后再进行操作!!");
		}else {
			jsonResult=iRoomService.RoomVo(user.getUserId());
		}
		return SUCCESS;
	}
}
