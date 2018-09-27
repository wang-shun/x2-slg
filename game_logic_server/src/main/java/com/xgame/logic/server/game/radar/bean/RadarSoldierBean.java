package com.xgame.logic.server.game.radar.bean;
import com.xgame.logic.server.game.soldier.bean.FullSoldierBean;
import com.xgame.logic.server.game.country.bean.TransformBean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Radar-RadarSoldierBean - 兵种信息
 */
public class RadarSoldierBean extends XPro {
	/**序列号*/
	@MsgField(Id = 1)
	public int index;
	/**士兵*/
	@MsgField(Id = 2)
	public FullSoldierBean soldier;
	/**放兵点*/
	@MsgField(Id = 3)
	public TransformBean transformBean;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("index:" + index +",");
		buf.append("soldier:" + soldier +",");
		buf.append("transformBean:" + transformBean +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}