package com.xgame.logic.server.game.country.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.net.process.PlayerCallbackCommand;
import com.xgame.logic.server.game.country.structs.build.defenseSoldier.DefebseSoldierControl;
import com.xgame.logic.server.game.defensesoldier.message.ReqUnDefensSolderSetupMessage;
import com.xgame.logic.server.game.defensesoldier.message.ResUnDefensSolderSetupMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqUnDefensSolderSetupHandler extends PlayerCallbackCommand<ReqUnDefensSolderSetupMessage>{


    @Override
    public ResUnDefensSolderSetupMessage callback() {
    	DefebseSoldierControl soldierControl = player.getCountryManager().getDefebseSoldierControl();
    	if(soldierControl!=null){
    		return soldierControl.unSettingSoldier(player, message.buildUid);
    	}else{
    		Language.ERRORCODE.send(player, ErrorCodeEnum.E100_COUNTRY.CODE17);
    	}
    	return new ResUnDefensSolderSetupMessage();
    }
}
