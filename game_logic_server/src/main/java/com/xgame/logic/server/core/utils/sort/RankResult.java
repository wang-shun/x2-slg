/**
 * 
 */
package com.xgame.logic.server.core.utils.sort;



/**
 * 排名结果对象
 * @author jacky
 *
 */
public class RankResult<VO> {

	/**
	 * 分页对象
	 */
	private Page<VO> page = new Page<VO>();
	
	/**
	 * 自己的排行对象
	 */
	private MapRankable owner;

	public Page<VO> getPage() {
		return page;
	}

	public void setPage(Page<VO> page) {
		this.page = page;
	}

	public int getOwnerRank() {
		if(owner != null){
			return owner.getRank();
		}
		return -1;
	}

	public MapRankable getOwner() {
		return owner;
	}

	public void setOwner(MapRankable owner) {
		this.owner = owner;
	}
	
}
