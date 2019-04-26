package com.mt.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class SuperAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	protected ServletContext application;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	
	public SuperAction() {
		request=ServletActionContext.getRequest();
		session=request.getSession();
		response=ServletActionContext.getResponse();
		application=request.getServletContext();
	}
	
}