package com.xgame.logic.server.game.playerattribute.message;
import com.xgame.logic.server.game.playerattribute.bean.AttributeNodeBean;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Playerattribute-ResAttrbutesAdd - 返回属性加成列表
 */
public class ResAttrbutesAddMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=111101;
	//模块号
	public static final int FUNCTION_ID=111;
	//消息号
	public static final int MSG_ID=101;
	
	/**属性节点列表*/
	@MsgField(Id = 1)
	public List<AttributeNodeBean> attributeNodes = new ArrayList<AttributeNodeBean>();
		
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
		buf.append("attributeNodes:{");
		for (int i = 0; i < attributeNodes.size(); i++) {
			buf.append(attributeNodes.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}