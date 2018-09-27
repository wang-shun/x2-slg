package com.xgame.logic.server.game.allianceext.bean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * AllianceExt-AllianceBoxBean - 联盟宝箱
 */
public class AllianceBoxBean extends XPro {
	/**宝箱id*/
	@MsgField(Id = 1)
	public long id;
	/**联盟id*/
	@MsgField(Id = 2)
	public long allianceId;
	/**宝箱id配置表*/
	@MsgField(Id = 3)
	public int boxTid;
	/**开始时间*/
	@MsgField(Id = 4)
	public long startTime;
	/**结束时间*/
	@MsgField(Id = 5)
	public long endTime;
	/**是否已领取 0未领取 1已领取*/
	@MsgField(Id = 6)
	public int rewardFlag;
	/**奖励道具列表*/
	@MsgField(Id = 7)
	public List<AwardBean> rewards = new ArrayList<AwardBean>();
	/**宝箱点数*/
	@MsgField(Id = 8)
	public int boxNum;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("id:" + id +",");
		buf.append("allianceId:" + allianceId +",");
		buf.append("boxTid:" + boxTid +",");
		buf.append("startTime:" + startTime +",");
		buf.append("endTime:" + endTime +",");
		buf.append("rewardFlag:" + rewardFlag +",");
		buf.append("rewards:{");
		for (int i = 0; i < rewards.size(); i++) {
			buf.append(rewards.get(i).toString() +",");
		}
		buf.append("boxNum:" + boxNum +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}