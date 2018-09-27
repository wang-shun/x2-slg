package com.xgame.logic.server.game.playerattribute;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.xgame.framework.lifecycle.Startup;
import com.xgame.framework.lifecycle.StartupOrder;
import com.xgame.logic.server.core.utils.InjectorUtil;

@Component
public class AttributeAddManager {
	
	private static AttributeAddManager injector;
	
	/**
	 * 属性加成模块
	 */
	private Map<Class<?>,IAttributeAddModule> attributeAddMap = new HashMap<>();

	@Startup(order = StartupOrder.ATTRIBUTE_MANAGER, desc = "属性启动加载")
	public void load(){
		Map<String, IAttributeAddModule> attributeAddModuleMap = InjectorUtil.getInjector().getApplicationContext().getBeansOfType(IAttributeAddModule.class);
		if(attributeAddModuleMap != null) {
			for(IAttributeAddModule module : attributeAddModuleMap.values()) {
				attributeAddMap.put(module.getClass(), module);
			}
		}
		injector = this;
	}
	
	
	
	public Map<Class<?>, IAttributeAddModule> getAttributeAddMap() {
		return attributeAddMap;
	}



	public static AttributeAddManager get(){
		return injector;
	}
}
