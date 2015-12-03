package com.stone.commons.extjs;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
/**
 * @author 赵少泉
 * 保存要生成Excel报表的信息
 */
public class ExtModel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private ExtColumn []columns;				//表头
	private List<Map<String,Object>> data;		//数据
	
	public ExtModel(){}
	/**
	 * @param columns
	 * 			表头
	 * @param data
	 * 			数据
	 */
	public ExtModel(ExtColumn []columns,List<Map<String,Object>> data){
		this.columns = columns;
		this.data = data;
	}
	
	public ExtColumn[] getColumns() {
		return columns;
	}
	public void setColumns(ExtColumn[] columns) {
		this.columns = columns;
	}
	public List<Map<String, Object>> getData() {
		return data;
	}
	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}
	
}
