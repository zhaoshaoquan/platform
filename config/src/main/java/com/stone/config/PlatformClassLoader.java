package com.stone.config;

import java.net.URL;

public class PlatformClassLoader extends ClassLoader {

	public PlatformClassLoader(ClassLoader parent){
		super(parent);
	}

	public URL getResource(String name){
		URL url = super.getResource(name);
		if((url == null) && ("".equals(name))) {
			url = PlatformClassLoader.class.getProtectionDomain().getCodeSource().getLocation();
		}
		return url;
	}
}
