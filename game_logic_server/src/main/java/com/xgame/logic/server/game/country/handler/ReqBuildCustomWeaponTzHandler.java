package com.xgame.logic.server.game.country.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.customweanpon.message.ReqBuildCustomWeaponTzMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqBuildCustomWeaponTzHandler extends PlayerCommand<ReqBuildCustomWeaponTzMessage>{


	@Override
    public void action() {
//		if (player.getCountryManager().getTechBuildControl() != null) {
//        	player.getCountryManager().getTechBuildControl().reqBuildingWeaponTz(player, message);
//        }else{
//        	Language.ERRORCODE.send(player, ErrorCodeEnum.E100_COUNTRY.CODE14);
//        }
		
//		player.getCustomWeaponManager().createDesignMap(player, name, partBeanList, type, systemIndex, index, buildIndex)
	}
}
