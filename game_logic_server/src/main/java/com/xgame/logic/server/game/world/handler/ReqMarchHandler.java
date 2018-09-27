package com.xgame.logic.server.game.world.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.world.WorldLogicManager;
import com.xgame.logic.server.game.world.constant.MarchType;
import com.xgame.logic.server.game.world.message.ReqMarchMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqMarchHandler extends PlayerCommand<ReqMarchMessage>{

	@Autowired
	private WorldLogicManager worldLogicManager;
	
	@Override
    public void action() {
		
		//判断坐标范围的合法性
        if(message.target.x <0 || message.target.x > 512 || message.target.y < 0 || message.target.y > 512) {
        	Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE15);
        	return;
        }
        
        if(message.type <= 0) {
        	Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE15);
        	return;
        }
        
		// 士兵信息
		if (message.type != MarchType.SCOUT.ordinal() && message.type != MarchType.TRADE.ordinal()) {
			if (message.soldiers == null || message.soldiers.isEmpty()) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE15);
				return;
			}

			if (message.soldiers.size() >= 100) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE15);
				return;
			}
		}
        
		worldLogicManager.reqOnDestination(player, message.soldiers, message.target.x, message.target.y, message.type, message.teamId, message.remainTime, message.resource);
    }
}
