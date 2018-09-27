package com.xgame.logic.server.game.commander;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.config.attribute.AttributeConfMap;
import com.xgame.config.attribute.LibraryConf;
import com.xgame.config.commanderInfo.CommanderInfoPir;
import com.xgame.config.commanderInfo.CommanderInfoPirFactory;
import com.xgame.config.equipment.EquipmentPir;
import com.xgame.config.equipment.EquipmentPirFactory;
import com.xgame.config.exp.ExpPir;
import com.xgame.config.exp.ExpPirFactory;
import com.xgame.config.fastPaid.FastPaidPir;
import com.xgame.config.fastPaid.FastPaidPirFactory;
import com.xgame.config.global.GlobalPir;
import com.xgame.config.global.GlobalPirFactory;
import com.xgame.config.marchingTroops.MarchingTroopsPir;
import com.xgame.config.marchingTroops.MarchingTroopsPirFactory;
import com.xgame.config.talent.TalentPir;
import com.xgame.config.talent.TalentPirFactory;
import com.xgame.logic.server.core.component.AbstractComponent;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.language.view.success.SuccessTipEnum;
import com.xgame.logic.server.core.utils.CurrencyUtil;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.bag.ItemKit;
import com.xgame.logic.server.game.bag.constant.ItemIdConstant;
import com.xgame.logic.server.game.bag.constant.ItemOrigin;
import com.xgame.logic.server.game.commander.bean.TalentPro;
import com.xgame.logic.server.game.commander.constant.CommanderConstant;
import com.xgame.logic.server.game.commander.entity.CommanderData;
import com.xgame.logic.server.game.commander.entity.TalentData;
import com.xgame.logic.server.game.commander.entity.TalentsPage;
import com.xgame.logic.server.game.commander.entity.eventmodel.AddTalentEventObject;
import com.xgame.logic.server.game.commander.entity.eventmodel.CommanderChangeNameEventObject;
import com.xgame.logic.server.game.commander.entity.eventmodel.CommanderLevelUpEventObject;
import com.xgame.logic.server.game.commander.entity.eventmodel.EnergyRefreshEventObject;
import com.xgame.logic.server.game.commander.entity.eventmodel.UnuseEquipmentEventObject;
import com.xgame.logic.server.game.commander.entity.eventmodel.UseEquipmentEventObject;
import com.xgame.logic.server.game.commander.message.ResAddCPointCommanderMessage;
import com.xgame.logic.server.game.commander.message.ResChangeNameMessage;
import com.xgame.logic.server.game.commander.message.ResChangeStyleMessage;
import com.xgame.logic.server.game.commander.message.ResCommanderMessage;
import com.xgame.logic.server.game.commander.message.ResEnergyMessage;
import com.xgame.logic.server.game.commander.message.ResInsertEquitMessage;
import com.xgame.logic.server.game.commander.message.ResLevelUpCommanderMessage;
import com.xgame.logic.server.game.commander.message.ResOtherCommanderMessage;
import com.xgame.logic.server.game.commander.message.ResResetTalentMessage;
import com.xgame.logic.server.game.commander.message.ResSwitchTanlentMessage;
import com.xgame.logic.server.game.commander.message.ResTalentAddPointMessage;
import com.xgame.logic.server.game.commander.message.ResUninstellEquitMessage;
import com.xgame.logic.server.game.constant.CurrencyEnum;
import com.xgame.logic.server.game.equipment.entity.Equipment;
import com.xgame.logic.server.game.notice.NoticeManager;
import com.xgame.logic.server.game.notice.constant.NoticeConstant;
import com.xgame.logic.server.game.player.PlayerManager;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.player.entity.RoleInfo;
import com.xgame.logic.server.game.playerattribute.AttributeUtil;
import com.xgame.logic.server.game.playerattribute.IAttributeAddModule;
import com.xgame.logic.server.game.playerattribute.constant.AttributeFromEnum;
import com.xgame.logic.server.game.playerattribute.constant.AttributeNodeEnum;
import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;
import com.xgame.logic.server.game.playerattribute.entity.eventmodel.AttributeRefreshEventObject;
import com.xgame.logic.server.game.playerattribute.fightpower.FightPowerKit;

/**
 * 指挥官 每个玩家一个 2016-10-09 11:12:46
 * 
 * @author ye.yuan
 *
 */
