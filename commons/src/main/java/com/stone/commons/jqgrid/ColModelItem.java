package com.stone.commons.jqgrid;

/**
 * The colModel property defines the individual grid columns as an array of
 * properties. This is the most important part of the jqGrid
 */
public class ColModelItem {

	/**
	 * Defines the alignment of the cell in the Body layer, not in header cell.
	 * Possible values: left, center, right.
	 */
	private String align = "left";
	/**
	 * This option allow to add classes to the column. If more than one class
	 * will be used a space should be set. By example classes:'class1 class2'
	 * will set a class1 and class2 to every cell on that column. In the grid
	 * css there is a predefined class ui-ellipsis which allow to attach
	 * ellipsis to a particular row. Also this will work in FireFox too
	 */
	private String classes = "";
	/**
	 * Governs format of sorttype:date (when datetype is set to local) and
	 * editrules {date:true} fields. Determines the expected date format for
	 * that column. Uses a PHP-like date formatting. Currently ”/”, ”-”, and ”.”
	 * are supported as date separators. Valid formats are: y,Y,yyyy for four
	 * digits year YY, yy for two digits year m,mm for months d,dd for days.
	 */
	private String datefmt;
	/**
	 * The default value for the search field. This option is used only in
	 * Custom Searching and will be set as initial search.
	 */
	private String defval;
	/**
	 * Defines if the field is editable. This option is used in cell, inline and
	 * form modules. See editing
	 */
	private boolean editable = false;
	/**
	 * Defines the edit type for inline and form editing Possible values: text,
	 * textarea, select, checkbox, password, button, image and file. See also
	 * editing
	 */
	private String edittype = "text";
	/**
	 * If set to asc or desc, the column will be sorted in that direction on
	 * first sort.Subsequent sorts of the column will toggle as usual
	 */
	private String firstsortorder;
	/**
	 * If set to true this option does not allow recalculation of the width of
	 * the column if shrinkToFit option is set to true. Also the width does not
	 * change if a setGridWidth method is used to change the grid width.
	 */
	private boolean fixed = false;
	/**
	 * The predefined types (string) or custom function name that controls the
	 * format of this field. See Formatter for more details.
	 */
	private String formatter;
	/**
	 * If set to true determines that this column will be frozen after calling
	 * the setFrozenColumns method
	 */
	private boolean frozen = false;
	/**
	 * If set to true this column will not appear in the modal dialog where
	 * users can choose which columns to show or hide. See Show/Hide Columns
	 */
	private boolean hidedlg = false;
	/**
	 * Defines if this column is hidden at initialization.
	 */
	private boolean hidden = false;
	/**
	 * Set the index name when sorting. Passed as sidx parameter.
	 */
	private String index;
	/**
	 * Defines the json mapping for the column in the incoming json string. See
	 * Retrieving Data
	 */
	private String jsonmap;
	/**
	 * In case if there is no id from server, this can be set as as id for the
	 * unique row id. Only one column can have this property. If there are more
	 * than one key the grid finds the first one and the second is ignored.
	 */
	private boolean key = false;
	/**
	 * When colNames array is empty, defines the heading for this column. If
	 * both the colNames array and this setting are empty, the heading for this
	 * column comes from the name property.
	 */
	private String label;
	/**
	 * Set the unique name in the grid for the column. This property is
	 * required. As well as other words used as property/event names, the
	 * reserved words (which cannot be used for names) include subgrid, cb and
	 * rn.
	 */
	private String name;
	/**
	 * Defines if the column can be re sized
	 */
	private boolean resizable = true;
	/**
	 * When used in search modules, disables or enables searching on that
	 * column. Search Configuration
	 */
	private boolean search = true;
	/**
	 * Defines is this can be sorted.
	 */
	private boolean sortable = true;
	/**
	 * Used when datatype is local. Defines the type of the column for
	 * appropriate sorting.Possible values:<br/>
	 * int/integer - for sorting integer<br/>
	 * float/number/currency - for sorting decimal numbers <br/>
	 * date - for sorting date<br/>
	 * text - for text sorting <br/>
	 * function - defines a custom function for sorting. To this function we
	 * pass the value to be sorted and it should return a value too.
	 */
	private String sorttype;
	/**
	 * Determines the type of the element when searching. See Search
	 * Configuration
	 */
	private String stype;
	/**
	 * Valid only in Custom Searching and edittype : 'select' and describes the
	 * url from where we can get already-constructed select element
	 */
	private String surl;
	/**
	 * If this option is false the title is not displayed in that column when we
	 * hover a cell with the mouse
	 */
	private boolean title = true;
	/**
	 * Set the initial width of the column, in pixels. This value currently can
	 * not be set as percentage
	 */
	private int width = 150;
	/**
	 * Defines the xml mapping for the column in the incomming xml file. Use a
	 * CSS specification for this See Retrieving Data
	 */
	private String xmlmap;
	/**
	 * This option is valid only when viewGridRow method is activated. When the
	 * option is set to false the column does not appear in view Form
	 */
	private boolean viewable;
	private int widthOrg;
	private Object formatoptions = new DateFormatoptions();

