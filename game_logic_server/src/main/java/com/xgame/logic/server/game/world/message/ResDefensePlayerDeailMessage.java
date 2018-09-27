package com.xgame.logic.server.game.world.message;
import com.xgame.logic.server.game.war.bean.WarSoldierBean;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * World-ResDefensePlayerDeail - 返回防御驻军详细信息
 */
public class ResDefensePlayerDeailMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=121132;
	//模块号
	public static final int FUNCTION_ID=121;
	//消息号
	public static final int MSG_ID=132;
	
	/**玩家士兵信息*/
	@MsgField(Id = 1)
	public List<WarSoldierBean> warSoldierList = new ArrayList<WarSoldierBean>();
		
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
		buf.append("warSoldierList:{");
		for (int i = 0; i < warSoldierList.size(); i++) {
			buf.append(warSoldierList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}