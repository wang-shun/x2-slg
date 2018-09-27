package com.xgame.logic.server.game.timertask.entity.job;

import java.util.Iterator;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.game.alliance.converter.AllianceConverter;
import com.xgame.logic.server.game.alliance.enity.Alliance;
import com.xgame.logic.server.game.alliance.enity.HelpInfo;
import com.xgame.logic.server.game.alliance.message.ResAllianceHelpUpdateMessage;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.timertask.constant.TimerTaskCommand;
import com.xgame.logic.server.game.timertask.entity.eventmodel.CancelTimerTaskEventObject;
import com.xgame.logic.server.game.timertask.entity.eventmodel.CreateTimerTaskEventObject;
import com.xgame.logic.server.game.timertask.entity.eventmodel.EndTimerTaskEventObject;
import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;
import com.xgame.logic.server.game.timertask.message.ResRemoveTimerMessage;
import com.xgame.utils.TimeUtils;


/**
 * 抽象timer类
 * @author jacky.jiang
 * @param <T>
 */
@Slf4j
public abstract class AbstractTimerTask implements ITimerTask<TimerTaskData>{
	
	private TimerTaskCommand timerTaskCommand;
	
	public AbstractTimerTask(TimerTaskCommand timerTaskCommand) {
		this.timerTaskCommand = timerTaskCommand;
	}

	@Override
	public TimerTaskData register(Player player, int cmd, int taskTime, Object param) {
		if(getTimerTaskCommand().getCmds() != null && getTimerTaskCommand().getCmds().size() > 0 && !getTimerTaskCommand().getCmds().contains(cmd)) {
			log.error("cmd not find ");
			return null;
		}
		
		// 创建timer加锁
		synchronized (this) {
			TimerTaskData t = factoryTimerTaskData(param);
			
			// 初始化timer数据
			initTimerTaskData(player, t, cmd, taskTime);
			
			// 添加成功回调
			if(onAdd(player, t)) {
				// 注册到timer管理器
				player.getTimerTaskManager().addTimerTask(player, t, this.getRunnable(player, t));
				try {
					String jsonParam = JsonUtil.toJSON(t.getParam());
					CreateTimerTaskEventObject createTimerTaskEventObject = new CreateTimerTaskEventObject(player, EventTypeConst.CREATE_TIMER_TASK, cmd, taskTime, jsonParam);
					EventBus.getSelf().fireEvent(createTimerTaskEventObject);	
				} catch(Exception e) {
					log.error("日志添加异常：", e);
				}
				return t;
			} else {
				// 添加不处理
				return null;
			}	
		}
	}

	@Override
	public TimerTaskData register(Player player, int taskTime, Object param) {
		return this.register(player, 0, taskTime, param);
	}

	/**
	 * 返回timerTask
	 * @param params
	 * @return
	 */
	public TimerTaskData factoryTimerTaskData(Object obj) {
		TimerTaskData timerTaskData = new TimerTaskData();
		timerTaskData.setParam(obj);
		return timerTaskData;
	}
	
	
	/**
	 * 初始化timerTaskData
	 * @param player
	 * @param t
	 * @param cmd
	 * @param taskTime
	 */
	public void initTimerTaskData(Player player, TimerTaskData t, int cmd, int taskTime) {
		int currentTime = TimeUtils.getCurrentTime();
		t.setTaskId(InjectorUtil.getInjector().timerTaskSequance.genTimerId());
		t.setRoleId(player.getRoleId());
		t.setQueueId(getTimerTaskCommand().getId());
		t.seteType(cmd);
		t.setTaskTime(taskTime);
		t.setTriggerTime(currentTime + taskTime);
		t.setStartTime(currentTime);
		t.setCreateTime(currentTime);
	}
	

