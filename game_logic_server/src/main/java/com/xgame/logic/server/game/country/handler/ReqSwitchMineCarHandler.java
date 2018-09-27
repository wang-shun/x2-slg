package com.xgame.logic.server.game.country.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.country.bean.SwitchCarBean;
import com.xgame.logic.server.game.country.message.ReqSwitchMineCarMessage;
import com.xgame.logic.server.game.country.message.ResSwitchCarMessage;
import com.xgame.logic.server.game.country.structs.build.mine.MineCarControl;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqSwitchMineCarHandler extends PlayerCommand<ReqSwitchMineCarMessage>{
    @Override
    public void action() {
        MineCarControl mineCarControl = player.getCountryManager().getMineCarControl();
        for(SwitchCarBean switchCarBean : message.switchCarBean) {
        	 mineCarControl.mineCarSwitch(player, switchCarBean.uid, switchCarBean.resourceId);
        }
        
        //反悔切换
        ResSwitchCarMessage resSwitchCarMessage = new ResSwitchCarMessage();
        resSwitchCarMessage.switchCarResult.addAll(this.getMessage().switchCarBean);
        player.send(resSwitchCarMessage);
    }
}
