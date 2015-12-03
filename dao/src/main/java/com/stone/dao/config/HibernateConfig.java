package com.stone.dao.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

@Configuration
public class HibernateConfig implements BeanFactoryPostProcessor, Ordered {
	private static final Logger log = LoggerFactory.getLogger(HibernateConfig.class);
	
	@Override
	public int getOrder(){
		return 4;
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException{
		DataSource dataSource = (DataSource)beanFactory.getSingleton("dataSource");
		if(dataSource != null){
			LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();//hibernate4
			sessionFactoryBean.setDataSource(dataSource);
			sessionFactoryBean.setPackagesToScan(new String[]{"com.stone.**.domain","org.**.domain"});
			sessionFactoryBean.setHibernateProperties(hibernateProperties());
			beanFactory.registerSingleton("sessionFactoryBean", sessionFactoryBean);
		}
	}
	
	public static Properties hibernateProperties(){
		Properties properties = new Properties();
		ClassPathResource cpr = new ClassPathResource("hibernate.properties");
		try{
			properties.load(cpr.getInputStream());
		}catch(IOException e){
			if(log.isErrorEnabled()){
				log.error("Hibernate配置文件加载失败");
				log.error(e.getLocalizedMessage(), e);
			}
		}
		return properties;
	}

}
