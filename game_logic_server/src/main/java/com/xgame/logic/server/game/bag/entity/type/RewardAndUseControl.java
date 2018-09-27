package com.xgame.logic.server.game.bag.entity.type;

import com.xgame.config.items.ItemsPir;
import com.xgame.config.items.ItemsPirFactory;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.utils.CurrencyUtil;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.alliance.enity.PlayerAlliance;
import com.xgame.logic.server.game.bag.entity.ItemContext;
import com.xgame.logic.server.game.bag.entity.ItemControl;
import com.xgame.logic.server.game.player.entity.Player;

/**
 * 自动使用类道具 type=8
 * @author jacky.jiang
 *
 */
public class RewardAndUseControl extends ItemControl {

	public RewardAndUseControl(int type) {
		super(type);
	}

	@Override
	public boolean use(Player player, int itemId, int num, ItemContext rewardContext, Object... param) {
		if(num < 1) {
			return false;
		}
	
		if(param.length >0) {
			rewardContext = (ItemContext)param[0];
		}
		
		// 使用
		ItemsPir config = ItemsPirFactory.get(itemId);
		int parseInt = Integer.parseInt(config.getV1());
		int v2 = Integer.parseInt(config.getV2()) * num;
		
		if (parseInt == 6) {
			player.getCommanderManager().commanderUpLevel(v2);
			CurrencyUtil.send(player);
			return true;
		} else if (parseInt >= 1 && parseInt <= 5) {
			CurrencyUtil.increaseFinal(parseInt, player, v2, GameLogSource.USE_ITEM);
			rewardContext.addCurrency(parseInt, v2);
			CurrencyUtil.send(player);
			return true;
		} else if (parseInt == 7) {
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
