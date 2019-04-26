package com.mt.interceptor;

import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@Component
public class ExceptionInterceptor extends AbstractInterceptor{
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		System.out.println("开启异常拦截");
		String result="";
		try {
			result=invocation.invoke();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("进入异常处理结果");
			result="exception";
		}
		return result;
		
	}
	
}
