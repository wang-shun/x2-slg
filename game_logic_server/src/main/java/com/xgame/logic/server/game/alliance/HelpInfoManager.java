package com.xgame.logic.server.game.alliance;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xgame.config.building.BuildingPir;
import com.xgame.config.building.BuildingPirFactory;
import com.xgame.config.global.GlobalPirFactory;
import com.xgame.gameconst.DBKey;
import com.xgame.gameconst.GlobalConstant;
import com.xgame.logic.server.core.db.cache.cache.CacheProxy;
import com.xgame.logic.server.core.utils.sequance.IDFactrorySequencer;
import com.xgame.logic.server.game.alliance.constant.HelpType;
import com.xgame.logic.server.game.alliance.enity.Alliance;
import com.xgame.logic.server.game.alliance.enity.HelpInfo;
import com.xgame.logic.server.game.country.structs.build.BuildFactory;
import com.xgame.logic.server.game.country.structs.build.camp.data.SoldierBrief;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.calculation.AttributeCounter;
import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;
import com.xgame.logic.server.game.timertask.constant.TimerTaskCommand;
import com.xgame.logic.server.game.timertask.entity.job.CreatBuildTask;
import com.xgame.logic.server.game.timertask.entity.job.data.BuildTimerTaskData;
import com.xgame.logic.server.game.timertask.entity.job.data.ModTimerTaskData;
import com.xgame.logic.server.game.timertask.entity.job.data.TechTimerTaskData;
import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;

/**
 * 联盟帮助
 * @author jacky.jiang
 *
 */
@Component
public class HelpInfoManager extends CacheProxy<HelpInfo> {
	
	@Autowired 
	private IDFactrorySequencer idSequencer;
	
	@Override
	public Class<?> getProxyClass() {
		return HelpInfo.class;
	}
	
	/**
	 * 生成联盟帮助
	 * @param level
	 * @param maxcount
	 * @param sender
	 * @param startTime
	 * @param type
	 * @param sid
	 * @param reduceSec
	 * @return
	 */
	public HelpInfo factoryAllianceHelp(Player player, Alliance alliance, TimerTaskData timerTaskData) {
		
		int level = 0;
		int sid = 0;
		int maxCount = 0;
		int type = 0;
		String value = GlobalPirFactory.get(GlobalConstant.HELP_REDUCE_TIME_RATIO).getValue();
		double rate = AttributeCounter.getPlayerAttribute(player.getId(),AttributesEnum.HELP_REDUCE_BUILDING_TIME, Double.valueOf(value));
		int reduceSec = Double.valueOf(timerTaskData.getTaskTime() * rate).intValue();
		BuildingPir buildingPir = BuildingPirFactory.get(BuildFactory.MAIN.getTid());
		if(buildingPir != null) {
			Map<Integer, Integer> mainBuildMap = buildingPir.getV1();
			if(mainBuildMap != null) {
				maxCount = mainBuildMap.get(player.getCountryManager().getMainBuildControl().getMaxLevelBuild().getLevel());
			}
		}
		
		if(timerTaskData.getQueueId() == TimerTaskCommand.BUILD.getId()) {
			BuildTimerTaskData buildTimerTaskData = (BuildTimerTaskData)timerTaskData.getParam();
			if(buildTimerTaskData != null) {
				sid = buildTimerTaskData.getSid();
				level = buildTimerTaskData.getLevel() + 1;
			}
			
			if(timerTaskData.geteType() == CreatBuildTask.CREATE_BUILD_CMD) {
				type = HelpType.CREATE_BUILD.ordinal();
			} else if(timerTaskData.geteType() == CreatBuildTask.LEVEL_UP_BUILD_CMD) {
				type = HelpType.LEVEL_UP_BUILD.ordinal();
			}
		} else if(timerTaskData.getQueueId() == TimerTaskCommand.LEVEL_TECH.getId()) {
			TechTimerTaskData techTimerTaskData = (TechTimerTaskData)timerTaskData.getParam();
			if(techTimerTaskData != null) {
				sid = techTimerTaskData.getSid();
				level = techTimerTaskData.getLevel() + 1;
			}
			type = HelpType.TECH_LEVEL_UP.ordinal();
		} else if(timerTaskData.getQueueId() == TimerTaskCommand.MOD_SOLODIER.getId()) {
			ModTimerTaskData modTimerTaskData = (ModTimerTaskData)timerTaskData.getParam();
			int num = 0;
			if(modTimerTaskData != null) {
				for(SoldierBrief soldierBrief : modTimerTaskData.getSoldierList()) {
					num += soldierBrief.getNum();
				}
			}
			level = num;
			type = HelpType.MOD_SOLDIER.ordinal();
		}
		
		// 联盟帮助
		HelpInfo helpInfo = new HelpInfo();
		helpInfo.setHelpId(idSequencer.createEnityID(DBKey.ALLIANCE_HELP_ID_KEY));
		helpInfo.setLevel(level);
		helpInfo.setMaxcount(maxCount);
		helpInfo.setNowcount(0);
		helpInfo.setReduceSec(reduceSec);
		helpInfo.setSenderd(player.getId());
		helpInfo.setSid(sid);
		helpInfo.setStartTime(System.currentTimeMillis());
		helpInfo.setType(type);
		helpInfo.setTaskId(timerTaskData.getId());
		return helpInfo;
	}
	
	
}