@Component
@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CommanderManager extends AbstractComponent<Player> implements IAttributeAddModule{

	public static final int UTF_LEN = 14;

	// 统帅点公告
	public static final int COMMANDER_CPOINT_NOTICE_LEVEL = 10;

	private Player player;

	@Autowired
	private PlayerManager playerManager;
	@Autowired
	private NoticeManager noticeManager;

	@Autowired
	private EventBus gameLog;

	/**
	 * 指挥官数据
	 */
	private CommanderData commanderData;

	public CommanderManager() {

	}

	@Override
	public void firstLoad(Object... param) {
		commanderData.setLevel(1);
		commanderData.setcPoint(1);
		commanderData.setStyle(1);
		commanderData.setUseTalentPage(1);
		commanderData.setEnergy(100);
		commanderData.setTalentPoints(3);
		commanderData.getTalents().put(1, new TalentsPage(1));
		commanderData.getTalents().put(2, new TalentsPage(2));
	}

	/**
	 * 新玩家第一次初始化操作
	 */
	public void loadComponent(Object... param) {
		commanderData = ((RoleInfo) param[0]).getCommanderData();
	}

	@Override
	public void loginLoad(Object... param) {
		send();
	}

	/**
	 * 查看他人指挥官信息
	 * 
	 * @param playerId
	 * @return
	 */
	public ResOtherCommanderMessage lookOtherCommander(long playerId) {
		Player player2 = playerManager.getPlayer(playerId);
		if (player2 == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E130_COMMANDER.CODE12);
			ResOtherCommanderMessage message = new ResOtherCommanderMessage();
			return message;
		}
		// 通过对方玩家异步执行管理器在对方线程里获得对方信息
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E130_COMMANDER.CODE5);
		return player2.getCommanderManager().getCommanderInfo();
	}

	/**
	 * 本指挥官 提供给他人的查看的信息
	 * 
	 * @return
	 */
	public ResOtherCommanderMessage getCommanderInfo() {
		ResOtherCommanderMessage commanderMessage = new ResOtherCommanderMessage();
		commanderMessage.uid = player.getRoleId();
		commanderMessage.name = player.roleInfo().getBasics().getRoleName();
		commanderMessage.level = commanderData.getLevel();
		commanderMessage.style = commanderData.getStyle();
		Iterator<Long> iterator2 = commanderData.getEquits().values().iterator();
		while (iterator2.hasNext()) {
			Equipment playerEquipment = player.getEquipmentManager().getPlayerEquipment(iterator2.next());
			commanderMessage.equits.add((long) playerEquipment.getEquipmentId());
		}
		FightPowerKit[] values = FightPowerKit.values();
		for (int i = 0; i < values.length; i++) {
			CommanderInfoPir commanderInfoPir = CommanderInfoPirFactory.get(values[i].getRow());
			if (commanderInfoPir.getIs_public() == 1) {
				commanderMessage.statisticLongs.add(values[i].toLongPro(player));
			}
		}
		return commanderMessage;
	}

	/**
	 * 改名
	 * 
	 * @param utf
	 */
	public ResChangeNameMessage changeName(String utf) {
		ResChangeNameMessage changeNameMessage = new ResChangeNameMessage();
		// 字符长度不能超过12
		if (utf.length() >= UTF_LEN) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E130_COMMANDER.CODE7);
			return changeNameMessage;
		}
		if (Language.haveSpecial(utf)) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E130_COMMANDER.CODE1);
			return changeNameMessage;
		}
		// 首字符不能为空格
		if (utf.codePointAt(0) == 32) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E130_COMMANDER.CODE2);
			return changeNameMessage;
		}
		// 修改的名字已存在
		if (InjectorUtil.getInjector().playerManager.checkPlayerNameExist(utf)) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E130_COMMANDER.CODE22);
			return changeNameMessage;
		}
		FastPaidPir paidPir = FastPaidPirFactory.get(ItemIdConstant.CHANGE_NAME);
		if (ItemKit.checkRemoveItemByTid(player, ItemIdConstant.CHANGE_NAME, 1)) {
			ItemKit.removeItemByTid(player, ItemIdConstant.CHANGE_NAME, 1, GameLogSource.CHANAGE_NAME);
		} else if (null == paidPir){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE6,FastPaidPir.class.getSimpleName(),ItemIdConstant.CHANGE_NAME);
			return changeNameMessage;
		} else if (CurrencyUtil.verify(player, paidPir.getPrice() , CurrencyEnum.DIAMOND)) {
			CurrencyUtil.decrement(player, paidPir.getPrice(), CurrencyEnum.DIAMOND, GameLogSource.CHANAGE_NAME);
			CurrencyUtil.send(player);
		} else {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E130_COMMANDER.CODE3);
			return changeNameMessage;
		}
		
		String old = player.roleInfo().getBasics().getRoleName();
		player.roleInfo().getBasics().setRoleName(utf);
		InjectorUtil.getInjector().dbCacheService.update(player);
		changeNameMessage.utf = utf;
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E130_COMMANDER.CODE2);

		CommanderChangeNameEventObject event = new CommanderChangeNameEventObject(player, EventTypeConst.EVENT_COMMANDER_CHANGE_NAME, old);
		this.gameLog.fireEvent(event);

		return changeNameMessage;
	}

	/**
	 * 改变形象
	 * 
	 * @param styleId
	 */
	public ResChangeStyleMessage changeStyle(int styleId) {
		ResChangeStyleMessage changeStyleMessage = new ResChangeStyleMessage();
		GlobalPir model = GlobalPirFactory.get(500720);
		// 已是该形象
		if (changeStyleMessage.styleId == styleId) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E130_COMMANDER.CODE4);
			return changeStyleMessage;
		}
		// 没找到要修改的形象id
		HashSet<Integer> styles = model.getValue();
		if (!styles.contains(styleId)) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E130_COMMANDER.CODE11);
			return changeStyleMessage;
		}
		FastPaidPir fast_paidPir = FastPaidPirFactory.get(ItemIdConstant.CHANGE_STYLE);
		if (ItemKit.checkRemoveItemByTid(player, ItemIdConstant.CHANGE_STYLE, 1)) {
			ItemKit.removeItemByTid(player, ItemIdConstant.CHANGE_STYLE, 1, GameLogSource.CHANGE_STYLE);
		} else if (null == fast_paidPir){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE6,FastPaidPir.class.getSimpleName(),ItemIdConstant.CHANGE_STYLE);
			return changeStyleMessage;
		} else if (CurrencyUtil.verify(player, fast_paidPir.getPrice() , CurrencyEnum.DIAMOND)) {
			CurrencyUtil.decrement(player, fast_paidPir.getPrice(), CurrencyEnum.DIAMOND, GameLogSource.CHANGE_STYLE);
			CurrencyUtil.send(player);
		} else {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E130_COMMANDER.CODE3);
			return changeStyleMessage;
		}
		commanderData.setStyle(styleId);
		InjectorUtil.getInjector().dbCacheService.update(player);
		changeStyleMessage.styleId = styleId;
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E130_COMMANDER.CODE3);
		return changeStyleMessage;
	}

	/**
	 * 插入装备
	 * 
	 * @param equitUid
	 * @return
	 */
	public ResInsertEquitMessage insertEquit(long equitUid) {
		ResInsertEquitMessage insertEquitMessage = new ResInsertEquitMessage();
		Equipment playerEquipment = player.getEquipmentManager().getPlayerEquipment(equitUid);
		// 装备不存在
		if (playerEquipment == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E130_COMMANDER.CODE5);
			return insertEquitMessage;
		}
		// 装备配置不存在
		EquipmentPir equipmentPir = EquipmentPirFactory.get(playerEquipment.getEquipmentId());
		if (equipmentPir == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E130_COMMANDER.CODE6);
			return insertEquitMessage;
		}
		// 植入体等级不能>指挥官等级
		if (equipmentPir.getLevel() > commanderData.getLevel()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E130_COMMANDER.CODE23, equipmentPir.getLevel(),
					commanderData.getLevel());
			return insertEquitMessage;
		}
		// 卸下老装备
