package com.xgame.logic.server.game.soldier.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Soldier-LibraryBean - 配件信息
 */
public class LibraryBean extends XPro {
	/**唯一id*/
	@MsgField(Id = 1)
	public int libraryId;
	/***/
	@MsgField(Id = 2)
	public int value;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("libraryId:" + libraryId +",");
		buf.append("value:" + value +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}