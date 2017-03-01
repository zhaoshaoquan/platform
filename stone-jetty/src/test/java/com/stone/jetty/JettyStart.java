package com.stone.jetty;

import static com.stone.jetty.JettyUtil.newJettyUtil;

public class JettyStart {

	public static void main(String[] args) {
		newJettyUtil().createServer("/jetty", 8080).start();
	}

}
