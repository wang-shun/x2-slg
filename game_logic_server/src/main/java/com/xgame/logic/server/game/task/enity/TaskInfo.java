package com.xgame.logic.server.game.task.enity;

import io.protostuff.Tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

/**
 * 玩家任务数据
 * @author zehong.he
 *
 */
public class TaskInfo implements Serializable, JBaseTransform{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2714284578337171722L;

	@Tag(1)
	private Map<Integer,BaseTask> baseTask = new HashMap<>();			//基地任务
	
	@Tag(2)
	private TimeRefreshTask timeRefreshTask = new TimeRefreshTask();    //时间刷新任务(日常、军团任务)

	@Tag(3)
	private ActiveInfo activeInfo = new ActiveInfo();					//活跃度
	
	@Tag(4)
	private MedalTaskInfo medalTaskInfo = new MedalTaskInfo();          //勋章
	
	@Tag(5)
	private TaskRecord taskRecord = new TaskRecord();					//任务特殊数据记录
	
//	@Tag(5)
//	private Map<Integer,Map<Long,Integer>> inheritProgressMap = new HashMap<>();  //保存任务继承的进度 key1:任务类型PlayerTaskTypeEnum，key2:任务类型BaseTaskTypeEnum

	public Map<Integer, BaseTask> getBaseTask() {
		return baseTask;
	}

	public void setBaseTask(Map<Integer, BaseTask> baseTask) {
		this.baseTask = baseTask;
	}

	public TimeRefreshTask getTimeRefreshTask() {
		return timeRefreshTask;
	}

	public void setTimeRefreshTask(TimeRefreshTask timeRefreshTask) {
		this.timeRefreshTask = timeRefreshTask;
	}

	public ActiveInfo getActiveInfo() {
		return activeInfo;
	}

	public void setActiveInfo(ActiveInfo activeInfo) {
		this.activeInfo = activeInfo;
	}

	public MedalTaskInfo getMedalTaskInfo() {
		return medalTaskInfo;
	}

	public void setMedalTaskInfo(MedalTaskInfo medalTaskInfo) {
		this.medalTaskInfo = medalTaskInfo;
	}
	
	
	
//	/**
//	 * 获取任务继承值
//	 * @param taskTypeEnum
//	 * @return
//	 */
//	public int getInheritProgress(PlayerTaskTypeEnum playerTaskTypeEnum,BaseTaskTypeEnum taskTypeEnum){
//		int key1 = playerTaskTypeEnum.ordinal();
//		long key2 = Long.parseLong(String.valueOf(taskTypeEnum.getCode()));
//		if(!inheritProgressMap.containsKey(key1)){
//			return 0;
//		}
//		Integer value = inheritProgressMap.get(key1).get(key2);
//		if(value == null){
//			return 0;
//		}
//		return value;
//	}
//	
//	/**
//	 * 添加任务继承值
//	 * @param taskTypeEnum
//	 * @param addValue
//	 */
//	public void addInheritProgress(PlayerTaskTypeEnum playerTaskTypeEnum,BaseTaskTypeEnum taskTypeEnum,int addValue){
//		int key1 = playerTaskTypeEnum.ordinal();
//		long key2 = Long.parseLong(String.valueOf(taskTypeEnum.getCode()));
//		if(!inheritProgressMap.containsKey(key1)){
//			inheritProgressMap.put(key1, new HashMap<>());
//		}
//		if(!inheritProgressMap.get(key1).containsKey(key2)){
//			inheritProgressMap.get(key1).put(key2, addValue);
//		}else{
//			int value = inheritProgressMap.get(key1).get(key2) + addValue;
//			inheritProgressMap.get(key1).put(key2, value);
//		}
//	}
	
	public TaskRecord getTaskRecord() {
		return taskRecord;
	}

	public void setTaskRecord(TaskRecord taskRecord) {
		this.taskRecord = taskRecord;
	}

	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		List<JBaseData> baseTaskJBaseList = new ArrayList<JBaseData>();
		for (Map.Entry entry : baseTask.entrySet()) {
			baseTaskJBaseList.add(((JBaseTransform) entry.getValue()).toJBaseData());
		}
		
		jbaseData.put("baseTask",baseTaskJBaseList);
		jbaseData.put("timeRefreshTask", ((JBaseTransform) timeRefreshTask).toJBaseData());
		jbaseData.put("activeInfo", ((JBaseTransform) activeInfo).toJBaseData());
		jbaseData.put("medalTaskInfo", ((JBaseTransform) medalTaskInfo).toJBaseData());
		jbaseData.put("taskRecord", ((JBaseTransform) taskRecord).toJBaseData());
//		jbaseData.put("inheritProgressMap", SplitUtil.map2String1(inheritProgressMap));
		
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		List<JBaseData> baseTaskJBaseList = jBaseData.getSeqBaseData("baseTask");
		
		for(JBaseData jBaseData2 : baseTaskJBaseList) {
			BaseTask baseTask = new BaseTask();
			baseTask.fromJBaseData(jBaseData2);
			this.baseTask.put(baseTask.getTaskId(), baseTask);
		}
		
		JBaseData jBaseDataTimeRefreshTask = jBaseData.getBaseData("timeRefreshTask");
		TimeRefreshTask timeRefreshTask = new TimeRefreshTask();
		timeRefreshTask.fromJBaseData(jBaseDataTimeRefreshTask);
		this.timeRefreshTask = timeRefreshTask;
		
		ActiveInfo activeInfo = new ActiveInfo();
		JBaseData jBaseDataActiveInfo = jBaseData.getBaseData("activeInfo");
		activeInfo.fromJBaseData(jBaseDataActiveInfo);
		this.activeInfo = activeInfo;
				
		MedalTaskInfo medalTaskInfo = new MedalTaskInfo();
		JBaseData jBaseDatamedalTaskInfo = jBaseData.getBaseData("medalTaskInfo");
		medalTaskInfo.fromJBaseData(jBaseDatamedalTaskInfo);
		
		TaskRecord taskRecord = new TaskRecord();
		JBaseData jBaseDatamedalTaskRecord = jBaseData.getBaseData("taskRecord");
		taskRecord.fromJBaseData(jBaseDatamedalTaskRecord);
//		String inheritProgressStr = jBaseData.getString("inheritProgressMap", "");
//		this.inheritProgressMap = SplitUtil.string2Map1(inheritProgressStr, this.inheritProgressMap);
		
		this.medalTaskInfo = medalTaskInfo;
	}
	
}