	@Override
	public boolean speedUp(Player player, TimerTaskData data, int time) {
		TimerTaskData timerTaskData = player.getTimerTaskManager().getTimerTaskData(data.getTaskId());
		if(timerTaskData == null) {
			log.error("定时器任务不存在,任务id:[{}], 任务类型:[{}], 角色id[{}]", data.getTaskId(), data.getQueueId(), data.getRoleId());
			return false;
		}
		
		// 加速
		int currentTime = TimeUtils.getCurrentTime();
		int period = (int)(timerTaskData.getTriggerTime() - time - currentTime);
		int speedTime =  period > 0 ? period : 0;
		player.getTimerTaskManager().resetTimerTask(player, data, currentTime, speedTime);
		
		// 发送重置
		player.getTimerTaskManager().sendTimerTask(player, Lists.newArrayList(timerTaskData));
		return true;
	}

	/**
	 * 任务队列到期执行
	 * @param player
	 * @param data
	 * @return
	 */
	private void execute(Player player, TimerTaskData data) {
		synchronized (data) {
			// 任务执行
			try {
				if(onExecute(player, data)) {
					// 增加timer次数
					data.incrCount();
					
					// 任务结束移除任务
					if(data.isOver()) {
						InjectorUtil.getInjector().timerTaskManager.removeTimerTask(player, data);	
						onRemove(player, data);
					} else {
						// 重置任务
						if(onReset(player, data)) {
							InjectorUtil.getInjector().timerTaskManager.resetTimerTask(player, data, 0, 0);
						}
					}
				}
			} catch(Exception e) {
				onException(player, data);
				log.error("任务队列执行报错", e);
			}
		}
	}

	/**
	 * 获得任务执行
	 * @return
	 */
	public Runnable getRunnable(Player player, TimerTaskData data) {
		return new Runnable() {
			public void run() {
				try {
					player.async(new Runnable() {
						@Override
						public void run() {
							execute(player, data);
						}
					});
				} catch(Exception e) {
					log.error("定时任务执行报错: 任务id:{}, 类型 :{}", data.getTaskId(), data.getQueueId(), e);
				}
			}
		};
	}
	
	public boolean onAdd(Player player, TimerTaskData data) {
		player.getTimerTaskManager().sendTimerTask(player, Lists.newArrayList(data));
		return true;
	}
	
	public boolean onExecute(Player player, TimerTaskData data) {
		return true;
	}
	
	public boolean onReset(Player player, TimerTaskData data) {
		player.getTimerTaskManager().sendTimerTask(player, Lists.newArrayList(data));
		return true;
	}
	
	public boolean onRemove(Player player, TimerTaskData data) {
		ResRemoveTimerMessage resTimerRunMessage = new ResRemoveTimerMessage();
		resTimerRunMessage.timerUid = data.getTaskId();
		player.send(resTimerRunMessage);
		
		try {
			String jsonParam = JsonUtil.toJSON(data.getParam());
			EndTimerTaskEventObject createTimerTaskEventObject = new EndTimerTaskEventObject(player, EventTypeConst.CREATE_TIMER_TASK, data.getQueueId(), jsonParam);
			EventBus.getSelf().fireEvent(createTimerTaskEventObject);	
			//TODO
			// 移除帮助id
			if(!StringUtils.isEmpty(data.getHelpId())) {
				HelpInfo helpInfo = InjectorUtil.getInjector().dbCacheService.get(HelpInfo.class, Long.valueOf(data.getHelpId()));
				if(helpInfo != null) {
					Alliance alliance = InjectorUtil.getInjector().dbCacheService.get(Alliance.class, player.getAllianceId());
					if(alliance != null) {
						alliance.getHelpList().remove(Long.valueOf(data.getHelpId()));
						InjectorUtil.getInjector().dbCacheService.update(alliance);
						
						ResAllianceHelpUpdateMessage resAllianceHelpUpdateMessage = new ResAllianceHelpUpdateMessage();
						resAllianceHelpUpdateMessage.allianceHelpBean = AllianceConverter.converterAllianceHelp(player, helpInfo, "",0);
						InjectorUtil.getInjector().sessionManager.writePlayers(alliance.getAllianceMember(), resAllianceHelpUpdateMessage);
					}
					InjectorUtil.getInjector().dbCacheService.delete(helpInfo);
				}
			}
		} catch (Exception e) {
			log.error("时间杜列结束异常：", e);
		}
		
		return true;
	}

