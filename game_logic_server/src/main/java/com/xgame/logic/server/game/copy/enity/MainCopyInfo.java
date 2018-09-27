package com.xgame.logic.server.game.copy.enity;

import io.protostuff.Tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

/**
 * 玩家主线副本信息
 * @author zehong.he
 *
 */
public class MainCopyInfo implements Serializable,JBaseTransform{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8873958807706553630L;
 
	@Tag(1)
	@Setter
	@Getter
	private Map<Integer,MainCopy> copyMap = new HashMap<>();

	@Override
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		List<JBaseData> copyList = new ArrayList<JBaseData>();
		for(Map.Entry<Integer, MainCopy> entry:copyMap.entrySet()) {
			copyList.add(((JBaseTransform)entry.getValue()).toJBaseData());
		}
		jbaseData.put("copys", copyList);
		
		return jbaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		List<JBaseData> copyList = jBaseData.getSeqBaseData("copys");
		for(JBaseData jBaseData2 : copyList) {
			MainCopy mainCopy = new MainCopy();
			mainCopy.fromJBaseData(jBaseData2);
			this.copyMap.put(mainCopy.getCopyId(), mainCopy);
		}
	}
}
