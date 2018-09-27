package com.xgame.logic.server.game.copy.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Copy-MainCopyPointBean - 副本节点
 */
public class MainCopyPointBean extends XPro {
	/**节点ID*/
	@MsgField(Id = 1)
	public int copyPointId;
	/**星星数*/
	@MsgField(Id = 2)
	public int star;
	/**是否开启*/
	@MsgField(Id = 3)
	public boolean isOpen;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("copyPointId:" + copyPointId +",");
		buf.append("star:" + star +",");
		buf.append("isOpen:" + isOpen +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}