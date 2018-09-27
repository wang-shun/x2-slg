package com.xgame.logic.server.game.allianceext.message;
import com.xgame.logic.server.game.allianceext.bean.AllianceAcitivityBean;
import com.xgame.logic.server.game.allianceext.bean.AllianceAcitivityQuestBean;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * AllianceExt-ResAllianceAcitivity - 返回联盟活跃面板
 */
public class ResAllianceAcitivityMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=1210110;
	//模块号
	public static final int FUNCTION_ID=1210;
	//消息号
	public static final int MSG_ID=110;
	
	/**活跃度信息*/
	@MsgField(Id = 1)
	public AllianceAcitivityBean allianceAcitivityBean;
	/**活跃度任务信息*/
	@MsgField(Id = 2)
	public List<AllianceAcitivityQuestBean> allianceAcitivityQuestBean = new ArrayList<AllianceAcitivityQuestBean>();
		
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
		buf.append("allianceAcitivityBean:" + allianceAcitivityBean +",");
		buf.append("allianceAcitivityQuestBean:{");
		for (int i = 0; i < allianceAcitivityQuestBean.size(); i++) {
			buf.append(allianceAcitivityQuestBean.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}