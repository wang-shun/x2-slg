package com.xgame.playersearch.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

/**
 * type=8
 * @author dingpeng.qu
 *
 */
public class LevelupBuildEventResolve implements ILogResolve {

	@Override
	public String resolve(String content) {
		JSONObject jsonObj = JSON.parseObject(content,Feature.DisableSpecialKeyDetect);
		return "建筑升级开始[建筑Id="+jsonObj.get("buildTid")+",用时="+jsonObj.get("costTime")+",战力="+jsonObj.get("fightPower")+",坐标="+jsonObj.get("position")+"]";
	}

}
