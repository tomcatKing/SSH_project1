package com.mt.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mt.dao.IFoodDao;
import com.mt.pojo.Food;
import com.mt.util.Page;
import com.mt.vo.FoodVo;

import lombok.extern.log4j.Log4j;

@Repository
@Log4j
public class FoodDaoImpl implements IFoodDao{
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Page pageResult(Page p,Map<String,String> params) {
		log.info("foodDaoImpl开始执行了listByWhere语句,hql语句为:"+p.getHql());
		Session session=sessionFactory.getCurrentSession();
		
		//from 
		String hql=p.getHql();
		//这里必须德加上一个缓存
		Query query=session.createQuery(hql).setCacheable(true);
		//如果hql包含keyword
		if(hql.contains(":keyword")) {
			query.setString("keyword", params.get(":keyword"));
		}
		//如果hql包含orderBy
		if(hql.contains(":orderBy")) {
			query.setString("orderBy", params.get(":orderBy"));
		}
		int count=query.list().size(); //获取总数据数
		System.out.println("查询到的数据量为:"+count);
		
		//设置从那个对象开始查询,参数的索引未知从第当前页*页数开始
		query.setFirstResult((p.getCurrentPage()-1)*p.getPageRows());
		query.setMaxResults(p.getPageRows());
		
		List<Food> foodList=query.list();

		p=Page.initPage(p, foodList, count);
		
		//这个事务是可读事务.可以不提交
		return p;
	}

	@Override
	public FoodVo detail(Integer foodId) {
		Session session=sessionFactory.getCurrentSession();
		Food food=(Food) session.get(Food.class,foodId);
		FoodVo foodVo=FoodVo.accessFoodVo(food);
		return foodVo;
	}

	@Override
	public Food getFoodById(Integer foodId) {
		Session session=sessionFactory.getCurrentSession();
		return (Food)session.get(Food.class, foodId);
	}

	
	

}
