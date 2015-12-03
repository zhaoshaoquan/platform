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
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;


@Configuration
public class JpaConfig implements BeanFactoryPostProcessor, Ordered {
	private static final Logger log = LoggerFactory.getLogger(JpaConfig.class);
	
	@Override
	public int getOrder(){
		return 2;
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException{
		DataSource dataSource = (DataSource)beanFactory.getSingleton("dataSource");
		if(dataSource != null){
			Properties jpaProperties = jpaProperties();
			HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
			jpaVendorAdapter.setShowSql(true);
			jpaVendorAdapter.setGenerateDdl(true);
			jpaVendorAdapter.setDatabasePlatform(jpaProperties.getProperty("hibernate.dialect"));
			LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
			entityManagerFactory.setDataSource(dataSource);
			entityManagerFactory.setPersistenceUnitName("entityManager");
			entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
			entityManagerFactory.setJpaProperties(jpaProperties);
			entityManagerFactory.setPackagesToScan(new String[]{"com.stone.**.domain","org.**.domain"});
			entityManagerFactory.afterPropertiesSet();
			beanFactory.registerSingleton("entityManagerFactory", entityManagerFactory);
		}
	}
	
	public static Properties jpaProperties(){
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
