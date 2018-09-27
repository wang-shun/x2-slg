package com.xgame.logic.server.core.utils.geometry.node;


/**
 *
 *2016-8-31  21:54:50
 *@author ye.yuan
 *
 */
public class WaterSpaceNode extends SpaceNode{

	public WaterSpaceNode(int x, int y) {
		super(x, y);
	}

	public  boolean  setUser(long user) {
		return false;
	}
	
	public  boolean  isUser() {
		return false;
	}
	
	public  boolean  isBlock() {
		return true;
	}
	
}
