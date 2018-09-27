package com.xgame.logic.server.game.war.message;
import com.xgame.logic.server.game.war.bean.WarSoldierBean;
import com.xgame.logic.server.game.war.bean.DefendSoldierBean;
import com.xgame.logic.server.game.war.bean.WarResourceBean;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * War-ResWarEnd - 返回战斗结束
 */
public class ResWarEndMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=500103;
	//模块号
	public static final int FUNCTION_ID=500;
	//消息号
	public static final int MSG_ID=103;
	
	/**获胜一方id*/
	@MsgField(Id = 1)
	public long winUid;
	/**摧毁程度：0-100*/
	@MsgField(Id = 2)
	public int destroyLevel;
	/**进攻方伤兵情况*/
	@MsgField(Id = 3)
	public List<WarSoldierBean> attackSoldierBean = new ArrayList<WarSoldierBean>();
	/**防守方士兵*/
	@MsgField(Id = 4)
	public List<DefendSoldierBean> defendSoldierBean = new ArrayList<DefendSoldierBean>();
	/**资源奖励*/
	@MsgField(Id = 5)
	public List<WarResourceBean> warResourceBean = new ArrayList<WarResourceBean>();
	/**奖励（id_num,id_num...）*/
	@MsgField(Id = 6)
	public String rewards;
		
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
		buf.append("winUid:" + winUid +",");
		buf.append("destroyLevel:" + destroyLevel +",");
		buf.append("attackSoldierBean:{");
		for (int i = 0; i < attackSoldierBean.size(); i++) {
			buf.append(attackSoldierBean.get(i).toString() +",");
		}
		buf.append("defendSoldierBean:{");
		for (int i = 0; i < defendSoldierBean.size(); i++) {
			buf.append(defendSoldierBean.get(i).toString() +",");
		}
		buf.append("warResourceBean:{");
		for (int i = 0; i < warResourceBean.size(); i++) {
			buf.append(warResourceBean.get(i).toString() +",");
		}
		buf.append("rewards:" + rewards +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}