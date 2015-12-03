package com.stone.config.config;

import java.io.IOException;
import java.util.Properties;

public class PropertiesFactoryBean extends org.springframework.beans.factory.config.PropertiesFactoryBean {

	public Properties createProperties() throws IOException{
		return super.createProperties();
	}

}
