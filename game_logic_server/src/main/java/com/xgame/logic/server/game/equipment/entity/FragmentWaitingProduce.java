/**
 * 
 */
package com.xgame.logic.server.game.equipment.entity;

import io.protostuff.Tag;

import java.io.Serializable;

/**
 * @author kevin.ouyang
 *
 */
public class FragmentWaitingProduce implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1397075399493169671L;
	
	@Tag(1)
	private int fragmentId;		
	
	/**
	 * 模板ID
	 * @return
	 */
	public int getFragmentId() {
		return fragmentId;
	}

	public void setFragmentId(int fragmentId) {
		this.fragmentId = fragmentId;
	}
}
