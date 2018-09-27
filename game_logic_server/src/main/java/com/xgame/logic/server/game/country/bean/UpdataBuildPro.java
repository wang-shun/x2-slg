package com.xgame.logic.server.game.country.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Country-UpdataBuildPro - 更新建筑物
 */
public class UpdataBuildPro extends XPro {
	/**家园模版id*/
	@MsgField(Id = 1)
	public int templateId;
	/**建筑物位置*/
	@MsgField(Id = 2)
	public TransformBean transform;
	/**建筑物*/
	@MsgField(Id = 3)
	public BuildBean build;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("templateId:" + templateId +",");
		buf.append("transform:" + transform +",");
		buf.append("build:" + build +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}