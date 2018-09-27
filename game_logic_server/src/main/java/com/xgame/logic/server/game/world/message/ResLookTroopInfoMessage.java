package com.xgame.logic.server.game.world.message;
import com.xgame.logic.server.game.war.bean.WarSoldierBean;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * World-ResLookTroopInfo - 返回部队信息
 */
public class ResLookTroopInfoMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=121105;
	//模块号
	public static final int FUNCTION_ID=121;
	//消息号
	public static final int MSG_ID=105;
	
	/**返回士兵信息列表*/
	@MsgField(Id = 1)
	public List<WarSoldierBean> soldierBeanList = new ArrayList<WarSoldierBean>();
		
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
		buf.append("soldierBeanList:{");
		for (int i = 0; i < soldierBeanList.size(); i++) {
			buf.append(soldierBeanList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}