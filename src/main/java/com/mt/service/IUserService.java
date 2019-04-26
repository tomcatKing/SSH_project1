package com.mt.service;

import javax.servlet.http.HttpSession;

import com.mt.result.JsonResult;

public interface IUserService {
	/**注册用户*/
	JsonResult regist(String userName,String userEamil,String code);
	
	/**用户登录*/
	JsonResult login(HttpSession session,String userName,String code);
	
	/**用户退出*/
	JsonResult logout(HttpSession session);
	
	/**用户修改绑定邮箱*/
	JsonResult update(String userName,String oldEmail,String newEmail,String code);
	
	/**获取验证码(通过用户名)*/
	JsonResult getCodeByUserName(String userName);
	
	/**通过邮箱获取验证码*/
	JsonResult getCodeByUserEmail(String emailName);
	
	/**判断用户是否已经被使用*/
	JsonResult UserisLive(String userName);
	
	/**判断邮箱是否已经被使用*/
	JsonResult EmailIsLive(String userEmail);
}
