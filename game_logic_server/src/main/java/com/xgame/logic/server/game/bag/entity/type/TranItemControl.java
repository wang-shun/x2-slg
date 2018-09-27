package com.xgame.logic.server.game.bag.entity.type;

import com.xgame.logic.server.game.bag.entity.ItemContext;
import com.xgame.logic.server.game.bag.entity.ItemControl;
import com.xgame.logic.server.game.player.entity.Player;

/**
 * 传送类道具 type=5
 * @author jacky.jiang
 *
 */
public class TranItemControl extends ItemControl {
	
	@Override
	public boolean use(Player player, int itemId, int num, ItemContext rewardContext, Object ... param){
		if(num < 1){
			return false;
		}
		return false;
	}
	
	public TranItemControl(int type) {
		super(type);
	}
}
