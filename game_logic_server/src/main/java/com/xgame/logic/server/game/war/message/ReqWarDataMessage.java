package com.xgame.logic.server.game.war.message;
import com.xgame.logic.server.game.world.bean.WorldSimpleSoldierBean;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * War-ReqWarData - 请求战斗
 */
public class ReqWarDataMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=500202;
	//模块号
	public static final int FUNCTION_ID=500;
	//消息号
	public static final int MSG_ID=202;
	
	/**敌方Uid*/
	@MsgField(Id = 1)
	public long enemyUid;
	/**带燃油数量*/
	@MsgField(Id = 2)
	public int oilNum;
	/**进攻方士兵列表*/
	@MsgField(Id = 3)
	public List<WorldSimpleSoldierBean> soldiers = new ArrayList<WorldSimpleSoldierBean>();
		
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
		buf.append("enemyUid:" + enemyUid +",");
		buf.append("oilNum:" + oilNum +",");
		buf.append("soldiers:{");
		for (int i = 0; i < soldiers.size(); i++) {
			buf.append(soldiers.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}