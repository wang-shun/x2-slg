package com.xgame.logic.server.game.allianceext.message;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * AllianceExt-ReqAssignReward - 分配奖励信息
 */
public class ReqAssignRewardMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=1210217;
	//模块号
	public static final int FUNCTION_ID=1210;
	//消息号
	public static final int MSG_ID=217;
	
	/**联盟Id*/
	@MsgField(Id = 1)
	public long allianceId;
	/**战事奖励ID*/
	@MsgField(Id = 2)
	public int itemId;
	/**数量*/
	@MsgField(Id = 3)
	public int num;
	/**玩家id列表*/
	@MsgField(Id = 4)
	public List<Long> playerId = new ArrayList<Long>();
		
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
		buf.append("allianceId:" + allianceId +",");
		buf.append("itemId:" + itemId +",");
		buf.append("num:" + num +",");
		buf.append("playerId:{");
		for (int i = 0; i < playerId.size(); i++) {
			buf.append(playerId.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}