//		Long oldEquitId = commanderData.getEquits().get(equipmentPir.getType());
//		if (oldEquitId != null) {
//			Equipment oldPlayerEquipment = player.getEquipmentManager().getPlayerEquipment(oldEquitId);
//			if (oldPlayerEquipment != null) {
//				player.getAttributeAppenderManager().deleteAppenderObject(oldPlayerEquipment.getEquipmentId(),
//						AttributeAppenderEnum.EQUITMENT.ordinal());
//			}
//		}
		// 穿上新装备
		commanderData.getEquits().put(equipmentPir.getType(), equitUid);

		// 计算战力
		FightPowerKit.EQUIT_POWER.math(player,GameLogSource.INSERT_EQUIT);
//		AttributeConfMap attributeConfMap = equipmentPir.getAttr_1();
		EventBus.getSelf().fireEvent(new AttributeRefreshEventObject(player));
		// 入库
		InjectorUtil.getInjector().dbCacheService.update(player);
		insertEquitMessage.equitId = equitUid;
		FightPowerKit.EQUIT_POWER.send(player);
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E130_COMMANDER.CODE4);
		EventBus.getSelf().fireEvent(new UseEquipmentEventObject(player, EventTypeConst.USE_EQUIPMENT, playerEquipment.getEquipmentId(), playerEquipment.getEquipmentSequenceId()));
		return insertEquitMessage;
	}

	/**
	 * 卸载装备
	 * 
	 * @param equitUid
	 * @return
	 */
	public ResUninstellEquitMessage uninsertEquit(long equitUid) {
		ResUninstellEquitMessage message = new ResUninstellEquitMessage();
		Equipment playerEquipment = player.getEquipmentManager().getPlayerEquipment(equitUid);
		// 装备不存在
		if (playerEquipment == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E130_COMMANDER.CODE5);
			return message;
		}
		// 装备配置不存在
		EquipmentPir configModel = EquipmentPirFactory.get(playerEquipment.getEquipmentId());
		if (configModel == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E130_COMMANDER.CODE6);
			return message;
		}
		// 取下装备
		commanderData.getEquits().remove(configModel.getType());
