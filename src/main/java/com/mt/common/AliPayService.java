package com.mt.common;

import java.util.Date;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.mt.config.AlipayConfig;
import com.mt.pojo.Order;
import com.mt.result.JsonResult;
import com.mt.util.DateUtil;

import lombok.extern.log4j.Log4j;

@Log4j
public class AliPayService {
	
	public static JsonResult payService(Order order) {
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
		
		//设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(AlipayConfig.return_url);
		alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
		
		//商户订单号，商户网站订单系统中唯一订单号，必填
		String out_trade_no = new String(order.getOrderNo().toString());
		//付款金额，必填
		
		String total_amount = new String(order.getPayment().toString());
		//订单名称，必填
		String subject = new String(order.getOrderNo().toString());
		//商品描述，可空
		String body = new String("用户于["+DateUtil.dateToString(new Date())+"]在美食之家,购买了价值"+order.getPayment()+"元的订单");
		
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
				+ "\"total_amount\":\""+ total_amount +"\"," 
				+ "\"subject\":\""+ subject +"\"," 
				+ "\"body\":\""+ body +"\"," 
				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
		
		//请求
		String result="";
		try {
			result = alipayClient.pageExecute(alipayRequest).getBody();
		} catch (AlipayApiException e) {
			log.error("支付异常",e);
			return JsonResult.errorMsg("支付失败0~0");
		}
		System.out.println("网页支付返回:"+result);
		
		return JsonResult.ok(result);
	}
}
