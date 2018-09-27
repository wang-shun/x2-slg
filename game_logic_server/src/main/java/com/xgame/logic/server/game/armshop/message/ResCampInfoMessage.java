package com.xgame.logic.server.game.armshop.message;
import com.xgame.logic.server.game.soldier.bean.SoldierBean;
import com.xgame.logic.server.game.armshop.bean.CampDataPro;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Armshop-ResCampInfo - 创建建筑物
 */
public class ResCampInfoMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=101104;
	//模块号
	public static final int FUNCTION_ID=101;
	//消息号
	public static final int MSG_ID=104;
	
	/***/
	@MsgField(Id = 1)
	public List<SoldierBean> soldierBeans = new ArrayList<SoldierBean>();
	/***/
	@MsgField(Id = 2)
	public List<CampDataPro> campDatas = new ArrayList<CampDataPro>();
		
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
		buf.append("soldierBeans:{");
		for (int i = 0; i < soldierBeans.size(); i++) {
			buf.append(soldierBeans.get(i).toString() +",");
		}
		buf.append("campDatas:{");
		for (int i = 0; i < campDatas.size(); i++) {
			buf.append(campDatas.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}