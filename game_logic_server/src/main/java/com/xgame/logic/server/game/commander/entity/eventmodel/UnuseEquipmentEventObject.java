package com.xgame.logic.server.game.commander.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 卸下装备
 * @author jacky.jiang
 *
 */
public class UnuseEquipmentEventObject extends GameLogEventObject {

	
	public UnuseEquipmentEventObject(Player player, int type, int equipmentId, long userEquipmentId) {
		super(player, type);
		this.equipmentId = equipmentId;
		this.userEquipmentId = userEquipmentId;
	}

	private int equipmentId;
	
	private long userEquipmentId;

	public int getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(int equipmentId) {
		this.equipmentId = equipmentId;
	}

	public long getUserEquipmentId() {
		return userEquipmentId;
	}

	public void setUserEquipmentId(long userEquipmentId) {
		this.userEquipmentId = userEquipmentId;
	}

	@Override
	public String toString() {
		return "UnuseEquipmentEventObject [equipmentId=" + equipmentId
				+ ", userEquipmentId=" + userEquipmentId + "]";
	}
	
}
