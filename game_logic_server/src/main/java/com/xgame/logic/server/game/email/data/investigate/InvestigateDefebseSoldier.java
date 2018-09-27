package com.xgame.logic.server.game.email.data.investigate;

import io.protostuff.Tag;

import java.io.Serializable;

import com.xgame.logic.server.game.country.bean.Vector2Bean;

/**
 *
 *2017-1-10  15:03:57
 *@author ye.yuan
 *
 */
public class InvestigateDefebseSoldier implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//兵种
	@Tag(1)
	public String soldierName;
	
	//兵种图标
	@Tag(2)
	public String soldierIcon;
	
	//人数
	@Tag(3)
	public int num;
	
	//防御塔位置
	@Tag(4)
	public Vector2Bean loaction;
	
	//防御塔位置
	@Tag(5)
	public int soldierId;
	
}
