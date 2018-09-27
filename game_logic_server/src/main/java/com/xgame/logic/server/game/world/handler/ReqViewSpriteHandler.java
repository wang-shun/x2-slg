package com.xgame.logic.server.game.world.handler;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.cross.CrossManager;
import com.xgame.logic.server.game.cross.entity.CrossWorldInfo;
import com.xgame.logic.server.game.cross.rmi.RemoteService;
import com.xgame.logic.server.game.world.SpriteManager;
import com.xgame.logic.server.game.world.WorldLogicManager;
import com.xgame.logic.server.game.world.constant.WorldConstant;
import com.xgame.logic.server.game.world.message.ReqViewSpriteMessage;
import com.xgame.logic.server.game.world.message.ResViewSpriteMessage;
import com.xgame.logic.server.game.world.message.ResViewWorldTerritoryMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqViewSpriteHandler extends PlayerCommand<ReqViewSpriteMessage>{

	@Autowired
	private WorldLogicManager worldLogicManager;
	@Autowired
	private CrossManager crossManager;
	@Autowired
	private SpriteManager spriteManager;
	
	@Override
    public void action() {
		
		// 返回地图精灵信息
		if(message.viewCenter == null) {
        	return;
        }
        
        //判断坐标范围的合法性
        if(message.viewCenter.x < 0 || message.viewCenter.x > 512 || message.viewCenter.y < 0 || message.viewCenter.y > 512) {
        	return;
        }
        
        if(message.serverId < 0) {
        	return;
        }
        
        log.info("**********************查询服务器信息id:{}", message.serverId);
		if (message.serverId == InjectorUtil.getInjector().serverId) {
			CrossWorldInfo crossWorldInfo =  worldLogicManager.getRangeSpriteInfo(message.viewCenter.x, message.viewCenter.y);
			 
			ResViewSpriteMessage resViewSpriteMessage = new ResViewSpriteMessage();
			resViewSpriteMessage.spriteBeanList = crossWorldInfo.getSpriteBeans();
			resViewSpriteMessage.viewCenter = message.viewCenter;
			resViewSpriteMessage.serverId = message.serverId;
			player.send(resViewSpriteMessage);
			
			ResViewWorldTerritoryMessage resViewWorldTerritoryMessage = new ResViewWorldTerritoryMessage();
			resViewWorldTerritoryMessage.queryType = WorldConstant.TERRITORY_QUERY_TYPE_QUERY;
			resViewWorldTerritoryMessage.territoryBean = crossWorldInfo.getTerritoryBeanList();
			player.send(resViewWorldTerritoryMessage);
			 
		} else {
			RemoteService remoteService = crossManager.getRemoteService(message.serverId);
			CrossWorldInfo crossWorldInfo = remoteService.getWorldInfo(message.viewCenter.x, message.viewCenter.y);
			
			ResViewSpriteMessage resViewSpriteMessage = new ResViewSpriteMessage();
			resViewSpriteMessage.spriteBeanList = crossWorldInfo.getSpriteBeans();
			resViewSpriteMessage.vectorInfoList = crossWorldInfo.getVectorInfos();
			resViewSpriteMessage.viewCenter = message.viewCenter;
			resViewSpriteMessage.serverId = message.serverId;
			player.send(resViewSpriteMessage);
		}
	}
}
