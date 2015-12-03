package com.stone.jetty;

import org.eclipse.jetty.server.Server;

public class JettyStart {

	public static void main(String[] args) {
		try{
			Server server = JettyUtil.createServer("/platform", 96);
			server.start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
