package com.stone.config.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class ConfigController {

	@RequestMapping(value="404")
	public String _404() throws Exception{
		return "/404";
	}

}
