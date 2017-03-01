package com.stone.dao.mybatis.sqlsource;

import java.util.List;

import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;

import com.stone.dao.mybatis.parser.Parser;

/**
 * 静态SQL资源分页处理类
 * @author 赵少泉
 * @date 2015年8月26日 下午12:53:31
 */
public class PageStaticSqlSource extends AbstractPageSqlSource {
    private String sql;
    private List<ParameterMapping> parameterMappings;
    private Configuration configuration;
    private Parser parser;
    private SqlSource original;

    @SuppressWarnings("unchecked")
	public PageStaticSqlSource(StaticSqlSource sqlSource, Parser parser) {
        MetaObject metaObject = SystemMetaObject.forObject(sqlSource);
        this.sql = (String)metaObject.getValue("sql");
        this.parameterMappings = (List<ParameterMapping>)metaObject.getValue("parameterMappings");
        this.configuration = (Configuration)metaObject.getValue("configuration");
        this.original = sqlSource;
        this.parser = parser;
    }

    @Override
    protected BoundSql getCountBoundSql(Object parameterObject) {
        return new BoundSql(configuration, parser.getCountSql(sql), parameterMappings, parameterObject);
    }

    @Override
    protected BoundSql getPageBoundSql(Object parameterObject) {
        String tempSql = sql;
        tempSql = parser.getPageSql(tempSql);
        return new BoundSql(configuration, tempSql, parser.getPageParameterMapping(configuration, original.getBoundSql(parameterObject)), parameterObject);
    }

    public SqlSource getOriginal() {
        return original;
    }
}
