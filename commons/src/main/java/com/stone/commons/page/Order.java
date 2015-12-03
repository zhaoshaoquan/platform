package com.stone.commons.page;

import java.io.Serializable;

/**
 * QL排序属性信息
 */
public class Order implements Serializable{
	private static final long serialVersionUID = -3287045670673865120L;
	/** 属性名 */
	private String propertyName;
	/** 排序类型 */
	private OrderType orderType;

	public Order(){

	}

	public Order(String propertyName, OrderType orderType){
		this.propertyName = propertyName;
		this.orderType = orderType;
	}

	public String getPropertyName(){
		return propertyName;
	}

	public void setPropertyName(String propertyName){
		this.propertyName = propertyName;
	}

	public OrderType getOrderType(){
		return orderType;
	}

	public void setOrderType(OrderType orderType){
		this.orderType = orderType;
	}

}