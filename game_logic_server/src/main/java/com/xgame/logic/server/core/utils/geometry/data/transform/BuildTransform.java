package com.xgame.logic.server.core.utils.geometry.data.transform;

import io.protostuff.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.core.utils.geometry.data.Vector2;

/**
 *建筑在每个国家内的位置信息
 *2016-7-14  20:29:21
 *@author ye.yuan
 *
 */
public class BuildTransform extends SpriteTransform implements JBaseTransform{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8743711451461286532L;
	
	
	/**
	 * 模版id
	 */
	private int templateId;

	/**
	 * 占用节点坐标缓存
	 */
	@Tag(4)
	private Map<Integer, Vector2> nodePoints = new ConcurrentHashMap<>();
	
	/**
	 * 描边点
	 */
	@Tag(5)
	private Map<Integer, Vector2> borderPoints = new ConcurrentHashMap<>();
	
	
	public BuildTransform() {
		
	}
	
	public BuildTransform(long uid, int templateId) {
		super(uid);
		this.templateId = templateId;
	}


	public Map<Integer, Vector2> getNodePoints() {
		return nodePoints;
	}


	public void setNodePoints(Map<Integer, Vector2> nodePoints) {
		this.nodePoints = nodePoints;
	}


	public Map<Integer, Vector2> getBorderPoints() {
		return borderPoints;
	}


	public void setBorderPoints(Map<Integer, Vector2> borderPoints) {
		this.borderPoints = borderPoints;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData baseData = new JBaseData();
		baseData.put("templateId", templateId);
		baseData.put("uid", getUid());
		baseData.put("lastLocation", getLastLocation().toJBaseData());
		baseData.put("location", getLocation().toJBaseData());
		
		List<JBaseData> nodePoints = new ArrayList<JBaseData>();
		for(Map.Entry<Integer, Vector2> entry : this.nodePoints.entrySet()) {
			JBaseData vector2JBase = entry.getValue().toJBaseData();
			vector2JBase.put("nodeId", entry.getKey());
			nodePoints.add(vector2JBase);
		}
		baseData.put("nodePoints", nodePoints);
		
		List<JBaseData> borderPoints = new ArrayList<JBaseData>();
		for(Map.Entry<Integer, Vector2> entry : this.borderPoints.entrySet()) {
			JBaseData vector2JBase = entry.getValue().toJBaseData();
			vector2JBase.put("nodeId", entry.getKey());
			borderPoints.add(vector2JBase);
		}
		baseData.put("borderPoints", borderPoints);
		
		return baseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.templateId = jBaseData.getInt("templateId", 0);
		this.setUid(jBaseData.getInt("uid", 0));
		
		Vector2 lastLocation = new Vector2();
		lastLocation.fromJBaseData(jBaseData.getBaseData("lastLocation"));
		this.setLastLocation(lastLocation);
		
		Vector2 location = new Vector2();
		location.fromJBaseData(jBaseData.getBaseData("location"));
		this.setLocation(location);
		
		List<JBaseData> nodePoints = jBaseData.getSeqBaseData("nodePoints");
		for(JBaseData nodeBaseData : nodePoints) {
			Vector2 vector2 = new Vector2();
			vector2.fromJBaseData(nodeBaseData);
			int nodeId = nodeBaseData.getInt("nodeId", 0);
			this.nodePoints.put(nodeId, vector2);
		}
		
		List<JBaseData> borderPoints = jBaseData.getSeqBaseData("borderPoints");
		for(JBaseData borderJBaseData : borderPoints) {
			Vector2 vector2 = new Vector2();
			vector2.fromJBaseData(borderJBaseData);
			int nodeId = borderJBaseData.getInt("nodeId", 0);
			this.borderPoints.put(nodeId, vector2);
		}
	}
	
}
