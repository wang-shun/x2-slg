package com.xgame.logic.server.game.alliance.message;
import java.util.ArrayList;
import java.util.List;

import com.xgame.logic.server.game.alliance.bean.AllianceFightInfoBean;
import com.xgame.msglib.ResMessage;
import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Alliance-ResAllianceFightList - 获取军情列表
 */
public class ResAllianceFightListMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=1007130;
	//模块号
	public static final int FUNCTION_ID=1007;
	//消息号
	public static final int MSG_ID=130;
	
	/**联盟id*/
	@MsgField(Id = 1)
	public List<AllianceFightInfoBean> allianceFightInfo = new ArrayList<AllianceFightInfoBean>();
		
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
		buf.append("allianceFightInfo:{");
		for (int i = 0; i < allianceFightInfo.size(); i++) {
			buf.append(allianceFightInfo.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}