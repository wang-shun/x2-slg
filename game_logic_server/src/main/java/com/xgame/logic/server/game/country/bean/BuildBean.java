package com.xgame.logic.server.game.country.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Country-BuildBean - 创建建筑物
 */
public class BuildBean extends XPro {
	/**唯一id*/
	@MsgField(Id = 1)
	public int uid;
	/**tid*/
	@MsgField(Id = 2)
	public int sid;
	/**等级*/
	@MsgField(Id = 3)
	public int level;
	/**建筑状态(TimeState 里的取值)*/
	@MsgField(Id = 4)
	public int state;
	/**建筑扩展字段*/
	@MsgField(Id = 5)
	public String ext;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("uid:" + uid +",");
		buf.append("sid:" + sid +",");
		buf.append("level:" + level +",");
		buf.append("state:" + state +",");
		buf.append("ext:" + ext +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}