package com.xgame.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public abstract class BasePriFactory<E> {

	protected Map<Integer,E> factory = new HashMap<>();
	
	public abstract E newPri();
	
	protected boolean verify(Object obj) {
		return obj!=""?true:false;
	}
	
	public abstract void init(E pir);
	
	public abstract void load(E pir);
	
	public Map<Integer, E> getFactory() {
		return factory;
	}
	
	/**
	 * Alex
	 * 当前配置加载完成后的处理
	 * */
	public void loadOver(String programConfigPath, Map<Integer,E> data)
	{
		
	}
}
