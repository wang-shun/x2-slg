package com.xgame.logic.server.game.soldier.message;
import java.util.ArrayList;
import java.util.List;

import com.xgame.logic.server.game.soldier.bean.BuildingDefenSoldierBean;
import com.xgame.logic.server.game.soldier.bean.ReformSoldierBean;
import com.xgame.logic.server.game.soldier.bean.SoldierBean;
import com.xgame.msglib.ResMessage;
import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Soldier-ResUpdateSoldier - 更新兵信息
 */
public class ResUpdateSoldierMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=102150;
	//模块号
	public static final int FUNCTION_ID=102;
	//消息号
	public static final int MSG_ID=150;
	
	/**军营士兵*/
	@MsgField(Id = 1)
	public List<SoldierBean> campSoldierList = new ArrayList<SoldierBean>();
	/**受伤士兵*/
	@MsgField(Id = 2)
	public List<SoldierBean> hurtSoldierList = new ArrayList<SoldierBean>();
	/**驻防士兵*/
	@MsgField(Id = 3)
	public List<BuildingDefenSoldierBean> defenSoldierList = new ArrayList<BuildingDefenSoldierBean>();
	/**改装士兵*/
	@MsgField(Id = 4)
	public List<ReformSoldierBean> reformSoldierList = new ArrayList<ReformSoldierBean>();
	/**执行任务的兵*/
	@MsgField(Id = 5)
	public List<SoldierBean> runSoldierList = new ArrayList<SoldierBean>();
	/**可收取的士兵*/
	@MsgField(Id = 6)
	public List<SoldierBean> collectSoldierList = new ArrayList<SoldierBean>();
		
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
		buf.append("campSoldierList:{");
		for (int i = 0; i < campSoldierList.size(); i++) {
			buf.append(campSoldierList.get(i).toString() +",");
		}
		buf.append("hurtSoldierList:{");
		for (int i = 0; i < hurtSoldierList.size(); i++) {
			buf.append(hurtSoldierList.get(i).toString() +",");
		}
		buf.append("defenSoldierList:{");
		for (int i = 0; i < defenSoldierList.size(); i++) {
			buf.append(defenSoldierList.get(i).toString() +",");
		}
		buf.append("reformSoldierList:{");
		for (int i = 0; i < reformSoldierList.size(); i++) {
			buf.append(reformSoldierList.get(i).toString() +",");
		}
		buf.append("runSoldierList:{");
		for (int i = 0; i < runSoldierList.size(); i++) {
			buf.append(runSoldierList.get(i).toString() +",");
		}
		buf.append("collectSoldierList:{");
		for (int i = 0; i < collectSoldierList.size(); i++) {
			buf.append(collectSoldierList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}