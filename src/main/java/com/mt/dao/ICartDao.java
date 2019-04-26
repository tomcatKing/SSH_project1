package com.mt.dao;

import java.util.List;
import java.util.Map;

import com.mt.pojo.Cart;
import com.mt.result.JsonResult;
import com.mt.util.Page;

public interface ICartDao {
	Page list(Page page,Map<String,Integer> params);
	
	JsonResult add(Integer userId, Integer foodId, Integer foodNum);

	JsonResult remove(Integer userId, Integer cartId);
	
	JsonResult update(Integer userId,Integer cartId,Integer foodNum);
	
	List<Cart> getCartByChecked(Integer userId);
	
	//通过美食的id获取购物车,因为购物车中的美食id是唯一的
	Cart getCartByFoodId(Integer userId,Integer foodId);
	
	//获取用户的购物车的长度,如果超过10条就不允许添加
	int getUserCartSize(Integer userId);
	
}
