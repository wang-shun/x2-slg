package com.xgame.logic.server.game.country.message;
import com.xgame.logic.server.game.country.bean.CountryBean;
import com.xgame.logic.server.game.country.bean.BuildBean;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Country-ResAllCountry - 移除建筑物
 */
public class ResAllCountryMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=100101;
	//模块号
	public static final int FUNCTION_ID=100;
	//消息号
	public static final int MSG_ID=101;
	
	/**唯一id*/
	@MsgField(Id = 1)
	public int useTemplateId;
	/***/
	@MsgField(Id = 2)
	public List<CountryBean> countrys = new ArrayList<CountryBean>();
	/***/
	@MsgField(Id = 3)
	public List<BuildBean> builds = new ArrayList<BuildBean>();
		
	@Override
	public int getId() {
		return ID;
	}

	@Override
	public String getQueue() {
		return "s2s";
	}
	
	@Override
	public String getServer() {
		return null;
	}
	
	@Override
	public boolean isSync() {
		return false;
	}

	@Override
	public CommandEnum getCommandEnum() {
		return Communicationable.CommandEnum.PLAYERMSG;
	}
	
	@Override
	public HandlerEnum getHandlerEnum() {
		return Communicationable.HandlerEnum.SC;
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("useTemplateId:" + useTemplateId +",");
		buf.append("countrys:{");
		for (int i = 0; i < countrys.size(); i++) {
			buf.append(countrys.get(i).toString() +",");
		}
		buf.append("builds:{");
		for (int i = 0; i < builds.size(); i++) {
			buf.append(builds.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}