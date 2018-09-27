package com.xgame.logic.server.core.utils;

import java.util.Iterator;
import java.util.Map;

import com.xgame.config.global.CurrencyChangeConf;
import com.xgame.config.global.GlobalPirFactory;
import com.xgame.gameconst.GlobalConstant;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.game.constant.CurrencyEnum;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.player.entity.eventmodel.CurrencyEventObject;
import com.xgame.logic.server.game.player.message.ResCurrencyMessage;
import com.xgame.logic.server.game.playerattribute.fightpower.FightPowerKit;

/**
 *
 * 2016-8-03 16:17:47 玩家身上货币计算 必须放在玩家线程去修改 必须保证只有一个线程修改
 * 
 * @author ye.yuan
 *
 */
public class CurrencyUtil {

	/** 石油 **/
	public static long getOil(Player player) {
		return player.roleInfo().getCurrency().getOil();
	}

	public static boolean setOil(Player player, long sourceCurrency, long currency) {
		if (player.roleInfo().getCurrency().getOil() == sourceCurrency) {
			player.roleInfo().getCurrency().setOil(currency);
			return true;
		}
		return false;
	}

	/** 稀土 **/
	public static long getRare(Player player) {
		return player.roleInfo().getCurrency().getRare();
	}

	public static boolean setRare(Player player, long sourceCurrency, long currency) {
		if (player.roleInfo().getCurrency().getRare() == sourceCurrency) {
			player.roleInfo().getCurrency().setRare(currency);
			return true;
		}
		return false;
	}

	/** 钢材 **/
	public static long getSteel(Player player) {
		return player.roleInfo().getCurrency().getSteel();
	}

	public static boolean setSteel(Player player, long sourceCurrency, long currency) {
		if (player.roleInfo().getCurrency().getSteel() == sourceCurrency) {
			player.roleInfo().getCurrency().setSteel(currency);
			return true;
		}
		return false;
	}

	/** 黄金 **/
	public static long getGlod(Player player) {
		return player.roleInfo().getCurrency().getMoney();
	}

	public static boolean setGlod(Player player, long sourceCurrency, long currency) {
		if (player.roleInfo().getCurrency().getMoney() == sourceCurrency) {
			player.roleInfo().getCurrency().setMoney(currency);
			return true;
		}
		return false;
	}

	/** 钻石 **/
	public static long getDiamond(Player player) {
		return player.roleInfo().getCurrency().getDiamond();
	}

	public static boolean setDiamond(Player player, long sourceCurrency, long currency) {
		if (player.roleInfo().getCurrency().getDiamond() == sourceCurrency) {
			player.roleInfo().getCurrency().setDiamond(currency);
			return true;
		}
		return false;
	}

	// VIP_EXP {

	// public long getCurrency(Player player) {return
	// player.roleInfo().vipInfo.exp;}
	//
	// public boolean setCurrency(Player player,long sourceCurrency,long
	// currency) {
	// if(player.roleInfo().vipInfo.exp==sourceCurrency&&currency>0&&currency>player.roleInfo().vipInfo.exp){
	// byte i = player.roleInfo().vipInfo.vip;
	// int size = vipConfigModel.factory.size();
	// for(;i<size;i++){
	// vipConfigModel vipConfig = vipConfigModel.factory.get(i);
	// if(currency<vipConfig.getNeedExp()||currency<=0){
	// break;
	// }
	// player.roleInfo().vipInfo.vip++;
	// currency-=vipConfig.getNeedExp();
	// }
	// player.roleInfo().vipInfo.exp=(int)currency;
	// return true;
	// }
	// return false;
	// }
	// },

	public static final short FAST_USE = 0;

	public static final short USE = 1;

	public static long getCurrency(Player player, CurrencyEnum type) {
		switch (type) {
		case OIL:
			return CurrencyUtil.getOil(player);
		case RARE:
			return CurrencyUtil.getRare(player);
		case STEEL:
			return CurrencyUtil.getSteel(player);
		case GLOD:
			return CurrencyUtil.getGlod(player);
		case DIAMOND:
			return CurrencyUtil.getDiamond(player);
		default:
			break;
		}
		return 0;
	}

