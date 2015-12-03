package com.stone.commons.page;

/**
 * QL条件关系
 */
public enum RelateType{

	AND(" AND "), OR(" OR ");

	private String desc;

	private RelateType(String desc){
		this.desc = desc;
	}

	public String toString(){
		return desc;
	}
}
