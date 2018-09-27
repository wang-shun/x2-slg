package com.xgame.logic.server.game.country.structs.build;

import com.xgame.logic.server.game.country.entity.BlockBuild;
import com.xgame.logic.server.game.country.entity.XBuild;
import com.xgame.logic.server.game.player.entity.Player;

/**
 *
 *2016-11-05  23:22:09
 *@author ye.yuan
 *
 */
public class BlockBuildControl extends BuildControl{
	
	/**
	 * 障碍物不能移动
	 */
	public boolean isMove() {
		return false;
	}
	
	/**
	 * 障碍物走单独存储集合
	 */
	public void createInit0(Player player,XBuild build) {
		player.roleInfo().getBaseCountry().getBlocks().put(build.getUid(), (BlockBuild)build);
	}
	
	public void remove(Player player,int buildUid){
		player.roleInfo().getBaseCountry().getBlocks().remove(buildUid);
	}

	/**
	 * 障碍物没有最大建筑物说法
	 */
	protected XBuild mathLevelMaxBuild(){
		return maxLevelBuild = getDefianlBuild();
	}
	
	protected XBuild newCountryBuild(){
		return new BlockBuild();
	}
}
