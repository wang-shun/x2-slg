package com.xgame.logic.server.game.radar.bean;
import com.xgame.logic.server.game.country.bean.Vector2Bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Radar-RadarBuildPro - 雷达建筑
 */
public class RadarBuildPro extends XPro {
	/**建筑id*/
	@MsgField(Id = 1)
	public int buildUid;
	/**配置id*/
	@MsgField(Id = 2)
	public int buildSid;
	/**建筑等级*/
	@MsgField(Id = 3)
	public int level;
	/**兵种放入点*/
	@MsgField(Id = 4)
	public Vector2Bean soldierLoction;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("buildUid:" + buildUid +",");
		buf.append("buildSid:" + buildSid +",");
		buf.append("level:" + level +",");
		buf.append("soldierLoction:" + soldierLoction +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}