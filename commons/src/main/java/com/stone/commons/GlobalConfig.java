package com.stone.commons;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

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
            properties.load(new InputStreamReader(cpr.getInputStream(), "UTF-8"));
            ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resourcePatternResolver.getResources("classpath*:META-INF/config/*.properties");
            for(Resource resource : resources){
                properties.load(new InputStreamReader(resource.getInputStream(), "UTF-8"));
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
