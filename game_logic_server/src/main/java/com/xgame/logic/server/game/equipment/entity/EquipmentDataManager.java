/**
 * 
 */
package com.xgame.logic.server.game.equipment.entity;

import io.protostuff.Tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.core.utils.JsonUtil;

/**
 * @author kevin.ouyang
 *
 */
public class EquipmentDataManager implements Serializable, JBaseTransform {

	private static final long serialVersionUID = 1L;
			
	@Tag(1)
	private List<Integer> fragmentWaitingProduceID = new ArrayList<Integer>();
	
	@Tag(2)
	private int unlockPosition;
	
	@Tag(3)
	private int currentProducingFragmentID;
	
	@Tag(4)
	private List<EquipmentWaitingBuilding> equipmentWaitingBuildings = new ArrayList<EquipmentWaitingBuilding>();
	
	/**
	 * 待收取装备列表
	 * <ul>
	 * 	<li> uid(建筑id)</li>
	 *  <li> 装备id</li>
	 * </ul>
	 */
	@Tag(5)
	private Map<Integer, Integer> outputEquipment = new HashMap<>();
	
	/**
	 * 待收取材料列表
	 */
	@Tag(6)
	private Map<Integer, List<Integer>> outputFragment = new HashMap<>();
	
	public EquipmentDataManager()
	{
		this.unlockPosition = 2;
		this.currentProducingFragmentID = 0;
	}

	public List<Integer> getFragmentWaitingProduceID() {
		return fragmentWaitingProduceID;
	}

	public void setFragmentWaitingProduceID(List<Integer> fragmentWaitingProduceID) {
		this.fragmentWaitingProduceID = fragmentWaitingProduceID;
	}

	public int getUnlockPosition() {
		return unlockPosition;
	}

	public void setUnlockPosition(int unlockPosition) {
		this.unlockPosition = unlockPosition;
	}

	public int getCurrentProducingFragmentID() {
		return currentProducingFragmentID;
	}

	public void setCurrentProducingFragmentID(int currentProducingFragmentID) {
		this.currentProducingFragmentID = currentProducingFragmentID;
	}

	public List<EquipmentWaitingBuilding> getEquipmentWaitingBuildings() {
		return equipmentWaitingBuildings;
	}

	public void setEquipmentWaitingBuildings(
			List<EquipmentWaitingBuilding> equipmentWaitingBuildings) {
		this.equipmentWaitingBuildings = equipmentWaitingBuildings;
	}

	public Map<Integer, Integer> getOutputEquipment() {
		return outputEquipment;
	}

	public void setOutputEquipment(Map<Integer, Integer> outputEquipment) {
		this.outputEquipment = outputEquipment;
	}

	public Map<Integer, List<Integer>> getOutputFragment() {
		return outputFragment;
	}

	public void setOutputFragment(Map<Integer, List<Integer>> outputFragment) {
		this.outputFragment = outputFragment;
	}


	@Override
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("fragmentWaitingProduceID", fragmentWaitingProduceID);
		jbaseData.put("unlockPosition", unlockPosition);
		jbaseData.put("currentProducingFragmentID", currentProducingFragmentID);
		
		List<JBaseData> equipmentWaitingBuildingsList = new ArrayList<JBaseData>();
		for (int i = 0; i < equipmentWaitingBuildings.size(); i++) {
			Object obj = equipmentWaitingBuildings.get(i);
			equipmentWaitingBuildingsList.add(((JBaseTransform)obj).toJBaseData());
		}
		jbaseData.put("equipmentWaitingBuildings", equipmentWaitingBuildingsList);
		
		jbaseData.put("outputEquipment", JsonUtil.toJSON(outputEquipment));
		jbaseData.put("outputFragment", JsonUtil.toJSON(outputFragment));
		
		return jbaseData;
	}
		
	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.fragmentWaitingProduceID = jBaseData.getSeqInt("fragmentWaitingProduceID");
		this.unlockPosition = jBaseData.getInt("unlockPosition", 0);
		this.currentProducingFragmentID = jBaseData.getInt("currentProducingFragmentID", 0);
		
		List<JBaseData> jBaseDatas = jBaseData.getSeqBaseData("equipmentWaitingBuildings");
		if(jBaseDatas != null) {
			for(JBaseData jBaseData2 : jBaseDatas) {
				EquipmentWaitingBuilding equipmentWaitingBuilding = new EquipmentWaitingBuilding();
				equipmentWaitingBuilding.fromJBaseData(jBaseData2);
				this.equipmentWaitingBuildings.add(equipmentWaitingBuilding);
			}
		}
		
		String outputEquipmentData= jBaseData.getString("outputEquipment", "");
		if(!StringUtils.isEmpty(outputEquipmentData)) {
			this.outputEquipment = JsonUtil.fromJSON(outputEquipmentData, Map.class);
		}
	
		String outputFragmentData= jBaseData.getString("outputFragment", "");
		if(!StringUtils.isEmpty(outputFragmentData)) {
			this.outputFragment = JsonUtil.fromJSON(outputFragmentData, Map.class);	
		}
	}
}



