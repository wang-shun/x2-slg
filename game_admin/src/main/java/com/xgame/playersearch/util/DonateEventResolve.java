package com.xgame.playersearch.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

/**
 * type=206
 * @author dingpeng.qu
 *
 */
public class DonateEventResolve implements ILogResolve{
	
	@Override
	public String resolve(String content) {
		//{"@type":"com.xgame.logic.server.game.alliance.enity.eventmodel.DonateEventObject","currencyEnum":"OIL","donateCount":4,"donateType":1,"type":206}
		JSONObject jsonObj = JSON.parseObject(content,Feature.DisableSpecialKeyDetect);
		if(jsonObj.getIntValue("donateType") == 1){
			jsonObj.put("donateType", "军团");
		}else if(jsonObj.getIntValue("donateType") == 2){
			jsonObj.put("donateType", "军团科研");
		}else{
			jsonObj.put("donateType", "");
		}
		for(CurrencyTypeEnum cte : CurrencyTypeEnum.values()){
			if(cte.name().equals(jsonObj.get("currencyEnum"))){
				jsonObj.put("currencyEnum", cte.getDescribe());
			}
		}
		return "捐献[类型="+jsonObj.get("donateType")+",资源类型="+jsonObj.get("currencyEnum")+",捐献次数="+jsonObj.get("donateCount")+"]";
	}
}
