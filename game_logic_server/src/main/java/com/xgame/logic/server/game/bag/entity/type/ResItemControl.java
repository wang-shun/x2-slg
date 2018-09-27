package com.xgame.logic.server.game.bag.entity.type;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.config.items.ItemsPir;
import com.xgame.config.items.ItemsPirFactory;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.utils.CurrencyUtil;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.alliance.enity.PlayerAlliance;
import com.xgame.logic.server.game.bag.entity.ItemControl;
import com.xgame.logic.server.game.bag.entity.ItemContext;
import com.xgame.logic.server.game.player.entity.Player;

/**
 * 增加数值道具 type=1
 * @author lin.lin
 *
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ResItemControl extends ItemControl {
	
	
	public ResItemControl(int type) {
		super(type);
	}
	
	@Override
	public boolean use(Player player, int itemId, int num, ItemContext rewardContext, Object ... param) {
		if(num < 1) {
			return false;
		}
		
		ItemsPir config = ItemsPirFactory.get(itemId);
		int parseInt = Integer.parseInt(config.getV1());
		int v2 = Integer.parseInt(config.getV2()) * num;
		
		if (parseInt == 6) {//指挥官经验
			player.getCommanderManager().commanderUpLevel(v2);
			CurrencyUtil.send(player);
			return true;
		} else if (parseInt >= 1 && parseInt <= 5) {//货币
			CurrencyUtil.increaseFinal(parseInt, player, v2, GameLogSource.USE_ITEM);
			rewardContext.addCurrency(parseInt, v2);
			CurrencyUtil.send(player);
			return true;
		} else if (parseInt == 7) {//体力
			player.getCommanderManager().increaseEnergy(v2);
			rewardContext.addCurrency(parseInt, v2);
			CurrencyUtil.send(player);
			return true;
		} else if(parseInt == 8){//模拟器筹码
			//TODO
			CurrencyUtil.send(player);
			return true;
		} else if(parseInt == 9) {//军团贡献
			PlayerAlliance playerAlliance = player.getPlayerAllianceManager().getOrCreate(player.getId()); 
			if(playerAlliance != null) {
				playerAlliance.addDonate(v2);
				InjectorUtil.getInjector().dbCacheService.update(playerAlliance);
			}
			CurrencyUtil.send(player);
			return true;
		}
		return false;
	}
}
