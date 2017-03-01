package com.stone.dao.mybatis;

/**
 * @author 赵少泉
 * @date 2015年8月26日 上午11:00:11
 */
public interface Constant{
	/**分页的id后缀*/
	public String SUFFIX_PAGE = "_PageHelper";
	/**count查询的id后缀*/
	public String SUFFIX_COUNT = SUFFIX_PAGE + "_Count";
	/**第一个分页参数*/
	public String PAGEPARAMETER_FIRST = "First" + SUFFIX_PAGE;
	/**第二个分页参数*/
	public String PAGEPARAMETER_SECOND = "Second" + SUFFIX_PAGE;
	/***/
	public String PROVIDER_OBJECT = "_provider_object";
	/**存储原始的参数*/
	public String ORIGINAL_PARAMETER_OBJECT = "_ORIGINAL_PARAMETER_OBJECT";
}
