package com.xgame.logic.server.game.world.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * World-LandFormBean - 地貌信息
 */
public class LandFormBean extends XPro {
	/**装饰物*/
	@MsgField(Id = 1)
	public int surfaceId;
	/**沙地图片*/
	@MsgField(Id = 2)
	public int groundId;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("surfaceId:" + surfaceId +",");
		buf.append("groundId:" + groundId +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}