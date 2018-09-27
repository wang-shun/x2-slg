package com.xgame.logic.server.game.allianceext.message;
import com.xgame.logic.server.game.allianceext.bean.AllianceBoxBean;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * AllianceExt-ResAllianceBox - 返回联盟宝箱
 */
public class ResAllianceBoxMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=1210113;
	//模块号
	public static final int FUNCTION_ID=1210;
	//消息号
	public static final int MSG_ID=113;
	
	/**宝箱信息*/
	@MsgField(Id = 1)
	public List<AllianceBoxBean> allianceBoxBean = new ArrayList<AllianceBoxBean>();
	/**宝箱等级*/
	@MsgField(Id = 2)
	public int boxLevel;
	/**当前经验*/
	@MsgField(Id = 3)
	public int exp;
		
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
		buf.append("allianceBoxBean:{");
		for (int i = 0; i < allianceBoxBean.size(); i++) {
			buf.append(allianceBoxBean.get(i).toString() +",");
		}
		buf.append("boxLevel:" + boxLevel +",");
		buf.append("exp:" + exp +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}