package com.xgame.logic.server.core.utils.geometry.conf;

import com.xgame.logic.server.core.utils.geometry.able.SpaceFactory;

/**
 *
 *2016-10-28  17:35:35
 *@author ye.yuan
 *
 */
public class RingConf extends SpaceConf{

	int [][] size;
	
	
	public RingConf(int nodeWidth,int nodeHeight,int h,int v,SpaceFactory factory,int [] ... size) {
		this.nodeWidth=nodeWidth;
		this.nodeHeight=nodeHeight;
		this.h=h;
		this.v=v;
		this.factory=factory;
		this.size = size;
		int[] len = size[size.length-1];
		capacityX = len[0];
		capacityY = len[1];
	}
	
	public SpaceFactory getFactory() {
		return factory;
	}

	public void setFactory(SpaceFactory factory) {
		this.factory = factory;
	}

	public int[][] getSize() {
		return size;
	}

	public void setSize(int[][] size) {
		this.size = size;
	}

	public int getNodeWidth() {
		return nodeWidth;
	}

	public void setNodeWidth(int nodeWidth) {
		this.nodeWidth = nodeWidth;
	}

	public int getNodeHeight() {
		return nodeHeight;
	}

	public void setNodeHeight(int nodeHeight) {
		this.nodeHeight = nodeHeight;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int getV() {
		return v;
	}

	public void setV(int v) {
		this.v = v;
	}
	
	
}
