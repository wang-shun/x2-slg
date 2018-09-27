package com.xgame.logic.server.game.world.bean;


import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 * 军营信息
 */
public class SoldierPro extends XPro {

	//士兵id
	@MsgField(Id = 1)
	public int soldierId;
	
	//士兵数量
	@MsgField(Id = 2)
	public int num;
	
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		//士兵id
		buf.append("soldierId:" + soldierId +",");
		//士兵数量
		buf.append("num:" + num +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}