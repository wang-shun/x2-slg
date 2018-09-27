package com.xgame.base.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xgame.base.dao.IBaseDao;
import com.xgame.base.service.IBaseService;
import com.xgame.common.Pagination;

@Service(value="baseService")
@Transactional
public class BaseServiceImpl<T> implements IBaseService<T> {
	
	@Autowired
	private IBaseDao<T> baseDao;
	
	public void setBaseDao(IBaseDao<T> baseDao){
		this.baseDao =baseDao;
	}

	@Override
	public T save(T entity) {
		return baseDao.save(entity);
	}

	@Override
	public T update(T entity) {
		return baseDao.update(entity);
	}

	@Override
	public T saveOrUpdate(T entity) {
		return baseDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(T entity) {
		baseDao.delete(entity);
	}

	@Override
	public T findById(Serializable id) {
		return baseDao.findById(id);
	}

	@Override
	public void deleteById(Serializable id) {
		baseDao.deleteById(id);
		
	}

	@Override
	public List<T> getAll() {
		return baseDao.getAll();
	}

	@Override
	public List<T> getByIds(String[] ids) {
		return baseDao.getByIds(ids);
	}

	@Override
	public int getTotalCount() {
		return baseDao.getTotalCount();
	}

	@Override
	public Pagination getPagination(int pageNumber, int pageSize) {
		return baseDao.getPagination(pageNumber, pageSize);
	}

	@Override
	public Pagination getPaginationByHql(String hql, Map<String, Object> params, int pageNumber, int pageSize) {
		return baseDao.getPaginationByHql(hql, params, pageNumber, pageSize);
	}

	@Override
	public Pagination getPaginationBySql(String sql, int pageNumber, int pageSize) {
		return baseDao.getPaginationBySql(sql, pageNumber, pageSize);
	}

	@Override
	public List<T> findList(String hql, Object... params) {
		return baseDao.findList(hql, params);
	}

	@Override
	public List<T> findListByHql(String hql, Map<String, Object> params) {
		return baseDao.findListByHql(hql, params);
	}

	@Override
	public List findListBySql(String sql) {
		return baseDao.findListBySql(sql);
	}

	@Override
	public List<T> findByProperty(String name, Object value) {
		return baseDao.findByProperty(name, value);
	}

	@Override
	public List<T> findByProperties(Map<String, Object> conditionMap) {
		return baseDao.findByProperties(conditionMap);
	}
	
	@Override
	public List getTableNames(String fuzzyName){
		return baseDao.getTableNames(fuzzyName);
	}
}
