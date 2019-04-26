package com.mt.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.mt.common.Const;
import com.mt.pojo.User;
import com.mt.result.JsonResult;
import com.mt.service.IUserService;

import lombok.Getter;
import lombok.Setter;

@Controller
@Scope("prototype")
public class UserController extends SuperAction{
	private static final long serialVersionUID = 1L;

	@Autowired
	private IUserService iUserService;
	
	@Getter
	private JsonResult jsonResult;
	
	@Setter
	private String userName;
	
	@Setter
	private String userEmail;
	
	@Setter
	private String code;
	
	@Setter
	private String oldEmail;
	
	@Setter
	private String newEmail;
	
	@Getter
	private JsonResult errorResult=JsonResult.errorMsg("请求出错了");
	
	/**判断用户名是否被使用*/
	public String containUserName() {
		jsonResult=iUserService.UserisLive(userName);
		return SUCCESS;
	}
	
	/**通过用户名获取验证码*/
	public String getCodeByUserName() {
		jsonResult=iUserService.getCodeByUserName(userName);
		return SUCCESS;
	}
	
	/**通过邮箱获取验证码*/
	public String getCodeByUserEmail() {
		jsonResult=iUserService.getCodeByUserEmail(userEmail);
		return SUCCESS;
	}
	
	/**用户登录*/
	public String login() {
		jsonResult=iUserService.login(session, userName, code);
		return SUCCESS;
	}
	
	/**用户退出*/
	public String logout() {
		jsonResult=iUserService.logout(session);
		return SUCCESS;
	}
	
	/**判断邮箱是否被使用*/
	public String containUserEmail() {
		jsonResult=iUserService.EmailIsLive(userEmail);
		return SUCCESS;
	}
	
	/**用户注册*/
	public String regist() {
		jsonResult=iUserService.regist(userName, userEmail, code);
		return SUCCESS;
	}
	
	/**用户更改邮箱*/
	public String update() {
		jsonResult=iUserService.update(userName, oldEmail, newEmail, code);
		return SUCCESS;
	}
	
	/**获取当前登录的用户*/
	public String info() {
		User user=(User)session.getAttribute(Const.CURRENT_USER);
		if(user==null) {
			jsonResult=JsonResult.errorMsg("当前用户未登录");
		}else {
			jsonResult=JsonResult.ok(user.getUserName());
		}
		return SUCCESS;
	}
}