	public static boolean setCurrency(Player player, long sourceCurrency, long currency, CurrencyEnum type) {
		switch (type) {
		case OIL:
			CurrencyUtil.setOil(player, sourceCurrency, currency);
			return true;
		case RARE:
			CurrencyUtil.setRare(player, sourceCurrency, currency);
			return true;
		case STEEL:
			CurrencyUtil.setSteel(player, sourceCurrency, currency);
			return true;
		case GLOD:
			CurrencyUtil.setGlod(player, sourceCurrency, currency);
			return true;
		case DIAMOND:
			CurrencyUtil.setDiamond(player, sourceCurrency, currency);
			return true;
		default:
			break;
		}

		return false;
	}

	public static boolean verify(Player player, Number value, CurrencyEnum type) {
		if (value == null) {
			return true;
		}
		// =0是让过的 因为有些批量验证需要通过 但在正在扣除的时候不会过
		if (value.longValue() < 0) {
			return false;
		}
		if (getCurrency(player, type) < value.longValue()) {
			return false;
		}
		return true;
	}

	/**
	 * 减去货币 非线程安全 所以必须在玩家所在线程执行
	 * 
	 * @param player
	 * @param diamond
	 * @param factory
	 * @return
	 */
	public static boolean decrement(Player player, Number value, CurrencyEnum type, GameLogSource gameLogSource) {
		try {
			if (value == null) {
				return false;
			}
			long longValue = value.longValue();
			if (longValue <= 0) {
				return false;
			}

			long currency = getCurrency(player, type);

			if (longValue > currency) {
				return false;
			}

			boolean decrementRs = setCurrency(player, currency, currency - longValue, type);

			long newCurrency = getCurrency(player, type);

			CurrencyEventObject event = new CurrencyEventObject(player, EventTypeConst.EVENT_CURRENCY_DECREMENT, type, false, currency, newCurrency, gameLogSource);
			EventBus.getSelf().fireEvent(event);
			
			return decrementRs;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 增加
	 * 
	 * @param player
	 * @param diamond
	 * @param factory
	 * @return
	 */
	public static boolean increase(Player player, Number value, CurrencyEnum type, GameLogSource gameLogSource) {
		try {
			if (value == null) {
				return false;
			}
			long longValue = value.longValue();
			if (longValue <= 0) {
				return false;
			}
			long currency = getCurrency(player, type);

			boolean increaseRs = setCurrency(player, currency, currency + longValue, type);

			long newCurrency = getCurrency(player, type);

			CurrencyEventObject event = new CurrencyEventObject(player, EventTypeConst.EVENT_CURRENCY_INCREASE, type, true, currency, newCurrency, gameLogSource);

			EventBus.getSelf().fireEvent(event);

			return increaseRs;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static double mathCurrencyDiamond(Player player, Number value, CurrencyEnum type) {
		if (value == null)
			return 0;
		
		long num = 0;
		if(type == CurrencyEnum.OIL) {
			num = value.longValue() * GlobalPirFactory.getInstance().getInt(GlobalConstant.OIL_WEIGHT);
		} else if(type == CurrencyEnum.STEEL){
			num = value.longValue() * GlobalPirFactory.getInstance().getInt(GlobalConstant.STEEL_WEIGHT);
		} else if(type == CurrencyEnum.RARE) {
			num = value.longValue() * GlobalPirFactory.getInstance().getInt(GlobalConstant.RARE_WEIGHT);
		} else if(type == CurrencyEnum.GLOD) {
			num = value.longValue() * GlobalPirFactory.getInstance().getInt(GlobalConstant.GOLD_WEIGHT);
		}  else {
			num = value.longValue();
		}
		
		return findChange(num - getCurrency(player, type), GlobalPirFactory.getInstance().currencyChangeConfs);
	}

	public static double mathSpeedDiamond(Player player, Number value) {
		if (value == null)
			return 0;
		return findChange(value.longValue(), GlobalPirFactory.getInstance().speedChangeConfs);
	}

	/* static m */
	public static final boolean verifyCurrency(Player player, Number money, Number steel, Number oil, Number rare) {
		if (!verify(player, money, CurrencyEnum.GLOD)) {
			return false;
		}
		if (!verify(player, steel, CurrencyEnum.STEEL)) {
			return false;
		}
		if (!verify(player, oil, CurrencyEnum.OIL)) {
			return false;
		}
		if (!verify(player, rare, CurrencyEnum.RARE)) {
			return false;
		}
		return true;
	}

	/**
	 * 给功能定制的扣钱方法
	 * 
	 * @param player
	 * @param money
	 * @param steels
	 * @param oil
	 * @param rare
	 * @param useType
	 *            快速使用还是使用
	 * @param time
	 *            cd时间 用于计算快速使用的钻石
	 * @return
	 */
	public static final boolean decrementCurrency(Player player, Number money, Number steels, Number oil, Number rare, int useType, Number time, GameLogSource gameLogSource) {
		if (useType == CurrencyUtil.USE) {
			// 验证花费是否足够
			if (!CurrencyUtil.verifyCurrency(player, money, steels, oil, rare))
				return false;
			// 在扣材料 如果扣失败也不管了 所以玩家自身属性操作 定要抛回玩家线程进行处理
			CurrencyUtil.decrementCurrency(player, money, steels, oil, rare, gameLogSource);
		} else if (useType == CurrencyUtil.FAST_USE) {
			// 把秒转换成分钟 向上取整
			double ceil = Math.ceil(time.doubleValue() / 60);
			// 算出加速钻石
			double speedChange = CurrencyUtil.speedChange(player, ceil);
			// 算出多出那部分的资源需要销毁的钻石
			double oil1 = mathCurrencyDiamond(player, oil, CurrencyEnum.OIL);
			double money1 = mathCurrencyDiamond(player, money, CurrencyEnum.GLOD);
			double steel1 = mathCurrencyDiamond(player, steels, CurrencyEnum.STEEL);
			double rare1 = mathCurrencyDiamond(player, rare, CurrencyEnum.RARE);
			double currencyChange = oil1 + money1 + steel1 + rare1;
			// 算出总共需要花费的钻石数量
			double diamond = Math.ceil(currencyChange) + Math.ceil(speedChange);

			// 验证钻石是否足够
			if (!verify(player, diamond, CurrencyEnum.DIAMOND))
				return false;
			// 由于这里是扣除当前已有的全部资源 所以 只要带单线程下面计算 是不需要验证
			if (oil1 > 0) {
				decrement(player, getCurrency(player, CurrencyEnum.OIL), CurrencyEnum.OIL, gameLogSource);
			} else {
				decrement(player, oil, CurrencyEnum.OIL, gameLogSource);
			}
			
			if (money1 > 0) {
				decrement(player, getCurrency(player, CurrencyEnum.GLOD), CurrencyEnum.GLOD, gameLogSource);
			} else {
				decrement(player, money, CurrencyEnum.GLOD, gameLogSource);
			}
			if (steel1 > 0) {
				decrement(player, getCurrency(player, CurrencyEnum.STEEL), CurrencyEnum.STEEL, gameLogSource);
			} else {
				decrement(player, steels, CurrencyEnum.STEEL, gameLogSource);
			}
			if (rare1 > 0) {
				decrement(player, getCurrency(player, CurrencyEnum.RARE), CurrencyEnum.RARE, gameLogSource);
			} else {
				decrement(player, rare, CurrencyEnum.RARE, gameLogSource);
			}
			
			// 扣除钻石
			decrement(player, diamond, CurrencyEnum.DIAMOND, gameLogSource);
		}
		
		send(player);
		return true;
	}

	public static final boolean increaseCurrency(Player player, Number money, Number steel, Number oil, Number rare, GameLogSource gameLogSource) {
		boolean success = false;
		if (increase(player, money, CurrencyEnum.GLOD, gameLogSource)) {
			success = true;
		}
		if (increase(player, steel, CurrencyEnum.STEEL, gameLogSource)) {
			success = true;
		}
		if (increase(player, oil, CurrencyEnum.OIL, gameLogSource)) {
			success = true;
		}
		if (increase(player, rare, CurrencyEnum.RARE, gameLogSource)) {
			success = true;
		}
		if (success) {
			InjectorUtil.getInjector().dbCacheService.update(player);
			send(player);
		}

		return success;
	}

	public static final boolean decrementCurrency(Player player, Number money, Number steel, Number oil, Number rare, GameLogSource gameLogSource) {
		boolean success = false;
		if (decrement(player, money, CurrencyEnum.GLOD, gameLogSource)) {
			success = true;
		}
		
		if (decrement(player, steel, CurrencyEnum.STEEL, gameLogSource)) {
			success = true;
		}
		
		if (decrement(player, oil, CurrencyEnum.OIL, gameLogSource)) {
			success = true;
		}
		
		if (decrement(player, rare, CurrencyEnum.RARE, gameLogSource)) {
			success = true;
		}
		
		if (success) {
			InjectorUtil.getInjector().dbCacheService.update(player);
			send(player);
		}
		return success;
	}

	public static final boolean decrementCurrency(Player player, int id, int value, GameLogSource gameLogSource) {
		boolean success = false;
		decrement(player, value, EnumUtils.getEnum(CurrencyEnum.class, id), gameLogSource);
		if (success) {
			InjectorUtil.getInjector().dbCacheService.update(player);
			send(player);
		}
		return success;
	}

	public static final boolean verifyFinal(Player player, int id, Number value) {
		return verify(player, value, EnumUtils.getEnum(CurrencyEnum.class, id));
	}

	public static final void increaseFinal(int id, Player player, long value, GameLogSource gameLogSource) {
		increase(player, value, EnumUtils.getEnum(CurrencyEnum.class, id), gameLogSource);
	}

	public static final void decrementFinal(Player player, int id, long value, GameLogSource gameLogSource) {
		decrement(player, value, EnumUtils.getEnum(CurrencyEnum.class, id), gameLogSource);
	}

	public static double currencyChange(Player player, Number x, Number x2, Number x3, Number x4) {
		return mathCurrencyDiamond(player, x, CurrencyEnum.OIL) + mathCurrencyDiamond(player, x2, CurrencyEnum.RARE)
				+ mathCurrencyDiamond(player, x3, CurrencyEnum.STEEL)
				+ mathCurrencyDiamond(player, x4, CurrencyEnum.GLOD);
	}

	public static double speedChange(Player player, Number x) {
		return mathSpeedDiamond(player, x);
	}

	public static double findChange(Number value, Map<Integer, CurrencyChangeConf> map) {
		try {
			long x = value.longValue();
			if (x <= 0)
				return 0;
			Iterator<CurrencyChangeConf> iterator = map.values().iterator();
			while (iterator.hasNext()) {
				CurrencyChangeConf changeConf = iterator.next();
				if (x > changeConf.getMinRange() && x <= changeConf.getMaxRange()) {
					changeConf.getJep().addVariable("x", x);
					return (double) changeConf.getJep().evaluate();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static final void send(Player player) {
		ResCurrencyMessage message = new ResCurrencyMessage();
		message.resCP = getCurrency(player, CurrencyEnum.GLOD);
		message.resGC = getCurrency(player, CurrencyEnum.STEEL);
		message.resSY = getCurrency(player, CurrencyEnum.OIL);
		message.resXT = getCurrency(player, CurrencyEnum.RARE);
		message.resZS = getCurrency(player, CurrencyEnum.DIAMOND);
		message.fightPower = FightPowerKit.FINAL_POWER.getValue(player.getId()).longValue();
		player.send(message);
	}
}
