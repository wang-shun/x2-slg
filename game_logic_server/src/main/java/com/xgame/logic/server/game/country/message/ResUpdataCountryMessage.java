package com.xgame.logic.server.game.country.message;
import com.xgame.logic.server.game.country.bean.UpdataBuildPro;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Country-ResUpdataCountry - 更新建筑物消息
 */
public class ResUpdataCountryMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=100109;
	//模块号
	public static final int FUNCTION_ID=100;
	//消息号
	public static final int MSG_ID=109;
	
	/**刷新的建筑物集合*/
	@MsgField(Id = 1)
	public List<UpdataBuildPro> updataBuilds = new ArrayList<UpdataBuildPro>();
		
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
		buf.append("updataBuilds:{");
		for (int i = 0; i < updataBuilds.size(); i++) {
			buf.append(updataBuilds.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}