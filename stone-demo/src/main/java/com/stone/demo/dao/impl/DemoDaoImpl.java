package com.stone.demo.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.stone.dao.jpa.AbstractJpaGeneralDao;
import com.stone.demo.dao.DemoDao;
import com.stone.demo.domain.Demo;

@Repository
public class DemoDaoImpl extends AbstractJpaGeneralDao<Demo, Integer> implements DemoDao {

	@Override
	public List<Demo> selectByName(String name) throws Exception{
		String hql = "select o from Demo o where o.name like ?";
		return query(hql, "%"+name+"%");
	}

	@Override
	public Class<Demo> entityClass(){
		return Demo.class;
	}
	
}
