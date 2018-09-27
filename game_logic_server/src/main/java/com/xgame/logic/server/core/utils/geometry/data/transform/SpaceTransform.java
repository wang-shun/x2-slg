package com.xgame.logic.server.core.utils.geometry.data.transform;

import com.xgame.logic.server.core.utils.geometry.matrix.Matrix;

import io.protostuff.Tag;

/**
 *
 *2016-10-30  20:35:59
 *@author ye.yuan
 *
 */
public class SpaceTransform extends Transform{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2909825070841999095L;

	@Tag(2)
	private int capacityX;
	
	@Tag(3)
	private int capacityY;
	
	@Tag(4)
	private int size;
	
	@Tag(5)
	private Matrix matrix;

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

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Matrix getMatrix() {
		return matrix;
	}

	public void setMatrix(Matrix matrix) {
		this.matrix = matrix;
	}

}
