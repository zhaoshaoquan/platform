package com.stone.demo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stone.commons.page.Page;
import com.stone.demo.dao.DemoDao;
import com.stone.demo.domain.Demo;
import com.stone.demo.mapper.DemoMapper;
import com.stone.demo.service.DemoService;

@Service
@Transactional
public class DemoServiceImpl implements DemoService{
	@Resource
	private DemoDao demoDao;
	@Autowired
	private DemoMapper demoMapper;

	@Override
	public Demo findById(Integer id) throws Exception{
		return demoDao.find(id);
	}

	@Override
	public List<Demo> selectAll() throws Exception{
		return demoMapper.selectAll();
	}
	
	@Override
	public List<Demo> selectByName(String name) throws Exception{
		return demoDao.selectByName(name);
	}
	
	@Override
	public void selectByPage(Page<Demo> page) throws Exception{
		demoMapper.selectByPage(page);
	}
	
	@Override
	public Integer save(Demo demo) throws Exception{
		demoDao.save(demo);
		return demo.getId();
	}

	@Override
	public void update(Demo demo) throws Exception{
		demoDao.update(demo);
	}

	@Override
	public boolean delete(Demo demo) throws Exception{
		demoDao.remove(demo);
		return true;
	}

}
