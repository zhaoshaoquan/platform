package com.stone.dao.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;

public class DefaultWebApplicationInitializer implements WebApplicationInitializer{

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException{
//		EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST);
//		FilterRegistration.Dynamic openEntityManagerInViewFilterChain = servletContext.addFilter("jpa-open-entity-manager", new OpenEntityManagerInViewFilter());
//		openEntityManagerInViewFilterChain.addMappingForUrlPatterns(dispatcherTypes, true, "/*");
	}

}
