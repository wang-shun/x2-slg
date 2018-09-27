package com.xgame.logic.server.game.player.message;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.player.bean.MsgPlayerEquipmentBag;
import com.xgame.logic.server.game.player.bean.MsgPlayerEquipmentFragmentBag;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Player-ResUserInfo - 
 */
public class ResUserInfoMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=1003101;
	//模块号
	public static final int FUNCTION_ID=1003;
	//消息号
	public static final int MSG_ID=101;
	
	/***/
	@MsgField(Id = 1)
	public String nickname;
	/***/
	@MsgField(Id = 2)
	public long resSY;
	/***/
	@MsgField(Id = 3)
	public long resXT;
	/***/
	@MsgField(Id = 4)
	public long resGC;
	/***/
	@MsgField(Id = 5)
	public long resCP;
	/***/
	@MsgField(Id = 6)
	public long resZS;
	/***/
	@MsgField(Id = 7)
	public long fightPower;
	/***/
	@MsgField(Id = 8)
	public long spriteId;
	/***/
	@MsgField(Id = 9)
	public Vector2Bean worldLocation;
	/***/
	@MsgField(Id = 10)
	public String union;
	/***/
	@MsgField(Id = 11)
	public MsgPlayerEquipmentBag playerEquipmentBag;
	/***/
	@MsgField(Id = 12)
	public int server;
	/**基地被打时间*/
	@MsgField(Id = 13)
	public long beAttackTime;
	/**联盟id*/
	@MsgField(Id = 14)
	public long allianceId;
	/**联盟简称*/
	@MsgField(Id = 15)
	public String allianceSimpleName;
	/**登出时间*/
	@MsgField(Id = 16)
	public long logoutTime;
	/***/
	@MsgField(Id = 17)
	public MsgPlayerEquipmentFragmentBag playerEquipmentFragmentBag;
		
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
		buf.append("nickname:" + nickname +",");
		buf.append("resSY:" + resSY +",");
		buf.append("resXT:" + resXT +",");
		buf.append("resGC:" + resGC +",");
		buf.append("resCP:" + resCP +",");
		buf.append("resZS:" + resZS +",");
		buf.append("fightPower:" + fightPower +",");
		buf.append("spriteId:" + spriteId +",");
		buf.append("worldLocation:" + worldLocation +",");
		buf.append("union:" + union +",");
		buf.append("playerEquipmentBag:" + playerEquipmentBag +",");
		buf.append("server:" + server +",");
		buf.append("beAttackTime:" + beAttackTime +",");
		buf.append("allianceId:" + allianceId +",");
		buf.append("allianceSimpleName:" + allianceSimpleName +",");
		buf.append("logoutTime:" + logoutTime +",");
		buf.append("playerEquipmentFragmentBag:" + playerEquipmentFragmentBag +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}