package com.xgame.logic.server.core.utils.geometry.conf;

import com.xgame.logic.server.core.utils.geometry.able.SpaceFactory;

/**
 *空间配置器
 *2016-11-02  15:21:41
 *@author ye.yuan
 *
 */
public class SpaceConf {
	
	/**
	 * 节点长
	 */
	protected int nodeWidth;
	
	/**
	 * 节点宽
	 */
	protected int nodeHeight;
	
	/**
	 * x象限
	 */
	protected int h;
	
	/**
	 * y象限
	 */
	protected int v;
	
	/**
	 * 回调生产对象
	 */
	protected SpaceFactory factory;

	/**
	 * 总x
	 */
	protected int capacityX;
	
	/**
	 * 总y
	 */
	protected int capacityY;
	
	public SpaceConf() {
	}
	
	
	public SpaceConf(int nodeWidth, int nodeHeight, int h, int v,int capacityX, int capacityY) {
		this(nodeWidth, nodeHeight, h, v, null, capacityX, capacityY);
	}
	
	public SpaceConf(int nodeWidth, int nodeHeight, int h, int v,
			SpaceFactory factory, int capacityX, int capacityY) {
		this.nodeWidth = nodeWidth;
		this.nodeHeight = nodeHeight;
		this.h = h;
		this.v = v;
		this.factory = factory;
		this.capacityX = capacityX;
		this.capacityY = capacityY;
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

	public SpaceFactory getFactory() {
		return factory;
	}

	public void setFactory(SpaceFactory factory) {
		this.factory = factory;
	}

	public int getCapacityX() {
		return capacityX;
	}

	public void setCapacityX(int capacityX) {
		this.capacityX = capacityX;
	}

	public int getCapacityY() {
		return capacityY;
	}

	public void setCapacityY(int capacityY) {
		this.capacityY = capacityY;
	}

	
}
