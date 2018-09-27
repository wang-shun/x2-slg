package com.xgame.logic.server.game.timertask.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.config.items.ItemsPir;
import com.xgame.config.items.ItemsPirFactory;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.language.view.success.SuccessTipEnum;
import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;
import com.xgame.logic.server.game.timertask.message.ReqUseSpeedItemMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqUseSpeedItemHandler extends PlayerCommand<ReqUseSpeedItemMessage> {
    @Override
    public void action() {
    	if(message.type == 0){
    		TimerTaskData timerTaskData = InjectorUtil.getInjector().dbCacheService.get(TimerTaskData.class, message.timerUid);
    		if(timerTaskData == null) {
    			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE23);
    			return;
    		}
    	}
    	
    	ItemsPir config = ItemsPirFactory.get(message.itemId);
		if(config == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE6.get(), ItemsPir.class.getSimpleName(), String.valueOf(message.itemId));
			return;
		}
    	player.getItemManager().useItem(message.itemUid, message.itemId, message.num, true,message.type, message.timerUid);
    	Language.SUCCESSTIP.send(player, SuccessTipEnum.E109_ITEM.CODE2.get());
    }
}
