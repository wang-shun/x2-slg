package com.xgame.logic.server.core.utils;

/**
 * 参数格式化
 * @author jacky.jiang
 *
 */
public class ParamFormatUtils {
	
	public static String formatParma(Object... objects) {
		return JsonUtil.toJSON(objects);
	}
}
