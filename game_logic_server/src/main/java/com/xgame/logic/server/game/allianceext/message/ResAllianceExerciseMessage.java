package com.xgame.logic.server.game.allianceext.message;
import com.xgame.logic.server.game.allianceext.bean.AllianceExerciseBean;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * AllianceExt-ResAllianceExercise - 返回军团演习结果
 */
public class ResAllianceExerciseMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=1210107;
	//模块号
	public static final int FUNCTION_ID=1210;
	//消息号
	public static final int MSG_ID=107;
	
	/**开始演习时间*/
	@MsgField(Id = 1)
	public long exerciseStartTime;
	/**军团演习信息*/
	@MsgField(Id = 2)
	public List<AllianceExerciseBean> allianceExerciseBean = new ArrayList<AllianceExerciseBean>();
		
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
		buf.append("exerciseStartTime:" + exerciseStartTime +",");
		buf.append("allianceExerciseBean:{");
		for (int i = 0; i < allianceExerciseBean.size(); i++) {
			buf.append(allianceExerciseBean.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}