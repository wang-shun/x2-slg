package com.xgame.logic.server.game.modify;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.xgame.config.global.GlobalPir;
import com.xgame.config.global.GlobalPirFactory;
import com.xgame.config.peiJian.PeiJianPir;
import com.xgame.config.peiJian.PeiJianPirFactory;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.language.view.success.SuccessTipEnum;
import com.xgame.logic.server.core.utils.CurrencyUtil;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.sequance.ItemSequance;
import com.xgame.logic.server.game.constant.CurrencyEnum;
import com.xgame.logic.server.game.country.structs.build.camp.data.ReformSoldier;
import com.xgame.logic.server.game.customweanpon.bean.PartBean;
import com.xgame.logic.server.game.customweanpon.entity.DesignMap;
import com.xgame.logic.server.game.modify.message.ResDestorySoldierMessage;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.player.entity.RoleCurrency;
import com.xgame.logic.server.game.playerattribute.calculation.AttributeCounter;
import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;
import com.xgame.logic.server.game.playerattribute.fightpower.FightPowerKit;
import com.xgame.logic.server.game.soldier.bean.ReformSoldierBean;
import com.xgame.logic.server.game.soldier.constant.SoldierChangeType;
import com.xgame.logic.server.game.soldier.entity.Soldier;
import com.xgame.logic.server.game.soldier.message.ResUpdateSoldierMessage;
import com.xgame.logic.server.game.timertask.constant.TimerTaskCommand;
import com.xgame.logic.server.game.timertask.entity.job.TimerTaskHolder;
import com.xgame.logic.server.game.timertask.entity.job.data.ReformTimeTaskData;


/**
 * 修改厂
 * @author jacky.jiang
 *
 */
@Component
public class ModifyManager {
	
