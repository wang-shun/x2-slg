package com.xgame.logic.server.game.bag.entity;

import com.xgame.config.items.ItemsPir;
import com.xgame.config.items.ItemsPirFactory;
import com.xgame.logic.server.game.player.entity.Player;

/**
 *
 *2016-7-27  11:48:25
 *@author ye.yuan
 *
 */
public abstract class ItemControl {
	
	protected int type;
	
	public ItemControl(int type) {
		this.type=type;
	}
	
	public abstract boolean use(Player player, int itemId, int num, ItemContext rewardContext, Object ... param);
	

	public int getType() {
		return type;
	}
	
	/**
	 * 是否可以在背包内使用，直接通过item配置use字段判断
	 * @param player
	 * @param itemId
	 * @param num
	 * @param param
	 * @return
	 */
	public boolean canUse(Player player, int itemId, int num,Object ... param) {
		ItemsPir configModel = ItemsPirFactory.get(itemId);
		if(configModel.getUse() != 0){
			return true;
		}
		return false;
	}
	
}
