package com.xgame.playersearch.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
/**
 * type = 10
 * @author dingpeng.qu
 *
 */
public class ItemChangeEventResolve implements ILogResolve {
	
	@Override
	public String resolve(String content) {
		JSONObject jsonObj = JSON.parseObject(content,Feature.DisableSpecialKeyDetect);
		if(jsonObj.getIntValue("changeType") == 1){
			jsonObj.put("changeType", "增加");
		}else if(jsonObj.getIntValue("changeType") == 2){
			jsonObj.put("changeType", "删除");
		}else{
			jsonObj.put("changeType", "");
		}
		return "道具数量变更[道具Id="+jsonObj.get("itemTid")+",玩家Id="+jsonObj.get("playerId")+",变更类型="+jsonObj.get("changeType")+",变更前="+jsonObj.get("beforeNum")+",变更后="+jsonObj.get("afterNum")+"]";
	}

}
