package com.xgame.logic.server.core.utils.sequance;


import org.springframework.stereotype.Component;

import com.xgame.gameconst.DBKey;
import com.xgame.logic.server.core.utils.InjectorUtil;

@Component
public class ItemSequance {


	private long itemid = 0;

	private short _itemid = -1;

	private void initItemSequance() {
		itemid = (InjectorUtil.getInjector().redisClient.incr(DBKey.SEQUANCE_ITEM) + IdSequancePrefix.ITEM_ID) * 100000;
	}

	public void init() {
		initItemSequance();
	}

	private short addShortValue(short value) {
		value++;
		if (value == Short.MAX_VALUE) {
			value = 0;
		}
		return value;
	}

	public long genItemId() {
		_itemid = addShortValue(_itemid);
		if (_itemid == 0) {
			initItemSequance();
		}
		return itemid + _itemid;
	}
}
