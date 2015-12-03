package com.stone.dao.mybatis.parser.impl;

import java.util.Map;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;

import com.stone.commons.page.Page;

/**
 * Hsqldb分页处理类
 * @author 赵少泉
 * @date 2015年8月26日 下午1:14:30
 */
public class HsqldbParser extends AbstractParser{
	@Override
	public String getPageSql(String sql){
		StringBuilder sqlBuilder = new StringBuilder(sql.length() + 20);
		sqlBuilder.append(sql);
		sqlBuilder.append(" limit ? offset ?");
		return sqlBuilder.toString();
	}

	@Override
	public Map<String, Object> setPageParameter(MappedStatement ms, Object parameterObject, BoundSql boundSql, Page<?> page){
		Map<String, Object> paramMap = super.setPageParameter(ms, parameterObject, boundSql, page);
		paramMap.put(PAGEPARAMETER_FIRST, page.getPageSize());
		paramMap.put(PAGEPARAMETER_SECOND, page.getStartRow());
		return paramMap;
	}
}