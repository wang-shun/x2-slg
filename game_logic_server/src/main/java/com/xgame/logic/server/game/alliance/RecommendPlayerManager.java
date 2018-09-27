package com.xgame.logic.server.game.alliance;

import java.util.List;

import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.utils.sort.MapRankableComparator;
import com.xgame.logic.server.core.utils.sort.SmartRank;
import com.xgame.logic.server.game.alliance.enity.RecommandPlayerRankable;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 联盟加入推荐玩家列表
 * @author jacky.jiang
 *
 */
@Component
public class RecommendPlayerManager {
	
	/**
	 * 推荐排行
	 */
	private SmartRank recommendRank = new SmartRank(new MapRankableComparator("loginTime DESC, vipLevel DESC, fightPower DESC, level DESC"), 200);
	
	/**
	 * 刷新排行榜
	 * @param player
	 * @param loginTime
	 * @param vipLevel
	 * @param fightPower
	 * @param level
	 */
	public void refreshRank(long id, long loginTime, int vipLevel, long fightPower, int level) {
		RecommandPlayerRankable recommandPlayerRankable = new RecommandPlayerRankable();
		recommandPlayerRankable.setLevel(level);
		recommandPlayerRankable.setFightPower(fightPower);
		recommandPlayerRankable.setOwnerId(id);
		recommandPlayerRankable.setVipLevel(vipLevel);
		recommandPlayerRankable.setLoginTime(loginTime);
		recommendRank.compareAndRefresh(recommandPlayerRankable);
	}
	
	public void refreshRank(Player player) {
		RecommandPlayerRankable recommandPlayerRankable = new RecommandPlayerRankable();
		recommandPlayerRankable.setLevel(player.getLevel());
		recommandPlayerRankable.setFightPower(player.roleInfo().getCurrency().getPower());
		recommandPlayerRankable.setOwnerId(player.getId());
		recommandPlayerRankable.setVipLevel(player.roleInfo().getVipInfo().getVipLevel());
		recommandPlayerRankable.setLoginTime(player.roleInfo().getBasics().getLoginTime());
		recommendRank.compareAndRefresh(recommandPlayerRankable);
	}
	
	/**
	 * 移除排行榜
	 * @param playerId
	 */
	public void removeRank(long playerId) {
		recommendRank.removeAndRefresh(playerId);
	}
	
	public List<Object> getRecommandPlayerList() {
		return recommendRank.getRankList(0, 12);
	}
}
