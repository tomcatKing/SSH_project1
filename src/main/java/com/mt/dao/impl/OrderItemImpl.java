package com.mt.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mt.dao.IOrderItemDao;
import com.mt.pojo.OrderItem;

@Repository
public class OrderItemImpl implements IOrderItemDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void addOrderItem(OrderItem orderItem) {
		Session session=sessionFactory.getCurrentSession();
		session.persist(orderItem);
	}

	@Override
	public List<OrderItem> getOrderItemsByOrderNo(Long orderNo) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from OrderItem where orderNo=:orderNo");
		query.setLong("orderNo", orderNo);
		List<OrderItem> orderItemList=query.list();
		return orderItemList;
	}

}
