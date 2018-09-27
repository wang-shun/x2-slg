package com.xgame.logic.server.game.war.entity.queue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import com.xgame.logic.server.game.war.entity.Battle;
import com.xgame.logic.server.game.war.entity.report.WarEndReport;
import com.xgame.logic.server.game.world.entity.SpriteInfo;
import com.xgame.utils.TimeUtils;


/**
 * 战斗推送延迟队列
 * @author jacky.jiang
 *
 */
public class WarFinishQueue implements Delayed {

	/**
	 * 精灵信息
	 */
	private SpriteInfo spriteInfo;
	
	/**
	 * 战场信息
	 */
	private Battle battle;
	
	/**
	 * 结束战报
	 */
	private WarEndReport warEndReport;
	
	/**
	 * 战斗开始时间
	 */
	private long battleStartTime;
	
	/**
	 * 战斗结束时间
	 */
	private long battleEndTime;
	
	public SpriteInfo getSpriteInfo() {
		return spriteInfo;
	}

	public void setSpriteInfo(SpriteInfo spriteInfo) {
		this.spriteInfo = spriteInfo;
	}

	public long getBattleStartTime() {
		return battleStartTime;
	}

	public void setBattleStartTime(long battleStartTime) {
		this.battleStartTime = battleStartTime;
	}

	public long getBattleEndTime() {
		return battleEndTime;
	}

	public void setBattleEndTime(long battleEndTime) {
		this.battleEndTime = battleEndTime;
	}

	public Battle getBattle() {
		return battle;
	}

	public void setBattle(Battle battle) {
		this.battle = battle;
	}
	
	public WarEndReport getWarEndReport() {
		return warEndReport;
	}

	public void setWarEndReport(WarEndReport warEndReport) {
		this.warEndReport = warEndReport;
	}

	@Override
	public int compareTo(Delayed o) {
		if (this == o) {
			return 0;
		}

		long diff = 0;

		if (o instanceof WarFinishQueue) {
			WarFinishQueue other = (WarFinishQueue) o;
			diff = this.getBattleEndTime() - other.getBattleEndTime();
		} else {
			diff = (getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
		}

		return (diff == 0) ? 0 : ((diff < 0) ? -1 : 1);
	}

	@Override
	public long getDelay(TimeUnit unit) {
		long diff = this.getBattleEndTime() - TimeUtils.getCurrentTimeMillis();
		return unit.convert(diff, TimeUnit.MILLISECONDS);
	}
}
