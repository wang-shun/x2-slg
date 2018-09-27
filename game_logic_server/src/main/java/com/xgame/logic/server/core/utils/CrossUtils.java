package com.xgame.logic.server.core.utils;

import com.xgame.gameconst.DBKey;

/**
 * 跨服工具类
 * @author jacky.jiang
 *
 */
public class CrossUtils {
	
	/**
	 * 跨服服务器id
	 * @param id
	 * @return
	 */
	public static int getCrossServerByIncrId(long id) {
		return Integer.valueOf(String.valueOf(id).substring(0, DBKey.ID_PRIFIX_LENGTH));
	}
	
	/**
	 * 跨服服务器id
	 * @param id
	 * @return
	 */
	public static int getCrossServerByIncrId(String id) {
		return Integer.valueOf(id.substring(0, DBKey.ID_PRIFIX_LENGTH));
	}
}
