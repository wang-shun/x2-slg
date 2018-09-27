package com.xgame.logic.server.game.customweanpon.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.customweanpon.CustomWeaponManager;
import com.xgame.logic.server.game.customweanpon.message.ReqChangeDesignMapMessage;

/** 
 * 请求生产图纸
 * @author messageGenerator
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqChangeDesignMapHandler extends PlayerCommand<ReqChangeDesignMapMessage>{
	
	@Autowired
	private CustomWeaponManager customWeaponManager;
	
    @Override
    public void action() {
    	customWeaponManager.changeDesignMap(player, message.id, message.name, message.partList);
    }

}
