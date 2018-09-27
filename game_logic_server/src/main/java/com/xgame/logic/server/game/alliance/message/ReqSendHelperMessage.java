package com.xgame.logic.server.game.alliance.message;
import com.xgame.msglib.ReqMessage;
import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Alliance-ReqSendHelper - 发送帮助
 */
public class ReqSendHelperMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=1007231;
	//模块号
	public static final int FUNCTION_ID=1007;
	//消息号
	public static final int MSG_ID=231;
	
	/**任务id*/
	@MsgField(Id = 1)
	public long taskId;
	/**援建类型（1建筑建造2建筑升级3科技升级4士兵修理）*/
	@MsgField(Id = 2)
	public int type;
		
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
		buf.append("taskId:" + taskId +",");
		buf.append("type:" + type +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}