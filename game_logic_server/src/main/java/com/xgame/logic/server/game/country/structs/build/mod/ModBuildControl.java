package com.xgame.logic.server.game.country.structs.build.mod;

import com.xgame.logic.server.game.country.structs.build.CountryBuildControl;

/**
 * 修改厂
 * 2016-8-23 11:03:52
 *
 * @author ye.yuan
 *
 */
public class ModBuildControl extends CountryBuildControl {

	/**
	 * 最大缓存
	 */
	int maxCache;

	/**
	 * 自建武器最大缓存
	 */
	int maxReformCache;
	
	
//	public void dataLoadAfter(Player player) {
//		player.getRepairManager().(player);
//	}
//	
//	/**
//	 * 添加以后
//	 * @param player
//	 * @param build
//	 */
//	protected  void addBuildAfter(Player player,XBuild build){
//		mathMaxCache();
//		updataClient(player);
//	}
//
//	/**
//	 * 升级完成 值发生改变 更新客户端
//	 * 
//	 * @param player
//	 * @param uid
//	 *            升级的建筑物
//	 */
//	public void levelUpAfter(Player player, int uid) {
//		mathMaxCache();
//		updataClient(player);
//	}
	
}
