package com.stone.commons.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Page<T> implements Serializable, Iterable<T> {
	private static final long serialVersionUID = 7701386586632916397L;
	/**当前页数*/
	private  int page;
	/**每页显示的行数*/
	private  int size;
	/**记录总数*/
	private long total;
	/**查询的数据集合*/
	private List<T> result = new ArrayList<T>();
	/**查询参数*/
	private Map<String, Object> params = new HashMap<String, Object>();
	
	/**默认当前页数*/
	public static final int DEFAULT_CURRENT_PAGE = 1;
	/**默认每页显示行数*/
	public static final int DEFAULT_PAGE_SIZE = 25;
	
	public Page() {
		this(DEFAULT_CURRENT_PAGE, DEFAULT_PAGE_SIZE, null);
	}
	
	public Page(int page, int size) {
		this(page, size, null);
	}
	
	public Page(int page, int size, Map<String, Object> params) {
		this.page = page > 0 ? page : DEFAULT_CURRENT_PAGE;
		this.size = size > 0 ? size : DEFAULT_PAGE_SIZE;
		if(params != null){
			this.params.putAll(params);
		}
	}

	public long getTotal(){
		return total;
	}

	public void setTotal(long total){
		this.total = total;
	}

	public List<T> getResult(){
		return result;
	}

	@SuppressWarnings("unchecked")
	public void setResult(List<?> result){
		this.result.clear();
		this.result.addAll((Collection<? extends T>)result);
	}

	public Map<String, Object> getP(){
		return params;
	}
	
	public Map<String, Object> getParams(){
		return params;
	}

	public void setParams(Map<String, Object> params){
		this.params.clear();
		this.params.putAll(params);
	}

	public Page<T> addParams(Map<String, Object> params){
		this.params.putAll(params);
		return this;
	}
	
	public Page<T> addParams(String key, Object value){
		this.params.put(key, value);
		return this;
	}
	
	public int getPageNumber() {
		return this.page;
	}

	public void setPageNumber(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return this.size;
	}

	public void setPageSize(int size) {
		 this.size = size;
	}

	public int getTotalPage() {
		return (int)Math.ceil((double)total / (double)getPageSize());
	}

	public long getStartRow() {
		return (page-1) * size;
	}
	
	public long getEndRow() {
		return getStartRow() + size > total ? total : getStartRow() + size;
	}
	
	public boolean hasPreviousPage() {
		return getPageNumber() > 0;
	}

	public boolean isFirstPage() {
		return !hasPreviousPage();
	}

	public boolean hasNextPage() {
		return ((page + 1) * size) < total;
	}

	public boolean isLastPage() {
		return !hasNextPage();
	}

	@Override
	public Iterator<T> iterator() {
		return result.iterator();
	}

	@Override
	public String toString() {
		String contentType = "UNKNOWN";
		if(result.size() > 0){
			contentType = result.get(0).getClass().getName();
		}
		return String.format("Page %s of %d containing %s instances", getPageNumber(), getTotalPage(), contentType);
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj){
			return true;
		}
		if(!(obj instanceof Page<?>)){
			return false;
		}
		Page<?> that = (Page<?>)obj;
		boolean pageEqual = this.page == that.page;
		boolean sizeEqual = this.size == that.size;
		boolean totalEqual = this.total == that.total;
		boolean resultEqual = this.result.equals(that.result);
		boolean paramsEqual = this.params.equals(that.params);
		return pageEqual && sizeEqual && totalEqual && resultEqual && paramsEqual;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + page;
		result = 31 * result + size;
		result = 31 * result + (int)(total ^ total >>> 32);
		result = 31 * result + this.result.hashCode();
		result = 31 * result + params.hashCode();
		return result;
	}
	
}
