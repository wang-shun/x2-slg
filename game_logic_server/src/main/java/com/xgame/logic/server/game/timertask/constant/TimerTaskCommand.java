package com.xgame.logic.server.game.timertask.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.xgame.logic.server.game.timertask.entity.job.CreatBuildTask;


/**
 *任务定时器指令集
 *
 *指令集  是指很多有相互联系的指令在一起 为一个指令集合,
 *
 *每个枚举都是一个固有指令集   指令集与指令集之间  没有联系   不会相互影响
 *
 *每个指令集下的指令是互斥的,每次只能执行一条指令   每条指令会执行指定次数
 *
 *2016-9-07  11:53:24
 *@author ye.yuan
 *
 */
public enum TimerTaskCommand {
	
	/**
	 * 创建建筑物 定时回调  0  建筑升级定时回调 1
	 */
	BUILD(1, true, Lists.newArrayList(CreatBuildTask.CREATE_BUILD_CMD, CreatBuildTask.LEVEL_UP_BUILD_CMD)),
	/**
	 * 删除建筑物
	 */
	REMOVE_BUILD(2,true, null),
			
	/**
	 * 科技升级
	 */
	LEVEL_TECH(3, true, null),
	
	/**
	 * 士兵生产
	 */
	SOLDIER_TANK_OUTPUT(4, true, null),
	
	/**
	 * 士兵生产
	 */
	SOLDIER_CAR_OUTPUT(5, true, null),
	
	/**
	 * 士兵生产
	 */
	SOLDIER_PLANT_OUTPUT(6, true, null),
	
	/**
	 * 修理厂修理
	 */
	MOD_SOLODIER(7, true, null),
	
	/**
	 * 生产材料
	 */
	PRODUCE_FRAGMENT(10, true, null),
	
	/**
	 * 生产装备
	 */
	PRODUCE_EQUIPMENT(11, true, null),
	
	/**
	 * 改造装甲
	 */
	REFORM_QUEUE(12, true, null),
	
	/**
	 * 行军队列
	 */
	RUN_SOLDIER(100, true, null),
	
	/**
	 * 采集
	 */
	COLLECT(101, false, null),
	
	/**
	 * 占领
	 */
	OCCUPY(102, false, null),
	
	/**
	 * buff道具
	 */
	BUFF_ITEM(103, false, null),
	
	/**
	 * 集结进攻战队
	 */
	ALLIANCE_BATTLE_TEAM_LEADER(104, false, null),
	;
	
	private int id;
	private boolean speedUp;
	private List<Integer> cmds = new ArrayList<>();
	
	// 缓存timerTask
	private static Map<Integer, TimerTaskCommand> timerTaskCommandMap = new HashMap<>();
	static {
		for(TimerTaskCommand timerTaskCommand : TimerTaskCommand.values()) {
			timerTaskCommandMap.put(timerTaskCommand.id, timerTaskCommand);
		}
	}
	
	/**
	 * 判断是否是士兵生产的queue
	 * @param timerTaskCommand
	 * @return
	 */
	public static boolean isSoldierOutputQueue(int queueId) {
		if(queueId == TimerTaskCommand.SOLDIER_CAR_OUTPUT.getId() || queueId == TimerTaskCommand.SOLDIER_PLANT_OUTPUT.getId() || queueId == TimerTaskCommand.SOLDIER_TANK_OUTPUT.getId()) {
			return true;
		}
		return false;
	}
	
	public static TimerTaskCommand getTimerTaskCommand(int queueId) {
		return timerTaskCommandMap.get(queueId);
	}
	
	private TimerTaskCommand(int id, boolean speedUp, List<Integer> cmds) {
		this.id = id;
		this.speedUp = speedUp;
		this.cmds = cmds;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isSpeedUp() {
		return speedUp;
	}

	public void setSpeedUp(boolean speedUp) {
		this.speedUp = speedUp;
	}

	public List<Integer> getCmds() {
		return cmds;
	}

	public void setCmds(List<Integer> cmds) {
		this.cmds = cmds;
	}
}
