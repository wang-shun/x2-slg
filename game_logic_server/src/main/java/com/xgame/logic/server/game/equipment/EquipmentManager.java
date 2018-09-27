package com.xgame.logic.server.game.equipment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.xgame.config.attribute.AttributeConfMap;
import com.xgame.config.attribute.LibraryConf;
import com.xgame.config.equipment.EquipmentPir;
import com.xgame.config.equipment.EquipmentPirFactory;
import com.xgame.config.fastPaid.FastPaidPir;
import com.xgame.config.fastPaid.FastPaidPirFactory;
import com.xgame.config.global.GlobalPirFactory;
import com.xgame.config.items.ItemsPir;
import com.xgame.config.items.ItemsPirFactory;
import com.xgame.logic.server.core.component.AbstractComponent;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum.E300_EQUIP;
import com.xgame.logic.server.core.language.view.success.SuccessTipEnum;
import com.xgame.logic.server.core.utils.CurrencyUtil;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.sequance.EquipmentSequence;
import com.xgame.logic.server.game.bag.ItemKit;
import com.xgame.logic.server.game.bag.entity.Item;
import com.xgame.logic.server.game.bag.entity.ItemFactory;
import com.xgame.logic.server.game.bag.entity.eventmodel.GetImplantedEventObject;
import com.xgame.logic.server.game.bag.entity.eventmodel.ItemChangeEventObject;
import com.xgame.logic.server.game.bag.entity.eventmodel.MaterialsProductionEventObject;
import com.xgame.logic.server.game.constant.CurrencyEnum;
import com.xgame.logic.server.game.constant.SystemEnum;
import com.xgame.logic.server.game.country.structs.build.industry.IndustryBuildControl;
import com.xgame.logic.server.game.equipment.bean.ComposeEquipmentMaterial;
import com.xgame.logic.server.game.equipment.bean.EquipmentFragment;
import com.xgame.logic.server.game.equipment.bean.EquipmentOutput;
import com.xgame.logic.server.game.equipment.bean.EquipmentUID;
import com.xgame.logic.server.game.equipment.bean.SubComposeEquipment;
import com.xgame.logic.server.game.equipment.constant.EquipmentQuality;
import com.xgame.logic.server.game.equipment.entity.Equipment;
import com.xgame.logic.server.game.equipment.entity.EquipmentPositionInfo;
import com.xgame.logic.server.game.equipment.entity.EquipmentWaitingBuilding;
import com.xgame.logic.server.game.equipment.entity.eventmodel.ImplantedCompoundEventObject;
import com.xgame.logic.server.game.equipment.entity.eventmodel.ImplantedProduceEventObject;
import com.xgame.logic.server.game.equipment.message.ResBuildEquipmentResultMessage;
import com.xgame.logic.server.game.equipment.message.ResBuyFragmentMessage;
import com.xgame.logic.server.game.equipment.message.ResBuySpecialEquipmentResultMessage;
import com.xgame.logic.server.game.equipment.message.ResDecomposeEquipmentMessage;
import com.xgame.logic.server.game.equipment.message.ResEquipmentBuidingResultMessage;
import com.xgame.logic.server.game.equipment.message.ResEquipmentBuildingInfoMessage;
import com.xgame.logic.server.game.equipment.message.ResFragmentComposeResultMessage;
import com.xgame.logic.server.game.equipment.message.ResLevelUpEquipmentQualityResultMessage;
import com.xgame.logic.server.game.equipment.message.ResModifyEquipmentResultMessage;
import com.xgame.logic.server.game.equipment.message.ResOutputEquipmentMessage;
import com.xgame.logic.server.game.notice.NoticeManager;
import com.xgame.logic.server.game.notice.constant.NoticeConstant;
import com.xgame.logic.server.game.player.bean.MsgEquipment;
import com.xgame.logic.server.game.player.bean.MsgPlayerEquipmentBag;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.player.entity.PlayerBag;
import com.xgame.logic.server.game.player.entity.eventmodel.BuildingRewardEventObject;
import com.xgame.logic.server.game.playerattribute.AttributeUtil;
import com.xgame.logic.server.game.playerattribute.IAttributeAddModule;
import com.xgame.logic.server.game.playerattribute.constant.AttributeFromEnum;
import com.xgame.logic.server.game.playerattribute.constant.AttributeNodeEnum;
import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;
import com.xgame.logic.server.game.timertask.constant.TimerTaskCommand;
import com.xgame.logic.server.game.timertask.entity.job.TimerTaskHolder;
import com.xgame.logic.server.game.timertask.entity.job.data.ProduceEquipmentTaskData;



