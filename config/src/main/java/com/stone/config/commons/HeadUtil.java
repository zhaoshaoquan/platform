package com.stone.config.commons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.stone.config.HeadItem;

/**
 * exthead.properties文件工具类
 */
public class HeadUtil {
	public static final List<HeadItem> heads = new ArrayList<HeadItem>();
	public static final ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
	
	static{
		try{
			buildConfigInfo();
		}catch (Exception e){
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 初始化exthead配置信息
	 */
	private static void buildConfigInfo()throws Exception{
		Resource[] resources = resourcePatternResolver.getResources("classpath*:exthead.properties");
		for(Resource resource : resources){
			Properties prop = new Properties();
			prop.load(resource.getInputStream());
			HeadItem configItem = new HeadItem();
			
			String headHtml = prop.getProperty("head-html", StringUtils.EMPTY);
			if(StringUtils.isNotBlank(headHtml))configItem.setHeadHtml(headHtml);
			
			String systemExtPath = prop.getProperty("system-ext-path", StringUtils.EMPTY);
			if(StringUtils.isNotBlank(systemExtPath))configItem.setSystemExtPath(systemExtPath);
			
			String nofilter = prop.getProperty("nofilter", StringUtils.EMPTY);
			if(StringUtils.isNotBlank(nofilter))configItem.setNofilter(nofilter);
			
			String systemName = prop.getProperty("system-name", StringUtils.EMPTY);
			if(StringUtils.isNotBlank(systemName))configItem.setSystemName(systemName.toString());
			
			String headOrder = prop.getProperty("head-order", "0");
			if(StringUtils.isNotBlank(headOrder))configItem.setHeadOrder(Integer.parseInt(headOrder));
			heads.add(configItem);
		}
		Collections.sort(heads, new HeadComparator());
	}
	
}
