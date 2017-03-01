package com.stone.commons.jqgrid;

import java.util.ArrayList;
import java.util.List;

public class JsonReader<T> {

	/**
	 * json中代表实际模型数据的入口
	 */
	protected List<T> rows = new ArrayList<T>();
	/**
	 * json中代表当前页码的数据
	 */
	protected long page;
	/**
	 * json中代表页码总数的数据
	 */
	protected long total;
	/**
	 * json中代表数据行总数的数据
	 */
	protected long records;
	/**
	 * 用户数据
	 */
	protected String userdata;

	public List<T> getRows() {
		return rows;
	}

	public JsonReader<T> setRows(List<T> rows) {
		this.rows = rows;
		return this;
	}
	
	public JsonReader<T> addRows(T rowItem){
		this.rows.add(rowItem);
		return this;
	}

	public long getPage() {
		return page;
	}

	public JsonReader<T> setPage(long page) {
		this.page = page;
		return this;
	}

	public long getTotal() {
		return total;
	}

	public JsonReader<T> setTotal(long total) {
		this.total = total;
		return this;
	}

	public long getRecords() {
		return records;
	}

	public JsonReader<T> setRecords(long records) {
		this.records = records;
		return this;
	}

	public String getUserdata() {
		return userdata;
	}

	public JsonReader<T> setUserdata(String userdata) {
		this.userdata = userdata;
		return this;
	}

}
