package com.stone.commons.page;

import java.io.Serializable;

/**
 * 条件操作类
 */
public class Condition implements Serializable {
	private static final long serialVersionUID = -3096986479371347838L;
	/** 条件关系 */
	protected RelateType relateType;
	/** 是否有前括号 */
	protected boolean isPrefixBrackets = false;
	/** 是否有后括号 */
	protected boolean isSuffixBrackets = false;
	/** 属性名 */
	protected String propertyName;
	/** 属性值 */
	protected Object propertyValue;
	/** 操作符 */
	protected Operation operation;
	/** 属性类型 */
	protected String propertyType;
	/**组前缀括号*/
	protected String groupPrefixBrackets ="";

	public Condition() {}

	public Condition(String propertyName, Object propertyValue, Operation operation) {
		this(RelateType.AND, propertyName, propertyValue, operation);
	}

	public Condition(String propertyName, Operation operation) {
		this(RelateType.AND, propertyName, null, operation);
	}
	
	public Condition(String propertyName, Operation operation,boolean isSuffixBrackets) {
		this(RelateType.AND, propertyName, null, operation,isSuffixBrackets);
	}

	public Condition(RelateType relateType, String propertyName, Object propertyValue, Operation operation) {
		this.relateType = relateType;
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
		this.operation = operation;
	}
	
	public Condition(boolean isPrefixBrackets,RelateType relateType, String propertyName, Object propertyValue, Operation operation) {
		this.relateType = relateType;
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
		this.operation = operation;
		this.isPrefixBrackets = isPrefixBrackets;
	}

	public Condition(RelateType relateType, String propertyName, Object propertyValue, Operation operation,boolean isSuffixBrackets) {
		this.relateType = relateType;
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
		this.operation = operation;
		this.isSuffixBrackets = isSuffixBrackets;
	}
	
	public Condition(RelateType relateType, String propertyName, Operation operation,boolean isSuffixBrackets) {
		this(relateType, propertyName, null, operation,isSuffixBrackets);
	}
	
	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Object getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(Object propertyValue) {
		this.propertyValue = propertyValue;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public RelateType getRelateType() {
		return relateType;
	}

	public void setRelateType(RelateType relateType) {
		this.relateType = relateType;
	}

	public boolean isPrefixBrackets() {
		return isPrefixBrackets;
	}

	public void setPrefixBrackets(boolean isPrefixBrackets) {
		this.isPrefixBrackets = isPrefixBrackets;
	}

	public boolean isSuffixBrackets() {
		return isSuffixBrackets;
	}

	public void setSuffixBrackets(boolean isSuffixBrackets) {
		this.isSuffixBrackets = isSuffixBrackets;
	}

	public void setGroupPrefixBrackets(String groupPrefixBrackets) {
		this.groupPrefixBrackets = groupPrefixBrackets;
	}

	public String getGroupPrefixBrackets() {
		return groupPrefixBrackets;
	}

}
