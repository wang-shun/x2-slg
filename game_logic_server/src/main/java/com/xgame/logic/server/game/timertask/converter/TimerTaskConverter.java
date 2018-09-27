package com.xgame.logic.server.game.timertask.converter;

import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.timertask.bean.TimerBean;
import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;

/**
 * 时间任务转换器
 * @author jacky.jiang
 *
 */
public class TimerTaskConverter {
	
	/**
	 * 转换timerBean 
	 * @param player
	 * @param data
	 * @return
	 */
	public static TimerBean toTimerBean(Player player, TimerTaskData data) {
		TimerBean timerBean = new TimerBean();
		timerBean.timerUid = data.getTaskId();
		timerBean.endTime = data.getTriggerTime();
		timerBean.startTime = data.getStartTime();
		timerBean.type = data.getQueueId();
		timerBean.data = JsonUtil.toJSON(data.getParam());
		return timerBean;
	}
}
