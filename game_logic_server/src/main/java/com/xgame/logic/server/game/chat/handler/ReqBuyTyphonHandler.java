package com.xgame.logic.server.game.chat.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.config.fastPaid.FastPaidPir;
import com.xgame.config.fastPaid.FastPaidPirFactory;
import com.xgame.config.items.ItemsPir;
import com.xgame.config.items.ItemsPirFactory;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.core.utils.CurrencyUtil;
import com.xgame.logic.server.game.bag.ItemKit;
import com.xgame.logic.server.game.chat.message.ReqBuyTyphonMessage;
import com.xgame.logic.server.game.chat.message.ResBuyTyphonMessage;
import com.xgame.logic.server.game.constant.CurrencyEnum;
import com.xgame.logic.server.game.constant.SystemEnum;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqBuyTyphonHandler extends PlayerCommand<ReqBuyTyphonMessage>{

	@Override
	public void action() {
		//参数判断
		if(message.num < 1 || message.itemId < 1) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE15.get());
			return;
		}
		
		//配置判断
		ItemsPir itemsPir = ItemsPirFactory.get(message.itemId);
		if(itemsPir == null){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE6.get(),ItemsPir.class.getSimpleName(),message.itemId);
			return;
		}
		
		//钻石判断
		//获取快速购买价格
		FastPaidPir configModel = FastPaidPirFactory.get(message.itemId);
		if(configModel == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE6.get(),FastPaidPir.class.getSimpleName(),message.itemId);
			return;
		}
		if(!CurrencyUtil.verify(player, configModel.getPrice()*message.num , CurrencyEnum.DIAMOND)) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE16.get());
			return;
		}
		//扣钻石发道具
		CurrencyUtil.decrement(player, configModel.getPrice()*message.num , CurrencyEnum.DIAMOND, GameLogSource.EDIT_ALLIANCE_INFO);
		CurrencyUtil.send(player);
		ItemKit.addItemAndTopic(player, message.itemId, message.num, SystemEnum.FASTPAID, GameLogSource.FAST_PAID);
		
		ResBuyTyphonMessage resBuyTyphonMessage = new ResBuyTyphonMessage();
		resBuyTyphonMessage.success = 1;
		player.send(resBuyTyphonMessage);
	}
}
