package com.stone.build;

import org.eclipse.jetty.server.Server;

import com.stone.jetty.JettyUtil;

public class BuildStart {

	public static void main(String[] args) {
		try{
			Server server = JettyUtil.createServer("/platform", 96);
			server.start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
