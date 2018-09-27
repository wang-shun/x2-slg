package com.xgame.logic.server.game.email.data.investigate;


import io.protostuff.Tag;

import com.xgame.logic.server.game.customweanpon.bean.DesignMapBean;
import com.xgame.logic.server.game.world.entity.Vector2;

/**
 *
 *2017-1-16  17:13:42
 *@author ye.yuan
 *
 */
public class InvestigateSetOffSoldier {

	
	//兵种
	@Tag(1)
	public String soldierName;
	
	//兵种图标
	@Tag(2)
	public String soldierIcon;
	
	//人数
	@Tag(3)
	public int num;
	
	/**
	 * 出征位置
	 */
	@Tag(4)
	public Vector2 loaction;
	
	@Tag(5)
	public long soldierId;
	
	/**
	 * 出征人数
	 */
	@Tag(6)
	public int color;
	
	/**
	 * 图纸信息
	 */
	@Tag(7)
	public DesignMapBean designMapBean;
		
}
