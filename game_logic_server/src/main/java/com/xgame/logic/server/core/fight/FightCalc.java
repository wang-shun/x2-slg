package com.xgame.logic.server.core.fight;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xgame.config.ConfigSystem;
import com.xgame.config.attribute.AttributeConfMap;
import com.xgame.config.attribute.AttributeParser;
import com.xgame.config.peiJian.PeiJianPirFactory;
import com.xgame.logic.server.core.fight.luaj.lib.jse.CoerceJavaToLua;
import com.xgame.logic.server.core.fight.luaj.lib.jse.JsePlatform;
import com.xgame.logic.server.core.fight.luaj.vm2.Globals;
import com.xgame.logic.server.core.fight.luaj.vm2.LuaValue;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.customweanpon.converter.CustomWeaponConverter;
import com.xgame.logic.server.game.soldier.bean.FullSoldierBean;
import com.xgame.logic.server.game.soldier.bean.SoldierBean;
import com.xgame.logic.server.game.war.bean.DefendSoldierBean;
import com.xgame.logic.server.game.war.bean.WarAttackData;
import com.xgame.logic.server.game.war.bean.WarAttr;
import com.xgame.logic.server.game.war.bean.WarBuilding;
import com.xgame.logic.server.game.war.bean.WarDefendData;
import com.xgame.logic.server.game.war.bean.WarSoldierBean;
import com.xgame.logic.server.game.war.constant.WarType;
import com.xgame.logic.server.game.war.converter.BattleConverter;
import com.xgame.logic.server.game.war.entity.WarBuildFactory;
import com.xgame.logic.server.game.war.entity.WarSoldierFactory;
import com.xgame.logic.server.game.war.entity.report.WarEndReport;
import com.xgame.logic.server.game.war.message.ResWarDataMessage;

@Slf4j
@Component
public class FightCalc {

	@Autowired
	private ConfigSystem configSystem;

	private LuaValue sysCfgLuaValue;

	private LuaValue pjCfgLuaValue;

	private String path;

	private boolean isInit = false;

	public static final int FPS = 30; // 服务器按按照每秒30帧 计算战斗(时间秒 * 30 == 循环次数)

