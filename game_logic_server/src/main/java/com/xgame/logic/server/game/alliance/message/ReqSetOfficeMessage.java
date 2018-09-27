package com.xgame.logic.server.game.alliance.message;
import com.xgame.msglib.ReqMessage;
import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Alliance-ReqSetOffice - 设置官员
 */
public class ReqSetOfficeMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=1007239;
	//模块号
	public static final int FUNCTION_ID=1007;
	//消息号
	public static final int MSG_ID=239;
	
	/**目标玩家id*/
	@MsgField(Id = 1)
	public long tagetPlayerId;
	/**官员位置:1官员12官员2。。。*/
	@MsgField(Id = 2)
	public int officePos;
	/**队长位置:1官员12官员2。。。*/
	@MsgField(Id = 3)
	public int teamPos;
		
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
		buf.append("tagetPlayerId:" + tagetPlayerId +",");
		buf.append("officePos:" + officePos +",");
		buf.append("teamPos:" + teamPos +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}