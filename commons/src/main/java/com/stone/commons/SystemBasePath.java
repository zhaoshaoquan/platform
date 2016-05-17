package com.stone.commons;

import static com.stone.commons.GlobalConfig.get;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemBasePath {
	private static final Logger log = LoggerFactory.getLogger(SystemBasePath.class);
	private static File rootDir = null;
	private static File tempDir = null;
	private static File downloadDir = null;
	private static File uploadDir = null;
	private static File ueditorDir = null;
	
	public static void initPath(String path){
		try{
			if("user.dir".equals(get("root.path", "user.dir").toLowerCase())){
				rootDir = new File(System.getProperty("user.dir"));
			}else{
				rootDir = new File(path);
			}
			tempDir = new File(rootDir, "temp");
			downloadDir = new File(rootDir, "download");
			
			if("root.path".equals(get("upload.path", "root.path").toLowerCase())){
				uploadDir = new File(rootDir, "upload");
			}else{
				uploadDir = new File(get("upload.path"), "upload");
			}
			ueditorDir = new File(uploadDir, "ueditor");
		}catch(Exception e){
			if(log.isErrorEnabled()){
				log.error("初始化创建目录失败");
				log.error(e.getLocalizedMessage(), e);
			}
		}
	}


	public static File getRootDir(){
		if(!rootDir.exists())rootDir.mkdirs();
		return rootDir;
	}

	public static String getRootDirPath(){
		return getRootDir().getAbsolutePath();
	}

	public static File getTempDir(){
		if(!tempDir.exists())tempDir.mkdirs();
		return tempDir;
	}

	public static String getTempDirPath(){
		return getTempDir().getAbsolutePath();
	}

	public static File getDownloadDir(){
		if(!downloadDir.exists())downloadDir.mkdirs();
		return downloadDir;
	}

	public static String getDownloadDirPath(){
		return getDownloadDir().getAbsolutePath();
	}

	public static File getUploadDir(){
		if(!uploadDir.exists())uploadDir.mkdirs();
		return uploadDir;
	}

	public static String getUploadDirPath(){
		return getUploadDir().getAbsolutePath();
	}

	public static File getUeditorDir(){
		if(!ueditorDir.exists())ueditorDir.mkdirs();
		return ueditorDir;
	}

	public static String getUeditorDirPath(){
		return getUeditorDir().getAbsolutePath();
	}

}
