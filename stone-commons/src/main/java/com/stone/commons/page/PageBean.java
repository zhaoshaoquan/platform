package com.stone.commons.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页对象
 */
public class PageBean<T> implements Serializable{

	private static final long serialVersionUID = 7679460027367239482L;

	/** 默认每页显示记录 */
	public static final int PAGE_ROWS = 10;

	/**
	 * 当前页数
	 */
	protected int currentPage = 1;

	/**
	 * 满足条件总记录数
	 */
	protected int totalRows;

	/**
	 * 每页显示记录
	 */
	protected int rowsPerPage = PageBean.PAGE_ROWS;

	/**
	 * 查询出来的数据集
	 */
	protected List<T> result = new ArrayList<T>();

	/**
	 * 条件集合
	 */
	protected List<Condition> conditions = new ArrayList<Condition>();

	/**
	 * 排序集合
	 */
	protected List<Order> orders = new ArrayList<Order>();

	/**
	 * 参数集合
	 */
	private Map<String, Object> params = new HashMap<String, Object>();

	public PageBean(){

	}

	public PageBean(int currentPage, int totalRows, int rowsPerPage){
		this(currentPage, totalRows, rowsPerPage, new ArrayList<T>());
	}

	public PageBean(int currentPage, int totalRows, int rowsPerPage, List<T> result){
		setCurrentPage(currentPage);
		this.totalRows = totalRows;
		setRowsPerPage(rowsPerPage);
		this.result = result;
	}

	public int getCurrentPage(){
		return currentPage;
	}

	public void setCurrentPage(int currentPage){
		if(currentPage < 1) {
			currentPage = 1;
		}
		this.currentPage = currentPage;
	}

	public int getTotalRows(){
		return totalRows;
	}

	public void setTotalRows(int totalRows){
		this.totalRows = totalRows;
	}

	public int getRowsPerPage(){
		return rowsPerPage;
	}

	public void setRowsPerPage(int rowsPerPage){
		if(rowsPerPage <= 0) {
			rowsPerPage = PageBean.PAGE_ROWS;
		}
		this.rowsPerPage = rowsPerPage;
	}

	public List<T> getResult(){
		return result;
	}

	public void setResult(List<T> result){
		this.result.clear();
		this.result.addAll(result);
	}

	public int getTotalPages(){
		return (totalRows + rowsPerPage - 1) / rowsPerPage;
	}

	public List<Condition> getConditions(){
		return conditions;
	}

	public void setConditions(List<Condition> conditions){
		this.conditions.clear();
		this.conditions.addAll(conditions);
	}

	public PageBean<T> addCondition(Condition condition){
		this.conditions.add(condition);
		return this;
	}

	public List<Order> getOrders(){
		return orders;
	}

	public void setOrders(List<Order> orders){
		this.orders.clear();
		this.orders.addAll(orders);
	}

	public PageBean<T> addOrder(Order order){
		this.orders.add(order);
		return this;
	}

	public Map<String, Object> getParams(){
		return params;
	}

	public void setParams(Map<String, Object> params){
		this.params = params;
	}

	public PageBean<T> addParams(String key, Object value){
		this.params.put(key, value);
		return this;
	}

	public void setPropPrefix(String propPrefix){
		for(Order order : getOrders()){
			String name = order.getPropertyName();
			if(!name.contains(".")) {
				order.setPropertyName(propPrefix + name);
			}
		}
		for(Condition condition : getConditions()){
			String name = condition.getPropertyName();
			if(!name.contains(".")) {
				condition.setPropertyName(propPrefix + name);
			}
		}
	}

	public void setMustPropPrefix(String propPrefix){
		for(Order order : getOrders()){
			String name = order.getPropertyName();
			order.setPropertyName(propPrefix + name);
		}
		for(Condition condition : getConditions()){
			String name = condition.getPropertyName();
			condition.setPropertyName(propPrefix + name);
		}
	}

}
