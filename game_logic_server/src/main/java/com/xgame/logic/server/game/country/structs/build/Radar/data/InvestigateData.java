package com.xgame.logic.server.game.country.structs.build.Radar.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xgame.logic.server.game.country.bean.Vector2Bean;

import io.protostuff.Tag;


/**
 *
 *2017-1-06  11:22:54
 *@author ye.yuan
 *
 */
public class InvestigateData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1722905991764321792L;

	@Tag(1)
	private Map<Integer,RadarBuild> radarAllBuild = new HashMap<>();
	
	@Tag(2)
	private List<RadarTower> towerBuilds = new ArrayList<>();
	
	@Tag(3)
	private List<RadarDefebse> defebseBuilds = new ArrayList<>();
	
	@Tag(4)
	private Vector2Bean vector2Bean;
	
	@Tag(5)
	private int spriteType;
	
	@Tag(6)
	private List<RadarSoldier> radrSoldiers = new ArrayList<>();


	public List<RadarTower> getTowerBuilds() {
		return towerBuilds;
	}

	public void setTowerBuilds(List<RadarTower> towerBuilds) {
		this.towerBuilds = towerBuilds;
	}

	public List<RadarDefebse> getDefebseBuilds() {
		return defebseBuilds;
	}

	public void setDefebseBuilds(List<RadarDefebse> defebseBuilds) {
		this.defebseBuilds = defebseBuilds;
	}

	public Map<Integer, RadarBuild> getRadarAllBuild() {
		return radarAllBuild;
	}

	public void setRadarAllBuild(Map<Integer, RadarBuild> radarAllBuild) {
		this.radarAllBuild = radarAllBuild;
	}

	public int getSpriteType() {
		return spriteType;
	}

	public void setSpriteType(int spriteType) {
		this.spriteType = spriteType;
	}

	public List<RadarSoldier> getRadrSoldiers() {
		return radrSoldiers;
	}

	public void setRadrSoldiers(List<RadarSoldier> radrSoldiers) {
		this.radrSoldiers = radrSoldiers;
	}

	public Vector2Bean getVector2Bean() {
		return vector2Bean;
	}

	public void setVector2Bean(Vector2Bean vector2Bean) {
		this.vector2Bean = vector2Bean;
	}
	
}
