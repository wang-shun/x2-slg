package com.xgame.logic.server.game.country.structs.build.mine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.config.building.BuildingPir;
import com.xgame.config.building.BuildingPirFactory;
import com.xgame.config.global.GlobalPir;
import com.xgame.config.global.GlobalPirFactory;
import com.xgame.gameconst.GlobalConstant;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.utils.CurrencyUtil;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.bag.entity.eventmodel.MineProductUpdateEventObject;
import com.xgame.logic.server.game.constant.CurrencyEnum;
import com.xgame.logic.server.game.country.bean.BuildBean;
import com.xgame.logic.server.game.country.entity.CountryBuild;
import com.xgame.logic.server.game.country.entity.MineCar;
import com.xgame.logic.server.game.country.entity.MineRepository;
import com.xgame.logic.server.game.country.entity.XBuild;
import com.xgame.logic.server.game.country.message.ResMineCarBuildingMessage;
import com.xgame.logic.server.game.country.message.ResMineRepositoryMessage;
import com.xgame.logic.server.game.country.structs.build.BuildControl;
import com.xgame.logic.server.game.country.structs.build.BuildFactory;
import com.xgame.logic.server.game.country.structs.build.CountryBuildControl;
import com.xgame.logic.server.game.country.structs.status.TimeState;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.player.entity.eventmodel.BuildingRewardEventObject;
import com.xgame.logic.server.game.playerattribute.PlayerAttributeManager;


/**
 * 资源采矿厂
 * @author jacky.jiang
 *
 */
