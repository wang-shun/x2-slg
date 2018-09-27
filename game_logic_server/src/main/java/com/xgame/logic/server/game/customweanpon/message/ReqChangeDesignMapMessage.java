package com.xgame.logic.server.game.customweanpon.message;
import com.xgame.logic.server.game.customweanpon.bean.PartBean;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * CustomWeanpon-ReqChangeDesignMap - 请求修改图纸
 */
public class ReqChangeDesignMapMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=600202;
	//模块号
	public static final int FUNCTION_ID=600;
	//消息号
	public static final int MSG_ID=202;
	
	/**图纸ID*/
	@MsgField(Id = 1)
	public long id;
	/**图纸名称*/
	@MsgField(Id = 2)
	public String name;
	/**配件列表*/
	@MsgField(Id = 3)
	public List<PartBean> partList = new ArrayList<PartBean>();
		
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
		buf.append("id:" + id +",");
		buf.append("name:" + name +",");
		buf.append("partList:{");
		for (int i = 0; i < partList.size(); i++) {
			buf.append(partList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}