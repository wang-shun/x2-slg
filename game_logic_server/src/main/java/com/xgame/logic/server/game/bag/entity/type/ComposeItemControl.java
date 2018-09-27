package com.xgame.logic.server.game.bag.entity.type;

import com.xgame.logic.server.game.bag.entity.ItemContext;
import com.xgame.logic.server.game.bag.entity.ItemControl;
import com.xgame.logic.server.game.player.entity.Player;

/**
 * 装备道具 type=9
 * 不通过背包调用，通过装备合成模块调用
 * @author jacky.jiang
 *
 */
public class ComposeItemControl extends ItemControl{
	
	public ComposeItemControl(int type) {
		super(type);
	}

	@Override
	public boolean use(Player player, int itemId, int num, ItemContext rewardContext, Object ... param) {
		if(num < 1) {
			return false;
		}
		return false;
	}
}
