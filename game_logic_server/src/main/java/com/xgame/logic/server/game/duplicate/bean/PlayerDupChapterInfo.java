package com.xgame.logic.server.game.duplicate.bean;

import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 * 副本章信息
 */
public class PlayerDupChapterInfo extends XPro {

	//玩家id
	@MsgField(Id = 1)
	public long playerId;
	
	//玩家章节副本信息
	@MsgField(Id = 2)
	public List<DupChapterInfo> dupChapterInfoList = new ArrayList<DupChapterInfo>();
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		//玩家id
		buf.append("playerId:" + playerId +",");
		//玩家章节副本信息
		buf.append("dupChapterInfoList:{");
		for (int i = 0; i < dupChapterInfoList.size(); i++) {
			buf.append(dupChapterInfoList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}