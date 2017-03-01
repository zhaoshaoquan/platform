package com.stone.dao.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.stone.dao.hibernate.AtomikosJtaPlatform;

@Configuration
public class TransactionConfig implements BeanFactoryPostProcessor, Ordered {
	private static final Logger log = LoggerFactory.getLogger(TransactionConfig.class);
	private DataSource dataSource;

	@Override
	public int getOrder() {
		return 6;
	}
	
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException{
		dataSource = (DataSource)beanFactory.getSingleton("dataSource");
		if(dataSource != null){
			buildJpaTransactionManager(beanFactory);
		}
	}
	
	public void buildJpaTransactionManager(ConfigurableListableBeanFactory beanFactory){
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(beanFactory.getBean(EntityManagerFactory.class));
		beanFactory.registerSingleton("transactionManager", transactionManager);
	}
	
	public void buildJtaTransactionManager(ConfigurableListableBeanFactory beanFactory){
		try{
			UserTransactionManager atomikosTransactionManager = new UserTransactionManager();
			atomikosTransactionManager.setForceShutdown(true);
			atomikosTransactionManager.init();
			beanFactory.registerSingleton("atomikosTransactionManager", atomikosTransactionManager);

			UserTransactionImp atomikosUserTransaction = new UserTransactionImp();
			atomikosUserTransaction.setTransactionTimeout(3600);
			JtaTransactionManager transactionManager = new JtaTransactionManager();
			transactionManager.setAllowCustomIsolationLevels(true);
			
			transactionManager.setUserTransaction(atomikosUserTransaction);
			transactionManager.setTransactionManager(atomikosTransactionManager);
			beanFactory.registerSingleton("transactionManager", transactionManager);

			AtomikosJtaPlatform atomikosJtaPlatform = new AtomikosJtaPlatform();
			AtomikosJtaPlatform.setTransactionManager(atomikosTransactionManager);
			AtomikosJtaPlatform.setUserTransaction(atomikosUserTransaction);
			beanFactory.registerSingleton("atomikosJtaPlatform", atomikosJtaPlatform);
		}catch(Exception e){
			log.error("JTA事务初始化失败");
			log.error(e.getLocalizedMessage(), e);
			throw new RuntimeException(e);
		}
	}

}
