package com.xgame.logic.server.game.alliance.message;
import com.xgame.msglib.ReqMessage;
import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Alliance-ReqTrade2Player - 请求与某玩家贸易
 */
public class ReqTrade2PlayerMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=1007261;
	//模块号
	public static final int FUNCTION_ID=1007;
	//消息号
	public static final int MSG_ID=261;
	
	/**玩家ID*/
	@MsgField(Id = 1)
	public long roleId;
	/**石油数量*/
	@MsgField(Id = 2)
	public int oil;
	/**稀土数量*/
	@MsgField(Id = 3)
	public int rare;
	/**钢材数量*/
	@MsgField(Id = 4)
	public int steel;
	/**钞票数量*/
	@MsgField(Id = 5)
	public int money;
		
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
		buf.append("roleId:" + roleId +",");
		buf.append("oil:" + oil +",");
		buf.append("rare:" + rare +",");
		buf.append("steel:" + steel +",");
		buf.append("money:" + money +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}