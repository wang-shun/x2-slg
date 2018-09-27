package com.xgame.logic.server.game.copy.message;
import com.xgame.logic.server.game.copy.bean.CopyRaidResult;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Copy-ResCopyRaid - 扫荡返回结果
 */
public class ResCopyRaidMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=2100104;
	//模块号
	public static final int FUNCTION_ID=2100;
	//消息号
	public static final int MSG_ID=104;
	
	/**扫荡结果*/
	@MsgField(Id = 1)
	public CopyRaidResult copyRaidResult;
		
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
		buf.append("copyRaidResult:" + copyRaidResult +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}