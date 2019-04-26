package com.mt.util;

import lombok.extern.log4j.Log4j;


/**
 *@Title 关于问题的缓存类
 *@Author TomcatBbzzzs
 *@Desctiption 主要用于存放问题答案的有效时长,当超过这个时间后,必须重新加载
 *@Date 2019/2/6 12:20:12
 *由Guava提供,使用Tomcat集群就不需要使用这个了
 */
@Log4j
public class TokenCache {
	//身份标识
	public static final String TOKEN_PREFIX="email_token_"; //邮箱验证码标志
	//超时事件
	public static final Integer TOKEN_TIMEOUT=5;
	
	/**存放缓存到guava中*/
	public static void setKey(String key, String value) {
		JedisUtil.setEx(TOKEN_PREFIX+key,value,180);
	}
	
	/**读物缓存*/
	public static String getKey(String key) {
		String value = null;
		try {
			value=JedisUtil.get(TOKEN_PREFIX+key);
			if (value==null || "null".equals(value)) {
				return null;
			}
			return value;
		} catch (Exception e) {
			log.error("guava缓存读取失败",e);
		}
		return null;
	}
	
	/**清除缓存*/
	public static void clearKey(String key) {
		JedisUtil.expire(TOKEN_PREFIX+key, 0);
	}
	
	
}

