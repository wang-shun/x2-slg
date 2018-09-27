package com.xgame.logic.server.game.country.structs.build.Lab;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.xgame.logic.server.game.country.structs.build.CountryBuildControl;
import com.xgame.logic.server.game.country.structs.build.industry.IndustryBuildControl;
import com.xgame.logic.server.game.equipment.bean.EquipmentFragment;
import com.xgame.logic.server.game.equipment.bean.EquipmentOutput;
import com.xgame.logic.server.game.equipment.entity.EquipmentPositionInfo;
import com.xgame.logic.server.game.equipment.entity.EquipmentWaitingBuilding;
import com.xgame.logic.server.game.equipment.message.ResEquipmentBuildingInfoMessage;
import com.xgame.logic.server.game.equipment.message.ResOutputEquipmentMessage;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.timertask.constant.TimerTaskCommand;
import com.xgame.logic.server.game.timertask.entity.job.TimerTaskHolder;

/**
 * 实验室
 * @author jacky.jiang
 *
 */
public class LabBuildControl extends CountryBuildControl{

	@Override
	public void giveOutput(Player player, int uid) {
		player.getEquipmentManager().output(player, uid);
	}
	
	// 玩家上线下发消息
	public void send(Player player) {
		initData(player);
	}
	
	/**
	 * 实验室相关的数据
	 * @param player
	 */
	public void initData(Player player) {
		
		// 登录发送装备是否正在锻造
//		ResEquipmentBuidingResultMessage resEquipmentBuidingResultMessage = new ResEquipmentBuidingResultMessage();
//		resEquipmentBuidingResultMessage.isBuilding = TimerTaskHolder.getTimerTask(TimerTaskCommand.PRODUCE_EQUIPMENT).checkQueueCapacityMax(player);
//		player.send(resEquipmentBuidingResultMessage);
		
		// 发送正在锻造的材料信息
		if (TimerTaskHolder.getTimerTask(TimerTaskCommand.PRODUCE_EQUIPMENT).checkQueueCapacityMax(player)) {
			List<EquipmentWaitingBuilding> equipmentWaitingBuilding = player.roleInfo().getEquipmentDataManager().getEquipmentWaitingBuildings();
			ResEquipmentBuildingInfoMessage resEquipmentBuildingInfoMessage = new ResEquipmentBuildingInfoMessage();
			resEquipmentBuildingInfoMessage.EquipmentIDs = equipmentWaitingBuilding.get(0).getSubComposeEquipments();
			List<EquipmentFragment> equipmentFragments = new ArrayList<EquipmentFragment>();
			
			for (Entry<Integer, EquipmentPositionInfo> entry : equipmentWaitingBuilding.get(0).getMaterials().entrySet()) {
				EquipmentFragment equipmentFragment = new EquipmentFragment();
				equipmentFragment.itemID = entry.getValue().getEquipmentFragmentID();
				equipmentFragment.account = entry.getValue().getAccount();
				equipmentFragment.position = entry.getValue().getPosition();
				
				equipmentFragments.add(equipmentFragment);
			}
			
			resEquipmentBuildingInfoMessage.EquipmentFragments = equipmentFragments;
			resEquipmentBuildingInfoMessage.EquipmentID = equipmentWaitingBuilding.get(0).getTargetEquipmentID();
			player.send(resEquipmentBuildingInfoMessage);
		} else {
			ResOutputEquipmentMessage resOutputEquipmentMessage = new ResOutputEquipmentMessage();
			Map<Integer, Integer> map = player.roleInfo().getEquipmentDataManager().getOutputEquipment();
			if(map != null && !map.isEmpty()) {
				for(Map.Entry<Integer, Integer> entry : map.entrySet()) {
					EquipmentOutput equipmentOutput = new EquipmentOutput();
					equipmentOutput.uid = entry.getKey();
					equipmentOutput.equipmentId = entry.getValue();
					equipmentOutput.outputType = IndustryBuildControl.OUTPUT_EQUIP;
					resOutputEquipmentMessage.equipmentOutput.add(equipmentOutput);
				}
				player.send(resOutputEquipmentMessage);
			}
		}
	}
	
}
