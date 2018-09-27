package com.xgame.logic.server.game.defensesoldier.bean;
import com.xgame.logic.server.game.soldier.bean.SoldierBean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * DefenseSoldier-DefensSolderBuildPro - 防守驻地信息
 */
public class DefensSolderBuildPro extends XPro {
	/**建筑唯一id*/
	@MsgField(Id = 1)
	public int buildUid;
	/**手动设置的驻军兵种  没有为null*/
	@MsgField(Id = 2)
	public SoldierBean useSoldier;
	/**0 开 1关 自动补充*/
	@MsgField(Id = 3)
	public int autoPatch;
	/**2开  3关 自动防御*/
	@MsgField(Id = 4)
	public int autoDefens;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("buildUid:" + buildUid +",");
		buf.append("useSoldier:" + useSoldier +",");
		buf.append("autoPatch:" + autoPatch +",");
		buf.append("autoDefens:" + autoDefens +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}