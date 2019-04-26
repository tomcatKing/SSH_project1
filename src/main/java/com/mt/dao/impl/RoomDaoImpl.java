package com.mt.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mt.dao.IRoomDao;
import com.mt.pojo.Room;
import com.mt.util.Page;

@Repository
public class RoomDaoImpl implements IRoomDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Room getRoomById(Integer roomId) {
		Session session=sessionFactory.getCurrentSession();
		Room room=(Room)session.get(Room.class,roomId);
		return room;
	}

	@Override
	public void update(Room room) {
		Session session=sessionFactory.getCurrentSession();
		session.update(room);
	}

	@Override
	public Page list(Page p, Map<String, Integer> params) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery(p.getHql()).setCacheable(true);
		query.setInteger("roomStatus",params.get(":roomStatus"));
		
		int count=query.list().size();
		//设置从那个对象开始查询,参数的索引未知从第当前页*页数开始
		query.setFirstResult((p.getCurrentPage()-1)*p.getPageRows());
		query.setMaxResults(p.getPageRows());
		
		List<Room> roomList=query.list();
		p=Page.initPage(p, roomList, count);
		return p;
	}

}
