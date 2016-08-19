package com.stone.baseinfo;

import static com.stone.jetty.JettyUtil.newJettyUtil;

public class BaseInfoStart {

	public static void main(String[] args) {
		newJettyUtil().createServer("/jetty", 8080).start();
	}

}
