package com.xgame.logic.server.game.country.structs.build.camp.data;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xgame.logic.server.game.soldier.entity.Soldier;

import io.protostuff.Tag;

/**
 *军工厂
 *2016-8-05  11:42:32
 *@author ye.yuan
 *
 */
public class Camp implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Tag(1)
	public Map<Integer, Soldier> soldierTable = new ConcurrentHashMap<>();
	
	@Tag(2)
	public Map<Integer,int[]> soldierGivesTable = new ConcurrentHashMap<>();
	
	@Tag(3)
	public Map<Integer, Integer> soldierHurtTable = new ConcurrentHashMap<>();

	@Tag(4)
	public Map<Integer, Integer> soldierRunTable = new ConcurrentHashMap<>();
	//改造厂
	@Tag(5)
	public Map<Long, ReformSoldier> reformSoldierTable = new ConcurrentHashMap<>();
	//改造厂
	@Tag(6)
	public Map<Long, ReformSoldier> reformSoldierHurtTable = new ConcurrentHashMap<>();
	
	
}
