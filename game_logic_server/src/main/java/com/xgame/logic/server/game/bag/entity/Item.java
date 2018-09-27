package com.xgame.logic.server.game.bag.entity;

import io.protostuff.Tag;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

import lombok.Getter;
import lombok.Setter;

/**
 * 道具
 * @author lmj
 *
 */
public class Item implements Serializable,Comparable<Item>,JBaseTransform {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1397075399493169671L;
	
	@Tag(1)
	@Setter
	@Getter
	private long id;	//唯一ID
	
	@Tag(2)
	@Setter
	@Getter
	private int itemId;	//模板ID
	
	@Tag(3)
	@Setter
	@Getter
	private volatile int num;	//数量
	
	@Tag(4)
	@Setter
	@Getter
	private long startTime; //获得时间
	
	/**
	 * 来源信息
	 * <ul>
	 * 		<li>来源id</li>
	 * 		<li>道具数量</li>
	 * </ul
	 */
	@Tag(5)
	@Setter
	@Getter
	private Map<Integer, Integer> originInfo = new HashMap<Integer, Integer>();

	@Override
	public int compareTo(Item item) {
		if(id < item.id) {
			return 1;
		} else if(id > item.id) {
			return -1;
		} else {
			if(this.num < item.num) {
				return -1;
			} else if(this.num > item.num) {
				return 1;
			}
		}
		return 0;
	}
	
	/**
	 * 创建道具
	 * @param itemId
	 * @param num
	 * @param id
	 * @return
	 */
	public static Item valueOf(int itemId, int num, long id) {
		Item item = new Item();
		item.setId(id);
		item.setItemId(itemId);
		item.setNum(num);
		item.setStartTime(new Date().getTime() / 1000);
		return item;
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jBaseData = new JBaseData();
		jBaseData.put("id", id);
		jBaseData.put("itemId", itemId);
		jBaseData.put("num", num);
		jBaseData.put("startTime", startTime);
		return jBaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.id = jBaseData.getLong("id", 0);
		this.itemId = jBaseData.getInt("itemId", 0);
		this.num = jBaseData.getInt("num", 0);
		this.startTime = jBaseData.getLong("startTime", 0);
	}

}
