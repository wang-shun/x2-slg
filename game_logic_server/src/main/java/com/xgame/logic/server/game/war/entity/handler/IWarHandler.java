package com.xgame.logic.server.game.war.entity.handler;

import com.xgame.logic.server.game.war.constant.WarType;
import com.xgame.logic.server.game.war.entity.Battle;
import com.xgame.logic.server.game.war.entity.WarFightParam;
import com.xgame.logic.server.game.war.entity.report.WarEndReport;


/**
 * 战斗接口
 * @author jacky.jiang
 *
 */
public interface IWarHandler {
	
	/**
	 * 战斗类型
	 * @return
	 */
	public WarType getWarType();
	
	/**
	 * 战斗初始化
	 * @param warFightParam
	 * @return
	 */
	public Battle init(WarFightParam warFightParam);
	
	/**
	 * 战斗计算
	 * @param battle
	 * @param rwd
	 * @param battleCallback
	 */
	public void fight(Battle battle);
	
	/**
	 * 战斗结束
	 * @param battle
	 * @param warEndReport
	 */
	public void fightEnd(Battle battle, WarEndReport warEndReport);
}
