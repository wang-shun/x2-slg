package com.xgame.logic.server.game.commander.message;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Commander-ResLevelUpCommander - 升级刷新
 */
public class ResLevelUpCommanderMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=130102;
	//模块号
	public static final int FUNCTION_ID=130;
	//消息号
	public static final int MSG_ID=102;
	
	/**等级*/
	@MsgField(Id = 1)
	public int level;
	/**指挥官经验*/
	@MsgField(Id = 2)
	public long exp;
	/**体能*/
	@MsgField(Id = 3)
	public int energy;
	/**已有天赋点数*/
	@MsgField(Id = 4)
	public int talentPoints;
		
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
		buf.append("level:" + level +",");
		buf.append("exp:" + exp +",");
		buf.append("energy:" + energy +",");
		buf.append("talentPoints:" + talentPoints +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}