//		player.getAttributeAppenderManager().deleteAppenderObject(playerEquipment.getEquipmentId(),
//				AttributeAppenderEnum.EQUITMENT.ordinal());
		EventBus.getSelf().fireEvent(new AttributeRefreshEventObject(player));
		// 计算战力
		FightPowerKit.EQUIT_POWER.math(player,GameLogSource.UNINSERT_EQUIT);
		InjectorUtil.getInjector().dbCacheService.update(player);

		CurrencyUtil.send(player);
		message.equitId = equitUid;
		FightPowerKit.EQUIT_POWER.send(player);
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E130_COMMANDER.CODE9);
		EventBus.getSelf().fireEvent(new UnuseEquipmentEventObject(player, EventTypeConst.USE_EQUIPMENT, playerEquipment.getEquipmentId(), playerEquipment.getEquipmentSequenceId()));
		return message;
	}

	/**
	 * 升级
	 * 
	 * @param exp
	 */
	public void commanderUpLevel(long exp) {
		
		int size = ExpPirFactory.getInstance().getFactory().size();
		// 等级已是最大 且不能<=0 且不能没有配置
		if (exp <= 0 || size <= 0 || commanderData.getLevel() >= size) {
			return;
		}
		
		//进行指挥官经验增幅计算
		if(commanderData.getExpAddPercent() > 0 && commanderData.getExpAddTime() > 0 && commanderData.getExpAddTime() > System.currentTimeMillis()){
			exp = (long) (exp + exp*commanderData.getExpAddPercent());
		}
		
		size -= commanderData.getLevel();
		int originLevel = commanderData.getLevel();
		ExpPir configModel = null;
		commanderData.setExp(commanderData.getExp() + exp);
		do {
			size--;
			configModel = ExpPirFactory.get(commanderData.getLevel());
			if (configModel == null) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E130_COMMANDER.CODE20);
				return;
			}
			if (commanderData.getExp() >= configModel.getExp()) {
				commanderData.setLevel(commanderData.getLevel() + 1);
			} else {
				break;
			}
			commanderData.setExp(commanderData.getExp() - configModel.getExp());
			commanderData.setEnergy(configModel.getPower_max());
			commanderData.setTalentPoints(commanderData.getTalentPoints() + configModel.getTalent_point());
		} while (commanderData.getExp() > 0 && size > 0);
		FightPowerKit.COMMANDER_LEVEL_POWER.math(player,GameLogSource.COMMANDER_LEVEL_UP);
		CurrencyUtil.send(player);
		ResLevelUpCommanderMessage message = new ResLevelUpCommanderMessage();
		message.energy = commanderData.getEnergy();
		message.exp = commanderData.getExp();
		message.level = commanderData.getLevel();
		message.talentPoints = commanderData.getTalentPoints();

		InjectorUtil.getInjector().dbCacheService.update(player);
		player.send(message);
		
		if(commanderData.getLevel() > originLevel){
			EventBus.getSelf().fireEvent(new CommanderLevelUpEventObject(player , EventTypeConst.EVENT_COMMAND_LEVELUP , originLevel, commanderData.getLevel()));
		}
	}

	/**
	 * 添加体力
	 * 
	 * @param energy
	 */
	public void increaseEnergy(int energy) {
		if (energy <= 0)
			return;
		ExpPir configModel = ExpPirFactory.get(commanderData.getLevel());
		if (commanderData == null || configModel == null)
			return;
		// 结果>体力卡上限 强制等于最大上限
		if (commanderData.getEnergy() + energy >= configModel.getPower_max_card()) {
			commanderData.setEnergy(configModel.getPower_max_card());
		} else {
			// 自然相加
			commanderData.setEnergy(commanderData.getEnergy() + energy);
		}

		InjectorUtil.getInjector().dbCacheService.update(player);
		
		EventBus.getSelf().fireEvent(new EnergyRefreshEventObject(player , EventTypeConst.EVENT_COMMANDER_REFRESH_ENERGY , commanderData.getEnergy() - energy, commanderData.getEnergy()));

		// 刷新体力
		sendEnergy();
	}

	/**
	 * 刷新体力
	 */
	public void sendEnergy() {
		ResEnergyMessage message = new ResEnergyMessage();
		message.energy = commanderData.getEnergy();
		player.send(message);
	}

	/**
	 * 回复体力 定时器在使用 注意线程 6 分钟 一次 "0 0/6 * * * ?"
	 */
	public boolean restoreEnergy() {
		ExpPir configModel = ExpPirFactory.get(commanderData.getLevel());
		if (configModel != null) {
			if (commanderData.getEnergy() >= configModel.getPower_max())
				return false;
		}
		increaseEnergy(1);
		return true;
	}

	/**
	 * 添加统帅点
	 * 
	 * @return
	 */
	public strictfp ResAddCPointCommanderMessage addCPoint() {
		ResAddCPointCommanderMessage message = new ResAddCPointCommanderMessage();
		// 不能>=当前指挥官等级的2倍
		if (commanderData.getcPoint() >= commanderData.getLevel() * 2) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E130_COMMANDER.CODE7);
			message.cpoint = commanderData.getcPoint();
			return message;
		}
		// 不能>=最大等级的2倍
		if (commanderData.getcPoint() >= ExpPirFactory.getInstance().getFactory().size() * 2) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E130_COMMANDER.CODE8);
			message.cpoint = commanderData.getcPoint();
			return message;
		}
		// 额外概率
		int oldLevel = commanderData.getcPoint();
		float odds = 0f;
		FastPaidPir paidPir = FastPaidPirFactory.get(ItemIdConstant.CAPTIAN_CARD);
		if (ItemKit.checkRemoveItemByTid(player, ItemIdConstant.CAPTIAN_CARD, 1)) {
			odds = useCPointItem(ItemIdConstant.CAPTIAN_CARD);
		} else if (null == paidPir){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE6,FastPaidPir.class.getSimpleName(),ItemIdConstant.CAPTIAN_CARD);
			message.cpoint = commanderData.getcPoint();
			return message;
		} else if (CurrencyUtil.verify(player, paidPir.getPrice() , CurrencyEnum.DIAMOND)) {
			CurrencyUtil.decrement(player, paidPir.getPrice() , CurrencyEnum.DIAMOND, GameLogSource.ADD_CAPTAIN_POINT);
			odds = GlobalPirFactory.getInstance().marchingTroops.get(ItemOrigin.shop.name());
		} else {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E130_COMMANDER.CODE9);
			message.cpoint = commanderData.getcPoint();
			return message;
		}
		MarchingTroopsPir configModel2 = MarchingTroopsPirFactory.get(commanderData.getcPoint());
		odds += configModel2.getChance_1();
		// <概率证明随机成功
		double random = Math.random();
		if (random < odds) {
			commanderData.setcPoint(commanderData.getcPoint() + 1);
			FightPowerKit.COMMANDER_CPOINT_POWER.math(player,GameLogSource.ADD_CAPTAIN_POINT);
			InjectorUtil.getInjector().dbCacheService.update(player);
		}

		// 指挥官升级公告
		if (oldLevel != commanderData.getcPoint() && commanderData.getcPoint() >= COMMANDER_CPOINT_NOTICE_LEVEL) {
			noticeManager.sendWorldNotice(NoticeConstant.COMMAND_LEVELUP, player.getName(), commanderData.getcPoint());
		}

		Language.SUCCESSTIP.send(player, SuccessTipEnum.E130_COMMANDER.CODE1);
		message.cpoint = commanderData.getcPoint();
		return message;
	}

	/**
	 * 使用统帅物品
	 * 
	 * @param itemId
	 * @return
	 */
	private float useCPointItem(int tId) {
		float odds = 0;
		Map<Integer, Integer> originItem = ItemKit.useOriginItem(player, tId, 1, GameLogSource.USE_CAPTAIN);
		if (originItem != null) {
			Iterator<Integer> iterator = originItem.values().iterator();
			if (iterator.hasNext()) {
				Integer next = iterator.next();
				if (next != null) {
					ItemOrigin itemOrigin = ItemOrigin.values()[next];
					if (itemOrigin != null && GlobalPirFactory.getInstance().marchingTroops.containsKey(itemOrigin.name())) {
						// 存在,就取配置枚举
						odds = GlobalPirFactory.getInstance().marchingTroops.get(itemOrigin.name());
					}
				}
			}
		}
		return odds;
	}

	/**
	 * 切换天赋
	 * 
	 * @param tanlentPage
	 * @return
	 */
	public ResSwitchTanlentMessage switchTanlent(int tanlentPage) {
		ResSwitchTanlentMessage message = new ResSwitchTanlentMessage();
		// 已是当前天赋页
		if (commanderData.getUseTalentPage() == tanlentPage) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E130_COMMANDER.CODE14);
			return message;
		}
		// 获得天赋页
		TalentsPage talentsNew = getSwitchAndResetTalents(tanlentPage);
		if (talentsNew == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E130_COMMANDER.CODE15);
			return message;
		}
		commanderData.setUseTalentPage(tanlentPage);

