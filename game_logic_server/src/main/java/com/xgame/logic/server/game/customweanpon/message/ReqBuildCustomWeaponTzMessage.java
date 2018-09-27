package com.xgame.logic.server.game.customweanpon.message;
import com.xgame.logic.server.game.customweanpon.bean.CustomWeaponTZ;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * CustomWeanpon-ReqBuildCustomWeaponTz - 请求生产蓝图
 */
public class ReqBuildCustomWeaponTzMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=600200;
	//模块号
	public static final int FUNCTION_ID=600;
	//消息号
	public static final int MSG_ID=200;
	
	/***/
	@MsgField(Id = 1)
	public int buildType;
	/***/
	@MsgField(Id = 2)
	public CustomWeaponTZ fragments;
		
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
		buf.append("buildType:" + buildType +",");
		buf.append("fragments:" + fragments +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}