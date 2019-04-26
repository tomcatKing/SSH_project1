package com.mt.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mt.dao.IPayInfoDao;
import com.mt.pojo.PayInfo;

@Repository
public class PayInfoImpl implements IPayInfoDao{
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void addPayInfo(PayInfo payInfo) {
		Session session=sessionFactory.getCurrentSession();
		session.save(payInfo);
	}
	
}
