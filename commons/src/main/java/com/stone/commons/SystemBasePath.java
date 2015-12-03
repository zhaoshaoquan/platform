package com.stone.commons;

import java.io.File;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

public class SystemBasePath {
	private static final Logger log = LoggerFactory.getLogger(SystemBasePath.class);
	private static File rootDir = null;
	private static File tempDir = null;
	private static File downloadDir = null;
	private static File uploadDir = null;
	private static File ueditorDir = null;
	
	public static void initPath(String path){
		try{
			Properties p = new Properties();
			ClassPathResource cpr = new ClassPathResource("config.properties");
			p.load(cpr.getInputStream());
			if("user.dir".equals(p.getProperty("root.path", "user.dir").toLowerCase())){
				rootDir = new File(System.getProperty("user.dir"));
			}else{
				rootDir = new File(path);
			}
			tempDir = new File(rootDir, "temp");
			downloadDir = new File(rootDir, "download");
			
			if("root.path".equals(p.getProperty("upload.path", "root.path").toLowerCase())){
				uploadDir = new File(rootDir, "upload");
			}else{
				uploadDir = new File(p.getProperty("upload.path"), "upload");
			}
			ueditorDir = new File(uploadDir, "ueditor");
			
			rootDir.mkdirs();
			tempDir.mkdirs();
			downloadDir.mkdirs();
			uploadDir.mkdirs();
			ueditorDir.mkdirs();
		}catch(Exception e){
			if(log.isErrorEnabled()){
				log.error("初始化创建目录失败");
				log.error(e.getLocalizedMessage(), e);
			}
		}
	}


	public static File getRootDir(){
		return rootDir;
	}

	public static String getRootDirPath(){
		return rootDir.getAbsolutePath();
	}

	public static File getTempDir(){
		return tempDir;
	}

	public static String getTempDirPath(){
		return tempDir.getAbsolutePath();
	}
	
	public static File getDownloadDir(){
		return downloadDir;
	}
	
	public static String getDownloadDirPath(){
		return downloadDir.getAbsolutePath();
	}

	public static File getUploadDir(){
		return uploadDir;
	}
	
	public static String getUploadDirPath(){
		return uploadDir.getAbsolutePath();
	}

	public static File getUeditorDir(){
		return ueditorDir;
	}
	
	public static String getUeditorDirPath(){
		return ueditorDir.getAbsolutePath();
	}

}
