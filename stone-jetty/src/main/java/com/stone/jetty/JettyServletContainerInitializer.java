package com.stone.jetty;

import org.eclipse.jetty.servlets.GzipFilter;

import javax.servlet.*;
import java.util.EnumSet;
import java.util.Set;

/**
 * Created by 赵少泉 on 2016-08-05.
 */
public class JettyServletContainerInitializer implements ServletContainerInitializer{
    @Override
    public void onStartup(Set<Class<?>> webAppInitializerClasses, ServletContext servletContext) throws ServletException{
        String mimeTypes = "text/html,text/plain,text/xml,application/xhtml+xml,text/css,application/javascript,application/json,application/x-javascript,image/svg+xml";
        EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST);
        FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("JettyGzipFilter", new GzipFilter());
        characterEncodingFilter.setInitParameter("mimeTypes", mimeTypes);
        characterEncodingFilter.addMappingForUrlPatterns(dispatcherTypes, true, "/*");
    }
}
