package com.stone.commons;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class DateUtil {
	public static final String yyyy_MM_dd="yyyy-MM-dd";
	public static final String yyyy_MM_dd_ZN="yyyy 年 MM 月 dd 日";
	public static final String yyyy_MM_dd_ZN1="yyyy年MM月dd日";
	public static final String yyyy_MM_dd_ZN2="yyyy年M月d日";
	public static final String yyyy_MM_dd_HH="yyyy 年 MM 月 dd 日    HH 时";
	public static final String yyyy_MM_dd_HH_mm="yyyy 年 MM 月 dd 日    HH 时mm分";
	public static final String yyyy_MM_dd_HH_mm_ss="yyyy-MM-dd HH:mm:ss";
	public static final String HH_mm_ss="HH:mm:ss";
	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	
	public static String  dateToStr(String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String str = sdf.format(new Date());
		return str;
	}
	
	public static String  dateToStr(){
		return dateToStr(yyyy_MM_dd_HH_mm_ss);
	}
	
	public static String  dateToStr(Date  date){
		return dateToStr(date, yyyy_MM_dd);
	}
	
	public static String dateToStr(Date date,String pattern){
		if(date == null){
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String str = sdf.format(date);
		return str;
	}
	/**
	 * 时间String转Date
	 * @param date
	 * @param pattern
	 * @return
	 * @throws ParseException 
	 * @throws Exception
	 */
	public static Date strToDate(String date,String pattern) throws ParseException{
		DateFormat format = new SimpleDateFormat(pattern);        
		return format.parse(date); 
	}
	/**
	 * 时间String转Date
	 * 默认 转化类型 yyyy-MM-dd
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static Date strToDate(String date)throws Exception{
		return strToDate(date, yyyy_MM_dd);
	}
	/**
	 * 得到当前时间
	 * @return   yyyy-MM-dd HH:mm:ss
	 * @throws Exception
	 */
	public static Date getCurrDate() throws Exception{
		String dateStr = dateToStr(yyyy_MM_dd_HH_mm_ss);
		SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
		return sdf.parse(dateStr);
	}
	
	 public static int getLastDayOfMonth(int year, int month) {   
         Calendar cal = Calendar.getInstance();   
         cal.set(Calendar.YEAR, year);   
         cal.set(Calendar.MONTH, month);   
         return cal.getActualMaximum(Calendar.DATE);
     } 
	 public static int getFirstDayOfMonth(int year, int month) {   
         Calendar cal = Calendar.getInstance();   
         cal.set(Calendar.YEAR, year);   
         cal.set(Calendar.MONTH, month);
         return cal.getMinimum(Calendar.DATE);
     } 
	 
		/**
		 * 比较两个日期
		 * @param start
		 * @param end
		 * @return
		 *  start >  end 则返回 1 
		 *  start =  end 则返回 0 
		 *  start <  end 则返回 -1 
		 */
		public static  int dateCompare(Date start, Date end) throws Exception{
			if(start == null && end == null){
				return 0;
			}
			if(start == null && end != null){
				return -1;
			}
			if(start != null && end == null){
				return 1;
			}
			Calendar cal= Calendar.getInstance();
			cal.setTime(start);
			Calendar ca2 = Calendar.getInstance();
			ca2.setTime(end);
			return cal.compareTo(ca2);
		}
		/**
		 * 判断一个时间是否在一段时间内
		 * @param start 
		 * @param date 比较的日期
		 * @param end 
		 * @return
		 *  start <= date <=  end 则返回 true
		 *  其他则返回false
		 */
		public static boolean  dateInTwoDays(Date start, Date date, Date end) throws Exception{
			if(start == null || end == null || date == null){
				return false;
			}
			Calendar ca1= Calendar.getInstance();
			ca1.setTime(start);
			Calendar ca2 = Calendar.getInstance();
			ca2.setTime(end);
			
			Calendar ca3= Calendar.getInstance();
			ca3.setTime(date);
			
			if( ca2.compareTo(ca1) > -1 && ca3.compareTo(ca1) > -1 && ca2.compareTo(ca3) > -1){
				return true;
			}
			return false;
		}
		
		
		/**
		 * 得到当前月的最后一天
		 * @return   yyyy-MM-dd HH:mm:ss
		 */
		 public  static String getLastDayOfMonth() {   
	         Calendar cal = Calendar.getInstance();   
	         String yearMonth = cal.get(Calendar.YEAR)+ "-";
	         if((cal.get(Calendar.MONTH)+1) < 10){
	        	 yearMonth += "0"+(cal.get(Calendar.MONTH)+1);
	         }else{
	        	 yearMonth += (cal.get(Calendar.MONTH)+1);
	         }
	         return yearMonth + "-" + cal.getActualMaximum(Calendar.DATE) + " 23:59:59";
	     } 
		 /**
		 * 得到当前月的第一天
		 * @return   yyyy-MM-dd HH:mm:ss
		 */
		 public static String getFirstDayOfMonth() {   
			 Calendar cal = Calendar.getInstance();   
	         String yearMonth = cal.get(Calendar.YEAR)+ "-";
	         if((cal.get(Calendar.MONTH)+1) < 10){
	        	 yearMonth += "0"+(cal.get(Calendar.MONTH)+1);
	         }else{
	        	 yearMonth += (cal.get(Calendar.MONTH)+1);
	         }
	         return yearMonth + "-01 00:00:00";
	     } 
		 
		 
		 /**
		  * 得到当前年
		  * @return   yyyy
		  */
		 public  static String getNowYear() {   
			 Calendar cal = Calendar.getInstance();   
			 return cal.get(Calendar.YEAR) + "";
		 } 
		 public  static int getYearByDate(Date date) {   
			 Calendar cal = Calendar.getInstance();   
			 cal.setTime(date);
			 return cal.get(Calendar.YEAR);
		 } 
		 /**
		  * 得到当前月
		  * @return   mm
		  */
		 public  static String getNowMonth() {   
			 Calendar cal = Calendar.getInstance();   
			 return cal.get(Calendar.MONTH) + "";
		 } 
		 public  static int getMonthByDate(Date date) {   
			 Calendar cal = Calendar.getInstance();   
			 cal.setTime(date);
			 return cal.get(Calendar.MONTH);
		 } 
		 /**
		  * 得到当前日
		  * @return   mm
		  */
		 public  static String getNowDay() {   
			 Calendar cal = Calendar.getInstance();   
			 return cal.get(Calendar.DATE) + "";
		 } 
		 public  static int getDayByDate(Date date) {   
			 Calendar cal = Calendar.getInstance();   
			 cal.setTime(date);
			 return cal.get(Calendar.DATE);
		 } 
		 /**
		  * 根据年月日得到日期
		  * @return   mm
		  */
		 public  static Date getDate(int year,int month, int date) {   
			 Calendar cal = Calendar.getInstance();  
			 cal.set(year, month, date);
			 return cal.getTime();
		 } 
		 
		 /**
		  * 计算两个日期将的天数
		  * @param startDate
		  * @param endDate
		  * @return 天数
		  */
		 public  static int daysBetween(Date startDate, Date endDate) {   
			 try {
				if(startDate == null || endDate == null){
					 return 0;
				}
			    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			    startDate = sdf.parse(sdf.format(startDate));
			    endDate = sdf.parse(sdf.format(endDate));
		        Calendar cal = Calendar.getInstance();  
		        cal.setTime(startDate);  
		        long time1 = cal.getTimeInMillis ();               
		        cal.setTime(endDate);  
		        long time2 = cal.getTimeInMillis ();       
		        long between_days=(time2-time1)/ (1000*3600*24);
		        return Integer.parseInt(String.valueOf (between_days));
			 } catch (Exception e) {
				e.printStackTrace();
			 }
			 return 0;
		 } 
		 
		 /**
		  * 计算年龄
		  * @param csrq 出生日期
		  * @return
		  */
		 public  static int calculationAge(Date csrq) {   
			 try {
				 if(csrq == null ){
					 return 0;
				 }
				 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				 String csyear = sdf.format(csrq).substring(0, 4);
				 String nowYear = getNowYear();
				 return Integer.valueOf(nowYear) - Integer.valueOf(csyear);
			 } catch (Exception e) {
				 e.printStackTrace();
			 }
			 return 0;
		 } 
		 
		 /**
		  * 验证开始时间是否小于结束时间
		  * @param startDate
		  * 		开始时间
		  * @param endDate
		  * 		结束时间
		  * @throws Exception
		  */
		 public static void checkTime(Date startDate, Date endDate) throws Exception {
			if(startDate==null){
				throw new RuntimeException("开始时间不能为空");
			}
			if(endDate==null){
				throw new RuntimeException("结束时间不能为空");
			}
			if(startDate.after(endDate)){
				throw new RuntimeException("开始时间不能大于结束时间");
			}
		}
		 
		 /**
		  * 对当前刑期做处理
		  * （去除oo年、00个月、00天）
		  * @param xq   xx年xx个月xx天
		  * @return
		  */
		 public static String minutesXqZero(String xq){
			 if(StringUtils.isEmpty(xq)){
				 return "无";
			 }
			 String yearStr="",monthStr="", dayStr="";
			 try {
				 yearStr = xq.split("年")[0];
			 } catch (Exception e) {}
			 xq = xq.replace(yearStr+"年", "");
			 try {
				 monthStr = xq.split("个月")[0];
			 } catch (Exception e) {}
			 xq = xq.replace(monthStr+"个月", "");
			 try {
				 dayStr = xq.split("天")[0];
			 } catch (Exception e) {}
			 int year = 0, month=0, day=0;
			 try {
				 year = Integer.valueOf(yearStr);
			 } catch (Exception e) {}
			 try {
				 month = Integer.valueOf(monthStr);
			 } catch (Exception e) {}
			 try {
				 day = Integer.valueOf(dayStr);
			 } catch (Exception e) {}
			 String xqBack = "";
			 if(year>0){
				 xqBack += year+"年";
			 }
			 if(month>0){
				 xqBack += month+"个月";
			 }
			 if(day>0){
				 xqBack += day+"天";
			 }
			 return xqBack;
		 }
		 
}
