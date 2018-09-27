package com.xgame.logic.server.game.commander.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Commander-TalentPro - 天赋
 */
public class TalentPro extends XPro {
	/**所属天赋页*/
	@MsgField(Id = 1)
	public int formPage;
	/**配置id*/
	@MsgField(Id = 2)
	public int sid;
	/**当前等级*/
	@MsgField(Id = 3)
	public int level;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("formPage:" + formPage +",");
		buf.append("sid:" + sid +",");
		buf.append("level:" + level +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}