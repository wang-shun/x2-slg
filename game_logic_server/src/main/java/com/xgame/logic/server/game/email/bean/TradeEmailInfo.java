package com.xgame.logic.server.game.email.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Email-TradeEmailInfo - 贸易报告
 */
public class TradeEmailInfo extends XPro {
	/**运送者签名*/
	@MsgField(Id = 1)
	public EmailSignature signature;
	/**金币*/
	@MsgField(Id = 2)
	public long resMoney;
	/**石油*/
	@MsgField(Id = 3)
	public long resOil;
	/**稀土*/
	@MsgField(Id = 4)
	public long resRare;
	/**钢材*/
	@MsgField(Id = 5)
	public long resSteel;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("signature:" + signature +",");
		buf.append("resMoney:" + resMoney +",");
		buf.append("resOil:" + resOil +",");
		buf.append("resRare:" + resRare +",");
		buf.append("resSteel:" + resSteel +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}