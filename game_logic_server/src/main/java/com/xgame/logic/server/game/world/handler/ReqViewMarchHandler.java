package com.xgame.logic.server.game.world.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.world.WorldLogicManager;
import com.xgame.logic.server.game.world.bean.VectorInfo;
import com.xgame.logic.server.game.world.message.ReqViewMarchMessage;
import com.xgame.logic.server.game.world.message.ResViewMarchMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqViewMarchHandler extends PlayerCommand<ReqViewMarchMessage>{

	@Autowired
	private WorldLogicManager worldLogicManager;

	@Override
    public void action() {
        if(message.viewCenter.x <0 || message.viewCenter.x > 512 || message.viewCenter.y < 0 || message.viewCenter.y > 512) {
        	return;
        }
        
        // 返回出征信息
        ResViewMarchMessage resViewMarchMessage = new ResViewMarchMessage();
        List<VectorInfo> vectorInfos = worldLogicManager.getWorldMarch(message.viewCenter.x, message.viewCenter.y);
        resViewMarchMessage.vectorInfoList = vectorInfos;
        player.send(resViewMarchMessage);
    }
}
