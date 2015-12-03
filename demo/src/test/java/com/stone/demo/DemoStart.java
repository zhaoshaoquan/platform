package com.stone.demo;

import org.eclipse.jetty.server.Server;

import com.stone.jetty.JettyUtil;

public class DemoStart {

	public static void main(String[] args) {
		try{
			Server server = JettyUtil.createServer("/platform", 96);
			server.start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
