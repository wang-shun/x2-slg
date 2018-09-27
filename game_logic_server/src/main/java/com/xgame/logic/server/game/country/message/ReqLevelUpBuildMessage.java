package com.xgame.logic.server.game.country.message;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Country-ReqLevelUpBuild - 移除建筑物
 */
public class ReqLevelUpBuildMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=100204;
	//模块号
	public static final int FUNCTION_ID=100;
	//消息号
	public static final int MSG_ID=204;
	
	/**唯一id*/
	@MsgField(Id = 1)
	public int uid;
	/***/
	@MsgField(Id = 2)
	public int sid;
	/**是否立即创建*/
	@MsgField(Id = 3)
	public int createType;
		
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
		buf.append("uid:" + uid +",");
		buf.append("sid:" + sid +",");
		buf.append("createType:" + createType +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}