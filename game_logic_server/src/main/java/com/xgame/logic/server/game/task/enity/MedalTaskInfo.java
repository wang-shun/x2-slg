package com.xgame.logic.server.game.task.enity;

import io.protostuff.Tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

import lombok.Getter;
import lombok.Setter;

/**
 * 玩家勋章信息
 * @author zehong.he
 *
 */
public class MedalTaskInfo implements Serializable, JBaseTransform{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5873327307231818145L;

	/**使用中的勋章*/
	@Tag(1)
	private int useMedal;
	
	/**玩家勋章任务进度*/
	@Tag(2)
	private List<MedalTask> medalTaskList = new ArrayList<MedalTask>();
	
	/**已完成的勋章id列表*/
	@Tag(3)
	private List<Integer> finishMedal = new ArrayList<Integer>();

	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("useMedal", useMedal);
		
		List<JBaseData> medalJsonTaskListList = new ArrayList<JBaseData>();
		for (int i = 0; i < medalTaskList.size(); i++) {
			MedalTask obj = medalTaskList.get(i);
			medalJsonTaskListList.add(obj.toJBaseData());
		}
		
		jbaseData.put("medalTaskList", medalJsonTaskListList);
		
		jbaseData.put("finishMedal", finishMedal);
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		this.useMedal = jBaseData.getInt("useMedal", 0);
		
		List<JBaseData> jBaseDatas = jBaseData.getSeqBaseData("medalTaskList");
		for (JBaseData jBaseData2 : jBaseDatas) {
			MedalTask medalTask = new MedalTask();
			medalTask.fromJBaseData(jBaseData2);
			this.medalTaskList.add(medalTask);
		}
		this.finishMedal = jBaseData.getSeqInt("finishMedal");
	}

	public MedalTask getMedalTask(int taskId){
		for(MedalTask task : medalTaskList){
			if(task.getTaskId() == taskId){
				return task;
			}
		}
		return null;
	}
	
	public int getUseMedal() {
		return useMedal;
	}

	public void setUseMedal(int useMedal) {
		this.useMedal = useMedal;
	}

	public List<MedalTask> getMedalTaskList() {
		return medalTaskList;
	}

	public void setMedalTaskList(List<MedalTask> medalTaskList) {
		this.medalTaskList = medalTaskList;
	}

	public List<Integer> getFinishMedal() {
		return finishMedal;
	}

	public void setFinishMedal(List<Integer> finishMedal) {
		this.finishMedal = finishMedal;
	}
	
	
}
