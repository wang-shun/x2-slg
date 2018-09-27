package com.xgame.drop;

/**
 * 掉落
 * @author jacky.jiang
 *
 */
public interface IDrop {

	/**
	 * 获取掉落信息
	 * @return
	 */
	public int getValue();
	
	/**
	 * 道具id
	 * @return
	 */
	public int getTid();
	
	/**
	 * 道具数量
	 * @return
	 */
	public int getNum();
	
	/**
	 * 转换成String
	 * @return
	 */
	public String toString();
}
