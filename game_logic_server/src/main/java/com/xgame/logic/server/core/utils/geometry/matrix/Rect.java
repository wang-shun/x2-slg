package com.xgame.logic.server.core.utils.geometry.matrix;

import com.xgame.logic.server.core.utils.geometry.data.Vector2;
import com.xgame.logic.server.core.utils.geometry.data.transform.RectTransform;

/**
 *简单的四边形管理类
 *2016-10-29  15:30:32
 *@author ye.yuan
 *
 */
public class Rect {

	private RectTransform spaceTransform = new RectTransform();
	
	public Rect() {
	}
	
	public Rect(int h,int v,int sX,int sY,int width,int height) {
		spaceTransform.getLocation().set(sX, sY);
		spaceTransform.setMatrix(new Matrix(h, v));
		spaceTransform.setCapacityX(width);
		spaceTransform.setCapacityY(height);
		spaceTransform.setLeftUpPoint(new Vector2(sX,(sY+height)));
		spaceTransform.setLeftDownPoint(new Vector2(sX,sY));
		spaceTransform.setRightUpPoint(new Vector2(sX+width,sY+height));
		spaceTransform.setRightDownPoint(new Vector2(sX+width,sY));
		spaceTransform.setCenterPoint(new Vector2(sX+width/2, sY+height/2));
	}

	public RectTransform getSpaceTransform() {
		return spaceTransform;
	}

	public void setSpaceTransform(RectTransform spaceTransform) {
		this.spaceTransform = spaceTransform;
	}
	
}
