package com.xgame.logic.server.core.utils.geometry.able;

import com.xgame.logic.server.core.utils.geometry.node.SpaceNode;
import com.xgame.logic.server.core.utils.geometry.space.RingArea;

/**
 *
 *2016-10-30  10:31:56
 *@author ye.yuan
 *
 */
public class SpaceFactory {

	
	public SpaceNode newNode(RingArea ringArea,int x,int y){
		return new SpaceNode(x,y);
	}
	
}
