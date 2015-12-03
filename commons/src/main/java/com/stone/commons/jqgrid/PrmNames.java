package com.stone.commons.jqgrid;

/**
 * prmNames是jqGrid的一个重要选项，用于设置jqGrid将要向Server传递的参数名称
 */
public class PrmNames {

	/**
	 * the requested page (default value page)
	 */
	private String page = "page";
	/**
	 * the number of rows requested (default value rows)
	 */
	private String rows = "rows";
	/**
	 * the sorting column (default value sidx)
	 */
	private String sort = "sidx";
	/**
	 * the sort order (default value sord)
	 */
	private String order = "sord";
	/**
	 * the search indicator (default value _search)
	 */
	private String search = "_search";
	/**
	 * the time passed to the request (for IE browsers not to cache the request)
	 * (default value nd)
	 */
	private String nd = "nd";
	/**
	 * the name of the id when POST-ing data in editing modules (default value
	 * id)
	 */
	private String id = "id";
	/**
	 * the operation parameter (default value oper)
	 */
	private String oper = "oper";
	/**
	 * the name of operation when the data is POST-ed in edit mode (default
	 * value edit)
	 */
	private String editoper = "edit";
	/**
	 * the name of operation when the data is posted in add mode (default value
	 * add)
	 */
	private String addoper = "add";
	/**
	 * the name of operation when the data is posted in delete mode (default
	 * value del)
	 */
	private String deloper = "del";
	/**
	 * the number of the total rows to be obtained from server - see rowTotal
	 * (default value totalrows)
	 */
	private String totalrows = "totalrows";
	/**
	 * the name passed when we click to load data in the subgrid (default value
	 * id)
	 */
	private String subgridid = "id";

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getNd() {
		return nd;
	}

	public void setNd(String nd) {
		this.nd = nd;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public String getEditoper() {
		return editoper;
	}

	public void setEditoper(String editoper) {
		this.editoper = editoper;
	}

	public String getAddoper() {
		return addoper;
	}

	public void setAddoper(String addoper) {
		this.addoper = addoper;
	}

	public String getDeloper() {
		return deloper;
	}

	public void setDeloper(String deloper) {
		this.deloper = deloper;
	}

	public String getTotalrows() {
		return totalrows;
	}

	public void setTotalrows(String totalrows) {
		this.totalrows = totalrows;
	}

	public String getSubgridid() {
		return subgridid;
	}

	public void setSubgridid(String subgridid) {
		this.subgridid = subgridid;
	}

}
