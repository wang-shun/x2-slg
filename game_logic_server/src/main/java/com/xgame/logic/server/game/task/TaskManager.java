package com.xgame.logic.server.game.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xgame.config.VIP.VIPPir;
import com.xgame.config.VIP.VIPPirFactory;
import com.xgame.config.achieve.AchievePirFactory;
import com.xgame.config.active.ActivePir;
import com.xgame.config.active.ActivePirFactory;
import com.xgame.config.activeRewards.ActiveRewardsPir;
import com.xgame.config.activeRewards.ActiveRewardsPirFactory;
import com.xgame.config.dailyTask.DailyTaskPir;
import com.xgame.config.dailyTask.DailyTaskPirFactory;
import com.xgame.config.fastPaid.FastPaidPir;
import com.xgame.config.fastPaid.FastPaidPirFactory;
import com.xgame.config.global.GlobalPir;
import com.xgame.config.global.GlobalPirFactory;
import com.xgame.config.item.ItemBox;
import com.xgame.config.task.TaskPir;
import com.xgame.config.task.TaskPirFactory;
import com.xgame.drop.DropService;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.utils.CurrencyUtil;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.scheduler.Scheduler;
import com.xgame.logic.server.core.utils.sequance.TaskSequance;
import com.xgame.logic.server.game.bag.BagRewardKit;
import com.xgame.logic.server.game.bag.ItemKit;
import com.xgame.logic.server.game.bag.constant.ItemIdConstant;
import com.xgame.logic.server.game.bag.entity.RewardContext;
import com.xgame.logic.server.game.constant.CurrencyEnum;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.task.constant.TaskConstant;
import com.xgame.logic.server.game.task.converter.TaskConverter;
import com.xgame.logic.server.game.task.enity.ActiveInfo;
import com.xgame.logic.server.game.task.enity.ActiveItem;
import com.xgame.logic.server.game.task.enity.BaseTask;
import com.xgame.logic.server.game.task.enity.MedalTask;
import com.xgame.logic.server.game.task.enity.MedalTaskInfo;
import com.xgame.logic.server.game.task.enity.RewardsStatus;
import com.xgame.logic.server.game.task.enity.TaskInfo;
import com.xgame.logic.server.game.task.enity.TimeRefreshTask;
import com.xgame.logic.server.game.task.enity.TimeTask;
import com.xgame.logic.server.game.task.enity.eventmodel.FinishTaskEventObject;
import com.xgame.logic.server.game.task.parser.basetask.BaseParser;
import com.xgame.utils.TimeUtils;


@Component
public class TaskManager{
	
	@Autowired
	TaskSequance taskSequance;
	
	@Autowired
	private Scheduler scheduler;

	/**
	 * 任务gm
	 * @param player
	 * @param type
	 * @param taskId
	 */
	public void gmFinishedTask(Player player,int type,int taskId){
		if(type == 1){//基地任务
			BaseTask baseTask = player.roleInfo().getTaskInfo().getBaseTask().get(taskId);
			baseTask.setComplete(true);
			TaskPir pir = TaskPirFactory.get(taskId);
			String str = pir.getV2();
			long totalProgress = Long.valueOf(str);
			baseTask.setProgress(totalProgress);
			InjectorUtil.getInjector().dbCacheService.update(player);
			queryTaskInfoMessage(player);
		}else if(type == 2){//活跃度任务
			ActiveItem activeItem = player.roleInfo().getTaskInfo().getActiveInfo().getActiveItems().get(taskId);
			activeItem.setComplete(true);
			ActivePir pir = ActivePirFactory.get(taskId);
			int num = pir.getV2();
			activeItem.setFiniCount(num);
			InjectorUtil.getInjector().dbCacheService.update(player);
			queryActiveInfoMessage(player);
		}else if(type == 3){//时间刷新任务
			List<TimeTask> list = new ArrayList<>();
			list.addAll(player.roleInfo().getTaskInfo().getTimeRefreshTask().getDayTask());
			list.addAll(player.roleInfo().getTaskInfo().getTimeRefreshTask().getGuildTask());
			for(TimeTask task : list){
				if(task.getTaskId() == taskId){
					task.setEndTime((int)(System.currentTimeMillis() / 1000));
					task.setState(TimeTask.STATE_COMPLETE);
					EventBus.getSelf().fireEvent(new FinishTaskEventObject(player,FinishTaskEventObject.TASK_TYPE_1,taskId));
				}
			}
			InjectorUtil.getInjector().dbCacheService.update(player);
			queryDayTask(player);
		}
	}
	