//		// 把老的页增益全部减去
//		clear: {
//			player.getAttributeAppenderManager().deleteAllAppender(AttributeAppenderEnum.COMMANDER.ordinal(), true);
//			break clear;
//		}
//
//		// 把新的页加进去
//		news: {
//			commanderData.setUseTalentPage(tanlentPage);
//			Iterator<TalentData> iterator = talentsNew.getTalents().values().iterator();
//			while (iterator.hasNext()) {
//				TalentData talentData = iterator.next();
//				TalentPir configModel = TalentPirFactory.get(talentData.getSid());
//				player.getAttributeAppenderManager().rebuild(configModel.getAttr(), talentData.getLevel(),
//						AttributeAppenderEnum.COMMANDER.ordinal(), true);
//				EventBus.getSelf().fireEvent(new AttributeRefreshEventObject(player));
//			}
//			break news;
//		}
		InjectorUtil.getInjector().dbCacheService.update(player);
		EventBus.getSelf().fireEvent(new AttributeRefreshEventObject(player));
		CurrencyUtil.send(player);
		message.page = commanderData.getUseTalentPage();
		// player.getAttributeAppenderManager().send();
		FightPowerKit.COMMANDER_TALENT_POWER.math(player,GameLogSource.SWITCH_TALENT_PAGE);
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E130_COMMANDER.CODE7);
		return message;
	}

	/**
	 * 重置天赋
	 * 
	 * @param tanlentPage
	 * @return
	 */
	public ResResetTalentMessage resetTanlent(int tanlentPage) {
		ResResetTalentMessage message = new ResResetTalentMessage();
		TalentsPage talents = getSwitchAndResetTalents(tanlentPage);
		if (talents == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E130_COMMANDER.CODE13);
			return message;
		}
		// 清理天赋页
		talents.setUseTalentPoints(0);
		talents.getTalents().clear();
		// 把老的页增益全部减去
