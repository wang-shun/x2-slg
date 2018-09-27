package com.xgame.logic.server.game.player.message;
import com.xgame.msglib.ResMessage;
import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Player-ResCurrency - 
 */
public class ResCurrencyMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=1003100;
	//模块号
	public static final int FUNCTION_ID=1003;
	//消息号
	public static final int MSG_ID=100;
	
	/***/
	@MsgField(Id = 1)
	public long resSY;
	/***/
	@MsgField(Id = 2)
	public long resXT;
	/***/
	@MsgField(Id = 3)
	public long resGC;
	/***/
	@MsgField(Id = 4)
	public long resCP;
	/***/
	@MsgField(Id = 5)
	public long resZS;
	/***/
	@MsgField(Id = 6)
	public long fightPower;
		
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
		buf.append("resSY:" + resSY +",");
		buf.append("resXT:" + resXT +",");
		buf.append("resGC:" + resGC +",");
		buf.append("resCP:" + resCP +",");
		buf.append("resZS:" + resZS +",");
		buf.append("fightPower:" + fightPower +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}