	/**
	 * 销毁装甲
	 * @param player
	 * @param id
	 * @param num
	 * @param isDestory
	 */
	public void destroyArmor(Player player, long id, int num) {
		
		if (num <= 0) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E600200_WEAPON.CODE9.get());
			return;
		}

		// 队列检测
		if (TimerTaskHolder.getTimerTask(TimerTaskCommand.REFORM_QUEUE).checkQueueCapacityMax(player)) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E600200_WEAPON.CODE10.get());
			return;
		}
				
		ResDestorySoldierMessage destoryArmorMsg = new ResDestorySoldierMessage();

		ReformSoldier reformSoldier = player.roleInfo().getSoldierData().getReformSoldier(id);
		if(reformSoldier == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E600200_WEAPON.CODE11.get());
			return;			
		}
		
		// 旧装甲生产所需资源
		double produceoil = 0;
		double producemoney = 0;
		double producesteels = 0;
		double producerare = 0;
		// 修复所需资源
		double recoveroil = 0;
		double recovermoney = 0;
		double recoversteels = 0;
		double recoverrare = 0;

		DesignMap designMap = player.getCustomWeaponManager().queryDesignMapById(player, reformSoldier.getSolderId());
		Iterator<PartBean> iterator3 = designMap.getPartList().iterator();
		while (iterator3.hasNext()) {
			PartBean peiJian = iterator3.next();
			PeiJianPir peijianConf = PeiJianPirFactory.get(peiJian.partId);
			if (peijianConf != null) {
				recovermoney += peijianConf.getFix_cost_cash();
				recoveroil += peijianConf.getFix_cost_oil();
				recoverrare += peijianConf.getFix_cost_earth();
				recoversteels += peijianConf.getFix_cost_steel();

				producemoney += peijianConf.getCost_cash();
				produceoil += peijianConf.getCost_oil();
				producerare += peijianConf.getCost_cash();
				producesteels += peijianConf.getCost_steel();
			}
		}

		GlobalPir globalConf = GlobalPirFactory.get(500000);
		double value = Float.valueOf(globalConf.getValue());
		double oil = (produceoil - recoveroil) * num * value;
		double money = (producemoney - recovermoney) * num * value;
		double steels = (producesteels - recoversteels) * num * value;
		double rare = (producerare - recoverrare) * num * value;
		
		RoleCurrency rc = player.roleInfo().getCurrency();
		if (rc.getOil() < oil || rc.getSteel() < steels || rc.getMoney() < money || rc.getRare() < rare) {// 某资源不足
			Language.ERRORCODE.send(player, ErrorCodeEnum.E600200_WEAPON.CODE11.get());
			return;
		}
		
		CurrencyUtil.increase(player, (int)oil, CurrencyEnum.OIL, GameLogSource.DESTORY_SOLDIER);
		CurrencyUtil.increase(player, (int)money, CurrencyEnum.GLOD, GameLogSource.DESTORY_SOLDIER);
		CurrencyUtil.increase(player, (int)steels, CurrencyEnum.STEEL, GameLogSource.DESTORY_SOLDIER);
		CurrencyUtil.increase(player, (int)rare, CurrencyEnum.RARE, GameLogSource.DESTORY_SOLDIER);
		
		if (reformSoldier.getState() == 1) {
			if (reformSoldier.getNum() > num) {
				reformSoldier.setNum(reformSoldier.getNum() - num);
			} else {
				player.roleInfo().getSoldierData().getReformSoldierHurtTable().remove(id);
			}

		} else {
			if (reformSoldier.getNum() > num) {
				reformSoldier.setNum(reformSoldier.getNum() - num);
			} else {
				player.roleInfo().getSoldierData().getReformSoldierTable().remove(id);
			}
		}
		
		InjectorUtil.getInjector().dbCacheService.update(player);
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E600_WEAPON.CODE2.get());
		destoryArmorMsg.isSuccess = 1;
		destoryArmorMsg.type = designMap.getType();
		destoryArmorMsg.num = num;
		player.send(destoryArmorMsg);
		
		// 士兵更新消息
		ResUpdateSoldierMessage resUpdateSoldierMessage = new ResUpdateSoldierMessage();
		ReformSoldierBean reformSoldierBean = new ReformSoldierBean();
		reformSoldierBean.id = id;
		resUpdateSoldierMessage.reformSoldierList.add(reformSoldierBean);
		player.send(resUpdateSoldierMessage);
		
		CurrencyUtil.send(player);
	}

	/**
	 * 改造装甲
	 * @param player
	 * @param id
	 * @param num
	 * @param isDestory
	 */
	public void reformArmor(Player player, long id, int num) {
		// 队列检测
		if (TimerTaskHolder.getTimerTask(TimerTaskCommand.REFORM_QUEUE).checkQueueCapacityMax(player)) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E600200_WEAPON.CODE14.get());
			return;
		}

		if (num <= 0) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E600200_WEAPON.CODE15.get());
			return;
		}

		ReformSoldier reformSoldier = player.roleInfo().getSoldierData().getReformSoldier(id);
		if (reformSoldier == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E600200_WEAPON.CODE16.get());
			return;
		}
		
		if (num > reformSoldier.getNum()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E600200_WEAPON.CODE15.get());
			return;
		}

		// 旧装甲生产所需资源
		double produceoil = 0;
		double producemoney = 0;
		double producesteels = 0;
		double producerare = 0;
		// 修复所需资源
		double recoveroil = 0;
		double recovermoney = 0;
		double recoversteels = 0;
		double recoverrare = 0;
		float time = 0;

		DesignMap designMap = player.getCustomWeaponManager().queryDesignMapById(player, reformSoldier.getSolderId());
		Iterator<PartBean> iterator3 = designMap.getPartList().iterator();
		while (iterator3.hasNext()) {
			PartBean peiJian = iterator3.next();
			PeiJianPir peijianConf = PeiJianPirFactory.get(peiJian.partId);
			if (peijianConf != null) {
				producemoney += peijianConf.getCost_cash();
				produceoil += peijianConf.getCost_oil();
				producerare += peijianConf.getCost_earth();
				producesteels += peijianConf.getCost_steel();
				if (reformSoldier.getState() == 1) {
					recovermoney += peijianConf.getFix_cost_cash();
					recoveroil += peijianConf.getFix_cost_oil();
					recoverrare += peijianConf.getFix_cost_earth();
					recoversteels += peijianConf.getFix_cost_steel();
					time += peijianConf.getFix_time();
				}
			}
		}

		double newoil = 0;
		double newmoney = 0;
		double newsteels = 0;
		double newrare = 0;
		if (designMap != null) {
			List<PartBean> peijians = designMap.getPartList();
			for (int i = 0; i < peijians.size(); i++) {
				PartBean partBean = peijians.get(i);
				PeiJianPir peijianConf = PeiJianPirFactory.get(partBean.partId);
				if (peijianConf != null) {
					newmoney += peijianConf.getCost_cash();
					newoil += peijianConf.getCost_oil();
					newrare += peijianConf.getCost_earth();
					newsteels += peijianConf.getCost_steel();
				}
			}
		}

		double oil = (newoil - (produceoil - recoveroil)) * num;
		double money = (newmoney - (producemoney - recovermoney)) * num;
		double steels = (newsteels - (producesteels - recoversteels)) * num;
		double rare = (newrare - (producerare - recoverrare)) * num;
		RoleCurrency rc = player.roleInfo().getCurrency();
		if (oil >= 0) {
			if (rc.getOil() < oil) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE11);
				return;
			}
			CurrencyUtil.decrement(player, oil, CurrencyEnum.OIL, GameLogSource.REFIT_SOLDIER);
		} else {
			CurrencyUtil.increase(player, Math.abs(oil), CurrencyEnum.OIL, GameLogSource.REFIT_SOLDIER);
		}

		if (money >= 0) {
			if (rc.getMoney() < money) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE11);
				return;
			}
			CurrencyUtil.decrement(player, money, CurrencyEnum.GLOD, GameLogSource.REFIT_SOLDIER);
		} else {
			CurrencyUtil.increase(player, Math.abs(money), CurrencyEnum.GLOD, GameLogSource.REFIT_SOLDIER);
		}

		if (steels >= 0) {
			if (rc.getSteel() < steels) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE11);
				return;
			}
			CurrencyUtil.decrement(player, steels, CurrencyEnum.STEEL, GameLogSource.REFIT_SOLDIER);
		} else {
			CurrencyUtil.increase(player, Math.abs(steels), CurrencyEnum.STEEL, GameLogSource.REFIT_SOLDIER);
		}

		if (rare >= 0) {
			if (rc.getRare() < rare) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE11);
				return;
			}
			CurrencyUtil.decrement(player, rare, CurrencyEnum.RARE, GameLogSource.REFIT_SOLDIER);
		} else {
			CurrencyUtil.increase(player, Math.abs(rare), CurrencyEnum.RARE, GameLogSource.REFIT_SOLDIER);
		}

		int lastTime = Math.round(time * num);
		lastTime = (int)AttributeCounter.getPlayerAttribute(player.getId(), AttributesEnum.HOSPITAL_RECOVER_RATE, lastTime);
		if (reformSoldier.getState() == 1) {
			if (lastTime > 0) {
				TimerTaskHolder.getTimerTask(TimerTaskCommand.REFORM_QUEUE).register(player, lastTime, new ReformTimeTaskData(0, id, num));
				player.roleInfo().getSoldierData().getRefittingData().setSoldierId(id);
				player.roleInfo().getSoldierData().getRefittingData().setNum(num);
			} else {
				refittingSucc(player, id, num);
			}
			
			InjectorUtil.getInjector().dbCacheService.update(player);
			CurrencyUtil.send(player);
			player.getSoldierManager().send(player);
			
		} else {
			if (reformSoldier.getNum() > num) {
				reformSoldier.setNum(reformSoldier.getNum() - num);
			} else {
				player.roleInfo().getSoldierData().getReformSoldierTable().remove(id);
				player.roleInfo().getSoldierData().getSoldiers().remove(id);
			}

			// 加入改造装甲
			DesignMap newDesignMap = player.getCustomWeaponManager().queryLastestDesignMap(player, designMap.getType(), designMap.getSystemIndex(), designMap.getBuildIndex());
			player.getSoldierManager().getOrCreateSoldier(player, newDesignMap.getId());
			player.roleInfo().getSoldierData().addSoldier(newDesignMap.getId(), num, SoldierChangeType.COMMON);
			InjectorUtil.getInjector().dbCacheService.update(player);
			
			FightPowerKit.CAMP_POWER.math(player,GameLogSource.REFIT_SOLDIER);
			CurrencyUtil.send(player);
			
			// 清空改装厂的士兵
			ResUpdateSoldierMessage resUpdateSoldierMessage = new ResUpdateSoldierMessage();
			ReformSoldierBean updateReformSoldier = new ReformSoldierBean();
			resUpdateSoldierMessage.reformSoldierList.add(updateReformSoldier);
			updateReformSoldier.id = id;
			player.send(resUpdateSoldierMessage);
		
			
			player.getSoldierManager().send(player);
			
			CurrencyUtil.send(player);
			Language.SUCCESSTIP.send(player, SuccessTipEnum.E600_WEAPON.CODE3.get());
		}
	}
	
	/**
	 * 改装成功
	 * @param player
	 * @param id
	 * @param num
	 */
	public void refittingSucc(Player player, long id, int num) {
		ReformSoldier reformSoldier = player.roleInfo().getSoldierData().getReformSoldierHurtTable().get(id);
		if (reformSoldier != null) {
			if (reformSoldier.getNum() > num) {
				reformSoldier.setNum(reformSoldier.getNum() - num);
			} else {
				player.roleInfo().getSoldierData().getReformSoldierHurtTable().remove(id);
			}

			// 加入改造装甲
			// 加入改造装甲
			DesignMap designMap = player.getCustomWeaponManager().queryDesignMapById(player, reformSoldier.getSolderId());
			DesignMap newDesignMap = player.getCustomWeaponManager().queryLastestDesignMap(player, designMap.getType(), designMap.getSystemIndex(), designMap.getBuildIndex());
			player.getSoldierManager().getOrCreateSoldier(player, newDesignMap.getId());
			player.roleInfo().getSoldierData().addSoldier(newDesignMap.getId(), num, SoldierChangeType.COMMON);
			InjectorUtil.getInjector().dbCacheService.update(player);
			player.roleInfo().getSoldierData().addSoldier(reformSoldier.getSolderId(),num, SoldierChangeType.COMMON);
			player.roleInfo().getSoldierData().getRefittingData().setSoldierId(0);
			player.roleInfo().getSoldierData().getRefittingData().setNum(0);
			InjectorUtil.getInjector().dbCacheService.update(player);

			CurrencyUtil.send(player);
			player.getSoldierManager().send(player);
		}
	}

	/**
	 * 修改时把数据扔到改装厂
	 * @param player
	 * @param soldier
	 * @param num
	 * @param hurt
	 */
	public void assembly(Player player, Soldier soldier, int num, int hurt) {
		if (soldier == null)
			return;
		if (soldier.getNum() <= 0)
			return;
		
		ReformSoldier reformSoldier = new ReformSoldier();
		ItemSequance sequance = player.getSequance();
		reformSoldier.setId(sequance.genItemId());
		reformSoldier.setSolderId(soldier.getSoldierId());
		reformSoldier.setNum(num);

		// 把每个兵模型的控制器缓存在军营里
		if (hurt == 1) {
			player.roleInfo().getSoldierData().decrementSoldier(reformSoldier.getSolderId(), num, SoldierChangeType.INJURE);
			player.roleInfo().getSoldierData().getReformSoldierHurtTable().put(reformSoldier.getId(), reformSoldier);
		} else {
			player.roleInfo().getSoldierData().decrementSoldier(reformSoldier.getSolderId(), num, SoldierChangeType.COMMON);
			player.roleInfo().getSoldierData().getReformSoldierTable().put(reformSoldier.getId(), reformSoldier);
		}
		
		InjectorUtil.getInjector().dbCacheService.update(player);
		player.getSoldierManager().send(player);
	}
}
