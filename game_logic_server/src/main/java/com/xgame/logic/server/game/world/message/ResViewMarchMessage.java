package com.xgame.logic.server.game.world.message;
import com.xgame.logic.server.game.world.bean.VectorInfo;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * World-ResViewMarch - 返回周围行军信息
 */
public class ResViewMarchMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=121102;
	//模块号
	public static final int FUNCTION_ID=121;
	//消息号
	public static final int MSG_ID=102;
	
	/**地图行军信息*/
	@MsgField(Id = 1)
	public List<VectorInfo> vectorInfoList = new ArrayList<VectorInfo>();
		
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
		buf.append("vectorInfoList:{");
		for (int i = 0; i < vectorInfoList.size(); i++) {
			buf.append(vectorInfoList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}