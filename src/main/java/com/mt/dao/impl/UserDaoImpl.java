package com.mt.dao.impl;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mt.dao.IUserDao;
import com.mt.pojo.User;

@Repository
public class UserDaoImpl implements IUserDao{
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public User getUserById(Integer userId) {
		Session session=sessionFactory.getCurrentSession();
		User user=(User)session.get(User.class, userId);
		return user;
	}

	@Override
	public boolean containUserName(String userName) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("select count(1) from User where userName=:userName");
		query.setString("userName", userName);
		Long count=(Long)query.uniqueResult();
		if(count>0) {
			//用户已经存在
			return true;
		}
		return false;
	}

	@Override
	public boolean containUserEmail(String userEmail) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("select count(1) from User where userEmail=:userEmail");
		query.setString("userEmail", userEmail);
		Long count=(Long)query.uniqueResult();
		if(count>0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean saveUser(User user) {
		Session session=sessionFactory.getCurrentSession();
		session.save(user);
		if(user.getUserId()!=null || user.getUserId()>0) {
			return true;
		}
		return false;
	}

	@Override
	public User getUserByName(String userName) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from User where userName=:userName");
		query.setString("userName", userName);
		User user=(User)query.uniqueResult();
		if(user==null) {
			return null;
		}
		return user;
	}

	@Override
	public User getUserByUserNameAndUserEmail(String userName, String userEmail) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from User where userName=:userName and userEmail=:userEmail");
		query.setString("userName", userName);
		query.setString("userEmail", userEmail);
		User user=(User)query.uniqueResult();
		return user;
	}

	@Override
	public void update(User user) {
		Session session=sessionFactory.getCurrentSession();
		session.update(user);
	}

	
}
