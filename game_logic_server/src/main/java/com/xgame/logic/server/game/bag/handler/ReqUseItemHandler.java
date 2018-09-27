package com.xgame.logic.server.game.bag.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.config.items.ItemsPir;
import com.xgame.config.items.ItemsPirFactory;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.language.view.success.SuccessTipEnum;
import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.bag.constant.ItemIdConstant;
import com.xgame.logic.server.game.bag.entity.ItemFactory;
import com.xgame.logic.server.game.bag.message.ReqUseItemMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqUseItemHandler extends PlayerCommand<ReqUseItemMessage>{
    @Override
    public void action() {
		if (message.num <= 0) {
    		Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE15.get());
			return;
    	}
		
		ItemsPir config = ItemsPirFactory.get(message.itemId);
		if(config == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE6.get(), ItemsPir.class.getSimpleName(), String.valueOf(message.itemId));
			return;
		}
		
		ItemFactory itemFactory = ItemFactory.factory.get(config.getType());
		if(itemFactory == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE17.get(), ItemsPir.class.getSimpleName(), String.valueOf(message.itemId));
			return;
		}
		
		if(!itemFactory.getControl().canUse(player, message.itemId, message.num)) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE17.get()); 
			return;
		}
		//随机迁城道具做特殊处理
		if(message.itemId == ItemIdConstant.RANDOM_TRANSFER) {
			if(message.num != 1) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE10.get()); 
				return;
			}
			player.getWorldLogicManager().moveRandomCity(player);
		} else {
			// 使用道具
			if(player.getItemManager().useItem(message.id, message.itemId, message.num, true)) {
				Language.SUCCESSTIP.send(player, SuccessTipEnum.E109_ITEM.CODE1.get()); // 使用成功
			}
		}
    }
}
