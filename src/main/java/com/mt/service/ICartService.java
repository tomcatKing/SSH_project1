package com.mt.service;

import com.mt.result.JsonResult;

public interface ICartService {
	//获取指定用户的列表清单
	JsonResult list(Integer userId,int pageNum,int pageSize);

	//添加购物车
	JsonResult add(Integer userId, Integer foodId, Integer foodNum);
	
	//删除购物车
	JsonResult remove(Integer userId, Integer cartId);
	
	//修改购物车
	JsonResult update(Integer userId,Integer cartId,Integer foodNum);
}
