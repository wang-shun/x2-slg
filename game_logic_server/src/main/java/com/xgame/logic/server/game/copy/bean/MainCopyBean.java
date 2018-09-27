package com.xgame.logic.server.game.copy.bean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Copy-MainCopyBean - 副本数据
 */
public class MainCopyBean extends XPro {
	/**副本id*/
	@MsgField(Id = 1)
	public int copyId;
	/**当前完成副本节点数*/
	@MsgField(Id = 2)
	public int currCopyPoint;
	/**当前获得星星数*/
	@MsgField(Id = 3)
	public int star;
	/**0-未解锁；1-进行中；2-已完成*/
	@MsgField(Id = 4)
	public int state;
	/**宝箱领取记录(二进制0（未领取）,1(已领取)表示)*/
	@MsgField(Id = 5)
	public int rewardBoxFlag;
	/**节点信息*/
	@MsgField(Id = 6)
	public List<MainCopyPointBean> pointList = new ArrayList<MainCopyPointBean>();
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("copyId:" + copyId +",");
		buf.append("currCopyPoint:" + currCopyPoint +",");
		buf.append("star:" + star +",");
		buf.append("state:" + state +",");
		buf.append("rewardBoxFlag:" + rewardBoxFlag +",");
		buf.append("pointList:{");
		for (int i = 0; i < pointList.size(); i++) {
			buf.append(pointList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}