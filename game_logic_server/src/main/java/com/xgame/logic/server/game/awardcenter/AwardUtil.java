package com.xgame.logic.server.game.awardcenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import com.xgame.common.AwardConfList;
import com.xgame.common.ItemConf;
import com.xgame.config.equipment.EquipmentPir;
import com.xgame.config.equipment.EquipmentPirFactory;
import com.xgame.config.items.ItemsPir;
import com.xgame.config.items.ItemsPirFactory;
import com.xgame.config.peiJian.PeiJianPir;
import com.xgame.config.peiJian.PeiJianPirFactory;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.game.awardcenter.entity.AwardDB;
import com.xgame.logic.server.game.bag.ItemKit;
import com.xgame.logic.server.game.constant.SystemEnum;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.utils.StringUtil;

/**
 * 奖励中心系统 发奖可以走这里 2016-12-17 17:19:30
 * 
 * @author ye.yuan
 *
 */
@Slf4j
public enum AwardUtil {

	/**
	 * 道具发奖
	 */
	ITEM(6) {
		public AwardDB giveCenter(Player player, int id, int num, int type, GameLogSource gameLogSource) {
			try {
				ItemsPir itemsPir = ItemsPirFactory.get(id);
				if (itemsPir != null) {
					return genAwardDB(player, id, num, type, gameLogSource);
				} else {
					log.info("配置问题 道具"+id+"找不到");
				}
			} catch (Exception e) {
				log.info("奖励中心发奖失败 id:" + id + " num: " + num + "  type:" + type + " \r\n"
						+ StringUtil.getExceptionTrace(e));
			} finally {

			}
			return null;
		}

		public void receiveCenter(Player player, AwardDB awardDB, GameLogSource gameLogSource) {
			try {
				SystemEnum valueOf = SystemEnum.valueOf(awardDB.getType());
				if (valueOf == null) {
					valueOf = SystemEnum.COMMON;
				}
				ItemKit.addItemAndTopic(player, awardDB.getAwardId(), awardDB.getNum(), valueOf, gameLogSource);
			} catch (NullPointerException e) {
				log.info("player or awardDB can not be null \r\n" + StringUtil.getExceptionTrace(e));
			}
		}

	},

	/**
	 * 配件发奖 只能解锁一个
	 */
	PEIJIAN(7) {

		public AwardDB giveCenter(Player player, int id, int num, int type, GameLogSource gameLogSource) {
			try {
				PeiJianPir jianPir = PeiJianPirFactory.get(id);
				if (jianPir != null) {
					return genAwardDB(player, id, num, type, gameLogSource);
				} else {
					log.info("give object not have ");
				}
			} catch (Exception e) {
				log.info("item give fail id:" + id + " num: " + num + "  type:" + type + " \r\n"
						+ StringUtil.getExceptionTrace(e));
			} finally {

			}
			return null;
		}

		public void receiveCenter(Player player, AwardDB awardDB, GameLogSource gameLogSource) {
			try {
				player.getCustomWeaponManager().unlockPeijian(player, awardDB.getAwardId(), gameLogSource);
			} catch (Exception e) {
				log.info("give peijian fail  \r\n" + StringUtil.getExceptionTrace(e));
			}
		}

	},

	/**
	 * 发放植入体 可以发送多个,但每个按照单个奖励放入
	 */
	EQUIT(5) {

		public AwardDB giveCenter(Player player, int id, int num, int type, GameLogSource gameLogSource) {
			try {
				EquipmentPir equipmentPir = EquipmentPirFactory.get(id);
				if (equipmentPir != null) {
					return genAwardDB(player, id, num, type, gameLogSource);
				} else {
					log.info("give object not have ");
				}
			} catch (Exception e) {
				log.info("item give fail id:" + id + " num: " + num + "  type:" + type + " \r\n"
						+ StringUtil.getExceptionTrace(e));
			} finally {

			}
			return null;
		}

		public void receiveCenter(Player player, AwardDB awardDB, GameLogSource gameLogSource) {
			try {
				for(int i=0;i<awardDB.getNum();i++){
					player.getEquipmentManager().addEquipment(awardDB.getAwardId(), gameLogSource);
				}
			} catch (Exception e) {
				log.info("give peijian fail  \r\n" + StringUtil.getExceptionTrace(e));
			}
		}
	};

	public int idType;

	AwardUtil(int idType) {
		this.idType = idType;
	}

	/**
	 * 奖励放入奖励中心
	 * 
	 * @param player
	 * @param id
	 * @param num
	 * @param type
	 * @param param
	 */
	public AwardDB giveCenter(Player player, int id, int num, int type, GameLogSource gameLogSource) {
		return null;
	}

	/**
	 * 奖励中心领取奖励
	 * 
	 * @param player
	 * @param awardDB
	 * @param param
	 */
	public void receiveCenter(Player player, AwardDB awardDB, GameLogSource gameLogSource) {

	}

	public AwardDB genAwardDB(Player player, int id, int num, int type, GameLogSource gameLogSource) {
		AwardDB awardDB = new AwardDB();
		awardDB.setAwardId(id);
		awardDB.setNum(num);
		awardDB.setOrdinal(ordinal());
		awardDB.setType(type);
		awardDB.setGameLogSource(gameLogSource);
		player.getAwardCenterManager().addAward(awardDB);
		return awardDB;
	}

	/**
	 * 奖励放入奖励中心
	 * 
	 * @param player
	 * @param id
	 * @param num
	 * @param type
	 * @param param
	 */
	public static void awardToCenter(Player player, AwardConfList confList, int type, GameLogSource gameLogSource) {
		if (confList != null) {
			List<AwardDB> dbs = new ArrayList<>();
			Iterator<ItemConf> iterator = confList.iterator();
			while (iterator.hasNext()) {
				ItemConf next = iterator.next();
				AwardUtil awardSystem = MAP.get(Integer.toString(next.getTid()).length());
				if (awardSystem != null) {
					AwardDB awardDB = awardSystem.giveCenter(player, next.getTid(), next.getNum(), type, gameLogSource);
					if (awardDB != null) {
						dbs.add(awardDB);
					}
				}
			}
			
			// 奖励中心
			player.getAwardCenterManager().sendAwardsToClient(dbs);
		}
	}

	public final static Map<Integer, AwardUtil> MAP = new HashMap<>();

	static {
		AwardUtil[] values = values();
		for (int i = 0; i < values.length; i++) {
			MAP.put(values[i].idType, values[i]);
		}
	}
}
