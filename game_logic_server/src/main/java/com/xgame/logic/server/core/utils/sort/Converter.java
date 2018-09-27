/**
 * 
 */
package com.xgame.logic.server.core.utils.sort;

/**
 * 抽象的转换者接口
 * @author jacky
 *
 */
public interface Converter<S, T>{

	/**
	 * 转换
	 * @param source 源对象
	 * @param obects 附加参数
	 * @return
	 */
	T convert(S source, Object...obects);
	
}
