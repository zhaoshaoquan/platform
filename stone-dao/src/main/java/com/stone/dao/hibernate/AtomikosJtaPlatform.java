package com.stone.dao.hibernate;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.hibernate.engine.transaction.jta.platform.internal.AbstractJtaPlatform;

public class AtomikosJtaPlatform extends AbstractJtaPlatform{
	private static final long serialVersionUID = 4896569440655581002L;
	private static TransactionManager transactionManager;
	private static UserTransaction userTransaction;

	public AtomikosJtaPlatform(){
	}

	@Override
	protected TransactionManager locateTransactionManager(){
		return AtomikosJtaPlatform.transactionManager;
	}

	@Override
	protected UserTransaction locateUserTransaction(){
		return AtomikosJtaPlatform.userTransaction;
	}

	public static void setTransactionManager(TransactionManager transactionManager){
		AtomikosJtaPlatform.transactionManager = transactionManager;
	}

	public static void setUserTransaction(UserTransaction userTransaction){
		AtomikosJtaPlatform.userTransaction = userTransaction;
	}

}
