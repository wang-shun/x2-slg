package com.xgame.logic.server.game.alliance.message;
import java.util.ArrayList;
import java.util.List;

import com.xgame.logic.server.game.alliance.bean.AlliancePlayerViewBean;
import com.xgame.msglib.ResMessage;
import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Alliance-ResQueryAllianceMemeber - 返回查询联盟成员列表
 */
public class ResQueryAllianceMemeberMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=1007135;
	//模块号
	public static final int FUNCTION_ID=1007;
	//消息号
	public static final int MSG_ID=135;
	
	/**盟主信息*/
	@MsgField(Id = 1)
	public AlliancePlayerViewBean leader;
	/**R5*/
	@MsgField(Id = 2)
	public List<AlliancePlayerViewBean> rankFive = new ArrayList<AlliancePlayerViewBean>();
	/**R4*/
	@MsgField(Id = 3)
	public List<AlliancePlayerViewBean> rankFour = new ArrayList<AlliancePlayerViewBean>();
	/**R3*/
	@MsgField(Id = 4)
	public List<AlliancePlayerViewBean> rankThree = new ArrayList<AlliancePlayerViewBean>();
	/**R2*/
	@MsgField(Id = 5)
	public List<AlliancePlayerViewBean> rankTwo = new ArrayList<AlliancePlayerViewBean>();
	/**R1*/
	@MsgField(Id = 6)
	public List<AlliancePlayerViewBean> rankOne = new ArrayList<AlliancePlayerViewBean>();
		
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
		buf.append("leader:" + leader +",");
		buf.append("rankFive:{");
		for (int i = 0; i < rankFive.size(); i++) {
			buf.append(rankFive.get(i).toString() +",");
		}
		buf.append("rankFour:{");
		for (int i = 0; i < rankFour.size(); i++) {
			buf.append(rankFour.get(i).toString() +",");
		}
		buf.append("rankThree:{");
		for (int i = 0; i < rankThree.size(); i++) {
			buf.append(rankThree.get(i).toString() +",");
		}
		buf.append("rankTwo:{");
		for (int i = 0; i < rankTwo.size(); i++) {
			buf.append(rankTwo.get(i).toString() +",");
		}
		buf.append("rankOne:{");
		for (int i = 0; i < rankOne.size(); i++) {
			buf.append(rankOne.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}