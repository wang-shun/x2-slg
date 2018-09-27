package com.xgame.logic.server.core.db.cache.entity;

import java.io.Serializable;

import com.xgame.logic.server.core.db.cache.EntityLifecycle;


/**
 * 
 * @author jiangxt
 * @param <K>
 */
public abstract class EntityAdapter<K extends Serializable & Comparable<K>> implements IEntity<K>, Lockable<K>, EntityLifecycle, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5980896972553044513L;

	@Override
	public void onLoad() {
		
	}

	@Override
	public void onUpdate() {
		
	}

	@Override
	public void postUpdate() {
		
	}

	@Override
	public void onDelete() {
		
	}

	@Override
	public void onSave() {
		
	}

	@Override
	public K getIdentity() {
		return null;
	}
	
}
