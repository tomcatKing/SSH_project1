package com.mt.service;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import com.alipay.api.AlipayApiException;
import com.mt.result.JsonResult;

public interface IOrderService {
	/**创建订单*/
	JsonResult createOrder(Integer userId);
	
	/**取消订单*/
	JsonResult cancelOrder(Integer userId,Long orderNo);
	
	/**支付订单*/
	JsonResult pay(Integer userId,Long orderNo);
	
	/**订单详情(这里不允许查看别人的订单详情)*/
	JsonResult detail(Integer userId,Long orderNo);
	
	/**支付宝异步回调*/
	JsonResult alipay_notify_back(HttpServletRequest request)throws UnsupportedEncodingException, AlipayApiException;
	
	/**支付宝同步回调*/
	JsonResult alipay_return_back(HttpServletRequest request)throws AlipayApiException, UnsupportedEncodingException;
	
	/**显示用户的订单*/
	JsonResult list(Integer userId,int pageNum,int pageSize);
	
	/**显示用户的未支付订单*/
	JsonResult nopay(Integer userId,int pageNum,int pageSize);
	
	/**显示用户的已支付订单*/
	JsonResult inpay(Integer userId,int pageNum,int pageSize);
	
	/**显示用户的已完成订单*/
	JsonResult success(Integer userId,int pageNum,int pageSize);
	
	/**显示用户的指定订单的订单状态*/
	JsonResult status(Integer userId, Long orderNo);
	
	/**关闭未支付订单*/
	JsonResult closeOrder();
}
