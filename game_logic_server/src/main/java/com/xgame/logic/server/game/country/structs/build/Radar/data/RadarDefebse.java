package com.xgame.logic.server.game.country.structs.build.Radar.data;

import com.xgame.logic.server.core.utils.geometry.data.Vector2;

import io.protostuff.Tag;


/**
 *雷达功能使用的防守驻军
 *2016-12-28  12:01:55
 *@author ye.yuan
 *
 */
public final class RadarDefebse extends RadarBuild{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4364676877197792064L;

	@Tag(4)
	private RadarDefebseSoldier defebseSoldier;
	
	public RadarDefebse(int sid,int uid, int level, Vector2 loaction) {
		super(sid, uid, level, loaction);
	}

	public RadarDefebseSoldier getDefebseSoldier() {
		return defebseSoldier;
	}

	public void setDefebseSoldier(RadarDefebseSoldier defebseSoldier) {
		this.defebseSoldier = defebseSoldier;
	}

}
