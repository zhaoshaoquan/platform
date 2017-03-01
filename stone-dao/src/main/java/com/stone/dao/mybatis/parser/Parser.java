package com.stone.dao.mybatis.parser;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.Configuration;

import com.stone.commons.page.Page;

/**
 * 处理SQL接口
 * @author 赵少泉
 * @date 2015年8月26日 上午10:50:49
 */
public interface Parser {

    /**
     * 是否支持MappedStatement全局缓存
     *
     * @return
     */
    public boolean isSupportedMappedStatementCache();

    /**
     * 获取总数sql - 如果要支持其他数据库，修改这里就可以
     *
     * @param sql 原查询sql
     * @return 返回count查询sql
     */
    public String getCountSql(String sql);

    /**
     * 获取分页sql - 如果要支持其他数据库，修改这里就可以
     *
     * @param sql 原查询sql
     * @return 返回分页sql
     */
    public String getPageSql(String sql);

    /**
     * 获取分页参数映射
     *
     * @param configuration
     * @param boundSql
     * @return
     */
    public List<ParameterMapping> getPageParameterMapping(Configuration configuration, BoundSql boundSql);

    /**
     * 设置分页参数
     *
     * @param ms
     * @param parameterObject
     * @param boundSql
     * @param page
     * @return
     */
    public Map<String,Object> setPageParameter(MappedStatement ms, Object parameterObject, BoundSql boundSql, Page<?> page);
}
