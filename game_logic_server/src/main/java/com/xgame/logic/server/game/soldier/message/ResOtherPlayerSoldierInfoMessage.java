package com.xgame.logic.server.game.soldier.message;
import com.xgame.logic.server.game.soldier.bean.FullSoldierBean;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Soldier-ResOtherPlayerSoldierInfo - 返回其他玩家兵信息
 */
public class ResOtherPlayerSoldierInfoMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=102101;
	//模块号
	public static final int FUNCTION_ID=102;
	//消息号
	public static final int MSG_ID=101;
	
	/**全士兵信息*/
	@MsgField(Id = 1)
	public FullSoldierBean soldier;
		
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
		buf.append("soldier:" + soldier +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}