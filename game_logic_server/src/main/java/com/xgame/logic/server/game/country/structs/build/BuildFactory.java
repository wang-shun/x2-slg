package com.xgame.logic.server.game.country.structs.build;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.xgame.logic.server.game.country.CountryManager;
import com.xgame.logic.server.game.country.structs.BuildCreate;
import com.xgame.logic.server.game.country.structs.build.Lab.LabBuildControl;
import com.xgame.logic.server.game.country.structs.build.army.ArmyBuildControl;
import com.xgame.logic.server.game.country.structs.build.bomb.BombBuildControl;
import com.xgame.logic.server.game.country.structs.build.camp.PlaneBuildControl;
import com.xgame.logic.server.game.country.structs.build.camp.SUVBuildControl;
import com.xgame.logic.server.game.country.structs.build.camp.TankBuildControl;
import com.xgame.logic.server.game.country.structs.build.consulate.ConsulateBuildControl;
import com.xgame.logic.server.game.country.structs.build.defenseSoldier.DefebseSoldierControl;
import com.xgame.logic.server.game.country.structs.build.defenseTower.DefenseTower1301;
import com.xgame.logic.server.game.country.structs.build.defenseTower.DefenseTower1302;
import com.xgame.logic.server.game.country.structs.build.defenseTower.DefenseTower1303;
import com.xgame.logic.server.game.country.structs.build.defenseTower.DefenseTower1304;
import com.xgame.logic.server.game.country.structs.build.defenseTower.DefenseTower1305;
import com.xgame.logic.server.game.country.structs.build.defenseTower.DefenseTowerBuildControl;
import com.xgame.logic.server.game.country.structs.build.gene.GeneStorageBuildControl;
import com.xgame.logic.server.game.country.structs.build.industry.IndustryBuildControl;
import com.xgame.logic.server.game.country.structs.build.main.MainBuildControl;
import com.xgame.logic.server.game.country.structs.build.militaryCamp.MilitaryCampControl;
import com.xgame.logic.server.game.country.structs.build.mine.MineBuildControl;
import com.xgame.logic.server.game.country.structs.build.mine.MineCarControl;
import com.xgame.logic.server.game.country.structs.build.mod.ModBuildControl;
import com.xgame.logic.server.game.country.structs.build.obstruct.BlockBuild1700Control;
import com.xgame.logic.server.game.country.structs.build.organism.OrganismReactorControl;
import com.xgame.logic.server.game.country.structs.build.partyShop.PartyShopBuildControl;
import com.xgame.logic.server.game.country.structs.build.prison.PrisonBuildControl;
import com.xgame.logic.server.game.country.structs.build.resource.MoneyResourceControl;
import com.xgame.logic.server.game.country.structs.build.resource.OilResourceControl;
import com.xgame.logic.server.game.country.structs.build.resource.RareResourceCountrol;
import com.xgame.logic.server.game.country.structs.build.resource.SteelResourceControl;
import com.xgame.logic.server.game.country.structs.build.superSpace.SuperSpaceBuildControl;
import com.xgame.logic.server.game.country.structs.build.tach.TechBuildControl;
import com.xgame.logic.server.game.country.structs.build.trade.TradeBuildControl;
import com.xgame.logic.server.game.country.structs.build.wall.WallBuildControl;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.radar.RadarBuildControl;

/**
 * 建筑生成器枚举 2016-7-15 17:18:55
 * 
 * @author ye.yuan
 *
 */
@SuppressWarnings("unchecked")
public enum BuildFactory {

	/**
	 * 主基地
	 */
	MAIN(1100, MainBuildControl.class),

	/**
	 * 障碍物
	 */
	OBSTRUCT1700(1700, BlockBuild1700Control.class),

	OBSTRUCT1701(1701, BlockBuild1700Control.class),

	OBSTRUCT1702(1702, BlockBuild1700Control.class),

	/**
	 * 科技馆
	 */
	TECH(1102, TechBuildControl.class),

	/**
	 * 飞机厂
	 */
	PLANE(1201, PlaneBuildControl.class),

	/**
	 * 坦克
	 */
	TANK(1202, TankBuildControl.class),

	/**
	 * 越野车工厂
	 */
	SUV(1203, SUVBuildControl.class),

	/**
	 * 生物实验室
	 */
	LAB(1103, LabBuildControl.class),

	/**
	 * 修理厂
	 */
	MOD(1204, ModBuildControl.class),

	/**
	 * 交易市场
	 */
	TRADE(1110, TradeBuildControl.class),

	/**
	 * 雷达
	 */
	RADAR(1108, RadarBuildControl.class),

	/**
	 * 采矿场
	 */
	MINE(1405, MineBuildControl.class),

	/**
	 * 矿车
	 */
	MINE_CAR(1406, MineCarControl.class),

