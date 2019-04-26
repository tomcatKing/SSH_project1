package com.mt.dao;

import java.util.Map;

import com.mt.pojo.Room;
import com.mt.util.Page;

public interface IRoomDao {
	Room getRoomById(Integer roomId);
	
	void update(Room room);
	
	Page list(Page p, Map<String, Integer> params);
}
