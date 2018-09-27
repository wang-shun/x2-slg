/**
 * 
 */
package com.xgame.logic.server.core.db.cache.cache;

import java.io.Serializable;

/**
 * 缓存对象
 * @author jacky
 *
 */
public class CachedObject implements Serializable {

	private static final long serialVersionUID = 705814956576783500L;
	
	/**
	 * 实际缓存的对象
	 */
	private Object object;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((object == null) ? 0 : object.hashCode());
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
		CachedObject other = (CachedObject) obj;
		if (object == null) {
			if (other.object != null)
				return false;
		} else if (!object.equals(other.object))
			return false;
		return true;
	}

	public CachedObject(Object object) {
		super();
		this.object = object;
	}

	public Object getObject() {
		return object;
	}

}
