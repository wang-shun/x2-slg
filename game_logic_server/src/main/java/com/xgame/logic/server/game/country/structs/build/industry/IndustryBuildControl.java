package com.xgame.logic.server.game.country.structs.build.industry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xgame.config.costDiamond.CostDiamondPir;
import com.xgame.config.costDiamond.CostDiamondPirFactory;
import com.xgame.config.global.GlobalPir;
import com.xgame.config.global.GlobalPirFactory;
import com.xgame.config.items.ItemsPir;
import com.xgame.config.items.ItemsPirFactory;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.utils.CurrencyUtil;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.bag.ItemKit;
import com.xgame.logic.server.game.constant.CurrencyEnum;
import com.xgame.logic.server.game.constant.SystemEnum;
import com.xgame.logic.server.game.country.structs.build.CountryBuildControl;
import com.xgame.logic.server.game.equipment.bean.EquipmentOutput;
import com.xgame.logic.server.game.equipment.bean.fragmentData;
import com.xgame.logic.server.game.equipment.message.ResCancelFragmentResultMessage;
import com.xgame.logic.server.game.equipment.message.ResOutputEquipmentMessage;
import com.xgame.logic.server.game.equipment.message.ResProduceFragmentMessage;
import com.xgame.logic.server.game.equipment.message.ResProducingEquipmentMessage;
import com.xgame.logic.server.game.equipment.message.ResUnlockResultMessage;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.player.entity.eventmodel.BuildingRewardEventObject;
import com.xgame.logic.server.game.timertask.constant.TimerTaskCommand;
import com.xgame.logic.server.game.timertask.entity.job.TimerTaskHolder;
import com.xgame.logic.server.game.timertask.entity.job.data.ProduceFragmentTaskData;

public class IndustryBuildControl extends CountryBuildControl{
	public static final int OUTPUT_EQUIP = 2;
	public static final int OUTPUT_FRAGMENT = 1;
	
	
	/**
	 * 生产装备材料
	 * @param fragmentID
	 */
	public void produceFragment(Player player,int fragmentID, int uid) {
						
		//如果有队列在执行  反回
		if(TimerTaskHolder.getTimerTask(TimerTaskCommand.PRODUCE_FRAGMENT).checkQueueCapacityMax(player)){
			player.getEquipmentManager().addFragmentWaitingProduce(fragmentID);
			return;
		}
		
		CountryBuildControl countryBuildControl = player.getCountryManager().getIndustryBuildControl();
		if(countryBuildControl == null) {
			return;
		}
				
		ItemsPir itemconfigConfig = ItemsPirFactory.get(fragmentID);
		int quality = itemconfigConfig.getQuality();
		GlobalPir globalConfigStr = GlobalPirFactory.get(250001);
		
		int time = 0;
		String[] data = ((String) globalConfigStr.getValue()).split(",");
		for (int i = 0; i < data.length; i++) {
			String[] qualityToTimeStrings = data[i].split("_");
			if (Integer.parseInt(qualityToTimeStrings[0]) == quality) {
				time = Integer.parseInt(qualityToTimeStrings[1]);
				break;
			}
		}
				
		player.roleInfo().getEquipmentDataManager().setCurrentProducingFragmentID(fragmentID);
		InjectorUtil.getInjector().dbCacheService.update(player);
		
		TimerTaskHolder.getTimerTask(TimerTaskCommand.PRODUCE_FRAGMENT).register(player, time, new ProduceFragmentTaskData(fragmentID, uid));
	}
	
	/**
	 * 材料生产成功
	 * @param fragmentID
	 */
	public void produceFragmentEnd(Player player, int fragmentID, int uid) {
		// 生产材料
		List<Integer> fragmentIds = player.roleInfo().getEquipmentDataManager().getOutputFragment().get(uid);
		if(fragmentIds == null) {
			fragmentIds = new ArrayList<>();
			player.roleInfo().getEquipmentDataManager().getOutputFragment().put(uid, fragmentIds);
		}
		fragmentIds.add(fragmentID);	
		
		// 生产材料
		List<Integer> list = player.roleInfo().getEquipmentDataManager().getFragmentWaitingProduceID();
		if (list == null ||list.size() <= 0) {
			player.roleInfo().getEquipmentDataManager().setCurrentProducingFragmentID(0);
		}
		
		EquipmentOutput equipmentOutput = new EquipmentOutput();
		equipmentOutput.equipmentId = fragmentID;
		equipmentOutput.uid = uid;
		equipmentOutput.outputType = IndustryBuildControl.OUTPUT_FRAGMENT;
		
		ResOutputEquipmentMessage resOutputEquipmentMessage = new ResOutputEquipmentMessage();
		resOutputEquipmentMessage.equipmentOutput.add(equipmentOutput);
		player.send(resOutputEquipmentMessage);
	}
	
	/**
	 * 生产下一件装备
	 * @param player
	 * @param uid
	 */
	public void nextEquipment(Player player, int uid) {
		// 生产下一个装备
		List<Integer> list = player.roleInfo().getEquipmentDataManager().getFragmentWaitingProduceID();
		if(list != null && !list.isEmpty()) {
			produceFragment(player, list.get(0), uid);
			player.roleInfo().getEquipmentDataManager().setCurrentProducingFragmentID(list.get(0));
			player.roleInfo().getEquipmentDataManager().getFragmentWaitingProduceID().remove(0);
			InjectorUtil.getInjector().dbCacheService.update(player);	
		}
	}
	
