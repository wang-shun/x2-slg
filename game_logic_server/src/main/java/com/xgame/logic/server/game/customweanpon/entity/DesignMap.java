package com.xgame.logic.server.game.customweanpon.entity;

import java.util.ArrayList;
import java.util.List;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.game.customweanpon.bean.PartBean;

/**
 * 图纸
 * @author jacky.jiang
 *
 */
public class DesignMap implements JBaseTransform {
	
	/**
	 * 图纸id
	 */
	private long id;
	
	/**
	 * 类型
	 */
	private int type;
	
	/**
	 * 系统兵种序列号(原DinPanIndex)
	 */
	private int systemIndex;

	/**
	 * 位置顺序(系统兵种index=0)
	 */
	private int index;
	
	/**
	 * 建造顺序
	 */
	private int buildIndex;
	
	/**
	 * 版本号
	 */
	private int version;
	
	/**
	 * 图纸名字
	 */
	private String name;
	
	
	/**
	 * 图纸是否解锁
	 */
	private boolean unlock = false;
	
	
	private List<PartBean> partList = new ArrayList<>();

	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public int getSystemIndex() {
		return systemIndex;
	}


	public void setSystemIndex(int systemIndex) {
		this.systemIndex = systemIndex;
	}


	public int getIndex() {
		return index;
	}


	public void setIndex(int index) {
		this.index = index;
	}


	public int getBuildIndex() {
		return buildIndex;
	}


	public void setBuildIndex(int buildIndex) {
		this.buildIndex = buildIndex;
	}


	public int getVersion() {
		return version;
	}


	public void setVersion(int version) {
		this.version = version;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public List<PartBean> getPartList() {
		return partList;
	}


	public void setPartList(List<PartBean> partList) {
		this.partList = partList;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}
	
	public boolean isUnlock() {
		return unlock;
	}

	public void setUnlock(boolean unlock) {
		this.unlock = unlock;
	}

	public List<Integer> partBeanIdList() {
		List<Integer> partBeanIds = new ArrayList<Integer>();
		for(PartBean partBean : partList) {
			partBeanIds.add(partBean.partId);
		}
		return partBeanIds;
	}


	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("id", id);
		jbaseData.put("type", type);
		jbaseData.put("systemIndex", systemIndex);
		jbaseData.put("index", index);
		jbaseData.put("buildIndex", buildIndex);
		jbaseData.put("version", version);
		jbaseData.put("name", name);
		jbaseData.put("unlock", unlock);
		
		List<JBaseData> partList = new ArrayList<JBaseData>();
		for (int i = 0; i < this.partList.size(); i++) {
			PartBean partBean = this.partList.get(i);
			JBaseData jBaseData2 = new JBaseData();
			jBaseData2.put("partId", partBean.partId);
			jBaseData2.put("position", partBean.position);
			partList.add(jBaseData2);
		}
		
		jbaseData.put("partList", partList);
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		this.id = jBaseData.getLong("id", 0);
		this.type = jBaseData.getInt("type", 0);
		this.systemIndex = jBaseData.getInt("systemIndex", 0);
		this.index = jBaseData.getInt("index", 0);
		this.buildIndex = jBaseData.getInt("buildIndex", 0);
		this.version = jBaseData.getInt("version", 0);
		this.name = jBaseData.getString("name", "");
		this.unlock = jBaseData.getBoolean("unlock", false);
		
		List<JBaseData> partList = jBaseData.getSeqBaseData("partList");
		if (partList != null && !partList.isEmpty()) {
			for (JBaseData jBaseData2 : partList) {
				PartBean partBean = new PartBean();
				partBean.partId = jBaseData2.getInt("partId", 0);
				partBean.position = jBaseData2.getInt("position", 0);
				this.partList.add(partBean);
			}
		}
	}
}