//		clear: {
//			player.getAttributeAppenderManager().deleteAllAppender(AttributeAppenderEnum.COMMANDER.ordinal(), true);
//			break clear;
//		}
		InjectorUtil.getInjector().dbCacheService.update(player);
		CurrencyUtil.send(player);
		// player.getAttributeAppenderManager().send();
		EventBus.getSelf().fireEvent(new AttributeRefreshEventObject(player));
		FightPowerKit.COMMANDER_TALENT_POWER.math(player,GameLogSource.RESET_TANLENT);
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E130_COMMANDER.CODE10);
		return message;
	}

	/**
	 * 加点天赋
	 * 
	 * @param tanlentId
	 * @return
	 */
	public ResTalentAddPointMessage addTanlentPoint(int tanlentId) {
		ResTalentAddPointMessage message = new ResTalentAddPointMessage();
		// <=0 滚
		if (commanderData.getTalentPoints() <= 0) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E130_COMMANDER.CODE10);
			return message;
		}
		TalentPir configModel = TalentPirFactory.get(tanlentId);
		// 配置不存在
		if (configModel == null || configModel.getBook() != commanderData.getUseTalentPage()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E130_COMMANDER.CODE16);
			return message;
		}
		TalentsPage talents = commanderData.getTalents().get(commanderData.getUseTalentPage());
		// 当前天赋不存在 如果当前天赋找不到 那肯定有问题
		if (talents == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E130_COMMANDER.CODE13);
			return message;
		}
		change: {
			Set<Integer> pre_id = configModel.getPre_id();
			Iterator<Integer> iterator2 = pre_id.iterator();
			while (iterator2.hasNext()) {
				Integer id = iterator2.next();
				TalentData talentData = talents.getTalents().get(id);
				if (talentData == null) {
					Language.ERRORCODE.send(player, ErrorCodeEnum.E130_COMMANDER.CODE18, id + "");
					break change;
				}
				TalentPir beforConf = TalentPirFactory.get(talentData.getSid());
				if (beforConf == null || beforConf.getUnlock_next_lv() > talentData.getLevel()) {
					Language.ERRORCODE.send(player, ErrorCodeEnum.E130_COMMANDER.CODE19, id + "",
							beforConf.getUnlock_next_lv() + "");
					break change;
				}
			}
			TalentData talentData = talents.getTalents().get(tanlentId);
			if (talentData == null) {
				talentData = new TalentData();
				talentData.setSid(tanlentId);
				talents.getTalents().put(tanlentId, talentData);
			}
			// 已是满级
			if (talentData.getLevel() >= configModel.getMax_lv()) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E130_COMMANDER.CODE20);
				break change;
			}
			
			talentData.setLevel(talentData.getLevel() + 1);
			talents.setUseTalentPoints(talents.getUseTalentPoints() + 1);
			message.talent = tanlentId;
			message.talentPoint = talentData.getLevel();
