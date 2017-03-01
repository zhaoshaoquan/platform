package com.stone.build;

import static com.stone.jetty.JettyUtil.newJettyUtil;

public class BuildStart {

	public static void main(String[] args) {
		newJettyUtil().createServer("/platform", 8080).start();
	}

}
