package com.xgame.logic.server.game.armshop.message;
import com.xgame.logic.server.game.soldier.bean.SoldierBean;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Armshop-ResCampCreate - 创建建筑物
 */
public class ResCampCreateMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=101103;
	//模块号
	public static final int FUNCTION_ID=101;
	//消息号
	public static final int MSG_ID=103;
	
	/**唯一id*/
	@MsgField(Id = 1)
	public int campType;
	/**1生产 2是销毁*/
	@MsgField(Id = 2)
	public boolean mtype;
	/**0快速使用, 1是使用*/
	@MsgField(Id = 3)
	public SoldierBean soldierData;
		
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
		buf.append("campType:" + campType +",");
		buf.append("mtype:" + mtype +",");
		buf.append("soldierData:" + soldierData +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}