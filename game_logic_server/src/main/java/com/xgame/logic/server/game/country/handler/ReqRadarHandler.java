package com.xgame.logic.server.game.country.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.radar.RadarBuildControl;
import com.xgame.logic.server.game.radar.message.ReqRadarMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqRadarHandler extends PlayerCommand<ReqRadarMessage>{


	@Override
    public void action() {
		RadarBuildControl radarBuildControl = player.getCountryManager().getRadarBuildControl();
		if(radarBuildControl!=null){
			radarBuildControl.send(player);
		}
    }
	
}
