package com.stone.commons.jqgrid;

/**
 * 查询参数
 */
public class QueryParams {
	
	/**
	 * 表示请求页码
	 */
	protected int page;
	/**
	 * 表示请求行数
	 */
	protected int rows;
	/**
	 * 表示用于排序的列名
	 */
	protected String sidx; 
	/**
	 * 表示采用的排序方式
	 */
	protected String sord;
	protected String nd;
	/**
	 * 表示是否是搜索请求
	 */
	protected String _search;
	/**
	 * 搜索字段
	 */
	protected String searchField;
	/**
	 * 字段操作
	 */
	protected String searchOper;
	/**
	 * 字段值
	 */
	protected String searchString;
	/**
	 * 字段类型
	 */
	protected String fieldType;
	/**
	 * 多条件过滤
	 */
	protected String filters;

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getNd() {
		return nd;
	}

	public void setNd(String nd) {
		this.nd = nd;
	}

	public String get_search() {
		return _search;
	}

	public void set_search(String _search) {
		this._search = _search;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchOper() {
		return searchOper;
	}

	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}

}
