package com.xgame.logic.server.game.country.structs.build.mod;

import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.war.constant.WarResultType;
import com.xgame.logic.server.game.war.constant.WarType;

/**
 *
 *2017-1-10  14:18:46
 *@author ye.yuan
 *
 */
public class ModifyKit {
	
	
	public static int warInsert(Player player, long soldierId,int num, WarType warType, WarResultType warResultType, int soldierChangeType) {
		return player.getRepairManager().fightInsertModFactory(player, soldierId, num, warType, warResultType, soldierChangeType);
	}
	
	/**
	 * 插入到修理厂
	 * @param player
	 * @param soldierId
	 * @param num
	 * @param soldierChangeType
	 * @return
	 */
	public static int insert(Player player,long soldierId,int num, int soldierChangeType) {
		return player.getRepairManager().insertModFactory(player, soldierId, num, soldierChangeType);
	}
	
	/**
	 * 插入驻防士兵到修理厂
	 * @param player
	 * @param soldierId
	 * @param num
	 * @param buildUid
	 * @return
	 */
	public static int insertDefenseSoldier(Player player,long soldierId,int num,int buildUid) {
		return player.getRepairManager().insertModFactory(player, soldierId, num, 0,buildUid);
	}
	
//	public static void updataClient(Player player) {
//		ModBuildControl modBuildControl = player.getCountryManager().getModBuildControl();
//		if(modBuildControl!=null){
//			modBuildControl.updataClient(player);
//		}
//	}
	
}
