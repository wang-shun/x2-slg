/**
 * 
 */
package com.xgame.logic.server.game.timertask.entity.job.data;

import io.protostuff.Tag;

/**
 * @author kevin.ouyang
 *
 */
public class ProduceFragmentTaskData {
	
	@Tag(1)
	private int  itemId;
	
	@Tag(2)
	private int uid;
	
	
	public ProduceFragmentTaskData() {
		
	}
	
	public ProduceFragmentTaskData(int itemId, int uid) {
		super();
		this.itemId = itemId;
		this.uid = uid;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	
}