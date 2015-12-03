package com.stone.commons.page;
/**
 * 各种条件操作类型
 */
public enum Operation {
	/**属于*/
	IN(" IN "),
	/**等于*/
	EQ(" = "),
	/**大于*/
	GT(" > "),
	/**小于*/
	LT(" < "),
	/**不等于*/
	NE(" != "),
	/**大于等于*/
	GE(" >= "),
	/**小于等于*/
	LE(" <= "),
	/**两者之间*/
	BETWEEN(" BETWEEN "),
	/**包含*/
	CN(" LIKE "),
	/**开始于*/
	BW(" LIKE "),
	/**结束于*/
	EW(" LIKE "),
	/**不开始于*/
	BN(" NOT LIKE "),
	/**不属于*/
	NI(" NOT IN "),
	/**不包含*/
	NC(" NOT LIKE "),
	/**不结束于*/
	EN(" NOT LIKE "),
	/**空值*/
	NU(" IS NULL "),
	/**非空值*/
	NN(" IS NOT NULL ");
	
	private String desc;
	
	private Operation(String desc){
		this.desc = desc;
	}
	
	public String toString() {
		return desc;
	}
}
