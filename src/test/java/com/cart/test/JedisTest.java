package com.cart.test;

import com.mt.common.RedisPool;

import redis.clients.jedis.Jedis;

public class JedisTest {
	public static void main(String[] args) throws InterruptedException {
		Jedis jedis=RedisPool.getJedis();
		
		System.out.println("1");
		jedis.setex("a", 1, "123");
		String val1=jedis.get("a");
		
		Thread.sleep(1000);
		String val2=jedis.get("a");
		
		System.out.println("过期前:"+val1+",过期后:"+val2);
	}
}
