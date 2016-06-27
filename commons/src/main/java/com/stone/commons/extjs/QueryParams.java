package com.stone.commons.extjs;

import com.stone.commons.page.Page;

import java.util.HashMap;
import java.util.Map;

public class QueryParams {
	/**
	 * 开始页数
	 */
	protected int start;
	/**
	 * 一页的记录数
	 */
	protected int limit;
	/**
	 * 查询的页数
	 */
	protected int page;
	/**
	 * 排序字段
	 */
	protected String sort;
	/**
	 * 排序规则
	 */
	protected String dir;
	/**
	 * 多条件过滤
	 */
	protected String filters;
	/**
	 * 其它参数信息
	 */
	protected Map<String,Object> params = new HashMap<String,Object>();

    /**
     * 获取当前的pqge对象
     */
    public <T> Page<T> pageBean(){
        return new Page<>(start, limit, params);
    }

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

}
