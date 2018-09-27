package com.xgame.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.xgame.common.Pagination;

public interface IBaseDao<T> {
	
	public T save(T entity);
	
	public T update(T entity);
	
	public T saveOrUpdate(T entity);
	
	public void delete(T entity);
	
	public T findById(Serializable id);
	
	public void deleteById(Serializable id);
	
	public List<T> findList(String hql,Object... params);
	
	public List<T> findListByHql(String hql,Map<String,Object> params);
	
	public List findListBySql(String sql);
	
	public List<T> findByProperty(String name,Object value);
	
	public List<T> findByProperties(Map<String,Object> conditionMap);
	
	public List<T> getAll();
	
	public List<T> getByIds(String[] ids);
	
	public int getTotalCount();
	
	public Pagination getPagination(int pageNumber,int pageSize);
	
	public Pagination getPaginationByHql(String hql,Map<String,Object> params,int pageNumber,int pageSize);
	
	public Pagination getPaginationBySql(String sql,int pageNumber,int pageSize);
	
	public List getTableNames(String fuzzyName);
}
