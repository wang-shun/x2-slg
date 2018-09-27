package com.xgame.logic.server.game.country.bean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Country-CountryBean - 创建建筑物
 */
public class CountryBean extends XPro {
	/**唯一id*/
	@MsgField(Id = 1)
	public int templateId;
	/***/
	@MsgField(Id = 2)
	public List<TransformBean> transformBeans = new ArrayList<TransformBean>();
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("templateId:" + templateId +",");
		buf.append("transformBeans:{");
		for (int i = 0; i < transformBeans.size(); i++) {
			buf.append(transformBeans.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}