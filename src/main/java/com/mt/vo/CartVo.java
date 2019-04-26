package com.mt.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.mt.pojo.Cart;
import com.mt.pojo.Food;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CartVo implements Serializable{
	private Integer cartId;
	private String foodImg;
	private String foodName;
	private Integer foodNum;
	private BigDecimal foodPrice;
	
	public static CartVo accessCartVo(Cart cart) {
		CartVo cartVo=new CartVo();
		cartVo.setCartId(cart.getCartId());
		Food food=cart.getFoodId();
		cartVo.setFoodImg(food.getFoodImg());
		cartVo.setFoodName(food.getFoodName());
		cartVo.setFoodNum(cart.getFoodNum());
		cartVo.setFoodPrice(food.getFoodPrice());
		return cartVo;
	}
}
