package com.xgame.logic.server.game.world.message;
import com.xgame.logic.server.game.world.bean.TerritoryBean;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * World-ResQueryTerritory - 返回查询的领土信息
 */
public class ResQueryTerritoryMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=121161;
	//模块号
	public static final int FUNCTION_ID=121;
	//消息号
	public static final int MSG_ID=161;
	
	/**1(1-10名)2(11-20)3(21-30)*/
	@MsgField(Id = 1)
	public List<TerritoryBean> territoryBean = new ArrayList<TerritoryBean>();
		
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