package com.ys.util.basedao;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class BaseTransaction {

	DataSourceTransactionManager transactionManager = null;
	DefaultTransactionDefinition def = null;
	TransactionStatus status = null;
	String name = "";
	
	public BaseTransaction() {
		
	}
	
	public BaseTransaction(String name) {
		this.name = name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void begin() throws Exception {

		transactionManager = BaseDAO.getTransaction(name);
		def = new DefaultTransactionDefinition(); 
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED); 
		status = transactionManager.getTransaction(def);
		//System.out.println(status);
		//System.out.println(status.isNewTransaction());
	}
	
	public void commit() throws Exception {
		transactionManager.commit(status);
	}
	
	public void rollback() throws Exception {
		transactionManager.rollback(status);
	}
}
