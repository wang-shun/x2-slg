package com.xgame.logic.server.game.world.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *2016-9-01  21:40:59
 *@author ye.yuan
 *
 */
public class ResourceRange {

	private Map<Integer, List<int[]>> rangeNodeMap = new HashMap<>();
	
	private Map<Integer, int[]> canUseNodeMap;

	public Map<Integer, List<int[]>> getRangeNodeMap() {
		return rangeNodeMap;
	}

	public void setRangeNodeMap(Map<Integer, List<int[]>> rangeNodeMap) {
		this.rangeNodeMap = rangeNodeMap;
	}

	public Map<Integer, int[]> getCanUseNodeMap() {
		return canUseNodeMap;
	}

	public void setCanUseNodeMap(Map<Integer, int[]> canUseNodeMap) {
		this.canUseNodeMap = canUseNodeMap;
	}
	
}
