package com.stone.commons.jqgrid;

import java.util.ArrayList;
import java.util.List;

public class ComplexGroupHeaders {

	private boolean useColSpanStyle;
	private List<GroupHeaderItem> groupHeaders = new ArrayList<GroupHeaderItem>();
	private ComplexGroupHeaderItem[] complexGroupHeaders;

	public boolean isUseColSpanStyle() {
		return useColSpanStyle;
	}

	public void setUseColSpanStyle(boolean useColSpanStyle) {
		this.useColSpanStyle = useColSpanStyle;
	}

	public List<GroupHeaderItem> getGroupHeaders() {
		return groupHeaders;
	}

	public void setGroupHeaders(List<GroupHeaderItem> groupHeaders) {
		this.groupHeaders = groupHeaders;
	}

	public ComplexGroupHeaderItem[] getComplexGroupHeaders() {
		return complexGroupHeaders;
	}

	public void setComplexGroupHeaders(
			ComplexGroupHeaderItem[] complexGroupHeaders) {
		this.complexGroupHeaders = complexGroupHeaders;
	}

	public ComplexGroupHeaders addGroupHeaderItem(
			GroupHeaderItem groupHeaderItem) {
		groupHeaders.add(groupHeaderItem);
		return this;
	}
	
}
