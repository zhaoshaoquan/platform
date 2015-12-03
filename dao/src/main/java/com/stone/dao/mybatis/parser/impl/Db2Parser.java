package com.stone.dao.mybatis.parser.impl;

import java.util.Map;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;

import com.stone.commons.page.Page;

/**
 * DB2分页处理类
 * @author 赵少泉
 * @date 2015年8月26日 下午1:14:21
 */
public class Db2Parser extends AbstractParser{
	@Override
	public String getPageSql(String sql){
		StringBuilder sqlBuilder = new StringBuilder(sql.length() + 120);
		sqlBuilder.append("select * from (select tmp_page.*,rownumber() over() as row_id from ( ");
		sqlBuilder.append(sql);
		sqlBuilder.append(" ) as tmp_page) where row_id between  ? and ?");
		return sqlBuilder.toString();
	}

	@Override
	public Map<String, Object> setPageParameter(MappedStatement ms, Object parameterObject, BoundSql boundSql, Page<?> page){
		Map<String, Object> paramMap = super.setPageParameter(ms, parameterObject, boundSql, page);
		paramMap.put(PAGEPARAMETER_FIRST, page.getStartRow() + 1);
		paramMap.put(PAGEPARAMETER_SECOND, page.getEndRow());
		return paramMap;
	}
}