package com.stone.demo.dao;

import java.util.List;

import com.stone.dao.comm.GeneralDao;
import com.stone.demo.domain.Demo;

public interface DemoDao extends GeneralDao<Demo, Integer>{
	public List<Demo> selectByName(String name)throws Exception;
}
