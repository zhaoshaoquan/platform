package com.stone.config.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
	 * 设置Freemarker静态模块类
	 * @return FreemarkerStaticModels
	 * @throws Exception
	 */
	// @Bean
	// public FreemarkerStaticModels freemarkerStaticModels() throws Exception{
	// FreemarkerStaticModels freemarkerStaticModels =
	// FreemarkerStaticModels.getInstance();
	// Resource[] resources =
	// resourcePatternResolver.getResources("classpath*:freemarkerstatic.properties");
	// PropertiesFactoryBean propertiesFactoryBean = new
	// PropertiesFactoryBean();
	// propertiesFactoryBean.setLocations(resources);
	// freemarkerStaticModels.setStaticModels(propertiesFactoryBean.createProperties());
	// return freemarkerStaticModels;
	// }

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
//		Set<String> extpathSet = new HashSet<String>();
//		List<String> headset = new ArrayList<String>();
		/*for(ConfigItem item : ConfigUtil.configList){
			String system_ext_path = item.getSystemExtPath();
			if(system_ext_path != null) {
				String[] system_ext_paths = system_ext_path.split(",");
				for(String path : system_ext_paths){
					if(StringUtils.isNotBlank(path)) {
						extpathSet.add(path);
					}
				}
			}
			String headHtml = item.getHeadHtml();
			if(headHtml != null) {
				String[] head_htmls = headHtml.toString().split(",");
				for(int i = 0; i < head_htmls.length; i++){
					headset.add(head_htmls[i]);
				}
			}
		}*/
//		variables.put("sys_ext_paths", extpathSet);
//		variables.put("head_htmls", headset);
//		variables.putAll(freemarkerStaticModels());
		
		//加载所有Freemarker标签
		ScanPackageClasses spc = new ScanPackageClasses(FreemarkerTag.class);
		Set<Class<?>> tagClassSet = spc.doScan("com.stone");
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
