package com.stone.commons;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PoiExcelUtil{
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 打开excel文件
	 * @param file
	 * 			Excel文件对象
	 * @return
	 * 			POI的Workbook对象
	 * @throws Exception
	 */
	public static Workbook openWorkbook(File file)throws Exception{
		String fileName = file.getName();
		if(".XLS".equals(fileName.substring(fileName.lastIndexOf(".")).toUpperCase())){
			return new HSSFWorkbook(new FileInputStream(file));
		}else{
			return new XSSFWorkbook(new FileInputStream(file));
		}
	}
	
	/**
	 * 获取Excel单元格的值
	 * @param cell
	 * 			单元格对象
	 * @return
	 * 			单元格的字符串值
	 */
	public static String cellValue(Cell cell){
		return cellValue(cell, "%.2f");
	}
	
	/**
	 * 获取Excel单元格的值
	 * @param cell
	 * 			单元格对象
	 * @param format
	 * 			格式字符串
	 * @return
	 * 			单元格的字符串值
	 */
	public static String cellValue(Cell cell,String format){
		if(cell == null)return "";
		format = StringUtils.isEmpty(format) ? "%d" : format;
		switch(cell.getCellType()){
		case Cell.CELL_TYPE_NUMERIC:
		case Cell.CELL_TYPE_FORMULA:
			if(HSSFDateUtil.isCellDateFormatted(cell)){
                Date date = cell.getDateCellValue();
                return sdf.format(date);
             }else{
            	 BigDecimal b = new BigDecimal(cell.getNumericCellValue());
            	 return String.format(format, b.setScale(2, RoundingMode.HALF_UP).doubleValue());
             }
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		case Cell.CELL_TYPE_BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case Cell.CELL_TYPE_ERROR:
			return String.valueOf(cell.getErrorCellValue());
		default:
			return "";
		}
	}
	
}