	private Editrules editrules;

	public ColModelItem() {
	}

	public ColModelItem(String name, String index, String formatter) {
		this.name = name;
		this.index = index;
		this.formatter = formatter;
	}

	public ColModelItem(String name, String index, String formatter,
			Formatoptions formatoptions) {
		this.name = name;
		this.index = index;
		this.formatter = formatter;
		this.formatoptions = formatoptions;
	}

	public ColModelItem(String name, String index) {
		this.name = name;
		this.index = index;
	}

	public ColModelItem(String name, String index, String formatter, int width) {
		this.name = name;
		this.index = index;
		this.formatter = formatter;
		this.width = width;
	}

	public ColModelItem(String name, String index, String formatter, int width,
			boolean sortable) {
		super();
		this.name = name;
		this.index = index;
		this.formatter = formatter;
		this.width = width;
		this.sortable = sortable;
	}

	public boolean isTitle() {
		return title;
	}

	public void setTitle(boolean title) {
		this.title = title;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public int getWidthOrg() {
		return widthOrg;
	}

	public void setWidthOrg(int widthOrg) {
		this.widthOrg = widthOrg;
	}

	public boolean isResizable() {
		return resizable;
	}

	public void setResizable(boolean resizable) {
		this.resizable = resizable;
	}

	public Object getFormatoptions() {
		return formatoptions;
	}

	public void setFormatoptions(Object formatoptions) {
		this.formatoptions = formatoptions;
	}

	public boolean isSortable() {
		return sortable;
	}

	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getFormatter() {
		return formatter;
	}

	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isHidedlg() {
		return hidedlg;
	}

	public void setHidedlg(boolean hidedlg) {
		this.hidedlg = hidedlg;
	}

	public boolean isSearch() {
		return search;
	}

	public void setSearch(boolean search) {
		this.search = search;
	}

	public boolean isFixed() {
		return fixed;
	}

	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	public String getDatefmt() {
		return datefmt;
	}

	public void setDatefmt(String datefmt) {
		this.datefmt = datefmt;
	}

	public String getDefval() {
		return defval;
	}

	public void setDefval(String defval) {
		this.defval = defval;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public String getEdittype() {
		return edittype;
	}

	public void setEdittype(String edittype) {
		this.edittype = edittype;
	}

	public String getFirstsortorder() {
		return firstsortorder;
	}

	public void setFirstsortorder(String firstsortorder) {
		this.firstsortorder = firstsortorder;
	}

	public boolean isFrozen() {
		return frozen;
	}

	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}

	public String getJsonmap() {
		return jsonmap;
	}

	public void setJsonmap(String jsonmap) {
		this.jsonmap = jsonmap;
	}

	public boolean isKey() {
		return key;
	}

	public void setKey(boolean key) {
		this.key = key;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getSorttype() {
		return sorttype;
	}

	public void setSorttype(String sorttype) {
		this.sorttype = sorttype;
	}

	public String getStype() {
		return stype;
	}

	public void setStype(String stype) {
		this.stype = stype;
	}

	public String getSurl() {
		return surl;
	}

	public void setSurl(String surl) {
		this.surl = surl;
	}

	public String getXmlmap() {
		return xmlmap;
	}

	public void setXmlmap(String xmlmap) {
		this.xmlmap = xmlmap;
	}

	public boolean isViewable() {
		return viewable;
	}

	public void setViewable(boolean viewable) {
		this.viewable = viewable;
	}

	public Editrules getEditrules() {
		return editrules;
	}

	public void setEditrules(Editrules editrules) {
		this.editrules = editrules;
	}

}
