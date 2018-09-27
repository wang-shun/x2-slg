package com.xgame.logic.server.game.alliance.message;
import com.xgame.msglib.ReqMessage;
import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Alliance-ReqDealApply - 发送招募公告
 */
public class ReqDealApplyMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=1007227;
	//模块号
	public static final int FUNCTION_ID=1007;
	//消息号
	public static final int MSG_ID=227;
	
	/**玩家id*/
	@MsgField(Id = 1)
	public long playerId;
	/**审核结果过(1同意2不同意)*/
	@MsgField(Id = 2)
	public int result;
		
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
		buf.append("playerId:" + playerId +",");
		buf.append("result:" + result +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}