package com.xgame.logic.server.game.alliance.message;
import com.xgame.logic.server.game.alliance.bean.AllianceHelpeBean;
import com.xgame.msglib.ResMessage;
import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Alliance-ResDoneHelp - 通知被援建目标
 */
public class ResDoneHelpMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=1007133;
	//模块号
	public static final int FUNCTION_ID=1007;
	//消息号
	public static final int MSG_ID=133;
	
	/**联盟援建*/
	@MsgField(Id = 1)
	public AllianceHelpeBean allianceHelpe;
		
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
		return Communicationable.HandlerEnum.SC;
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("allianceHelpe:" + allianceHelpe +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}