package com.xgame.logic.server.game.playerattribute.message;
import com.xgame.logic.server.game.playerattribute.bean.AttrbuteAppenderListBean;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Playerattribute-ResAttrbutesAddFrom - 返回属性加成来自模块
 */
public class ResAttrbutesAddFromMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=111102;
	//模块号
	public static final int FUNCTION_ID=111;
	//消息号
	public static final int MSG_ID=102;
	
	/**属性来源模块*/
	@MsgField(Id = 1)
	public List<AttrbuteAppenderListBean> list = new ArrayList<AttrbuteAppenderListBean>();
		
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
		buf.append("list:{");
		for (int i = 0; i < list.size(); i++) {
			buf.append(list.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}