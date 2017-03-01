package com.stone.commons.jqgrid;

import java.util.List;

/**
 *
 */
public class Options {

	/**
	 * The url of the file that returns the data needed to populate the grid
	 */
	private String url;
	/**
	 * Defines in what format to expect the data that fills the grid. Valid
	 * options are xml (we expect data in xml format), xmlstring (we expect xml
	 * data as string), json (we expect data in JSON format), jsonstring (we
	 * expect JSON data as a string), local (we expect data defined at client
	 * side (array data)), javascript (we expect javascript as data), function
	 * (custom defined function for retrieving data). See colModel API and
	 * Retrieving Data
	 */
	private String datatype = "xml";
	/**
	 * Sets how many records we want to view in the grid. This parameter is
	 * passed to the url for use by the server routine retrieving the data. Note
	 * that if you set this parameter to 10 (i.e. retrieve 10 records) and your
	 * server return 15 then only 10 records will be loaded. <del>Set this
	 * parameter to -1 (unlimited) to disable this checking.</del>
	 */
	private int rowNum = 20;
	/**
	 * An array to construct a select box element in the pager in which we can
	 * change the number of the visible rows. When changed during the execution,
	 * this parameter replaces the rowNum parameter that is passed to the url.
	 * If the array is empty, this element does not appear in the pager.
	 * Typically you can set this like [10,20,30]. If the rowNum parameter is
	 * set to 30 then the selected value in the select box is 30.
	 */
	private List<String> rowList;
	/**
	 * Defines that we want to use a pager bar to navigate through the records.
	 * This must be a valid HTML element; in our example we gave the div the id
	 * of 鈥減ager鈥� but any name is acceptable. Note that the navigation layer
	 * (the 鈥減ager鈥�div) can be positioned anywhere you want, determined by your
	 * HTML; in our example we specified that the pager will appear after the
	 * body layer. The valid settings can be (in the context of our example)
	 * pager, #pager, jQuery('#pager'). I recommend to use the second one -
	 * #pager. See Pager
	 */
	private String pager;
	/**
	 * If true, jqGrid displays the beginning and ending record number in the
	 * grid, out of the total number of records in the query. This information
	 * is shown in the pager bar (bottom right by default)in this format: 鈥淰iew
	 * X to Y out of Z鈥� If this value is true, there are other parameters that
	 * can be adjusted, including emptyrecords and recordtext.
	 */
	private boolean viewrecords = false;
	/**
	 * When set to true, the grid width is recalculated automatically to the
	 * width of the parent element. This is done only initially when the grid is
	 * created. In order to resize the grid when the parent element changes
	 * width you should apply custom code and use the setGridWidth method for
	 * this purpose
	 */
	private boolean autowidth = false;
	/**
	 * he height of the grid. Can be set as number (in this case we mean pixels)
	 * or as percentage (only 100% is acceped) or value of auto is acceptable.
	 */
	private String height = "150";
	/**
	 * The column according to which the data is to be sorted when it is
	 * initially loaded from the server (note that you will have to use
	 * datatypes xml or json to load remote data). This parameter is appended to
	 * the url. If this value is set and the index (name) matches the name from
	 * colModel, then an icon indicating that the grid is sorted according to
	 * this column is added to the column header. This icon also indicates the
	 * sorting order - descending or ascending (see the parameter sortorder).
	 * Also see prmNames.
	 */
	private String sortname;
	/**
	 * Defines the caption for the grid. This caption appears in the caption
	 * layer, which is above the header layer (see How It Works). If the string
	 * is empty the caption does not appear.
	 */
	private String caption;

	/**
	 * If this flag is set to true a multi selection of rows is enabled. A new
	 * column at left side containing checkboxes is added. Can be used with any
	 * datatype option.
	 */
	private boolean multiselect = false;
	/**
	 * Array which describes the parameters of the columns.This is the most
	 * important part of the grid. For a full description of all valid values
	 * see colModel API.
	 */
	private String[] colNames;
	/**
	 * An array in which we place the names of the columns. This is the text
	 * that appears in the head of the grid (header layer). The names are
	 * separated with commas. Note that the number of elements in this array
	 * should be equal of the number elements in the colModel array.
	 */
	private ColModelItem[] colModel;

