package com.xgame.logic.server.game.customweanpon.message;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * CustomWeanpon-ResPartList - 返回配件列表
 */
public class ResPartListMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=600103;
	//模块号
	public static final int FUNCTION_ID=600;
	//消息号
	public static final int MSG_ID=103;
	
	/**配件ID列表*/
	@MsgField(Id = 1)
	public List<Integer> partIdList = new ArrayList<Integer>();
		
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
		buf.append("partIdList:{");
		for (int i = 0; i < partIdList.size(); i++) {
			buf.append(partIdList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}