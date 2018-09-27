package ${package};

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date ${parseFileBean.xdate} 
 */
public class ${parseFileBean.fileFactoryName} extends BasePriFactory<${parseFileBean.fileName}>{
	

	public void init(${parseFileBean.fileName} pir) {
		
	}
	
	@Override
	public void load(${parseFileBean.fileName} pir) {
		
	}
	
	<#list confBeans as confBean>
	
	<#if confBean.type=="Object">
	/**
	 *自定义解析  ${confBean.fieldName}
	 */
	@ConfParse("${confBean.fieldName}")
	public void ${confBean.fieldName}Pares(String conf,${parseFileBean.fileName} pir){
		
	}
	</#if>
	</#list>
	
	@Override
	public ${parseFileBean.fileName} newPri() {
		return new ${parseFileBean.fileName}();
	}
	
	public static ${parseFileBean.fileName} get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final ${parseFileBean.fileFactoryName} instance = new ${parseFileBean.fileFactoryName}(); 
	
	
	public static ${parseFileBean.fileFactoryName} getInstance() {
		return instance;
	}
}