	@Override
	public boolean cancelTask(Player player, TimerTaskData data) {
		player.getTimerTaskManager().removeTimerTask(player, data);
		
		try {
			ResRemoveTimerMessage cancelMessage  = new ResRemoveTimerMessage(); 
			cancelMessage.timerUid=data.getTaskId();
			player.send(cancelMessage);
			
			// 取消时间队列任务日志
			EventBus.getSelf().fireEvent(new CancelTimerTaskEventObject(player, EventTypeConst.END_TIMER_TASK, timerTaskCommand.getId(), JsonUtil.toJSON(data.getParam())));
			
			// 移除帮助id
			if(!StringUtils.isEmpty(data.getHelpId())) {
				HelpInfo helpInfo = InjectorUtil.getInjector().dbCacheService.get(HelpInfo.class, Long.valueOf(data.getHelpId()));
				if(helpInfo != null) {
					Alliance alliance = InjectorUtil.getInjector().dbCacheService.get(Alliance.class, player.getAllianceId());
					if(alliance != null) {
						alliance.getHelpList().remove(Long.valueOf(data.getHelpId()));
						InjectorUtil.getInjector().dbCacheService.update(alliance);
						
						ResAllianceHelpUpdateMessage resAllianceHelpUpdateMessage = new ResAllianceHelpUpdateMessage();
						resAllianceHelpUpdateMessage.allianceHelpBean = AllianceConverter.converterAllianceHelp(player, helpInfo, "",0);
						InjectorUtil.getInjector().sessionManager.writePlayers(alliance.getAllianceMember(), resAllianceHelpUpdateMessage);
					}
					InjectorUtil.getInjector().dbCacheService.delete(helpInfo);
				}
			}
		} catch(Exception e) {
			log.error("时间队列取消异常：", e);
		}
		
		return true;
	}
	
	/**
	 * 异常处理
	 * @param player
	 * @param data
	 */
	public void onException(Player player, TimerTaskData data) {
		InjectorUtil.getInjector().dbCacheService.delete(data);
		log.error("任务队列异常:{}",data.toString());
	}

	@Override
	public TimerTaskCommand getTimerTaskCommand() {
		return timerTaskCommand;
	}

	public void setTimerTaskCommand(TimerTaskCommand timerTaskCommand) {
		this.timerTaskCommand = timerTaskCommand;
	}
	
	@Override
	public void resetTimerTask(Player player, TimerTaskData timerTaskData) {
		// 重置队列信息
		player.getTimerTaskManager().resetTimerTask(player, timerTaskData, 0, 0);
		InjectorUtil.getInjector().dbCacheService.update(player);
	}

	@Override
	public boolean checkQueueCapacityMax(Player player) {
		int num=0;
		Iterator<Long> iterator2 = player.roleInfo().getTimerTaskMap().values().iterator();
		while (iterator2.hasNext()) {
			Long taskId = iterator2.next();
			TimerTaskData timerTaskData = player.getTimerTaskManager().getTimerTaskData(taskId);
			if(timerTaskData == null) {
				player.roleInfo().getTimerTaskMap().remove(taskId);
				log.error("玩家队列参数没有移除,taskId:{}", taskId);
				continue;
			}
			
			if(timerTaskData.getQueueId() == timerTaskCommand.getId()) {
				num++;
				if (num >= maxQueueCapacity(player)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public int maxQueueCapacity(Player player) {
		return 1;
	}

	@Override
	public TimerTaskData dealRestart(TimerTaskData timerTaskData) {
		return null;
	}
}
