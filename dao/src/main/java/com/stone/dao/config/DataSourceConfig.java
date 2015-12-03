package com.stone.dao.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.ClassPathResource;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
public class DataSourceConfig implements BeanFactoryPostProcessor, Ordered {
	private static final Logger log = LoggerFactory.getLogger(DataSourceConfig.class);

	@Override
	public int getOrder(){
		return 1;
	}
	
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException{
		//DriverManagerDataSource dataSource = new DriverManagerDataSource();
		DruidDataSource dataSource = new DruidDataSource();
		BeanWrapper beanWrapper = new BeanWrapperImpl(dataSource);
		beanWrapper.setPropertyValues(dataSourceProperties());
		dataSource.setName(StringUtils.isEmpty(dataSource.getName()) ? "water" : dataSource.getName());
		beanFactory.registerSingleton("dataSource", dataSource);
	}

	public static Map<String, String> dataSourceProperties(){
		Map<String, String> configMap = new HashMap<>();
		ClassPathResource cpr = new ClassPathResource("datasource.xml");
		SAXReader reader = new SAXReader();
		Document dom = null;
		try{
			dom = reader.read(cpr.getInputStream());
		}catch(DocumentException e1){
			if(log.isErrorEnabled()){
				log.error("datasource.xml解析异常");
				log.error(e1.getLocalizedMessage(), e1);
			}
		}catch(IOException e2){
			if(log.isErrorEnabled()){
				log.error("datasource.xml读取异常");
				log.error(e2.getLocalizedMessage(), e2);
			}
		}
		Element root = dom.getRootElement();
		@SuppressWarnings("unchecked")
		Iterator<Element> it = root.elementIterator();
		while(it.hasNext()){
			Element e = it.next();
			Attribute name = e.attribute("name");
			Attribute value = e.attribute("value");
			if(name!=null && StringUtils.isNotEmpty(name.getValue())){
				configMap.put(name.getValue(), (value!=null ? value.getValue() : null));
			}
		}
		return configMap;
	}

}
