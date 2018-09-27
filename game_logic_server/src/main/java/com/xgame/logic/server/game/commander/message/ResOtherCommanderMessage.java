package com.xgame.logic.server.game.commander.message;
import com.xgame.logic.server.game.commander.bean.StatisticLongPro;
import com.xgame.logic.server.game.commander.bean.StatisticIntegerPro;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Commander-ResOtherCommander - 注释
 */
public class ResOtherCommanderMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=130408;
	//模块号
	public static final int FUNCTION_ID=130;
	//消息号
	public static final int MSG_ID=408;
	
	/**名字*/
	@MsgField(Id = 1)
	public String name;
	/**玩家id*/
	@MsgField(Id = 2)
	public long uid;
	/**等级*/
	@MsgField(Id = 3)
	public int level;
	/**点赞*/
	@MsgField(Id = 4)
	public int worship;
	/**头衔*/
	@MsgField(Id = 5)
	public String title;
	/**形象*/
	@MsgField(Id = 6)
	public int style;
	/**穿戴装备唯一id集合*/
	@MsgField(Id = 7)
	public List<Long> equits = new ArrayList<Long>();
	/**需要长整形统计*/
	@MsgField(Id = 8)
	public List<StatisticLongPro> statisticLongs = new ArrayList<StatisticLongPro>();
	/**int统计*/
	@MsgField(Id = 9)
	public List<StatisticIntegerPro> statisticInts = new ArrayList<StatisticIntegerPro>();
		
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
		buf.append("name:" + name +",");
		buf.append("uid:" + uid +",");
		buf.append("level:" + level +",");
		buf.append("worship:" + worship +",");
		buf.append("title:" + title +",");
		buf.append("style:" + style +",");
		buf.append("equits:{");
		for (int i = 0; i < equits.size(); i++) {
			buf.append(equits.get(i).toString() +",");
		}
		buf.append("statisticLongs:{");
		for (int i = 0; i < statisticLongs.size(); i++) {
			buf.append(statisticLongs.get(i).toString() +",");
		}
		buf.append("statisticInts:{");
		for (int i = 0; i < statisticInts.size(); i++) {
			buf.append(statisticInts.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}