package com.stone.dao.mybatis.parser.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;

import com.stone.commons.page.Page;
import com.stone.dao.mybatis.Constant;
import com.stone.dao.mybatis.Dialect;
import com.stone.dao.mybatis.parser.Parser;
import com.stone.dao.mybatis.parser.SqlParser;
import com.stone.dao.mybatis.sqlsource.PageProviderSqlSource;

public abstract class AbstractParser implements Parser, Constant {
    //处理SQL
    public static final SqlParser sqlParser = new SqlParser();

    public static Parser newParser(Dialect dialect) {
        Parser parser = null;
        switch (dialect) {
            case mysql:
            case mariadb:
            case sqlite:
                parser = new MysqlParser();
                break;
            case oracle:
                parser = new OracleParser();
                break;
            case hsqldb:
                parser = new HsqldbParser();
                break;
            case sqlserver:
                parser = new SqlServerParser();
                break;
            case db2:
                parser = new Db2Parser();
                break;
            case postgresql:
                parser = new PostgreSQLParser();
                break;
            case informix:
                parser = new InformixParser();
                break;
            default:
                break;
        }
        return parser;
    }

    @Override
    public boolean isSupportedMappedStatementCache() {
        return true;
    }

    public String getCountSql(final String sql) {
        return sqlParser.getSmartCountSql(sql);
    }

    public abstract String getPageSql(String sql);

    public List<ParameterMapping> getPageParameterMapping(Configuration configuration, BoundSql boundSql) {
        List<ParameterMapping> newParameterMappings = new ArrayList<ParameterMapping>();
        if(boundSql != null && boundSql.getParameterMappings() != null){
            newParameterMappings.addAll(boundSql.getParameterMappings());
        }
        newParameterMappings.add(new ParameterMapping.Builder(configuration, PAGEPARAMETER_FIRST, Long.class).build());
        newParameterMappings.add(new ParameterMapping.Builder(configuration, PAGEPARAMETER_SECOND, Integer.class).build());
        return newParameterMappings;
    }
    
    @SuppressWarnings("unchecked")
	public Map<String, Object> processParameter(MappedStatement ms, Object parameterObject, BoundSql boundSql) {
        Map<String, Object> paramMap = null;
        if(parameterObject == null){
            paramMap = new HashMap<String, Object>();
        }else if(parameterObject instanceof Map){
            //解决不可变Map的情况
            paramMap = new HashMap<String, Object>();
			paramMap.putAll((Map<String, Object>)parameterObject);
        }else{
            paramMap = new HashMap<String, Object>();
            //动态sql时的判断条件不会出现在ParameterMapping中，但是必须有，所以这里需要收集所有的getter属性
            //TypeHandlerRegistry可以直接处理的会作为一个直接使用的对象进行处理
            boolean hasTypeHandler = ms.getConfiguration().getTypeHandlerRegistry().hasTypeHandler(parameterObject.getClass());
            MetaObject metaObject = SystemMetaObject.forObject(parameterObject);
            //需要针对注解形式的MyProviderSqlSource保存原值
            if(ms.getSqlSource() instanceof PageProviderSqlSource){
                paramMap.put(PROVIDER_OBJECT, parameterObject);
            }
            if(!hasTypeHandler){
                for(String name : metaObject.getGetterNames()){
                    paramMap.put(name, metaObject.getValue(name));
                }
            }
            //下面这段方法，主要解决一个常见类型的参数时的问题
            if(boundSql.getParameterMappings() != null && boundSql.getParameterMappings().size() > 0){
                for(ParameterMapping parameterMapping : boundSql.getParameterMappings()){
                    String name = parameterMapping.getProperty();
                    if(!name.equals(PAGEPARAMETER_FIRST) && !name.equals(PAGEPARAMETER_SECOND) && paramMap.get(name) == null) {
                        if(hasTypeHandler || parameterMapping.getJavaType().equals(parameterObject.getClass())){
                            paramMap.put(name, parameterObject);
                            break;
                        }
                    }
                }
            }
        }
        //备份原始参数对象
        paramMap.put(ORIGINAL_PARAMETER_OBJECT, parameterObject);
        return paramMap;
    }

    public Map<String, Object> setPageParameter(MappedStatement ms, Object parameterObject, BoundSql boundSql, Page<?> page) {
        return processParameter(ms, parameterObject, boundSql);
    }
}
