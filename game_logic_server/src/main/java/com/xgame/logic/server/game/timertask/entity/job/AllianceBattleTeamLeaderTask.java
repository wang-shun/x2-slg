package com.xgame.logic.server.game.timertask.entity.job;

import lombok.extern.slf4j.Slf4j;

import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.alliance.constant.AllianceTeamState;
import com.xgame.logic.server.game.alliance.enity.AllianceBattleTeam;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.timertask.constant.TimerTaskCommand;
import com.xgame.logic.server.game.timertask.entity.job.data.AllianceBattleTeamLeaderData;
import com.xgame.logic.server.game.timertask.entity.job.data.RunCampTaskData;
import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;
import com.xgame.logic.server.game.world.constant.MarchState;
import com.xgame.logic.server.game.world.entity.WorldMarch;

/**
 * 联盟战队集结
 * @author jacky.jiang
 *
 */
@Slf4j
public class AllianceBattleTeamLeaderTask extends AbstractTimerTask {

	public AllianceBattleTeamLeaderTask(TimerTaskCommand timerTaskCommand) {
		super(timerTaskCommand);
	}

//	@Override
//	public TimerTaskData factoryTimerTaskData(Object obj) {
//		TimerTaskData timerTaskData = new TimerTaskData();
//		AllianceBattleTeamLeaderData teamAttackTaskData = new AllianceBattleTeamLeaderData();
//		teamAttackTaskData.setTeamId((String) params[0]);
//		timerTaskData.setParam(teamAttackTaskData);
//		return timerTaskData;
//	}

	@Override
	public boolean onExecute(Player player, TimerTaskData data) {
		AllianceBattleTeamLeaderData teamAttackTaskData = (AllianceBattleTeamLeaderData)data.getParam();
		if(teamAttackTaskData != null) {
			AllianceBattleTeam allianceBattleTeam = InjectorUtil.getInjector().dbCacheService.get(AllianceBattleTeam.class, teamAttackTaskData.getTeamId());
			if(allianceBattleTeam != null) {
				// 开始行军
				WorldMarch worldMarch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, teamAttackTaskData.getMarchId());
				if(worldMarch != null) {
					player.getWorldLogicManager().getWorldExecutor().execute(new Runnable() {
						@Override
						public void run() {
							log.error("集结行军出发{}", player.getId());
							int remainMarchTime = (int)((worldMarch.getMarchArrivalTime() - System.currentTimeMillis()) / 1000);
							TimerTaskData timerTaskData = TimerTaskHolder.getTimerTask(TimerTaskCommand.RUN_SOLDIER).register(player, remainMarchTime, new RunCampTaskData(worldMarch.getId()));
							if(timerTaskData != null) {
								worldMarch.setMarchState(MarchState.MARCH);
								worldMarch.setTaskId(timerTaskData.getId());
								InjectorUtil.getInjector().dbCacheService.update(worldMarch);
								worldMarch.executor.pushToWorldMarch(player, worldMarch);
								
								// 设置玩家状态在行军
								AllianceBattleTeam allianceBattleTeam = InjectorUtil.getInjector().dbCacheService.get(AllianceBattleTeam.class, worldMarch.getTeamId());
								if(allianceBattleTeam != null) {
									allianceBattleTeam.setAllianceTeamState(AllianceTeamState.MARCH);
									InjectorUtil.getInjector().dbCacheService.update(allianceBattleTeam);
								}
							}
						}
					});
				}
			}
		}
		return super.onExecute(player, data);
	}

}
