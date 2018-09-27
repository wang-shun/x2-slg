package com.xgame.logic.server.game.playerattribute.bean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Playerattribute-AttributeNodeBean - 属性节点对象
 */
public class AttributeNodeBean extends XPro {
	/**属性对象节点id  0-建筑；1-坦克；2-战车；3-飞机；4-玩家*/
	@MsgField(Id = 1)
	public int nodeId;
	/**属性值对象*/
	@MsgField(Id = 2)
	public List<AttrbuteProBean> attributes = new ArrayList<AttrbuteProBean>();
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("nodeId:" + nodeId +",");
		buf.append("attributes:{");
		for (int i = 0; i < attributes.size(); i++) {
			buf.append(attributes.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}