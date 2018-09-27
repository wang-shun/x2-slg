package com.xgame.logic.server.game.email.data.investigate;

import java.util.ArrayList;
import java.util.List;

import com.xgame.logic.server.game.email.data.TalkMailData;

import io.protostuff.Tag;

/**
 *
 *2017-1-10  14:53:42
 *@author ye.yuan
 *
 */
public class InvestigatePlayerMailData extends TalkMailData{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 金钱
	 */
	@Tag(54)
	public long resMoney;

	/**
	 * 石油
	 */
	@Tag(55)
	public long resOil;

	/**
	 * 稀土
	 */
	@Tag(56)
	public long resRare;

	/**
	 * 钢材
	 */
	@Tag(57)
	public long resSteel;
	
	/**
	 * 
	 */
	@Tag(58)
	public List<DefenseTower> defenseTowers = new ArrayList<>();
	
	//防守部队列表
	@Tag(59)
	public List<InvestigateDefebseSoldier> soldierList = new ArrayList<>();

}
