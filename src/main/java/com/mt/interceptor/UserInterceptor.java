package com.mt.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Component;

import com.mt.common.Const;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@Component
public class UserInterceptor extends AbstractInterceptor{
	private static final long serialVersionUID = 1L;
	
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		System.out.println("用户方法拦截");
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session=request.getSession();
		
		Object obj=session.getAttribute(Const.CURRENT_USER);
		if(obj==null) {
			System.out.println("不放行");
			//当前没有用户登录,不放行
			return "success";
		}
		//放行
		return invocation.invoke();
	}
	
}
