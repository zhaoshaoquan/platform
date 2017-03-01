package com.stone.dao.mybatis.parser.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.Configuration;

import com.stone.commons.page.Page;

/**
 * informix分页处理类
 * @author 赵少泉
 * @date 2015年8月26日 下午1:14:36
 */
public class InformixParser extends AbstractParser{
	@Override
	public String getPageSql(String sql){
		StringBuilder sqlBuilder = new StringBuilder(sql.length() + 40);
		sqlBuilder.append("select skip ? first ? * from ( ");
		sqlBuilder.append(sql);
		sqlBuilder.append(" ) temp_t");
		return sqlBuilder.toString();
	}

	@Override
	public List<ParameterMapping> getPageParameterMapping(Configuration configuration, BoundSql boundSql){
		List<ParameterMapping> newParameterMappings = new ArrayList<ParameterMapping>();
		newParameterMappings.add(new ParameterMapping.Builder(configuration, PAGEPARAMETER_FIRST, Integer.class).build());
		newParameterMappings.add(new ParameterMapping.Builder(configuration, PAGEPARAMETER_SECOND, Integer.class).build());
		if(boundSql.getParameterMappings() != null){
			newParameterMappings.addAll(boundSql.getParameterMappings());
		}
		return newParameterMappings;
	}

	@Override
	public Map<String, Object> setPageParameter(MappedStatement ms, Object parameterObject, BoundSql boundSql, Page<?> page){
		Map<String, Object> paramMap = super.setPageParameter(ms, parameterObject, boundSql, page);
		paramMap.put(PAGEPARAMETER_FIRST, page.getStartRow());
		paramMap.put(PAGEPARAMETER_SECOND, page.getPageSize());
		return paramMap;
	}
}