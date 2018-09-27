package com.xgame.logic.server.game.radar.message;
import com.xgame.logic.server.game.war.bean.WarBuilding;
import com.xgame.logic.server.game.war.bean.DefendSoldierBean;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Radar-ResInvestigateInfo - 雷达侦查信息
 */
public class ResInvestigateInfoMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=301401;
	//模块号
	public static final int FUNCTION_ID=301;
	//消息号
	public static final int MSG_ID=401;
	
	/**出征最大数量*/
	@MsgField(Id = 1)
	public int marchMaxNum;
	/**敌方建筑集*/
	@MsgField(Id = 2)
	public List<WarBuilding> enemyBuilds = new ArrayList<WarBuilding>();
	/**敌方士兵集*/
	@MsgField(Id = 3)
	public List<DefendSoldierBean> enemySoldiers = new ArrayList<DefendSoldierBean>();
		
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
		buf.append("marchMaxNum:" + marchMaxNum +",");
		buf.append("enemyBuilds:{");
		for (int i = 0; i < enemyBuilds.size(); i++) {
			buf.append(enemyBuilds.get(i).toString() +",");
		}
		buf.append("enemySoldiers:{");
		for (int i = 0; i < enemySoldiers.size(); i++) {
			buf.append(enemySoldiers.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}