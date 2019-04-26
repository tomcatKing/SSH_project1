package com.mt.vo;

import java.io.Serializable;

import com.mt.pojo.User;

import lombok.Data;

@Data
public class UserVo implements Serializable{
	private Integer userId;
	private String userName;
	private RoomVo roomVo;
	
	public static UserVo accessUserVo(User user) {
		UserVo userVo=new UserVo();
		userVo.setUserId(user.getUserId());
		userVo.setUserName(user.getUserName());
		userVo.setRoomVo(RoomVo.accessRoomVo(user.getRoomId()));
		
		return userVo;
	}
}
