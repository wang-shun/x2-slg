package com.xgame.logic.server.game.country.message;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Country-ReqRewardRepository - 领取临时仓库资源
 */
public class ReqRewardRepositoryMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=100208;
	//模块号
	public static final int FUNCTION_ID=100;
	//消息号
	public static final int MSG_ID=208;
	
	/**资源类型*/
	@MsgField(Id = 1)
	public List<Integer> resourceId = new ArrayList<Integer>();
		
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
		return Communicationable.HandlerEnum.CS;
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("resourceId:{");
		for (int i = 0; i < resourceId.size(); i++) {
			buf.append(resourceId.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}