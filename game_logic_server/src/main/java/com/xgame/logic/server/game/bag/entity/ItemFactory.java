package com.xgame.logic.server.game.bag.entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xgame.data.item.ItemType;
import com.xgame.logic.server.game.bag.entity.type.BuffItemControl;
import com.xgame.logic.server.game.bag.entity.type.BuffUseControl;
import com.xgame.logic.server.game.bag.entity.type.ChestsItemControl;
import com.xgame.logic.server.game.bag.entity.type.CommonItemControl;
import com.xgame.logic.server.game.bag.entity.type.ComposeItemControl;
import com.xgame.logic.server.game.bag.entity.type.FragmentItemControl;
import com.xgame.logic.server.game.bag.entity.type.ResItemControl;
import com.xgame.logic.server.game.bag.entity.type.RewardAndUseControl;
import com.xgame.logic.server.game.bag.entity.type.SpeedUpItemControl;
import com.xgame.logic.server.game.bag.entity.type.TranItemControl;



/**
 *
 *2016-7-28  10:59:28
 *@author ye.yuan
 *
 */
public enum ItemFactory {

	// 增加数值类道具
	RES_ITEM(new ResItemControl(ItemType.RESOURCE_ADD_ITEM.ordinal())),
	// 加速道具
	SPEED_TIEM(new SpeedUpItemControl(ItemType.SPEED_UP_ITEM.ordinal())),
	// buff 道具
	BUFF_TIEM(new BuffItemControl(ItemType.BUFF_ITEM.ordinal())),
	// buff 使用
	BUFF_USE_TIEM(new BuffUseControl(ItemType.BUFF_USE_ITEM.ordinal())),
	// 传送道具
	TRAN_TIEM(new TranItemControl(ItemType.TRANSFER_ITEM.ordinal())),
	// 普通
	COMMON_TIEM(new CommonItemControl(ItemType.COMMON_ITEM.ordinal())),
	// 宝箱
	CHESTS_TIEM(new ChestsItemControl(ItemType.TREASURE_ITEM.ordinal())),
	// 奖励直接使用道具
	REWARD_AND_USE_ITEM(new RewardAndUseControl(ItemType.REWARD_AND_USE_ITEM.ordinal())),
	// 合成类道具
	COMPOSE_ITEM(new ComposeItemControl(ItemType.COMPOSE_ITEM.ordinal())),
	//碎片合成
	FRAGMENT_ITEM(new FragmentItemControl(ItemType.FRAGMENT_ITEM.ordinal())),
	
	;
	public static final Map<Integer, ItemFactory> factory  = new ConcurrentHashMap<>();
	
	static{
		// 添加
		ItemFactory[] values = values();
		for(int i=0;i<values.length;i++){
			factory.put(values[i].control.type,values[i]);
		}
	}
	
	private ItemControl control;
	
	ItemFactory(ItemControl control){
		this.control=control;
	}

	public ItemControl getControl() {
		return control;
	}

	public void setControl(ItemControl control) {
		this.control = control;
	}
}