//			player.getAttributeAppenderManager().rebuild(configModel.getAttr(), talentData.getLevel(), AttributeAppenderEnum.COMMANDER.ordinal(), true);
			InjectorUtil.getInjector().dbCacheService.update(player);
			EventBus.getSelf().fireEvent(new AttributeRefreshEventObject(player));
			CurrencyUtil.send(player);
			Language.SUCCESSTIP.send(player, SuccessTipEnum.E130_COMMANDER.CODE8);
		}
		
		EventBus.getSelf().fireEvent(new AddTalentEventObject(player, EventTypeConst.ADD_TALENT, tanlentId));
		return message;
	}

	public TalentsPage getDefaultTalentPage() {
		return commanderData.getTalents().get(commanderData.getUseTalentPage());
	}

	/**
	 * 重置和切换天赋的时候 获得天赋页的方法 里面包含了各种验证 其他方法务调
	 * 
	 * @param tanlentPage
	 * @return
	 */
	private TalentsPage getSwitchAndResetTalents(int tanlentPage) {
		// 当前天赋页不包含所选天赋页
		if (!commanderData.getTalents().containsKey(tanlentPage)) {
			return null;
		}
		
		FastPaidPir paidPir = FastPaidPirFactory.get(((int[])GlobalPirFactory.get(CommanderConstant.GLOBAL_TALENT_RESET_ID).getValue())[0]);
		if (ItemKit.checkRemoveItemByTid(player, paidPir.getId(), 1)) {
			ItemKit.removeItemByTid(player, paidPir.getId(), 1, GameLogSource.SWITCH_TALENT_PAGE);
		} else if (CurrencyUtil.verify(player, paidPir.getPrice() , CurrencyEnum.DIAMOND)) {
			CurrencyUtil.decrement(player, paidPir.getPrice() , CurrencyEnum.DIAMOND, GameLogSource.SWITCH_TALENT_PAGE);
			CurrencyUtil.send(player);
		} else {
			return null;
		}
		TalentsPage talents = commanderData.getTalents().get(tanlentPage);
		if (talents == null) {
			return null;
		}
		return talents;
	}

