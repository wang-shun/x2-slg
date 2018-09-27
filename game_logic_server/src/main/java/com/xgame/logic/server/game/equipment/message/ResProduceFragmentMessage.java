package com.xgame.logic.server.game.equipment.message;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Equipment-ResProduceFragment - 是否开始生产
 */
public class ResProduceFragmentMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=300102;
	//模块号
	public static final int FUNCTION_ID=300;
	//消息号
	public static final int MSG_ID=102;
	
	/**是否成功*/
	@MsgField(Id = 1)
	public boolean isSuccess;
	/**建筑id*/
	@MsgField(Id = 2)
	public Integer buildId;
	/**材料ID*/
	@MsgField(Id = 3)
	public List<Integer> fragmentID = new ArrayList<Integer>();
		
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
		buf.append("isSuccess:" + isSuccess +",");
		buf.append("buildId:" + buildId +",");
		buf.append("fragmentID:{");
		for (int i = 0; i < fragmentID.size(); i++) {
			buf.append(fragmentID.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}