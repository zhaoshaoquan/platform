package com.stone.config.commons;

import java.io.File;

/**
 * 系统版本工具类
 */
public class VersionUtil {

	/**
	 * 获取当前系统版本信息
	 */
	public static String currentVersion() {
		String path = VersionUtil.class.getProtectionDomain().getCodeSource()
				.getLocation().getFile();
		File file = new File(path);
		if (file.isFile()) {
			String[] array = file.getName().split("-");
			if (array.length >= 2) {
				return array[1].replaceAll("\\.jar", "");
			}
		}
		return "";
	}
	
	/**
	 * 获取当前系统版本信息
	 */
	public static String currentVersion(String className) throws Exception {
		return currentVersion(Class.forName(className));
	}
	
	/**
	 * 获取当前系统版本信息
	 */
	public static String currentVersion(Class<?> clazz) {
		String path = clazz.getProtectionDomain().getCodeSource()
				.getLocation().getFile();
		File file = new File(path);
		if (file.isFile()) {
			String[] array = file.getName().split("-");
			if (array.length >= 2) {
				return array[1].replaceAll("\\.jar", "");
			}
		}
		return "";
	}
	
}
