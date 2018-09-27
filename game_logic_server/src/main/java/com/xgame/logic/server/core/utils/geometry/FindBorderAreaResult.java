package com.xgame.logic.server.core.utils.geometry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xgame.logic.server.core.utils.geometry.node.SpaceNode;

/**
 *格子查找返回结果集
 *2016-7-14  11:08:31
 *@author ye.yuan
 *
 */
public class FindBorderAreaResult {
	
	
	/**
	 * 找到的全部格子
	 */
	private Map<Integer, SpaceNode> findMap = new ConcurrentHashMap<>();
	
	/**
	 * 物体占用描边格子
	 */
	private Map<Integer, SpaceNode> borderMap = new ConcurrentHashMap<>();
	
	/**
	 * 物体实际占用格子
	 */
	private Map<Integer, SpaceNode> buildMap = new ConcurrentHashMap<>();


	public Map<Integer, SpaceNode> getFindMap() {
		return findMap;
	}

	public void setFindMap(Map<Integer, SpaceNode> findMap) {
		this.findMap = findMap;
	}

	public Map<Integer, SpaceNode> getBorderMap() {
		return borderMap;
	}

	public void setBorderMap(Map<Integer, SpaceNode> borderMap) {
		this.borderMap = borderMap;
	}

	public Map<Integer, SpaceNode> getBuildMap() {
		return buildMap;
	}

	public void setBuildMap(Map<Integer, SpaceNode> buildMap) {
		this.buildMap = buildMap;
	}

	
}
