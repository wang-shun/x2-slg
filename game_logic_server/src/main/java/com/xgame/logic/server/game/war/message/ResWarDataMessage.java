package com.xgame.logic.server.game.war.message;
import com.xgame.logic.server.game.war.bean.WarAttackData;
import com.xgame.logic.server.game.war.bean.WarDefendData;
import com.xgame.logic.server.game.war.bean.WarResourceBean;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * War-ResWarData - 返回战斗数据
 */
public class ResWarDataMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=500102;
	//模块号
	public static final int FUNCTION_ID=500;
	//消息号
	public static final int MSG_ID=102;
	
	/**进攻方数据*/
	@MsgField(Id = 1)
	public WarAttackData attackData;
	/**防守方数据*/
	@MsgField(Id = 2)
	public WarDefendData defenData;
	/**战斗id*/
	@MsgField(Id = 3)
	public long battleId;
	/**战斗类型*/
	@MsgField(Id = 4)
	public int battleType;
	/**战斗资源信息*/
	@MsgField(Id = 5)
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