package com.stone.demo.service;

import java.util.List;

import com.stone.commons.page.Page;
import com.stone.demo.domain.Demo;

public interface DemoService{
	
	public Demo findById(Integer id)throws Exception;
	
	public List<Demo> selectAll()throws Exception;
	
	public List<Demo> selectByName(String name)throws Exception;
	
	public void selectByPage(Page<Demo> page)throws Exception;
	
	public Integer save(Demo demo)throws Exception;
	
	public void update(Demo demo)throws Exception;
	
	public boolean delete(Demo demo)throws Exception;
	
}
