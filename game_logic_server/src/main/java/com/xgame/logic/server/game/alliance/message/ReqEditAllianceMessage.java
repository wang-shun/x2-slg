package com.xgame.logic.server.game.alliance.message;
import com.xgame.msglib.ReqMessage;
import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Alliance-ReqEditAlliance - 编辑头衔
 */
public class ReqEditAllianceMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=1007222;
	//模块号
	public static final int FUNCTION_ID=1007;
	//消息号
	public static final int MSG_ID=222;
	
	/**联盟名称*/
	@MsgField(Id = 1)
	public String allianceName;
	/**简称*/
	@MsgField(Id = 2)
	public String abbr;
	/**联盟旗帜*/
	@MsgField(Id = 3)
	public String icon;
	/**联盟宣言*/
	@MsgField(Id = 4)
	public String announce;
	/**语言*/
	@MsgField(Id = 5)
	public String language;
		
	@Override
	public int getId() {
		return ID;
	}

	@Override
	public String getQueue() {
		return "s2s";
	}
	
	@Override
	public String getServer() {
		return null;
	}
	
	@Override
	public boolean isSync() {
		return false;
	}

	@Override
	public CommandEnum getCommandEnum() {
		return Communicationable.CommandEnum.PLAYERMSG;
	}
	
	@Override
	public HandlerEnum getHandlerEnum() {
		return Communicationable.HandlerEnum.CS;
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("allianceName:" + allianceName +",");
		buf.append("abbr:" + abbr +",");
		buf.append("icon:" + icon +",");
		buf.append("announce:" + announce +",");
		buf.append("language:" + language +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}