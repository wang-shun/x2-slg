package com.xgame.logic.server.game.copy.message;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Copy-ReqGetRewardBox - 领取宝箱奖励
 */
public class ReqGetRewardBoxMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=2100203;
	//模块号
	public static final int FUNCTION_ID=2100;
	//消息号
	public static final int MSG_ID=203;
	
	/**副本id*/
	@MsgField(Id = 1)
	public int copyId;
	/**宝箱ID(1,2,3)*/
	@MsgField(Id = 2)
	public int boxId;
		
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
		buf.append("copyId:" + copyId +",");
		buf.append("boxId:" + boxId +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}