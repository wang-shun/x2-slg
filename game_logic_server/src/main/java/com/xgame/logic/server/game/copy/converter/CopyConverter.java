package com.xgame.logic.server.game.copy.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xgame.config.attribute.AttributeConfMap;
import com.xgame.config.attribute.AttributeParser;
import com.xgame.config.copyMonster.CopyMonsterPir;
import com.xgame.config.copyMonster.CopyMonsterPirFactory;
import com.xgame.config.copyPoint.CopyPointPir;
import com.xgame.config.copyPoint.CopyPointPirFactory;
import com.xgame.logic.server.game.copy.bean.CopyRaidResult;
import com.xgame.logic.server.game.copy.bean.MainCopyBean;
import com.xgame.logic.server.game.copy.bean.MainCopyPointBean;
import com.xgame.logic.server.game.copy.enity.MainCopy;
import com.xgame.logic.server.game.copy.enity.MainCopyInfo;
import com.xgame.logic.server.game.copy.enity.MainCopyPoint;
import com.xgame.logic.server.game.copy.message.ResCopyRaidMessage;
import com.xgame.logic.server.game.copy.message.ResGetRewardBoxMessage;
import com.xgame.logic.server.game.copy.message.ResQueryCopyInfoMessage;
import com.xgame.logic.server.game.copy.message.ResWarDefendDataMessage;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.customweanpon.converter.CustomWeaponConverter;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.soldier.entity.Soldier;
import com.xgame.logic.server.game.war.bean.WarBuilding;
import com.xgame.logic.server.game.war.bean.WarDefendData;
import com.xgame.logic.server.game.war.entity.WarBuildFactory;
import com.xgame.logic.server.game.war.entity.WarDefender;
import com.xgame.logic.server.game.war.entity.WarSoldierFactory;

public class CopyConverter {

	
	public static ResWarDefendDataMessage resWarDefendDataMessageBuilder(int point){
		ResWarDefendDataMessage info = new ResWarDefendDataMessage();
		WarDefendData warDefendData = warDefendData(point);
		info.warDefendData = warDefendData;
		return info;
	}
	
	public static WarDefender warDefendDataBuilder(String copyFile){
		return warDefendDataBuilder(copyFile,1,1);
	}
	
	@SuppressWarnings("unchecked")
	public static WarDefender warDefendDataBuilder(String copyFile,double monsterTrim,double buildingTrim){
		//初始化防守方
		WarDefender warDefender = new WarDefender();
		warDefender.setNpc(true);
		String fileFactoryName = "com.xgame.config."+copyFile+"."+copyFile+"PirFactory";
		try {
			Class<?> factoryClass = Class.forName(fileFactoryName);
			Object factory = factoryClass.getMethod("getInstance", factoryClass.getClasses()).invoke(null);
			Map<Integer, Object> map =  (Map<Integer, Object>)factory.getClass().getMethod("getFactory").invoke(factory);
			List<WarBuilding> defendBuildingList = new ArrayList<>();
			Map<Integer, Soldier> defendSoldierMap = new HashMap<>();
			for(Object o : map.values()){
				int buildingId = (int)o.getClass().getMethod("getBuildingId").invoke(o);
				int x = (int)o.getClass().getMethod("getX").invoke(o);
				int y = (int)o.getClass().getMethod("getY").invoke(o);
				int buildingLv = (int)o.getClass().getMethod("getBuildingLv").invoke(o);
				int resource = (int)o.getClass().getMethod("getResource").invoke(o);
				int monsterId = (int)o.getClass().getMethod("getMonsterId").invoke(o);
				int monsterNum = (int)o.getClass().getMethod("getMonsterNum").invoke(o);
				Vector2Bean v2b = new Vector2Bean();
				v2b.x = x;
				v2b.y = y;
				WarBuilding building = WarBuildFactory.initWarPVEBuilding(buildingId, buildingLv, v2b, buildingTrim);
				building.resourceNum = resource;
				defendBuildingList.add(building);
				// 构建防守方兵
				if(monsterId > 0){
					CopyMonsterPir monsterPir = CopyMonsterPirFactory.get(monsterId);
					AttributeConfMap acm = new AttributeConfMap(monsterId);
					String attr = monsterPir.getAttr();
					AttributeParser.parse(attr,acm);
					List<Integer> pjList = monsterPir.getPeijianIds();
					Soldier soldier = WarSoldierFactory.initSoldier(monsterNum,acm,v2b,monsterTrim,CustomWeaponConverter.assemebleMonster(pjList));
					defendSoldierMap.put(soldier.getBuildUid(), soldier);
				}
			}
			//初始化建筑
			warDefender.initMonsterBuildings(defendBuildingList);
			warDefender.setDefendSoldierMap(defendSoldierMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return warDefender;
	}
	
	public static WarDefendData warDefendData(int pointId){
		CopyPointPir pir = CopyPointPirFactory.get(pointId);
		String copyFile = pir.getCopyFile();
		double monsterTrim = pir.getMonsterTrim();
		double buildingTrim = pir.getBuildingTrim();
		WarDefender warDefender = warDefendDataBuilder(copyFile,monsterTrim,buildingTrim);
		warDefender.setNpc(true);
		return warDefender.getWarDefenData();
	}
	
	public static ResQueryCopyInfoMessage resQueryCopyInfoMessage(Player player){
		ResQueryCopyInfoMessage info = new ResQueryCopyInfoMessage();
		List<MainCopyBean> mainCopyList = info.mainCopyList;
		MainCopyInfo mainCopyInfo = player.roleInfo().getMainCopyInfo();
		for(MainCopy mainCopy : mainCopyInfo.getCopyMap().values()){
			MainCopyBean mainCopyBean = mainCopyBean(mainCopy);
			mainCopyList.add(mainCopyBean);
		}
		
		return info;
	}
	
	private static MainCopyBean mainCopyBean(MainCopy mainCopy){
		MainCopyBean mainCopyBean = new MainCopyBean();
		mainCopyBean.copyId = mainCopy.getCopyId();
		mainCopyBean.currCopyPoint = mainCopy.getCurrCopyPoint();
		mainCopyBean.star = mainCopy.getStar();
		mainCopyBean.state = mainCopy.getState();
		mainCopyBean.rewardBoxFlag = mainCopy.getRewardBoxFlag();
		for(MainCopyPoint point : mainCopy.getPointMap().values()){
			MainCopyPointBean mainCopyPointBean = new MainCopyPointBean();
			mainCopyPointBean.copyPointId = point.getCopyPointId();
			mainCopyPointBean.star = point.getStar();
			mainCopyPointBean.isOpen = point.isOpen();
			mainCopyBean.pointList.add(mainCopyPointBean);
		}
		
		return mainCopyBean;
	}
	
	public static ResGetRewardBoxMessage resGetRewardBoxMessageBuilder(MainCopy mainCopy){
		ResGetRewardBoxMessage info = new ResGetRewardBoxMessage();
		MainCopyBean mainCopyBean = mainCopyBean(mainCopy);
		info.mainCopyBean = mainCopyBean;
		return info;
	}
	
	public static ResCopyRaidMessage resCopyRaidMessageBuilder(int copyId,int pointId,String reward){
		ResCopyRaidMessage info = new ResCopyRaidMessage();
		CopyRaidResult result = new CopyRaidResult();
		result.copyId = copyId;
		result.copyPointId = pointId;
		result.rewardList = reward;
		
		info.copyRaidResult = result;
		
		return info;
	}
}
