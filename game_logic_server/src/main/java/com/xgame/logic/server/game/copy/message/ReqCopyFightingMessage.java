package com.xgame.logic.server.game.copy.message;
import com.xgame.logic.server.game.world.bean.WorldSimpleSoldierBean;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Copy-ReqCopyFighting - 请求副本战斗
 */
public class ReqCopyFightingMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=2100202;
	//模块号
	public static final int FUNCTION_ID=2100;
	//消息号
	public static final int MSG_ID=202;
	
	/**副本id*/
	@MsgField(Id = 1)
	public int copyId;
	/**节点ID*/
	@MsgField(Id = 2)
	public int copyPointId;
	/**精灵信息*/
	@MsgField(Id = 3)
	public List<WorldSimpleSoldierBean> soldiers = new ArrayList<WorldSimpleSoldierBean>();
		
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
		buf.append("copyId:" + copyId +",");
		buf.append("copyPointId:" + copyPointId +",");
		buf.append("soldiers:{");
		for (int i = 0; i < soldiers.size(); i++) {
			buf.append(soldiers.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}