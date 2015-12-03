package com.stone.dao.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.stone.dao.mybatis.PagePlugin;


@Configuration
public class MyBatisConfig implements BeanFactoryPostProcessor, Ordered {
	private static final Logger log = LoggerFactory.getLogger(MyBatisConfig.class);
	private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
	
	@Override
	public int getOrder(){
		return 5;
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException{
		DataSource dataSource = (DataSource)beanFactory.getSingleton("dataSource");
		if(dataSource != null){
			try{
				SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
				sqlSessionFactory.setDataSource(dataSource);
				sqlSessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
				sqlSessionFactory.setMapperLocations(resourcePatternResolver.getResources("classpath*:mapper/**/*-mapper.xml"));
				PagePlugin pagePlugin = new PagePlugin();
				pagePlugin.setProperties(myBatisProperties());
				Interceptor[] plugins = {pagePlugin};
				sqlSessionFactory.setPlugins(plugins);
				sqlSessionFactory.afterPropertiesSet();
				beanFactory.registerSingleton("sqlSessionFactory", sqlSessionFactory);
			}catch(Exception e){
				if(log.isErrorEnabled()){
					log.error("MyBatis初始化失败");
					log.error(e.getLocalizedMessage(), e);
				}
			}
		}
	}
	
	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer(){
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setBasePackage("com.stone.**.mapper");
		return mapperScannerConfigurer;
	}
	
	public static Properties myBatisProperties(){
		Properties properties = new Properties();
		ClassPathResource cpr = new ClassPathResource("mybatis.properties");
		try{
			properties.load(cpr.getInputStream());
		}catch(IOException e){
			if(log.isErrorEnabled()){
				log.error("MyBatis配置文件加载失败");
				log.error(e.getLocalizedMessage(), e);
			}
		}
		return properties;
	}

}
