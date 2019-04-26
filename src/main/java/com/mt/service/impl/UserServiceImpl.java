package com.mt.service.impl;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mt.common.Const;
import com.mt.dao.IUserDao;
import com.mt.pojo.User;
import com.mt.result.JsonResult;
import com.mt.service.IUserService;
import com.mt.util.MailUtil;
import com.mt.util.TokenCache;

import lombok.extern.log4j.Log4j;

@Service
@Transactional
@Log4j
public class UserServiceImpl implements IUserService {
	@Autowired
	private IUserDao iUserDao;
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public JsonResult regist(String userName, String userEmail, String code) {
		log.info("注册服务已被使用:userName->"+userName+",userEmail->"+userEmail+",code->"+code);
		if(StringUtils.isBlank(userName) || StringUtils.isBlank(userEmail) || StringUtils.isBlank(code)) {
			return JsonResult.errorMsg("传入参数错误");
		}
		//3.判断guava缓存中是否已经验证码
		String code_val=TokenCache.getKey(userEmail);
		if(StringUtils.isBlank(code_val)) {
			return JsonResult.errorMsg("验证码已过期!!");
		}
		if(!code_val.equals(code)) {
			return JsonResult.errorMsg("验证不通过!!");
		}
		//清除缓存
		TokenCache.clearKey(userEmail);
		
		//1.判断用户是否已经存在
		if(iUserDao.containUserName(userName)) {
			return JsonResult.errorMsg("用户名已经存在!!");
		}
		
		//2.判断邮箱是否已经被使用
		if(iUserDao.containUserEmail(userEmail)) {
			return JsonResult.errorMsg("邮箱已经被使用");
		}
		
		//3.注册用户信息
		User user=new User();
		user.setUserName(userName);
		user.setUserEmail(userEmail);
		
		boolean count=iUserDao.saveUser(user);
		if(count==true) {
			return JsonResult.ok();
		}
		return JsonResult.errorMsg("添加用户失败");
	}

	@Override
	@Transactional(readOnly=true)
	public JsonResult login(HttpSession session,String userName, String code) {
		log.info("登录服务已被使用:userName->"+userName+",code->"+code);
		if(StringUtils.isBlank(userName) || StringUtils.isBlank(code)) {
			return JsonResult.errorMsg("传入参数错误");
		}
		User user=iUserDao.getUserByName(userName);
		if(user==null) {
			return JsonResult.errorMsg("用户不存在");
		}
		String userEmail=user.getUserEmail();
		String code_val=TokenCache.getKey(userEmail);
		if(StringUtils.isBlank(code_val)) {
			return JsonResult.errorMsg("验证码已经过期!!");
		}
		if(!code.equals(code_val)) {
			return JsonResult.errorMsg("验证不通过!!");
		}
		//清除缓存
		TokenCache.clearKey(userEmail);
		
		//验证通过
		session.setAttribute(Const.CURRENT_USER, user);
		return JsonResult.ok();
	}

	@Override
	public JsonResult logout(HttpSession session) {
		log.info("退出服务已被使用");
		if(session.getAttribute(Const.CURRENT_USER)==null) {
			return JsonResult.errorMsg("用户未登录");
		}
		session.removeAttribute(Const.CURRENT_USER);
		return JsonResult.ok();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public JsonResult update(String userName, String oldEmail, String newEmail, String code) {
		log.info("重置服务已被使用,userName->"+userName+",oldEmail->"+oldEmail+",");
		if(StringUtils.isBlank(userName) || StringUtils.isBlank(oldEmail) || StringUtils.isBlank(newEmail) || StringUtils.isBlank(code)) {
			return JsonResult.errorMsg("传入参数错误");
		}
		String code_val=TokenCache.getKey(oldEmail);
		if(code_val==null) {
			return JsonResult.errorMsg("验证码已过期");
		}
		if(!code_val.equals(code)) {
			return JsonResult.errorMsg("验证不通过"); 
		}
		User user=iUserDao.getUserByUserNameAndUserEmail(userName, oldEmail);
		if(user==null) {
			return JsonResult.errorMsg("用户名和邮箱验证不一致");
		}
		boolean result=iUserDao.containUserEmail(newEmail);
		if(result==true) {
			return JsonResult.errorMsg("新邮箱已经被使用");
		}
		//清除缓存
		TokenCache.clearKey(oldEmail);
		
		user.setUserEmail(newEmail);
		iUserDao.update(user);
		
		return JsonResult.ok();
	}

	@Override
	@Transactional(readOnly=true)
	public JsonResult getCodeByUserName(String userName) {
		log.info("获取验证码服务(byName)已经启动,userName->:"+userName);
		// TODO Auto-generated method stub
		if(StringUtils.isBlank(userName)) {
			return JsonResult.errorMsg();
		}
		User user=iUserDao.getUserByName(userName);
		if(user==null) {
			return JsonResult.errorMsg("不存在这个用户");
		}
		String userEmail=user.getUserEmail();
		//发送验证码
		MailUtil.send_mail(userEmail);
		return JsonResult.ok();
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public JsonResult getCodeByUserEmail(String emailName) {
		log.info("发送验证码服务启动,emailName->:"+emailName);
		//发送验证码
		String result=MailUtil.send_mail(emailName);
		if(StringUtils.isBlank(result)) {
			return JsonResult.errorMsg("服务器繁忙,请稍后重试!!");
		}
		return JsonResult.ok();
	}

	@Override
	@Transactional(readOnly=true)
	public JsonResult UserisLive(String userName) {
		log.info("判断用户名是否可以使用的服务启动,userName->:"+userName);
		boolean result=iUserDao.containUserName(userName);
		if(result==true) {
			return JsonResult.errorMsg("当前用户名已经被使用");
		}
		return JsonResult.ok();
	}

	@Override
	@Transactional(readOnly=true)
	public JsonResult EmailIsLive(String userEmail) {
		log.info("判断当前的邮箱是否可以使用服务启动,userEmail->:"+userEmail);
		boolean result=iUserDao.containUserEmail(userEmail);
		if(result==true) {
			return JsonResult.errorMsg("当前邮箱已经被使用");
		}
		return JsonResult.ok();
	}

}
