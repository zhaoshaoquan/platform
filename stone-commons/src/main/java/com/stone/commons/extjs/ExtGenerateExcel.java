package com.stone.commons.extjs;

import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang.StringUtils;

/**
 * @author 赵少泉
 * 用于把Ext列表中选择的数据生成Excel的公用处理类
 */
public class ExtGenerateExcel {
	//Excel工作薄
	private WritableSheet sheet = null;
	//生成Excel的数据集
	private ExtModel extModel = new ExtModel();
	//保存数据表头与该表头所在列的对应关系
	private Map<String,Cell> headerCol = new HashMap<String, Cell>();
	//当前列数
	private int col;
	//树的最大深度
	private  int maxDepth;
	//叶子节点个数
	private  int leafNumber;
	//设置表头字体样式
	private WritableFont headerFont = new WritableFont(WritableFont.createFont("宋体"),10,WritableFont.BOLD);
	private WritableCellFormat headerCellFormat = null;
	//设置正文字体样式
	private WritableFont bodyFont = new WritableFont(WritableFont.createFont("宋体"),10,WritableFont.NO_BOLD);
	private WritableCellFormat bodyCellFormat = null;
	/**
	 * 匹配XML标签正则
	 */
	private Pattern xmlTagPattern = Pattern.compile("(<[^<>]+>)");
	
	/**
	 * 树的根节点的深度值
	 */
	private int ROOT_DEPTH = 0;
	
	/**
	 * 设置树的最大深度值
	 * @param depth
	 */
	private  void setMaxDepth(int depth){
		this.maxDepth = (depth > maxDepth) ? depth : maxDepth;
	}
	
	/**
	 * 生成Excel的构造方法
	 * @param extModel
	 * 				生成Excel的数据集
	 */
	public ExtGenerateExcel(ExtModel extModel){
		this.extModel = extModel == null ? this.extModel : extModel;
		this.IterateData(this.extModel.getColumns(), ROOT_DEPTH);
	}

	/**
	 * 生成Excel文件
	 * @param file
	 * 			生成的文件
	 */
	public void buildExcel(File file){
		//用于判断指定的目录是否存在，如果不存在则依次创建所有目录
		File pf = new File(file.getAbsolutePath());
		if(pf.exists() || !pf.isDirectory()){
			pf.mkdirs();
		}
		try{
			this.writeBook(Workbook.createWorkbook(file));
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("~~~~~Excel文件生成失败~~~~~");
		}
	}
	
	/**
	 * 生成Excel文件
	 * @param os
	 * 			输出流对象
	 */
	public void buildExcel(OutputStream os){
		try{
			this.writeBook(Workbook.createWorkbook(os));
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("~~~~~Excel文件生成失败~~~~~");
		}
	}
	
	/**
	 * 把数据写入指定的工作薄
	 * @param
	 * 			xls工作薄对象
	 */
	private void writeBook(WritableWorkbook book) throws Exception{
		sheet = book.createSheet("Sheet0", 0);
		//生成表格表头
		buildHeader(extModel.getColumns());
		Object obj = null;
		//生成表格数据
		for(int i=0,row=maxDepth+1;i<extModel.getData().size();i++,row++){
			Map<String,Object> data = extModel.getData().get(i);
			for(String str : headerCol.keySet()){
				//设置单元格的样式
				bodyCellFormat = new WritableCellFormat(bodyFont);
				bodyCellFormat.setWrap(true);
				bodyCellFormat.setAlignment(headerCol.get(str).align);
				bodyCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
				obj = data.get(str)==null ? "" : data.get(str);
				sheet.addCell(new Label(headerCol.get(str).colNumber, row, this.delXmlTag(obj.toString()),bodyCellFormat));
			}
		}
		book.write();
		book.close();
	}
	
