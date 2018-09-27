package com.xgame.logic.server.game.defensesoldier.message;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * DefenseSoldier-ResDefensAutoSetup - 注释
 */
public class ResDefensAutoSetupMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=132401;
	//模块号
	public static final int FUNCTION_ID=132;
	//消息号
	public static final int MSG_ID=401;
	
	/**唯一id*/
	@MsgField(Id = 1)
	public int buildUid;
	/** 0 开自动补兵 1 关自动补兵 2开自动防御  3关自动防御*/
	@MsgField(Id = 2)
	public int auto;
		
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
		buf.append("buildUid:" + buildUid +",");
		buf.append("auto:" + auto +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}