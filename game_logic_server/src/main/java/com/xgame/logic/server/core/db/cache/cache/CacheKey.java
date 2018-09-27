/**
 * 
 */
package com.xgame.logic.server.core.db.cache.cache;

import java.io.Serializable;

/**
 * 缓存key
 * @author jacky
 *
 */
public class CacheKey implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1116168963168175445L;

	private Class<?> entityClass;
	
	private Serializable id;

	public CacheKey(Class<?> entityClass, Serializable id) {
		super();
		this.entityClass = entityClass;
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((entityClass == null) ? 0 : entityClass.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CacheKey other = (CacheKey) obj;
		if (entityClass == null) {
			if (other.entityClass != null)
				return false;
		} else if (!entityClass.equals(other.entityClass))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}



	public Class<?> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
	}

	public Serializable getId() {
		return id;
	}

	public void setId(Serializable id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "CacheKey [entityClass=" + entityClass + ", id=" + id + "]";
	}
}
