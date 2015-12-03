package com.stone.dao.config;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class JdbcConfig implements BeanFactoryPostProcessor,Ordered {
	
	@Override
	public int getOrder() {
		return 3;
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException{
		DataSource dataSource = (DataSource)beanFactory.getSingleton("dataSource");
		if(dataSource!=null){
			beanFactory.registerSingleton("jdbcTemplate", new JdbcTemplate(dataSource));
		}
	} 

}
