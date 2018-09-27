package com.xgame.logic.server.game.alliance.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Alliance-AllianceOfficeBean - 联盟官员
 */
public class AllianceOfficeBean extends XPro {
	/**玩家*/
	@MsgField(Id = 1)
	public AlliancePlayerViewBean allianceOffice;
	/**官职名称*/
	@MsgField(Id = 2)
	public String officeName;
	/**官员类型(1-对应官职1)*/
	@MsgField(Id = 3)
	public int type;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("allianceOffice:" + allianceOffice +",");
		buf.append("officeName:" + officeName +",");
		buf.append("type:" + type +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}