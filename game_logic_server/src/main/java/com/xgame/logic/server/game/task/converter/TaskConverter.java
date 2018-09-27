package com.xgame.logic.server.game.task.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xgame.config.achieve.AchievePirFactory;
import com.xgame.config.active.ActivePir;
import com.xgame.config.active.ActivePirFactory;
import com.xgame.config.task.TaskPir;
import com.xgame.config.task.TaskPirFactory;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.task.bean.ActiveTaskBean;
import com.xgame.logic.server.game.task.bean.BaseTaskBean;
import com.xgame.logic.server.game.task.bean.MedalTaskBean;
import com.xgame.logic.server.game.task.bean.PlayerActiveTaskBean;
import com.xgame.logic.server.game.task.bean.PlayerMedalTaskBean;
import com.xgame.logic.server.game.task.bean.PlayerTimeRefreshTaskBean;
import com.xgame.logic.server.game.task.bean.TimeTaskInfoBean;
import com.xgame.logic.server.game.task.constant.Sequence;
import com.xgame.logic.server.game.task.constant.TaskConstant;
import com.xgame.logic.server.game.task.enity.ActiveInfo;
import com.xgame.logic.server.game.task.enity.ActiveItem;
import com.xgame.logic.server.game.task.enity.BaseTask;
import com.xgame.logic.server.game.task.enity.MedalTask;
import com.xgame.logic.server.game.task.enity.MedalTaskInfo;
import com.xgame.logic.server.game.task.enity.RewardsStatus;
import com.xgame.logic.server.game.task.enity.TimeRefreshTask;
import com.xgame.logic.server.game.task.enity.TimeTask;
import com.xgame.logic.server.game.task.message.ResDayTaskMessage;
import com.xgame.logic.server.game.task.message.ResGetAchieveRewardMessage;
import com.xgame.logic.server.game.task.message.ResGetTimeRefreshTaskRewardMessage;
import com.xgame.logic.server.game.task.message.ResMedalTaskInfoMessage;
import com.xgame.logic.server.game.task.message.ResPushActiveTaskMessage;
import com.xgame.logic.server.game.task.message.ResPushBaseTaskMessage;
import com.xgame.logic.server.game.task.message.ResQueryActiveInfoMessage;
import com.xgame.logic.server.game.task.message.ResQueryTaskInfoMessage;
import com.xgame.logic.server.game.task.message.ResRewardActiveBoxMessage;
import com.xgame.logic.server.game.task.message.ResRewardTaskInfoMessage;
import com.xgame.logic.server.game.task.parser.active.BaseActiveParser;
import com.xgame.logic.server.game.task.parser.basetask.BaseParser;

public class TaskConverter {
	
	
	public static ResQueryTaskInfoMessage resQueryTaskInfoMessageBuilder(Player player){
		ResQueryTaskInfoMessage info = new ResQueryTaskInfoMessage();
		Map<Integer,BaseTask> baseTask = player.roleInfo().getTaskInfo().getBaseTask();
		List<BaseTask> list = new ArrayList<BaseTask>(baseTask.values());
		Collections.sort(list, Sequence.BASETASK_SORT);
		Map<Integer,Integer> countMap = new HashMap<>();
		for(BaseTask task : list){
			if(task.getTag() != 0){
				continue;
			}
			TaskPir pir = TaskPirFactory.get(task.getTaskId());
			if(!countMap.containsKey(pir.getTab())){
				countMap.put(pir.getTab(), 1);
			}else{
				countMap.put(pir.getTab(), countMap.get(pir.getTab()) + 1);
			}
			if(!task.isComplete() || task.isGet()){
				if(countMap.get(pir.getTab()) > TaskConstant.BASE_TASK_SELECT_NUM){
					continue;
				}
			}
			BaseParser parser = BaseParser.baseParserMap.get(pir.getType());
			if(parser != null){
				parser.beforRead(player, task);
			}
			if(task.isComplete() && !task.isGet()){
				info.finishTaskId.add(task.getTaskId());
			}else{
				BaseTaskBean bean = baseTaskBeanBuilder(task);
				info.baseTaskList.add(bean);
			}
		}
		
		return info;
	}
	
