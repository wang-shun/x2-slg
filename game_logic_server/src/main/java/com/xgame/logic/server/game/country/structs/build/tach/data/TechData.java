package com.xgame.logic.server.game.country.structs.build.tach.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

/**
 *
 *2016-11-15  15:37:42
 *@author ye.yuan
 *
 */
public class TechData implements Serializable, JBaseTransform{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private Map<Integer, Tech> techs = new HashMap<>();


	public Map<Integer, Tech> getTechs() {
		return techs;
	}


	public void setTechs(Map<Integer, Tech> techs) {
		this.techs = techs;
	}


	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		List<JBaseData> techsJBaseList = new ArrayList<JBaseData>();
		for (Map.Entry entry : techs.entrySet()) {
			techsJBaseList.add(((JBaseTransform) entry.getValue()).toJBaseData());
		}
		jbaseData.put("techs", techsJBaseList);
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		List<JBaseData> jBaseDatas = jBaseData.getSeqBaseData("techs");
		for(JBaseData jBaseData2  : jBaseDatas) {
			Tech tech = new Tech();
			tech.fromJBaseData(jBaseData2);
			this.techs.put(tech.getId(), tech);
		}
	}


}
