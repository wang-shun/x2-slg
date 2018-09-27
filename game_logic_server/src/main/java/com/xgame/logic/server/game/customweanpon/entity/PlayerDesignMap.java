package com.xgame.logic.server.game.customweanpon.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;


/**
 * 玩家图纸信息
 * @author jacky.jiang
 *
 */
public class PlayerDesignMap implements JBaseTransform {

	/**
	 * 图纸列表
	 */
	private Map<Long, DesignMap> designMapInfo = new ConcurrentHashMap<Long, DesignMap>();

	public Map<Long, DesignMap> getDesignMap() {
		return designMapInfo;
	}

	public void setDesignMap(Map<Long, DesignMap> designMap) {
		this.designMapInfo = designMap;
	}
	
	public void addDesignMap(DesignMap designMap) {
		this.designMapInfo.put(designMap.getId(), designMap);
	}
	
	public DesignMap getDesignMap(long id) {
		return designMapInfo.get(id);
	}
	
	/**
	 * 获取图纸信息
	 * @param player
	 * @param systemIndex
	 * @return
	 */
	public DesignMap getSystemDesignMap(int systemIndex, int soldierType) {
		Collection<DesignMap> collection = designMapInfo.values();
		if(collection != null) {
			for(DesignMap designMap : collection) {
				if(designMap.getType() == soldierType && designMap.getSystemIndex() == systemIndex && designMap.getIndex() ==0 && designMap.getBuildIndex() == 0) {
					return designMap;
				}
			}
		}
		return null;
	}
	
//	public int getMaxBuildIndex(int systemIndex, int type, ) {
//		int buildIndex = -1;
//		Collection<DesignMap> collection = designMapInfo.values();
//		for(DesignMap designMap : collection) {
//			if(designMap.getSystemIndex() == systemIndex && designMap.getType() == type) {
//				if(designMap.getBuildIndex() != 0 && designMap.getBuildIndex() > buildIndex) {
//					buildIndex = designMap.getBuildIndex();
//				}
//			}
//		}
//		return buildIndex;
//	}
	
	/**
	 * 判断当前位置上是否存在图纸
	 * @param soldierType
	 * @param systemIndex
	 * @param buildIndex
	 * @param index
	 * @return true 当前位置上已经存在图纸 false 不存在
	 */
	public boolean checkPositionExist(int soldierType, int systemIndex, int buildIndex, int index) {
		Collection<DesignMap> collection = designMapInfo.values();
		for(DesignMap designMap : collection) {
			if(designMap.getType() == soldierType && designMap.getSystemIndex() == systemIndex) {
				if(designMap.getBuildIndex() != 0 && designMap.getBuildIndex() >= buildIndex) {
					return true;
				}
				
				if(designMap.getIndex() != 0 && designMap.getIndex() == index) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jBaseData = new JBaseData();
		List<JBaseData> designBaseDatas = new ArrayList<JBaseData>();
		
		for(DesignMap designMap :designMapInfo.values()) {
			designBaseDatas.add(designMap.toJBaseData());
		}
		
		jBaseData.put("designBaseDatas", designBaseDatas);
		return jBaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		List<JBaseData> designBaseDatas = jBaseData.getSeqBaseData("designBaseDatas");
		if(designBaseDatas != null && !designBaseDatas.isEmpty()) {
			for(JBaseData jBaseData2 : designBaseDatas) {
				DesignMap designMap = new DesignMap();
				designMap.fromJBaseData(jBaseData2);
				this.designMapInfo.put(designMap.getId(), designMap);
			}
		}
	}
	
}
