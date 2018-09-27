package com.xgame.config.attribute;

import java.util.HashMap;

import com.google.common.collect.HashBasedTable;

/**
 *把属性表达式转换成使用对象
 *本身的map k=属性id   v=这个id属性每级附加值和全部附加目标 方便属性承载体AttributeObject的子类重算属性的时候  快速获取值 
 *但如果是附加者重算属性,如果使用这层结构将导致多次无效的循环(因为有些属性不是每级都加)
 *所以有了第二个结构   libraryConfs  专门在附加值重算的时候使用, 二维map 可理解为带4象限的2维度数组    
 *c = 附加者id  r = 等级      v = 本等级需要附加的 全部属性map 
 *2016-11-14  16:28:10
 *@author ye.yuan
 *
 */
public class AttributeConfMap extends HashMap<Integer, AttributeAppenderConf>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 本属性所属配置id
	 */
	public int confId;
	

	/**
	 * 按目标节点和等级 确定的属性map
	 */
	private HashBasedTable<Integer, Integer, LibraryConf> libraryConfs = HashBasedTable.create();
	
	
	public AttributeConfMap(int confId) {
		super();
		this.confId = confId;
	}

	
	public HashBasedTable<Integer, Integer, LibraryConf> getLibraryConfs() {
		return libraryConfs;
	}

}
