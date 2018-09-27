package com.xgame.logic.server.game.radar.bean;
import com.xgame.logic.server.game.soldier.bean.SoldierBean;
import com.xgame.logic.server.game.country.bean.Vector2Bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Radar-RadarCorpsData - 兵种数据结构体
 */
public class RadarCorpsData extends XPro {
	/**兵种对应的位置1-6服务器随机  camp_message.xml*/
	@MsgField(Id = 1)
	public SoldierBean soldierBrief;
	/**兵种名称（因为是自建兵种）*/
	@MsgField(Id = 2)
	public String corpsName;
	/**兵种Icon名称（因为是自建兵种）*/
	@MsgField(Id = 3)
	public String corpsIconName;
	/**颜色*/
	@MsgField(Id = 4)
	public int color;
	/**兵种放入点*/
	@MsgField(Id = 5)
	public Vector2Bean soldierLoction;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("soldierBrief:" + soldierBrief +",");
		buf.append("corpsName:" + corpsName +",");
		buf.append("corpsIconName:" + corpsIconName +",");
		buf.append("color:" + color +",");
		buf.append("soldierLoction:" + soldierLoction +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}