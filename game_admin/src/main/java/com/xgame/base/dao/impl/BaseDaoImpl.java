package com.xgame.base.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.xgame.base.dao.IBaseDao;
import com.xgame.common.Pagination;

@SuppressWarnings("unchecked")
@Repository(value="baseDao")
public class BaseDaoImpl<T> implements IBaseDao<T>{
	
	public static final String FROM ="from";
	public static final String ORDER = "order";
	
	protected Class<T> clazz;
	
	public BaseDaoImpl() {
		Type type = this.getClass().getGenericSuperclass();
		if(type instanceof ParameterizedType){
			clazz = (Class<T>)((ParameterizedType)type).getActualTypeArguments()[0];
		}else{
			clazz = null;
		}
	}
	
	@Resource
	private SessionFactory sessionFactory;
	
	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	protected Query setParameter(Query query,Map<String,Object> params){
		for(Iterator iterator = (Iterator) params.keySet().iterator();iterator.hasNext();){
			String key = (String) iterator.next();
			query.setParameter(key, params.get(key));
		}
		return query;
	}
	
	protected String parseSelectCount(String queryString){
		String hql = queryString.toLowerCase();
		if(hql.lastIndexOf(ORDER) == -1){
			queryString = queryString.substring(hql.indexOf(FROM));
		}else{
			queryString = queryString.substring(hql.indexOf(FROM), hql.lastIndexOf(ORDER));
		}
		queryString = "select count(*) " + queryString;
		return queryString;
	}
	
	@Override
	public T save(T entity){
		getSession().save(entity);
		return entity;
	}
	
	@Override
	public T update(T entity){
		//防止org.hibernate.NonUniqueObjectException
		getSession().flush();
		getSession().clear();
		getSession().update(entity);
		return entity;
	}
	
	@Override
	public T saveOrUpdate(T entity){
		getSession().saveOrUpdate(entity);
		return entity;
	}
	
	@Override
	public void delete(T entity){
		getSession().delete(entity);
	}
	
	@Override
	public T findById(Serializable id){
		return (T) this.getSession().get(this.clazz, id);
	}
	
	@Override
	public void deleteById(Serializable id){
		this.getSession().delete(this.findById(id));
	}
	
	@Override
	public List<T> getAll(){
		return getSession().createQuery("from " + clazz.getSimpleName()).list();
	}
	
	@Override
	public List<T> getByIds(String[] ids){
		if(ids == null || ids.length == 0){
			return Collections.EMPTY_LIST;
		}
		return getSession().createQuery("FROM " + clazz.getSimpleName() + " WHERE id IN(:ids)").setParameterList("ids", ids).list();
	}
	
	@Override
	public int getTotalCount(){
		int totalCount = ((Long)getSession().createQuery("select count(*) from" + clazz.getSimpleName()).list().get(0)).intValue();
		return totalCount;
	}
	
	@Override
	public Pagination getPagination(int pageNumber,int pageSize){
		Pagination pagination = new Pagination(pageNumber, pageSize, getTotalCount());
		List<T> list = getSession().createQuery("from" + clazz.getSimpleName()).setFirstResult(pagination.getFirstResult()).setMaxResults(pagination.getPageSize()).list();
		pagination.setList(list);
		return pagination;
	}
	
	public Pagination getPaginationByHql(String hql,Map<String,Object> params,int pageNumber,int pageSize){
		Pagination pagination = new Pagination(pageNumber, pageSize, getTotalCountByHql(hql,params));
		Query query = this.getSession().createQuery(hql);
		this.setParameter(query, params);
		List list = query.setFirstResult(pagination.getFirstResult()).setMaxResults(pagination.getPageSize()).list();
		pagination.setList(list);
		return pagination;
	}
	
	/**
	 * 多个from的处理
	 * @param hql
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Pagination getPaginationByHqlMultiFrom(String hql,String countHql,Map<String,Object> params,int pageNumber,int pageSize){
		Pagination pagination = new Pagination(pageNumber, pageSize, getTotalCountByHqlMultiFrom(countHql,params));
		Query query = this.getSession().createQuery(hql);
		this.setParameter(query, params);
		List list = query.setFirstResult(pagination.getFirstResult()).setMaxResults(pagination.getPageSize()).list();
		pagination.setList(list);
		return pagination;
	}
	
	protected long getTotalCountByHql(String hql,Map<String,Object> params){
		hql = parseSelectCount(hql);
		Query query = this.getSession().createQuery(hql);
		setParameter(query, params);
		return ((Long)query.list().get(0)).longValue();
	}
	
	protected long getTotalCountByHqlMultiFrom(String hql,Map<String,Object> params){
		Query query = this.getSession().createQuery(hql);
		setParameter(query, params);
		return ((Long)query.list().get(0)).longValue();
	}
	
	public Pagination getPaginationBySql(String sql,int pageNumber,int pageSize){
		Pagination pagination = new Pagination(pageNumber, pageSize, getTotalCountBySql(sql));
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = query.setFirstResult(pagination.getFirstResult()).setMaxResults(pagination.getPageSize()).list();
		pagination.setList(list);
		return pagination;
	}
	
	protected long getTotalCountBySql(String sql){
		sql = parseSelectCount(sql);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		return ((BigInteger)query.list().get(0)).longValue();
	}
	
	@Override
	public List<T> findList(String hql,Object... params){
		Query query = getSession().createQuery(hql);
		for(int i=0;i<params.length;i++){
			query.setParameter(i, params[i]);
		}
		return query.list();
	}
	
	@Override
	public List<T> findListByHql(String hql,Map<String,Object> params){
		Query query = this.getSession().createQuery(hql);
		this.setParameter(query, params);
		return query.list();
	}
	
	@Override
	public List findListBySql(String sql){
		SQLQuery query = this.getSession().createSQLQuery(sql);
		return query.list();
	}
	
	@Override
	public List<T> findByProperty(String name,Object value){
		String hql = "from " + this.clazz.getSimpleName() + " where " + name + "=?";
		return findList(hql, value);
	}
	
	@Override
	public List<T> findByProperties(Map<String,Object> conditionMap){
		StringBuilder hql = new StringBuilder();
		hql.append("from " + clazz.getSimpleName());
		if(!conditionMap.isEmpty()){
			Iterator<String> iterator = conditionMap.keySet().iterator();
			String key = iterator.next();
			hql.append(" where " + key + "=:" + key);
			while(iterator.hasNext()){
				hql.append(" and "  + key + "=:" + key);
			}
		}
		return findListByHql(hql.toString(), conditionMap);
	}
	
	/**
	 * 根据fuzzyName模糊匹配数据库表
	 */
	@Override
	public List getTableNames(String fuzzyName){
		String sql = "show TABLES like '"+fuzzyName+"%'";
		SQLQuery query = getSession().createSQLQuery(sql);
		return query.list();
	}
}
