package com.mt.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.mt.common.Const;
import com.mt.dao.IOrderDao;
import com.mt.pojo.Order;
import com.mt.pojo.OrderItem;
import com.mt.pojo.Room;
import com.mt.util.DateUtil;
import com.mt.util.Page;
import com.mt.vo.OrderItemVo;
import com.mt.vo.OrderVo;
import com.mt.vo.RoomVo;

@Repository
public class OrderDaoImpl implements IOrderDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public void addOrder(Order order) {
		Session session=sessionFactory.getCurrentSession();
		session.persist(order);
	}

	@Override
	public Order getOrderByUserIdAndOrderNo(Integer userId, Long orderNo) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("select new com.mt.pojo.Order(o.orderId,o.orderNo,o.status) from com.mt.pojo.Order o where o.userId.userId=:userId and o.orderNo=:orderNo");
		query.setInteger("userId", userId);
		query.setLong("orderNo", orderNo);
		Order order=(Order) query.uniqueResult();
		return order;
	}
	
	@Override
	public void updateOrder(Order order) {
		Session session=sessionFactory.getCurrentSession();
		session.update(order);
	}

	@Override
	public Order getOrderByUserIdAndOrderNoSuper(Integer userId, Long orderNo) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from com.mt.pojo.Order where userId.userId=:userId and orderNo=:orderNo");
		query.setInteger("userId", userId);
		query.setLong("orderNo", orderNo);
		Order order=(Order) query.uniqueResult();
		return order;
	}

	@Override
	public Order getOrderByOrderNo(Long orderNo) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Order where orderNo=:orderNo");
		query.setLong("orderNo", orderNo);
		Order order=(Order)query.uniqueResult();
		return order;
	}

	@Override
	public Page list(Page p, Map<String, Integer> params) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery(p.getHql());
		query.setInteger("userId", params.get(":userId"));
		int count=query.list().size();
		
		query.setFirstResult((p.getCurrentPage()-1)*p.getPageRows());
		query.setMaxResults(p.getPageRows());
		
		List<Order> orderList=query.list();
		
		p=Page.initPage(p, orderList, count);
		
		return p;
	}

	@Override
	public Page getOrderListByStatus(Page page,Map<String,Integer> params) {
		Session session=sessionFactory.getCurrentSession();
		page.setHql("from Order o where o.status=:status and o.userId.userId=:userId");
		Query query=session.createQuery(page.getHql());
		query.setInteger("userId", params.get(":userId"));
		query.setInteger("status", params.get(":status"));
		int count=query.list().size();
		
		query.setFirstResult((page.getCurrentPage()-1)*page.getPageRows());
		query.setMaxResults(page.getPageRows());
		
		List<Order> orderList=query.list();
		
		page=page.initPage(page, orderList, count);
		return page;
	}

	@Override
	public long list(Integer userId) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("select count(1) from Order o where o.userId.userId=:userId and status=:status");
		query.setInteger("userId", userId);
		query.setInteger("status", Const.OrderStatusEnum.NOPAY.getCode());
		Long result=(Long)query.uniqueResult();
		return result;
	}

	@Override
	public List<Order> getOrdersByUserIdAndRoomId(Integer userId, Integer roomId) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Order o where o.userId.userId=:userId and o.roomId.roomId=:roomId");
		query.setInteger("userId", userId);
		query.setInteger("roomId", roomId);
		List<Order> orderList=query.list();
		return orderList;
	}

	@Override
	public List<Order> getOrders() {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from com.mt.pojo.Order");
		List<Order> orderList=query.list();
		
		return orderList;
	}


	

}
