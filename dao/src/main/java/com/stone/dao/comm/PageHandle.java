package com.stone.dao.comm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageHandle{
	private static Logger log = LoggerFactory.getLogger(PageHandle.class);

	public static void setParameter(Query query, int position, Object value){
		try{
			query.setParameter(position, value);
		}catch(IllegalArgumentException e){
			log.debug("WARN : " + e.getMessage());
			Pattern p = Pattern.compile("(\\w+\\.\\w+\\.\\w+)");
			Matcher matcher = p.matcher(e.getMessage());
			while(matcher.find()){
				String clazz = matcher.group(1);
				if(Integer.class.getName().equals(clazz)){
					value = Integer.parseInt(value.toString());
				}else if(Long.class.getName().equals(clazz)){
					value = Long.parseLong(value.toString());
				}else if(Double.class.getName().equals(clazz)){
					value = Double.parseDouble(value.toString());
				}else if(Boolean.class.getName().equals(clazz)){
					value = Boolean.parseBoolean(value.toString());
				}else if(Date.class.getName().equals(clazz)){
					String temp = value.toString();
					String pattern = null;
					if(temp.matches("^\\d{4}-\\d{2}-\\d{2}$")){
						pattern = "yyyy-MM-dd";
					}else if(temp.matches("^\\d{8}$")) {
						pattern = "yyyyMMdd";
					}else if(temp.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$")){
						pattern = "yyyy-MM-dd HH:mm:ss";
					}
					if(pattern != null){
						SimpleDateFormat sdf = new SimpleDateFormat(pattern);
						try{
							value = sdf.parse(temp);
						}catch(ParseException e1){
							throw new RuntimeException(e1);
						}
					}else{
						throw new RuntimeException("传递的日期值" + temp + "不能够被识别");
					}
				}
				query.setParameter(position, value);
			}
		}
	}

	public static Map<String, Class<?>> preProp(String[] fieldArray, Object[] fieldValArray){
		Map<String, Class<?>> propertyMap = new HashMap<String, Class<?>>();
		Class<?> clazz = null;
		for(int i = 0; i < fieldArray.length; i++){
			clazz = fieldValArray[i] != null ? fieldValArray[i].getClass() : Object.class;
			propertyMap.put(fieldArray[i], clazz);
		}
		return propertyMap;
	}

	public static String[] preFieldInfo(String list_ql){
		String[] fieldArray;
		int firstFormIndex = list_ql.indexOf("FROM");
		log.debug("firstFromIndex={}", firstFormIndex);
		String prefixFrom = list_ql.substring(0, firstFormIndex);
		log.debug("prefixFrom={}", prefixFrom);
		fieldArray = prefixFrom.replace("SELECT", "").trim().split(",");
		for(int i = 0; i < fieldArray.length; i++){
			String field = fieldArray[i];
			String[] s = field.split(" as | AS | ");
			if(s.length == 2){
				fieldArray[i] = s[1];
			}
			String[] tempArray = fieldArray[i].split("\\.");
			fieldArray[i] = tempArray[tempArray.length - 1];
		}
		return fieldArray;
	}

	/**
	 * 解析统计ql语句
	 * 
	 * @param qlString
	 * @return
	 * @throws Exception
	 */
	public static String preCountJPQL(String qlString){
		String countField = "*";
		String distinctField = findDistinctField(qlString);
		if(distinctField != null){
			countField = "DISTINCT " + distinctField;
		}
		if(qlString.matches("^FROM.+")){
			qlString = "SELECT count(" + countField + ") " + qlString;
		}else{
			int beginIndex = qlString.indexOf("FROM");
			qlString = "SELECT count(" + countField + ") " + qlString.substring(beginIndex);
		}
		return qlString.replaceAll("FETCH", "");
	}

	private static String findDistinctField(String qlString){
		int index = qlString.indexOf("DISTINCT");
		if(index != -1){
			String subql = qlString.substring(index + 9);
			int end = -1;
			for(int i = 0; i < subql.length(); i++){
				if(subql.substring(i, i + 1).matches(" |,")) {
					end = i;
					break;
				}
			}
			return subql.substring(0, end);
		}
		return null;
	}

	/**
	 * 格式化ql
	 * 
	 * @param qlString
	 * @return
	 */
	public static String convertQL(String qlString){
		String result = qlString.replaceAll("from", "FROM").replaceAll("distinct", "DISTINCT")
				.replaceAll("left join", "LEFT JOIN").replaceAll("fetch", "FETCH").replaceAll("select", "SELECT")
				.replaceAll("where", "WHERE").replaceAll("order by", "ORDER BY").replaceAll("asc", "ASC")
				.replaceAll("desc", "DESC").trim();
		log.debug("qlString = {}", result);
		return result;
	}

	/**
	 * 解析ql语句和参数
	 * 
	 * @param qlString
	 * @param params
	 * @param values
	 * @return
	 */
	public static String preQLAndParam(String qlString, Map<String, Object> params, List<Object> values){
		log.debug("开始解析qlString：{}", qlString);
		Map<Integer, Object> map_values = new HashMap<Integer, Object>();
		Map<Integer, String> map_names = new HashMap<Integer, String>();
		List<Integer> list = new ArrayList<Integer>();
		String preQL = qlString;
		if(params != null){
			for(String key : params.keySet()){
				int index = qlString.indexOf(":" + key);
				Object value = params.get(key);
				preQL = preQL.replaceAll(":" + key + " ", "? ");
				list.add(index);
				map_values.put(index, value);
				map_names.put(index, key);
			}
			log.debug("解析完成qlString:{}", preQL);
			Collections.sort(list);
			log.debug("最终参数值顺序(参数名->参数位置->参数值)：");
			for(Integer position : list){
				if(log.isDebugEnabled()){
					System.out.println(map_names.get(position) + "->" + position + "->" + map_values.get(position));
				}
				values.add(map_values.get(position));
			}
		}
		return preQL;
	}

}
