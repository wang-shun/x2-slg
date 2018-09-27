package com.xgame.logic.server.game.player.message;
import com.xgame.msglib.ReqMessage;
import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Player-ReqLogin - 登录消息
 */
public class ReqLoginMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=1003204;
	//模块号
	public static final int FUNCTION_ID=1003;
	//消息号
	public static final int MSG_ID=204;
	
	/***/
	@MsgField(Id = 1)
	public String username;
	/***/
	@MsgField(Id = 2)
	public String password;
	/***/
	@MsgField(Id = 3)
	public long roleid;
		
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
		buf.append("username:" + username +",");
		buf.append("password:" + password +",");
		buf.append("roleid:" + roleid +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}