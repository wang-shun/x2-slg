package com.xgame.logic.server.core.utils.geometry.node;

import com.google.common.collect.HashBasedTable;
import com.google.common.eventbus.EventBus;
import com.xgame.logic.server.core.utils.geometry.conf.SpaceConf;
import com.xgame.logic.server.core.utils.geometry.data.Vector2;
import com.xgame.logic.server.core.utils.geometry.data.transform.SpaceTransform;
import com.xgame.logic.server.core.utils.geometry.space.Space;

/**
 *
 *2016-8-31  21:45:06
 *@author ye.yuan
 *
 */
public class SpaceNode{
	
	protected int id;
	
	protected SpaceTransform spaceTransform = new SpaceTransform();
	
	protected Space space;
	
	protected long userUid; 
	
	protected EventBus bus = new EventBus();
	
	public String see; 
	
	public  SpaceNode(int x,int y) {
		spaceTransform.setLocation(new Vector2(x, y));
	}
	
	public void load(SpaceConf conf,HashBasedTable<Integer, Integer, SpaceNode> spaceNodes){
		
	}
	
	public  boolean  isUser() {
		if(userUid == 0&&!isBlock()){
			return false;
		}
		return true;
	}
	
	/**
	 * 一个双向的关联   格子被哪个精灵使用  那个精灵的位置=格子的位置
	 * @param spaceSprite
	 * @return
	 */
	public  boolean  setUser(long userUid) {
		if(this.userUid == 0){
			this.userUid=userUid;
			return true;
		}
		return false;
	}
	
	public  void  unUser() {
		userUid=0;
	}
	
	
	public  boolean  isBlock() {
		return false;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SpaceTransform getSpaceTransform() {
		return spaceTransform;
	}

	public void setSpaceTransform(SpaceTransform spaceTransform) {
		this.spaceTransform = spaceTransform;
	}


	public EventBus getBus() {
		return bus;
	}


	public void setBus(EventBus bus) {
		this.bus = bus;
	}

	public long getUserUid() {
		return userUid;
	}

	public void setUserUid(long userUid) {
		this.userUid = userUid;
	}

	public Space getSpace() {
		return space;
	}

	public void setSpace(Space space) {
		this.space = space;
	}

}
