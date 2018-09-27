package com.xgame.logic.server.game.world.message;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.war.bean.WarResourceBean;
import com.xgame.logic.server.game.world.bean.WorldSimpleSoldierBean;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * World-ReqMarch - 请求出征
 */
public class ReqMarchMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=121204;
	//模块号
	public static final int FUNCTION_ID=121;
	//消息号
	public static final int MSG_ID=204;
	
	/**类型（1.采集 2.扎营3占领4攻打玩家）*/
	@MsgField(Id = 1)
	public int type;
	/**目标格子*/
	@MsgField(Id = 2)
	public Vector2Bean target;
	/**石油信息*/
	@MsgField(Id = 3)
	public int oil;
	/**队伍编号*/
	@MsgField(Id = 4)
	public String teamId;
	/**集结时间*/
	@MsgField(Id = 5)
	public int remainTime;
	/**运送资源信息*/
	@MsgField(Id = 6)
	public WarResourceBean resource;
	/**士兵简易信息*/
	@MsgField(Id = 7)
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
		buf.append("type:" + type +",");
		buf.append("target:" + target +",");
		buf.append("oil:" + oil +",");
		buf.append("teamId:" + teamId +",");
		buf.append("remainTime:" + remainTime +",");
		buf.append("resource:" + resource +",");
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