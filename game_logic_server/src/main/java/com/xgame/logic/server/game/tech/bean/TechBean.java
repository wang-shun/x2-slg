package com.xgame.logic.server.game.tech.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Tech-TechBean - 
 */
public class TechBean extends XPro {
	/***/
	@MsgField(Id = 1)
	public int sid;
	/***/
	@MsgField(Id = 2)
	public int level;
	/***/
	@MsgField(Id = 3)
	public int state;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("sid:" + sid +",");
		buf.append("level:" + level +",");
		buf.append("state:" + state +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}