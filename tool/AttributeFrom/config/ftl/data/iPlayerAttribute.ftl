package com.xgame.data.attribute;
/**
 *
 *
 *@author gameTools
 *
 */
public interface ${className} {
	
	<#list fields as field>	
	/**
	 *${field.explain}
	 */
	${field.type} get${field.upperName}();	
	</#list>
	
	
}
