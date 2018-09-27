package com.xgame.logic.server.game.world.bean;


import com.xgame.logic.server.game.armshop.bean.SoldierBriefPro;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 * 行军队列信息
 */
public class MarchSoldierBean extends XPro {

	//行军的基本信息
	@MsgField(Id = 1)
	public SoldierBriefPro soldier;
	
	//序号
	@MsgField(Id = 2)
	public int index;
	
	//家园所在位置
	@MsgField(Id = 3)
	public Vector2Bean location;
	
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		//行军的基本信息
		if(this.soldier!=null) buf.append("soldier:" + soldier.toString() +",");
		//序号
		buf.append("index:" + index +",");
		//家园所在位置
		if(this.location!=null) buf.append("location:" + location.toString() +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}