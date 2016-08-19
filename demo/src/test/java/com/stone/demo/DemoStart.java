package com.stone.demo;

import static com.stone.jetty.JettyUtil.newJettyUtil;

public class DemoStart {

	public static void main(String[] args) {
		newJettyUtil().createServer("/platform", 96).start();
	}

}