	/**
	 * 取消生产装备材料
	 * @param player
	 * @param fragmentID
	 * @param index
	 */
	public void cancelProduceFragment(Player player, int fragmentID, int index) {
		List<Integer> list = player.roleInfo().getEquipmentDataManager().getFragmentWaitingProduceID();
		int indexFragment = -1;
		for (int i = 0; i < list.size(); i++) {
			if (i == (index-2) && list.get(i) == fragmentID) {
				indexFragment = i;
				break;
			}
		}
		
		Boolean cancelResultBoolean = false;
		if (indexFragment >= 0) {
			player.roleInfo().getEquipmentDataManager().getFragmentWaitingProduceID().remove(indexFragment);
			cancelResultBoolean = true;
		}
		
		ResCancelFragmentResultMessage resCancelFragmentResultMessage = new ResCancelFragmentResultMessage();
		resCancelFragmentResultMessage.isSuccess = cancelResultBoolean;
		resCancelFragmentResultMessage.fragmentID = fragmentID;
		resCancelFragmentResultMessage.index = index;
		
		player.send(resCancelFragmentResultMessage);
	}
	
	// 解锁生产队列的位置
	public void openLockPosition(Player player, int position) {
		
		CostDiamondPir costDiamondPir = CostDiamondPirFactory.get(position);
		if(costDiamondPir == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE18);
			return;
		}
		
		if (!CurrencyUtil.verify(player, costDiamondPir.getValue_1() , CurrencyEnum.DIAMOND)) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE16);
			return;
		}
		
		CurrencyUtil.decrement(player, costDiamondPir.getValue_1() , CurrencyEnum.DIAMOND, GameLogSource.UNLOCK_INDUSTRY_POSITION);
		CurrencyUtil.send(player);
		
		int index = player.roleInfo().getEquipmentDataManager().getUnlockPosition() + 1;
		player.roleInfo().getEquipmentDataManager().setUnlockPosition(index);
		
		ResUnlockResultMessage resUnlockResultMessage = new ResUnlockResultMessage();
		resUnlockResultMessage.isSuccess = true;
		resUnlockResultMessage.position = index;
		player.send(resUnlockResultMessage);
	}
	
	public void sendUnlockPosition(Player player){
		
		ResUnlockResultMessage resUnlockResultMessage = new ResUnlockResultMessage();
        resUnlockResultMessage.isSuccess = true;
		resUnlockResultMessage.position = player.roleInfo().getEquipmentDataManager().getUnlockPosition();
		player.send(resUnlockResultMessage);
	}
	
	public void createEnd(Player player,int uid) {
		super.createEnd(player, uid);
		sendUnlockPosition(player);
	}
	
	// 玩家上线下发消息
	public void send(Player player) {
		initData(player);
	}
	
	public void initData(Player player)
	{
		sendUnlockPosition(player);
		if (player.roleInfo().getEquipmentDataManager().getCurrentProducingFragmentID() > 0) {
			// 登录发送正在生产的材料ID
			List<fragmentData> list = new ArrayList<fragmentData>();
			
			fragmentData data = new fragmentData();
			data.index = 1;
			data.fragmentID = player.roleInfo().getEquipmentDataManager().getCurrentProducingFragmentID();
			list.add(data);
			
			List<Integer> fragmentIDList =  player.roleInfo().getEquipmentDataManager().getFragmentWaitingProduceID();
			for (int i = 0; i < fragmentIDList.size(); i++) {
				fragmentData fragmentData = new fragmentData();
				fragmentData.index = i+2;
				fragmentData.fragmentID = fragmentIDList.get(i);
				list.add(fragmentData);
			}
			
			ResProducingEquipmentMessage resProducingEquipmentMessage = new ResProducingEquipmentMessage();
			resProducingEquipmentMessage.producingID = player.roleInfo().getEquipmentDataManager().getCurrentProducingFragmentID();
			resProducingEquipmentMessage.fragments = list;
			player.send(resProducingEquipmentMessage);
		} 
		
		ResOutputEquipmentMessage resOutputEquipmentMessage = new ResOutputEquipmentMessage();
		Map<Integer, List<Integer>> map = player.roleInfo().getEquipmentDataManager().getOutputFragment();
		if(map != null && !map.isEmpty()) {
			for(Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
				for(Integer id : entry.getValue()) {
					EquipmentOutput equipmentOutput = new EquipmentOutput();
					equipmentOutput.uid = entry.getKey();
					equipmentOutput.equipmentId = id;
					equipmentOutput.outputType = OUTPUT_FRAGMENT;
					resOutputEquipmentMessage.equipmentOutput.add(equipmentOutput);
				}
			}
			player.send(resOutputEquipmentMessage);
		}
	}

	@Override
	public void giveOutput(Player player, int uid) {
		ResProduceFragmentMessage resProduceFragmentMessage = new ResProduceFragmentMessage();
		List<Integer> list = player.roleInfo().getEquipmentDataManager().getOutputFragment().get(uid);
		
		if(list != null && !list.isEmpty()) {
			for(Integer fragmentID : list) {
				ItemKit.addItemAndTopic(player, fragmentID, 1, SystemEnum.EQUIP, GameLogSource.INDUSTRY_OUTPUT);
				resProduceFragmentMessage.fragmentID.add(fragmentID);
			}
		}
		
		player.roleInfo().getEquipmentDataManager().getOutputFragment().clear();
		resProduceFragmentMessage.isSuccess = true;
		InjectorUtil.getInjector().dbCacheService.update(player);
		EventBus.getSelf().fireEvent(new BuildingRewardEventObject(player));
		player.send(resProduceFragmentMessage);
	}

}
