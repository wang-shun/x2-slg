package com.xgame.logic.server.core.utils.geometry.matrix;
/**
 *空间矩阵信息
 *2016-10-30  11:36:10
 *@author ye.yuan
 *
 */
public class Matrix {

	/**
	 * 原点坐标x  
	 */
	public int sourcePointX;
	

	/**
	 * 原点坐标y
	 */
	public int sourcePointY;
	
	
	public Matrix(int sourcePointX, int sourcePointY) {
		this.sourcePointX = sourcePointX;
		this.sourcePointY = sourcePointY;
	}
	
	public int transfomX(int v){
		return v*sourcePointX;
	}
	
	public int transfomY(int v){
		return v*sourcePointY;
	}
	
}
