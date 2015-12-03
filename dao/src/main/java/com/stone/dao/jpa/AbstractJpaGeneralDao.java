package com.stone.dao.jpa;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
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

import org.hibernate.annotations.QueryHints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.commons.page.Page;
import com.stone.dao.comm.CglibBean;
import com.stone.dao.comm.GeneralDao;
import com.stone.dao.comm.PageHandle;

public abstract class AbstractJpaGeneralDao<E, PK extends Serializable> implements GeneralDao<E, PK> {
	private static Logger log = LoggerFactory.getLogger(AbstractJpaGeneralDao.class);
	@SuppressWarnings("unchecked")
	private Class<E> entityClass = (Class<E>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	@PersistenceContext(unitName="entityManager")
	protected EntityManager entityManager;
	
	
	protected EntityManager getEntityManager() {
		return entityManager;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void save(E...entity) throws Exception{
		if(entity == null)return;
		for(E e : entity){
			getEntityManager().persist(e);
		}
	}

	@Override
	public void save(E entity) throws Exception{
		if(entity == null)return;
		getEntityManager().persist(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(E...entity) throws Exception{
		if(entity == null)return;
		for(E e : entity){
			getEntityManager().merge(e);
		}
	}

	@Override
	public void update(E entity) throws Exception{
		if(entity == null)return;
		getEntityManager().merge(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveOrUpdate(E...entity) throws Exception{
		if(entity == null)return;
		for(E e : entity){
			getEntityManager().merge(e);
		}
	}
	
	@Override
	public void saveOrUpdate(E entity) throws Exception{
		if(entity == null)return;
		getEntityManager().merge(entity);
	}
	
	@Override
	public int executeUpdate(String hql, Object...values) throws Exception{
		Query query = getEntityManager().createQuery(hql);
		if(values != null){
			for(int i = 0; i < values.length; i++){
				query.setParameter(i+1, values[i]);
			}
		}
		return query.executeUpdate();
	}

	@Override
	public int executeUpdate(String hql, Map<String, Object> params) throws Exception{
		Query query = getEntityManager().createQuery(hql);
		for(String name : params.keySet()){
			query.setParameter(name, params.get(name));
		}
		return query.executeUpdate();
	}

	@Override
	public int executeUpdate(String hql, List<Object> values) throws Exception{
		Query query = getEntityManager().createQuery(hql);
		if(values != null){
			for(int i = 0; i < values.size(); i++){
				query.setParameter(i+1, values.get(i));
			}
		}
		return query.executeUpdate();
	}

	@Override
	public List<E> queryAll() throws Exception{
		String hql = "SELECT o FROM " + entityClass.getSimpleName() + " o";
		return (List<E>)getEntityManager().createQuery(hql, entityClass).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> query(String hql, Map<String, Object> params) throws Exception{
		Query query = getEntityManager().createQuery(hql);
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
		Query query = getEntityManager().createQuery(hql);
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
		Query query = getEntityManager().createQuery(hql);
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
		Query query = getEntityManager().createQuery(hql);
		if(values != null){
			for(int i = 0; i < values.length; i++){
				query.setParameter(i+1, values[i]);
			}
		}
		return (E)query.getSingleResult();
	}

	@Override
	public <T> void queryPager(Page<T> page, String hql) throws Exception{
		queryPager(page, hql, page.getParams());
	}

	@Override
	public <T> void queryPager(Page<T> page, String hql, boolean cacheable) throws Exception{
		queryPager(page, hql, new ArrayList<Object>(), cacheable);
	}

	@Override
	public <T> void queryPager(Page<T> page, String hql, Object...values) throws Exception{
		queryPager(page, hql, Arrays.asList(values));
	}

	@Override
	public <T> void queryPager(Page<T> page, String hql, List<Object> values) throws Exception{
		queryPager(page, hql, values, false);
	}

	@Override
	public <T> void queryPager(Page<T> page, String hql, Map<String, Object> params) throws Exception{
		List<Object> values = new ArrayList<Object>();
		hql = PageHandle.preQLAndParam(hql, params, values);
		queryPager(page, hql, values);
	}
	
	private <T> void queryPager(Page<T> page, String hql, List<Object> values, boolean cacheable) throws Exception{
		if(values == null) {
			values = new ArrayList<Object>();
		}
		hql = PageHandle.convertQL(hql);
		String count_hql = PageHandle.preCountJPQL(hql);
		executeCount(page, count_hql, values, cacheable);
		executeList(page, hql, values, cacheable);
	}
	
	@Override
	public E find(PK id) throws Exception{
		return (E)getEntityManager().find(entityClass, id);
	}

	@Override
	public List<E> batchFind(Object...ids) throws Exception{
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<E> query = cb.createQuery(entityClass);
		Root<E> systemBackup = query.from(entityClass);
		Path<String> id = systemBackup.get(getIdName());
		query.select(systemBackup).where(id.in(ids));
		return getEntityManager().createQuery(query).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public int batchDelete(PK...ids) throws Exception{
		if(ids == null || ids.length < 1){
			return 0;
		}
		StringBuilder hql = new StringBuilder("DELETE FROM " +entityClass.getSimpleName()+ " WHERE " +getIdName()+ " in (");
		for(int i = 0; i < ids.length; i++){
			hql.append("?,");
		}
		hql.replace(hql.length()-1, hql.length(), ")");
		Query query = getEntityManager().createQuery(hql.toString());
		for(int i = 0; i < ids.length; i++){
			query.setParameter(i+1, ids[i]);
		}
		return query.executeUpdate();
	}

	@Override
	public int delete(E entity) throws Exception{
		String hql = "DELETE FROM " +entityClass.getSimpleName()+ " WHERE " +getIdName()+ "=?";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter(1, getIdField(entityClass).get(entity));
		return query.executeUpdate();
	}

	@Override
	public void remove(E entity) throws Exception{
		getEntityManager().remove(getEntityManager().merge(entity));
	}
	
	/**
	 * 执行集合语句
	 */
	@SuppressWarnings("unchecked")
	protected <T> void executeList(Page<T> page, String list_ql, List<Object> values, boolean cacheable){
		Query query = getEntityManager().createQuery(list_ql);
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
				String[] fieldArray = PageHandle.preFieldInfo(list_ql);
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
		getEntityManager().clear();
	}
	
	/**
	 * 执行统计语句
	 */
	protected <T> void executeCount(Page<T> page, String count_ql, List<Object> values, boolean cacheable){
		Query query = getEntityManager().createQuery(count_ql);
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
	
	private String getIdName() {
		Field idField = getIdField(entityClass);
		if(idField != null){
			return idField.getName();
		}
		return null;
	}
	
	private Field getIdField(Class<?> clazz) {
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
				item = getIdField(superclass);
			}
		}
		return item;
	}
	
}
