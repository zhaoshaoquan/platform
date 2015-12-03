package com.stone.config.config;

import java.net.URL;

import freemarker.cache.URLTemplateLoader;

/**
 * 平台模板加载器
 */
public class PlatformTemplateLoader extends URLTemplateLoader {

	@Override
	protected URL getURL(String name){
		System.out.println("PlatformTemplateLoader:" + name);
		return null;
	}

}
