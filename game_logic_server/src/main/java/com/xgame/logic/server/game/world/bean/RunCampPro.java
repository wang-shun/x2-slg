package com.xgame.logic.server.game.world.bean;

import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 * 军营信息
 */
public class RunCampPro extends XPro {

	//建筑物sid
	@MsgField(Id = 1)
	public int campId;
	
	//出征士兵
	@MsgField(Id = 2)
	public List<SoldierPro> soldiers = new ArrayList<SoldierPro>();
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		//建筑物sid
		buf.append("campId:" + campId +",");
		//出征士兵
		buf.append("soldiers:{");
		for (int i = 0; i < soldiers.size(); i++) {
			buf.append(soldiers.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}