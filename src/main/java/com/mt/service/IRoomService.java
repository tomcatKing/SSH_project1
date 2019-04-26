package com.mt.service;

import com.mt.result.JsonResult;

public interface IRoomService {
	
	/**用户选择房间(1个用户只能选择1个房间)*/
	JsonResult selectRoom(Integer userId,Integer roomId);
	
	/**用户注销房间,如果存在订单.会删除所有未支付订单*/
	JsonResult removeRoom(Integer userId);
	
	/**用户查看所有可以使用的房间*/
	JsonResult Rooms(int pageNum,int pageSize);
	
	/**获取当前用户所顶的房间的详细信息,包括订单信息**/
	JsonResult RoomVo(Integer userId);
	
}
