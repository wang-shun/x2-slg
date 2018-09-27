package com.xgame.logic.server.game.warehouse.message;
import com.xgame.logic.server.game.warehouse.bean.ResourcePro;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Warehouse-ResResource - 发送当前仓库情况
 */
public class ResResourceMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=133100;
	//模块号
	public static final int FUNCTION_ID=133;
	//消息号
	public static final int MSG_ID=100;
	
	/**资源*/
	@MsgField(Id = 1)
	public List<ResourcePro> resources = new ArrayList<ResourcePro>();
		
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
		buf.append("resources:{");
		for (int i = 0; i < resources.size(); i++) {
			buf.append(resources.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}