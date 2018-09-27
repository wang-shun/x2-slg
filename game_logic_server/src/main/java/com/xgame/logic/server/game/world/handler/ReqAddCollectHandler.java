package com.xgame.logic.server.game.world.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.world.bean.SpriteBean;
import com.xgame.logic.server.game.world.converter.WorldConverter;
import com.xgame.logic.server.game.world.entity.SpriteInfo;
import com.xgame.logic.server.game.world.message.ReqAddCollectMessage;
import com.xgame.logic.server.game.world.message.ResAddCollectMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqAddCollectHandler extends PlayerCommand<ReqAddCollectMessage>{


	@Override
    public void action() {
		
		if(message.viewCenter == null) {
        	return;
        }
        
        //判断坐标范围的合法性
        if(message.viewCenter.x <0 || message.viewCenter.x > 512 || message.viewCenter.y < 0 || message.viewCenter.y > 512) {
        	return;
        }
        
        
        SpriteInfo spriteInfo = player.getSpriteManager().getGrid(message.viewCenter.x, message.viewCenter.y);
        if(spriteInfo == null) {
        	return;
        }
        
        SpriteBean spriteBean = WorldConverter.converterSprite(spriteInfo, false);
        player.roleInfo().getPlayerCollect().addCollect(message.viewCenter.x, message.viewCenter.y, message.type);

        // 返回收藏信息
        ResAddCollectMessage resAddCollectMessage = new ResAddCollectMessage();
        resAddCollectMessage.spriteBean = spriteBean;
        player.send(resAddCollectMessage);
	}
}
