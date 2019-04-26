package com.mt.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.mt.common.Const;
import com.mt.pojo.Food;
import com.mt.util.DateUtil;

import lombok.Data;

@Data
public class FoodVo implements Serializable{
	private Integer foodId;
	private String foodType;
	private String foodName;
	private String foodImg;
	private String foodDetail;
	private BigDecimal foodPrice;
	private String foodStatus;
//	private String createTime;
//	private String updateTime;
	
	public static FoodVo accessFoodVo(Food food) {
		FoodVo foodVo=new FoodVo();
		foodVo.setFoodId(food.getFoodId());
		foodVo.setFoodType(Const.FoodTypeEnum.codeOf(food.getFoodType()).getValue());
		foodVo.setFoodName(food.getFoodName());
		foodVo.setFoodImg(food.getFoodImg());
		foodVo.setFoodDetail(food.getFoodDetail());
		foodVo.setFoodPrice(food.getFoodPrice());
		foodVo.setFoodStatus(Const.FoodStatusEnum.codeOf(food.getFoodStatus()).getValue());
//		foodVo.setCreateTime(DateUtil.dateToString(food.getCreateTime()));
//		foodVo.setUpdateTime(DateUtil.dateToString(food.getUpdateTime()));
		return foodVo;
	}
	
}