	public void test() {
		try {

			ResWarDataMessage rwd = new ResWarDataMessage();

			WarDefendData warDefendData = new WarDefendData();

			WarAttackData attackData = new WarAttackData();
			
			warDefendData.defendUid = 3456;

			rwd.defenData = warDefendData;

			rwd.attackData = attackData;

			rwd.battleId = 12222;

			rwd.battleType = WarType.COUNTRY_SEARCH.getConfigId();

			List<WarBuilding> defendBuildingList = new ArrayList<WarBuilding>();

			List<DefendSoldierBean> defendSoldiersList = new ArrayList<DefendSoldierBean>();
			// 构建防守方建筑
			// BasePriFactory ccc = (BasePriFactory)
			// configSystem.getConfig("C100011");
			Vector2Bean v2b = new Vector2Bean();
			v2b.x = 26;
			v2b.y = 31;
			WarBuilding building = WarBuildFactory.initWarPVEBuilding(1100, 1, v2b, 1.1);
			building.building.uid = 888;
			defendBuildingList.add(building);

			v2b = new Vector2Bean();
			v2b.x = 27;
			v2b.y = 48;
			building = WarBuildFactory.initWarPVEBuilding(1401, 1, v2b, 1.1);
			building.resourceNum = 4000;
			building.building.uid = 555;
			defendBuildingList.add(building);

			v2b = new Vector2Bean();
			v2b.x = 58;
			v2b.y = 58;
			building = WarBuildFactory.initWarPVEBuilding(1404, 1, v2b, 1.1);
			building.resourceNum = 8000;
			building.building.uid = 666;
			defendBuildingList.add(building);

			v2b = new Vector2Bean();
			v2b.x = 59;
			v2b.y = 63;
			building = WarBuildFactory.initWarPVEBuilding(1600, 1, v2b, 1.1);
			building.building.uid = 777;
			defendBuildingList.add(building);

			warDefendData.buildings = defendBuildingList;

			// 构建防守方兵
			AttributeConfMap acm = new AttributeConfMap(1);
			Map<Integer, Double> attrMap = AttributeParser.parse("101,-1:100;104,-1:20;106,-1:200;102,-1:100;115,-1:30;116,-1:30;122,-1:0.6;123,-1:6;124,-1:6;125,-1:6;127,-1:360;129,-1:1");
			List<Integer> pjList = new ArrayList<Integer>();
			pjList.add(1010001);
			pjList.add(1110001);
			pjList.add(1110001);
			pjList.add(1210001);
			pjList.add(1310001);
			pjList.add(1430001);
//			pjList.add(500501);
			
			WarAttr warAttr = BattleConverter.converterAttr(attrMap);

			WarSoldierBean wsbd = WarSoldierFactory.initPVEWarSoldier(100, pjList, acm, v2b);
			wsbd.warAttr = warAttr;

			DefendSoldierBean defenSoldierBean = new DefendSoldierBean();
			wsbd.position = new Vector2Bean();
			wsbd.position.x = 61;
			wsbd.position.y = 51;
			wsbd.playerId = 2222;
			defenSoldierBean.soldier = wsbd;
			FullSoldierBean fullSoldierBean = new FullSoldierBean();
			fullSoldierBean.soldier= new SoldierBean();
			wsbd.soldier = fullSoldierBean;
			wsbd.soldier.soldier.soldierId = 111111;
			wsbd.soldier.soldier.num = 50;
			wsbd.soldier.designMap = CustomWeaponConverter.converterDesignMapBean(CustomWeaponConverter.assemebleMonster(pjList));
			defenSoldierBean.buildingUid = 777;
			
			defendSoldiersList.add(defenSoldierBean);

			v2b = new Vector2Bean();
			v2b.x = 53;
			v2b.y = 61;
			wsbd = WarSoldierFactory.initPVEWarSoldier(50, pjList, acm, v2b);
			wsbd.playerId = 2222;
			
			defenSoldierBean = new DefendSoldierBean();
			defenSoldierBean.buildingUid = 777;
			defenSoldierBean.soldier = wsbd;
			fullSoldierBean = new FullSoldierBean();
			fullSoldierBean.soldier= new SoldierBean();
			wsbd.soldier = fullSoldierBean;
			wsbd.soldier.soldier.soldierId = 222222;
			wsbd.soldier.soldier.num = 50;
			wsbd.soldier.designMap = CustomWeaponConverter.converterDesignMapBean(CustomWeaponConverter.assemebleMonster(pjList));

			defendSoldiersList.add(defenSoldierBean);

			warDefendData.soldiers = defendSoldiersList;

			// 构建进攻方兵
			List<WarSoldierBean> attackerList = new ArrayList<WarSoldierBean>();
			attackData.oilNum = 999;
			attackData.attackUid = 12345;

			v2b = new Vector2Bean();
			v2b.x = 0;
			v2b.y = 0;
			wsbd = WarSoldierFactory.initPVEWarSoldier(300, pjList, acm, v2b);
			wsbd.index = 0;
			wsbd.playerId = 3333;
			wsbd.soldier = new FullSoldierBean();
			wsbd.soldier.soldier= new SoldierBean();
			wsbd.soldier.soldier.soldierId = 33331L;
			wsbd.soldier.soldier.num = 50;
			wsbd.soldier.designMap = CustomWeaponConverter.converterDesignMapBean(CustomWeaponConverter.assemebleMonster(pjList));
			wsbd.warAttr = warAttr;
			attackerList.add(wsbd);

			v2b = new Vector2Bean();
			v2b.x = 22;
			v2b.y = 12;
			wsbd = WarSoldierFactory.initPVEWarSoldier(200, pjList, acm, v2b);
			wsbd.playerId = 3333;
			wsbd.soldier = new FullSoldierBean();
			wsbd.soldier.soldier= new SoldierBean();
			wsbd.soldier.soldier.soldierId = 44441L;
			wsbd.soldier.soldier.num = 50;
			wsbd.soldier.designMap = CustomWeaponConverter.converterDesignMapBean(CustomWeaponConverter.assemebleMonster(pjList));
			attackerList.add(wsbd);
			wsbd.index = 1;
			int ggg = wsbd.warAttr.hp;
			attackData.soldiers = attackerList;
			
			
			String str = "{\"@type\":\"com.xgame.logic.server.game.war.message.ResWarDataMessage\",\"attackData\":{\"attackUid\":10050000000002,\"oilNum\":0,\"soldiers\":[{\"deadNum\":0,\"fightPower\":0,\"index\":1,\"playerId\":10050000000002,\"position\":{\"x\":0,\"y\":0},\"soldier\":{\"attrList\":[],\"designMap\":{\"buildIndex\":0,\"id\":10050000000021,\"index\":0,\"name\":\"\",\"partList\":[{\"partId\":1010001,\"position\":0},{\"partId\":1110001,\"position\":1},{\"partId\":1110001,\"position\":2},{\"partId\":1210001,\"position\":1},{\"partId\":1310001,\"position\":1},{\"partId\":1430001,\"position\":1}],\"systemIndex\":1,\"type\":1,\"unlock\":1,\"version\":1},\"soldier\":{\"num\":600,\"soldierId\":10050000000021}},\"warAttr\":{\"attack\":6,\"crit\":6,\"critical\":6,\"defend\":20,\"dodge\":12,\"electricityDamage\":0,\"electricityDefense\":0,\"energyDamage\":0,\"energyDefense\":0,\"heatDamage\":0,\"heatDefense\":0,\"hit\":12,\"hp\":28,\"laserDamage\":0,\"laserDefense\":0,\"load\":100,\"loadConsume\":37,\"power\":100,\"powerConsume\":37,\"radarIntensity\":12,\"radius\":10,\"seekingDistance\":0,\"seekingNum\":1,\"speedBase\":1.35,\"toughness\":6,\"weight\":1.2}}]},\"battleId\":9,\"battleType\":102,\"commandEnum\":\"PLAYERMSG\",\"defenData\":{\"buildings\":[{\"building\":{\"level\":15,\"sid\":1100,\"state\":2,\"uid\":811000},\"resourceNum\":0,\"transform\":{\"uid\":811000,\"vector2Bean\":{\"x\":44,\"y\":44}},\"warAttr\":{\"attack\":0,\"crit\":0,\"critical\":0,\"defend\":771000,\"dodge\":0,\"electricityDamage\":0,\"electricityDefense\":0,\"energyDamage\":0,\"energyDefense\":0,\"heatDamage\":0,\"heatDefense\":0,\"hit\":0,\"hp\":771000,\"laserDamage\":0,\"laserDefense\":0,\"load\":0,\"loadConsume\":0,\"power\":0,\"powerConsume\":0,\"radarIntensity\":0,\"radius\":0,\"seekingDistance\":0,\"seekingNum\":0,\"speedBase\":0,\"toughness\":0,\"weight\":0}},{\"building\":{\"level\":1,\"sid\":1102,\"state\":2,\"uid\":611020},\"resourceNum\":0,\"transform\":{\"uid\":611020,\"vector2Bean\":{\"x\":44,\"y\":40}},\"warAttr\":{\"attack\":0,\"crit\":0,\"critical\":0,\"defend\":16000,\"dodge\":0,\"electricityDamage\":0,\"electricityDefense\":0,\"energyDamage\":0,\"energyDefense\":0,\"heatDamage\":0,\"heatDefense\":0,\"hit\":0,\"hp\":16000,\"laserDamage\":0,\"laserDefense\":0,\"load\":0,\"loadConsume\":0,\"power\":0,\"powerConsume\":0,\"radarIntensity\":0,\"radius\":0,\"seekingDistance\":0,\"seekingNum\":0,\"speedBase\":0,\"toughness\":0,\"weight\":0}},{\"building\":{\"level\":15,\"sid\":1202,\"state\":2,\"uid\":612020},\"resourceNum\":0,\"transform\":{\"uid\":612020,\"vector2Bean\":{\"x\":41,\"y\":40}},\"warAttr\":{\"attack\":0,\"crit\":0,\"critical\":0,\"defend\":462600,\"dodge\":0,\"electricityDamage\":0,\"electricityDefense\":0,\"energyDamage\":0,\"energyDefense\":0,\"heatDamage\":0,\"heatDefense\":0,\"hit\":0,\"hp\":462600,\"laserDamage\":0,\"laserDefense\":0,\"load\":0,\"loadConsume\":0,\"power\":0,\"powerConsume\":0,\"radarIntensity\":0,\"radius\":0,\"seekingDistance\":0,\"seekingNum\":0,\"speedBase\":0,\"toughness\":0,\"weight\":0}}],\"defendUid\":10050000000001,\"soldiers\":[]},\"handlerEnum\":\"SC\",\"id\":500102,\"queue\":\"s2s\",\"sync\":false}";

			rwd = JsonUtil.fromJSON(str, ResWarDataMessage.class);
			/////////////////////////////////////////////// 数据伪造完毕

			if (!this.isInit) {
				log.debug("初始化战斗的LuaConfig.....");
				this.isInit = true;
				this.sysCfgLuaValue = CoerceJavaToLua.coerce(configSystem);
				this.pjCfgLuaValue = CoerceJavaToLua.coerce(PeiJianPirFactory.getInstance());

				path = FightCalc.class.getResource("/com/xgame/logic/server/core/fight/lua/").getPath();
				log.error("path============================={}", path);
				System.setProperty("luaj.package.path", path + "?.lua");
			}

			Globals globals = JsePlatform.debugGlobals();

			globals.load(new InputStreamReader(new FileInputStream(new File(path + "ServerFight.lua"))),
					"Server-Battle").call();

			LuaValue func = globals.get(LuaValue.valueOf("battleStart"));

			LuaValue battleMessageData = CoerceJavaToLua.coerce(rwd);
			
			WarEndReport wer = new WarEndReport();
			
			LuaValue warReport = CoerceJavaToLua.coerce(wer);

			func.invoke(new LuaValue[] { battleMessageData, this.sysCfgLuaValue, this.pjCfgLuaValue, warReport,
					LuaValue.valueOf(FPS), LuaValue.valueOf(Integer.MAX_VALUE) });
			

			System.out.println("--------------------battle calc over------------------" + wer.getWinUid());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
