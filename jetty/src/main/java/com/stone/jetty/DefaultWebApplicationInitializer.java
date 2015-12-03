package com.stone.jetty;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.eclipse.jetty.servlets.GzipFilter;
import org.springframework.web.WebApplicationInitializer;

public class DefaultWebApplicationInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		String mimeTypes = "text/html,text/plain,text/xml,application/xhtml+xml,text/css,application/javascript,application/json,application/x-javascript,image/svg+xml";
		EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST);
		FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("GzipFilter", new GzipFilter());
		characterEncodingFilter.setInitParameter("mimeTypes", mimeTypes);
		characterEncodingFilter.addMappingForUrlPatterns(dispatcherTypes, true, "/*");
	}

}
