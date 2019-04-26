package com.mt.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.log4j.Log4j;

/**
 *@Title PropertiesUtil
 *@Author TomcatBbzzzs
 *@Date 2019/2/7 08:53:01
 */
@Log4j
public class PropertiesUtil {
    private static Properties props;
    
    //读取tomcatBbzzzs.properties
    static {
        String fileName = "tomcatBbzzzs.properties";
        props = new Properties();
        try {
            props.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName),"UTF-8"));
        } catch (IOException e) {
            log.error("[tomcatBbzzzs.properties配置文件初始化失败"+fileName+"了]",e);
        }
    }
    
    /**读取配置文件中的key对应的val*/
    public static String getProperty(String key){
        String value = props.getProperty(key.trim());
        log.info("[获取key="+key+"的value...]");
        if(StringUtils.isNotBlank(value)) {
        	 return value.trim();
        }
        return null;
    }

    /**读取配置文件中的key对应的val,当值不存在时,允许给一个默认值*/
    public static String getProperty(String key,String defaultValue){
        String value = props.getProperty(key.trim());
        if(StringUtils.isNotBlank(value)) {
	       return value.trim();
	    }
        return defaultValue;
    }
}