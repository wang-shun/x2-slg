package com.xgame.logic.server.game.vip.message;
import com.xgame.logic.server.game.vip.bean.VipBean;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Vip-ResVip - 返回玩家vip信息
 */
public class ResVipMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=1008100;
	//模块号
	public static final int FUNCTION_ID=1008;
	//消息号
	public static final int MSG_ID=100;
	
	/**玩家vip信息*/
	@MsgField(Id = 1)
	public VipBean vipBean;
		
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
		buf.append("vipBean:" + vipBean +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}