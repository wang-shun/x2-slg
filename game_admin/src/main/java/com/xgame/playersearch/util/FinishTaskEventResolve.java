package com.xgame.playersearch.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

public class FinishTaskEventResolve implements ILogResolve {
	
	public static final int TASK_TYPE_1 = 1;//1日常
	public static final int TASK_TYPE_2 = 2;//2军团
	public static final int TASK_TYPE_3 = 3;//3基地
	public static final int TASK_TYPE_4 = 4;//4-活跃度

	@Override
	public String resolve(String content) {
		JSONObject jsonObj = JSON.parseObject(content,Feature.DisableSpecialKeyDetect);
		switch (jsonObj.getIntValue("taskType")) {
		case TASK_TYPE_1:
			jsonObj.put("taskType", "日常");
			break;
		case TASK_TYPE_2:
			jsonObj.put("taskType", "军团");
			break;
		case TASK_TYPE_3:
			jsonObj.put("taskType", "基地");
			break;
		case TASK_TYPE_4:
			jsonObj.put("taskType", "活跃度");
			break;
		default:
			jsonObj.put("taskType", "");
			break;
		}
		return "完成任务[任务Id="+jsonObj.get("taskId")+",任务类型="+jsonObj.get("taskType")+"]";
	}

}
