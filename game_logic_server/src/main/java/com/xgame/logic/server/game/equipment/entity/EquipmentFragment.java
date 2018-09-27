package com.xgame.logic.server.game.equipment.entity;

import io.protostuff.Tag;

import java.io.Serializable;


/**
 * 植入体合成装备的材料
 * @author kevin.ouyang
 *
 */
public class EquipmentFragment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1397075399493169671L;

	@Tag(1)
	private long id;	
	@Tag(2)
	private int materialId;	
	@Tag(3)
	private volatile int num;	
	
	/**
	 * 唯一ID
	 * @return
	 */
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * 模板ID
	 * @return
	 */
	public int getMaterialId() {
		return materialId;
	}

	public void setMaterialId(int materialId) {
		this.materialId = materialId;
	}

	/**
	 * 数量
	 * @return
	 */
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}