	/**
	 * 初始化玩家任务
	 * @return
	 */
	public TaskInfo initTask(Player player){
		TaskInfo taskInfo = new TaskInfo();
		//初始化基地任务
		initBaseTask(player,taskInfo);
		//初始化日常任务
		initTimeRefreshTask(taskInfo,1,getTaskNum(TaskConstant.DAILY_TASK_INIT_NUM,1,player.roleInfo().getVipInfo().getVipLevel()));
		//初始化军团任务
		initTimeRefreshTask(taskInfo,2,getTaskNum(TaskConstant.LAGUE_TASK_INIT_NUM,2,player.roleInfo().getVipInfo().getVipLevel()));
		//活跃度任务
		initActiveTask(taskInfo);
		//初始化勋章任务
		initAchieve(taskInfo);
		player.roleInfo().setTaskInfo(taskInfo);
		return taskInfo;
	}
	
	/**
	 * 获取vip加成后的任务次数
	 */
	public int getTaskNum(int originalNum,int type,int level){
		VIPPir vippir = VIPPirFactory.get(level);
		if(null == vippir){
			return originalNum;
		}
		if(type == 1){
			return originalNum + Integer.valueOf(vippir.getDailyMission());
		}else if(type == 2){
			return originalNum + Integer.valueOf(vippir.getArmyMission());
		}
		return originalNum;
	}
	
	/**
	 * 初始化时间刷新任务
	 * @param taskInfo
	 * @param type
	 * @param initNum
	 */
	private void initTimeRefreshTask(TaskInfo taskInfo,int type,int initNum){
		refreshTimeRefreshTask(taskInfo,type,initNum,false);
	}

	/**
	 * 刷新时间任务
	 * @param taskInfo
	 * @param type
	 * @param initNum
	 */
	private void refreshTimeRefreshTask(TaskInfo taskInfo,int type,int initNum,boolean isManual){
		List<ItemBox> list = DropService.getDrop(DailyTaskPirFactory.boxs, initNum);
		List<TimeTask> timeRefreshTask = new ArrayList<>();  
		for(ItemBox box : list){
			TimeTask task = new TimeTask();
			task.setTaskId(box.gettId());
			task.setId(taskSequance.genTaskId());
			timeRefreshTask.add(task);
		}
		if(!isManual){
			taskInfo.getTimeRefreshTask().setRefreshTime(System.currentTimeMillis());
		}
		List<TimeTask> taskList = null;
		if(type == 1){
			taskList = taskInfo.getTimeRefreshTask().getDayTask();
		}else{
			taskList = taskInfo.getTimeRefreshTask().getGuildTask();
		}
		for(int i = taskList.size() - 1;i >=0;i--){
			TimeTask tt = taskList.get(i);
			if(tt.getState() == 0){
				taskList.remove(i);
			}
		}
		taskList.addAll(timeRefreshTask);
	}
	
	
	public boolean updateTimeRefreshTask(Player player,int dailyNum,int lagueNum,boolean isManual){
		TimeRefreshTask timeRefreshTask = player.roleInfo().getTaskInfo().getTimeRefreshTask();
		GlobalPir config = GlobalPirFactory.get(250011);
		long updateTime = (Integer.parseInt(config.getValue())) * 1000;//读表
		boolean isUpdate = false;
		if(System.currentTimeMillis() - timeRefreshTask.getRefreshTime() >= updateTime){
			refreshTimeRefreshTask(player.roleInfo().getTaskInfo(),1,dailyNum,isManual);
			refreshTimeRefreshTask(player.roleInfo().getTaskInfo(),2,lagueNum,isManual);
			InjectorUtil.getInjector().dbCacheService.update(player);
			isUpdate = true;
		}
		
		return isUpdate;
	}
	
	/**
	 * 刷新活跃度任务
	 * @param taskInfo
	 * @param level
	 */
	private void refreshActiveTask(TaskInfo taskInfo,int level){
		Map<Integer, ActiveItem> activeItems = new HashMap<>();
		for(ActivePir ap : ActivePirFactory.getInstance().getFactory().values()){
			String limit = ap.getLimit();
			String[] arr = limit.split("_");
			int minLevel = Integer.parseInt(arr[0]);
			int maxLevel = Integer.parseInt(arr[1]);
			if(level >= minLevel && level <= maxLevel){
				ActiveItem activeItem = new ActiveItem();
				activeItem.setId(ap.getId());
				activeItems.put(activeItem.getId(), activeItem);
			}
		}
		taskInfo.getActiveInfo().setTime(System.currentTimeMillis());
		taskInfo.getActiveInfo().setActiveItems(activeItems);
	}
	
