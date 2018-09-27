package com.xgame.logic.server.game.modify.message;
import com.xgame.logic.server.game.modify.bean.ArmorsBean;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Modify-ResArmorsInfo - 返回装甲信息
 */
public class ResArmorsInfoMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=601104;
	//模块号
	public static final int FUNCTION_ID=601;
	//消息号
	public static final int MSG_ID=104;
	
	/**唯一id*/
	@MsgField(Id = 1)
	public ArmorsBean armorBeans;
		
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
		buf.append("armorBeans:" + armorBeans +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}