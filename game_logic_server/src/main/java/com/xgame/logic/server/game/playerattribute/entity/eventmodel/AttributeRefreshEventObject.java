package com.xgame.logic.server.game.playerattribute.entity.eventmodel;

import java.util.List;

import com.xgame.logic.server.core.gamelog.event.EventObject;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.game.player.entity.Player;

public class AttributeRefreshEventObject extends EventObject {

	private List<Integer> attrIds;

	public AttributeRefreshEventObject(Player player, List<Integer> attrIds) {
		super(player, EventTypeConst.EVENT_ATTRIBUTE_REFRESH);
		this.attrIds = attrIds;
	}
	
	public AttributeRefreshEventObject(Player player){
		super(player, EventTypeConst.EVENT_ATTRIBUTE_REFRESH);
	}

	public List<Integer> getAttrIds() {
		return this.attrIds;
	}
}