package com.xgame.logic.server.game.world.message;
import com.xgame.logic.server.game.world.bean.WorldTerritoryBean;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * World-ResViewWorldTerritory - 返回查询领地信息
 */
public class ResViewWorldTerritoryMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=121162;
	//模块号
	public static final int FUNCTION_ID=121;
	//消息号
	public static final int MSG_ID=162;
	
	/**查询类型1查询返回2.领地变更*/
	@MsgField(Id = 1)
	public int queryType;
	/**领地信息*/
	@MsgField(Id = 2)
	public List<WorldTerritoryBean> territoryBean = new ArrayList<WorldTerritoryBean>();
		
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
		buf.append("queryType:" + queryType +",");
		buf.append("territoryBean:{");
		for (int i = 0; i < territoryBean.size(); i++) {
			buf.append(territoryBean.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}