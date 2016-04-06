package com.stone.commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JsonUtils {
	private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);
	private static JsonFactory factory = new JsonFactory();
	
	
	@SuppressWarnings({"unchecked"})
	public static <E> E json2object(String json, Class<?> clazz){
		if(StringUtils.isEmpty(json))return null;
		ObjectMapper localObjectMapper = new ObjectMapper(factory);
		try{
			return (E)localObjectMapper.readValue(json.getBytes("utf-8"), clazz);
		}catch(Exception e){
			log.error(e.getMessage(), e);
			return null;
		}
	}
	
	public static String object2json(Object obj){
		if(obj == null)return StringUtils.EMPTY;
		ObjectMapper localObjectMapper = new ObjectMapper(factory);
		try{
			return localObjectMapper.writeValueAsString(obj);
		}catch(Exception e){
			log.error(e.getMessage(), e);
			return StringUtils.EMPTY;
		}
	}
	
	public static <T> T json2obj(File file, Class<T> clazz){
		ObjectMapper localObjectMapper = new ObjectMapper(factory);
		try{
			return localObjectMapper.readValue(new InputStreamReader(new FileInputStream(file), "utf-8"), clazz);
		}catch(Exception e){
			log.error(e.getMessage(), e);
			return null;
		}
	}
}
