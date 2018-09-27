package com.xgame.logic.server.game.world.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.world.message.ReqDeleteMessage;
import com.xgame.logic.server.game.world.message.ResDeleteMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqDeleteHandler extends PlayerCommand<ReqDeleteMessage>{


	@Override
    public void action() {
		
		if(message.viewCenter == null) {
        	return;
        }
        
        //判断坐标范围的合法性
        if(message.viewCenter.x <0 || message.viewCenter.x > 512 || message.viewCenter.y < 0 || message.viewCenter.y > 512) {
        	return;
        }
        
        player.roleInfo().getPlayerCollect().removeCollect(message.viewCenter.x, message.viewCenter.y, message.type);
        ResDeleteMessage resDeleteCollectMessage = new ResDeleteMessage();
        Vector2Bean bean =  new Vector2Bean();
        bean.x = message.viewCenter.x;
        bean.y = message.viewCenter.y;
        resDeleteCollectMessage.viewCenter = bean;
        resDeleteCollectMessage.type = message.type;
        player.send(resDeleteCollectMessage);
        
    }
}