	/**
	 * 生成Excel的表头信息
	 * @param nodes
	 * 			节点数组集合
	 * @param headerCellFormat
	 * 			表头样式
	 */
	private void buildHeader(ExtColumn []nodes) throws Exception{
		for(int i=0;i<nodes.length;i++){
			if(nodes[i].getColumns() != null && !nodes[i].isHidden()){
				//对非叶子节点的节点进行行的合并操作
				sheet.mergeCells(col, nodes[i].getDepth(), (col+nodes[i].getLeafNumber()-1), nodes[i].getDepth());
				//设置单元格的样式
				headerCellFormat = new WritableCellFormat(headerFont);
				headerCellFormat.setAlignment(nodes[i].getAlignment());
				headerCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
				headerCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
				//把表头写入到Excel的单元格中
				sheet.addCell(new Label(col, nodes[i].getDepth(), nodes[i].getText(), headerCellFormat));
				buildHeader(nodes[i].getColumns());
			}else{
				//只有当前节点不是隐藏节点时才生成
				if(!nodes[i].isHidden()){
					//当前节点的深度不等于树的最大深度时，就对该节点进行列的合并操作
					if(nodes[i].getDepth() != maxDepth){
						int mr =  (nodes[i].getDepth() == ROOT_DEPTH) ? (maxDepth-nodes[i].getDepth()) : (maxDepth-nodes[i].getDepth()+1);
						sheet.mergeCells(col, nodes[i].getDepth(), col, mr);
					}
					sheet.setColumnView(col, nodes[i].getColWidth());
					//把表头与表关所在的列的对应保存到Map集合中
					if(nodes[i].getDataIndex() != null)
						headerCol.put(nodes[i].getDataIndex(), new Cell(col,nodes[i].getAlign()));
					//设置单元格的样式
					headerCellFormat = new WritableCellFormat(headerFont);
					headerCellFormat.setAlignment(nodes[i].getAlignment());
					headerCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
					headerCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
					//把表头写入到Excel的单元格中
					sheet.addCell(new Label(col++, nodes[i].getDepth(), nodes[i].getText(), headerCellFormat));
				}
			}
		}
	}
	
	/**
	 * 遍历树结构的数据，计算出每个节点的深度和该节点下的叶子节点的个数
	 * @param nodes
	 * 			节点数组集合
	 * @param depth
	 * 			节点的深度
	 */
	private void IterateData(ExtColumn []nodes,int depth){
		for(int i=0;i<nodes.length;i++){
			if(nodes[i].getColumns() != null && !nodes[i].isHidden()){
				nodes[i].setDepth(depth);
				setMaxDepth(++depth);
				IterateData(nodes[i].getColumns(),depth);
				leafNumber = 0;
				countLeaf(nodes[i].getColumns());
				nodes[i].setLeafNumber(leafNumber);
				depth--;
			}else{
				nodes[i].setDepth(depth);
			}
		}
	}
	
	/**
	 * 计算某个节点下面所有的叶子节点的个数
	 * @param nodes
	 * 			节点数组集合
	 */
	private void countLeaf(ExtColumn []nodes){
		for(int i=0;i<nodes.length;i++){
			if(nodes[i].getColumns() != null && !nodes[i].isHidden()){
				countLeaf(nodes[i].getColumns());
			}else{
				if(!nodes[i].isHidden())leafNumber++;
			}
		}
	}
	
	/**
	 * Excel单元格式对象
	 * @author 赵少泉
	 */
	private class Cell{
		//单元格列号
		private int colNumber;
		//单元格水平对齐方式
		private Alignment align;
		
		public Cell(int colNumber,String align){
			this.colNumber = colNumber;
			align = "".equals(align) || align==null ? "CENTRE" : align;
			if("LEFT".equals(align.toUpperCase())){
				this.align = Alignment.LEFT;
			}else if("CENTRE".equals(align.toUpperCase())){
				this.align = Alignment.CENTRE;
			}else if("RIGHT".equals(align.toUpperCase())){
				this.align = Alignment.RIGHT;
			}else{
				this.align = Alignment.CENTRE;
			}
		}
	}
	
	/**
	 * 删除XML标签内容
	 * @param str
	 * 			原始字符串
	 * @return
	 * 			删除后的字符串
	 */
	private String delXmlTag(String str){
		if(StringUtils.isEmpty(str))return "";
		StringBuffer sufferStr = new StringBuffer();
		Matcher xmlTagMatcher = xmlTagPattern.matcher(str);
		while(xmlTagMatcher.find()){
			xmlTagMatcher.appendReplacement(sufferStr, "");
		}
		xmlTagMatcher.appendTail(sufferStr);
		return sufferStr.toString();
	}
}
