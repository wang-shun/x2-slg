package com.xgame.logic.server.game.modify.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.modify.message.ReqDestorySoldierMessage;


/**
 * 请求销毁士兵
 * @author jacky.jiang
 *
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqDestorySoldierHandler extends PlayerCommand<ReqDestorySoldierMessage>{
	
	@Override
    public void action() {
		player.getModifyManager().destroyArmor(player, message.id, message.num);
    }
}
