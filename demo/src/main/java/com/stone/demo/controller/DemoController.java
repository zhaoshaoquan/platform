package com.stone.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stone.commons.page.Page;
import com.stone.demo.consist.DemoConsist;
import com.stone.demo.domain.Demo;
import com.stone.demo.service.DemoService;

@Controller
@RequestMapping("/demo")
public class DemoController{
	@Autowired
	private DemoService demoService;
	
	@RequestMapping(value="index")
	public String index(HttpServletRequest request)throws Exception{
		Page<Demo> page = new Page<>(1, 20);
		page.addParams("name", "aa");
		demoService.selectByPage(page);
		for(Demo demo : page.getResult()){
			System.out.println(demo.getId() +"======"+ demo.getName());
		}
		return DemoConsist.toHtmlPage("index");
	}
	
	@RequestMapping(value="welcome")
	public String welcome(HttpServletRequest request)throws Exception{
		List<Demo> list = demoService.selectAll();
		for(Demo demo : list){
			System.out.println(demo.getId() +"======"+ demo.getName());
		}
		return DemoConsist.toJspPage("welcome");
	}
	
}
