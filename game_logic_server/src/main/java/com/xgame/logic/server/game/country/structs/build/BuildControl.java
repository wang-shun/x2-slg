package com.xgame.logic.server.game.country.structs.build;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import com.xgame.common.ItemConf;
import com.xgame.config.building.BuildingPir;
import com.xgame.config.building.BuildingPirFactory;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.language.view.success.SuccessTipEnum;
import com.xgame.logic.server.core.utils.CurrencyUtil;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.bag.ItemKit;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.country.entity.CountryBuild;
import com.xgame.logic.server.game.country.entity.XBuild;
import com.xgame.logic.server.game.country.entity.eventmodel.LevelUpBulidFinishEventObject;
import com.xgame.logic.server.game.country.entity.eventmodel.LevelupBuildEventObject;
import com.xgame.logic.server.game.country.message.ResLevelUpBuildMessage;
import com.xgame.logic.server.game.country.structs.status.TimeState;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.PlayerAttributeManager;
import com.xgame.logic.server.game.playerattribute.fightpower.FightPowerKit;
import com.xgame.logic.server.game.timertask.constant.TimerTaskCommand;
import com.xgame.logic.server.game.timertask.entity.job.CreatBuildTask;
import com.xgame.logic.server.game.timertask.entity.job.ITimerTask;
import com.xgame.logic.server.game.timertask.entity.job.TimerTaskHolder;
import com.xgame.logic.server.game.timertask.entity.job.data.BuildTimerTaskData;

/**
 *
 *2017-1-09  10:44:18
 *@author ye.yuan
 *
 */
@SuppressWarnings("unchecked")
public class BuildControl {
	
	/**
	 * 建筑物配置id
	 */
	protected int sid;
	
	/**
	 * 最大等级建筑
	 */
	protected XBuild maxLevelBuild;

	/**
	 * 以建筑物uid存储的map
	 */
	protected ConcurrentSkipListMap<Integer,XBuild> buildMap = new ConcurrentSkipListMap<>();
	
	/**
	 * 刚创建建筑控制器的时候  这时候还是一个空的控制器 什么都没有
	 * @param player
	 */
	public void initControl(Player player) {
		
	}
	
	/**
	 * 数据加载
	 * @param player
	 */
	public final void dataLoad(Player player) {
		mathLevelMaxBuild();
		dataLoadAfter(player);
	}
	
	/**
	 * 数据库数据加载完成之后
	 * @param player
	 */
	public void dataLoadAfter(Player player) {
		
	}

	/**
	 * 建造创建初始化
	 * @param player
	 * @param sid
	 * @param uid
	 * @param stateId
	 * @return
	 */
	public final <T extends XBuild> T createInit(Player player,int uid,int stateId) {
		XBuild build = newCountryBuild();
		build.setState((short)stateId);
		build.setSid(sid);
		build.setUid(uid);
		build.setIndex(buildMap.size());
		build.setLevel(1);
		build.setOutputTime(System.currentTimeMillis());
		addBuild(player,build);
		createInit0(player, build);
		createInitAfter(player,build);
		return (T)build;
	}
	
	/**
	 * 创建建筑后 还没添加到集合之前   子类自定义初始化
	 * @param player
	 * @param countryBuild
	 */
	protected void createInit0(Player player,XBuild build) {
		
	}
	
	/**
	 * 创建全部结束以后  再次回调子类实现
	 * @param player
	 * @param build
	 */
	public  void createInitAfter(Player player,XBuild build) {
		
	}
	
	/**
	 * 创建的时候多态获得建筑物对象
	 * @return
	 */
	protected XBuild newCountryBuild(){
		return new CountryBuild();
	}
	
	/**
	 * 添加建筑到建筑控制器下  
	 * @param build
	 */
	protected void addBuild(Player player,XBuild build) {
		buildMap.put(build.getUid(), build);
		mathLevelMaxBuild();
		addBuildAfter(player, build);
	}
	
	/**
	 * 添加以后
	 * @param player
	 * @param build
	 */
	protected void addBuildAfter(Player player,XBuild build){
		
	}
	
	public void remove(Player player,int buildUid){
		
	}
	
	/**
	 * 通用收取 收取士兵 收取资源
	 * @param player
	 * @param uid 建筑唯一id
	 */
	public void giveOutput(Player player,int uid){
		
	}
	
	/**
	 * 建筑物是否能移动
	 * @return
	 */
	public boolean isMove() {
		return true;
	}
	
	/**
	 * 同建筑找最大等级的建筑  不是按照自然顺序查找,根据hash表找的,就是说如果有并列等级建筑 每次hashcode变更找到的对象也会变更
	 * @return
	 */
	protected XBuild mathLevelMaxBuild(){
		XBuild tmp = null;
		Iterator<? extends XBuild> iterator = buildMap.values().iterator();
		while (iterator.hasNext()) {
			XBuild next = iterator.next();
			if(tmp==null){
				tmp = next;
			}
			else if(next.getLevel()>tmp.getLevel()){
				tmp = next;
			}
		}
		maxLevelBuild = tmp;
		return tmp;
	}
	
