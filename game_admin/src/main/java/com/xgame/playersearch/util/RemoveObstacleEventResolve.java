package com.xgame.playersearch.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

/**
 * type=16
 * @author dingpeng.qu
 *
 */
public class RemoveObstacleEventResolve implements ILogResolve {

	@Override
	public String resolve(String content) {
		JSONObject jsonObj = JSON.parseObject(content,Feature.DisableSpecialKeyDetect);
		return "移除障碍物[等级="+jsonObj.get("level")+",坐标="+jsonObj.get("position")+"]";
	}

}
