package com.xgame.logic.server.game.bag.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 使用道具
 * @author zehong.he
 *
 */
public class UseItemEventObject extends GameLogEventObject {
	
	public UseItemEventObject(Player player,int itemTid,int tab,int num) {
		super(player,EventTypeConst.USE_ITEM);
		this.itemTid = itemTid;
		this.tab = tab;
		this.num = num;
	}

	/**
	 * 道具id
	 */
	private int itemTid;
	
	/**
	 * 1资源，2 加速，3 战争，4其他
	 */
	private int tab;

	/**
	 * 使用数量
	 */
	private int num;
	
	
	/**
	 * @return the itemTid
	 */
	public int getItemTid() {
		return itemTid;
	}

	/**
	 * @param itemTid the itemTid to set
	 */
	public void setItemTid(int itemTid) {
		this.itemTid = itemTid;
	}

	/**
	 * @return the tab
	 */
	public int getTab() {
		return tab;
	}

	/**
	 * @param tag the tab to set
	 */
	public void setTab(int tab) {
		this.tab = tab;
	}

	/**
	 * @return the num
	 */
	public int getNum() {
		return num;
	}

	/**
	 * @param num the num to set
	 */
	public void setNum(int num) {
		this.num = num;
	}
	
	
}
