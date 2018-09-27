package com.xgame.logic.server.game.world.message;
import com.xgame.logic.server.game.world.bean.VectorInfo;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * World-ResUseTroopSpeedUpProp - 返回加速信息
 */
public class ResUseTroopSpeedUpPropMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=121121;
	//模块号
	public static final int FUNCTION_ID=121;
	//消息号
	public static final int MSG_ID=121;
	
	/**返回行军队列信息*/
	@MsgField(Id = 1)
	public VectorInfo vectorInfo;
		
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
		buf.append("vectorInfo:" + vectorInfo +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}