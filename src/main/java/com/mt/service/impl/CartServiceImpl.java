package com.mt.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.mt.dao.ICartDao;
import com.mt.dao.IFoodDao;
import com.mt.result.JsonResult;
import com.mt.service.ICartService;
import com.mt.util.Page;

import lombok.extern.log4j.Log4j;

@Service
@Transactional
@Log4j
public class CartServiceImpl implements ICartService {
	@Autowired
	private ICartDao iCartDao;
	
	@Autowired
	private IFoodDao iFoodDao;
	
	@Override
	@Transactional(readOnly=true)
	public JsonResult list(Integer userId,int pageNum,int pageSize) {
		log.info("获取购物车列表服务启动了");
		Page p=new Page();
		p.setCurrentPage(pageNum);
		p.setPageRows(pageSize);
//		StringBuilder sb=new StringBuilder("select new Cart(c.cartId,c.foodId,c.foodNum) from Cart c where c.userId.userId=:userId ");
		p.setHql("select c.cartId,c.foodId,c.foodNum from Cart c where c.userId.userId=:userId");
		Map<String,Integer> params=Maps.newHashMap();
		params.put(":userId", userId);
		Page result=iCartDao.list(p,params);
		return JsonResult.ok(result);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public JsonResult add(Integer userId, Integer foodId, Integer foodNum) {
		log.info("添加美食到购物车的服务启动了,userId->:"+userId+",foodId->:"+foodId+",foodNum->:"+foodNum);
		if(userId==null || foodId==null || foodNum==null || userId<1 || foodId<1 || foodNum<1) {
			return JsonResult.errorMsg();
		}
		return iCartDao.add(userId, foodId, foodNum);
		
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public JsonResult remove(Integer userId, Integer cartId) {
		log.info("删除购物车的服务启动了,userId->:"+userId+",cartId->:"+cartId);
		return iCartDao.remove(userId,cartId);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public JsonResult update(Integer userId,Integer cartId,Integer foodNum) {
		log.info("更新购物车的服务启动了,userId->:"+userId+",cartId->:"+cartId+",foodNum->:"+foodNum);
		if(cartId==null || foodNum==null ) {
			return JsonResult.errorMsg("传入参数异常");
		}
		return iCartDao.update(userId, cartId, foodNum);
	}

}