	/**
	 * 初始化活跃度任务
	 * @param taskInfo
	 */
	private void initActiveTask(TaskInfo taskInfo){
		refreshActiveTask(taskInfo,1);
	}
	
	/**
	 * 初始化勋章任务
	 * @param taskInfo
	 */
	private void initAchieve(TaskInfo taskInfo){
		for(Integer taskId : AchievePirFactory.achievePirMap.keySet()){
			MedalTask task = new MedalTask();
			task.setTaskId(taskId);
			taskInfo.getMedalTaskInfo().getMedalTaskList().add(task);
		}
	}
	
	/**
	 * 初始化基地任务
	 * @param taskInfo
	 */
	private void initBaseTask(Player player,TaskInfo taskInfo){
		List<TaskPir> baseTaskConfigs = TaskPirFactory.getInitTask();
		Map<Integer,BaseTask> baseTask = new HashMap<>();
		for(TaskPir tp : baseTaskConfigs){
			BaseTask baseTask0 = createBaseTask(player,tp);
			baseTask.put(tp.getId(),baseTask0);
		}
		taskInfo.setBaseTask(baseTask);
	}
	
	/**
	 * 更新活跃度任务
	 * @param player
	 */
	public void updateActiveItems(Player player){
		ActiveInfo activeInfo = player.roleInfo().getTaskInfo().getActiveInfo();
		boolean today = TimeUtils.isToday(activeInfo.getTime());
		if(!today){
			refreshActiveTask(player.roleInfo().getTaskInfo(),player.getLevel());
			InjectorUtil.getInjector().dbCacheService.update(player);
		}
	}
	
	/**
	 * 获取基地任务信息
	 * @param player
	 * @return
	 */
	public void queryTaskInfoMessage(Player player){
		player.send(TaskConverter.resQueryTaskInfoMessageBuilder(player));
	}
	
