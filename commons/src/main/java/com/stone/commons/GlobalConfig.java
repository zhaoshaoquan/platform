package com.stone.commons;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 全局读取配置文件
 * @author 赵少泉
 * 2016年2月6日 下午10:37:27
 */
public class GlobalConfig{
	private static final Logger logger = LoggerFactory.getLogger(GlobalConfig.class);
	private static final Properties properties = new Properties();
	static{
        try{
            StringBuilder filePaths = new StringBuilder();
            ClassPathResource cpr = new ClassPathResource("config.properties");
            Assert.notNull(cpr, "classes目录中缺少config.properties配置文件");
            filePaths.append(cpr.getURI()).append("\n");
            properties.load(new InputStreamReader(cpr.getInputStream(), "UTF-8"));
            ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            Resource[] resources;
            String[] scanPath = {"classpath*:META-INF/config/*.properties", "classpath:*.properties"};
            for(String path : scanPath){
                resources = resourcePatternResolver.getResources(path);
                for(Resource resource : resources){
                    filePaths.append(resource.getURI()).append("\n");
                    properties.load(new InputStreamReader(resource.getInputStream(), "UTF-8"));
                }
            }
            logger.info(String.format("\n=====加载配置文件开始=====\n%s=====加载配置文件完成=====\n", filePaths.toString()));
        }catch(IOException e){
            logger.error(e.getMessage(), e);
        }
	}
	
	public static String get(String key){
		return get(key, StringUtils.EMPTY);
	}
	
	public static String get(String key,String defaultValue){
		return properties.getProperty(key, defaultValue);
	}

}
