package com.xgame.logic.server.gm.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.gm.message.ReqGMMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqGMHandler extends PlayerCommand<ReqGMMessage>{
    
	@Override
    public void action() {
        InjectorUtil.getInjector().gmProxySystem.invoke(player,message.params);
    }

}
