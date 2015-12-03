package com.stone.demo.consist;

public class DemoConsist{
	public static final String jspRootPath = "/demo/jsp/";
	public static final String htmlRootPath = "/demo/html/";

	public static String toJspPage(String pageName){
		return String.format("%s%s", jspRootPath, pageName);
	}

	public static String toHtmlPage(String pageName){
		return String.format("%s%s", htmlRootPath, pageName);
	}
}
