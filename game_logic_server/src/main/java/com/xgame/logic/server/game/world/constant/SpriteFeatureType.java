package com.xgame.logic.server.game.world.constant;

import com.xgame.logic.server.game.world.entity.Vector2;




/**
 * 精灵外观枚举
 * @author jacky.jiang
 *
 */
public enum SpriteFeatureType {
	
	/**
	 * 1*1的格子
	 */
	SPRITE_1X1 {
		/**
		 * 获取数组
		 */
		public Vector2[] getRange(int x, int y) {
			Vector2[] vec = new Vector2[1];
			vec[0] = new Vector2(x, y);
			return vec;
		}
	},
	
	/**
	 * 2*2的格子
	 */
	SPRITE_2X2 {
		/**
		 * 获取数组
		 */
		public Vector2[] getRange(int x, int y) {
			Vector2[] vec = new Vector2[2];
			vec[0] = new Vector2(x, y);
			vec[1] = new Vector2(x + 1, y + 1);
			vec[2] = new Vector2(x, y + 1);
			vec[3] = new Vector2(x + 1, y);
			return vec;
		}
	},
	
	/**
	 * 3*3格子
	 */
	SPRITE_3X3 {
		/**
		 * 获取数组
		 */
		public Vector2[] getRange(int x, int y) {
			Vector2[] vec = new Vector2[2];
			vec[0] = new Vector2(x, y);
			vec[1] = new Vector2(x + 1, y);
			vec[2] = new Vector2(x, y + 1);
			vec[3] = new Vector2(x - 1, y);
			vec[4] = new Vector2(x, y - 1);
			vec[5] = new Vector2(x - 1, y - 1);
			return vec;
		}
	};
	
	/***
	 * 获取范围格子序列
	 * @param x
	 * @param y
	 * @return
	 */
	public Vector2[] getRange(int x, int y) {
		throw new RuntimeException("获取x和y的数组序列.");
	}
}
