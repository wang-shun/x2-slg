package com.xgame.logic.server.game.equipment.entity;

import io.protostuff.Tag;

import java.io.Serializable;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

/**
 * 植入体
 * @author kevin.ouyang
 *
 */
public class Equipment implements Serializable, JBaseTransform {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1397075399493169671L;
	
	@Tag(1)
	private long 	equipmentSequenceId;
	@Tag(2)
	private int 	equipmentId;
	@Tag(3)
	private boolean	isEquiped = false;

	/**
	 * 唯一ID
	 * @return
	 */
	public long getEquipmentSequenceId() {
		return equipmentSequenceId;
	}

	public void setEquipmentSequenceId(long equipmentSequenceId) {
		this.equipmentSequenceId = equipmentSequenceId;
	}

	/**
	 * 模板ID
	 * @return
	 */
	public int getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(int equipmentId) {
		this.equipmentId = equipmentId;
	}
	
	/**
	 * 装备是否被穿戴
	 * @return
	 */
	public boolean isEquiped() {
		return isEquiped;
	}

	public void setEquiped(boolean isEquiped) {
		this.isEquiped = isEquiped;
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jBaseData = new JBaseData();
		jBaseData.put("equipmentSequenceId", equipmentSequenceId);
		jBaseData.put("equipmentId", equipmentId);
		jBaseData.put("isEquiped", isEquiped);
		return jBaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.equipmentSequenceId = jBaseData.getLong("equipmentSequenceId", 0);
		this.equipmentId = jBaseData.getInt("equipmentId", 0);
		this.isEquiped = jBaseData.getBoolean("isEquiped", false);
	}
}

