package com.mt.common;

import com.mt.util.PropertiesUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {
	//保证jedis连接池在tomcat启动时连接
	private static JedisPool pool; //jedis连接池
	private static Integer maxTotal=Integer.parseInt(PropertiesUtil.getProperty("redis.max.total","20")); //最大连接数
	private static Integer maxIdle=Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle","10"));; //最大空闲
	private static Integer minIdle=Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle","2"));; //最小空闲
	private static Boolean testOnBorrow=Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow","true"));; //连接jedisPool是否成功,返回结果
	private static Boolean testOnReturn=Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return","true"));; //在return一个jedis实例的时候,是否要进行验证操作.
	//设置连接jedis的ip
	private static String redisIp=PropertiesUtil.getProperty("redis.ip");
	//设置连接jedis的port
	private static Integer port=Integer.parseInt(PropertiesUtil.getProperty("redis.port","6379"));
	//设置超时时间
	
	//这个类只能调用1次
	private static void initPool() {
		JedisPoolConfig config=new JedisPoolConfig();
		config.setMaxTotal(maxTotal);
		config.setMaxIdle(maxIdle);
		config.setMinIdle(minIdle);
		config.setTestOnBorrow(testOnBorrow);
		config.setTestOnReturn(testOnReturn);
		
		//当连接耗尽时,是否阻塞,false会抛出异常,true阻塞直到超时(TimeoutException).默认true
		config.setBlockWhenExhausted(true);
		
		pool=new JedisPool(config, redisIp, port,1000*2);
	}
	//静态代码块,只执行1次
	static {
		initPool();
	}
	
	
	public static Jedis getJedis() {
		return pool.getResource();
	}
	/**当连接是成功的*/
	public static void returnResource(Jedis jedis) {
		jedis.close();
	}
	/**当连接是失败的*/
	public static void returnBrokenResource(Jedis jedis) {
		//这里不加是否为null的判断是因为源码中已经帮我们判断了
		jedis.close();
	}
}		