package com.xgame.logic.server.game.customweanpon.bean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * CustomWeanpon-DesignMapBean - 图纸信息
 */
public class DesignMapBean extends XPro {
	/**ID*/
	@MsgField(Id = 1)
	public long id;
	/**图纸类型:1坦克2战车3飞机*/
	@MsgField(Id = 2)
	public int type;
	/**系统兵种序列号(原DinPanIndex)*/
	@MsgField(Id = 3)
	public int systemIndex;
	/**位置顺序(系统兵种index=0, 1-3)*/
	@MsgField(Id = 4)
	public int index;
	/**建造顺序(1-3)*/
	@MsgField(Id = 5)
	public int buildIndex;
	/**版本号*/
	@MsgField(Id = 6)
	public int version;
	/**图纸名字*/
	@MsgField(Id = 7)
	public String name;
	/**是否解锁*/
	@MsgField(Id = 8)
	public int unlock;
	/**配件列表*/
	@MsgField(Id = 9)
	public List<PartBean> partList = new ArrayList<PartBean>();
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("id:" + id +",");
		buf.append("type:" + type +",");
		buf.append("systemIndex:" + systemIndex +",");
		buf.append("index:" + index +",");
		buf.append("buildIndex:" + buildIndex +",");
		buf.append("version:" + version +",");
		buf.append("name:" + name +",");
		buf.append("partList:{");
		for (int i = 0; i < partList.size(); i++) {
			buf.append(partList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}