	/**
	 * 工业工厂
	 */
	INDUSTRY_FACTORY(1101, IndustryBuildControl.class),

	/**
	 * 防守驻地
	 */
	DEFEBSE_SOLDIER(1300, DefebseSoldierControl.class),

	/**
	 * 军营
	 */
	MILITARY_CAMP(1200, MilitaryCampControl.class),

	/**
	 * 金钱
	 */
	MONEY_RESOURCE(1401, MoneyResourceControl.class),

	/**
	 * 稀土
	 */
	RARE_RESOURCE(1402, RareResourceCountrol.class),

	/**
	 * 石油
	 */
	OIL_RESOURCE(1403, OilResourceControl.class),

	/**
	 * 钢材
	 */
	STEEL_RESOURCE(1404, SteelResourceControl.class),

	/**
	 * 歼击炮
	 */
	TOWER1301(1301, DefenseTower1301.class),

	/**
	 * 近防抛
	 */
	TOWER1302(1302, DefenseTower1302.class),

	/**
	 * 防空炮
	 */
	TOWER1303(1303, DefenseTower1303.class),

	/**
	 * 火焰炮
	 */
	TOWER1304(1304, DefenseTower1304.class),

	/**
	 * 电磁塔
	 */
	TOWER1305(1305, DefenseTower1305.class),

	/**
	 * 超时空电站
	 */
	SUPER_SPACE(1306, SuperSpaceBuildControl.class),

	/**
	 * 外事联络处
	 */
	CONSULATE(1104, ConsulateBuildControl.class),

	/**
	 * 军营
	 */
	ARMY(1200, ArmyBuildControl.class),

	/**
	 * 基因存储站
	 */
	GENE(1106, GeneStorageBuildControl.class),

	/**
	 * 生化反应堆
	 */
	ORGANISM(1105, OrganismReactorControl.class),

	/**
	 * 联盟商店
	 */
	PARTY_SHOP(1107, PartyShopBuildControl.class),

	/**
	 * 监狱
	 */
	PRISON(1109, PrisonBuildControl.class),

	/**
	 * 围墙
	 */
	WALL(1500, WallBuildControl.class),

	/**
	 * 隐形炸弹
	 */
	BOMB(1600, BombBuildControl.class),

	/**
	 * 外事联络出
	 */
	REINFORCE(1104, BombBuildControl.class),;

	private Class<?> classForm;

	private int tid;

	private static final Map<String, Field> FIELD_MAP = new HashMap<>();

	private static final Map<Integer, BuildFactory> MAP = new HashMap<>();

	static {
		for (int i = 0; i < values().length; i++) {
			MAP.put(values()[i].tid, values()[i]);
		}
		
		Field[] declaredFields = CountryManager.class.getDeclaredFields();
		for (int i = 0; i < declaredFields.length; i++) {
			if (declaredFields[i].getAnnotation(BuildCreate.class) != null) {
				declaredFields[i].setAccessible(true);
				FIELD_MAP.put(declaredFields[i].getType().getSimpleName(), declaredFields[i]);
			}
		}
	}

	private BuildFactory(int tid, Class<?> class1) {
		this.tid = tid;
		this.classForm = class1;
	}

	public static boolean instanceOf(int sid, Class<?> superClass) {
		BuildFactory buildFactory = MAP.get(sid);
		System.out.println(superClass.isAssignableFrom(buildFactory.classForm) + ">>>>"
				+ DefenseTower1305.class.isAssignableFrom(DefenseTowerBuildControl.class) + ">>>>>" + sid);
		return buildFactory != null && superClass.isAssignableFrom(buildFactory.classForm);
	}

	public static <T extends BuildControl> T getOrCreate(Player player, int tid) {
		BuildControl buildControl = player.getCountryManager().getBuildControls().get(tid);
		try {
			if (buildControl == null) {
				BuildFactory buildFactory = MAP.get(tid);
				if (buildFactory != null) {
					Field field = BuildFactory.FIELD_MAP.get(buildFactory.classForm.getSimpleName());
					if (field != null) {
						buildControl = (BuildControl) buildFactory.classForm.newInstance();
						buildControl.sid = tid;
						field.set(player.getCountryManager(), buildControl);
					}
				} else {
					// TODO 先容错 开发期确保没有写的建筑也可以建
					buildControl = new BuildControl();
				}
				player.getCountryManager().getBuildControls().put(tid, buildControl);
				buildControl.initControl(player);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (T) buildControl;
	}

	public int getTid() {
		return tid;
	}

	public void setSid(int sid) {
		this.tid = sid;
	}

	public Class<?> getClassForm() {
		return classForm;
	}

	public void setClassForm(Class<?> classForm) {
		this.classForm = classForm;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}
	
}
