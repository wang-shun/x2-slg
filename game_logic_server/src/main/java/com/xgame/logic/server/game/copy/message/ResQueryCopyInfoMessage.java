package com.xgame.logic.server.game.copy.message;
import com.xgame.logic.server.game.copy.bean.MainCopyBean;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Copy-ResQueryCopyInfo - 返回副本列表信息
 */
public class ResQueryCopyInfoMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=2100101;
	//模块号
	public static final int FUNCTION_ID=2100;
	//消息号
	public static final int MSG_ID=101;
	
	/**副本列表信息*/
	@MsgField(Id = 1)
	public List<MainCopyBean> mainCopyList = new ArrayList<MainCopyBean>();
		
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
		buf.append("mainCopyList:{");
		for (int i = 0; i < mainCopyList.size(); i++) {
			buf.append(mainCopyList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}