	/**
	 * The default value of this property is: <br>
	 <code>{page:鈥減age鈥�rows:鈥渞ows鈥� sort:鈥渟idx鈥� order:鈥渟ord鈥� search:鈥淿search鈥� nd:鈥渘d鈥� id:鈥渋d鈥� oper:鈥渙per鈥� editoper:鈥渆dit鈥� addoper:鈥渁dd鈥� deloper:鈥渄el鈥� subgridid:鈥渋d鈥� npage:null, totalrows:鈥渢otalrows鈥潁</code> <br>
	 This customizes names of the fields sent to the server on a POST request. For example, with this setting, you can change the sort order element from <code>sidx</code> to <code>mysort</code> by setting <code>prmNames: {sort: 鈥渕ysort鈥潁</code>. The string that will be POST-ed to the server will then be <code>myurl.php?page=1&amp;rows=10&amp;mysort=myindex&amp;sord=asc</code>  rather than <code>myurl.php?page=1&amp;rows=10&amp;sidx=myindex&amp;sord=asc</code> <br>
	 So the value of the column on which to sort upon can be obtained by looking at <code>$POST['mysort']</code> in <acronym title="Hypertext Preprocessor">PHP</acronym>. When some parameter is set to null, it will be not sent to the server. For example if we set <code>prmNames: {nd:null}</code> the <code>nd</code> parameter will not be sent to the server.  For <code>npage</code> option see the <code>scroll</code> option. <br>
	 These options have the following meaning and default values: <br>
	 <code>page</code>: the requested page (default value <code>page</code>) <br>
	 <code>rows</code>: the number of rows requested (default value <code>rows</code>) <br>
	 <code>sort</code>: the sorting column (default value <code>sidx</code>) <br>
	 <code>order</code>: the sort order (default value <code>sord</code>) <br>
	 <code>search</code>: the search indicator (default value <code>_search</code>) <br>
	 <code>nd</code>: the time passed to the request (for <acronym title="Internet Explorer">IE</acronym> browsers not to cache the request) (default value <code>nd</code>) <br>
	 <code>id</code>: the name of the id when POST-ing data in editing modules (default value <code>id</code>) <br>
	 <code>oper</code>: the operation parameter (default value <code>oper</code>) <br>
	 <code>editoper</code>: the name of operation when the data is POST-ed in edit mode (default value <code>edit</code>) <br>
	 <code>addoper</code>: the name of operation when the data is posted in add mode (default value <code>add</code>) <br>
	 <code>deloper</code>: the name of operation when the data is posted in delete mode (default value <code>del</code>) <br>
	 <code>totalrows</code>: the number of the total rows to be obtained from server - see <code>rowTotal</code> (default value <code>totalrows</code>) <br>
	 <code>subgridid</code>: the name passed when we click to load data in the subgrid (default value <code>id</code>)
	 */
	private PrmNames prmNames;
	
	
	public boolean isMultiselect() {
		return multiselect;
	}

	public void setMultiselect(boolean multiselect) {
		this.multiselect = multiselect;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public List<String> getRowList() {
		return rowList;
	}

	public void setRowList(List<String> rowList) {
		this.rowList = rowList;
	}

	public String getPager() {
		return pager;
	}

	public void setPager(String pager) {
		this.pager = pager;
	}

	public boolean isViewrecords() {
		return viewrecords;
	}

	public void setViewrecords(boolean viewrecords) {
		this.viewrecords = viewrecords;
	}

	public boolean isAutowidth() {
		return autowidth;
	}

	public void setAutowidth(boolean autowidth) {
		this.autowidth = autowidth;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getSortname() {
		return sortname;
	}

	public void setSortname(String sortname) {
		this.sortname = sortname;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String[] getColNames() {
		return colNames;
	}

	public void setColNames(String[] colNames) {
		this.colNames = colNames;
	}

	public ColModelItem[] getColModel() {
		return colModel;
	}

	public void setColModel(ColModelItem[] colModel) {
		this.colModel = colModel;
	}

	public PrmNames getPrmNames() {
		return prmNames;
	}

	public void setPrmNames(PrmNames prmNames) {
		this.prmNames = prmNames;
	}
	
	

}
