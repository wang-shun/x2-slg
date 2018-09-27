package com.xgame.logic.server.game.email.data.investigate;

import java.io.Serializable;

import com.xgame.logic.server.game.country.bean.Vector2Bean;

import io.protostuff.Tag;

/**
 *
 *2017-1-10  15:01:08
 *@author ye.yuan
 *
 */
public class DefenseTower implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//防御塔数量
	@Tag(1)
	public int defnseId;
	
	//防御塔等级
	@Tag(2)
	public int defnseLevel;
	
	//防御塔位置
	@Tag(3)
	public Vector2Bean loaction;
		
}
