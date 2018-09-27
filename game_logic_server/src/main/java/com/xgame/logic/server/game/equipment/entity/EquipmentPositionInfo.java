/**
 * 
 */
package com.xgame.logic.server.game.equipment.entity;

import io.protostuff.Tag;

import java.io.Serializable;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

/**
 * @author kevin.ouyang
 *
 */
public class EquipmentPositionInfo implements Serializable, JBaseTransform {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1397075399493169671L;
	
	@Tag(1)
	private int equipmentFragmentID;
	
	@Tag(2)
	private int account;

	@Tag(3)
	private int position;

	public int getEquipmentFragmentID() {
		return equipmentFragmentID;
	}

	public void setEquipmentFragmentID(int equipmentFragmentID) {
		this.equipmentFragmentID = equipmentFragmentID;
	}

	public int getAccount() {
		return account;
	}

	public void setAccount(int account) {
		this.account = account;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("equipmentFragmentID",equipmentFragmentID);
		jbaseData.put("account",account);
		jbaseData.put("position",position);
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		this.equipmentFragmentID = jBaseData.getInt("equipmentFragmentID", 0);
		this.account = jBaseData.getInt("account", 0);
		this.position = jBaseData.getInt("position", 0);
	}
	
}
