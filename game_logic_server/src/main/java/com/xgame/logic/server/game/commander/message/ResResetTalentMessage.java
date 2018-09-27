package com.xgame.logic.server.game.commander.message;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;

/** 
 * @author ProtocolEditor
 * Commander-ResResetTalent - 注释
 */
public class ResResetTalentMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=130405;
	//模块号
	public static final int FUNCTION_ID=130;
	//消息号
	public static final int MSG_ID=405;
	

		
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

		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}