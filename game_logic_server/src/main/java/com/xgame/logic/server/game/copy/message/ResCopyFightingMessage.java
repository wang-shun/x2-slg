package com.xgame.logic.server.game.copy.message;
import com.xgame.logic.server.game.war.bean.WarAttackData;
import com.xgame.logic.server.game.war.bean.WarDefendData;
import com.xgame.logic.server.game.war.bean.WarResourceBean;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Copy-ResCopyFighting - 
 */
public class ResCopyFightingMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=2100102;
	//模块号
	public static final int FUNCTION_ID=2100;
	//消息号
	public static final int MSG_ID=102;
	
	/**副本id*/
	@MsgField(Id = 1)
	public int copyId;
	/**节点ID*/
	@MsgField(Id = 2)
	public int copyPointId;
	/**进攻方数据*/
	@MsgField(Id = 3)
	public WarAttackData attackData;
	/**防守方数据*/
	@MsgField(Id = 4)
	public WarDefendData defenData;
	/**战斗id*/
	@MsgField(Id = 5)
	public long battleId;
	/**战斗类型*/
	@MsgField(Id = 6)
	public int battleType;
	/**战斗资源信息*/
	@MsgField(Id = 7)
	public WarResourceBean warResourceBean;
		
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
		buf.append("copyId:" + copyId +",");
		buf.append("copyPointId:" + copyPointId +",");
		buf.append("attackData:" + attackData +",");
		buf.append("defenData:" + defenData +",");
		buf.append("battleId:" + battleId +",");
		buf.append("battleType:" + battleType +",");
		buf.append("warResourceBean:" + warResourceBean +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}