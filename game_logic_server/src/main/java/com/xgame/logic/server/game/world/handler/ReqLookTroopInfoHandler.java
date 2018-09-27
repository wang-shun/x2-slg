package com.xgame.logic.server.game.world.handler;

import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.success.SuccessTipEnum;
import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.war.bean.WarSoldierBean;
import com.xgame.logic.server.game.world.message.ReqLookTroopInfoMessage;
import com.xgame.logic.server.game.world.message.ResLookTroopInfoMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqLookTroopInfoHandler extends PlayerCommand<ReqLookTroopInfoMessage>{

	@Override
    public void action() {
		List<WarSoldierBean> soldierBeans = player.getWorldLogicManager().getWarSoldierBean(message.marchId);
		ResLookTroopInfoMessage resLookTroopInfoMessage = new ResLookTroopInfoMessage();
		resLookTroopInfoMessage.soldierBeanList = soldierBeans;
		player.send(resLookTroopInfoMessage);
		
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E121_WORLD.CODE4);
	}
}
