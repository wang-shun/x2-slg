package com.xgame.logic.server.game.country.structs.build.tach;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.xgame.common.AwardConfList;
import com.xgame.common.BuildCondtitionBean;
import com.xgame.config.science.SciencePir;
import com.xgame.config.science.SciencePirFactory;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.language.view.success.SuccessTipEnum;
import com.xgame.logic.server.core.utils.CurrencyUtil;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.awardcenter.AwardUtil;
import com.xgame.logic.server.game.constant.SystemEnum;
import com.xgame.logic.server.game.country.entity.CountryBuild;
import com.xgame.logic.server.game.country.structs.build.CountryBuildControl;
import com.xgame.logic.server.game.country.structs.build.tach.data.Tech;
import com.xgame.logic.server.game.country.structs.build.tach.data.TechData;
import com.xgame.logic.server.game.country.structs.build.tach.eventmodel.ResearchLevelUpEndEventObject;
import com.xgame.logic.server.game.country.structs.build.tach.eventmodel.ResearchLevelUpEventObject;
import com.xgame.logic.server.game.country.structs.status.TimeState;
import com.xgame.logic.server.game.customweanpon.message.ResPartListMessage;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.PlayerAttributeManager;
import com.xgame.logic.server.game.playerattribute.entity.eventmodel.AttributeRefreshEventObject;
import com.xgame.logic.server.game.playerattribute.fightpower.FightPowerKit;
import com.xgame.logic.server.game.tech.bean.TechBean;
import com.xgame.logic.server.game.tech.message.ResAllTechMessage;
import com.xgame.logic.server.game.tech.message.ResLevelUpTechMessage;
import com.xgame.logic.server.game.timertask.constant.TimerTaskCommand;
import com.xgame.logic.server.game.timertask.entity.job.TimerTaskHolder;
import com.xgame.logic.server.game.timertask.entity.job.data.TechTimerTaskData;

/**
 *
 * 2016-7-22 14:43:36
 *
 * @author ye.yuan
 *
 */
public class TechBuildControl extends CountryBuildControl {
	
	TechData techData;
	
	public void initControl(Player player){
		techData = player.roleInfo().getTechs();
	}
	
	public void dataLoadAfter(Player player) {
		techData = player.roleInfo().getTechs();
	}
	

