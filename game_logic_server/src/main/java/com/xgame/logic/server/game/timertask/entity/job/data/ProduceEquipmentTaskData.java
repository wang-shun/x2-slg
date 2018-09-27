/**
 * 
 */
package com.xgame.logic.server.game.timertask.entity.job.data;

import io.protostuff.Tag;

/**
 * 生产装备
 * @author kevin.ouyang
 *
 */
public class ProduceEquipmentTaskData {
	
	/**
	 * 装备id
	 */
	@Tag(1)
	private int  equipId;
	
	/**
	 * 建筑id
	 */
	@Tag(2)
	private int buildId;
	
	
	public ProduceEquipmentTaskData() {
		
	}
	

	public ProduceEquipmentTaskData(int equipId, int buildId) {
		this.equipId = equipId;
		this.buildId = buildId;
	}

	public int getEquipId() {
		return equipId;
	}

	public void setEquipId(int equipId) {
		this.equipId = equipId;
	}

	public int getBuildId() {
		return buildId;
	}

	public void setBuildId(int buildId) {
		this.buildId = buildId;
	}
	
}
