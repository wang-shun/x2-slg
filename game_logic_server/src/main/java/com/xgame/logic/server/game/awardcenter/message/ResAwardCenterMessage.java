package com.xgame.logic.server.game.awardcenter.message;
import com.xgame.logic.server.game.awardcenter.bean.AwardPro;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * AwardCenter-ResAwardCenter - 
 */
public class ResAwardCenterMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=200100;
	//模块号
	public static final int FUNCTION_ID=200;
	//消息号
	public static final int MSG_ID=100;
	
	/***/
	@MsgField(Id = 1)
	public List<AwardPro> awardList = new ArrayList<AwardPro>();
		
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
		buf.append("awardList:{");
		for (int i = 0; i < awardList.size(); i++) {
			buf.append(awardList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}