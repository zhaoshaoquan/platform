package com.stone.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import javax.persistence.Id;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stone.commons.page.Page;
import com.stone.dao.comm.GeneralDao;

@Repository
public abstract class AbstractHibernateGeneralDao<E, PK extends Serializable> implements GeneralDao<E, PK> {
	@SuppressWarnings("unchecked")
	private Class<E> entityClass = (Class<E>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	@Autowired
	protected SessionFactory sessionFactory;
	
	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void save(E...entity) throws Exception{
		if(entity == null)return;
		Session session = getCurrentSession();
		for(E e : entity){
			session.save(e);
		}
	}

	@Override
	public void save(E entity) throws Exception{
		if(entity == null)return;
		getCurrentSession().save(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(E...entity) throws Exception{
		if(entity == null)return;
		Session session = getCurrentSession();
		for(E e : entity){
			session.update(e);
		}
	}

	@Override
	public void update(E entity) throws Exception{
		if(entity == null)return;
		getCurrentSession().update(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveOrUpdate(E...entity) throws Exception{
		if(entity == null)return;
		Session session = getCurrentSession();
		for(E e : entity){
			session.saveOrUpdate(e);
		}
	}
	
	@Override
	public void saveOrUpdate(E entity) throws Exception{
		if(entity == null)return;
		getCurrentSession().saveOrUpdate(entity);
	}
	
	@Override
	public int executeUpdate(String qlString, Object...values) throws Exception{
		Session session = getCurrentSession();
		Query query = session.createQuery(qlString);
		if(values != null) {
			for(int i = 0; i < values.length; i++){
				query.setParameter(i, values[i]);
			}
		}
		return query.executeUpdate();
	}

	@Override
	public int executeUpdate(String qlString, Map<String, Object> params) throws Exception{
		Session session = getCurrentSession();
		Query query = session.createQuery(qlString);
		for(String name : params.keySet()){
			query.setParameter(name, params.get(name));
		}
		return query.executeUpdate();
	}

	@Override
	public int executeUpdate(String qlString, List<Object> values) throws Exception{
		Session session = getCurrentSession();
		Query query = session.createQuery(qlString);
		if(values != null) {
			for(int i = 0; i < values.size(); i++){
				query.setParameter(i, values.get(i));
			}
		}
		return query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> queryAll() throws Exception{
		String qlString = "SELECT o FROM " + entityClass.getSimpleName() + " o";
		Session session = getCurrentSession();
		Query query = session.createQuery(qlString);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> query(String qlString, Map<String, Object> params) throws Exception{
		Session session = getCurrentSession();
		Query query = session.createQuery(qlString);
		if(params != null) {
			for(String key : params.keySet()){
				query.setParameter(key, params.get(key));
			}
		}
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> query(String qlString, List<Object> values) throws Exception{
		Session session = getCurrentSession();
		Query query = session.createQuery(qlString);
		if(values != null) {
			for(int i = 0; i < values.size(); i++){
				query.setParameter(i, values.get(i));
			}
		}
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> query(String qlString, Object...values) throws Exception{
		Session session = getCurrentSession();
		Query query = session.createQuery(qlString);
		if(values != null) {
			for(int i = 0; i < values.length; i++){
				query.setParameter(i, values[i]);
			}
		}
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public E queryObject(String qlString, Object...values) throws Exception{
		Session session = getCurrentSession();
		Query query = session.createQuery(qlString);
		if(values != null) {
			for(int i = 0; i < values.length; i++){
				query.setParameter(i, values[i]);
			}
		}
		return (E)query.uniqueResult();
	}

	@Override
	public <T> void queryPager(Page<T> page, String qlString) throws Exception{
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void queryPager(Page<T> page, String qlString, boolean cacheable) throws Exception{
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void queryPager(Page<T> page, String qlString, Map<String, Object> params) throws Exception{
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void queryPager(Page<T> page, String qlString, List<Object> values) throws Exception{
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void queryPager(Page<T> page, String qlString, Object...values) throws Exception{
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public E find(PK id) throws Exception{
		return (E)getCurrentSession().get(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> batchFind(Object...ids) throws Exception{
		if(ids == null || ids.length < 1){
			return null;
		}
		Session session = getCurrentSession();
		StringBuilder hql = new StringBuilder("SELECT FROM " +entityClass.getSimpleName()+ " WHERE " +getIdName()+ " in (");
		for(int i = 0; i < ids.length; i++){
			hql.append("?,");
		}
		hql.replace(hql.length() - 1, hql.length(), ")");
		Query query = session.createQuery(hql.toString());
		for(int i = 0; i < ids.length; i++){
			query.setParameter(i, ids[i]);
		}
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public int batchDelete(PK...ids) throws Exception{
		if(ids == null || ids.length < 1){
			return 0;
		}
		Session session = getCurrentSession();
		StringBuilder hql = new StringBuilder("DELETE FROM " +entityClass.getSimpleName()+ " WHERE " +getIdName()+ " in (");
		for(int i = 0; i < ids.length; i++){
			hql.append("?,");
		}
		hql.replace(hql.length() - 1, hql.length(), ")");
		Query query = session.createQuery(hql.toString());
		for(int i = 0; i < ids.length; i++){
			query.setParameter(i, ids[i]);
		}
		return query.executeUpdate();
	}

	@Override
	public int delete(E entity) throws Exception{
		getCurrentSession().delete(entity);
		return 1;
	}

	@Override
	public void remove(E entity) throws Exception{
		getCurrentSession().delete(entity);
	}
	
	private String getIdName(){
		Field[] fields = entityClass.getDeclaredFields();
		for(Field field : fields){
			Id id = field.getAnnotation(Id.class);
			if(id != null){
				return field.getName();
			}
		}
		return null;
	}
	
}
