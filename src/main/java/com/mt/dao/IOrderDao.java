package com.mt.dao;

import java.util.List;
import java.util.Map;

import com.mt.pojo.Order;
import com.mt.util.Page;

public interface IOrderDao {
	void addOrder(Order order);
	
	/**通过用户id和订单编号获取订单*/
	Order getOrderByUserIdAndOrderNo(Integer userId,Long orderNo);
	
	/**修改订单*/
	void updateOrder(Order order);
	
	/**通过用户id和订单编号获取订单(完整数据版)*/
	Order getOrderByUserIdAndOrderNoSuper(Integer userId,Long orderNo);
	
	Order getOrderByOrderNo(Long orderNo);
	
	/**获取指定用户的所有订单*/
	Page list(Page p, Map<String, Integer> params);
	
	/**获取用户的所有订单的数量*/
	long list(Integer userId);
	
	/**获取用户的所有指定状态订单*/
	Page getOrderListByStatus(Page p,Map<String,Integer> params);
	
	/**通过用户名和房间id获取订单*/
	List<Order> getOrdersByUserIdAndRoomId(Integer userId, Integer roomId);
	
	/**获取所有订单*/
	List<Order> getOrders();
	
}