	/**
	 * 领取基地任务奖励
	 * @param player
	 * @param taskId
	 */
	public void getBaseTaskReward(Player player,int taskId){
		BaseTask baseTask = player.roleInfo().getTaskInfo().getBaseTask().get(taskId);
		if(baseTask == null){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1008_TASK.CODE1); 
			return;
		}
		if(!baseTask.isComplete()){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1008_TASK.CODE2); 
			return;
		}
		if(baseTask.isGet()){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1008_TASK.CODE3); 
			return;
		}
		TaskPir taskPir = TaskPirFactory.get(taskId);
		RewardContext rewardContext = BagRewardKit.checkReward(player, taskPir.getRewards());
		if(!rewardContext.isOk()){
			Language.ERRORCODE.send(player, rewardContext.getErrorCode()); 
			return;
		}
		BagRewardKit.sendReward(player,rewardContext,GameLogSource.BASE_TASK);
		player.roleInfo().getTaskInfo().getBaseTask().remove(taskId);
		//初始化下一个任务
		List<TaskPir> newList = TaskPirFactory.getByRequireTask(taskId);
		for(TaskPir taskPir0 : newList){
			BaseTask baseTask0 = createBaseTask(player,taskPir0);
			player.roleInfo().getTaskInfo().getBaseTask().put(baseTask0.getTaskId(), baseTask0);
		}
		InjectorUtil.getInjector().dbCacheService.update(player);
		player.send(TaskConverter.resRewardTaskInfoMessageBuilder(player,taskId));
	}
	
	/**
	 * 构建一个基任务
	 * @param player
	 * @param taskPir
	 * @return
	 */
	private BaseTask createBaseTask(Player player,TaskPir taskPir){
		BaseParser parser = BaseParser.baseParserMap.get(taskPir.getType());
		int tag = TaskUtil.isAchieveTask(taskPir.getId()) ? 1 : 0;
		if(parser != null){
			long progress = parser.initProgress(taskPir,player);
			String str = taskPir.getV2();
			long totalProgress = Long.valueOf(str);
			if(progress > totalProgress){
				progress = totalProgress;
			}
			boolean complete = progress >= totalProgress;
			return BaseTask.valueOf(taskPir.getId(), progress,complete,taskPir.getOrder(),tag);
		}
		return BaseTask.valueOf(taskPir.getId(), 0,false,taskPir.getOrder(),tag);
	}
	
	/**
	 * 获取活跃度信息
	 * @param player
	 */
	public void queryActiveInfoMessage(Player player){
		updateActiveItems(player);
		player.send(TaskConverter.resQueryActiveInfoMessageBuilder(player));
	}
	
	/**
	 * 领取活跃度宝箱
	 * @param player
	 * @param tag
	 */
	public void getRewardActiveBox(Player player,int tag){
		updateActiveItems(player);
		ActiveInfo activeInfo = player.roleInfo().getTaskInfo().getActiveInfo();
		RewardsStatus rewardsStatus = activeInfo.getRewards().get(tag);
		if(rewardsStatus == null){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1008_TASK.CODE2);
			return;
		}
		if(rewardsStatus.getStatus() == 1){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1008_TASK.CODE4);
			return;
		}
		ActiveRewardsPir activeRewardsPir = ActiveRewardsPirFactory.get(tag);
		int level = player.getLevel();
		String reward;
		if(level >= 1 && level <= 5){
			reward = activeRewardsPir.getRewards1();
		}else if(level >= 6 && level <= 10){
			reward = activeRewardsPir.getRewards2();
		}else if(level >= 11 && level <= 15){
			reward = activeRewardsPir.getRewards3();
		}else if(level >= 16 && level <= 20){
			reward = activeRewardsPir.getRewards4();
		}else if(level >= 21 && level <= 25){
			reward = activeRewardsPir.getRewards5();
		}else if(level >= 26 && level <= 30){
			reward = activeRewardsPir.getRewards6();
		}else{
			reward = "";
			Language.ERRORCODE.send(player,ErrorCodeEnum.E1008_TASK.CODE1); 
			return;
		}
		RewardContext rewardContext = BagRewardKit.checkReward(player,reward);
		if(!rewardContext.isOk()){
			Language.ERRORCODE.send(player, rewardContext.getErrorCode()); 
			return;
		}
		BagRewardKit.sendReward(player,rewardContext,GameLogSource.ACTIVE_TASK);
		rewardsStatus.setStatus(1);
		InjectorUtil.getInjector().dbCacheService.update(player);
		player.send(TaskConverter.resRewardActiveBoxMessageBuilder(player,tag));
	}
	
	/**
	 * 获取日常任务
	 * @param player
	 */
	public void queryDayTask(Player player){
		updateTimeRefreshTask(player,getTaskNum(TaskConstant.DAILY_TASK_INIT_NUM,1,player.roleInfo().getVipInfo().getVipLevel()),getTaskNum(TaskConstant.LAGUE_TASK_INIT_NUM,2,player.roleInfo().getVipInfo().getVipLevel()),false);
		//刷新每个任务时间
		updateTimeRefreshTaskState(player);
		player.send(TaskConverter.resDayTaskMessageBuilder(player));
	}
	
	/**
	 * 领取时间刷新任务奖励
	 * @param player
	 * @param index 
	 * @param type 1-日常任务；2-联盟任务
	 */
	public void getTimeRefreshTaskReward(Player player,int type,long id){
		//刷新每个任务时间
		updateTimeRefreshTaskState(player);
		boolean isUpdate = updateTimeRefreshTask(player,getTaskNum(TaskConstant.DAILY_TASK_INIT_NUM,1,player.roleInfo().getVipInfo().getVipLevel()),getTaskNum(TaskConstant.LAGUE_TASK_INIT_NUM,2,player.roleInfo().getVipInfo().getVipLevel()),false);
		if(isUpdate){
			player.send(TaskConverter.resDayTaskMessageBuilder(player));
			return;
		}
		List<TimeTask> tasks;
		if(type == 1){
			tasks = player.roleInfo().getTaskInfo().getTimeRefreshTask().getDayTask();
		}else{
			tasks = player.roleInfo().getTaskInfo().getTimeRefreshTask().getGuildTask();
		}
		TimeTask task = null;
		for(TimeTask t : tasks){
			if(t.getId() == id){
				task = t;
				break;
			}
		}
		if(task == null){
			Language.ERRORCODE.send(player,ErrorCodeEnum.E1008_TASK.CODE1); 
			return;
		}
		if(task.getState() != 2){
			Language.ERRORCODE.send(player,ErrorCodeEnum.E1008_TASK.CODE2); 
			return;
		}
		DailyTaskPir pir = DailyTaskPirFactory.get(task.getTaskId());
		String reward;
		int level = player.getLevel();
		if(level >= 1 && level <= 5){
			reward = pir.getRewards1();
		}else if(level >= 6 && level <= 10){
			reward = pir.getRewards2();
		}else if(level >= 11 && level <= 15){
			reward = pir.getRewards3();
		}else if(level >= 16 && level <= 20){
			reward = pir.getRewards4();
		}else if(level >= 21 && level <= 25){
			reward = pir.getRewards5();
		}else if(level >= 26 && level <= 30){
			reward = pir.getRewards6();
		}else{
			reward = "";
			Language.ERRORCODE.send(player,ErrorCodeEnum.E1008_TASK.CODE1); 
			return;
		}
		RewardContext rewardContext = BagRewardKit.checkReward(player,reward); 
		if(!rewardContext.isOk()){
			Language.ERRORCODE.send(player, rewardContext.getErrorCode()); 
			return;
		}
		BagRewardKit.sendReward(player,rewardContext,GameLogSource.REFRESH_TASK);
		tasks.remove(task);
		InjectorUtil.getInjector().dbCacheService.update(player);
		player.send(TaskConverter.resGetTimeRefreshTaskRewardMessage(player,type,task.getTaskId()));
	}
	
	/**
	 * 重置时间刷新任务（日常任务，军团任务）
	 * @param player
	 * @param type 1-日常任务；2-联盟任务
	 */
	public void resetTimeRefreshTask(Player player,int type){
//		GlobalPir globalPir = GlobalPirFactory.get(TaskConstant.REFRESH_TIME_TASK_ITEM_GLOBAL_ID);
//		String str = globalPir.getValue();
//		String[] arr = str.split("_");
//		int itemId = Integer.parseInt(arr[0]);
//		int num = Integer.parseInt(arr[1]);
//		List<Item> itemList = player.roleInfo().getPlayerBag().getPlayerItem(itemId);
//		int leftNum = 0;
//		for(Item item : itemList){
//			leftNum += item.getNum();
//		}
//		int needMoney = 0;
//		int costItemNum = num;
//		if(leftNum < num){
//			FastPaidPir fastPaid = FastPaidPirFactory.get(ItemIdConstant.DAILY_TASK_RESET);
//			needMoney = fastPaid.getPrice() * (num - leftNum);
//			costItemNum = leftNum;
//		}
//		if(needMoney > 0){
//			//钱足扣钱 
//			if (CurrencyUtil.decrement(player, needMoney , CurrencyEnum.DIAMOND, GameLogSource.RESET_TIME_REFRESH_TASK)) {
//				CurrencyUtil.send(player);
//			} else {
//				Language.ERRORCODE.send(player,ErrorCodeEnum.E1008_TASK.CODE6); 
//				return;
//			}
//		}
//		if(costItemNum > 0){
//			ItemKit.removeItemByTid(player, itemId, costItemNum, GameLogSource.RESET_TIME_REFRESH_TASK);
//		}
		int itemId = 0;
		if(type == 1){
			itemId = ItemIdConstant.DAILY_TASK_RESET;
		}else if(type == 2){
			itemId = ItemIdConstant.ALLIANCE_TASK_RESET;
		}
		if(itemId < 1){
			Language.ERRORCODE.send(player,ErrorCodeEnum.E001_LOGIN.CODE15);
			return;
		}
		FastPaidPir paidPir = FastPaidPirFactory.get(itemId);
		if (ItemKit.checkRemoveItemByTid(player, itemId, 1)) {
			ItemKit.removeItemByTid(player, itemId, 1, GameLogSource.CHANAGE_NAME);
		} else if (null == paidPir){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE6.get(),FastPaidPir.class.getSimpleName(),itemId);
			return;
		} else if (CurrencyUtil.verify(player, paidPir.getPrice() , CurrencyEnum.DIAMOND)) {
			CurrencyUtil.decrement(player, paidPir.getPrice(), CurrencyEnum.DIAMOND, GameLogSource.CHANAGE_NAME);
			CurrencyUtil.send(player);
		} else {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE16.get());
			return;
		}
		
		TimeRefreshTask timeRefreshTask = player.roleInfo().getTaskInfo().getTimeRefreshTask();
		List<TimeTask> myTask;
		int refreshNum;
		if(type == 1){
			myTask = timeRefreshTask.getDayTask();
			refreshNum = getTaskNum(TaskConstant.DAILY_TASK_INIT_NUM,1,player.roleInfo().getVipInfo().getVipLevel());
		}else{
			myTask = timeRefreshTask.getGuildTask();
			refreshNum = getTaskNum(TaskConstant.LAGUE_TASK_INIT_NUM,2,player.roleInfo().getVipInfo().getVipLevel());
		}
		for(int i = myTask.size() - 1;i >= 0;i--){
			TimeTask timeTask = myTask.get(i);
			if(timeTask.getState() == 0){
				myTask.remove(i);
			}
		}
		List<ItemBox> list = DropService.getDrop(DailyTaskPirFactory.boxs, refreshNum);
		for(ItemBox box : list){
			TimeTask task = new TimeTask();
			task.setTaskId(box.gettId());
			task.setId(taskSequance.genTaskId());
			myTask.add(task);
		}
		InjectorUtil.getInjector().dbCacheService.update(player);
		player.send(TaskConverter.resDayTaskMessageBuilder(player));
	}
	
	/**
	 * 开始一个时间任务(同一时间只能开启一个任务)
	 * @param player
	 * @param type
	 * @param index
	 */
	public void openTimeRefreshTask(Player player,int type,long id){
		boolean isUpdate = updateTimeRefreshTask(player,getTaskNum(TaskConstant.DAILY_TASK_INIT_NUM,1,player.roleInfo().getVipInfo().getVipLevel()),getTaskNum(TaskConstant.LAGUE_TASK_INIT_NUM,2,player.roleInfo().getVipInfo().getVipLevel()),false);
		if(isUpdate){
			player.send(TaskConverter.resDayTaskMessageBuilder(player));
			return;
		}
		//刷新每个任务时间
		updateTimeRefreshTaskState(player);
		List<TimeTask> tasks;
		if(type == 1){
			tasks = player.roleInfo().getTaskInfo().getTimeRefreshTask().getDayTask();
		}else{
			tasks = player.roleInfo().getTaskInfo().getTimeRefreshTask().getGuildTask();
		}
		TimeTask task = null;
		for(TimeTask t : tasks){
			if(t.getState() == TimeTask.STATE_RUNNING){
				Language.ERRORCODE.send(player,ErrorCodeEnum.E1008_TASK.CODE10); 
				return;
			}
			if(t.getId() == id){
				if(task == null){
					task = t;
					if(task.getState() != TimeTask.STATE_NO_OPEN){
						Language.ERRORCODE.send(player,ErrorCodeEnum.E1008_TASK.CODE5); 
						return;
					}
				}
			}
		}
		if(task == null){
			Language.ERRORCODE.send(player,ErrorCodeEnum.E1008_TASK.CODE1); 
			return;
		}
		DailyTaskPir pir = DailyTaskPirFactory.get(task.getTaskId());
		int now = (int)(System.currentTimeMillis() / 1000);
		int endTime = now + pir.getTime();
		task.setEndTime(endTime);
		task.setStartTime(now);
		task.setState(TimeTask.STATE_RUNNING);
		InjectorUtil.getInjector().dbCacheService.update(player);
		player.send(TaskConverter.resDayTaskMessageBuilder(player));
	}
	
	private void updateTimeRefreshTaskState(Player player){
		List<TimeTask> dayTask = player.roleInfo().getTaskInfo().getTimeRefreshTask().getDayTask();
		boolean flag = false;
		int now = (int)(System.currentTimeMillis() / 1000);
		for(TimeTask task : dayTask){
			if(task.getState() == TimeTask.STATE_RUNNING && now  > task.getEndTime()){
				task.setState(TimeTask.STATE_COMPLETE);
				flag = true;
				EventBus.getSelf().fireEvent(new FinishTaskEventObject(player,FinishTaskEventObject.TASK_TYPE_1,task.getTaskId()));
			}
		}
		List<TimeTask> guildTask = player.roleInfo().getTaskInfo().getTimeRefreshTask().getGuildTask();
		for(TimeTask task : guildTask){
			if(task.getState() == TimeTask.STATE_RUNNING && now > task.getEndTime()){
				task.setState(TimeTask.STATE_COMPLETE);
				flag = true;
				EventBus.getSelf().fireEvent(new FinishTaskEventObject(player,FinishTaskEventObject.TASK_TYPE_2,task.getTaskId()));
			}
		}
		if(flag){
			InjectorUtil.getInjector().dbCacheService.update(player);
		}
	}
	
	/**
	 * 获取勋章信息
	 * @param player
	 */
	public void queryMedalTaskInfo(Player player){
		player.send(TaskConverter.resMedalTaskInfoMessageBuilder(player));
	}
	
	/**
	 * 使用勋章
	 * @param player
	 * @param taskId
	 */
	public void useMedal(Player player,int taskId){
		MedalTaskInfo medalTaskInfo = player.roleInfo().getTaskInfo().getMedalTaskInfo();
		if(!medalTaskInfo.getFinishMedal().contains(taskId)){
			Language.ERRORCODE.send(player,ErrorCodeEnum.E1008_TASK.CODE7); 
			return;
		}
		medalTaskInfo.setUseMedal(taskId);
		InjectorUtil.getInjector().dbCacheService.update(player);
		player.send(TaskConverter.resMedalTaskInfoMessageBuilder(player));
	}
	
	/**
	 * 任务加速
	 * @param player
	 * @param taskUid
	 * @return
	 */
	public boolean speeTask(Player player,long taskUid,int decrTime){
		TimeTask task0 = null;
		for(TimeTask task : player.roleInfo().getTaskInfo().getTimeRefreshTask().getDayTask()){
			if(task.getId() == taskUid){
				task0 = task;
				break;
			}
		}
		if(task0 == null){
			for(TimeTask task : player.roleInfo().getTaskInfo().getTimeRefreshTask().getGuildTask()){
				if(task.getId() == taskUid){
					task0 = task;
					break;
				}
			}
		}
		if(task0 == null){
			Language.ERRORCODE.send(player,ErrorCodeEnum.E1008_TASK.CODE1); 
			return false;
		}
		if(task0.getState() == 0){
			Language.ERRORCODE.send(player,ErrorCodeEnum.E1008_TASK.CODE8); 
			return false;
		}
		if(task0.getState() == 2){
			Language.ERRORCODE.send(player,ErrorCodeEnum.E1008_TASK.CODE9); 
			return false;
		}
		int endTime = task0.getEndTime();
		endTime -= decrTime;
		int now = (int)(System.currentTimeMillis() / 1000);
		if(endTime < now){
			endTime = now;
			task0.setState(2);
		}
		task0.setEndTime(endTime);
		InjectorUtil.getInjector().dbCacheService.update(player);
		player.send(TaskConverter.resDayTaskMessageBuilder(player));
		
		return true;
	}
	
	/**
	 *	领取勋章任务奖励
	 * @param player
	 * @param achieveTaskId
	 */
	public void getAchieveReward(Player player,int achieveTaskId){
		MedalTask medalTask = player.roleInfo().getTaskInfo().getMedalTaskInfo().getMedalTask(achieveTaskId);
		if(medalTask == null){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1008_TASK.CODE1); 
			return;
		}
		BaseTask baseTask = null;
		for(Integer id : medalTask.getCurrentValue()){
			BaseTask task = player.roleInfo().getTaskInfo().getBaseTask().get(id);
			if(task != null && !task.isGet()){
				baseTask = task;
				break;
			}
		}
		if(baseTask == null || baseTask.isGet()){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1008_TASK.CODE3); 
			return;
		}
		TaskPir taskPir = TaskPirFactory.get(baseTask.getTaskId());
		RewardContext rewardContext = BagRewardKit.checkReward(player, taskPir.getRewards());
		if(!rewardContext.isOk()){
			Language.ERRORCODE.send(player, rewardContext.getErrorCode()); 
			return;
		}
		BagRewardKit.sendReward(player,rewardContext,GameLogSource.ACHIEVE_TASK);
		player.roleInfo().getTaskInfo().getBaseTask().remove(baseTask.getTaskId());
		//初始化下一个任务
		List<TaskPir> newList = TaskPirFactory.getByRequireTask(baseTask.getTaskId());
		for(TaskPir taskPir0 : newList){
			BaseTask baseTask0 = createBaseTask(player,taskPir0);
			player.roleInfo().getTaskInfo().getBaseTask().put(baseTask0.getTaskId(), baseTask0);
		}
		InjectorUtil.getInjector().dbCacheService.update(player);
		player.send(TaskConverter.resGetAchieveRewardMessageBuilder(player,achieveTaskId));
	}
}

