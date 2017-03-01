package com.stone.dao.mybatis.sqlsource;

import java.util.Map;

import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.session.Configuration;

import com.stone.dao.mybatis.Constant;
import com.stone.dao.mybatis.parser.Parser;

/**
 * 动态SQL资源分页处理类
 * @author 赵少泉
 * @date 2015年8月26日 下午1:01:42
 */
public class PageDynamicSqlSource extends AbstractPageSqlSource implements /*OrderBySqlSource,*/ Constant {
    private Configuration configuration;
    private SqlNode rootSqlNode;
    private SqlSource original;
    private Parser parser;

    public PageDynamicSqlSource(DynamicSqlSource sqlSource, Parser parser) {
        MetaObject metaObject = SystemMetaObject.forObject(sqlSource);
        this.configuration = (Configuration)metaObject.getValue("configuration");
        this.rootSqlNode = (SqlNode)metaObject.getValue("rootSqlNode");
        this.original = sqlSource;
        this.parser = parser;
    }

    @Override
    protected BoundSql getCountBoundSql(Object parameterObject) {
		DynamicContext context = new DynamicContext(configuration, parameterObject);
		rootSqlNode.apply(context);
		SqlSourceBuilder sqlSourceParser = new SqlSourceBuilder(configuration);
		Class<?> parameterType = parameterObject == null ? Object.class : parameterObject.getClass();
		SqlSource sqlSource = sqlSourceParser.parse(context.getSql(), parameterType, context.getBindings());
		BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
		sqlSource = new StaticSqlSource(configuration, parser.getCountSql(boundSql.getSql()), boundSql.getParameterMappings());
		boundSql = sqlSource.getBoundSql(parameterObject);
		//设置条件参数
		for(Map.Entry<String, Object> entry : context.getBindings().entrySet()){
			boundSql.setAdditionalParameter(entry.getKey(), entry.getValue());
		}
		return boundSql;
    }

    @SuppressWarnings("rawtypes")
	@Override
    protected BoundSql getPageBoundSql(Object parameterObject) {
		DynamicContext context = null;
		//由于增加分页参数后会修改parameterObject的值，因此在前面处理时备份该值
		//如果发现参数是Map并且包含该KEY，就使用备份的该值
		if(parameterObject != null && parameterObject instanceof Map && ((Map)parameterObject).containsKey(ORIGINAL_PARAMETER_OBJECT)){
			context = new DynamicContext(configuration, ((Map)parameterObject).get(ORIGINAL_PARAMETER_OBJECT));
		}else{
			context = new DynamicContext(configuration, parameterObject);
		}
		rootSqlNode.apply(context);
		SqlSourceBuilder sqlSourceParser = new SqlSourceBuilder(configuration);
		Class<?> parameterType = parameterObject == null ? Object.class : parameterObject.getClass();
		SqlSource sqlSource = sqlSourceParser.parse(context.getSql(), parameterType, context.getBindings());
		BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
		sqlSource = new StaticSqlSource(configuration, parser.getPageSql(boundSql.getSql()), parser.getPageParameterMapping(configuration, boundSql));
		boundSql = sqlSource.getBoundSql(parameterObject);
		//设置条件参数
		for(Map.Entry<String, Object> entry : context.getBindings().entrySet()){
			boundSql.setAdditionalParameter(entry.getKey(), entry.getValue());
		}
		return boundSql;
    }

    public SqlSource getOriginal() {
        return original;
    }

}