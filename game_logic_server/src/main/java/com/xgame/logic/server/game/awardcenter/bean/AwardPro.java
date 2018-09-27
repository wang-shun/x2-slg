package com.xgame.logic.server.game.awardcenter.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * AwardCenter-AwardPro - 
 */
public class AwardPro extends XPro {
	/**奖励下标*/
	@MsgField(Id = 1)
	public int index;
	/**道具id*/
	@MsgField(Id = 2)
	public int itemId;
	/**道具数量*/
	@MsgField(Id = 3)
	public int num;
	/**类型 (txtNl.txt 表ID)*/
	@MsgField(Id = 4)
	public int type;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("index:" + index +",");
		buf.append("itemId:" + itemId +",");
		buf.append("num:" + num +",");
		buf.append("type:" + type +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}