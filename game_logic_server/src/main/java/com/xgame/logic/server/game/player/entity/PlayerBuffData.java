package com.xgame.logic.server.game.player.entity;

import io.protostuff.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;


/**
 * 玩家buff数据
 * @author jacky.jiang
 *
 */
public class PlayerBuffData implements JBaseTransform {
	
	@Tag(1)
	private Map<String, PlayerBuff> buffMap = new HashMap<String, PlayerBuff>();

	public Map<String, PlayerBuff> getBuffMap() {
		return buffMap;
	}

	public void setBuffMap(Map<String, PlayerBuff> buffMap) {
		this.buffMap = buffMap;
	}
	
	public PlayerBuff queryPlayerBuff(String buffId) {
		return buffMap.get(buffId);
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jBaseData = new JBaseData();
		List<JBaseData> jBuffList = new ArrayList<JBaseData>();
		for(PlayerBuff playerBuff : buffMap.values()) {
			JBaseData jBaseData2 = playerBuff.toJBaseData();
			jBuffList.add(jBaseData2);
		}
		jBaseData.put("buffMap", jBuffList);
		return jBaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		List<JBaseData> jBuffList = jBaseData.getSeqBaseData("buffMap");
		for(JBaseData baseData : jBuffList) {
			PlayerBuff playerBuff = new PlayerBuff();
			playerBuff.fromJBaseData(baseData);
			this.buffMap.put(playerBuff.getBuffId(), playerBuff);
		}
	}
}
