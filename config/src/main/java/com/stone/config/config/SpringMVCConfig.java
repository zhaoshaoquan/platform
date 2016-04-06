package com.stone.config.config;

import static com.stone.commons.GlobalConfig.get;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.stone.commons.ScanPackageClasses;
import com.stone.commons.annotation.FreemarkerTag;
import com.stone.config.HeadItem;
import com.stone.config.commons.HeadUtil;
import com.stone.config.commons.SpringUtil;

@Configuration
public class SpringMVCConfig implements ApplicationContextAware{

	protected DefaultListableBeanFactory beanFactory;
	private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

	public SpringMVCConfig(){
		
	}

	@Bean
	public FreeMarkerViewResolver freeMarkerViewResolver(){
		FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();
		freeMarkerViewResolver.setViewClass(FreeMarkerView.class);
		freeMarkerViewResolver.setPrefix("");
		freeMarkerViewResolver.setSuffix(".html");
		freeMarkerViewResolver.setCache(false);
		freeMarkerViewResolver.setOrder(1);
		freeMarkerViewResolver.setContentType("text/html;charset=UTF-8");
		freeMarkerViewResolver.setExposeSessionAttributes(true);
		freeMarkerViewResolver.setExposeRequestAttributes(true);
		freeMarkerViewResolver.setRequestContextAttribute("request");
		return freeMarkerViewResolver;
	}

	@Bean
	public InternalResourceViewResolver internalResourceViewResolver(){
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setViewClass(InternalResourceView.class);
		internalResourceViewResolver.setPrefix("");
		internalResourceViewResolver.setSuffix(".jsp");
		internalResourceViewResolver.setCache(false);
		internalResourceViewResolver.setOrder(2);
		internalResourceViewResolver.setContentType("text/html;charset=UTF-8");
		internalResourceViewResolver.setRequestContextAttribute("request");
		return internalResourceViewResolver;
	}

	/**
	 * 初始化Freemarker资源信息
	 * @return FreeMarkerConfigurer
	 * @throws Exception
	 */
	@Bean
	public FreeMarkerConfigurer freemarkerConfigurer() throws Exception{
		FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
		freeMarkerConfigurer.setDefaultEncoding("UTF-8");
		Resource[] resources = resourcePatternResolver.getResources("classpath*:platform");
		String[] templateLoaderPaths = new String[resources.length];
		for(int i = 0; i < resources.length; i++){
			templateLoaderPaths[i] = resources[i].getURI().toString();
		}
		freeMarkerConfigurer.setTemplateLoaderPaths(templateLoaderPaths);
		freeMarkerConfigurer.setFreemarkerVariables(buildFreemarkerVariables());
		return freeMarkerConfigurer;
	}

	protected Map<String, Object> buildFreemarkerVariables() throws Exception{
		Map<String, Object> variables = new HashMap<String, Object>();
		Set<String> extPathSet = new HashSet<String>();
		List<String> headHtmlList = new ArrayList<String>();
		for(HeadItem head : HeadUtil.heads){
			String extPaths = head.getSystemExtPath();
			if(StringUtils.isNotBlank(extPaths)){
				for(String path : extPaths.split(",")){
					if(StringUtils.isNotBlank(path))extPathSet.add(path);
				}
			}
			String headHtmls = head.getHeadHtml();
			if(StringUtils.isNotBlank(headHtmls)){
				for(String headhtml : headHtmls.split(",")){
					if(StringUtils.isNotBlank(headhtml))headHtmlList.add(headhtml);
				}
			}
		}
		variables.put("head_htmls", headHtmlList);
		variables.put("sys_ext_paths", extPathSet);
		
		//加载所有Freemarker标签
		ScanPackageClasses spc = new ScanPackageClasses(FreemarkerTag.class);
		Set<Class<?>> tagClassSet = spc.doScan(get("freemarker.tag.base.package", "com"));
		for(Class<?> clazz : tagClassSet){
			FreemarkerTag ft = clazz.getAnnotation(FreemarkerTag.class);
			variables.put(ft.name(), clazz.newInstance());
		}
		return variables;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException{
		SpringUtil.applicationContext = applicationContext;
	}

}
