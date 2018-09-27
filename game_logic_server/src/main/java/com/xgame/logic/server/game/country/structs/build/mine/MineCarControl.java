package com.xgame.logic.server.game.country.structs.build.mine;

import java.util.Map;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.config.building.BuildingPir;
import com.xgame.config.building.BuildingPirFactory;
import com.xgame.config.global.GlobalPirFactory;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.country.entity.CountryBuild;
import com.xgame.logic.server.game.country.entity.MineCar;
import com.xgame.logic.server.game.country.entity.MineRepository;
import com.xgame.logic.server.game.country.message.ResMineRepositoryMessage;
import com.xgame.logic.server.game.country.structs.build.BuildFactory;
import com.xgame.logic.server.game.country.structs.build.CountryBuildControl;
import com.xgame.logic.server.game.player.entity.Player;

import lombok.extern.slf4j.Slf4j;


/**
 *  矿车
 * @author jacky.jiang
 *
 */
@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MineCarControl extends CountryBuildControl {

	/**
	 * 矿车切换
	 * @param player
	 * @param uid
	 * @param resourceId
	 */
	@SuppressWarnings("unchecked")
	public void mineCarSwitch(Player player, int uid, int resourceType) {
		
		MineBuildControl mineBuildControl = (MineBuildControl)player.getCountryManager().getBuildControls().get(BuildFactory.MINE.getTid());
		CountryBuild mineFactoryBuild = mineBuildControl.getDefianlBuild();
		if(mineFactoryBuild == null) {
			return;
		}
		
		int level = GlobalPirFactory.getInstance().getMineOpenLevel(resourceType);
		if(level > player.getLevel()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E100_COUNTRY.CODE21);
			return;
		}
		
		MineRepository mineRepository = player.roleInfo().getBaseCountry().getMineRepository();
		MineCar mineCar = mineRepository.getCar(uid);
		if(mineCar == null) {
			return;
		}
		
		BuildingPir buildingPir = BuildingPirFactory.get(BuildFactory.MINE.getTid());
		if(buildingPir == null) {
			log.error("获取资源场临时仓库数据异常.");
			return;
		}
		
		BuildingPir buildingPir2 = BuildingPirFactory.get(BuildFactory.MINE_CAR.getTid());
		int time = (int)(System.currentTimeMillis() - mineCar.getLastRewardTime()) / 1000;
		
		Map<Integer, Map<Integer, Float>> map = (Map<Integer, Map<Integer, Float>>)buildingPir2.getV1();
		CountryBuild countryBuild2 = this.getBuild(uid);
		Float addNum = map.get(mineCar.getResourceType()).get(countryBuild2.getLevel());
		if(addNum == null) {
			log.error("添加资源异常，资源类型{}，资源等级{}", mineCar.getResourceType(), countryBuild2.getLevel());
			return;
		}
		
		Map<Integer, Integer> maxResourceMap = (Map<Integer, Integer>)buildingPir.getV2();
		Integer maxResource =  maxResourceMap.get(mineFactoryBuild.getLevel());
		if(maxResource == null) {
			log.error("获取资源场临时仓库数据异常，资源等级{}", mineFactoryBuild);
			return;
		}
		
		if (time < 0 || addNum < 0) {
			log.error("资源计算出错:时间:{}，数量:{}", time, addNum);
			return;
		}
		
		// 添加资源
		boolean send = mineBuildControl.addResource(mineRepository, mineCar, Math.round(addNum / 3600 * time), maxResource);
		mineCar.setLastRewardTime(System.currentTimeMillis());
		mineCar.setResourceType(resourceType);
		InjectorUtil.getInjector().dbCacheService.update(player);
		
		if(send) {
			ResMineRepositoryMessage resMineRepositoryMessage = new ResMineRepositoryMessage();
			resMineRepositoryMessage.oil = Double.valueOf(mineRepository.getOil()).longValue();
			resMineRepositoryMessage.steel = Double.valueOf(mineRepository.getSteel()).longValue();
			resMineRepositoryMessage.rare = Double.valueOf(mineRepository.getRare()).longValue();
			resMineRepositoryMessage.money = Double.valueOf(mineRepository.getMoney()).longValue();
			player.send(resMineRepositoryMessage);
		}
	}
	
}
