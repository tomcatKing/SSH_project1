package com.mt.dao;

import com.mt.pojo.User;

public interface IUserDao {
	User getUserById(Integer userId);
	
	boolean containUserName(String userName);
	
	boolean containUserEmail(String userEmail);
	
	boolean saveUser(User user);
	
	User getUserByName(String userName);
	
	User getUserByUserNameAndUserEmail(String userName,String userEmail);
	
	void update(User user);
}
