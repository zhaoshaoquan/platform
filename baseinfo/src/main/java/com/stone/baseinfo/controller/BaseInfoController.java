package com.stone.baseinfo.controller;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stone.baseinfo.consist.BaseInfoConsist;

@Controller
@RequestMapping("/")
public class BaseInfoController{
	
	@RequestMapping(value="login")
	public String login(HttpServletRequest request)throws Exception{
		return BaseInfoConsist.toHtmlPage("login");
	}
	
	@RequestMapping(value="page/{model:^[a-zA-Z]{1,}[a-zA-Z_0-9-]*$}/{pageName:^[a-zA-Z]{2,}\\w*$}")
	public String toPage(@PathVariable String model, @PathVariable String pageName, HttpServletRequest request)throws Exception{
		Map<String, String[]> params = request.getParameterMap();
		for(Entry<String, String[]> entry : params.entrySet()){
			request.setAttribute(entry.getKey(), (entry.getValue().length==1 ? entry.getValue()[0] : entry.getValue()));
		}
		return String.format("/%s/html/%s", model, pageName);
	}
	
}