	private boolean verifyBeforeTech(SciencePir config, Tech tach,
			CountryBuild build) {
		Map<Integer, BuildCondtitionBean> techCondtition = config.getRequire_id();  
		BuildCondtitionBean condtitionBean = techCondtition.get(tach.getLevel());
		if (condtitionBean != null) {
			Iterator<Entry<Integer, Integer>> iterator = condtitionBean.getCache().entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<Integer, Integer> entry = iterator.next();
				Tech otherTach = techData.getTechs().get(entry.getKey());
				if (otherTach == null || otherTach.getLevel() < entry.getValue()) {
					return false;
				}
			}
		}
		return true;
	}

	public void techLevelUp(Player player, int sid, int useType) {
		SciencePir config = SciencePirFactory.get(sid);
		if(config==null){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E107_TECH.CODE1.get());
			return;
		}
		
		Tech tech = techData.getTechs().get(sid);
		if (tech == null) {
			tech = new Tech();
			tech.setId(sid);
			techData.getTechs().put(sid, tech);
		}
		// 如果有队列在执行 走
		if (useType == CurrencyUtil.USE && TimerTaskHolder.getTimerTask(TimerTaskCommand.LEVEL_TECH).checkQueueCapacityMax(player)) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E107_TECH.CODE2.get());
			return;
		}
		CountryBuild build = getDefianlBuild();
		if (tech.getLevel() >= config.getMax_lv()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E107_TECH.CODE3.get());
			return;
		}
		// 前置科技或建筑未全部解锁
		if (!verifyBeforeTech(config, tech, build)) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E107_TECH.CODE4.get());
			return;
		}
		if (!player.getCountryManager().verifyBeforeBuild(player, config.getBuilding_id(), tech.getLevel())) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E107_TECH.CODE5.get());
			return;
		}
		
		Map<Integer, Integer> cost_oil = config.getCost_oil();
		Map<Integer, Integer> cost_cash = config.getCost_cash();
		Map<Integer, Integer> cost_earth = config.getCost_earth();
		Map<Integer, Integer> cost_steel = config.getCost_steel();
		Map<Integer, Integer> cost_time = config.getCd();

		int level = tech.getLevel();
		Integer oil = cost_oil.get(level);
		Integer money = cost_cash.get(level);
		Integer steels = cost_steel.get(level);
		Integer rare = cost_earth.get(level);
		int time = cost_time.get(level);
		time = PlayerAttributeManager.get().scienceTime(player.getId(), time);
		// 扣钱失败
		if (!CurrencyUtil.decrementCurrency(player, money, steels, oil, rare, useType, time, GameLogSource.TECH_LEVEL_UP)) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E107_TECH.CODE6.get());
			return;
		}
		switch (useType) {
		case CurrencyUtil.USE:
			// 改变等级
			tech.setState(TimeState.LEVEL_UP.ordinal());
			InjectorUtil.getInjector().dbCacheService.update(player);

			// 去加速
//			time = (int)AttributeEnum.SCIENCE_TIME.playerMath(player, time); 
			TimerTaskHolder.getTimerTask(TimerTaskCommand.LEVEL_TECH).register(player, time, new TechTimerTaskData(sid, build.getState(), build.getUid(), build.getLevel()));
			break;
		case CurrencyUtil.FAST_USE:
			// 改变等级
			levelSuccess(player, sid);
			InjectorUtil.getInjector().dbCacheService.update(player);
			break;
		default:
			Language.ERRORCODE.send(player, ErrorCodeEnum.E107_TECH.CODE7.get());
			return;
		}
		EventBus.getSelf().fireEvent(new ResearchLevelUpEventObject(player,tech.getId(),level + 1));
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E107_TECH.CODE1.get());
	}

	public void levelSuccess(Player player, int techSid) {
		Tech tech = techData.getTechs().get(techSid);
		tech.setLevel(tech.getLevel() + 1);
		// 添加属性
		SciencePir config = SciencePirFactory.get(techSid);
//		player.getAttributeAppenderManager().rebuild(config.getV1(), tech.getLevel(), AttributeAppenderEnum.TECH.ordinal(), true);
		EventBus.getSelf().fireEvent(new AttributeRefreshEventObject(player));
		//加战力
		FightPowerKit.TECH_POWER.math(player,GameLogSource.TECH_LEVEL_UP);
		// 发送等级改变消息
		sendLevel(player, techSid, tech);
		CurrencyUtil.send(player);
		int addCombat;
		if(tech.getLevel() == 1){
			addCombat = SciencePirFactory.getInstance().getFightPower(techSid, tech.getLevel());
		}else{
			addCombat = SciencePirFactory.getInstance().getFightPower(techSid, tech.getLevel()) - SciencePirFactory.getInstance().getFightPower(techSid, tech.getLevel() - 1);
		}
		EventBus.getSelf().fireEvent(new ResearchLevelUpEndEventObject(player,techSid,tech.getLevel(),addCombat));
		//发奖
		HashMap<Integer, AwardConfList> map = config.getExp();
		AwardUtil.awardToCenter(player, map.get(tech.getLevel()),SystemEnum.COUNTRY.getId(), GameLogSource.TECH_LEVEL_UP);
	}
	
	public void cancelTechUp(Player player, int sid) {
		Tech tech = techData.getTechs().get(sid);
		SciencePir config = SciencePirFactory.get(tech.getId());
		Map<Integer, Integer> cost_oil = config.getCost_oil();
		Map<Integer, Integer> cost_cash = config.getCost_cash();
		Map<Integer, Integer> cost_earth = config.getCost_earth();
		Map<Integer, Integer> cost_steel = config.getCost_steel();
		
		Integer oil = cost_oil.get(tech.getLevel());
		Integer money = cost_cash.get(tech.getLevel());
		Integer steels = cost_steel.get(tech.getLevel());
		Integer rare = cost_earth.get(tech.getLevel());
		
		double oil1 = oil * 0.5d;
		double money1 = money * 0.5f;
		double steels1 = steels * 0.5f;
		double rare1 = rare * 0.5f;
		CurrencyUtil.increaseCurrency(player, money1, steels1, oil1, rare1, GameLogSource.CANCEL_TECH_LEVEL_UP);
		InjectorUtil.getInjector().dbCacheService.update(player);
		CurrencyUtil.send(player);
	}

	public void sendLevel(Player player, int sid, Tech tech) {
		ResLevelUpTechMessage resAllTechMessage = new ResLevelUpTechMessage();
		resAllTechMessage.sid = sid;
		resAllTechMessage.level = tech.getLevel();
		player.send(resAllTechMessage);
	}

	public void send(Player player) {
		ResAllTechMessage resAllTechMessage = new ResAllTechMessage();
		Iterator<Tech> iterator = techData.getTechs().values().iterator();
		while (iterator.hasNext()) {
			Tech tech = (Tech) iterator.next();
			TechBean bean = new TechBean();
			bean.level = tech.getLevel();
			bean.sid = tech.getId();
			resAllTechMessage.techs.add(bean);
		}
		player.send(resAllTechMessage);
		
		ResPartListMessage pJIDlist = new ResPartListMessage();
		
		Iterator<Integer> ator = player.roleInfo().getSoldierData().getUnlockPeijians().values().iterator();
		while (ator.hasNext()) {
			Integer a = (Integer) ator.next();
			pJIDlist.partIdList.add(a);
		}
		player.send(pJIDlist);
		
	}
	
	
	@Override
	public void createEnd(Player player, int uid) {
		super.createEnd(player, uid);
		EventBus.getSelf().fireEvent(new AttributeRefreshEventObject(player));
//		PlayerAttributeObject playerAttributeObject = (PlayerAttributeObject)player.getAttributeAppenderManager().getPlayerAttributeObject();
//		if(playerAttributeObject != null) {
//			playerAttributeObject.addLibrary(AttributeEnum.TECH_QUEUE.getId(), Double.valueOf(InitPirFactory.get(InitPirFactory.ID).getValue_9()));
//			InjectorUtil.getInjector().dbCacheService.update(player);
//		}
	}

	public TechData getTechData() {
		return techData;
	}
}
