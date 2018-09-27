package com.xgame.logic.server.game.duplicate.bean;


import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 * 副本节点信息
 */
public class DupNoteInfo extends XPro {

	//章节ID
	@MsgField(Id = 1)
	public int chapterId;
	
	//节ID
	@MsgField(Id = 2)
	public int noteId;
	
	//节点获得星星数
	@MsgField(Id = 3)
	public int noteStarNum;
	
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		//章节ID
		buf.append("chapterId:" + chapterId +",");
		//节ID
		buf.append("noteId:" + noteId +",");
		//节点获得星星数
		buf.append("noteStarNum:" + noteStarNum +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}