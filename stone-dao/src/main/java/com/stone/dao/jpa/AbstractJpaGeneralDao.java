package com.stone.dao.jpa;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.QueryHints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.commons.page.Order;
import com.stone.commons.page.Page;
import com.stone.dao.comm.CglibBean;
import com.stone.dao.comm.GeneralDao;
import com.stone.dao.comm.PageHandle;

public abstract class AbstractJpaGeneralDao<E, PK extends Serializable> implements GeneralDao<E, PK> {
	private static Logger log = LoggerFactory.getLogger(AbstractJpaGeneralDao.class);
	@PersistenceContext(unitName="entityManager")
	private EntityManager entityManager;
	
	
	protected EntityManager getEntityManager() {
		return entityManager;
	}
	
	/**
	 * 获取当前实体类的Class
	 * @return
	 * 			实体类Class
	 */
	public abstract Class<E> entityClass();
	
	protected String hql(){
		return String.format("SELECT o FROM %s o", entityClass().getSimpleName());
	}
	
	@Override
	public void save(E entity) throws Exception{
		if(entity != null){
			entityManager.persist(entity);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void save(E...entity) throws Exception{
		if(entity == null)return;
		for(E e : entity){
			entityManager.persist(e);
		}
	}

	@Override
	public void update(E entity) throws Exception{
		if(entity != null){
			entityManager.merge(entity);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void update(E...entity) throws Exception{
		if(entity == null)return;
		for(E e : entity){
			entityManager.merge(e);
		}
	}

	@Override
	public void saveOrUpdate(E entity) throws Exception{
		if(entity != null){
			entityManager.merge(entity);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void saveOrUpdate(E...entity) throws Exception{
		if(entity == null)return;
		for(E e : entity){
			entityManager.merge(e);
		}
	}
	
	@Override
	public int executeUpdate(String hql, Object...values) throws Exception{
		Query query = entityManager.createQuery(hql);
		if(values != null){
			for(int i = 0; i < values.length; i++){
				query.setParameter(i+1, values[i]);
			}
		}
		return query.executeUpdate();
	}

	@Override
	public int executeUpdate(String hql, Map<String, Object> params) throws Exception{
		Query query = entityManager.createQuery(hql);
		for(String name : params.keySet()){
			query.setParameter(name, params.get(name));
		}
		return query.executeUpdate();
	}

	@Override
	public int executeUpdate(String hql, List<Object> values) throws Exception{
		Query query = entityManager.createQuery(hql);
		if(values != null){
			for(int i = 0; i < values.size(); i++){
				query.setParameter(i+1, values.get(i));
			}
		}
		return query.executeUpdate();
	}

	@Override
	public List<E> queryAll() throws Exception{
		return (List<E>)entityManager.createQuery(hql(), entityClass()).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> query(String hql, Map<String, Object> params) throws Exception{
		Query query = entityManager.createQuery(hql);
		if(params != null){
			for(String key : params.keySet()){
				query.setParameter(key, params.get(key));
			}
		}
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> query(String hql, List<Object> values) throws Exception{
		Query query = entityManager.createQuery(hql);
		if(values != null){
			for(int i = 0; i < values.size(); i++){
				query.setParameter(i+1, values.get(i));
			}
		}
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> query(String hql, Object...values) throws Exception{
		Query query = entityManager.createQuery(hql);
		if(values != null){
			for(int i = 0; i < values.length; i++){
				query.setParameter(i+1, values[i]);
			}
		}
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public E queryObject(String hql, Object...values) throws Exception{
		Query query = entityManager.createQuery(hql);
		if(values != null){
			for(int i = 0; i < values.length; i++){
				query.setParameter(i+1, values[i]);
			}
		}
		return (E)query.getSingleResult();
	}

	public <T> void queryPager(Page<T> page)throws Exception{
		this.queryPager(page, true);
	}
	
	public <T> void queryPager(Page<T> page, boolean cacheable)throws Exception{
		this.queryPager(page, hql(), cacheable);
	}
	
	public <T> void queryPager(Page<T> page, String hql)throws Exception{
		this.queryPager(page, hql, true);
	}
	
	public <T> void queryPager(Page<T> page, String hql, boolean cacheable)throws Exception{
		this.queryPager(page, hql, new ArrayList<Object>(), cacheable);
	}
	
	public <T> void queryPager(Page<T> page, String hql, Object...values)throws Exception{
		this.queryPager(page, hql, true, values);
	}
	
	public <T> void queryPager(Page<T> page, String hql, boolean cacheable, Object...values)throws Exception{
		if(values != null){
			this.queryPager(page, hql, Arrays.asList(values), cacheable);
		}else{
			this.queryPager(page, hql, new ArrayList<Object>(), cacheable);
		}
	}
	
	public <T> void queryPager(Page<T> page, String hql, List<Object> values)throws Exception{
		this.queryPager(page, hql, values, true);
	}
	
	public <T> void queryPager(Page<T> page, String hql, List<Object> values, boolean cacheable)throws Exception{
		if(values == null){
			values = new ArrayList<Object>();
		}
		hql = PageHandle.convertHQL(hql);
		String countHql = PageHandle.preCountJPQL(hql);
		executeCount(page, countHql, values, cacheable);
		executeList(page, hql, values, cacheable);
	}
	
	public <T> void queryPager(Page<T> page, String hql, Map<String, Object> params)throws Exception{
		this.queryPager(page, hql, params, true);
	}
	
	public <T> void queryPager(Page<T> page, String hql, Map<String, Object> params, boolean cacheable)throws Exception{
		List<Object> values = new ArrayList<Object>();
		hql = PageHandle.preHQLAndParam(hql, params, values);
		this.queryPager(page, hql, values, cacheable);
	}
	
	@Override
	public E find(PK id) throws Exception{
		return (E)entityManager.find(entityClass(), id);
	}

	@Override
	public List<E> batchFind(Object...ids) throws Exception{
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<E> query = cb.createQuery(entityClass());
		Root<E> systemBackup = query.from(entityClass());
		Path<String> id = systemBackup.get(idName());
		query.select(systemBackup).where(id.in(ids));
		return entityManager.createQuery(query).getResultList();
	}

	@Override
	public int delete(E entity) throws Exception{
		Query query = entityManager.createQuery(String.format("DELETE FROM %s WHERE %s=?", entityClass().getSimpleName(), idName()));
		query.setParameter(1, idField(entityClass()).get(entity));
		return query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int batchDelete(PK...ids) throws Exception{
		if(ids == null || ids.length < 1){
			return 0;
		}
		StringBuilder str = new StringBuilder();
		for(int i = 0; i < ids.length; i++){
			str.append("?").append(i+1).append(((i<ids.length)?",":""));
		}
		Query query = entityManager.createQuery(String.format("DELETE FROM %s WHERE %s=(%s)", entityClass().getSimpleName(), idName(), str));
		for(int i = 0; i < ids.length; i++){
			query.setParameter(i+1, ids[i]);
		}
		return query.executeUpdate();
	}

	@Override
	public void remove(E entity) throws Exception{
		entityManager.remove(entityManager.merge(entity));
	}
	
	/**
	 * 执行集合语句
	 */
	@SuppressWarnings("unchecked")
	protected <T> void executeList(Page<T> page, String listHql, List<Object> values, boolean cacheable){
		if(page.getOrders().size() > 0){
			StringBuilder os = new StringBuilder(" ORDER BY ");
			for(Order order : page.getOrders()){
				os.append(order.getPropertyName()).append(" ").append(order.getOrderType()).append(",");
			}
			listHql = listHql + StringUtils.removeEnd(os.toString(), ",");
		}
		Query query = entityManager.createQuery(listHql);
		query.setHint(QueryHints.CACHEABLE, cacheable);
		for(int i = 0; i < values.size(); i++){
			PageHandle.setParameter(query, i+1, values.get(i));
		}
		int firstResult = (page.getPageNumber() - 1) * page.getPageSize();
		int maxResults = page.getPageSize();
		query.setFirstResult(firstResult).setMaxResults(maxResults);
		List<T> list = query.getResultList();
		if(list.size() > 0){
			Object item = list.get(0);
			if(item.getClass().isArray()){
				String[] fieldArray = PageHandle.preFieldInfo(listHql);
				Map<String, Class<?>> propertyMap = PageHandle.preProp(fieldArray, (Object[])list.get(0));
				List<T> items = new ArrayList<T>();
				for(Object object : list){
					Object[] entity = (Object[])object;
					CglibBean bean = new CglibBean(propertyMap);
					for(int i = 0; i < fieldArray.length; i++){
						bean.setValue(fieldArray[i], entity[i]);
					}
					items.add((T)bean.getObject());
				}
				list = items;
			}
		}
		page.setResult(list);
//		entityManager.clear();
	}
	
	/**
	 * 执行统计语句
	 */
	protected <T> void executeCount(Page<T> page, String count_ql, List<Object> values, boolean cacheable){
		Query query = entityManager.createQuery(count_ql);
		query.setHint(QueryHints.CACHEABLE, cacheable);
		for(int i = 0; i < values.size(); i++){
			PageHandle.setParameter(query, i+1, values.get(i));
		}
		List<?> list = query.getResultList();
		if(list.size() == 1){
			int totalRows = Integer.parseInt(list.get(0).toString());
			page.setTotal(totalRows);
			log.debug("executeCount totalRows = {}", totalRows);
		}else{
			page.setTotal(list.size());
		}
	}
	
	private String idName() {
		Field idField = idField(entityClass());
		if(idField != null){
			return idField.getName();
		}
		return null;
	}
	
	private Field idField(Class<?> clazz) {
		Field item = null;
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields){
			Id id = field.getAnnotation(Id.class);
			if(id != null){
				field.setAccessible(true);
				item = field;
				break;
			}
		}
		if(item == null){
			Class<?> superclass = clazz.getSuperclass();
			if(superclass != null){
				item = idField(superclass);
			}
		}
		return item;
	}
	
}
