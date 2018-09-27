package com.xgame.logic.server.game.war.bean;
import com.xgame.logic.server.game.country.bean.BuildBean;
import com.xgame.logic.server.game.country.bean.TransformBean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * War-WarBuilding - 战场建筑
 */
public class WarBuilding extends XPro {
	/**建筑物信息*/
	@MsgField(Id = 1)
	public BuildBean building;
	/**驻军坐标信息*/
	@MsgField(Id = 2)
	public TransformBean transform;
	/**战斗属性*/
	@MsgField(Id = 3)
	public WarAttr warAttr;
	/**资源数量*/
	@MsgField(Id = 4)
	public long resourceNum;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("building:" + building +",");
		buf.append("transform:" + transform +",");
		buf.append("warAttr:" + warAttr +",");
		buf.append("resourceNum:" + resourceNum +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}