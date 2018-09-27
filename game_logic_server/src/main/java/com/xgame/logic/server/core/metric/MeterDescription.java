/**
 * 
 */
package com.xgame.logic.server.core.metric;

import java.util.HashMap;

import com.xgame.logic.server.core.utils.JsonUtil;

/**
 * 测量描述
 * @author jiangxt
 *
 */
public class MeterDescription extends HashMap<String, Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6906708132706781033L;

	@Override
	public String toString(){
		return JsonUtil.toJSON(this);
	}
}
