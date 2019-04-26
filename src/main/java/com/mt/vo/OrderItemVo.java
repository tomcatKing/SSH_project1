package com.mt.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.mt.pojo.Food;
import com.mt.pojo.OrderItem;

import lombok.Data;

@Data
public class OrderItemVo implements Serializable{
	private Integer orderItemId;
	private Integer foodId;
	private String foodName;
	private String foodImg;
	private BigDecimal foodPrice;
	private Integer foodNum;
	private BigDecimal totalPrice;
	
	public static OrderItemVo accessOrderItemVo(OrderItem orderItem) {
		OrderItemVo orderItemVo=new OrderItemVo();
		orderItemVo.setOrderItemId(orderItem.getOrderItemId());
		Food food=orderItem.getFoodId();
		orderItemVo.setFoodId(food.getFoodId());
		orderItemVo.setFoodName(food.getFoodName());
		orderItemVo.setFoodImg(food.getFoodImg());
		orderItemVo.setFoodPrice(food.getFoodPrice());
		orderItemVo.setFoodNum(orderItem.getFoodNum());
		orderItemVo.setTotalPrice(orderItem.getTotalPrice());
		
		return orderItemVo;
	}
}
