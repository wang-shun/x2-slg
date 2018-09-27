package com.xgame.logic.server.game.alliance.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Alliance-AllianceTitleBean - 联盟头衔
 */
public class AllianceTitleBean extends XPro {
	/**官职1*/
	@MsgField(Id = 1)
	public String office1;
	/**官职2*/
	@MsgField(Id = 2)
	public String office2;
	/**官职3*/
	@MsgField(Id = 3)
	public String office3;
	/**官职4*/
	@MsgField(Id = 4)
	public String office4;
	/**成员1*/
	@MsgField(Id = 5)
	public String rankName1;
	/**成员2*/
	@MsgField(Id = 6)
	public String rankName2;
	/**成员3*/
	@MsgField(Id = 7)
	public String rankName3;
	/**成员4*/
	@MsgField(Id = 8)
	public String rankName4;
	/**成员5*/
	@MsgField(Id = 9)
	public String rankName5;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("office1:" + office1 +",");
		buf.append("office2:" + office2 +",");
		buf.append("office3:" + office3 +",");
		buf.append("office4:" + office4 +",");
		buf.append("rankName1:" + rankName1 +",");
		buf.append("rankName2:" + rankName2 +",");
		buf.append("rankName3:" + rankName3 +",");
		buf.append("rankName4:" + rankName4 +",");
		buf.append("rankName5:" + rankName5 +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}