package com.xgame.logic.server.game.world.entity;

import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.world.constant.WorldConstant;

import io.protostuff.Tag;

/**
 * 坐标点
 * @author jacky.jiang
 *
 */
public class Vector2 {
	
	/**
	 * x坐标
	 */
	@Tag(1)
	private int x;
	
	/**
	 * y坐标
	 */
	@Tag(2)
	private int y;
	
	public Vector2(){
		
	}
	
	public Vector2(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2Bean toVectorBean() {
		Vector2Bean vector2Bean = new Vector2Bean();
		vector2Bean.x = this.x;
		vector2Bean.y = this.y;
		return vector2Bean;
	}
	
	public int getIndex() {
		return y * WorldConstant.X_GRIDNUM + x;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
