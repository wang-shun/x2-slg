package com.xgame.logic.server.game.country.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.modify.message.ReqSpeedRecoveryArmorMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqSpeedRecoveryArmorHandler extends PlayerCommand<ReqSpeedRecoveryArmorMessage>{


	@Override
    public void action() {
//      player.getCountryManager().getModBuildControl().reformArmor(player, message.campType, message.id, message.num);
    }
}
