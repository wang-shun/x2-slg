package com.xgame.playersearch.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

/**
 * type=9
 * @author dingpeng.qu
 *
 */
public class SpeedUpEventResolve implements ILogResolve {

	@Override
	public String resolve(String content) {
		//{"@type":"com.xgame.logic.server.game.player.entity.eventmodel.SpeedUpEventObject","param":"{"@type":"com.xgame.logic.server.game.timertask.entity.job.data.BuildTimerTaskData","level":1,"sid":1101,"state":1,"uid":611010,"vector2Bean":{"x":38,"y":30}}","timerTaskCommand":"BUILD","type":9}
		//JSONObject jsonObj = JSON.parseObject(content,Feature.DisableSpecialKeyDetect);
		//return "加速时间[类型="+jsonObj.get("timerTaskCommand")+",参数="+jsonObj.get("param")+"]";
		return content;
	}

}
