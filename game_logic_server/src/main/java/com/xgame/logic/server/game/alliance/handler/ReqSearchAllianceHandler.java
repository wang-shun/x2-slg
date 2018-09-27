package com.xgame.logic.server.game.alliance.handler;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.alliance.AllianceManager;
import com.xgame.logic.server.game.alliance.message.ReqSearchAllianceMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqSearchAllianceHandler extends PlayerCommand<ReqSearchAllianceMessage>{

	@Autowired
	private AllianceManager allianceManager;

	@Override
    public void action() {
		
		if(StringUtils.isBlank(message.allianceName) || message.allianceName.length() >= 20) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE1);
		}
		
		// 搜索联盟
		allianceManager.searchAlliance(player, message.allianceName);
    }
}
