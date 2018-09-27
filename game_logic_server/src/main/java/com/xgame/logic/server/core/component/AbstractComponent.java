package com.xgame.logic.server.core.component;



/**
 * 抽象组件借口
 * @author jacky.jiang
 * @param <E>
 */
public abstract class AbstractComponent<E> implements Componentable<E> {
	
	protected E e;
	
	@Override
	public void firstLoad(Object ... param) {
		
	}

	@Override
	public void loadComponent(Object ... param) {
		
	}

	@Override
	public void loginLoad(Object... param) {
		
	}

	@Override
	public E getPlayer() {
		return e;
	}

	@Override
	public void setProduct(E product) {
		this.e = product;
	}

	@Override
	public void destroy() {
		e = null;
	}
}
