package com.stone.config.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;

public class ResourceHttpRequestHandler extends org.springframework.web.servlet.resource.ResourceHttpRequestHandler{
	protected Logger logger = LoggerFactory.getLogger(ResourceHttpRequestHandler.class);

	public void handleRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		checkRequest(request);
		
		// check whether a matching resource exists
		Resource resource = getResource(request);
		if(resource == null) {
			String path = request.getServletPath();
			logger.info(path + ";No matching resource found - returning 404");
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/" + HttpServletResponse.SC_NOT_FOUND);
			// response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		// check the resource's media type
		MediaType mediaType = getMediaType(resource);
		if(mediaType != null) {
			if(logger.isDebugEnabled()) {
				logger.debug("Determined media type '" + mediaType + "' for " + resource);
			}
		}else{
			if(logger.isDebugEnabled()) {
				logger.debug("No media type found for " + resource + " - not sending a content-type header");
			}
		}

		// header phase
		if(new ServletWebRequest(request, response).checkNotModified(resource.lastModified())) {
			logger.debug("Resource not modified - returning 304");
			return;
		}
		setHeaders(response, resource, mediaType);

		// content phase
		if(METHOD_HEAD.equals(request.getMethod())) {
			logger.trace("HEAD request - skipping content");
			return;
		}
		writeContent(response, resource);
	}

}
