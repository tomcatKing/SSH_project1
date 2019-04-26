package com.mt.dao;

import java.util.List;

import com.mt.pojo.OrderItem;

public interface IOrderItemDao {
	void addOrderItem(OrderItem orderItem);
	
	List<OrderItem> getOrderItemsByOrderNo(Long orderNo);
}
