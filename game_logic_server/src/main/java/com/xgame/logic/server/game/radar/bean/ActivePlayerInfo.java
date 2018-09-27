package com.xgame.logic.server.game.radar.bean;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Radar-ActivePlayerInfo - 入侵者信息
 */
public class ActivePlayerInfo extends XPro {
	/**进攻我的玩家id*/
	@MsgField(Id = 1)
	public long playerId;
	/**进攻我的行军id 用它来当key*/
	@MsgField(Id = 2)
	public long marchId;
	/**玩家头像等挂件*/
	@MsgField(Id = 3)
	public String playerImg;
	/**玩家名称*/
	@MsgField(Id = 4)
	public String playerName;
	/**对方开始出发的时间*/
	@MsgField(Id = 5)
	public int beginTime;
	/**偏移量*/
	@MsgField(Id = 6)
	public int time;
	/**1集结进攻2集结防御*/
	@MsgField(Id = 7)
	public int type;
	/**玩家坐标位置*/
	@MsgField(Id = 8)
	public Vector2Bean playerLoction;
	/**来袭兵种类型和数量、位置   country_message.xml*/
	@MsgField(Id = 9)
	public List<RadarCorpsData> corpsList = new ArrayList<RadarCorpsData>();
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("playerId:" + playerId +",");
		buf.append("marchId:" + marchId +",");
		buf.append("playerImg:" + playerImg +",");
		buf.append("playerName:" + playerName +",");
		buf.append("beginTime:" + beginTime +",");
		buf.append("time:" + time +",");
		buf.append("type:" + type +",");
		buf.append("playerLoction:" + playerLoction +",");
		buf.append("corpsList:{");
		for (int i = 0; i < corpsList.size(); i++) {
			buf.append(corpsList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}