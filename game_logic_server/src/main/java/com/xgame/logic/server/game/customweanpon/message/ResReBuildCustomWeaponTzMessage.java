package com.xgame.logic.server.game.customweanpon.message;
import com.xgame.logic.server.game.customweanpon.bean.CustomWeaponPJ;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * CustomWeanpon-ResReBuildCustomWeaponTz - 请求生产蓝图
 */
public class ResReBuildCustomWeaponTzMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=600100;
	//模块号
	public static final int FUNCTION_ID=600;
	//消息号
	public static final int MSG_ID=100;
	
	/***/
	@MsgField(Id = 1)
	public int soldierId;
	/***/
	@MsgField(Id = 2)
	public List<CustomWeaponPJ> fragments = new ArrayList<CustomWeaponPJ>();
		
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
		buf.append("soldierId:" + soldierId +",");
		buf.append("fragments:{");
		for (int i = 0; i < fragments.size(); i++) {
			buf.append(fragments.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}