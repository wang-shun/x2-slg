package com.xgame.logic.server.game.allianceext.message;
import com.xgame.logic.server.game.allianceext.bean.MarchInfo;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * AllianceExt-ResViewAllianceExplorer - 返回超级矿采集的玩家
 */
public class ResViewAllianceExplorerMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=1210109;
	//模块号
	public static final int FUNCTION_ID=1210;
	//消息号
	public static final int MSG_ID=109;
	
	/**采集列表*/
	@MsgField(Id = 1)
	public List<MarchInfo> marchInfoList = new ArrayList<MarchInfo>();
	/**资源数量*/
	@MsgField(Id = 2)
	public long resourceLeft;
		
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
		buf.append("marchInfoList:{");
		for (int i = 0; i < marchInfoList.size(); i++) {
			buf.append(marchInfoList.get(i).toString() +",");
		}
		buf.append("resourceLeft:" + resourceLeft +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}