	public static ResRewardTaskInfoMessage resRewardTaskInfoMessageBuilder(Player player,int taskId){
		ResQueryTaskInfoMessage info0 = resQueryTaskInfoMessageBuilder(player);
		ResRewardTaskInfoMessage info1 = new ResRewardTaskInfoMessage();
		info1.baseTaskList = info0.baseTaskList;
		info1.finishTaskId = info0.finishTaskId;
		info1.taskId = taskId;
		
		return info1;
	}
	
	public static ResQueryActiveInfoMessage resQueryActiveInfoMessageBuilder(Player player){
		ResQueryActiveInfoMessage info = new ResQueryActiveInfoMessage();
		ActiveInfo activeInfo = player.roleInfo().getTaskInfo().getActiveInfo();
		PlayerActiveTaskBean playerActiveTask = playerActiveTaskBeanBuilder(activeInfo);
		info.playerActiveTask = playerActiveTask;
		
		for(ActiveItem activeItem : activeInfo.getActiveItems().values()){
			ActivePir activePir = ActivePirFactory.get(activeItem.getId());
			BaseActiveParser parser = BaseActiveParser.activeParserMap.get(activePir.getType());
			if(parser != null){
				parser.beforRead(player, activeItem);
			}
			ActiveTaskBean activeTaskBean = activeTaskBeanBuilder(activeItem,activePir);
			info.activeTaskList.add(activeTaskBean);
		}
		return info;
	}
	
	public static ResRewardActiveBoxMessage resRewardActiveBoxMessageBuilder(Player player,int tag){
		ResQueryActiveInfoMessage info0 = resQueryActiveInfoMessageBuilder(player);
		ResRewardActiveBoxMessage info1 = new ResRewardActiveBoxMessage();
		info1.playerActiveTask = info0.playerActiveTask;
		info1.tag = tag;
		return info1;
	}
	
	public static PlayerActiveTaskBean playerActiveTaskBeanBuilder(ActiveInfo activeInfo){
		PlayerActiveTaskBean playerActiveTask = new PlayerActiveTaskBean();
		for(RewardsStatus reward : activeInfo.getRewards().values()){
			if(reward.getStatus() == 1){
				playerActiveTask.rewardTag.add(reward.getId());
			}
		}
		playerActiveTask.rewardActiveNum = activeInfo.getValue();
		return playerActiveTask;
	}
	
	
	public static ResDayTaskMessage resDayTaskMessageBuilder(Player player){
		ResDayTaskMessage info = new ResDayTaskMessage();
		PlayerTimeRefreshTaskBean playerTimeRefreshTask = new PlayerTimeRefreshTaskBean();
		TimeRefreshTask timeRefreshTask = player.roleInfo().getTaskInfo().getTimeRefreshTask();
		for(TimeTask task : timeRefreshTask.getDayTask()){
			TimeTaskInfoBean bean = timeTaskInfoBeanBuilder(task);
			playerTimeRefreshTask.dayTask.add(bean);
		}
		if(player.getAllianceId() > 0){
			for(TimeTask task : timeRefreshTask.getGuildTask()){
				TimeTaskInfoBean bean = timeTaskInfoBeanBuilder(task);
				playerTimeRefreshTask.guildTask.add(bean);
			}
		}
		playerTimeRefreshTask.refreshTime = timeRefreshTask.getRefreshTime(); 
		info.playerTimeRefreshTask = playerTimeRefreshTask;
		
		return info;
	}
	
	private static TimeTaskInfoBean timeTaskInfoBeanBuilder(TimeTask task){
		TimeTaskInfoBean bean = new TimeTaskInfoBean();
		bean.taskId = task.getTaskId();
		bean.state = task.getState();
		bean.endTime = task.getEndTime();
		bean.startTime = task.getStartTime();
		bean.id = task.getId();
		
		return bean; 
	}
	
	public static ResGetTimeRefreshTaskRewardMessage resGetTimeRefreshTaskRewardMessage(Player player,int type,int taskId){
		ResDayTaskMessage info0 = resDayTaskMessageBuilder(player);
		ResGetTimeRefreshTaskRewardMessage info1 = new ResGetTimeRefreshTaskRewardMessage();
		info1.playerTimeRefreshTask = info0.playerTimeRefreshTask;
		info1.type = type;
		info1.taskId = taskId;
		return info1;
	}
	
