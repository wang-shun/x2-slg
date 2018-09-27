package com.xgame.logic.server.game.task.enity;

import io.protostuff.Tag;

import java.io.Serializable;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

/**
 * 基地任务（包含勋章任务，为勋章任务时不在基地任务界面上显示）
 * @author zehong.he
 *
 */
public class BaseTask implements Serializable, JBaseTransform{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1904886903199493781L;

	@Tag(1)
	private int taskId;		//模板ID
	
	@Tag(2)
	private long progress;	//完成进度
	
	@Tag(3)
	private boolean isGet; //是否已领取奖励
	
	@Tag(4)
	private boolean complete;//是否完成
	
	@Tag(5)
	private long flag;		//辅助标识
	
	@Tag(6)
	private int order;		//推荐顺序

	@Tag(7)
	private int tag;		//0-基地任务；1-勋章任务
	

	public static BaseTask valueOf(int taskId,long progress,boolean complete,int order,int tag) {
		BaseTask baseTask = new BaseTask();
		baseTask.setTaskId(taskId);
		baseTask.setProgress(progress);
		baseTask.setComplete(complete);
		baseTask.setOrder(order);
		baseTask.setTag(tag);
		return baseTask;
	}
	
	public void addProgress(long addProgress){
		progress += addProgress;
	}

	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("taskId", taskId);
		jbaseData.put("progress", progress);
		jbaseData.put("isGet", isGet);
		jbaseData.put("complete", complete);
		jbaseData.put("flag", flag);
		jbaseData.put("order", order);
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		this.taskId = jBaseData.getInt("taskId", 0);
		this.progress = jBaseData.getLong("progress", 0);
		this.isGet = jBaseData.getBoolean("isGet", false);
		this.complete = jBaseData.getBoolean("complete", false);
		this.flag = jBaseData.getLong("flag", 0);
		this.order = jBaseData.getInt("order", 0);
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public long getProgress() {
		return progress;
	}

	public void setProgress(long progress) {
		this.progress = progress;
	}

	public boolean isGet() {
		return isGet;
	}

	public void setGet(boolean isGet) {
		this.isGet = isGet;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public long getFlag() {
		return flag;
	}

	public void setFlag(long flag) {
		this.flag = flag;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}
}
