package com.xgame.logic.server.game.modify.message;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Modify-ResReformSoldier - 请求改造 返回
 */
public class ResReformSoldierMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=601102;
	//模块号
	public static final int FUNCTION_ID=601;
	//消息号
	public static final int MSG_ID=102;
	
	/**1成功0失败*/
	@MsgField(Id = 1)
	public int isSuccess;
	/**唯一id*/
	@MsgField(Id = 2)
	public long id;
	/**数量*/
	@MsgField(Id = 3)
	public int num;
		
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
		buf.append("id:" + id +",");
		buf.append("num:" + num +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}