@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MineBuildControl extends CountryBuildControl {
	
	public void createInitAfter(Player player,XBuild countryBuild) {
		initMineCar(player, countryBuild.getUid());
	}

	@Override
	public void giveOutput(Player player, int uid) {
		rewardRepository(player);
	}

	@Override
	public void levelUpAfter(Player player, int uid) {
		List<CountryBuild> countryBuildList = initMineCar(player, uid);
		if(countryBuildList != null && !countryBuildList.isEmpty()) {
			// 推送矿车信息
			ResMineCarBuildingMessage resMineCarBuildingMessage = new ResMineCarBuildingMessage();
			if(countryBuildList != null && !countryBuildList.isEmpty()) {
				for(CountryBuild countryBuild : countryBuildList) {
					MineRepository mineRepository = player.roleInfo().getBaseCountry().getMineRepository();
					if(mineRepository != null) {
						MineCar mineCar = mineRepository.getCar(countryBuild.getUid());
						if(mineCar != null) {
							BuildBean buildBean = new BuildBean();
							buildBean.uid = countryBuild.getUid();
							buildBean.sid = countryBuild.getSid();
							buildBean.level = countryBuild.getLevel();
							buildBean.state = countryBuild.getState();
							buildBean.ext = String.valueOf(mineCar.getResourceType());
							resMineCarBuildingMessage.mineCarList.add(buildBean);
						}
					}
				}
				player.send(resMineCarBuildingMessage);
			}
		}
	}
	
	/**
	 * 初始化矿车
	 * @param player
	 * @param uid
	 * @return
	 */
	private List<CountryBuild> initMineCar(Player player, int uid) {
		List<CountryBuild> buildList = new ArrayList<CountryBuild>();
		CountryBuild countryBuild = getBuild(uid);
		if (countryBuild != null) {
			BuildingPir buildingPir = BuildingPirFactory.get(BuildFactory.MINE.getTid());
			if (buildingPir != null) {
				int addNum = 0;
				BuildControl countryBuildControl = player.getCountryManager().getBuildControls().get(BuildFactory.MINE_CAR.getTid());
				if(countryBuildControl != null) {
					addNum = getMineCarNum(buildingPir, countryBuild.getLevel()) - countryBuildControl.getBuildMap().keySet().size();
				} else {
					addNum = getMineCarNum(buildingPir, countryBuild.getLevel()) - 0;
				}
				
				MineRepository mineRepository = player.roleInfo().getBaseCountry().getMineRepository();
				for (int i = 1; i <= addNum; i++) {
					// 添加矿车数量
					int size = mineRepository.getMineCarRepository().keySet().size();
					int carId = 100000 + size + 1;
					if(countryBuildControl == null) {
						// 创建control
						countryBuildControl = BuildFactory.getOrCreate(player,BuildFactory.MINE_CAR.getTid());
						player.getCountryManager().getBuildControls().put(BuildFactory.MINE_CAR.getTid(), countryBuildControl);
					}
					
					// 创建建筑
					CountryBuild countryBuild2 = player.getCountryManager().getMineCarControl().createInit(player, carId, TimeState.USEING.ordinal());
					player.getCountryManager().fastCreateBuild(BuildFactory.MINE_CAR.getTid(), carId);
					
					// 随机矿车采矿
					int level = player.getCountryManager().getMainBuildControl().getMaxLevelBuild().getLevel();
					MineCar mineCar = mineRepository.getOrCreate(carId);
					
					// 随机矿车资源
					List<Integer> openLevelTag = GlobalPirFactory.getInstance().getMineResourceOpen(level);
					Random random = new Random();
					int index = random.nextInt(openLevelTag.size());
					int resourceType = openLevelTag.get(index);
					
					mineCar.setResourceType(resourceType);
					mineCar.setLastRewardTime(System.currentTimeMillis());
					buildList.add(countryBuild2);
				}
				
				// 更新数据库
				InjectorUtil.getInjector().dbCacheService.update(player);
				EventBus.getSelf().fireEvent(new MineProductUpdateEventObject(player));
			}
		}
		return buildList;
	}
	
	/**
	 * 获取当前等级矿车最大数量
	 * @param configModel
	 * @param level
	 * @return
	 */
	private int getMineCarNum(BuildingPir buildingPir, int level) {
		Object obj  = buildingPir.getV1();
		@SuppressWarnings("unchecked")
		Map<Integer, Integer> map = (Map<Integer, Integer>)obj;
		Integer num = map.get(level);
		if(num == null) {
			log.error(String.format("获取矿车最大数量为空,等级%s", level));
			return 0;
		}
		return num;
	}
	
	/**
	 * 矿车采集结算
	 * @param login(true 标识登录结算，false 非登录结算)
	 */
	public boolean dealMineCarResouce(Player player, boolean login) {
		CountryBuild mineFactoryBuild = this.getDefianlBuild();
		BuildingPir buildingPir = BuildingPirFactory.get(BuildFactory.MINE.getTid());
		if(buildingPir == null) {
			log.error("获取资源场临时仓库数据异.");
			return false;
		}
		
		@SuppressWarnings("unchecked")
		Map<Integer, Integer> maxResourceMap = (Map<Integer, Integer>)buildingPir.getV2();
		Integer maxResource =  maxResourceMap.get(mineFactoryBuild.getLevel());
		if(maxResource == null) {
			log.error("获取资源场临时仓库数据异常，资源等级{}", mineFactoryBuild.getLevel());
			return false;
		}
		
		maxResource = PlayerAttributeManager.get().capacity(player.getId(), maxResource);
		MineCarControl mineCarControl = (MineCarControl)player.getCountryManager().getBuildControls().get(BuildFactory.MINE_CAR.getTid());
		MineRepository tempRepository = player.roleInfo().getBaseCountry().getMineRepository();
		
		// 判断是否有道具减少时间
		GlobalPir config = GlobalPirFactory.get(GlobalConstant.MINE_CAR_CD_TIME);
		int cdTime = Integer.valueOf(config.getValue());
		int cdTimePlus = PlayerAttributeManager.get().mineCarCdTime(player.getId());
		
		// 资源产出
		boolean send = false;
		for(XBuild countryBuild2: mineCarControl.getBuildMap().values()) {
			MineCar mineCar = tempRepository.getOrCreate(countryBuild2.getUid());
			if(mineCar == null) {
				continue;
			}
			if(mineCar.getLastRewardTime() + cdTimePlus < System.currentTimeMillis()) {
				int mineProduct = PlayerAttributeManager.get().mineProduct(player.getId(), mineCar.getResourceType(), ((CountryBuild)countryBuild2).getLevel());
				int timeCell = (int)((System.currentTimeMillis() - mineCar.getLastRewardTime()) / (1000 * 30));
				// 添加资源
				if(timeCell > 0) {
					double add = timeCell * cdTime * mineProduct / 3600;
					if(addResource(tempRepository, mineCar, add, maxResource)) {
						send  = true;
					}
				}
			}
		}
		
		// 发送资源增加消息
		if(send) {
			InjectorUtil.getInjector().dbCacheService.update(player);

			ResMineRepositoryMessage resMineRepositoryMessage = new ResMineRepositoryMessage();
			resMineRepositoryMessage.money = Double.valueOf(tempRepository.getMoney()).longValue();
			resMineRepositoryMessage.oil = Double.valueOf(tempRepository.getOil()).longValue();
		    resMineRepositoryMessage.rare = Double.valueOf(tempRepository.getRare()).longValue();
		    resMineRepositoryMessage.steel = Double.valueOf(tempRepository.getSteel()).longValue();
		    player.send(resMineRepositoryMessage);
		    return true;
		}
		return false;
	}
	
	/**
	 * 添加资源（添加资源，添加完之后，将上一次领取的时间设置为当前时间, 避免每次结算出现重复领取）
	 * @param tempRepository
	 * @param mineCar
	 * @param addResource
	 * @param maxResource
	 */
	public boolean addResource(MineRepository tempRepository, MineCar mineCar, double addResource, int maxResource) {
		if(tempRepository.getOil() + tempRepository.getMoney() + tempRepository.getRare()+ tempRepository.getSteel() >= maxResource ) {
			return false;
		} else if(tempRepository.getOil() + tempRepository.getMoney() + tempRepository.getRare()+ tempRepository.getSteel() + addResource >= maxResource) {
			addResource = (int) (maxResource - (tempRepository.getOil() + tempRepository.getMoney() + tempRepository.getRare()+ tempRepository.getSteel()));
		}
		
		mineCar.setLastRewardTime(System.currentTimeMillis());
		if(mineCar.getResourceType() == CurrencyEnum.OIL.ordinal()) {
			tempRepository.setOil(tempRepository.getOil() + addResource);	
			return true;
		} else if(mineCar.getResourceType() == CurrencyEnum.GLOD.ordinal()) {
			tempRepository.setMoney(tempRepository.getMoney() + addResource);
			return true;
		} else if(mineCar.getResourceType() == CurrencyEnum.RARE.ordinal()) {
			tempRepository.setRare(tempRepository.getRare() + addResource);
			return true;
		} else if(mineCar.getResourceType() == CurrencyEnum.STEEL.ordinal()) {
			tempRepository.setSteel(tempRepository.getSteel() + addResource);
			return true;
		}
		return false;
	}
	
	
	/**
	 * 领取奖励
	 * @param player
	 * @param resourceId
	 */
	public void rewardRepository(Player player) {
		
		MineRepository mineRepository = player.roleInfo().getBaseCountry().getMineRepository();
		if(mineRepository == null) {
			return;
		}
		
		if(mineRepository.getOil() > 0) {
			long add = Double.valueOf(mineRepository.getOil()).longValue();
			mineRepository.setOil(0);
			CurrencyUtil.increase(player, add, CurrencyEnum.OIL, GameLogSource.MINE_OUTPUT);
		}
		
		if(mineRepository.getSteel() > 0) {
			long add =  Double.valueOf(mineRepository.getSteel()).longValue();
			mineRepository.setSteel(0);
			CurrencyUtil.increase(player, add, CurrencyEnum.STEEL, GameLogSource.MINE_OUTPUT);
		}
		
		if(mineRepository.getRare() > 0) {
			long add =  Double.valueOf(mineRepository.getRare()).longValue();
			mineRepository.setRare(0);
			CurrencyUtil.increase(player, add, CurrencyEnum.RARE, GameLogSource.MINE_OUTPUT);
		}
		
		if(mineRepository.getMoney() > 0)  {
			long add = Double.valueOf(mineRepository.getMoney()).longValue();
			mineRepository.setMoney(0);
			CurrencyUtil.increase(player, add, CurrencyEnum.GLOD, GameLogSource.MINE_OUTPUT);
		}
		
		// 领取奖励
		ResMineRepositoryMessage resMineRepositoryMessage = new ResMineRepositoryMessage();
		resMineRepositoryMessage.oil = Double.valueOf(mineRepository.getOil()).longValue();
		resMineRepositoryMessage.steel = Double.valueOf(mineRepository.getSteel()).longValue();
		resMineRepositoryMessage.rare = Double.valueOf(mineRepository.getRare()).longValue();
		resMineRepositoryMessage.money = Double.valueOf(mineRepository.getMoney()).longValue();
		player.send(resMineRepositoryMessage);
		EventBus.getSelf().fireEvent(new BuildingRewardEventObject(player));
		CurrencyUtil.send(player);
	}
}