@Component
@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EquipmentManager extends AbstractComponent<Player> implements IAttributeAddModule{
	
	@Autowired
	private NoticeManager noticeManager;
	
	/**
	 * 添加装备
	 * @param equipmentID
	 */
	public void addEquipment(int equipmentID, GameLogSource gameLogSource) {
		EquipmentPir equipmentPir = EquipmentPirFactory.get(equipmentID);
		if (equipmentPir == null) {
			Language.ERRORCODE.send(this.getPlayer(), ErrorCodeEnum.E001_LOGIN.CODE6.get(), EquipmentPir.class.getSimpleName(), String.valueOf(equipmentID));
			return;
		}
		
		Equipment equipment = createEquipment(equipmentPir, this.getPlayer().getEquipmentSequence());
		this.getPlayer().roleInfo().getPlayerBag().getEquipmentMap().put(equipment.getEquipmentSequenceId(), equipment);
		InjectorUtil.getInjector().dbCacheService.update(this.getPlayer());
		int totalNum = 0;
		for(Equipment ep : this.getPlayer().roleInfo().getPlayerBag().getEquipmentMap().values()){
			EquipmentPir epi = EquipmentPirFactory.get(ep.getEquipmentId());
			if(epi.getQuality() == equipmentPir.getQuality()){
				totalNum++;
			}
		}
		EventBus.getSelf().fireEvent(new ItemChangeEventObject(equipmentID, this.getPlayer(), 0, 1, ItemChangeEventObject.ADD, gameLogSource, equipment.getEquipmentSequenceId(),ItemChangeEventObject.EQUIPMENT_TYPE));
		EventBus.getSelf().fireEvent(new GetImplantedEventObject(this.getPlayer(),equipmentID,1,totalNum));
		// 这里通知客户端玩家获得的装备
		ResModifyEquipmentResultMessage resModifyEquipmentResultMessage = new ResModifyEquipmentResultMessage();
		resModifyEquipmentResultMessage.id = equipment.getEquipmentSequenceId();
		resModifyEquipmentResultMessage.equipmentId = equipment.getEquipmentId();
		resModifyEquipmentResultMessage.isEquiped = equipment.isEquiped();
		resModifyEquipmentResultMessage.isAdd = true;
		this.getPlayer().send(resModifyEquipmentResultMessage);
	}
	
	/**
	 * 删除玩家装备
	 * 
	 * @param id
	 * @param num
	 */
	private void removeEquipment(long equipmentSequenceId, GameLogSource gameLogSource) {
		Equipment equipment = getPlayerEquipment(equipmentSequenceId);
		if (equipment != null) {
			this.getPlayer().roleInfo().getPlayerBag().getEquipmentMap().remove(equipment.getEquipmentSequenceId());
			InjectorUtil.getInjector().dbCacheService.update(this.getPlayer());
			
			// 道具移除事件
			EventBus.getSelf().fireEvent(new ItemChangeEventObject(equipment.getEquipmentId(), this.getPlayer(), 1, 0, ItemChangeEventObject.REMOVE, gameLogSource, equipment.getEquipmentSequenceId(),ItemChangeEventObject.EQUIPMENT_TYPE));
			
			// 这里通知客户端删除玩家的装备
			ResModifyEquipmentResultMessage resModifyEquipmentResultMessage = new ResModifyEquipmentResultMessage();
			resModifyEquipmentResultMessage.id = equipment.getEquipmentSequenceId();
			resModifyEquipmentResultMessage.equipmentId = equipment.getEquipmentId();
			resModifyEquipmentResultMessage.isEquiped = equipment.isEquiped();
			resModifyEquipmentResultMessage.isAdd = false;
			this.getPlayer().send(resModifyEquipmentResultMessage);
		}
	}
	
	/**
	 * 合成装备(升级装备的品质)
	 * @param player
	 * @param equipmentMap
	 */
	public void composeEquipment(List<EquipmentUID> idList, Integer equipmentID) {
		// 获取合成目标装备的唯一ID
		if(idList == null || idList.size() < 4) {
			Language.ERRORCODE.send(this.getPlayer(), ErrorCodeEnum.E300_EQUIP.CODE4.get());
			return;
		}
		
		EquipmentPir configModel = EquipmentPirFactory.get(equipmentID);
		// 找不到合成的目标，装备合成失败
		if (configModel == null) {
			Language.ERRORCODE.send(this.getPlayer(), ErrorCodeEnum.E001_LOGIN.CODE6.get(), EquipmentPir.class.getSimpleName(), String.valueOf(equipmentID));
			return;
		}
		
		// 装备品质异常
		if(configModel.getQuality() < EquipmentQuality.WHITE || configModel.getQuality() >= EquipmentQuality.RED) {
			Language.ERRORCODE.send(this.getPlayer(), ErrorCodeEnum.E001_LOGIN.CODE15.get());
			return;	
		}
		
		Iterator<EquipmentUID> equipmentIterator = idList.iterator();
		while(equipmentIterator.hasNext()) {
			EquipmentUID equipmentUID = (EquipmentUID) equipmentIterator.next();
			
			Equipment equipment = getPlayerEquipment(equipmentUID.id);
			if (equipment == null) {
				Language.ERRORCODE.send(this.getPlayer(), ErrorCodeEnum.E300_EQUIP.CODE3.get());
				return;
			}
			
			EquipmentPir checkConfig = EquipmentPirFactory.get(equipment.getEquipmentId());
			
			// 等级品质限制
			if(checkConfig == null || checkConfig.getLevel() != configModel.getLevel() || checkConfig.getQuality() != configModel.getQuality() - 1) {
				Language.ERRORCODE.send(this.getPlayer(), ErrorCodeEnum.E300_EQUIP.CODE5.get());
				return;
			}
		}
		
		// 删除用于合成目标装备的材料
		for (int i = 0; i < idList.size(); i++) {
			removeEquipment(idList.get(i).id, GameLogSource.COMPOSE_EQUIPMENT);
		}
		
		// 添加目标装备到背包
		addEquipment(equipmentID, GameLogSource.COMPOSE_EQUIPMENT);
		InjectorUtil.getInjector().dbCacheService.update(this.getPlayer());
		
		// 合成成功通知客户端
		ResLevelUpEquipmentQualityResultMessage result = new ResLevelUpEquipmentQualityResultMessage();
		result.isSuccess = true;
		this.getPlayer().send(result);
	}
	
	/**
	 * 分解装备
	 * @param equipmentSequenceId
	 */
	public void decompose(Player player, long equipmentSequenceId) {
		
		Equipment equipment = getPlayerEquipment(equipmentSequenceId);
		if (equipment == null) {
			Language.ERRORCODE.send(this.getPlayer(), ErrorCodeEnum.E300_EQUIP.CODE3.get());
			return ;
		}
		
		EquipmentPir configModel = EquipmentPirFactory.get(equipment.getEquipmentId());
		
		// 删除装备
		removeEquipment(equipmentSequenceId, GameLogSource.DECOMPOSE_EQUIPMENT);
		
		int quality = configModel.getQuality();
		int composeNum = configModel.getCompose();
		
		// 获取类型为9的同品质的道具
		int type = ItemFactory.COMPOSE_ITEM.getControl().getType();
		List<ItemsPir> itemsConfig = ItemsPirFactory.getInstance().getItemsByTypeAndQuality(type, quality);
		
		// 添加分解后的材料
		for (int i = 0; i < composeNum; i++) {
			java.util.Random random = new java.util.Random();
			int index = random.nextInt(itemsConfig.size());
			
			ItemKit.addItemAndTopic(player, itemsConfig.get(index).getId(), 1, SystemEnum.EQUIP, GameLogSource.DECOMPOSE_EQUIPMENT);
		}
		
		InjectorUtil.getInjector().dbCacheService.update(this.getPlayer());
				
		ResDecomposeEquipmentMessage resDecomposeEquipmentMessage = new ResDecomposeEquipmentMessage();
		resDecomposeEquipmentMessage.isSuccess = true;
		this.getPlayer().send(resDecomposeEquipmentMessage);
	}
	
//	/**
//	 * 添加装备碎片
//	 * @param player
//	 * @param itemId
//	 * @param num
//	 */
//	public void addEquipmentFragment(int itemId, int num) {		
//		
//		PlayerBag bag = this.getProduct().roleInfo().playerBag;
//		
//		boolean isExist = false; // 是否已有装备材料
//		Item itemTarget = null;  
//		
//		for (Item item : bag.getEquipmentFragmentMap().values()) {
//			if (item.getItemId() == itemId)
//			{
//				isExist = true;
//				item.setNum(item.getNum() + num);
//				itemTarget = item; 
//				break;
//			}
//		}
//		
//		if (! isExist)
//		{
//			Item item = Item.valueOf(itemId, num, this.getProduct().getSequance().genItemId());
//			bag.getEquipmentFragmentMap().put(item.getId(), item);
//			itemTarget = item;
//		}
//		
////		ItemLog itemLog = new ItemLog(itemTarget.getId(), itemTarget.getItemId(), num, player, LogFactory.ITEM_ADD);
////		player.getGameLogSystem().execute(itemLog);
//		
//		// 这里发送添加装备材料消息
//		List<Item> list = Lists.newArrayList();
//		list.add(itemTarget);
//		this.getProduct().send(ItemConverter.getMsgItems(list));
//	}
	
//	/**
//	 * 减少装备碎片
//	 * @param player
//	 * @param id
//	 * @param num
//	 */
//	public void reduceEquipmentFragment(Player player,long id, int num) {		
//		
//		Item fragmentItem = getPlayerFragment(id);
//		int totalNum = fragmentItem.getNum() - num;
//		
//		
//		Item targetItem = null;
//		targetItem = player.roleInfo().playerBag.getEquipmentFragmentMap().get(id);
//		targetItem.setNum(totalNum);
//		
//		if (totalNum == 0) {
//			player.roleInfo().playerBag.getEquipmentFragmentMap().remove(id);
//		}
//		
////		ItemLog itemLog = new ItemLog(fragmentItem.getId(), fragmentItem.getItemId(), num, player, LogFactory.ITEM_ADD);
////		player.getGameLogSystem().execute(itemLog);
//		
//		// 这里发送添加装备材料消息
//		List<Item> list = Lists.newArrayList();
//		list.add(targetItem);
//		player.send(ItemConverter.getMsgItems(list));
//	}
	
	/**
	 * 购买装备材料
	 * @param player
	 * @param fragmentID
	 * @param num
	 */
	public void buyFragment(Integer fragmentID, Integer num) {
		
		FastPaidPir configModel = FastPaidPirFactory.get(fragmentID);
				
		// 购买失败
		if (configModel == null) {
			Language.ERRORCODE.send(this.getPlayer(), ErrorCodeEnum.E001_LOGIN.CODE6.get(), FastPaidPir.class.getSimpleName(), String.valueOf(fragmentID));
			return;
		}
		
		int totaCostDiamond = configModel.getPrice() * num;
		
		//验证钻石是否足够
		if (! CurrencyUtil.verify(this.getPlayer(), totaCostDiamond , CurrencyEnum.DIAMOND)) {
			Language.ERRORCODE.send(this.getPlayer(), ErrorCodeEnum.E001_LOGIN.CODE16.get());
			return;
		}
			 
		
		CurrencyUtil.decrement(this.getPlayer(), totaCostDiamond , CurrencyEnum.DIAMOND, GameLogSource.BUY_FRAGMENT);
		CurrencyUtil.send(this.getPlayer());
		
		//添加道具
		ItemKit.addItemAndTopic(this.getPlayer(), fragmentID, num, SystemEnum.EQUIP, GameLogSource.BUY_FRAGMENT);
		InjectorUtil.getInjector().dbCacheService.update(this.getPlayer());
		
		// FIXME 购买道具日报事件
		ResBuyFragmentMessage resBuyFragmentMessage = new ResBuyFragmentMessage();
		resBuyFragmentMessage.isSuccess = true;
		this.getPlayer().send(resBuyFragmentMessage);
	}
	
	/**
	 * 购买万能装备
	 * @param player
	 * @param equipmentID
	 * @param num
	 */
	public void buySpecialEquipment(Integer equipmentID, Integer num) {
		
		FastPaidPir configModel = FastPaidPirFactory.get(equipmentID);
				
		// 购买失败
		if (configModel == null) {
			Language.ERRORCODE.send(this.getPlayer(), ErrorCodeEnum.E001_LOGIN.CODE6.get(), FastPaidPir.class.getSimpleName(), String.valueOf(equipmentID));
			return;
		}
		
		//　
		int totaCostDiamond = configModel.getPrice() * num;
		if(totaCostDiamond <=0) {
			Language.ERRORCODE.send(this.getPlayer(), ErrorCodeEnum.E001_LOGIN.CODE16.get());
			return;
		}
		
		//验证钻石是否足够
		if (!CurrencyUtil.verify(this.getPlayer(), totaCostDiamond , CurrencyEnum.DIAMOND)) {
			Language.ERRORCODE.send(this.getPlayer(), ErrorCodeEnum.E001_LOGIN.CODE16.get());
			return;
		}
		
		CurrencyUtil.decrement(this.getPlayer(), totaCostDiamond, CurrencyEnum.DIAMOND, GameLogSource.BUY_SPECIAL_EQUIPMENT);
		CurrencyUtil.send(this.getPlayer());
		
		//添加道具
		for (int i = 0; i < num; i++) {
			addEquipment(equipmentID, GameLogSource.BUY_SPECIAL_EQUIPMENT);
		}
		
		ResBuySpecialEquipmentResultMessage resBuySpecialEquipmentResultMessage = new ResBuySpecialEquipmentResultMessage();
		resBuySpecialEquipmentResultMessage.isSuccess = true;
		this.getPlayer().send(resBuySpecialEquipmentResultMessage);
	}
	
	/**
	 * 装备和装备材料合成装备
	 * @param subComposeEquipments
	 * @param materials
	 */
	public void composeEquipmentByFragmentAndEquipment(List<SubComposeEquipment> subComposeEquipments, List<ComposeEquipmentMaterial> materials, 
			boolean immediately, Integer fragmentID, int buildId) {
		
		// 判断玩家是否钻石足够
		EquipmentPir configModel = EquipmentPirFactory.get(fragmentID);	
		if(configModel == null) {
			Language.ERRORCODE.send(this.getPlayer(), E300_EQUIP.CODE2.get());
			return;
		}
		
		if(subComposeEquipments == null || subComposeEquipments.size() > 999) {
			Language.ERRORCODE.send(this.getPlayer(), E300_EQUIP.CODE2.get());
			return;
		}
		
		boolean isFullOfCondition = true;
		
		Map<Integer, Integer> equipMap = new HashMap<>();
		equipMap.putAll(configModel.getConsumable_equipment());
		for (int i = 0; i < subComposeEquipments.size(); i++) {
			long equipmentUID = subComposeEquipments.get(i).id;
			Equipment equipment = getPlayerEquipment(equipmentUID);
			if (equipment == null) {
				isFullOfCondition = false;
				break;
			}
		
			EquipmentPir equipmentPir = EquipmentPirFactory.get(equipment.getEquipmentId());
			if(equipmentPir == null) {
				isFullOfCondition = false;
			}
			
			Integer num = equipMap.get(equipmentPir.getLevel());
			if(num == null) {
				isFullOfCondition = false;
			}
			
			if(num - 1 <=0) {
				equipMap.remove(equipmentPir.getLevel());
			} else {
				equipMap.put(equipmentPir.getLevel(), num - 1);
			}
		}
		
		if(equipMap != null && !equipMap.isEmpty()) {
			isFullOfCondition = false;
		}
		
		// 判断所有的装备和材料在背包中已经存在
		if (!isFullOfCondition) {
			Language.ERRORCODE.send(this.getPlayer(), E300_EQUIP.CODE2.get());
			return;
		}
		
		Map<Integer, Integer> materialMap = new HashMap<>();
		materialMap.putAll(configModel.getConsumable_materials());
		for (int i = 0; i < materials.size(); i++) {
			ComposeEquipmentMaterial fragment = materials.get(i);
			Item fragmentItem = getPlayerFragment(fragment.id);

			if (fragmentItem == null) {
				isFullOfCondition = false;
				break;
			}
			
			int totalNum = fragmentItem.getNum() - fragment.account;
			if (totalNum < 0) {
				isFullOfCondition = false;
				break;
			}
			
			Entry<Integer, Integer> entry = getItemPirList(materialMap, configModel, fragmentItem.getItemId());
			if(entry == null || entry.getValue() > fragment.account) {
				isFullOfCondition = false;
				break;
			}
			
			materialMap.remove(entry.getKey());
		}
		
		// 材料
		if(materialMap != null && !materialMap.isEmpty()) {
			isFullOfCondition = false;
		}
		
		// 判断所有的装备和材料在背包中已经存在
		if (!isFullOfCondition) {
			Language.ERRORCODE.send(this.getPlayer(), E300_EQUIP.CODE2.get());
			return;
		}
		
		// 立即锻造不需要时间
		if (immediately) {
			if (!CurrencyUtil.decrementCurrency(this.getPlayer(), configModel.getCost_cash(), 0, 0, 0, CurrencyUtil.FAST_USE, configModel.getTime(), GameLogSource.COMPOSE_EQUIPMENT)) {
				Language.ERRORCODE.send(this.getPlayer(), ErrorCodeEnum.E001_LOGIN.CODE11.get());
				return;
			};
		// 普通锻造不需要时间
		} else {
			// 获取装备
			Integer equipId = this.getPlayer().roleInfo().getEquipmentDataManager().getOutputEquipment().get(buildId);
			if(equipId != null) {
				Language.ERRORCODE.send(this.getPlayer(), E300_EQUIP.CODE1.get());
				return;
			}
			
			// 判断钢材是否足够
			if(!CurrencyUtil.decrementCurrency(this.getPlayer(), 0, configModel.getCost_cash(), 0, 0, CurrencyUtil.FAST_USE, 0, GameLogSource.COMPOSE_EQUIPMENT)) {
				Language.ERRORCODE.send(this.getPlayer(), ErrorCodeEnum.E001_LOGIN.CODE11.get());
				return;
			}
		}
		
		EquipmentWaitingBuilding equipmentWaitingBuilding = new EquipmentWaitingBuilding();
		// 扣除装备
		for (int i = 0; i < subComposeEquipments.size(); i++) {
			long equipmentUID = subComposeEquipments.get(i).id;
			
			Equipment equipment = getPlayerEquipment(equipmentUID);
			removeEquipment(equipmentUID, GameLogSource.FORGING_EQUIPMENT);
			
			equipmentWaitingBuilding.getSubComposeEquipments().add(equipment.getEquipmentId());
		}
		
		//合成装备 概率公式重算
		int[] data = GlobalPirFactory.getInstance().composeEquipmentQuality;
		//品质 数目键值对
		Map<Integer, Integer> map = new HashMap<Integer, Integer>() ;
		
		// 扣除材料
		for (int i = 0; i < materials.size(); i++) {
			ComposeEquipmentMaterial fragment = materials.get(i);
			Item fragmentItem = getPlayerFragment(fragment.id);
			if(fragmentItem == null) {
				continue;
			}
			
			//扣除植入体
			ItemKit.removeItemByUid(this.getPlayer(), fragment.id, fragment.account, GameLogSource.FORGING_EQUIPMENT);
			 
			// 锻造的材料存储
			EquipmentPositionInfo equipmentPositionInfo = new EquipmentPositionInfo();
			equipmentPositionInfo.setAccount(fragment.account);
			equipmentPositionInfo.setEquipmentFragmentID(fragmentItem.getItemId());
			equipmentPositionInfo.setPosition(fragment.position);
			
			equipmentWaitingBuilding.getMaterials().put(fragment.position, equipmentPositionInfo);
						
			ItemsPir itemConfig = ItemsPirFactory.get(fragmentItem.getItemId());
			int quality = itemConfig.getQuality();
			
			if (map.containsKey(quality)) {
				int add = fragment.account + map.get(quality);
				map.put(quality, add);
			} else {
				map.put(quality, fragment.account);
			}
		}
		int totalWeight = 0;
		//品质 权重键值对
		Map<Integer, Integer> weightMap = new HashMap<Integer, Integer>() ;
		for(int i = 0;i<data.length;i++){
			//将minlv ,maxlv外所有添加材料数为0的材料+1
			weightMap.put(i+1, map.get(i+1)!=null?map.get(i+1)*data[i]:i!=0&&i!=data.length-1?data[i]:0);
			totalWeight += map.get(i+1)!=null?map.get(i+1)*data[i]:i!=0&&i!=data.length-1?data[i]:0;
		}
		
		// 取随机数
		java.util.Random rd = new java.util.Random();
		double rate = rd.nextDouble() * 100;
		
		//实际产生的品质
		double percent = 0;
		int realQuality = 0;
		
		for (Map.Entry<Integer, Integer> entry : weightMap.entrySet()) {
			percent = percent + entry.getValue() / Double.valueOf(totalWeight);
			if (rate <= (percent * 100)) {
				realQuality = entry.getKey();
				break;
			}
		}
		
		Map<Integer, EquipmentPir> equipmentCondfigs = EquipmentPirFactory.getInstance().getEquipmentConfigBySynthes(configModel.getSynthesis());
		Iterator<Entry<Integer, EquipmentPir>> entriesTarget = equipmentCondfigs.entrySet().iterator();
		
		EquipmentPir targetEquipmentConfigModel = null;
		while (entriesTarget.hasNext()) {
			Map.Entry<java.lang.Integer, EquipmentPir> entryTarget = (Map.Entry<java.lang.Integer, EquipmentPir>) entriesTarget.next();
			if (entryTarget.getValue().getQuality() == realQuality) {
				targetEquipmentConfigModel = entryTarget.getValue();
				break;
			}
		}
		
		// 
		if(targetEquipmentConfigModel == null) {
			Language.ERRORCODE.send(this.getPlayer(), E300_EQUIP.CODE4.get());
			return;
		}
		
		if (!immediately) {
			equipmentWaitingBuilding.setTargetEquipmentID(fragmentID);
			this.getPlayer().roleInfo().getEquipmentDataManager().getEquipmentWaitingBuildings().add(equipmentWaitingBuilding);
			TimerTaskHolder.getTimerTask(TimerTaskCommand.PRODUCE_EQUIPMENT).register(this.getPlayer(), targetEquipmentConfigModel.getTime(), new ProduceEquipmentTaskData(targetEquipmentConfigModel.getId(), buildId));
		} else {
			addEquipment(targetEquipmentConfigModel.getId(), GameLogSource.FORGING_EQUIPMENT);
			EventBus.getSelf().fireEvent(new ImplantedCompoundEventObject(this.getPlayer()));
		}
		
		if (!immediately) {
			// 锻造的信息
			ResEquipmentBuildingInfoMessage resEquipmentBuildingInfoMessage = new ResEquipmentBuildingInfoMessage();
			resEquipmentBuildingInfoMessage.EquipmentIDs = equipmentWaitingBuilding.getSubComposeEquipments();
			List<EquipmentFragment> equipmentFragments = new ArrayList<EquipmentFragment>();
			for (Entry<Integer, EquipmentPositionInfo> entry : equipmentWaitingBuilding.getMaterials().entrySet()) {
				EquipmentFragment equipmentFragment = new EquipmentFragment();
				equipmentFragment.itemID = entry.getValue().getEquipmentFragmentID();
				equipmentFragment.account = entry.getValue().getAccount();
				equipmentFragment.position = entry.getValue().getPosition();
				
				equipmentFragments.add(equipmentFragment);
			}
			resEquipmentBuildingInfoMessage.EquipmentFragments = equipmentFragments;
			resEquipmentBuildingInfoMessage.EquipmentID = fragmentID;
			
			this.getPlayer().send(resEquipmentBuildingInfoMessage);
			
		} else {
			// 发送世界公告
			if(targetEquipmentConfigModel.getQuality() >= EquipmentQuality.PURPLE) {
				noticeManager.sendWorldNotice(NoticeConstant.EQUIPMENT_COMPOSE, e.getName(), targetEquipmentConfigModel.getId());
			}
		}
		
		// 锻造是否成功的信息
		ResBuildEquipmentResultMessage resBuildEquipmentResultMessage = new ResBuildEquipmentResultMessage();
		resBuildEquipmentResultMessage.isSuccess = true;
		resBuildEquipmentResultMessage.isImmediately = immediately;
		this.getPlayer().send(resBuildEquipmentResultMessage);
		
		Language.SUCCESSTIP.send(this.getPlayer(), SuccessTipEnum.E300_EQUIP.CODE1);
	}
	
	// 装备锻造结束
	public void buildEquipmentEnd(Player player,int uid, int equipmnetID) {
		
		player.roleInfo().getEquipmentDataManager().getOutputEquipment().put(uid, equipmnetID);
		
		EquipmentOutput equipmentOutput = new EquipmentOutput();
		equipmentOutput.equipmentId = equipmnetID;
		equipmentOutput.uid = uid;
		equipmentOutput.outputType = IndustryBuildControl.OUTPUT_EQUIP;
		
		ResOutputEquipmentMessage resOutputEquipmentMessage = new ResOutputEquipmentMessage();
		resOutputEquipmentMessage.equipmentOutput.add(equipmentOutput);
		player.send(resOutputEquipmentMessage);
		
//		addEquipment(equipmnetID);
//		player.roleInfo().equipmentDataManager.equipmentWaitingBuildings.clear();
//		InjectorUtil.getInjector().dbCacheService.update(player);
//		
//		ResEquipmentBuidingResultMessage resEquipmentBuidingResultMessage = new ResEquipmentBuidingResultMessage();
//		resEquipmentBuidingResultMessage.isBuilding = false;
//		player.send(resEquipmentBuidingResultMessage);
	}
	
	/**
	 * 收取植入体装备
	 * @param player
	 * @param uid 建筑id
	 */
	public void output(Player player, int uid) {
		Integer equipmentId = player.roleInfo().getEquipmentDataManager().getOutputEquipment().get(uid);
		if(equipmentId != null) {
			addEquipment(equipmentId, GameLogSource.FRAGMENT_OUTPUT);
			player.roleInfo().getEquipmentDataManager().getEquipmentWaitingBuildings().clear();
			player.roleInfo().getEquipmentDataManager().getOutputEquipment().remove(uid);
			InjectorUtil.getInjector().dbCacheService.update(player);
			
			// 发送世界公告
			EquipmentPir equipmentPir = EquipmentPirFactory.get(equipmentId);
			if(equipmentPir.getQuality() >= EquipmentQuality.PURPLE) {
				noticeManager.sendWorldNotice(NoticeConstant.EQUIPMENT_COMPOSE, e.getName(), equipmentPir.getId());
			}
			
			ResEquipmentBuidingResultMessage resEquipmentBuidingResultMessage = new ResEquipmentBuidingResultMessage();
			resEquipmentBuidingResultMessage.isBuilding = false;
			player.send(resEquipmentBuidingResultMessage);
			EventBus.getSelf().fireEvent(new BuildingRewardEventObject(player));
			EventBus.getSelf().fireEvent(new ImplantedProduceEventObject(player));
			Language.SUCCESSTIP.send(this.getPlayer(), SuccessTipEnum.E300_EQUIP.CODE2);
		}
	}
	
	public void composeFragment(Long id, int fragmentID, int account) {

		int itemId = id.intValue();
		ItemsPir itempair = ItemsPirFactory.get(itemId);
		
		// 判断背包中是否有该材料
		if (itempair == null) {
			Language.ERRORCODE.send(this.getPlayer(), ErrorCodeEnum.E001_LOGIN.CODE12.get());
			return;
		}
		
		// 道具不存在
		List<ItemsPir> itemList = ItemsPirFactory.getInstance().getItemsBySubTypeAndQuality(itempair.getType(), Integer.valueOf(itempair.getV1()), itempair.getQuality() + 1);
		if(itemList == null || itemList.isEmpty()) {
			Language.ERRORCODE.send(this.getPlayer(), ErrorCodeEnum.E001_LOGIN.CODE6.get(), ItemsPir.class.getSimpleName(), String.valueOf(fragmentID));
			return;
		}
		
		ItemsPir nextConfig = itemList.get(0);
		if(nextConfig == null) {
			Language.ERRORCODE.send(this.getPlayer(), ErrorCodeEnum.E001_LOGIN.CODE6.get(), ItemsPir.class.getSimpleName(), String.valueOf(fragmentID));
			return;
		}
		
		int totalNum = this.getPlayer().roleInfo().getPlayerBag().getItemNum(itempair.getId());
		if (totalNum < account) {
			Language.ERRORCODE.send(this.getPlayer(), ErrorCodeEnum.E300_EQUIP.CODE4.get());
			return;
		}
		
		// 减少背包中装备材料的数量
		ItemKit.removeItemByTid(this.getPlayer(), itempair.getId(), 4, GameLogSource.COMPOSE_FRAGMENT);
		ItemKit.addItemAndTopic(this.getPlayer(), nextConfig.getId(), 1, SystemEnum.EQUIP, GameLogSource.COMPOSE_FRAGMENT);
		InjectorUtil.getInjector().dbCacheService.update(this.getPlayer());
		
		// 通知客户端合成成功
		ResFragmentComposeResultMessage resFragmentComposeResultMessage = new ResFragmentComposeResultMessage();
		resFragmentComposeResultMessage.isSuccess = true;
		this.getPlayer().send(resFragmentComposeResultMessage);
		EventBus.getSelf().fireEvent(new MaterialsProductionEventObject(this.getPlayer()));
		Language.SUCCESSTIP.send(this.getPlayer(), SuccessTipEnum.E300_EQUIP.CODE3);
	}
	
	/**
	 * 根据模板创建装备
	 */
	private Equipment createEquipment(EquipmentPir equipmentConfig, EquipmentSequence sequance) {
		Equipment equipment = new Equipment();
		equipment.setEquipmentSequenceId(sequance.genEquipmentId());
		equipment.setEquipmentId(equipmentConfig.getId());
		equipment.setEquiped(false);
		
		return equipment;
	}
	
	
	// 待生产的装备材料列表
	public void addFragmentWaitingProduce(int fragmentID)
	{
		ItemsPir itemconfigConfig = ItemsPirFactory.get(fragmentID);
		if (itemconfigConfig == null) {
			return;
		}
		
		List<Integer> list = this.getPlayer().roleInfo().getEquipmentDataManager().getFragmentWaitingProduceID();
		int positionAccount = this.getPlayer().roleInfo().getEquipmentDataManager().getUnlockPosition();
		if (list.size() == positionAccount - 1) {
			list.remove(0);
		}
		
		this.getPlayer().roleInfo().getEquipmentDataManager().getFragmentWaitingProduceID().add(fragmentID);
		
		InjectorUtil.getInjector().dbCacheService.update(this.getPlayer());
	}
	
	/**
	 * 获取玩家单个装备
	 * 
	 * @param id
	 * @return
	 */
	public Equipment getPlayerEquipment(long equipmentSequenceId) {
		Equipment equipment = this.getPlayer().roleInfo().getPlayerBag().getEquipmentMap().get(equipmentSequenceId);
		return equipment;
	}
	
	/**
	 * 获取玩家单个装备材料
	 * @param id
	 * @return
	 */
	public Item getPlayerFragment(long FragmentUID) {
		Item fragmentItem = this.getPlayer().roleInfo().getPlayerBag().getPlayerItem(FragmentUID);
		return fragmentItem;
	}
	
	/**
	 * 通过合成线，查找合成线相同的装备
	 * @param itemType
	 * @return
	 */
	public List<EquipmentPir> getEquipmentConfigsBySynthesis(int synthesis) {
		List<EquipmentPir> equipmentList = new ArrayList<EquipmentPir>();
		
		Iterator<Map.Entry<Integer, EquipmentPir>> entriesIterator = EquipmentPirFactory.getInstance().getFactory().entrySet().iterator();
		
		while (entriesIterator.hasNext()) {
			EquipmentPir configModel= entriesIterator.next().getValue();
			if(configModel.getSynthesis() == synthesis)
				equipmentList.add(configModel);
		}
		
		return equipmentList;
	}
	
	public MsgPlayerEquipmentBag getMsgPlayerEquipmentBag(Player player) {
		PlayerBag bag = player.roleInfo().getPlayerBag();
		MsgPlayerEquipmentBag msgBag = new MsgPlayerEquipmentBag();
		msgBag.equipments = Lists.newArrayList();
		for (Equipment equipment: bag.getEquipmentMap().values()) {
			MsgEquipment msgEquipment = new MsgEquipment();
			msgEquipment.id = equipment.getEquipmentSequenceId();
			msgEquipment.equipmentId = equipment.getEquipmentId();
			msgEquipment.isEquiped = equipment.isEquiped();
			msgBag.equipments.add(msgEquipment);
		}
		return msgBag;
	}
	
	/**
	 * 获取植入体列表
	 * @param player
	 * @param itemId
	 * @param v1
	 * @return
	 */
	private Map.Entry<Integer, Integer> getItemPirList(Map<Integer, Integer> equipMap, EquipmentPir configModel, int itemId) {
		for(Entry<Integer, Integer> entry : equipMap.entrySet()) {
			List<Integer> itemPirs = ItemsPirFactory.getInstance().getComposeFragment(ItemFactory.COMPOSE_ITEM.getControl().getType(), entry.getKey());
			if(itemPirs != null && itemPirs.contains(itemId)) {
				return entry;
			}
		}
		return null;
	}

	@Override
	public Map<AttributeNodeEnum,Double> attributeAdd(Player player,AttributesEnum attributeEnum,AttributeNodeEnum...attributeNodeEnums) {
		double value = 0;
		Iterator<Long> iterator = player.getCommanderManager().getCommanderData().getEquits().values().iterator();
		Map<AttributeNodeEnum,Double> valueOfNodeMap = new HashMap<>();
		while (iterator.hasNext()) {
			Equipment equipment = player.getEquipmentManager().getPlayerEquipment(iterator.next());
			if(equipment == null) {
				continue;
			}
			
			EquipmentPir equipmentPir = EquipmentPirFactory.get(equipment.getEquipmentId());
			if (equipmentPir == null) {
				continue;
			}
			
			AttributeConfMap confMap = equipmentPir.getAttr_1();
			for(AttributeNodeEnum node : AttributeNodeEnum.values()){
				if(attributeNodeEnums.length > 0 && Arrays.binarySearch(attributeNodeEnums,node) < 0){
					continue;
				}
				LibraryConf libraryConf = confMap.getLibraryConfs().get(1, node.getCode());
				if(libraryConf != null && libraryConf.containsKey(attributeEnum.getId())){
					double nodeValue = libraryConf.get(attributeEnum.getId());
					value += nodeValue;
					if(nodeValue > 0){
						if(!valueOfNodeMap.containsKey(node.getCode())){
							valueOfNodeMap.put(node, nodeValue);
						}else{
							valueOfNodeMap.put(node, valueOfNodeMap.get(node.getCode()) + nodeValue);
						}
					}
				}
			}
		}
		if(valueOfNodeMap.size() > 0){
			log.info(AttributeUtil.logBuilder(valueOfNodeMap, attributeEnum.getId(), value,EquipmentPir.class.getSimpleName()));
		}
		return valueOfNodeMap;
	}
	
	@Override
	public Map<AttributeFromEnum, Double> selectAttributeAdd(Player player,
			AttributesEnum attributeEnum, AttributeNodeEnum node) {
		Map<AttributeNodeEnum,Double> nodeAttrMap = attributeAdd(player,attributeEnum, node);
		Map<AttributeFromEnum, Double> resultMap = new HashMap<>();
		if(nodeAttrMap.containsKey(node)){
			resultMap.put(AttributeFromEnum.EQUITMENT, nodeAttrMap.get(node));
		}
		return resultMap;
	}
	
}
