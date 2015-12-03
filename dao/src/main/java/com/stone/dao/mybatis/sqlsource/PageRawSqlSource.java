package com.stone.dao.mybatis.sqlsource;

import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.RawSqlSource;

import com.stone.dao.mybatis.parser.Parser;

/**
 * 原始SQL资源分页处理类
 * @author 赵少泉
 * @date 2015年8月26日 下午12:55:05
 */
public class PageRawSqlSource extends AbstractPageSqlSource {
    private AbstractPageSqlSource sqlSource;
    private SqlSource original;

    public PageRawSqlSource(RawSqlSource sqlSource, Parser parser) {
        MetaObject metaObject = SystemMetaObject.forObject(sqlSource);
        this.sqlSource = new PageStaticSqlSource((StaticSqlSource)metaObject.getValue("sqlSource"), parser);
        this.original = sqlSource;
    }

    @Override
    protected BoundSql getCountBoundSql(Object parameterObject) {
        return sqlSource.getCountBoundSql(parameterObject);
    }

    @Override
    protected BoundSql getPageBoundSql(Object parameterObject) {
        return sqlSource.getPageBoundSql(parameterObject);
    }

    public SqlSource getOriginal() {
        return original;
    }

}