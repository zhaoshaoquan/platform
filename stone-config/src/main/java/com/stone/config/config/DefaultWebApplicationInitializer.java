package com.stone.config.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.IntrospectorCleanupListener;

import com.stone.commons.SystemBasePath;

public class DefaultWebApplicationInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException{
		SystemBasePath.initPath(servletContext.getRealPath("/"));
		
		XmlWebApplicationContext webApplicationContext = new XmlWebApplicationContext();
		webApplicationContext.setConfigLocation("classpath*:META-INF/spring/module-*.xml");
		
		//add listener
		servletContext.addListener(new ContextLoaderListener(webApplicationContext));
		servletContext.addListener(new IntrospectorCleanupListener());
		servletContext.addListener(new RequestContextListener());
		
		//add filter
		EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST);
		FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("CharacterEncodingFilter", new CharacterEncodingFilter());
		characterEncodingFilter.setInitParameter("encoding", "UTF-8");
		characterEncodingFilter.setInitParameter("forceEncoding", "true");
		characterEncodingFilter.addMappingForUrlPatterns(dispatcherTypes, true, "/**");

		//add servlet
		DispatcherServlet dispatcherServlet = new DispatcherServlet(webApplicationContext);
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("mvc", dispatcherServlet);
		dispatcher.setMultipartConfig(new MultipartConfigElement(null, 1024*1024*50, 1024*1024*100, 1024*1024*10));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
	}

}
