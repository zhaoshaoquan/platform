package com.stone.commons;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * 全局读取配置文件
 * @author 赵少泉
 * 2016年2月6日 下午10:37:27
 */
public class GlobalConfig{
	private static final Logger log = LoggerFactory.getLogger(GlobalConfig.class);
	private static final Properties properties = new Properties();
	static{
		try{
			ClassPathResource cpr = new ClassPathResource("config.properties");
			properties.load(cpr.getInputStream());
			String configs = properties.getProperty("config.properties");
			if(StringUtils.isNotBlank(configs)){
				for(String config : configs.split(",")){
					ClassPathResource c = new ClassPathResource(config);
					if(c.exists())properties.load(c.getInputStream());
				}
			}
			log.info("====配置文件已读取完成====");
		}catch(IOException e){
			log.error(e.getMessage(), e);
		}
	}
	
	public static String get(String key){
		return get(key, StringUtils.EMPTY);
	}
	
	public static String get(String key,String defaultValue){
		return properties.getProperty(key, defaultValue);
	}

}
