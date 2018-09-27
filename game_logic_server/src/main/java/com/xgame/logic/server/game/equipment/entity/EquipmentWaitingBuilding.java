/**
 * 
 */
package com.xgame.logic.server.game.equipment.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

import io.protostuff.Tag;

/**
 * @author kevin.ouyang
 *
 */
public class EquipmentWaitingBuilding implements Serializable, JBaseTransform {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1397075399493169671L;
	
	@Tag(1)
	private int targetEquipmentID;
	
	@Tag(2)
	private List<Integer> subComposeEquipments = new ArrayList<Integer>();

	@Tag(3)
	private Map<Integer, EquipmentPositionInfo> materials = new ConcurrentHashMap<>();
	
	
	/**
	 * 目标装备ID
	 * @return
	 */
	public int getTargetEquipmentID() {
		return targetEquipmentID;
	}

	public void setTargetEquipmentID(int targetEquipmentID) {
		this.targetEquipmentID = targetEquipmentID;
	}
	
	/**
	 * 装备集合
	 * @return
	 */
	public List<Integer> getSubComposeEquipments() {
		return subComposeEquipments;
	}

	public void setSubComposeEquipments(List<Integer> subComposeEquipments) {
		this.subComposeEquipments = subComposeEquipments;
	}
	
	/**
	 * 装备材料的集合
	 * @return
	 */
	public Map<Integer, EquipmentPositionInfo> getMaterials() {
		return materials;
	}

	public void setMaterials(Map<Integer, EquipmentPositionInfo> materials) {
		this.materials = materials;
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("targetEquipmentID", targetEquipmentID);
		jbaseData.put("subComposeEquipments", subComposeEquipments);
		List<JBaseData> materialsJBaseList = new ArrayList<JBaseData>();
		
		for (Map.Entry<Integer, EquipmentPositionInfo> entry : materials.entrySet()) {
			materialsJBaseList.add(((JBaseTransform) entry.getValue()).toJBaseData());
		}
		jbaseData.put("materials", materialsJBaseList);
		return jbaseData;
	}
	
	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.targetEquipmentID = jBaseData.getInt("targetEquipmentID", 0);
		this.subComposeEquipments = jBaseData.getSeqInt("subComposeEquipments");
		
		List<JBaseData> materialsList = jBaseData.getSeqBaseData("materials");
		for(JBaseData innerBaseData : materialsList) {
			EquipmentPositionInfo equipmentPositionInfo = new EquipmentPositionInfo();
			equipmentPositionInfo.fromJBaseData(innerBaseData);
			this.materials.put(equipmentPositionInfo.getPosition(), equipmentPositionInfo);
		}
	}
}
