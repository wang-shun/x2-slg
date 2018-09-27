package com.xgame.data.attribute;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
 
/**
 * 玩家全部属性,里面包括了每个属性各个系统的所有值 
 * @author gameTools
 *
 */
public class ${className}  implements Serializable,IPlayerAttribute
{
 	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    /********** attribute ***********/
<#list fields as field>	
	/**
	 *${field.explain}
	 */
	public ${field.type} ${field.formName};	
</#list>

	public Map<Integer, XAttribute> attributeTable = new HashMap<>();
 

	<#list fields as field>	
	/**
	 *${field.explain}
	 */
	public ${field.type} get${field.upperName}(){
		return ${field.formName};
	}
	</#list>
}