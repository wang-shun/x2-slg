package com.xgame.utils.bean;


import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 * 
 */
public class MsgSpriteAward extends XPro {

	//
	@MsgField(Id = 1)
	public int itemId;
	
	//
	@MsgField(Id = 2)
	public int num;
	
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		//
		buf.append("itemId:" + itemId +",");
		//
		buf.append("num:" + num +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}