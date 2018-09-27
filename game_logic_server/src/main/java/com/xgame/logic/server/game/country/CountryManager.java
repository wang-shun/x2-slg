package com.xgame.logic.server.game.country;

 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.common.BuildCondtitionBean;
import com.xgame.common.Material;
import com.xgame.common.RefreshBuildConfig;
import com.xgame.config.attribute.AttributeConfMap;
import com.xgame.config.building.BuildingPir;
import com.xgame.config.building.BuildingPirFactory;
import com.xgame.config.global.GlobalPirFactory;
import com.xgame.logic.server.core.component.Componentable;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.language.view.success.SuccessTipEnum;
import com.xgame.logic.server.core.utils.CurrencyUtil;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.geometry.FindBorderAreaResult;
import com.xgame.logic.server.core.utils.geometry.able.SpaceFactory;
import com.xgame.logic.server.core.utils.geometry.conf.RingConf;
import com.xgame.logic.server.core.utils.geometry.data.Vector2;
import com.xgame.logic.server.core.utils.geometry.data.transform.BuildTransform;
import com.xgame.logic.server.core.utils.geometry.node.CountryNode0;
import com.xgame.logic.server.core.utils.geometry.node.CountryNode1;
import com.xgame.logic.server.core.utils.geometry.node.CountryNode2;
import com.xgame.logic.server.core.utils.geometry.node.CountryNode3;
import com.xgame.logic.server.core.utils.geometry.node.QuadNode;
import com.xgame.logic.server.core.utils.geometry.node.SpaceNode;
import com.xgame.logic.server.core.utils.geometry.space.RingArea;
import com.xgame.logic.server.core.utils.geometry.space.RingSpace;
import com.xgame.logic.server.game.bag.ItemKit;
import com.xgame.logic.server.game.country.bean.BuildBean;
import com.xgame.logic.server.game.country.bean.CountryBean;
import com.xgame.logic.server.game.country.bean.UpdataBuildPro;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.country.entity.BaseCountry;
import com.xgame.logic.server.game.country.entity.BlockDeleteInfo;
import com.xgame.logic.server.game.country.entity.CountryBuild;
import com.xgame.logic.server.game.country.entity.MineCar;
import com.xgame.logic.server.game.country.entity.MineRepository;
import com.xgame.logic.server.game.country.entity.XBuild;
import com.xgame.logic.server.game.country.entity.eventmodel.LevelUpBulidFinishEventObject;
import com.xgame.logic.server.game.country.entity.eventmodel.LevelupBuildEventObject;
import com.xgame.logic.server.game.country.entity.eventmodel.RemoveObstacleEventObject;
import com.xgame.logic.server.game.country.entity.eventmodel.RemoveObstacleFinishEventObject;
import com.xgame.logic.server.game.country.message.ResAllCountryMessage;
import com.xgame.logic.server.game.country.message.ResCreateBuildMessage;
import com.xgame.logic.server.game.country.message.ResMineRepositoryMessage;
import com.xgame.logic.server.game.country.message.ResMoveBuildMessage;
import com.xgame.logic.server.game.country.message.ResRemoveBuildingMessage;
import com.xgame.logic.server.game.country.message.ResUpdataCountryMessage;
import com.xgame.logic.server.game.country.structs.BuildCreate;
import com.xgame.logic.server.game.country.structs.BuildInfo;
import com.xgame.logic.server.game.country.structs.build.BlockBuildControl;
import com.xgame.logic.server.game.country.structs.build.BuildControl;
import com.xgame.logic.server.game.country.structs.build.BuildFactory;
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
import com.xgame.logic.server.game.country.structs.build.gene.GeneStorageBuildControl;
import com.xgame.logic.server.game.country.structs.build.industry.IndustryBuildControl;
import com.xgame.logic.server.game.country.structs.build.main.MainBuildControl;
import com.xgame.logic.server.game.country.structs.build.militaryCamp.MilitaryCampControl;
import com.xgame.logic.server.game.country.structs.build.mine.MineBuildControl;
import com.xgame.logic.server.game.country.structs.build.mine.MineCarControl;
import com.xgame.logic.server.game.country.structs.build.mod.ModBuildControl;
import com.xgame.logic.server.game.country.structs.build.obstruct.BlockBuild1700Control;
import com.xgame.logic.server.game.country.structs.build.obstruct.BlockBuild1701Control;
import com.xgame.logic.server.game.country.structs.build.obstruct.BlockBuild1702Control;
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
import com.xgame.logic.server.game.country.structs.status.TimeState;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.player.entity.RoleInfo;
import com.xgame.logic.server.game.playerattribute.AttributeUtil;
import com.xgame.logic.server.game.playerattribute.IAttributeAddModule;
import com.xgame.logic.server.game.playerattribute.PlayerAttributeManager;
import com.xgame.logic.server.game.playerattribute.constant.AttributeFromEnum;
import com.xgame.logic.server.game.playerattribute.constant.AttributeNodeEnum;
import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;
import com.xgame.logic.server.game.playerattribute.fightpower.FightPowerKit;
import com.xgame.logic.server.game.radar.RadarBuildControl;
import com.xgame.logic.server.game.timertask.constant.TimerTaskCommand;
import com.xgame.logic.server.game.timertask.entity.job.CreatBuildTask;
import com.xgame.logic.server.game.timertask.entity.job.ITimerTask;
import com.xgame.logic.server.game.timertask.entity.job.TimerTaskHolder;
import com.xgame.logic.server.game.timertask.entity.job.data.BuildTimerTaskData;
import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;
import com.xgame.utils.TimeUtils;

/**
 * 家园
 * 每个玩家的家园系统,  家园地表四叉共用一个,所有玩家自己存阻挡信息,不要对XArea的数据进行任何改变
 * 家园建筑物每个玩家一套,玩家多个家园数据只存储位置信息
 * 2016-7-12 11:01:44
 *
 * @author ye.yuan
 *
 */
