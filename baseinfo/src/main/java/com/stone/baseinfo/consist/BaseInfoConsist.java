package com.stone.baseinfo.consist;

public class BaseInfoConsist{
	public static final String jspRootPath = "/baseinfo/jsp/";
	public static final String htmlRootPath = "/baseinfo/html/";

	public static String toJspPage(String pageName){
		return String.format("%s%s", jspRootPath, pageName);
	}

	public static String toHtmlPage(String pageName){
		return String.format("%s%s", htmlRootPath, pageName);
	}
}
