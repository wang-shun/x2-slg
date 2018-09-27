package com.xgame.logic.server.game.world.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * World-TerrianBean - 地表信息
 */
public class TerrianBean extends XPro {
	/**是否是军事区0否1是*/
	@MsgField(Id = 1)
	public int fightZone;
	/**1.平原2.丘陵 3.山地4.丛林5.沼泽*/
	@MsgField(Id = 2)
	public int terrain;
	/**战败是否传送0否1是*/
	@MsgField(Id = 3)
	public int battleFailRandomTransfer;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("fightZone:" + fightZone +",");
		buf.append("terrain:" + terrain +",");
		buf.append("battleFailRandomTransfer:" + battleFailRandomTransfer +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}