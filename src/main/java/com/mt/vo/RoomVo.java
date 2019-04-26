package com.mt.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.google.common.collect.Lists;
import com.mt.common.Const;
import com.mt.pojo.OrderItem;
import com.mt.pojo.Room;

import lombok.Data;


@Data
public class RoomVo implements Serializable{
	private Integer roomId;
	private String roomName;
	private String roomAddress;
	private String roomType;
	private BigDecimal roomPrice;
	private String roomStatus;
	private List<OrderItemVo> orderItemVoList;

	public static RoomVo accessRoomVo(Room room) {
		RoomVo roomVo=new RoomVo();
		roomVo.setRoomId(room.getRoomId());
		roomVo.setRoomName(room.getRoomName());
		roomVo.setRoomAddress(room.getRoomAddress());
		roomVo.setRoomPrice(room.getRoomPrice());
		roomVo.setRoomType(Const.RoomTypeEnum.codeOf(room.getRoomType()).getVal());
//		roomVo.setRoomStatus(Const.RoomStatusEnum.codeOf(room.getRoomStatus()).getVal());
		return roomVo;
	}
	
	public static RoomVo transRoomVo(Room room,List<OrderItem> orderItemList) {
		List<OrderItemVo> orderItemVoList=Lists.newArrayList();
		for (OrderItem orderItem : orderItemList) {
			OrderItemVo orderItemVo=OrderItemVo.accessOrderItemVo(orderItem);
			orderItemVoList.add(orderItemVo);
		}
		RoomVo roomVo=new RoomVo();
		roomVo.setRoomId(room.getRoomId());
		roomVo.setRoomName(room.getRoomName());
		roomVo.setRoomAddress(room.getRoomAddress());
		roomVo.setRoomPrice(room.getRoomPrice());
		roomVo.setRoomType(Const.RoomTypeEnum.codeOf(room.getRoomType()).getVal());
		roomVo.setRoomStatus(Const.RoomStatusEnum.codeOf(room.getRoomStatus()).getVal());
		roomVo.setOrderItemVoList(orderItemVoList);
		return roomVo;
		
	}
}
