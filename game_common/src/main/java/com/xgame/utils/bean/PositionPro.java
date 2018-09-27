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
public class PositionPro extends XPro {

	//
	@MsgField(Id = 1)
	public float x;
	
	//
	@MsgField(Id = 2)
	public float y;
	
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		//
		buf.append("x:" + x +",");
		//
		buf.append("y:" + y +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}