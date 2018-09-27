package com.xgame.logic.server.game.country.message;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Country-ResMineRepository - 返回矿场采集
 */
public class ResMineRepositoryMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=100105;
	//模块号
	public static final int FUNCTION_ID=100;
	//消息号
	public static final int MSG_ID=105;
	
	/**石油*/
	@MsgField(Id = 1)
	public long oil;
	/**钢铁*/
	@MsgField(Id = 2)
	public long steel;
	/**稀土*/
	@MsgField(Id = 3)
	public long rare;
	/**金钱*/
	@MsgField(Id = 4)
	public long money;
		
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
		buf.append("oil:" + oil +",");
		buf.append("steel:" + steel +",");
		buf.append("rare:" + rare +",");
		buf.append("money:" + money +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}