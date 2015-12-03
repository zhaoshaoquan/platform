package com.stone.commons.page;

/**
 * QL排序类型
 */
public enum OrderType{
	ASC("ASC"), DESC("DESC");

	private String desc;

	private OrderType(String desc) {
		this.desc = desc;
	}

	public String toString() {
		return desc;
	}
}
