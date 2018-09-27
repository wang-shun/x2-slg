package com.xgame.logic.server.game.cross.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.xgame.logic.server.game.world.bean.SpriteBean;
import com.xgame.logic.server.game.world.bean.VectorInfo;
import com.xgame.logic.server.game.world.bean.WorldTerritoryBean;

/**
 * 世界地图坐标点
 * @author jacky.jiang
 *
 */
public class CrossWorldInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5280745863974264521L;

	/**
	 * 精灵信息
	 */
	private List<SpriteBean> spriteBeans = new ArrayList<SpriteBean>();
	
	/**
	 * 行军信息
	 */
	private List<VectorInfo> vectorInfos = new ArrayList<>();
	
	/**
	 * 
	 */
	private List<WorldTerritoryBean> territoryBeanList = new ArrayList<>();


	public List<SpriteBean> getSpriteBeans() {
		return spriteBeans;
	}

	public void setSpriteBeans(List<SpriteBean> spriteBeans) {
		this.spriteBeans = spriteBeans;
	}

	public List<VectorInfo> getVectorInfos() {
		return vectorInfos;
	}

	public void setVectorInfos(List<VectorInfo> vectorInfos) {
		this.vectorInfos = vectorInfos;
	}

	public List<WorldTerritoryBean> getTerritoryBeanList() {
		return territoryBeanList;
	}

	public void setTerritoryBeanList(List<WorldTerritoryBean> territoryBeanList) {
		this.territoryBeanList = territoryBeanList;
	}
}
