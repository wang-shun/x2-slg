package ${package};
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date ${parseFileBean.xdate} 
 */
public class ${parseFileBean.fileName} extends BaseFilePri{
	
	<#list confBeans as confBean>
	/**${confBean.see}*/
	${confBean.type} ${confBean.fieldName};
	</#list>
	
	
	
	<#list confBeans as confBean>
	/**${confBean.see}*/
	<#if confBean.type=="Object">
	@SuppressWarnings("unchecked")
	public <T> T get${confBean.methodName}(){
		return (T)${confBean.fieldName};
	}
	<#else>
	public ${confBean.type} get${confBean.methodName}(){
		return ${confBean.fieldName};
	}
	</#if>
	</#list>
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}