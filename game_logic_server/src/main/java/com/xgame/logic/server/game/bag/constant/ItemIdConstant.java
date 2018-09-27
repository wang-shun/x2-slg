package com.xgame.logic.server.game.bag.constant;

import com.xgame.config.global.GlobalPirFactory;

/**
 * 道具id常量表
 * @author jacky.jiang
 *
 */
public interface ItemIdConstant {
	
	/**
	 * 免侦查
	 */
	int AVOID_INVESTIGATE = Integer.valueOf(GlobalPirFactory.get(500901).getValue());
	
	/**
	 * 派兵上限
	 */
	int SOLDIER_LIMIT_UP = Integer.valueOf(GlobalPirFactory.get(500902).getValue());
	
	/**
	 * 侦查伪装
	 */
	int INVESTIGATE_PRETEND = Integer.valueOf(GlobalPirFactory.get(500903).getValue());
	
	/**
	 * 日常任务重置
	 */
	int DAILY_TASK_RESET = Integer.valueOf(GlobalPirFactory.get(500904).getValue());
	
	/**
	 * 联盟任务重置
	 */
	int ALLIANCE_TASK_RESET = Integer.valueOf(GlobalPirFactory.get(500905).getValue());
	
	/**
	 * 随机传送
	 */
	int RANDOM_TRANSFER = Integer.valueOf(GlobalPirFactory.get(500906).getValue());
	
	/**
	 * 联盟传送
	 */
	int ALLIANCE_TRANSFER = Integer.valueOf(GlobalPirFactory.get(500907).getValue());
	
	/**
	 * 领土标志变更
	 */
	int TERRITORY_FLAG_CHANGE = Integer.valueOf(GlobalPirFactory.get(500908).getValue());
	
	/**
	 * 撤军令
	 */
	int MARCH_BACK_ITEM = Integer.valueOf(GlobalPirFactory.get(500909).getValue());
	
	/**
	 * 建筑材料
	 */
	int BUILDING_MATERIAL = Integer.valueOf(GlobalPirFactory.get(500910).getValue());
	
	/**
	 * 固定位置传送
	 */
	int FIX_POSITION_TRANSFER = Integer.valueOf(GlobalPirFactory.get(500911).getValue());
	
	/**
	 * 新手传送
	 */
	int NEW_PLAYER_TRANSFER = Integer.valueOf(GlobalPirFactory.get(500912).getValue());
	
	/**
	 * 统帅卡
	 */
	int CAPTIAN_CARD = Integer.valueOf(GlobalPirFactory.get(500913).getValue());
	
	/**
	 * 形象变更
	 */
	int CHANGE_STYLE = Integer.valueOf(GlobalPirFactory.get(500914).getValue());
	
	/**
	 * 姓名变更
	 */
	int CHANGE_NAME = Integer.valueOf(GlobalPirFactory.get(500915).getValue());
	
	/**
	 * 天赋重置
	 */
	int TALENT_RESET = Integer.valueOf(GlobalPirFactory.get(500916).getValue());
	
	/**
	 * 大喇叭
	 */
	int TYPHON = Integer.valueOf(GlobalPirFactory.get(500917).getValue());
	
	/**
	 * 建筑拆除
	 */
	int BUILDING_REMOVE = Integer.valueOf(GlobalPirFactory.get(500918).getValue());
}
