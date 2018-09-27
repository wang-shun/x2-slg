package com.xgame.logic.server.game.country.message;
import com.xgame.logic.server.game.country.bean.SwitchCarBean;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Country-ResSwitchCar - 返回切换成功
 */
public class ResSwitchCarMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=100107;
	//模块号
	public static final int FUNCTION_ID=100;
	//消息号
	public static final int MSG_ID=107;
	
	/***/
	@MsgField(Id = 1)
	public List<SwitchCarBean> switchCarResult = new ArrayList<SwitchCarBean>();
		
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
		buf.append("switchCarResult:{");
		for (int i = 0; i < switchCarResult.size(); i++) {
			buf.append(switchCarResult.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}