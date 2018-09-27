package com.xgame.logic.server.game.country.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCallbackCommand;
import com.xgame.logic.server.game.radar.RadarBuildControl;
import com.xgame.logic.server.game.radar.message.ReqInvestigateInfoMessage;
import com.xgame.logic.server.game.radar.message.ResInvestigateInfoMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqInvestigateInfoHandler extends PlayerCallbackCommand<ReqInvestigateInfoMessage>{


    @Override
    public ResInvestigateInfoMessage callback() {
    	RadarBuildControl radarBuildControl = player.getCountryManager().getRadarBuildControl();
    	if(radarBuildControl!=null){
    		return radarBuildControl.reqRadarInfo(player, message.serverId, message.x, message.y);
    	}
    	return new ResInvestigateInfoMessage();
    }
}
