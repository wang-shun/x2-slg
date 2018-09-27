package com.xgame.logic.server.game.customweanpon.message;
import com.xgame.logic.server.game.customweanpon.bean.DesignMapBean;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * CustomWeanpon-ResChangeDesignMap - 请求生产图纸返回
 */
public class ResChangeDesignMapMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=600102;
	//模块号
	public static final int FUNCTION_ID=600;
	//消息号
	public static final int MSG_ID=102;
	
	/**图纸名称*/
	@MsgField(Id = 1)
	public String name;
	/**图纸*/
	@MsgField(Id = 2)
	public DesignMapBean designMap;
		
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
		buf.append("name:" + name +",");
		buf.append("designMap:" + designMap +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}