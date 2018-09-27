package com.xgame.logic.server.game.commander.message;
import com.xgame.logic.server.game.commander.bean.TalentPro;
import com.xgame.logic.server.game.commander.bean.StatisticLongPro;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Commander-ResCommander - 指挥官信息
 */
public class ResCommanderMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=130100;
	//模块号
	public static final int FUNCTION_ID=130;
	//消息号
	public static final int MSG_ID=100;
	
	/**名字*/
	@MsgField(Id = 1)
	public String name;
	/**等级*/
	@MsgField(Id = 2)
	public int level;
	/**指挥官经验*/
	@MsgField(Id = 3)
	public long exp;
	/**统帅点*/
	@MsgField(Id = 4)
	public int cPoints;
	/**体能*/
	@MsgField(Id = 5)
	public int energy;
	/**形象*/
	@MsgField(Id = 6)
	public int style;
	/**已有天赋点数*/
	@MsgField(Id = 7)
	public int talentPoints;
	/**当前天赋*/
	@MsgField(Id = 8)
	public int talentPage;
	/**穿戴装备唯一id集合*/
	@MsgField(Id = 9)
	public List<Long> equits = new ArrayList<Long>();
	/**以学天赋*/
	@MsgField(Id = 10)
	public List<TalentPro> talents = new ArrayList<TalentPro>();
	/**需要长整形统计*/
	@MsgField(Id = 11)
	public List<StatisticLongPro> statisticLongs = new ArrayList<StatisticLongPro>();
		
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
		buf.append("level:" + level +",");
		buf.append("exp:" + exp +",");
		buf.append("cPoints:" + cPoints +",");
		buf.append("energy:" + energy +",");
		buf.append("style:" + style +",");
		buf.append("talentPoints:" + talentPoints +",");
		buf.append("talentPage:" + talentPage +",");
		buf.append("equits:{");
		for (int i = 0; i < equits.size(); i++) {
			buf.append(equits.get(i).toString() +",");
		}
		buf.append("talents:{");
		for (int i = 0; i < talents.size(); i++) {
			buf.append(talents.get(i).toString() +",");
		}
		buf.append("statisticLongs:{");
		for (int i = 0; i < statisticLongs.size(); i++) {
			buf.append(statisticLongs.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}