	public static ResMedalTaskInfoMessage resMedalTaskInfoMessageBuilder(Player player){
		ResMedalTaskInfoMessage info = new ResMedalTaskInfoMessage();
		PlayerMedalTaskBean bean = new PlayerMedalTaskBean();
		MedalTaskInfo medalTaskInfo = player.roleInfo().getTaskInfo().getMedalTaskInfo();
		bean.useMedal = medalTaskInfo.getUseMedal();
		for(MedalTask medalTask : medalTaskInfo.getMedalTaskList()){
			MedalTaskBean medalTaskBean = medalTaskBeanBuilder(medalTask,player);
			bean.medalTaskList.add(medalTaskBean);
		}
		bean.finishMedal = medalTaskInfo.getFinishMedal();
		info.playerMedalTaskBean = bean;
		
		return info;
	}
	
	public static ResGetAchieveRewardMessage resGetAchieveRewardMessageBuilder(Player player,int taskId){
		ResGetAchieveRewardMessage info = new ResGetAchieveRewardMessage();
		MedalTaskInfo medalTaskInfo = player.roleInfo().getTaskInfo().getMedalTaskInfo();
		MedalTask medalTask = medalTaskInfo.getMedalTask(taskId);
		MedalTaskBean medalTaskBean = medalTaskBeanBuilder(medalTask,player);
		info.medalTaskBean = medalTaskBean;
		return info;
		
	}
	
	public static MedalTaskBean medalTaskBeanBuilder(MedalTask medalTask,Player player){
		MedalTaskBean medalTaskBean = new MedalTaskBean();
		medalTaskBean.taskId = medalTask.getTaskId();
		medalTaskBean.currentValue = medalTask.getCurrentValue();
		List<Integer> unlockList = AchievePirFactory.achievePirMap.get(medalTask.getTaskId());
		int currTaskId = 0;
		Map<Integer,BaseTask> baseTask = player.roleInfo().getTaskInfo().getBaseTask();
		for(Integer unlock : unlockList){
			if(!medalTask.getCurrentValue().contains(unlock)){
				currTaskId = unlock;
				break;
			}
		}
		if(currTaskId == 0){
			currTaskId = unlockList.get(unlockList.size() - 1);
			TaskPir tp = TaskPirFactory.get(currTaskId);
			String str = tp.getV2();
			long totalProgress = Long.valueOf(str);
			medalTaskBean.progress = totalProgress;
		}else{
			BaseTask task = baseTask.get(currTaskId);
			if(task != null){
				medalTaskBean.progress = task.getProgress();
			}
		}
		int rewardTimes = 0;
		for(Integer taskId : medalTask.getCurrentValue()){
			BaseTask task = baseTask.get(taskId);
			if(task == null || task.isGet()){
				rewardTimes++;
			}
		}
		medalTaskBean.rewardTimes = rewardTimes;
		
		return medalTaskBean;
	}
	
	public static BaseTaskBean baseTaskBeanBuilder(BaseTask task){
		BaseTaskBean bean = new BaseTaskBean();
		bean.taskId = task.getTaskId();
		bean.progress = task.getProgress();
		return bean;
	}
	
	public static ResPushBaseTaskMessage resPushBaseTaskMessageBuilder(BaseTask task){
		ResPushBaseTaskMessage info = new ResPushBaseTaskMessage();
		BaseTaskBean bean = baseTaskBeanBuilder(task);
		info.baseTaskBean = bean;
		return info;
	}
	
	public static ResPushActiveTaskMessage resPushActiveTaskMessageBuilder(ActiveItem activeItem,ActivePir activePir,int rewardActiveNum){
		ResPushActiveTaskMessage info = new ResPushActiveTaskMessage();
		ActiveTaskBean activeTaskBean = activeTaskBeanBuilder(activeItem,activePir);
		info.activeTaskBean = activeTaskBean;
		info.rewardActiveNum = rewardActiveNum;
		
		return info;
	}
	
	
	public static ActiveTaskBean activeTaskBeanBuilder(ActiveItem activeItem,ActivePir activePir){
		ActiveTaskBean activeTaskBean = new ActiveTaskBean();
		activeTaskBean.taskId = activeItem.getId();
		activeTaskBean.currentCount = activeItem.getFiniCount();
		activeTaskBean.maxCount = activePir.getV2();
		activeTaskBean.rewardActiveNum = activePir.getSingleRewards();
		activeTaskBean.maxActiveNum = activePir.getSingleRewards();
		
		return activeTaskBean;
	}
}
