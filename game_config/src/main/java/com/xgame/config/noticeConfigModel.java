package com.xgame.config;

import java.util.HashMap;
import java.util.Map;


/**
 * 公告配置信息
 * @author jacky.jiang
 *
 */
public class noticeConfigModel {
	
	public static Map<Integer, noticeConfigModel> factory = new HashMap<>();
	
	private int id;
	
	private String notice;
	
}
