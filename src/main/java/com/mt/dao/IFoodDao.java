package com.mt.dao;

import java.util.Map;

import com.mt.pojo.Food;
import com.mt.util.Page;
import com.mt.vo.FoodVo;

public interface IFoodDao {
	
	Page pageResult(Page p,Map<String,String> params);

	FoodVo detail(Integer foodId);
	
	Food getFoodById(Integer foodId);
}
