package com.xgame.logic.server.game.country.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.country.structs.build.tach.TechBuildControl;
import com.xgame.logic.server.game.tech.message.ReqLevelUpTechMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqLevelUpTechHandler extends PlayerCommand<ReqLevelUpTechMessage>{


	@Override
    public void action() {
		TechBuildControl techBuildControl = player.getCountryManager().getTechBuildControl();
		if(techBuildControl!=null){
			player.getCountryManager().getTechBuildControl().techLevelUp(player, message.sid,message.useType);
		}else{
			Language.ERRORCODE.send(player, ErrorCodeEnum.E100_COUNTRY.CODE19);
		}
    }
}
