package com.stone.dao.mybatis;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.stone.commons.page.Page;
import com.stone.dao.mybatis.parser.Parser;
import com.stone.dao.mybatis.parser.impl.AbstractParser;
import com.stone.dao.mybatis.sqlsource.AbstractPageSqlSource;
import com.stone.dao.mybatis.sqlsource.PageDynamicSqlSource;
import com.stone.dao.mybatis.sqlsource.PageProviderSqlSource;
import com.stone.dao.mybatis.sqlsource.PageRawSqlSource;
import com.stone.dao.mybatis.sqlsource.PageStaticSqlSource;

/**
 * 分页拦截器
 * @author 赵少泉
 * @date 2015年8月26日 下午1:51:01
 */
@Intercepts(@Signature(type=Executor.class,method="query",args={MappedStatement.class,Object.class,RowBounds.class,ResultHandler.class}))
public class PagePlugin implements Interceptor {
	
	private Parser parser = null;
	private final Map<String, MappedStatement> msCountMap = new ConcurrentHashMap<String, MappedStatement>();
    private static final ThreadLocal<Boolean> COUNT = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return null;
        }
    };

	@Override
	public Object intercept(Invocation invocation) throws Throwable{
		Object[] args = invocation.getArgs();
		MappedStatement ms = (MappedStatement)args[0];
		if(args[1] instanceof Page<?>){
			Page<?> page = (Page<?>)args[1];
			processMappedStatement(ms, parser);
			COUNT.set(Boolean.TRUE);
            args[0] = msCountMap.get(ms.getId());
            List<?> countList = (List<?>)invocation.proceed();
            page.setTotal((Long)countList.get(0));
            
            args[0] = ms;
            args[2] = RowBounds.DEFAULT;
            COUNT.set(Boolean.FALSE);
            BoundSql boundSql = ms.getBoundSql(args[1]);
            args[1] = parser.setPageParameter(ms, args[1], boundSql, page);
			Object result = invocation.proceed();
			page.setResult((List<?>)result);
			return result;
		}else{
			return invocation.proceed();
		}
	}

	@Override
	public Object plugin(Object target){
		if(target instanceof Executor){
            return Plugin.wrap(target, this);
        }else{
            return target;
        }
	}

	@Override
	public void setProperties(Properties properties){
		String dialect = properties.getProperty("dialect", "mysql");
		parser = AbstractParser.newParser(Dialect.of(dialect));
	}
	
	public static Boolean getCOUNT() {
		return COUNT.get();
	}
	
	public void processMappedStatement(MappedStatement ms, Parser parser)throws Throwable{
		SqlSource pageSqlSource = null;
		SqlSource sqlSource = ms.getSqlSource();
		MetaObject msObject = SystemMetaObject.forObject(ms);
		if(sqlSource instanceof StaticSqlSource){
			pageSqlSource = new PageStaticSqlSource((StaticSqlSource)sqlSource, parser);
		}else if(sqlSource instanceof RawSqlSource){
			pageSqlSource = new PageRawSqlSource((RawSqlSource)sqlSource, parser);
		}else if(sqlSource instanceof ProviderSqlSource){
			pageSqlSource = new PageProviderSqlSource((ProviderSqlSource)sqlSource, parser);
		}else if(sqlSource instanceof DynamicSqlSource){
			pageSqlSource = new PageDynamicSqlSource((DynamicSqlSource)sqlSource, parser);
		}else if(sqlSource instanceof AbstractPageSqlSource){//如果第二次缓存后直接使用sqlSource对象
			pageSqlSource = sqlSource;
		}else{
			throw new RuntimeException("SqlSource type error[" +sqlSource.getClass()+ "]");
		}
		msObject.setValue("sqlSource", pageSqlSource);
		msCountMap.put(ms.getId(), MappedStatementUtils.newCountMappedStatement(ms));
	}
	
}
