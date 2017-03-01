package com.stone.commons.jqgrid;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 前台页面表单数据
 */
public class FormData {

	private String className;
	private Map<String, Object> fieldMap = new HashMap<String, Object>();
	
	private OperType oper;
	private Locale locale;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}


	public OperType getOper() {
		return oper;
	}

	public void setOper(OperType oper) {
		this.oper = oper;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public Map<String, Object> getFieldMap() {
		return fieldMap;
	}

	public FormData put(String key,String value){
		fieldMap.put(key, value);
		return this;
	}
	
	public Object get(String key){
		return fieldMap.get(key);
	}
	
	public void remove(String key){
		fieldMap.remove(key);
	}
}