	/**
	 * 建筑升级
	 * @param player
	 * @param uid
	 * @param useType
	 */
	public void buildLevelUp(Player player,int uid,int useType){
		ITimerTask<?> timerTask = TimerTaskHolder.getTimerTask(TimerTaskCommand.BUILD);
		if(useType == CurrencyUtil.USE&&timerTask.checkQueueCapacityMax(player)){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E100_COUNTRY.CODE7.get());
			return;
		}
		//如果有队列在执行  走
		XBuild build = getBuild(uid);
		BuildingPir buildingPir = BuildingPirFactory.get(sid);
		
		//等级是否限制 经验 对线程模型  服务器结构    常用开源框架有了解    和 运用 但 不深入
		if(build.getLevel()>=buildingPir.getMax_lv()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E100_COUNTRY.CODE8.get());
			return;
		}
		//前置建筑
		if(!player.getCountryManager().verifyBeforeBuild(player, buildingPir.getRequire_id(), build.getLevel())){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E100_COUNTRY.CODE9.get());
			return;
		}
		
		int nextLevel = build.getLevel() + 1;
		Map<Integer, Integer> cost_oil = buildingPir.getCost_oil();
		Map<Integer, Integer> cost_cash = buildingPir.getCost_cash();
		Map<Integer, Integer> cost_earth = buildingPir.getCost_earth();
		Map<Integer, Integer> cost_steel = buildingPir.getCost_steel();
		Map<Integer, Integer> cost_time = buildingPir.getCost_time();
		
		Integer oil = cost_oil.get(nextLevel);
		Integer money = cost_cash.get(nextLevel);
		Integer steels = cost_steel.get(nextLevel);
		Integer rare = cost_earth.get(nextLevel);
		Integer time = cost_time.get(nextLevel);
