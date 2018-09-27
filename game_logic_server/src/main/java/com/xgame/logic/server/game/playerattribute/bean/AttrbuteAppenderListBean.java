package com.xgame.logic.server.game.playerattribute.bean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Playerattribute-AttrbuteAppenderListBean - 单个属性来源列表
 */
public class AttrbuteAppenderListBean extends XPro {
	/**属性对象节点id  0-建筑；1-坦克；2-战车；3-飞机；4-玩家*/
	@MsgField(Id = 1)
	public int nodeId;
	/**属性id*/
	@MsgField(Id = 2)
	public int attributeId;
	/**属性来源模块*/
	@MsgField(Id = 3)
	public List<AttrbuteAppenderBean> list = new ArrayList<AttrbuteAppenderBean>();
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("nodeId:" + nodeId +",");
		buf.append("attributeId:" + attributeId +",");
		buf.append("list:{");
		for (int i = 0; i < list.size(); i++) {
			buf.append(list.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}