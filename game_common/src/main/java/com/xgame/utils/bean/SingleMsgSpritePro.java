package com.xgame.utils.bean;

import java.util.List;
import java.util.ArrayList;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 * 
 */
public class SingleMsgSpritePro extends XPro {

	//
	@MsgField(Id = 1)
	public long spriteId;
	
	//
	@MsgField(Id = 2)
	public int type;
	
	//
	@MsgField(Id = 3)
	public String name;
	
	//
	@MsgField(Id = 4)
	public int level;
	
	//
	@MsgField(Id = 5)
	public String sovereign;
	
	//
	@MsgField(Id = 6)
	public String union;
	
	//
	@MsgField(Id = 7)
	public List<MsgSpriteAward> awardList = new ArrayList<MsgSpriteAward>();
	//
	@MsgField(Id = 8)
	public PositionPro worldLocation;
	
	//
	@MsgField(Id = 9)
	public MsgSpriteAttachmentInfo attachmentInfo;
	
	//
	@MsgField(Id = 10)
	public int state;
	
	//
	@MsgField(Id = 11)
	public int showType;
	
	// 被攻打时间
	@MsgField(Id = 12)
	public long attackTime;
	
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		//
		buf.append("spriteId:" + spriteId +",");
		//
		buf.append("type:" + type +",");
		//
		if(this.name!=null) buf.append("name:" + name.toString() +",");
		//
		buf.append("level:" + level +",");
		//
		if(this.sovereign!=null) buf.append("sovereign:" + sovereign.toString() +",");
		//
		if(this.union!=null) buf.append("union:" + union.toString() +",");
		//
		buf.append("awardList:{");
		for (int i = 0; i < awardList.size(); i++) {
			buf.append(awardList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		//
		if(this.worldLocation!=null) buf.append("worldLocation:" + worldLocation.toString() +",");
		//
		if(this.attachmentInfo!=null) buf.append("attachmentInfo:" + attachmentInfo.toString() +",");
		//
		buf.append("state:" + state +",");
		//
		buf.append("showType:" + showType +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}