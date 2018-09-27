package com.xgame.logic.server.game.world.message;
import com.xgame.logic.server.game.world.bean.TeamAttackBean;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * World-ResGetTeamBattleList - 返回集结进攻列表
 */
public class ResGetTeamBattleListMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=121129;
	//模块号
	public static final int FUNCTION_ID=121;
	//消息号
	public static final int MSG_ID=129;
	
	/**1进攻2防御*/
	@MsgField(Id = 1)
	public int type;
	/**集结进攻列表*/
	@MsgField(Id = 2)
	public List<TeamAttackBean> attackTeamList = new ArrayList<TeamAttackBean>();
		
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
		buf.append("type:" + type +",");
		buf.append("attackTeamList:{");
		for (int i = 0; i < attackTeamList.size(); i++) {
			buf.append(attackTeamList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}