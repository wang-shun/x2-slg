package com.xgame.logic.server.core.component;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *组件管理器
 *2016-9-29  11:57:39
 *@author ye.yuan
 *
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ComponentManager<E> {
	
	Map<Class<?>, Componentable<E>> components = new ConcurrentHashMap<>();
	
	public void register(Class<?> classBean, Componentable<E> component){
		components.put(classBean, component);
	}
	
	public void start(E product){
		Iterator<Componentable<E>> iterator = components.values().iterator();
		while (iterator.hasNext()) {
			Componentable<E> componentable = (Componentable<E>) iterator.next();
			componentable.setProduct(product);
		}
	}
	
	public void firstLoad(Object ... param){
		Iterator<Componentable<E>> iterator = components.values().iterator();
		while (iterator.hasNext()) {
			Componentable<E> componentable = (Componentable<E>) iterator.next();
			componentable.firstLoad(param);
		}
	}
	
	public void loadComponent(Object ... param){
		Iterator<Componentable<E>> iterator = components.values().iterator();
		while (iterator.hasNext()) {
			Componentable<E> componentable = (Componentable<E>) iterator.next();
			componentable.loadComponent(param);
		}
	}
	
	public void destroy(){
		Iterator<Componentable<E>> iterator = components.values().iterator();
		while (iterator.hasNext()) {
			Componentable<E> componentable = (Componentable<E>) iterator.next();
			componentable.destroy();
		}
	}
}
