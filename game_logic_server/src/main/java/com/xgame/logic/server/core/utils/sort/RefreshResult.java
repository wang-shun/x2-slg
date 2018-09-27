/**
 * 
 */
package com.xgame.logic.server.core.utils.sort;

/**
 * 刷新结果
 * @author jacky
 *
 */
public class RefreshResult {
	
	/**排名是否有变化*/
	private boolean refreshed;
	
	/**新加入排行榜的元素*/
	private MapRankable add;
	
	/**被挤出排行榜的元素*/
	private MapRankable remove;
	
	/**不是新进也没有挤出其他元素仅仅是变更属性的元素*/
	private MapRankable update;

	
	/**
	 * 仅仅是发生了刷新没有元素新进也没有元素被挤出
	 * @param rankable
	 * @param refreshed true-排名发生了变化 
	 * @return
	 */
	public static RefreshResult update(MapRankable rankable, boolean refreshed){
		RefreshResult result = new RefreshResult();
		result.refreshed = refreshed;
		result.update = rankable;
		return result;
	}
	
	/**
	 * 仅仅发生新进
	 * @param rankable
	 * @return
	 */
	public static RefreshResult add(MapRankable rankable){
		RefreshResult result = new RefreshResult();
		result.refreshed = true;
		result.add = rankable;
		return result;
	}
	
	/**
	 * 新进元素并且挤出了另一个元素
	 * @param add
	 * @param remove
	 * @return
	 */
	public static RefreshResult replace(MapRankable add, MapRankable remove){
		RefreshResult result = new RefreshResult();
		result.refreshed = true;
		result.add = add;
		result.remove = remove;
		return result;
	}
	
	/**
	 * 直接移除了一个元素
	 * @param remove
	 * @return
	 */
	public static RefreshResult remove(MapRankable remove){
		RefreshResult result = new RefreshResult();
		result.refreshed = true;
		result.remove = remove;
		return result;
	}
	
	
	public boolean isRefreshed() {
		return refreshed;
	}

	public MapRankable getAdd() {
		return add;
	}

	public MapRankable getRemove() {
		return remove;
	}

	public MapRankable getUpdate() {
		return update;
	}
}
