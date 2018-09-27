/**
 * Copyright (c) 2014, http://www.moloong.com/ All Rights Reserved. 
 * 
 */  
package ${package}.jobhandler;

import com.moloong.robot.core.ReqMsgJobHandler;
import com.moloong.robot.core.client.Player;
import org.apache.log4j.Logger;

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
public class ${className}JobHandler extends ReqMsgJobHandler {
	private static Logger log = Logger.getLogger(${className}JobHandler.class);
		
	@Override
	public void request(Player player) {
		${className}Message reqMsg = new ${className}Message();
		//TODO
		
		player.sendMsg(reqMsg);
	}

}
