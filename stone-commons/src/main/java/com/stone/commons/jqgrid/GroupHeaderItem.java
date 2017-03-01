package com.stone.commons.jqgrid;

public class GroupHeaderItem {
	private String startColumnName;
	private int numberOfColumns;
	private String titleText;
	private int startIndex;

	public GroupHeaderItem() {
	}

	public GroupHeaderItem(String startColumnName, int numberOfColumns,
			String titleText) {
		this.startColumnName = startColumnName;
		this.numberOfColumns = numberOfColumns;
		this.titleText = titleText;
	}

	public String getStartColumnName() {
		return startColumnName;
	}

	public void setStartColumnName(String startColumnName) {
		this.startColumnName = startColumnName;
	}

	public int getNumberOfColumns() {
		return numberOfColumns;
	}

	public void setNumberOfColumns(int numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
	}

	public String getTitleText() {
		return titleText;
	}

	public void setTitleText(String titleText) {
		this.titleText = titleText;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

}
