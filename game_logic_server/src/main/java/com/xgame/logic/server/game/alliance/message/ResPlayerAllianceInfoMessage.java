package com.xgame.logic.server.game.alliance.message;
import java.util.ArrayList;
import java.util.List;

import com.xgame.logic.server.game.alliance.bean.SimpleAllianceBean;
import com.xgame.msglib.ResMessage;
import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Alliance-ResPlayerAllianceInfo - 返回创建成功
 */
public class ResPlayerAllianceInfoMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=1007106;
	//模块号
	public static final int FUNCTION_ID=1007;
	//消息号
	public static final int MSG_ID=106;
	
	/**申请列表*/
	@MsgField(Id = 1)
	public List<SimpleAllianceBean> applyList = new ArrayList<SimpleAllianceBean>();
	/**申请列表*/
	@MsgField(Id = 2)
	public List<SimpleAllianceBean> inviteList = new ArrayList<SimpleAllianceBean>();
		
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
		buf.append("applyList:{");
		for (int i = 0; i < applyList.size(); i++) {
			buf.append(applyList.get(i).toString() +",");
		}
		buf.append("inviteList:{");
		for (int i = 0; i < inviteList.size(); i++) {
			buf.append(inviteList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}