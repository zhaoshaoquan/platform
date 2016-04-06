package com.stone.dao.comm;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.stone.commons.page.Page;

/**
 * 操作数据对象接口
 */
public interface GeneralDao<E,PK extends Serializable>{
	/**
	 * 保存对象
	 * @param entity
	 * 			持久对象
	 */
	public void save(E entity) throws Exception;
	
	/**
	 * 保存对象
	 * @param entity
	 * 			持久对象，或者对象集合
	 */
	@SuppressWarnings("unchecked")
	public void save(E...entity) throws Exception;
	
	/**
	 * 更新对象数据
	 * @param entity
	 * 			持久对象
	 * @return 最新的数据
	 */
	public void update(E entity) throws Exception;

	/**
	 * 更新对象数据
	 * @param entity
	 *            持久对象，或者对象集合
	 */
	@SuppressWarnings("unchecked")
	public void update(E...entity) throws Exception;
	
	/**
	 * 保存或更新对象数据
	 * @param entity
	 * 			持久对象
	 */
	public void saveOrUpdate(E entity) throws Exception;

	/**
	 * 保存或更新对象数据
	 * @param entity
	 * 			持久对象
	 */
	@SuppressWarnings("unchecked")
	public void saveOrUpdate(E...entity) throws Exception;
	
	/**
	 * 执行ql语句，可以是更新或者删除操作
	 * @param hql
	 *            基于jpa标准的ql语句
	 * @param values
	 *            ql中的?参数值,单个参数值或者多个参数值
	 * @return 返回执行后受影响的数据个数
	 */
	public int executeUpdate(String hql, Object...values)throws Exception;

	/**
	 * 执行ql语句，可以是更新或者删除操作
	 * @param hql
	 *            基于jpa标准的ql语句
	 * @param params
	 *            key表示ql中参数变量名，value表示该参数变量值
	 * @return 返回执行后受影响的数据个数
	 */
	public int executeUpdate(String hql, Map<String, Object> params)throws Exception;

	/**
	 * 执行ql语句，可以是更新或者删除操作
	 * @param hql
	 *            基于jpa标准的ql语句
	 * @param values
	 *            ql中的?参数值
	 * @return 返回执行后受影响的数据个数
	 */
	public int executeUpdate(String hql, List<Object> values)throws Exception;

	/**
	 * 获取指定类型的所有数据对象，大数据量时不推荐使用
	 * @param entityClass
	 *            	类型
	 * @return 返回该类型的所有数据对象
	 */
	public List<E> queryAll()throws Exception;

	/**
	 * 获取指定条件下的数据对象
	 * @param hql
	 *            基于jpa标准的ql语句
	 * @param params
	 *            key表示ql中参数变量名，value表示该参数变量值
	 * @return 返回指定条件下的所有数据对象
	 */
	public <T> List<T> query(String hql, Map<String, Object> params)throws Exception;

	/**
	 * 获取指定条件下的数据对象
	 * @param hql
	 *            基于jpa标准的ql语句
	 * @param values
	 *            ql中的?参数值
	 * @return 返回指定条件下的所有数据对象
	 */
	public <T> List<T> query(String hql, List<Object> values)throws Exception;

	/**
	 * 获取指定条件下的数据对象
	 * @param hql
	 *            基于jpa标准的ql语句
	 * @param values
	 *            ql中的?参数值,单个参数值或者多个参数值
	 * @return 返回指定条件下的所有数据对象
	 */
	public <T> List<T> query(String hql, Object...values)throws Exception;

	/**
	 * 获取指定条件下的数据对象
	 * @param hql
	 *            基于jpa标准的ql语句
	 * @param values
	 *            ql中的?参数值,单个参数值或者多个参数值
	 * @return 返回指定条件下的单个对象
	 */
	public E queryObject(String hql, Object...values)throws Exception;

	/**
	 * 分页查询，获取满足条件下的对象
	 * @param page
	 * 				分页信息
	 * @throws Exception
	 */
	public <T> void queryPager(Page<T> page)throws Exception;
	
