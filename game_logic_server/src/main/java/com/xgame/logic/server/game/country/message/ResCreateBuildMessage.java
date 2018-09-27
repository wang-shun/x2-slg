package com.xgame.logic.server.game.country.message;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Country-ResCreateBuild - 回复创建建筑物
 */
public class ResCreateBuildMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=100100;
	//模块号
	public static final int FUNCTION_ID=100;
	//消息号
	public static final int MSG_ID=100;
	
	/**客户端发来的唯一id*/
	@MsgField(Id = 1)
	public int clientGenUid;
	/**服务器生成的唯一id*/
	@MsgField(Id = 2)
	public int serverGenUid;
	/**配置id*/
	@MsgField(Id = 3)
	public int sid;
	/***/
	@MsgField(Id = 4)
	public int x;
	/***/
	@MsgField(Id = 5)
	public int y;
	/**是否成功*/
	@MsgField(Id = 6)
	public int isSuccess;
		
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
		buf.append("clientGenUid:" + clientGenUid +",");
		buf.append("serverGenUid:" + serverGenUid +",");
		buf.append("sid:" + sid +",");
		buf.append("x:" + x +",");
		buf.append("y:" + y +",");
		buf.append("isSuccess:" + isSuccess +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}