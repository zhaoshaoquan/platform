package com.stone.commons.extjs;

import java.io.Serializable;

import jxl.format.Alignment;

/**
 * @author 赵少泉
 * @date 2015年8月10日 上午10:29:29
 */
public class ExtColumn implements Serializable{
	private static final long serialVersionUID = 1136108581287298536L;
	private String text;
	private String dataIndex;
	private int width;
	private String align;
	private boolean hidden;
	private ExtColumn []columns;
	//该节点相对于根节点的深度
	private int depth;
	//该节点下叶子节点的个数
	private int leafNumber;
	
	/**
	 * 把页面传进来的像素宽度转换为Excel表格表示的宽度
	 * @return
	 * 		Excel表格表示的宽度
	 */
	public int getColWidth(){
		int width = (int)Math.round((this.width/7.5));
		if(width<0)width = 0;
		if(width>255) width = 255;
		return width;
	}
	
	/**
	 * 把页面传进来的表格的对齐方式（字符串）转换为Excel表格的对齐方式
	 * @return
	 * 		Excel表格的对齐方式
	 */
	public Alignment getAlignment(){
		this.align = "".equals(this.align) || this.align==null ? "CENTRE" : this.align;
		if("LEFT".equals(this.align.toUpperCase())){
			return Alignment.LEFT;
		}else if("CENTRE".equals(this.align.toUpperCase())){
			return Alignment.CENTRE;
		}else if("RIGHT".equals(this.align.toUpperCase())){
			return Alignment.RIGHT;
		}else{
			return Alignment.CENTRE;
		}
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getDataIndex() {
		return dataIndex;
	}
	public void setDataIndex(String dataIndex) {
		this.dataIndex = dataIndex;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public String getAlign() {
		return align;
	}
	public void setAlign(String align) {
		this.align = align;
	}
	public boolean isHidden() {
		return hidden;
	}
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	public ExtColumn[] getColumns() {
		return columns;
	}
	public void setColumns(ExtColumn[] columns) {
		this.columns = columns;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public int getLeafNumber() {
		return leafNumber;
	}
	public void setLeafNumber(int leafNumber) {
		this.leafNumber = leafNumber;
	}
	
}