	/**
	 * 分页查询，获取满足条件下的对象
	 * @param page
	 * 				分页信息
	 * @param cacheable
	 * 				是否启用缓存查询
	 * @throws Exception
	 */
	public <T> void queryPager(Page<T> page, boolean cacheable)throws Exception;
	
	/**
	 * 分页查询，获取满足条件下的对象
	 * @param page
	 * 				分页信息
	 * @param hql
	 * 				 基于jpa标准的hql语句
	 * @throws Exception
	 */
	public <T> void queryPager(Page<T> page, String hql)throws Exception;
	
	/**
	 * 分页查询，获取满足条件下的对象
	 * @param page
	 * 				分页信息
	 * @param hql
	 * 				基于jpa标准的hql语句
	 * @param cacheable
	 * 				是否启用缓存查询
	 * @throws Exception
	 */
	public <T> void queryPager(Page<T> page, String hql, boolean cacheable)throws Exception;
	
	/**
	 * 分页查询，获取满足条件下的对象
	 * @param page
	 * 				分页信息
	 * @param hql
	 * 				基于jpa标准的hql语句
	 * @param values
	 * 				hql中的?参数值
	 * @throws Exception
	 */
	public <T> void queryPager(Page<T> page, String hql, Object...values)throws Exception;
	
	/**
	 * 分页查询，获取满足条件下的对象
	 * @param page
	 * 				分页信息
	 * @param hql
	 * 				基于jpa标准的hql语句
	 * @param cacheable
	 * 				是否启用缓存查询
	 * @param values
	 * 				hql中的?参数值
	 * @throws Exception
	 */
	public <T> void queryPager(Page<T> page, String hql, boolean cacheable, Object...values)throws Exception;
	
	/**
	 * 分页查询，获取满足条件下的对象
	 * @param page
	 * 				分页信息
	 * @param hql
	 * 				基于jpa标准的hql语句
	 * @param values
	 * 				hql中的?参数值
	 * @throws Exception
	 */
	public <T> void queryPager(Page<T> page, String hql, List<Object> values)throws Exception;
	
	/**
	 * 分页查询，获取满足条件下的对象
	 * @param page
	 * 				分页信息
	 * @param hql
	 * 				基于jpa标准的hql语句
	 * @param values
	 * 				hql中的?参数值
	 * @param cacheable
	 * 				是否启用缓存查询
	 * @throws Exception
	 */
	public <T> void queryPager(Page<T> page, String hql, List<Object> values, boolean cacheable)throws Exception;
	
	/**
	 * 分页查询，获取满足条件下的对象
	 * @param page
	 * 				分页信息
	 * @param hql
	 * 				基于jpa标准的hql语句
	 * @param params
	 * 				hql中的:参数值
	 * @throws Exception
	 */
	public <T> void queryPager(Page<T> page, String hql, Map<String, Object> params)throws Exception;
	
	/**
	 * 分页查询，获取满足条件下的对象
	 * @param page
	 * 				分页信息
	 * @param hql
	 * 				基于jpa标准的hql语句
	 * @param params
	 * 				hql中的:参数值
	 * @param cacheable
	 * 				是否启用缓存查询
	 * @throws Exception
	 */
	public <T> void queryPager(Page<T> page, String hql, Map<String, Object> params, boolean cacheable)throws Exception;

	/**
	 * 结合对象主键id值获取该数据对象
	 * @param id
	 *          主键值
	 * @return 
	 * 			数据对象或者null
	 */
	public E find(PK id)throws Exception;

	/**
	 * 批量结合对象主键id值获取该数据对象
	 * @param ids
	 *          主键值
	 * @return 
	 * 			数据对象或者null
	 */
	public List<E> batchFind(Object...ids)throws Exception;
	
	/**
	 * 删除数据对象
	 * @param entity
	 * 			删除的对象
	 * @return
	 * 			删除成功数
	 */
	public int delete(E entity)throws Exception;

	/**
	 * 批量删除数据对象
	 * @param ids
	 * 			主键值
	 * @return
	 * 			删除成功记录数
	 */
	@SuppressWarnings("unchecked")
	public int batchDelete(PK...ids)throws Exception;

	/**
	 * 删除数据对象
	 * @param entity
	 * 			删除的对象
	 */
	public void remove(E entity)throws Exception;
}
