package com.xgame.logic.server.game.allianceext.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * AllianceExt-PlayerAllianceExtBean - 玩家联盟扩展信息
 */
public class PlayerAllianceExtBean extends XPro {
	/**玩家id*/
	@MsgField(Id = 1)
	public long playerId;
	/**活跃度*/
	@MsgField(Id = 2)
	public int activitySocre;
	/**活跃度石油*/
	@MsgField(Id = 3)
	public int rewardActivityOil;
	/**活跃度稀土*/
	@MsgField(Id = 4)
	public int rewardActivityRare;
	/**活跃度钢铁*/
	@MsgField(Id = 5)
	public int rewardActivitySteel;
	/**活跃度黄金*/
	@MsgField(Id = 6)
	public int rewardActivityGold;
	/**活跃度石油次数*/
	@MsgField(Id = 7)
	public int scienceOilCount;
	/**活跃度稀土次数*/
	@MsgField(Id = 8)
	public int scienceRareCount;
	/**活跃度钢铁次数*/
	@MsgField(Id = 9)
	public int scienceSteelCount;
	/**活跃度黄金次数*/
	@MsgField(Id = 10)
	public int scienceGoldCount;
	/**活跃度钻石次数*/
	@MsgField(Id = 11)
	public int scienceDiamondCount;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("playerId:" + playerId +",");
		buf.append("activitySocre:" + activitySocre +",");
		buf.append("rewardActivityOil:" + rewardActivityOil +",");
		buf.append("rewardActivityRare:" + rewardActivityRare +",");
		buf.append("rewardActivitySteel:" + rewardActivitySteel +",");
		buf.append("rewardActivityGold:" + rewardActivityGold +",");
		buf.append("scienceOilCount:" + scienceOilCount +",");
		buf.append("scienceRareCount:" + scienceRareCount +",");
		buf.append("scienceSteelCount:" + scienceSteelCount +",");
		buf.append("scienceGoldCount:" + scienceGoldCount +",");
		buf.append("scienceDiamondCount:" + scienceDiamondCount +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}