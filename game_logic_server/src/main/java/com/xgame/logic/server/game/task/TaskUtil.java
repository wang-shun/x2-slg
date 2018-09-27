package com.xgame.logic.server.game.task;

import java.util.List;
import java.util.Map.Entry;

import com.xgame.config.achieve.AchievePirFactory;

public class TaskUtil {
	
	/**
	 * 是否是勋章任务
	 * @param taskId
	 * @return
	 */
	public static boolean isAchieveTask(int taskId){
		for(Entry<Integer,List<Integer>> entry : AchievePirFactory.achievePirMap.entrySet()){
			if(entry.getValue().contains(taskId)){
				return true;
			}
		}
		return false;
	}

}
