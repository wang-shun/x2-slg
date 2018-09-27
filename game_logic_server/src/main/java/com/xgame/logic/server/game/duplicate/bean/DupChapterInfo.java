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
public class DupChapterInfo extends XPro {

	//章节ID
	@MsgField(Id = 1)
	public int chapterId;
	
	//当前进度
	@MsgField(Id = 2)
	public int currentStep;
	
	//章节获得星星总数
	@MsgField(Id = 3)
	public int chapterStarNum;
	
	//已领宝箱进度
	@MsgField(Id = 4)
	public List<Integer> rewardIndex = new ArrayList<Integer>();
	
	//能否领取奖励(false 不能 true有奖励)
	@MsgField(Id = 5)
	public List<Boolean> canReward = new ArrayList<Boolean>();
	

	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		//章节ID
		buf.append("chapterId:" + chapterId +",");
		//当前进度
		buf.append("currentStep:" + currentStep +",");
		//章节获得星星总数
		buf.append("chapterStarNum:" + chapterStarNum +",");
		//已领宝箱进度
		buf.append("rewardIndex:{");
		for (int i = 0; i < rewardIndex.size(); i++) {
			buf.append(rewardIndex.get(i) +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		//能否领取奖励(false 不能 true有奖励)
		buf.append("canReward:{");
		for (int i = 0; i < canReward.size(); i++) {
			buf.append(canReward.get(i) + canReward.toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}