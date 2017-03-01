package com.stone.config.commons;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class SpringUtil{
	/**
	 * 全局删除id标示
	 */
	public static String GLOB_DELETE_ID_VAL = "globDeleteIdVal";

	public static ApplicationContext applicationContext;

	public static HttpServletRequest getRequest(){
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		return requestAttributes == null ? null : requestAttributes.getRequest();
	}

	public static HttpSession getSession(){
		return getRequest().getSession(false);
	}

	public static <T> T getBean(Class<T> requiredType){
		return applicationContext.getBean(requiredType);
	}

	public static Object getBean(String name){
		return applicationContext.getBean(name);
	}

	public static String getRealRootPath(){
		return getRequest().getServletContext().getRealPath("/");
	}

	public static String getIp(){
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		if(servletRequestAttributes != null) {
			HttpServletRequest request = servletRequestAttributes.getRequest();
			return request.getRemoteAddr();
		}
		return null;
	}

	public static Object getSessionAttribute(String name){
		HttpServletRequest request = getRequest();
		return request == null ? null : request.getSession().getAttribute(name);
	}

	public static void setSessionAttribute(String name, Object value){
		HttpServletRequest request = getRequest();
		if(request != null) {
			request.getSession().setAttribute(name, value);
		}
	}

	public static Object getRequestAttribute(String name){
		HttpServletRequest request = getRequest();
		return request == null ? null : request.getAttribute(name);
	}

	public static void setRequestAttribute(String name, Object value){
		HttpServletRequest request = getRequest();
		if(request != null) {
			request.setAttribute(name, value);
		}
	}

	public static String getContextPath(){
		return getRequest().getContextPath();
	}

	public static void removeSessionAttribute(String name){
		getRequest().getSession().removeAttribute(name);
	}

	public static boolean checkBrowser(){
		String userAgent = SpringUtil.getRequest().getHeader("user-agent");
		return isChrome(userAgent) || isIE(userAgent);
	}

	public static boolean isChrome(String userAgent){
		return userAgent.contains("Chrome");
	}

	public static boolean isIE(String userAgent){
		return userAgent.contains("rv:11.0") || userAgent.contains("MSIE 10.0") || userAgent.contains("MSIE 9.0");
	}

	public static boolean checkOpenBrowser(){
		String userAgent = SpringUtil.getRequest().getHeader("user-agent");
		return checkisChrome(userAgent) || checkisIE(userAgent);
	}

	public static boolean checkisChrome(String userAgent){
		return userAgent.contains("Chrome");
	}

	public static boolean checkisIE(String userAgent){
		return userAgent.contains("rv:11.0") || userAgent.contains("MSIE 10.0") || userAgent.contains("MSIE 9.0")
				|| userAgent.contains("MSIE 8.0") || userAgent.contains("MSIE 7.0");
	}

	/**
	 * 构建服务器传输给客户端的文件信息
	 * 
	 * @param fileName
	 *            文件名为空则显示当前时间
	 * @param resource
	 *            word 文件的路径
	 * @return
	 * @throws Exception
	 */
	public static ResponseEntity<byte[]> getResponseEntityByFile(File file, String fileName) throws Exception{
		FileInputStream stream = new FileInputStream(file);
		byte[] cbyte = IOUtils.toByteArray(stream);
		stream.close();
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		responseHeaders.setContentLength(cbyte.length);
		responseHeaders.set("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");
		return new ResponseEntity<byte[]>(cbyte, responseHeaders, HttpStatus.OK);
	}

	public static ResponseEntity<byte[]> getResponseEntityByFile(File file) throws Exception{
		return getResponseEntityByFile(file, file.getName());
	}
}
