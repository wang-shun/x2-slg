package ${package}.handler;

import com.xgame.logic.server.process.PlayerCommand;

<#if imports??>
<#list imports as import>
import ${import};
</#list>
</#if>

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
public class ${className}Handler extends PlayerCommand<${className}Message>{
    @Override
    public void action() {
        ${className}Message msg = getData();
        
    }
}
