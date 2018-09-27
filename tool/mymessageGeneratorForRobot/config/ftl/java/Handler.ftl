package ${package}.handler;

import com.xgame.logic.server.layer.process.PlayerCommand;

<#if imports??>
<#list imports as import>
import ${import};
</#list>
</#if>
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ${className}Handler extends PlayerCommand<${className}Message>{


<#if msg.msgType==1>
    @Override
    public ${msg.msgName.messageName}Message callback() {
    	return new ${msg.msgName.messageName}Message();
    }
<#else>
	@Override
    public void action() {
    }
</#if>
}
