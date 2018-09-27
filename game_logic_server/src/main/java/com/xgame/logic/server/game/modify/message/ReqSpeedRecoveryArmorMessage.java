package com.xgame.logic.server.game.modify.message;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Modify-ReqSpeedRecoveryArmor - 请求加速修复装甲
 */
public class ReqSpeedRecoveryArmorMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=601204;
	//模块号
	public static final int FUNCTION_ID=601;
	//消息号
	public static final int MSG_ID=204;
	
	/**唯一id*/
	@MsgField(Id = 1)
	public long id;
	/**1宝石 2道具*/
	@MsgField(Id = 2)
	public int useType;
		
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
		buf.append("id:" + id +",");
		buf.append("useType:" + useType +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}