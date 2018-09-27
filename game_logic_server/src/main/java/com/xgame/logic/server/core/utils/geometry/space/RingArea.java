package com.xgame.logic.server.core.utils.geometry.space;

import java.util.LinkedList;

import org.apache.commons.lang3.RandomUtils;

import com.google.common.collect.HashBasedTable;
import com.xgame.logic.server.core.utils.geometry.data.Vector2;
import com.xgame.logic.server.core.utils.geometry.matrix.Rect;

/**
 * 环区域 2016-10-29 18:28:29
 *
 * @author ye.yuan
 *
 */
public class RingArea {

	/**
	 * 环地址
	 */
	private int index;

	/**
	 * 外环
	 */
	private Rect maxRect;

	/**
	 * 内环
	 */
	private Rect minRect;

	/**
	 * 环内节点位置
	 */
	private HashBasedTable<Integer, Integer, Vector2> nodeCoordinates = HashBasedTable
			.create();
	
	private LinkedList<Vector2> linkedList = new LinkedList<>();

	public Vector2 linkedRandomPoint() {
		return linkedList.get(RandomUtils.nextInt(0, linkedList.size()));
	}
	
	public Vector2 randomPoint() {
		switch (RandomUtils.nextInt(0, 4)) {
		case 0:{
			int x = RandomUtils.nextInt(maxRect.getSpaceTransform().getLeftDownPoint().getX(),maxRect.getSpaceTransform().getRightDownPoint().getX());
			int y = RandomUtils.nextInt(maxRect.getSpaceTransform().getLeftDownPoint().getY(),minRect.getSpaceTransform().getLeftDownPoint().getY());
			return new Vector2(x, y);
		}
		case 1:{
			int x = RandomUtils.nextInt(minRect.getSpaceTransform().getRightDownPoint().getX(),maxRect.getSpaceTransform().getRightDownPoint().getX());
			int y = RandomUtils.nextInt(minRect.getSpaceTransform().getRightDownPoint().getY(),minRect.getSpaceTransform().getRightUpPoint().getY());
			return new Vector2(x, y);
		}
		case 2:{
			int x = RandomUtils.nextInt(maxRect.getSpaceTransform().getLeftDownPoint().getX(),maxRect.getSpaceTransform().getRightDownPoint().getX());
			int y = RandomUtils.nextInt(minRect.getSpaceTransform().getLeftDownPoint().getY(),maxRect.getSpaceTransform().getLeftUpPoint().getY());
			return new Vector2(x, y);
		}
		case 3:{
			int x = RandomUtils.nextInt(maxRect.getSpaceTransform().getLeftDownPoint().getX(),maxRect.getSpaceTransform().getLeftDownPoint().getX());
			int y = RandomUtils.nextInt(minRect.getSpaceTransform().getLeftDownPoint().getY(),minRect.getSpaceTransform().getLeftUpPoint().getY());
			return new Vector2(x, y);
		}
		default:
			break;
		}
		return null;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Rect getMaxRect() {
		return maxRect;
	}

	public void setMaxRect(Rect maxRect) {
		this.maxRect = maxRect;
	}

	public Rect getMinRect() {
		return minRect;
	}

	public void setMinRect(Rect minRect) {
		this.minRect = minRect;
	}

	public HashBasedTable<Integer, Integer, Vector2> getNodeCoordinates() {
		return nodeCoordinates;
	}

	public void setNodeCoordinates(
			HashBasedTable<Integer, Integer, Vector2> nodeCoordinates) {
		this.nodeCoordinates = nodeCoordinates;
	}

	public LinkedList<Vector2> getLinkedList() {
		return linkedList;
	}

	public void setLinkedList(LinkedList<Vector2> linkedList) {
		this.linkedList = linkedList;
	}

}