//		time = (int)AttributeEnum.BUILDING_TIME.playerMath(player, (int)time);
		time = PlayerAttributeManager.get().buildingCreateTime(player.getId(), time);
		// 验证扣减道具
		Map<Integer, ItemConf> itemConfs = buildingPir.getCost_item();
		ItemConf itemConf = itemConfs.get(nextLevel);
		if(itemConf != null) {
			if(!ItemKit.checkRemoveItemByTid(player, itemConf.getTid(), itemConf.getNum())) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E100_COUNTRY.CODE11.get(),itemConf.getTid()+"",itemConf.getNum()+"");
				return;
			}
		}
		
		// 扣钱失败 
		if (!CurrencyUtil.decrementCurrency(player, money, steels, oil, rare, useType, time, GameLogSource.BUILD_LEVEL_UP)) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E100_COUNTRY.CODE10.get());
			return;
		}
		
		// 扣减道具
		if(itemConf != null) {
			ItemKit.removeItemByTid(player, itemConf.getTid(), itemConf.getNum(), GameLogSource.BUILD_LEVEL_UP);
		}
		long fightPower = FightPowerKit.FINAL_POWER.getValue(player.getId()).longValue();
		EventBus.getSelf().fireEvent(new LevelupBuildEventObject(player, EventTypeConst.EVENT_BUILD_LEVELUP_START, sid, time, new Vector2Bean(), fightPower));
		if(useType == CurrencyUtil.USE) {
			//改变状态为升级中
			build.setState(TimeState.LEVEL_UP.ordinal());
			//添加定时事件
			timerTask.register(player,CreatBuildTask.LEVEL_UP_BUILD_CMD,cost_time.get(nextLevel), new BuildTimerTaskData(sid, uid, build.getState(), build.getLevel(), null));
			Language.SUCCESSTIP.send(player, SuccessTipEnum.E100_COUNTRY.CODE3.get());
		} else if (useType == CurrencyUtil.FAST_USE) {
			incrementLevel(player, uid,1);
		}
	}
	
	
	/**
	 * 创建完成
	 * @param player
	 * @param uid 建筑物唯一id
	 */
	public void createEnd(Player player,int uid){
		
	}
	
	/**
	 * 取消时间队列
	 * @param player
	 * @param uid
	 */
	public void cancelTimerTask(Player player,int uid){
		BuildingPir buildingPir = BuildingPirFactory.get(sid);
		XBuild build = getBuild(uid);
		int level = build.getLevel()+1;
		Map<Integer, Integer> cost_oil = buildingPir.getCost_oil();
		Map<Integer, Integer> cost_cash = buildingPir.getCost_cash();
		Map<Integer, Integer> cost_earth = buildingPir.getCost_earth();
		Map<Integer, Integer> cost_steel = buildingPir.getCost_steel();
		
		Integer oil = cost_oil.get(level);
		Integer money = cost_cash.get(level);
		Integer steels = cost_steel.get(level);
		Integer rare = cost_earth.get(level);
		
		double oil1 = oil*0.5d;
		double money1 = money*0.5f;
		double steels1 = steels*0.5f;
		double rare1 = rare*0.5f;
		
		CurrencyUtil.increaseCurrency(player, money1, steels1, oil1, rare1, GameLogSource.CANCEL_BUILD_LEVEL_UP);
		CurrencyUtil.send(player);
		InjectorUtil.getInjector().dbCacheService.update(player);
	}
	
	/**
	 * 重置等级
	 * @param player
	 * @param uid
	 * @param modulus
	 */
	public void resetLevel(Player player,int uid,int modulus){
		XBuild build = getBuild(uid);
		build.setState(TimeState.USEING.ordinal());
		build.setLevel(modulus);
		//得出最大等级的建筑
		mathLevelMaxBuild();
		//更新客户端建筑等级
		sendBuildLevel(player, build);
		//等级升级后的自定义处理
		baseLevelUpHandle(player, build);
	}
	
	/**
	 * 定时事件升级触发
	 * @param player
	 * @param uid
	 * @param modulus 需要+多少级
	 */
	public void incrementLevel(Player player,int uid,int modulus){
		XBuild build = getBuild(uid);
		build.setState(TimeState.USEING.ordinal());
		build.setLevel(build.getLevel() + modulus);
		//得出最大等级的建筑
		mathLevelMaxBuild();
		//更新客户端建筑等级
		sendBuildLevel(player, build);
		//等级升级后的自定义处理
		baseLevelUpHandle(player, build);
		//回调子类升级之后的处理
		levelUpAfter(player, build.getUid());
		InjectorUtil.getInjector().dbCacheService.update(player);
		int addCombat;
		if(build.getLevel() == 1){
			addCombat = BuildingPirFactory.getInstance().getFightPower(uid, build.getLevel());
		}else{
			addCombat = BuildingPirFactory.getInstance().getFightPower(uid, build.getLevel()) - BuildingPirFactory.getInstance().getFightPower(uid, build.getLevel() - 1);
		}
		long fightPower = FightPowerKit.FINAL_POWER.getValue(player.getId()).longValue();
		LevelUpBulidFinishEventObject bulidLevelUpEventObject = new LevelUpBulidFinishEventObject(player , EventTypeConst.EVENT_BUILD_LEVELUP_FINISH, build.getSid(), build.getLevel() - 1, fightPower,addCombat);
		EventBus.getSelf().fireEvent(bulidLevelUpEventObject);
		
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E100_COUNTRY.CODE2.get());
	}
	
	/**
	 * 升级后 的一些基础信息处理  比如建筑是障碍物  那么 该方法不重写  如果是 家园建筑  会被重写 因为要更新属性发奖 等等
	 * @param player
	 * @param build
	 */
	public void baseLevelUpHandle(Player player,XBuild build){
		
	}
	
	/**
	 * 升级前置处理全部结束后 再次回调  和创建的时候一样步骤
	 * @param player
	 * @param uid 升级的建筑物
	 */
	public void levelUpAfter(Player player,int uid){
		
	}
	
	
	/**
	 * 发送等级到客户端
	 * @param player
	 * @param build
	 */
	public void sendBuildLevel(Player player,XBuild build){
		ResLevelUpBuildMessage resLevelUpBuildMessage = new ResLevelUpBuildMessage();
		resLevelUpBuildMessage.uid = build.getUid();
		resLevelUpBuildMessage.level=build.getLevel();
		player.send(resLevelUpBuildMessage);
	}
	
	
	
	/**
	 * 发送消息
	 * @param player
	 */
	public void send(Player player) {
		
	}

	/**
	 * 获得一条默认的建筑  集合第一个  一般只有一个建筑的时候 会用到
	 * @return
	 */
	public <T extends XBuild> T getDefianlBuild() {
		return (T)buildMap.firstEntry().getValue();
	}
	
	/**
	 * 获得指定建筑
	 * @param uid
	 * @return
	 */
	public <T extends XBuild> T getBuild(int uid) {
		return (T)buildMap.get(uid);
	}

	public ConcurrentSkipListMap<Integer, XBuild> getBuildMap() {
		return buildMap;
	}

	public void setBuildMap(ConcurrentSkipListMap<Integer, XBuild> buildMap) {
		this.buildMap = buildMap;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public XBuild getMaxLevelBuild() {
		return maxLevelBuild;
	}

	public void setMaxLevelBuild(XBuild maxLevelBuild) {
		this.maxLevelBuild = maxLevelBuild;
	}
}
