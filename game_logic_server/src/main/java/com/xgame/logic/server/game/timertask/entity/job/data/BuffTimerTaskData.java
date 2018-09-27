package com.xgame.logic.server.game.timertask.entity.job.data;

import io.protostuff.Tag;


/**
 * buff时间队列
 * @author jacky.jiang
 *
 */
public class BuffTimerTaskData {

	@Tag(1)
	private String buffId;
	
	@Tag(2)
	private int itemId;
	
	public BuffTimerTaskData() {
		
	}

	public BuffTimerTaskData(String buffId, int itemId) {
		super();
		this.buffId = buffId;
		this.itemId = itemId;
	}

	public String getBuffId() {
		return buffId;
	}

	public void setBuffId(String buffId) {
		this.buffId = buffId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	
}
