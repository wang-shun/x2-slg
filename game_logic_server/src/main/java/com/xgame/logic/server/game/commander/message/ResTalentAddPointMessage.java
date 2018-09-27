package com.xgame.logic.server.game.commander.message;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Commander-ResTalentAddPoint - 注释
 */
public class ResTalentAddPointMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=130404;
	//模块号
	public static final int FUNCTION_ID=130;
	//消息号
	public static final int MSG_ID=404;
	
	/**天赋id*/
	@MsgField(Id = 1)
	public int talent;
	/**当前天赋点*/
	@MsgField(Id = 2)
	public int talentPoint;
		
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
		buf.append("talent:" + talent +",");
		buf.append("talentPoint:" + talentPoint +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}