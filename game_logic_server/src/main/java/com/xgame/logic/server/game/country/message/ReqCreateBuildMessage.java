package com.xgame.logic.server.game.country.message;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Country-ReqCreateBuild - 创建建筑物
 */
public class ReqCreateBuildMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=100200;
	//模块号
	public static final int FUNCTION_ID=100;
	//消息号
	public static final int MSG_ID=200;
	
	/**唯一id*/
	@MsgField(Id = 1)
	public int uid;
	/**配置id*/
	@MsgField(Id = 2)
	public int sid;
	/**x*/
	@MsgField(Id = 3)
	public int x;
	/**y*/
	@MsgField(Id = 4)
	public int y;
	/**是否立即创建*/
	@MsgField(Id = 5)
	public int createType;
	/**阵型id*/
	@MsgField(Id = 6)
	public int templateId;
		
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
		buf.append("x:" + x +",");
		buf.append("y:" + y +",");
		buf.append("createType:" + createType +",");
		buf.append("templateId:" + templateId +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}