/**
 * Copyright (c) 2014, http://www.moloong.com/ All Rights Reserved. 
 * 
 */  
package ${package}.handler;

import com.moloong.bleach.discovery.message.ReqBeginDiscoveryMessage;

<#if imports??>
<#list imports as import>
import ${import};
</#list>
</#if>

/**  
 * @Description:
 * @author messageGenerator
 *
 */
public class ${className}JobHandler extends ReqMsgJobHandler{

	
	@Override
	public void requst(Player player) {
		${className}Message reqMsg = new ${className}Message();
		//TODO
		
		player.getClient().sendMessage(reqMsg);
	}

	@Override
	public String getCron() {
		return null;
	}

}
