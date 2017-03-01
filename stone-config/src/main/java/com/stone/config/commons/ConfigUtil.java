package com.stone.config.commons;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.stone.config.ConfigItem;

/**
 * config.properties文件工具类
 */
public class ConfigUtil {
	public static final String HEAD_ORDER = "head-order";
	public static final String SYSTEM_EXT_PATH = "system-ext-path";
	public static final String HEAD_HTML = "head-html";
	public static final String NOFILTER = "nofilter";
	public static final String SYSTEM_NAME = "system-name";
	public static File config = new File(new File(System.getProperties().getProperty("user.dir")), "config" + File.separator + "platform.properties");
	public static ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
	public static List<ConfigItem> configList = new ArrayList<ConfigItem>();
	
	static{
		try {
			buildConfigInfo();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 初始化config配置信息
	 */
	private static void buildConfigInfo() throws Exception {
		Resource[] platformResources = resourcePatternResolver.getResources("classpath*:config.properties");
		for (int i = 0; i < platformResources.length; i++) {
			Properties prop=new Properties();
			prop.load(platformResources[i].getInputStream());
			Object systemExtPath = prop.get(SYSTEM_EXT_PATH);
			Object headHtml = prop.get(HEAD_HTML);
			Object headOrder = prop.get(HEAD_ORDER);
			Object nofilter = prop.get(NOFILTER);
			Object systemName = prop.get(SYSTEM_NAME);
			ConfigItem configItem = new ConfigItem();
			if(headHtml!=null){
				configItem.setHeadHtml(headHtml.toString());
			}
			if(systemExtPath!=null){
				configItem.setSystemExtPath(systemExtPath.toString());
			}
			if(nofilter!=null){
				configItem.setNofilter(nofilter.toString());
			}
			if(systemName!=null){
				configItem.setSystemName(systemName.toString());
			}
			if(headOrder!=null){
				configItem.setHeadOrder(Integer.parseInt(headOrder.toString()));
			}
			configList.add(configItem);
		}
		Collections.sort(configList, new ConfigComparator());
	}
	
	

	/**
	 * 读取config配置信息
	 */
	public static String getConfigInfo(String key) throws Exception {
		String info = "";
		Properties props = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(config);
			props.load(fis);
			info = props.getProperty(key);
		} catch (Exception e) {
			System.out.println("警告:" + e.getMessage());
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		return info;
	}
	/**
	 * 读取config配置信息
	 */
	public static String getConfigInfo(String key,String defalutValue) throws Exception {
		String info = getConfigInfo(key);
		if(StringUtils.isEmpty(info)){
			return defalutValue;
		}
		return info;
	}
	/**
	 * 更新config配置信息
	 */
	public static void updateConfigInfo(String key, String value) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		PropertiesConfiguration props = new PropertiesConfiguration(config);
		try{
			props.setProperty(key, value);
			props.setHeader(String.format("Copyright (c) http://www.huanya-xt.com\nUpdate Time %s", sdf.format(new Date(System.currentTimeMillis()))));
			props.save();
		}catch (Exception e){
			System.out.println("警告:" + e.getMessage());
		}
	}
}