@Component
@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CountryManager implements Componentable<Player>,IAttributeAddModule{
	
	@Autowired
	private EventBus gameLog;
	
	/**
	 * 客户端服务器建筑id生产前缀
	 */
	public static final int CLIENT_ID = 6,SERVER_ID = 8;
	
	/**
	 * 共用的环形空间
	 */
	private static RingSpace space;
	
	/**
	 * 初始化环形空间
	 */
	static{
		int[][] arr = new int[4][2];
		arr[0] = new int[] { 10, 10 };
		arr[1] = new int[] { 30, 30 };
		arr[2] = new int[] { 44, 44 };
		arr[3] = new int[] { 88, 88 };
		space = new RingSpace(new RingConf(1, 1, 1, 1,new CountryFactory(),arr));
	}
	
	/**
	 * 建筑物的控制器
	 */
	private Map<Integer, BuildControl> buildControls = new HashMap<>();
	
	private Player player;

	@Override
	public void firstLoad(Object ... param) {
		//加载障碍物
		loadBlockBuild();
		/* 出生就有的建筑   */
		// 主基地
		if(mainBuildControl == null) {
			Vector2 center = new Vector2(CountryManager.space.getHalfX(), CountryManager.space.getHalfY());
			mainBuildControl = (MainBuildControl) player.getCountryManager().fastCreateBuildAndPutDown(newUid(CountryManager.SERVER_ID, BuildFactory.MAIN.getTid()), BuildFactory.MAIN.getTid(), center.getX(), center.getY(),TimeState.USEING.ordinal(),false);
		}
		// 资源矿场
		if(mineBuildControl == null){
			mineBuildControl = (MineBuildControl)player.getCountryManager().fastCreateBuildAndPutDown(newUid(CountryManager.SERVER_ID, BuildFactory.MINE.getTid()), BuildFactory.MINE.getTid(), 18, 66, TimeState.USEING.ordinal(),false);
		}
		//军营
		if(armyBuildControl == null){
			armyBuildControl = (ArmyBuildControl)player.getCountryManager().fastCreateBuildAndPutDown(newUid(CountryManager.SERVER_ID, BuildFactory.ARMY.getTid()), BuildFactory.ARMY.getTid(), 73, 76, TimeState.USEING.ordinal(),false);
		}
	}
	
	/**
	 * 加载家园组件的初始化
	 */
	@Override
	public void loadComponent(Object ... param) {
		BaseCountry baseCountry = ((RoleInfo)param[0]).getBaseCountry();
		//初始化数据创建控制器
		Iterator<? extends XBuild> iterator = baseCountry.getAllBuild().values().iterator();
		while (iterator.hasNext()) {
			XBuild build = iterator.next();
			BuildControl create = BuildFactory.getOrCreate(player,build.getSid());
			create.getBuildMap().put(build.getUid(), build);
			buildControls.put(build.getSid(), create);
		}
		
		//初始化障碍物集合
		iterator = baseCountry.getBlocks().values().iterator();
		while (iterator.hasNext()) {
			XBuild build = iterator.next();
			BuildControl create = BuildFactory.getOrCreate(player,build.getSid());
			create.getBuildMap().put(build.getUid(), build);
			buildControls.put(build.getSid(), create);
		}
		
		//初始化数据后 回调已有控制器
		Iterator<BuildControl> iterator2 = buildControls.values().iterator();
		while (iterator2.hasNext()) {
			BuildControl BuildControl = iterator2.next();
			BuildControl.dataLoad(player);
		}
	}
	
	/**
	 * 客户端登录游戏 初始化
	 */
	@Override
	public void loginLoad(Object... param) {
		Iterator<BuildControl> iterator = player.getCountryManager().getBuildControls().values().iterator();
		while (iterator.hasNext()) {
			BuildControl buildControl = iterator.next();
			buildControl.send(player);
		}
	}
	

	/**
	 * 刚进入游戏的时候随机刷新障碍物
	 * @param player
	 */
	public void loadBlockBuild() {
		 int nextInt = RandomUtils.nextInt(0, GlobalPirFactory.getInstance().refreshBuilds.size());
		 RefreshBuildConfig[] refreshBuildConfigs = GlobalPirFactory.getInstance().refreshBuilds.get(nextInt);
		 for(int i = 0;i<refreshBuildConfigs.length;i++){
			 BuildingPir buildingPir = BuildingPirFactory.get(refreshBuildConfigs[i].getId());
			 for(int j=0;j<refreshBuildConfigs[i].getArrys().length;j++){
				int[] arr = refreshBuildConfigs[i].getArrys()[j];
				int newUid = newUid(SERVER_ID, buildingPir.getId());
				fastCreateBuildAndPutDown(newUid, buildingPir.getId(), arr[0], arr[1], TimeState.USEING.ordinal(),true);
			 }
		 }
		 InjectorUtil.getInjector().dbCacheService.update(player);
	}
	
	/**
	 * 一定时间进来刷一次障碍物
	 */
	public void refreshObstructBuild() {
		int curTime = TimeUtils.getCurrentTime();
		boolean isOk = false;
		Iterator<BlockDeleteInfo> iterator = player.roleInfo().getBaseCountry().getBlockRefreshInfos().iterator();
		while (iterator.hasNext()) {
			BlockDeleteInfo blockBuild = iterator.next();
			BuildingPir buildingPir = BuildingPirFactory.get(blockBuild.getSid());
			if(buildingPir==null){
				continue;
			}
			
			if (curTime < blockBuild.getRemoveTime() + buildingPir.getCD()) {
				continue;
			}
			
			RingArea[] ringAreas = space.getRingAreas();
			RingArea ringArea = ringAreas[RandomUtils.nextInt(0, ringAreas.length-1)];
			if(ringArea!=null){
				Vector2 randomPoint = ringArea.linkedRandomPoint();
				if(randomPoint!=null){
					SpaceNode node = space.getNode(randomPoint.getX(),randomPoint.getY());
					Vector2 location = node.getSpaceTransform().getLocation();
					BuildControl fastCreateBuild = fastCreateBuildAndPutDown(blockBuild.getUid(), buildingPir.getId(), location.getX(), location.getY(), TimeState.USEING.ordinal(),true);
					if(fastCreateBuild!=null){
						iterator.remove();
						InjectorUtil.getInjector().dbCacheService.update(player);
						isOk=true;
					}
				}
			}
		}
		
		if(isOk) {
			updataBuild(player, player.roleInfo().getBaseCountry().getBlocks().values());
		}
	}
	
	public void updataBuild(Player player,Collection<? extends XBuild> builds){
		ResUpdataCountryMessage countryMessage = new ResUpdataCountryMessage();
		Iterator<? extends XBuild> iterator = builds.iterator();
		while (iterator.hasNext()) {
			XBuild xBuild = iterator.next();
			BuildTransform transform = player.roleInfo().getBaseCountry().getBuildTransform(player.roleInfo().getBaseCountry().getUseTemplateId(), xBuild.getUid());
			if(transform!=null){
				UpdataBuildPro buildPro = new UpdataBuildPro();
				buildPro.build = xBuild.toBuildBean(player);
				buildPro.transform = CountryMsgGener.toTransformBean(transform);
				countryMessage.updataBuilds.add(buildPro);
			}
		}
		player.send(countryMessage);
	}
	
	/**
	 * 生存id
	 * @param player 
	 * @return
	 */
	public int newUid(int idPrefix,int sid){
		int index = 0;
		BuildControl BuildControl = buildControls.get(sid);
		if(BuildControl!=null){
			index = BuildControl.getBuildMap().size();
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append(idPrefix).append(sid).append(index);
		return Integer.parseInt(buffer.toString());
	}

	public void buildLevelTimeEnd(TimerTaskData timerTaskData) {
		BuildTimerTaskData buildTimerTaskData = (BuildTimerTaskData)timerTaskData.getParam();
		BuildControl buildControl = buildControls.get(buildTimerTaskData.getSid());
		buildControl.incrementLevel(player, buildTimerTaskData.getBuildingUid(), 1);
	}
	
	/**
	 * 请求建筑升级
	 * @param sid
	 * @param uid
	 * @param useType
	 */
	public void buildLevelUp(int sid,int uid,int useType){
		BuildControl buildControl = buildControls.get(sid);
		if(buildControl!=null){
			buildControl.buildLevelUp(player,uid,useType);
		}
	}
	
	/**
	 * 删除建筑物  不验证是否是可删除类型  如障碍物    如果绕过客户端乱发导致号不能用了   那么活该
	 * @param player
	 * @param uid
	 */
	public void removeBlock(int uid,int sid){
		BuildControl buildControl = buildControls.get(sid);
		if(buildControl==null||!(buildControl instanceof BlockBuildControl)){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E100_COUNTRY.CODE13);
			return;
		}
		XBuild build = buildControl.getBuild(uid);
		BuildingPir buildingPir = BuildingPirFactory.get(sid);
		if(build==null||buildingPir==null){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE18);
			return;
		}
		
		//队列数量达到最大
		ITimerTask<?> timerTask = TimerTaskHolder.getTimerTask(TimerTaskCommand.REMOVE_BUILD);
		if(timerTask.checkQueueCapacityMax(player)) {
			Language.ERRORCODE.send(player,ErrorCodeEnum.E100_COUNTRY.CODE1);
			return;
		}
		
		//验证资源是否足够
		Material[] cost_types = buildingPir.getCost_type();
		for(int i=0;i<cost_types.length;i++){
			if(!CurrencyUtil.verifyFinal(player, cost_types[i].getType(), cost_types[i].getNum())){
				Language.ERRORCODE.send(player,ErrorCodeEnum.E001_LOGIN.CODE11);
				return;
			}
		}
		
		//扣除资源
		for(int i=0;i<cost_types.length;i++){
			CurrencyUtil.decrementFinal(player,cost_types[i].getType(),  cost_types[i].getNum(), GameLogSource.REMOVE_BUILD);
		}
		
		//加入时间队列等待到时
		build.setState(TimeState.REMOVEING.ordinal());
		
		//系统帮使用
		TimerTaskHolder.getTimerTask(TimerTaskCommand.REMOVE_BUILD).register(player, buildingPir.getCost_time1(), new BuildTimerTaskData(sid,uid,TimeState.REMOVEING.ordinal(), build.getLevel(), getBuildLocation(uid)));
		InjectorUtil.getInjector().dbCacheService.update(player);
		CurrencyUtil.send(player);
		
		Vector2Bean vector2Bean = this.getBuildLocation(uid);
		EventBus.getSelf().fireEvent(new RemoveObstacleEventObject(player, EventTypeConst.REMOVE_OBSTACLE, vector2Bean));
		
		Language.SUCCESSTIP.send(player,SuccessTipEnum.E100_COUNTRY.CODE6);
	}
	
	/**
	 * 删除成功
	 * @param sid
	 * @param uid
	 */
	public void removeSuccess(int sid,int uid) {
		//控制器是否有
		BuildControl buildControl = buildControls.get(sid);
		if(buildControl==null){
			return;
		}
		//建筑物数据是否有
		XBuild build = buildControl.getBuild(uid);
		BuildingPir buildingPir = BuildingPirFactory.get(sid);
		if(build==null||buildingPir==null)return;
		//添加奖励
		ItemKit.addItems(player, buildingPir.getClearance() , GameLogSource.REMOVE_BUILD);
		//删除建筑物
		remove:{
			buildControl.getBuildMap().remove(uid);
			//删除数据
			buildControl.remove(player, uid);
			//删除建筑在每个模版里面的数据
			Iterator<Entry<Integer, Map<Integer, BuildTransform>>> iterator = player.roleInfo().getBaseCountry().getBuildTransforms().entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<Integer, Map<Integer, BuildTransform>> next = iterator.next();
				next.getValue().remove(uid);
			}
			break remove;
		}
		player.roleInfo().getBaseCountry().getBlockRefreshInfos().add(new BlockDeleteInfo(build.getSid(),build.getUid(),TimeUtils.getCurrentTime()));
		
		//  移除障碍物成功
		Vector2Bean vector2Bean = this.getBuildLocation(uid);
		EventBus.getSelf().fireEvent(new RemoveObstacleFinishEventObject(player, EventTypeConst.REMOVE_OBSTACLE, vector2Bean, null));
		
		InjectorUtil.getInjector().dbCacheService.update(player);
		ResRemoveBuildingMessage buildMessage=new ResRemoveBuildingMessage();
		buildMessage.sid=sid;
		buildMessage.uid=uid;
		player.send(buildMessage);
	}
	
	/**
	 * 客户端请求移动已有建筑物
	 * @param player
	 * @param uid
	 * @param x
	 * @param y
	 * @param templateId
	 */
	public void reqMoveBuild( int uid,int sid, int x, int y) {
		ResMoveBuildMessage moveBuildMessage = new ResMoveBuildMessage();
		moveBuildMessage.uid = uid;
		BuildControl countryBuild = buildControls.get(sid);
		if (countryBuild == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE7.get());
			player.send(moveBuildMessage);
			return;
		}
		CountryBuild build = countryBuild.getBuild(uid);
		if(build == null){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE7.get());
			player.send(moveBuildMessage);
			return;
		}
		BuildTransform buildTransform = player.roleInfo().getBaseCountry().getBuildTransform(player.roleInfo().getBaseCountry().getUseTemplateId(), uid);
		if(buildTransform==null){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E100_COUNTRY.CODE12.get());
			player.send(moveBuildMessage);
			return;
		}
		Vector2 location = buildTransform.getLocation();
		moveBuildMessage.x = location.getX();
		moveBuildMessage.y = location.getY();
		if(!countryBuild.isMove()){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E100_COUNTRY.CODE12.get());
			player.send(moveBuildMessage);
			return;
		}
		BuildingPir buildingPir = BuildingPirFactory.get(sid);
		if(buildingPir==null){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE18.get());
			player.send(moveBuildMessage);
			return;
		}
		BuildTransform fastMoveBuild = fastMoveBuild(player.roleInfo().getBaseCountry().getUseTemplateId(),x, y, uid,sid,false);
		if(fastMoveBuild!=null){
			moveBuildMessage.x = fastMoveBuild.getLocation().getX();
			moveBuildMessage.y = fastMoveBuild.getLocation().getY();
			moveBuildMessage.isSuccess = 1;
		}
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E100_COUNTRY.CODE4.get());
		InjectorUtil.getInjector().dbCacheService.update(player);
		player.send(moveBuildMessage);
	}
	
	
	/**
	 * 使用模版
	 * @param player
	 * @param templateId
	 */
	public void useTemplate( int templateId) {
		// 启用阵型先把老的搞掉
		if(!verifyCountryNum(templateId)){
			return;
		}
		player.roleInfo().getBaseCountry().setUseTemplateId(templateId);
		InjectorUtil.getInjector().dbCacheService.update(player);
	}
	
	public boolean verifyBeforeBuild(Player player, Map<Integer, BuildCondtitionBean> buildCondtition, int level) {
		BuildCondtitionBean condtitionBean = buildCondtition.get(level);
		if (condtitionBean != null) {
			Iterator<Entry<Integer, Integer>> iterator = condtitionBean.getCache().entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<Integer, Integer> entry = iterator.next();
				BuildControl buildControl = player.getCountryManager().getBuildControls().get(entry.getKey());
				XBuild maxLevelBuild = buildControl.getMaxLevelBuild();
				if (maxLevelBuild == null || maxLevelBuild.getLevel() < entry.getValue()) {
					return false;
				}
			}
		}
		return true;
	}
	

	/**
	 * 客户端请求创建建筑物  带验证
	 * @param player
	 * @param sid
	 * @param uid
	 * @param x
	 * @param y
	 * @param createType 购买类型
	 * @param templateId 国家模版id
	 */
	public void reqCreateBuild( int sid, int clientUid, int x, int y, int createType) {
		int uid = newUid(CLIENT_ID, sid);
		ResCreateBuildMessage buildMessage = new ResCreateBuildMessage();
		buildMessage.clientGenUid = clientUid;
		buildMessage.serverGenUid = uid;
		buildMessage.sid = sid;
		
		if (clientUid != uid) {
			player.send(buildMessage);
			Language.ERRORCODE.send(player, ErrorCodeEnum.E100_COUNTRY.CODE2.get());
			return;
		}
		
		//建筑配置是否存在
		BuildingPir buildingPir = BuildingPirFactory.get(sid);
		if(buildingPir==null){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E100_COUNTRY.CODE2.get());
			player.send(buildMessage);
			return;
		}
		
		//本类型建筑>最大建筑数
		BuildControl buildControl = buildControls.get(sid);
		Map<Integer, Integer> mainNums = buildingPir.getMain_num();
		if (buildControl != null && mainNums.get(player.getLevel()) < buildControl.getBuildMap().size()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E100_COUNTRY.CODE4.get(), buildControl.getBuildMap().size()+"", mainNums.get(player.getLevel())+"");
			player.send(buildMessage);
			return;
		}
		//如果有队列在执行  走
		ITimerTask<?> timerTask = TimerTaskHolder.getTimerTask(TimerTaskCommand.BUILD);
		log.info("queue size:{}", timerTask.checkQueueCapacityMax(player));
		if(createType == CurrencyUtil.USE&&timerTask.checkQueueCapacityMax(player)){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E100_COUNTRY.CODE5.get());
			player.send(buildMessage);
			return;
		}
		int level = 1;
		//前置建筑不足
		if(!verifyBeforeBuild(player, buildingPir.getRequire_id(), level)){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E100_COUNTRY.CODE9.get());
			player.send(buildMessage);
			return;
		}
		int useTemplateId = player.roleInfo().getBaseCountry().getUseTemplateId();
		//尝试是否能放下
		FindBorderAreaResult findBorderAndBuild = tryPutDown(useTemplateId, x, y, uid, sid,false);
		if(findBorderAndBuild==null){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E100_COUNTRY.CODE6.get());
			player.send(buildMessage);
			return;
		}
		//扣钱(判断建造建筑扣除资源)
		Map<Integer, Integer> cost_oil = buildingPir.getCost_oil();
		Map<Integer, Integer> cost_gold = buildingPir.getCost_cash();
		Map<Integer, Integer> cost_earth = buildingPir.getCost_earth();
		Map<Integer, Integer> cost_steel = buildingPir.getCost_steel();
		Map<Integer, Integer> cost_time = buildingPir.getCost_time();
		
		Integer oil = cost_oil.get(level);
		Integer money = cost_gold.get(level);
		Integer steels = cost_steel.get(level);
		Integer rare = cost_earth.get(level);
		Integer time = cost_time.get(level);
		time = PlayerAttributeManager.get().buildingCreateTime(player.getId(), time);
		//扣钱失败 走
		if(!CurrencyUtil.decrementCurrency(player, money, steels, oil, rare, createType, time, GameLogSource.BUILD_LEVEL_UP)) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE11.get());
			player.send(buildMessage);
			return;
		}
		
		//创建建筑物
		BuildControl createBuild = createBuild(uid, sid,TimeState.CREATEING.ordinal());
		
		CountryBuild build = createBuild.getBuild(uid);
		//把建筑物放入位置
		putDown(useTemplateId, x, y, uid, sid, findBorderAndBuild);
		// 普通建造
		Vector2Bean vector2Bean = new Vector2Bean();
		vector2Bean.x = x;
		vector2Bean.y = y;
		// 升级事件参数
		long fightPower = FightPowerKit.FINAL_POWER.getValue(player.getId()).longValue();
		EventBus.getSelf().fireEvent(new LevelupBuildEventObject(player, EventTypeConst.EVENT_BUILD_LEVELUP_START, sid, time, vector2Bean, fightPower));
		if (createType == CurrencyUtil.USE) {
			timerTask.register(player, CreatBuildTask.CREATE_BUILD_CMD, time, new BuildTimerTaskData(sid, uid,build.getState(), build.getLevel(), vector2Bean));
		} else if (createType == CurrencyUtil.FAST_USE) {
			timeUp(sid, uid);
		} else {
			timerTask.register(player, CreatBuildTask.CREATE_BUILD_CMD, time, new BuildTimerTaskData(sid,uid,build.getState(), build.getLevel(), vector2Bean));
			Language.SUCCESSTIP.send(player, SuccessTipEnum.E100_COUNTRY.CODE1.get());
		}
 		buildMessage.isSuccess=1;
		buildMessage.x = x;
		buildMessage.y = y;
		InjectorUtil.getInjector().dbCacheService.update(player);
		log.info("create end");
		player.send(buildMessage);
	}
	
	/**
	 * 建造时间结束
	 * @param sid
	 * @param uid
	 */
	public void timeUp(int sid,int uid) {
		BuildControl buildControl = buildControls.get(sid);
		CountryBuild build = buildControl.getBuild(uid);
		createEnd(build, buildControl);
		InjectorUtil.getInjector().dbCacheService.update(player);
		CurrencyUtil.send(player);
		LevelUpBulidFinishEventObject bulidLevelUpEventObject = new LevelUpBulidFinishEventObject(player , EventTypeConst.EVENT_BUILD_LEVELUP_FINISH, build.getSid(), build.getLevel() - 1, 0,0);
		EventBus.getSelf().fireEvent(bulidLevelUpEventObject);
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E100_COUNTRY.CODE2.get());
	}
	
	/**
	 * 建造完成
	 * @param build
	 * @param buildControl
	 */
	private void createEnd(XBuild build,BuildControl buildControl){
		//状态改成使用中
		build.setState(TimeState.USEING.ordinal());
		//创建结束控制器回调
		buildControl.createEnd(player, build.getUid());
	}
	
	/**
	 * 创建建筑物并让如指定位置
	 * @param uid 建筑唯一id
	 * @param sid 配置id
	 * @param countryId 家园模版id
	 * @param x 
	 * @param y
	 * @param verifyBorder 是否把描边格子也算入(即原格子向外扩一格)
	 * @return
	 */
	public BuildControl fastCreateBuildAndPutDown(int uid,int sid,int x,int y,int state,boolean verifyBorder){
		//尝试放入指定位置
		FindBorderAreaResult findBorderAndBuild = tryPutDown(player.roleInfo().getBaseCountry().getUseTemplateId(), x, y, uid, sid,verifyBorder);
		if(findBorderAndBuild == null){
			return null;
		}
		//创建建筑
		BuildControl fastCreateBuild = fastCreateBuild(uid, sid);
		if(fastCreateBuild!=null){
			//创建成功 放入位置
			putDown(player.roleInfo().getBaseCountry().getUseTemplateId(), x, y, uid, sid,findBorderAndBuild);
		}
		return fastCreateBuild;
	}
	
	/**
	 * 创建一个建筑物
	 * @param uid
	 * @param sid
	 * @return
	 */
	public BuildControl fastCreateBuild(int uid,int sid){
		BuildingPir buildingPir = BuildingPirFactory.get(sid);
		if(buildingPir==null){
			return null;
		}
		//创建建筑物
		BuildControl createBuild = createBuild(uid, sid, TimeState.USEING.ordinal());
		if(createBuild!=null){
			createEnd(createBuild.getBuild(uid), createBuild);
		}
		return createBuild;
	}
	
	/**
	 * 创建建筑物
	 * @param uid
	 * @param sid
	 * @param x
	 * @param y
	 * @param state
	 * @return
	 */
	private BuildControl createBuild(int uid,int sid,int state){
		//创建建筑物
		BuildControl create = BuildFactory.getOrCreate(player, sid);
		if(create==null)return null;
		create.createInit(player,uid,state);
		return create;
	}
	
	
	/**
	 * 尝试放入建筑物   找到格子 看是否放下
	 * @param templateId
	 * @param x
	 * @param y
	 * @param uid
	 * @param sid
	 * @param verifyBorder 是否验证描边
	 * @return
	 */
	private FindBorderAreaResult tryPutDown(int templateId,int x, int y,int uid,int sid,boolean verifyBorder) {
		BuildingPir buildingPir = BuildingPirFactory.get(sid);
		// 找到格子
		FindBorderAreaResult findBorderAndBuild = space.findBorderArea(x, y, buildingPir.getSize(), 1);
		if (findBorderAndBuild==null) {
			return null;
		}
		//如果找到的格子有阻挡信息  走
		Map<Integer, BuildTransform> rows = player.roleInfo().getBaseCountry().getBuildTransforms().get(player.roleInfo().getBaseCountry().getUseTemplateId());
		Iterator<SpaceNode> iterator3 = findBorderAndBuild.getBuildMap().values().iterator();
		while (iterator3.hasNext()) {
			SpaceNode spaceNode = (SpaceNode) iterator3.next();
			if(rows != null) {
				Iterator<BuildTransform> iterator = rows.values().iterator();
				while (iterator.hasNext()) {
					BuildTransform buildTransform = iterator.next();
					if (buildTransform != null && buildTransform.getUid() != uid) {
						if(buildTransform.getNodePoints().containsKey(spaceNode.getId())||
						    verifyBorder&&buildTransform.getBorderPoints().containsKey(spaceNode.getId())){
							return null;
						}
					}
				}
			}
		}
		return findBorderAndBuild;
	}

	/**
	 * 重一个点  移动到另外一个     非向量移动
	 * @param player
	 * @param x
	 * @param y
	 * @param uid
	 * @param sid
	 * @param country
	 * @return
	 */
	private BuildTransform fastMoveBuild(int templateId,int x, int y,int uid,int sid,boolean verifyBorder) {
		FindBorderAreaResult findBorderAndBuild = tryPutDown(templateId, x, y, uid, sid,false);
		if(findBorderAndBuild == null){
			return null;
		}
		return putDown(templateId, x, y, uid, sid,findBorderAndBuild);
	}
	
	/**
	 * 在这个点上 把建筑物放下 
	 * @param templateId
	 * @param x
	 * @param y
	 * @param uid
	 * @param sid
	 * @param findBorderAndBuild
	 * @return
	 */
	private BuildTransform putDown(int templateId,int x, int y,int uid,int sid,FindBorderAreaResult findBorderAndBuild) {
		BuildTransform buildTransform = player.roleInfo().getBaseCountry().getBuildTransform(player.roleInfo().getBaseCountry().getUseTemplateId(), uid);
		//如果没有创建一个
		if(buildTransform ==null) {
			buildTransform = new BuildTransform(uid, templateId);
			player.roleInfo().getBaseCountry().addBuildTransform(player.roleInfo().getBaseCountry().getUseTemplateId(), uid, buildTransform);
		}
		
		//清理老的坐标
		clear: {
			buildTransform.getNodePoints().clear();
			buildTransform.getBorderPoints().clear();
			buildTransform.getLastLocation().set(buildTransform.getLocation().clone());
			break clear;
		}
		
		//设置新坐标
		news: {
			buildTransform.setLocation(new Vector2(x, y));
			// 设置实际格子
			Iterator<SpaceNode> iterator2 = findBorderAndBuild.getBuildMap().values().iterator();
			while (iterator2.hasNext()) {
				SpaceNode spaceNode = (SpaceNode) iterator2.next();
				buildTransform.getNodePoints().put(spaceNode.getId(), spaceNode.getSpaceTransform().getLocation().clone());
			}
			// 设置描边格子
			Iterator<SpaceNode> iterator3 = findBorderAndBuild.getBorderMap().values().iterator();
			while (iterator3.hasNext()) {
				SpaceNode spaceNode = (SpaceNode) iterator3.next();
				buildTransform.getBorderPoints().put(spaceNode.getId(), spaceNode.getSpaceTransform().getLocation().clone());
			}
			break news;
		}
		return buildTransform;
	}


	/**
	 * 验证建筑物数量
	 * @param player
	 * @param uid
	 * @param country
	 * @param num
	 * @return
	 */
	private boolean verifyCountryNum(int templateId) {
		Integer len = GlobalPirFactory.getInstance().countryNums.get(player.getLevel());
		if(templateId<0||len==null||len<templateId){
			return false;
		}
		return true;
	}
	
	public void giveOutput(int sid,int uid){
		BuildControl BuildControl = buildControls.get(sid);
		if(BuildControl!=null){
			BuildControl.giveOutput(player, uid);
		}
	}	
	
	/**
	 * 获得这种类类型的建筑 返回map
	 * @param classes
	 * @return
	 */
	public Map<Integer, XBuild> getBuildOfTypeMap(Class<?> classes){
		if(classes==null)return null;
		Map<Integer, XBuild> map = new HashMap<>();
		Iterator<BuildControl> iterator = buildControls.values().iterator();
		while (iterator.hasNext()) {
			BuildControl BuildControl = iterator.next();
			if(classes.isAssignableFrom(BuildControl.getClass())){
				map.putAll(BuildControl.getBuildMap());
			}
		}
		return map;
	}
	
	/**
	 * 获得这种类类型的建筑 返回list
	 * @param classes
	 * @return
	 */
	public List<BuildInfo> getBuildOfTypeList(Class<?> classes){
		if(classes==null)return null;
		List<BuildInfo> list = new ArrayList<>();
		Iterator<BuildControl> iterator = buildControls.values().iterator();
		while (iterator.hasNext()) {
			BuildControl BuildControl = iterator.next();
			if(classes.isAssignableFrom(BuildControl.getClass())){
				Iterator<XBuild> iterator2 = BuildControl.getBuildMap().values().iterator();
				while (iterator2.hasNext()) {
					XBuild xBuild = (XBuild) iterator2.next();
					list.add(new BuildInfo(xBuild, getBuildTransform(xBuild.getUid())));
				}
			}
		}
		return list;
	}
	
	/**
	 * 获得全部 CountryBuild 的建筑 返回list
	 * @param classes
	 * @return
	 */
	public List<BuildInfo> getAllCountryBuildList(){
		List<BuildInfo> list = new ArrayList<>();
		Iterator<CountryBuild> iterator = player.roleInfo().getBaseCountry().getAllBuild().values().iterator();
		while (iterator.hasNext()) {
			CountryBuild countryBuild = (CountryBuild) iterator.next();
			list.add(new BuildInfo(countryBuild, getBuildTransform(countryBuild.getUid())));
		}
		return list;
	}
	
	public BuildTransform getBuildTransform(int uid){
		return player.roleInfo().getBaseCountry().getBuildTransform(player.roleInfo().getBaseCountry().getUseTemplateId(), uid);
	}
	
	public Vector2Bean getBuildLocation(int uid) {
		BuildTransform buildTransform = this.getBuildTransform(uid);
		if(buildTransform != null) {
			return buildTransform.getLocation().toVectorInfo();
		}
		return new Vector2Bean();
	}
	
	public void send() {
		ResAllCountryMessage resAllCountryMessage = new ResAllCountryMessage();
		resAllCountryMessage.useTemplateId = player.roleInfo().getBaseCountry().getUseTemplateId();
		Iterator<? extends XBuild> iterator = player.roleInfo().getBaseCountry().getAllBuild().values().iterator();
		while (iterator.hasNext()) {
			XBuild build = (CountryBuild) iterator.next();
			resAllCountryMessage.builds.add(build.toBuildBean(player));
		}
		iterator = player.roleInfo().getBaseCountry().getBlocks().values().iterator();
		while (iterator.hasNext()) {
			XBuild build = iterator.next();
			resAllCountryMessage.builds.add(build.toBuildBean(player));
		}
		Iterator<Entry<Integer, Map<Integer, BuildTransform>>> iterator2 = player.roleInfo().getBaseCountry().getBuildTransforms().entrySet().iterator();
		while (iterator2.hasNext()) {
			Entry<Integer, Map<Integer, BuildTransform>> next = iterator2.next();
			CountryBean countryBean = new CountryBean();
			countryBean.templateId = next.getKey();
			Iterator<BuildTransform> iterator3 = next.getValue().values().iterator();
			while (iterator3.hasNext()) {
				BuildTransform next2 = iterator3.next();
				countryBean.transformBeans.add(CountryMsgGener.toTransformBean(next2));
			}
			resAllCountryMessage.countrys.add(countryBean);
		}
		player.send(resAllCountryMessage);
		
		// 发送临时仓库信息 
		player.getCountryManager().mineBuildControl.dealMineCarResouce(player, true);
		MineRepository mineRepository = player.roleInfo().getBaseCountry().getMineRepository();
		ResMineRepositoryMessage resMineRepositoryMessage = new ResMineRepositoryMessage();
		resMineRepositoryMessage.money = Double.valueOf(mineRepository.getMoney()).longValue();
		resMineRepositoryMessage.oil = Double.valueOf(mineRepository.getOil()).longValue();
	    resMineRepositoryMessage.rare = Double.valueOf(mineRepository.getRare()).longValue();
	    resMineRepositoryMessage.steel = Double.valueOf(mineRepository.getSteel()).longValue();
	    player.send(resMineRepositoryMessage);
	}
	
	public BuildBean toBuildBean(XBuild build){
		BuildBean bean = new BuildBean();
		bean.uid = build.getUid();
		bean.sid = build.getSid();
		bean.state = build.getState();
		if (build.getSid() == BuildFactory.MINE_CAR.getTid()) {
			MineRepository mineRepository = player.roleInfo().getBaseCountry().getMineRepository();
			if(mineRepository != null) {
				MineCar mineCar = mineRepository.getCar(build.getUid());
				if(mineCar != null) {
					bean.ext = String.valueOf(mineCar.getResourceType());
				}
			}
		}
		return bean;
	}
	
	/**
	 *  获取最大驻军数量
	 * @return
	 */
	public int getMaxReinforceNum() {
		return PlayerAttributeManager.get().concentrateArmy(player.getId());
	}
	
	
	/**
	 * 环形空间节点工厂
	 * @author ye.yuan
	 *
	 */
	public static class CountryFactory extends SpaceFactory {
		public SpaceNode newNode(RingArea ringArea,int x,int y) {
			switch (ringArea.getIndex()) {
			case 0:
				CountryNode0 countryNode0 = new CountryNode0(x,y);
				countryNode0.see = "+";
				return countryNode0;
			case 1:
				CountryNode1 countryNode1 = new CountryNode1(x,y);
				countryNode1.see = "@";
				return countryNode1;
			case 2:
				CountryNode2 countryNode2 = new CountryNode2(x,y);
				countryNode2.see = "%";
				return countryNode2;
			case 3:
				CountryNode3 countryNode3 = new CountryNode3(x,y);
				countryNode3.see = "#";
				return countryNode3;
			default:
				return new QuadNode(x,y);
			}
		}
	}
	
	/********************************************强类型调用***********************************************************/
	
	/**
	 * 科技馆
	 */
	@BuildCreate
	private TechBuildControl techBuildControl;
	
	/**
	 * 主基地
	 */
	@BuildCreate
	private MainBuildControl mainBuildControl;
	
	/**
	 * 障碍物1700
	 */
	@BuildCreate
	private BlockBuild1700Control obstruct1700Control;
	
	/**
	 * 障碍物1701
	 */
	@BuildCreate
	private BlockBuild1701Control obstruct1701Control;
	
	
	/**
	 * 障碍物1702
	 */
	@BuildCreate
	private BlockBuild1702Control obstruct1702Control;
	
	/**
	 * 飞机场
	 */
	@BuildCreate
	private PlaneBuildControl planeBuildControl;
	
	/**
	 * 实验室
	 */
	@BuildCreate
	private LabBuildControl labBuildControl;
	
	/**
	 * 坦克营
	 */
	@BuildCreate
	private TankBuildControl tankBuildControl;
	
	/**
	 * 越野车
	 */
	@BuildCreate
	private SUVBuildControl suvBuildControl;
	
	/**
	 * 修理厂
	 */
	@BuildCreate
	private ModBuildControl modBuildControl;
	
	/**
	 * 贸易战
	 */
	@BuildCreate
	private TradeBuildControl tradeBuildControl;
	
	/**
	 * 雷达
	 */
	@BuildCreate
	private RadarBuildControl radarBuildControl;
	
	/**
	 * 采矿场
	 */
	@BuildCreate
	private MineBuildControl mineBuildControl;
	
	/**
	 * 采矿车
	 */
	@BuildCreate
	private MineCarControl mineCarControl;
	
	/**
	 * 工业工厂
	 */
	@BuildCreate
	private IndustryBuildControl industryBuildControl;
	
	/**
	 * 防守驻军
	 */
	@BuildCreate
	private DefebseSoldierControl defebseSoldierControl;
	
	/**
	 * 军营
	 */
	@BuildCreate
	private MilitaryCampControl militaryCampControl;
	
	
	/**
	 * 黄金
	 */
	@BuildCreate
	private MoneyResourceControl moneyResourceControl;
	
	/**
	 * 稀土
	 */
	@BuildCreate
	private RareResourceCountrol rareResourceCountrol;
	
	/**
	 * 石油
	 */
	@BuildCreate
	private OilResourceControl oilResourceControl;
	
	/**
	 * 钢材
	 */
	@BuildCreate
	private SteelResourceControl steelResourceControl;
	
	/**
	 * 歼击炮
	 */
	@BuildCreate
	private DefenseTower1301 defenseTower1301;
	
	/**
	 * 近防炮
	 */
	@BuildCreate
	private DefenseTower1302 defenseTower1302;
	
	/**
	 * 放空炮
	 */
	@BuildCreate
	private DefenseTower1303 defenseTower1303;
	
	/**
	 * 火焰炮
	 */
	@BuildCreate
	private DefenseTower1304 defenseTower1304;
	
	/**
	 * 电磁塔
	 */
	@BuildCreate
	private DefenseTower1305 defenseTower1305;
	
	/**
	 * 军营
	 */
	@BuildCreate
	private ArmyBuildControl armyBuildControl;
	
	/**
	 * 超时空电站
	 */
	@BuildCreate
	private SuperSpaceBuildControl superSpaceBuildControl;
	
	/**
	 * 领事馆
	 */
	@BuildCreate
	private  ConsulateBuildControl consulateBuildControl;
	
	/**
	 * 基因存储站
	 */
	@BuildCreate
	private  GeneStorageBuildControl geneStorageBuildControl;
	
	/**
	 * 生化反应堆
	 */
	@BuildCreate
	private  OrganismReactorControl organismReactorControl;
	
	/**
	 * 联盟商店
	 */
	@BuildCreate
	private  PartyShopBuildControl partyShopBuildControl;
	
	/**
	 * 监狱
	 */
	@BuildCreate
	private  PrisonBuildControl prisonBuildControl;
	
	/**
	 * 围墙
	 */
	@BuildCreate
	private  WallBuildControl wallBuildControl;
	
	/**
	 * 隐形炸弹
	 */
	@BuildCreate
	private  BombBuildControl bombBuildControl;

	
	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public void setProduct(Player product) {
		this.player=product;
	}

	@Override
	public void destroy() {
		player = null;
	}

	public TechBuildControl getTechBuildControl() {
		return techBuildControl;
	}


	public void setTechBuildControl(TechBuildControl techBuildControl) {
		this.techBuildControl = techBuildControl;
	}


	public MainBuildControl getMainBuildControl() {
		return mainBuildControl;
	}


	public void setMainBuildControl(MainBuildControl mainBuildControl) {
		this.mainBuildControl = mainBuildControl;
	}

	public PlaneBuildControl getPlaneBuildControl() {
		return planeBuildControl;
	}


	public void setPlaneBuildControl(PlaneBuildControl planeBuildControl) {
		this.planeBuildControl = planeBuildControl;
	}


	public LabBuildControl getLabBuildControl() {
		return labBuildControl;
	}

	public void setLabBuildControl(LabBuildControl labBuildControl) {
		this.labBuildControl = labBuildControl;
	}

	public TankBuildControl getTankBuildControl() {
		return tankBuildControl;
	}

	public void setTankBuildControl(TankBuildControl tankBuildControl) {
		this.tankBuildControl = tankBuildControl;
	}

	public ModBuildControl getModBuildControl() {
		return modBuildControl;
	}


	public void setModBuildControl(ModBuildControl modBuildControl) {
		this.modBuildControl = modBuildControl;
	}


	public TradeBuildControl getTradeBuildControl() {
		return tradeBuildControl;
	}

	public void setTradeBuildControl(TradeBuildControl tradeBuildControl) {
		this.tradeBuildControl = tradeBuildControl;
	}

	public RadarBuildControl getRadarBuildControl() {
		return radarBuildControl;
	}

	public void setRadarBuildControl(RadarBuildControl radarBuildControl) {
		this.radarBuildControl = radarBuildControl;
	}

	public MineBuildControl getMineBuildControl() {
		return mineBuildControl;
	}

	public void setMineBuildControl(MineBuildControl mineBuildControl) {
		this.mineBuildControl = mineBuildControl;
	}

	public MineCarControl getMineCarControl() {
		return mineCarControl;
	}

	public void setMineCarControl(MineCarControl mineCarControl) {
		this.mineCarControl = mineCarControl;
	}

	public SUVBuildControl getSuvBuildControl() {
		return suvBuildControl;
	}

	public void setSuvBuildControl(SUVBuildControl suvBuildControl) {
		this.suvBuildControl = suvBuildControl;
	}

	public BlockBuild1700Control getObstruct1700Control() {
		return obstruct1700Control;
	}

	public void setObstruct1700Control(BlockBuild1700Control obstruct1700Control) {
		this.obstruct1700Control = obstruct1700Control;
	}

	public BlockBuild1701Control getObstruct1701Control() {
		return obstruct1701Control;
	}

	public void setObstruct1701Control(BlockBuild1701Control obstruct1701Control) {
		this.obstruct1701Control = obstruct1701Control;
	}

	public BlockBuild1702Control getObstruct1702Control() {
		return obstruct1702Control;
	}

	public void setObstruct1702Control(BlockBuild1702Control obstruct1702Control) {
		this.obstruct1702Control = obstruct1702Control;
	}

	public IndustryBuildControl getIndustryBuildControl() {
		return industryBuildControl;
	}

	public void setIndustryBuildControl(IndustryBuildControl industryBuildControl) {
		this.industryBuildControl = industryBuildControl;
	}


	public DefebseSoldierControl getDefebseSoldierControl() {
		return defebseSoldierControl;
	}


	public void setDefebseSoldierControl(DefebseSoldierControl defebseSoldierControl) {
		this.defebseSoldierControl = defebseSoldierControl;
	}

	public MilitaryCampControl getMilitaryCampControl() {
		return militaryCampControl;
	}

	public void setMilitaryCampControl(MilitaryCampControl militaryCampControl) {
		this.militaryCampControl = militaryCampControl;
	}


	public Map<Integer, BuildControl> getBuildControls() {
		return buildControls;
	}


	public void setBuildControls(Map<Integer, BuildControl> buildControls) {
		this.buildControls = buildControls;
	}


	public MoneyResourceControl getMoneyResourceControl() {
		return moneyResourceControl;
	}


	public void setMoneyResourceControl(MoneyResourceControl moneyResourceControl) {
		this.moneyResourceControl = moneyResourceControl;
	}


	public RareResourceCountrol getRareResourceCountrol() {
		return rareResourceCountrol;
	}


	public void setRareResourceCountrol(RareResourceCountrol rareResourceCountrol) {
		this.rareResourceCountrol = rareResourceCountrol;
	}


	public OilResourceControl getOilResourceControl() {
		return oilResourceControl;
	}


	public void setOilResourceControl(OilResourceControl oilResourceControl) {
		this.oilResourceControl = oilResourceControl;
	}


	public SteelResourceControl getSteelResourceControl() {
		return steelResourceControl;
	}


	public void setSteelResourceControl(SteelResourceControl steelResourceControl) {
		this.steelResourceControl = steelResourceControl;
	}


	public DefenseTower1301 getDefenseTower1301() {
		return defenseTower1301;
	}


	public void setDefenseTower1301(DefenseTower1301 defenseTower1301) {
		this.defenseTower1301 = defenseTower1301;
	}


	public DefenseTower1302 getDefenseTower1302() {
		return defenseTower1302;
	}


	public void setDefenseTower1302(DefenseTower1302 defenseTower1302) {
		this.defenseTower1302 = defenseTower1302;
	}


	public DefenseTower1303 getDefenseTower1303() {
		return defenseTower1303;
	}


	public void setDefenseTower1303(DefenseTower1303 defenseTower1303) {
		this.defenseTower1303 = defenseTower1303;
	}


	public DefenseTower1304 getDefenseTower1304() {
		return defenseTower1304;
	}


	public void setDefenseTower1304(DefenseTower1304 defenseTower1304) {
		this.defenseTower1304 = defenseTower1304;
	}


	public DefenseTower1305 getDefenseTower1305() {
		return defenseTower1305;
	}


	public void setDefenseTower1305(DefenseTower1305 defenseTower1305) {
		this.defenseTower1305 = defenseTower1305;
	}


	public static RingSpace getSpace() {
		return space;
	}

	public ArmyBuildControl getArmyBuildControl() {
		return armyBuildControl;
	}

	public void setArmyBuildControl(ArmyBuildControl armyBuildControl) {
		this.armyBuildControl = armyBuildControl;
	}

	public SuperSpaceBuildControl getSuperSpaceBuildControl() {
		return superSpaceBuildControl;
	}

	public void setSuperSpaceBuildControl(
			SuperSpaceBuildControl superSpaceBuildControl) {
		this.superSpaceBuildControl = superSpaceBuildControl;
	}

	public ConsulateBuildControl getConsulateBuildControl() {
		return consulateBuildControl;
	}

	public void setConsulateBuildControl(ConsulateBuildControl consulateBuildControl) {
		this.consulateBuildControl = consulateBuildControl;
	}

	public GeneStorageBuildControl getGeneStorageBuildControl() {
		return geneStorageBuildControl;
	}

	public void setGeneStorageBuildControl(
			GeneStorageBuildControl geneStorageBuildControl) {
		this.geneStorageBuildControl = geneStorageBuildControl;
	}

	public OrganismReactorControl getOrganismReactorControl() {
		return organismReactorControl;
	}

	public void setOrganismReactorControl(
			OrganismReactorControl organismReactorControl) {
		this.organismReactorControl = organismReactorControl;
	}

	public PartyShopBuildControl getPartyShopBuildControl() {
		return partyShopBuildControl;
	}

	public void setPartyShopBuildControl(PartyShopBuildControl partyShopBuildControl) {
		this.partyShopBuildControl = partyShopBuildControl;
	}

	public PrisonBuildControl getPrisonBuildControl() {
		return prisonBuildControl;
	}

	public void setPrisonBuildControl(PrisonBuildControl prisonBuildControl) {
		this.prisonBuildControl = prisonBuildControl;
	}

	public WallBuildControl getWallBuildControl() {
		return wallBuildControl;
	}

	public void setWallBuildControl(WallBuildControl wallBuildControl) {
		this.wallBuildControl = wallBuildControl;
	}
	
	public BuildControl getBuildControl(int tid) {
		return this.buildControls.get(tid);
	}

	@Override
	public Map<AttributeNodeEnum,Double> attributeAdd(Player player,AttributesEnum attributeEnum,AttributeNodeEnum...attributeNodeEnums) {
		double value = 0;
		Map<AttributeNodeEnum,Double> valueOfNodeMap = new HashMap<>();
		for(CountryBuild build : player.roleInfo().getBaseCountry().getAllBuild().values()){
			BuildingPir buildingPir = BuildingPirFactory.get(build.getSid());
			if(buildingPir != null){
//				//attr
//				AttributeConfMap attributeConfMapOfAttr = buildingPir.getAttr();
				//v1 特殊，写死   建筑id=1104;1108;时才生效 AttributeConstant
				AttributeConfMap attributeConfMapOfV1 = null;
				if(AttributeUtil.isAttrOfBuildingV1(build.getSid())){
					attributeConfMapOfV1 = buildingPir.getV1();
				}
				//V2 特殊，写死   建筑id=1100 时才生效
				AttributeConfMap attributeConfMapOfV2 = null;
				if(AttributeUtil.isAttrOfBuildingV2(build.getSid())){
					attributeConfMapOfV2 = buildingPir.getV2();
				}
				for(AttributeNodeEnum node : AttributeNodeEnum.values()){
					if(attributeNodeEnums.length > 0 && Arrays.binarySearch(attributeNodeEnums,node) < 0){
						continue;
					}
					double nodeValue = 0;
//					nodeValue += AttributeUtil.attributeAddCounter(attributeConfMapOfAttr, build.getLevel(), node.getCode(), attributeEnum.getId());
					nodeValue += AttributeUtil.attributeAddCounter(attributeConfMapOfV1, build.getLevel(), node.getCode(), attributeEnum.getId());
					nodeValue += AttributeUtil.attributeAddCounter(attributeConfMapOfV2, build.getLevel(), node.getCode(), attributeEnum.getId());
					value += nodeValue;
					if(nodeValue > 0){
						if(!valueOfNodeMap.containsKey(node.getCode())){
							valueOfNodeMap.put(node, nodeValue);
						}else{
							valueOfNodeMap.put(node, valueOfNodeMap.get(node.getCode()) + nodeValue);
						}
					}
				}
			}
		}
		if(valueOfNodeMap.size() > 0){
			log.info(AttributeUtil.logBuilder(valueOfNodeMap, attributeEnum.getId(), value,BuildingPir.class.getSimpleName()));
		}
		return valueOfNodeMap;
	}
	
	@Override
	public Map<AttributeFromEnum, Double> selectAttributeAdd(Player player,
			AttributesEnum attributeEnum, AttributeNodeEnum node) {
		Map<AttributeNodeEnum,Double> nodeAttrMap = attributeAdd(player,attributeEnum, node);
		Map<AttributeFromEnum, Double> resultMap = new HashMap<>();
		if(nodeAttrMap.containsKey(node)){
			resultMap.put(AttributeFromEnum.BUILD, nodeAttrMap.get(node));
		}
		return resultMap;
	}
}
