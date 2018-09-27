package com.xgame.logic.server.game.copy.enity;

import io.protostuff.Tag;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

/**
 * 主线副本节点
 * @author zehong.he
 *
 */
public class MainCopyPoint implements Serializable,JBaseTransform{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5492539549045932393L;

	@Tag(1)
	@Setter
	@Getter
	private int copyPointId;						//节点ID
	
	@Tag(2)
	@Setter
	@Getter
	private int star;								//星星数
	
	@Tag(3)
	@Setter
	@Getter
	private int passNum;							//通关次数
	
	@Tag(4)
	@Setter
	@Getter
	private boolean isOpen;								//是否开启
	
	public MainCopyPoint(int copyPointId,boolean isOpen){
		this.copyPointId = copyPointId;
		this.isOpen = isOpen;
	}
	
	public MainCopyPoint(){
		
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("copyPointId", copyPointId);
		jbaseData.put("star", star);
		jbaseData.put("passNum", passNum);
		jbaseData.put("isOpen", isOpen);
		return jbaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.copyPointId = jBaseData.getInt("copyPointId", 0);
		this.star = jBaseData.getInt("star",0);
		this.passNum = jBaseData.getInt("passNum", 0);
		this.isOpen = jBaseData.getBoolean("isOpen",false);
	}
}
