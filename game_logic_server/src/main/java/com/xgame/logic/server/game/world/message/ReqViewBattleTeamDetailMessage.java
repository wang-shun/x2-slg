package com.xgame.logic.server.game.world.message;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * World-ReqViewBattleTeamDetail - 获取集结进攻详细信息
 */
public class ReqViewBattleTeamDetailMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=121230;
	//模块号
	public static final int FUNCTION_ID=121;
	//消息号
	public static final int MSG_ID=230;
	
	/**队伍id*/
	@MsgField(Id = 1)
	public String teamId;
	/**类型1进攻2防御*/
	@MsgField(Id = 2)
	public int type;
		
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
		buf.append("teamId:" + teamId +",");
		buf.append("type:" + type +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}