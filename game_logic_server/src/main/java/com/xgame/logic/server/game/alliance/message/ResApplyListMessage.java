package com.xgame.logic.server.game.alliance.message;
import com.xgame.logic.server.game.alliance.bean.AllianceApplyBean;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Alliance-ResApplyList - 返回申请列表
 */
public class ResApplyListMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=1007126;
	//模块号
	public static final int FUNCTION_ID=1007;
	//消息号
	public static final int MSG_ID=126;
	
	/**联盟角标标记时间*/
	@MsgField(Id = 1)
	public long applyTagTime;
	/**待通过玩家信息*/
	@MsgField(Id = 2)
	public List<AllianceApplyBean> applyPlayerList = new ArrayList<AllianceApplyBean>();
		
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
		buf.append("applyTagTime:" + applyTagTime +",");
		buf.append("applyPlayerList:{");
		for (int i = 0; i < applyPlayerList.size(); i++) {
			buf.append(applyPlayerList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}