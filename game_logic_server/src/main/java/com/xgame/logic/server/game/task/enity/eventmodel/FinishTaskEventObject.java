package com.xgame.logic.server.game.task.enity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 完成任务
 * @author zehong.he
 *
 */
public class FinishTaskEventObject extends GameLogEventObject {
	
	public static final int TASK_TYPE_1 = 1;//1日常
	public static final int TASK_TYPE_2 = 2;//2军团
	public static final int TASK_TYPE_3 = 3;//3基地
	public static final int TASK_TYPE_4 = 4;//4-活跃度
	

	/**
	 * 1日常；2军团；3基地；4-活跃度
	 */
	private int taskType;
	
	private int taskId;
	
	public FinishTaskEventObject(Player player,int taskType,int taskId) {
		super(player, EventTypeConst.FINISH_TASK);
		this.taskType = taskType;
		this.taskId = taskId;
	}

	/**
	 * @return the taskType
	 */
	public int getTaskType() {
		return taskType;
	}

	/**
	 * @param taskType the taskType to set
	 */
	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}

	/**
	 * @return the taskId
	 */
	public int getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId the taskId to set
	 */
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
}
