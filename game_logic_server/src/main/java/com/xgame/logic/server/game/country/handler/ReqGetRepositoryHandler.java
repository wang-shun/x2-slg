package com.xgame.logic.server.game.country.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.country.entity.MineRepository;
import com.xgame.logic.server.game.country.message.ReqGetRepositoryMessage;
import com.xgame.logic.server.game.country.message.ResMineRepositoryMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqGetRepositoryHandler extends PlayerCommand<ReqGetRepositoryMessage>{
    @Override
    public void action() {
        MineRepository mineRepository = player.roleInfo().getBaseCountry().getMineRepository();
        if(mineRepository != null) {
        	ResMineRepositoryMessage resMineRepositoryMessage = new ResMineRepositoryMessage();
        	resMineRepositoryMessage.oil = Double.valueOf(mineRepository.getOil()).longValue();
        	resMineRepositoryMessage.money = Double.valueOf(mineRepository.getMoney()).longValue();
        	resMineRepositoryMessage.rare = Double.valueOf(mineRepository.getRare()).longValue();
        	resMineRepositoryMessage.steel = Double.valueOf(mineRepository.getSteel()).longValue();
        	player.send(resMineRepositoryMessage);
        }
    }
}
