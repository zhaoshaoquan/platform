package com.stone.demo.mapper;

import java.util.List;

import com.stone.commons.page.Page;
import com.stone.demo.domain.Demo;

public interface DemoMapper{
	public List<Demo> selectAll();
	public List<Demo> selectByPage(Page<Demo> page);
}
