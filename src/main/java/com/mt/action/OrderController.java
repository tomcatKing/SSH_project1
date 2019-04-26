package com.mt.action;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alipay.api.AlipayApiException;
import com.mt.common.Const;
import com.mt.pojo.User;
import com.mt.result.JsonResult;
import com.mt.service.IOrderService;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Controller
@Scope("prototype")
@Log4j
public class OrderController extends SuperAction{
	private static final long serialVersionUID = 3814369782310130150L;
	@Autowired
	private IOrderService iOrderService;
	
	@Getter
	private JsonResult jsonResult=JsonResult.errorMsg("当前用户未登录"); 
	
	@Setter
	private Long orderNo;
	
	@Setter
	private int pageNum=1;
	
	@Setter
	private int pageSize=10;
	
	@Getter
	private JsonResult errorResult=JsonResult.errorMsg("请求出错了");
	
	/**订单详情*/
	public String orderDetail() {
		User user=(User)session.getAttribute(Const.CURRENT_USER);
		Integer userId=user.getUserId();
		log.info("获取订单详情的action启动了,userId->"+userId+",oredrNo-->"+orderNo);
		jsonResult=iOrderService.detail(userId, orderNo);
		return SUCCESS;
	}
	
	/**获取订单状态(支付成功返回true)*/
	public String orderStatus() {
		User user=(User)session.getAttribute(Const.CURRENT_USER);
		Integer userId=user.getUserId();
		log.info("获取订单状态的action启动了,userId->"+userId+",oredrNo-->"+orderNo);
		jsonResult=iOrderService.status(userId, orderNo);
		return SUCCESS;
	}
	
	/**创建订单*/
	public String createOrder() {
		User user=(User)session.getAttribute(Const.CURRENT_USER);
		Integer userId=user.getUserId();
		log.info("创建订单的action启动了,userId->"+userId);
		jsonResult=iOrderService.createOrder(userId);
		return SUCCESS;
	}
	
	/**取消订单*/
	public String cancel() {
		User user=(User)session.getAttribute(Const.CURRENT_USER);
		Integer userId=user.getUserId();
		log.info("取消订单的action启动了,传入参数userId->"+userId+",orderNo->"+orderNo);
		jsonResult=iOrderService.cancelOrder(userId, orderNo);
		return SUCCESS;
	}
	
	/**列出订单*/
	public String list() {
		User user=(User)session.getAttribute(Const.CURRENT_USER);
		Integer userId=user.getUserId();
		log.info("获取所有订单的action启动了,传入userId->"+userId);
		jsonResult=iOrderService.list(userId,pageNum,pageSize);
		return SUCCESS;
	}
	
	/**支付订单*/
	public String pay() {
		User user=(User)session.getAttribute(Const.CURRENT_USER);
		Integer userId=user.getUserId();
		log.info("支付订单的action启动了,传入参数userId->"+userId+",orderNo->"+orderNo);
		jsonResult=iOrderService.pay(userId, orderNo);
		return SUCCESS;
	}
	
	/**支付宝异步回调
	 * @throws AlipayApiException 
	 * @throws UnsupportedEncodingException */
	public String ali_notify() throws UnsupportedEncodingException, AlipayApiException {
		log.info("支付宝异步回调的action启动了");
		jsonResult=iOrderService.alipay_notify_back(request);
		return SUCCESS;
	}
	
	/**支付宝同步回调
	 * @throws AlipayApiException 
	 * @throws UnsupportedEncodingException */
	public String ali_returnfy() throws UnsupportedEncodingException, AlipayApiException {
		log.info("支付宝同步回调的action启动了");
		jsonResult=iOrderService.alipay_return_back(request);
		return SUCCESS;
	}
	
	/**显示未支付订单*/
	public String nopay() {
		log.info("获取未支付订单的action启动了");
		User user=(User)session.getAttribute(Const.CURRENT_USER);
		jsonResult=iOrderService.nopay(user.getUserId(), pageNum, pageSize);
		return SUCCESS;
	}
	
	/**显示已支付订单*/
	public String ispay() {
		log.info("获取已支付订单的action启动了");
		User user=(User)session.getAttribute(Const.CURRENT_USER);
		jsonResult=iOrderService.inpay(user.getUserId(), pageNum, pageSize);
		return SUCCESS;
	}
	
	/**显示已完成订单*/
	public String success() {
		log.info("获取所有已经完成的订单的action启动了");
		User user=(User)session.getAttribute(Const.CURRENT_USER);
		jsonResult=iOrderService.success(user.getUserId(), pageNum, pageSize);
		return SUCCESS;
	}
}
