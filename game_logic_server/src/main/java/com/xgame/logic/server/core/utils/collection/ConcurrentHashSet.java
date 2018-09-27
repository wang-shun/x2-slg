package com.xgame.logic.server.core.utils.collection;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 带并发的set
 * @author jacky.jiang
 *
 */
public class ConcurrentHashSet<E> extends AbstractSet<E> implements Set<E>, java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2245200379630140937L;

	private static final Object OBJ = new Object();
	
	private final ConcurrentHashMap<E, Object> map;
	
	public ConcurrentHashSet() {
		map = new ConcurrentHashMap<>();
	}
	
	public ConcurrentHashSet(int initialCapacity) {
		map = new ConcurrentHashMap<E, Object>();
	}
	
	@Override
	public Iterator<E> iterator() {
		return map.keySet().iterator();
	}

	@Override
	public int size() {
		return map.size();
	}
	
	public boolean isEmpty() {
		return map.isEmpty();
	}
	
	public boolean contains(Object obj) {
		return map.keySet().contains(obj);
	}
	
	public boolean add(E e) {
		return map.put(e, OBJ) == null;
	}
	
	public boolean remove(Object obj) {
		return map.remove(obj) == OBJ;
	}
	
	public void clear() {
		map.clear();
	}

}
