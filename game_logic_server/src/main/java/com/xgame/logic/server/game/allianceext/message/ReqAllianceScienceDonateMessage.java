package com.xgame.logic.server.game.allianceext.message;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * AllianceExt-ReqAllianceScienceDonate - 联盟科技捐赠（捐献满当前等级自动升级 达到满级不能捐献）
 */
public class ReqAllianceScienceDonateMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=1210206;
	//模块号
	public static final int FUNCTION_ID=1210;
	//消息号
	public static final int MSG_ID=206;
	
	/**联盟Id*/
	@MsgField(Id = 1)
	public long allianceId;
	/**捐赠科技模版id*/
	@MsgField(Id = 2)
	public int scienceTid;
	/**捐献类型（1石油2稀土3钢铁4黄金5钻石）*/
	@MsgField(Id = 3)
	public int type;
		
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
		buf.append("allianceId:" + allianceId +",");
		buf.append("scienceTid:" + scienceTid +",");
		buf.append("type:" + type +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}