//	/**
//	 * 获取出征数量
//	 * 
//	 * @return
//	 */
//	public int getTroopNum() {
//		MarchingTroopsPir marchingTroopsPir = MarchingTroopsPirFactory.get(commanderData.getcPoint());
//		if (marchingTroopsPir != null) {
//			return marchingTroopsPir.getNum();
//		}
//		return 0;
//	}

	/**
	 * 刷新指挥官全部数据
	 */
	public void send() {
		// 指挥官基本信息
		ResCommanderMessage commanderMessage = new ResCommanderMessage();
		commanderMessage.name = player.roleInfo().getBasics().getRoleName();
		commanderMessage.level = commanderData.getLevel();
		commanderMessage.exp = commanderData.getExp();
		commanderMessage.cPoints = commanderData.getcPoint();
		commanderMessage.energy = commanderData.getEnergy();
		commanderMessage.style = commanderData.getStyle();
		commanderMessage.talentPoints = commanderData.getTalentPoints();
		commanderMessage.talentPage = commanderData.getUseTalentPage();

		// 已穿装备
		Iterator<Long> iterator2 = commanderData.getEquits().values().iterator();
		while (iterator2.hasNext()) {
			commanderMessage.equits.add(iterator2.next());
		}

		// 已学天赋
		Map<Integer, TalentsPage> talents = commanderData.getTalents();
		Iterator<Entry<Integer, TalentsPage>> iterator3 = talents.entrySet().iterator();
		while (iterator3.hasNext()) {
			Entry<Integer, TalentsPage> next = iterator3.next();
			TalentsPage talentsPage = next.getValue();
			Iterator<TalentData> iterator = talentsPage.getTalents().values().iterator();
			while (iterator.hasNext()) {
				TalentData talentData = (TalentData) iterator.next();
				TalentPro talentPro = new TalentPro();
				talentPro.formPage = next.getKey();
				talentPro.sid = talentData.getSid();
				talentPro.level = talentData.getLevel();
				commanderMessage.talents.add(talentPro);
			}
		}
		player.send(commanderMessage);
	}

	/**
	 * 获取统计数据
	 */
	public void sendStatistic() {
		FightPowerKit.sendAllFightPower(player);
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public void setProduct(Player product) {
		this.player = product;
	}

	@Override
	public void destroy() {

	}

	public CommanderData getCommanderData() {
		return commanderData;
	}

	public void setCommanderData(CommanderData commanderData) {
		this.commanderData = commanderData;
	}

	@Override
	public Map<AttributeNodeEnum,Double> attributeAdd(Player player, AttributesEnum attributeEnum,AttributeNodeEnum...attributeNodeEnums) {
		//天赋属性加成
		Map<AttributeNodeEnum,Double> attrMap = talentPirAttributeAdd(player,attributeEnum,attributeNodeEnums);
		//统帅属性加成
		marchingTroopsPirAttributeAdd(player,attributeEnum,attrMap);
		return attrMap;
	}
	
	/**
	 * 统帅属性加成
	 * @param playerId
	 * @param attributeEnum
	 * @return
	 */
	private Map<AttributeNodeEnum,Double> marchingTroopsPirAttributeAdd(Player player, AttributesEnum attributeEnum,Map<AttributeNodeEnum,Double> attrMap,AttributeNodeEnum...attributeNodeEnums){
		Map<AttributeNodeEnum,Double> valueOfNodeMap = new HashMap<>();
		if(attributeNodeEnums.length > 0 && Arrays.binarySearch(attributeNodeEnums, AttributeNodeEnum.PLAYER) < 0){
			return valueOfNodeMap;
		}
		double value = 0;
		if(attributeEnum != AttributesEnum.MATCH_ARMY_MAX_NUM){
			return valueOfNodeMap;
		}
		MarchingTroopsPir marchingTroopsPir = MarchingTroopsPirFactory.get(player.getCommanderManager().getCommanderData().getcPoint());
		if(marchingTroopsPir!=null) {
			AttributeConfMap confMap = marchingTroopsPir.getNum();
			if(confMap != null){
				LibraryConf libraryConf = confMap.getLibraryConfs().get(1,AttributeNodeEnum.PLAYER.getCode());
				if(libraryConf != null){
					if(libraryConf.containsKey(AttributesEnum.MATCH_ARMY_MAX_NUM.getId())){
						value = libraryConf.get(AttributesEnum.MATCH_ARMY_MAX_NUM.getId()).intValue();
						valueOfNodeMap.put(AttributeNodeEnum.PLAYER, value);
						if(!attrMap.containsKey(AttributeNodeEnum.PLAYER)){
							attrMap.put(AttributeNodeEnum.PLAYER, value);
						}else{
							attrMap.put(AttributeNodeEnum.PLAYER, attrMap.get(AttributeNodeEnum.PLAYER) + value);
						}
						log.info(AttributeUtil.logBuilder(valueOfNodeMap, attributeEnum.getId(), value,MarchingTroopsPir.class.getSimpleName()));
					}
				}
			}
		}
		
		return valueOfNodeMap;
	}
	
	/**
	 * 天赋属性加成
	 * @param playerId
	 * @param attributeEnum
	 * @return
	 */
	private Map<AttributeNodeEnum,Double> talentPirAttributeAdd(Player player, AttributesEnum attributeEnum,AttributeNodeEnum...attributeNodeEnums){
		double value = 0;
		TalentsPage defaultTalentPage = player.getCommanderManager().getDefaultTalentPage();
		Map<AttributeNodeEnum,Double> valueOfNodeMap = new HashMap<>();
		if(defaultTalentPage!=null){
			Iterator<TalentData> iterator = defaultTalentPage.getTalents().values().iterator();
			while (iterator.hasNext()) {
				TalentData talentData = iterator.next();
				TalentPir configModel = TalentPirFactory.get(talentData.getSid());
				if(configModel==null){
					continue; 
				}
				AttributeConfMap confMap = configModel.getAttr();
				for(AttributeNodeEnum node : AttributeNodeEnum.values()){
					if(attributeNodeEnums.length > 0 && Arrays.binarySearch(attributeNodeEnums,node) < 0){
						continue;
					}
					LibraryConf libraryConf = confMap.getLibraryConfs().get(talentData.getLevel(), node.getCode());
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
		}
		if(valueOfNodeMap.size() > 0){
			log.info(AttributeUtil.logBuilder(valueOfNodeMap, attributeEnum.getId(), value,TalentPir.class.getSimpleName()));
		}
		return valueOfNodeMap;
	}

	@Override
	public Map<AttributeFromEnum, Double> selectAttributeAdd(Player player,
			AttributesEnum attributeEnum, AttributeNodeEnum node) {
		Map<AttributeNodeEnum,Double> nodeAttrMap = attributeAdd(player,attributeEnum, node);
		Map<AttributeFromEnum, Double> resultMap = new HashMap<>();
		if(nodeAttrMap.containsKey(node)){
			resultMap.put(AttributeFromEnum.COMMANDER, nodeAttrMap.get(node));
		}
		return resultMap;
	}
}
