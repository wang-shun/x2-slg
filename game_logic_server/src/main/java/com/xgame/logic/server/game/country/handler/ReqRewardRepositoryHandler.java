package com.xgame.logic.server.game.country.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.country.message.ReqRewardRepositoryMessage;
import com.xgame.logic.server.game.country.structs.build.mine.MineBuildControl;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqRewardRepositoryHandler extends PlayerCommand<ReqRewardRepositoryMessage>{
    @Override
    public void action() {
        MineBuildControl mineBuildControl = player.getCountryManager().getMineBuildControl();
        mineBuildControl.rewardRepository(player);
    }	
}
