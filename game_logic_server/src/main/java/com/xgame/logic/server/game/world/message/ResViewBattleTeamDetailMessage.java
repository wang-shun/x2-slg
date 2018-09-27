package com.xgame.logic.server.game.world.message;
import com.xgame.logic.server.game.world.bean.TeamPlayerBean;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * World-ResViewBattleTeamDetail - 返回牵集结进攻详细信息
 */
public class ResViewBattleTeamDetailMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=121130;
	//模块号
	public static final int FUNCTION_ID=121;
	//消息号
	public static final int MSG_ID=130;
	
	/**队伍id*/
	@MsgField(Id = 1)
	public String teamId;
	/**类型1进攻2防御*/
	@MsgField(Id = 2)
	public int type;
	/**集结部队信息列表*/
	@MsgField(Id = 3)
	public List<TeamPlayerBean> teamPlayerList = new ArrayList<TeamPlayerBean>();
		
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
		buf.append("teamId:" + teamId +",");
		buf.append("type:" + type +",");
		buf.append("teamPlayerList:{");
		for (int i = 0; i < teamPlayerList.size(); i++) {
			buf.append(teamPlayerList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}