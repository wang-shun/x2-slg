package com.xgame.logic.server.game.country;

import com.xgame.logic.server.core.utils.geometry.data.transform.BuildTransform;
import com.xgame.logic.server.game.country.bean.TransformBean;
import com.xgame.logic.server.game.country.bean.Vector2Bean;

/**
 *
 *2016-11-04  15:31:40
 *@author ye.yuan
 *
 */
public class CountryMsgGener {

	public static TransformBean toTransformBean(BuildTransform buildTransform){
		TransformBean transformBean = new TransformBean();
		transformBean.uid=(int)buildTransform.getUid();
		Vector2Bean vector2 = new Vector2Bean();
		vector2.x=buildTransform.getLocation().getX();
		vector2.y=buildTransform.getLocation().getY();
		transformBean.vector2Bean=vector2;
		return transformBean;
	}
	
}
