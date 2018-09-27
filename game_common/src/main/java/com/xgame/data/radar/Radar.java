/**
 * 
 */
package com.xgame.data.radar;

import io.protostuff.Tag;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kevin.ouyang
 *
 */
public class Radar implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Tag(1)
	private Map<Long, RadarInvadeInfo> w2lPlayerInfoMap;

	public Radar()
	{
		w2lPlayerInfoMap = new ConcurrentHashMap<>();
	}
	
	public Map<Long, RadarInvadeInfo> getW2lPlayerInfoMap() {
		return w2lPlayerInfoMap;
	}
}
