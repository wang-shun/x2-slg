package com.xgame.logic.server.game.world.handler;

import java.util.Set;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.world.SpriteManager;
import com.xgame.logic.server.game.world.bean.SpriteBean;
import com.xgame.logic.server.game.world.converter.WorldConverter;
import com.xgame.logic.server.game.world.entity.SpriteInfo;
import com.xgame.logic.server.game.world.message.ReqCollectListMessage;
import com.xgame.logic.server.game.world.message.ResCollectListMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqCollectListHandler extends PlayerCommand<ReqCollectListMessage>{
	@Override
    public void action() {
        // 返回收藏列表
        ResCollectListMessage resCollectListMessage = new ResCollectListMessage();
        Set<Integer> friendSet = player.roleInfo().getPlayerCollect().getFriendCollect();
        if(friendSet != null) {
        	for(Integer index : friendSet) {
        		SpriteInfo targetInfo = player.getSpriteManager().getGrid(index % SpriteManager.xGridNum, index / SpriteManager.xGridNum);
        		SpriteBean spriteBean = WorldConverter.converterSprite(targetInfo, false);
        		resCollectListMessage.friendList.add(spriteBean);
        	}
        }
        
        Set<Integer> enmySet = player.roleInfo().getPlayerCollect().getEnemyCollect();
        if(enmySet != null) {
        	for(Integer index : enmySet) {
        		SpriteInfo targetInfo = player.getSpriteManager().getGrid(index % SpriteManager.xGridNum, index / SpriteManager.xGridNum);
        		SpriteBean spriteBean = WorldConverter.converterSprite(targetInfo, false);
        		resCollectListMessage.enmyList.add(spriteBean);
        	}
        }
        
        
        Set<Integer> resourceSet = player.roleInfo().getPlayerCollect().getResourceCollect();
        if(resourceSet != null) {
        	for(Integer index : resourceSet) {
        		SpriteInfo targetInfo = player.getSpriteManager().getGrid(index % SpriteManager.xGridNum, index / SpriteManager.xGridNum);
        		SpriteBean spriteBean = WorldConverter.converterSprite(targetInfo, false);
        		resCollectListMessage.resourceList.add(spriteBean);
        	}
        }
        
        player.send(resCollectListMessage);
    }
}
