package com.mt.util;

import java.util.Random;

import javax.mail.MessagingException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import lombok.extern.log4j.Log4j;

/**
 * 
 * @author	TomcatBbzzzs
 * @Desc	邮箱工具类
 */
@Log4j
public class MailUtil {
	private static Random random=new Random();
	/**
	 * 
	 * @param toMail		目标邮箱
	 * @throws EmailException 
	 * @throws MessagingException 
	 */
	public static String send_mail(String toMail) {
		HtmlEmail email=new HtmlEmail();
		
		email.setHostName("smtp.163.com");
		
		email.setCharset("utf-8"); 
		try {
			email.addTo(toMail); 
			
			
			email.setFrom(PropertiesUtil.getProperty("enail.emailname", "15113437287@163.com"), 
					PropertiesUtil.getProperty("email.username","[TomcatBbzzzs]"));
	

			email.setAuthentication(PropertiesUtil.getProperty("enail.emailname", "15113437287@163.com"), 
					PropertiesUtil.getProperty("email.token","tomcat1234"));
		
			email.setSubject("==美食之家发送的验证码==");
			
			String codeValue=Integer.toString(random.nextInt(9000)+1000);
			
			email.setMsg("本次服务的验证码为:"+codeValue+",请保存你的验证码,不要告诉别人,验证码在5分钟后会自动销毁");
			log.info("已发送验证码:["+codeValue+"],到邮箱:["+toMail+"]");
			String returnMsg=email.send();
			if(StringUtils.isBlank(returnMsg)) {
				log.info("发送验证码失败");
				return null;
			}
			else {
				//邮件发送成功后存入到guava中
				TokenCache.setKey(toMail, codeValue);
			}
			return codeValue;
		}catch(EmailException e) {
			log.info("服务器发送邮件出现异常了:",e);
			return null;
		}
	}
}
