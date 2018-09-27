package com.xgame.logic.server.game.allianceext.message;
import com.xgame.logic.server.game.allianceext.bean.AllianceActivityViewBean;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * AllianceExt-ResAcitivityRank - 返回活跃度排行
 */
public class ResAcitivityRankMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=1210111;
	//模块号
	public static final int FUNCTION_ID=1210;
	//消息号
	public static final int MSG_ID=111;
	
	/**排行信息*/
	@MsgField(Id = 1)
	public List<AllianceActivityViewBean> alliancePlayerView = new ArrayList<AllianceActivityViewBean>();
		
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
		buf.append("alliancePlayerView:{");
		for (int i = 0; i < alliancePlayerView.size(); i++) {
			buf.append(alliancePlayerView.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}