package com.mt.service;

import com.mt.result.JsonResult;

public interface IFoodService {
	
	//获取食物列表
	JsonResult list(int pageNum,int pageSize,String orderBy,String keyword,String foodTypes);

	//获取美食详情
	JsonResult detail(Integer foodId); 
}
