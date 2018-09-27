package com.xgame.logic.server.game.allianceext.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * AllianceExt-GoodsBean - 军团商店商品信息
 */
public class GoodsBean extends XPro {
	/**策划配置表ID*/
	@MsgField(Id = 1)
	public int id;
	/**道具ID*/
	@MsgField(Id = 2)
	public int itemId;
	/**单次兑换道具数量*/
	@MsgField(Id = 3)
	public int itemNum;
	/**解锁所需军团等级*/
	@MsgField(Id = 4)
	public int needArmyLv;
	/**兑换所需贡献*/
	@MsgField(Id = 5)
	public int gx;
	/**可兑换数量*/
	@MsgField(Id = 6)
	public int max;
	/**已兑换数量*/
	@MsgField(Id = 7)
	public int buyCount;
	/**品类 0普通 1珍品*/
	@MsgField(Id = 8)
	public int type;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("id:" + id +",");
		buf.append("itemId:" + itemId +",");
		buf.append("itemNum:" + itemNum +",");
		buf.append("needArmyLv:" + needArmyLv +",");
		buf.append("gx:" + gx +",");
		buf.append("max:" + max +",");
		buf.append("buyCount:" + buyCount +",");
		buf.append("type:" + type +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}