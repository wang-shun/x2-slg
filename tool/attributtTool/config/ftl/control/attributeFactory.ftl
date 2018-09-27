package com.xgame.logic.server.attribute;

import com.xgame.data.attribute.PlayerAttribute;
import com.xgame.data.attribute.XAttribute;


/**
 *
 *2016-7-21  20:08:56
 *@author ye.yuan
 *
 */
public enum ${className} {
	
	<#list fields as field>	
	/**
	 *${field.explain}
	 */
	${field.allUpperName}(${field.id}),
	</#list>
	;
	
	private int id;
	
	${className}(int id) {
		this.id=id;
	}
	
	public int getId() {
		return id;
	}
	
	
	public static PlayerAttributeControl create(PlayerAttribute playerAttribute){
		if(playerAttribute==null){
			playerAttribute = new PlayerAttribute();
		}
		<#list fields as field>	
		//${field.explain}
		if(!playerAttribute.attributeTable.containsKey(${field.allUpperName}.id)){
			playerAttribute.attributeTable.put(${field.allUpperName}.id,new XAttribute(${field.allUpperName}.id,true));
		}
		</#list>
		PlayerAttributeControl control = new PlayerAttributeControl();
		control.attribute = playerAttribute;
		return control;
	}

	
}