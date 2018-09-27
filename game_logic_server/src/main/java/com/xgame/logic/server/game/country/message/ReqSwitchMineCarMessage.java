package com.xgame.logic.server.game.country.message;
import com.xgame.logic.server.game.country.bean.SwitchCarBean;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Country-ReqSwitchMineCar - 切换矿车
 */
public class ReqSwitchMineCarMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=100207;
	//模块号
	public static final int FUNCTION_ID=100;
	//消息号
	public static final int MSG_ID=207;
	
	/***/
	@MsgField(Id = 1)
	public List<SwitchCarBean> switchCarBean = new ArrayList<SwitchCarBean>();
		
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
		buf.append("switchCarBean:{");
		for (int i = 0; i < switchCarBean.size(); i++) {
			buf.append(switchCarBean.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}