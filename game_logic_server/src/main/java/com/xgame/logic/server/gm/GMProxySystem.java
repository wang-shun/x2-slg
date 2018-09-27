package com.xgame.logic.server.gm;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import com.xgame.config.ConfigSystem;
import com.xgame.config.attribute.AttributeConfMap;
import com.xgame.config.attribute.AttributeParser;
import com.xgame.config.copy.CopyPir;
import com.xgame.config.copy.CopyPirFactory;
import com.xgame.config.equipment.EquipmentPir;
import com.xgame.config.equipment.EquipmentPirFactory;
import com.xgame.config.items.ItemsPir;
import com.xgame.config.items.ItemsPirFactory;
import com.xgame.config.peiJian.PeiJianPir;
import com.xgame.config.peiJian.PeiJianPirFactory;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.utils.CurrencyUtil;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.MathUtil;
import com.xgame.logic.server.game.bag.ItemKit;
import com.xgame.logic.server.game.bag.converter.ItemConverter;
import com.xgame.logic.server.game.bag.entity.Item;
import com.xgame.logic.server.game.bag.entity.ItemContext;
import com.xgame.logic.server.game.constant.CurrencyEnum;
import com.xgame.logic.server.game.constant.SystemEnum;
import com.xgame.logic.server.game.copy.constant.CopySequence;
import com.xgame.logic.server.game.copy.enity.MainCopy;
import com.xgame.logic.server.game.copy.enity.MainCopyPoint;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.country.entity.XBuild;
import com.xgame.logic.server.game.country.structs.build.BuildControl;
import com.xgame.logic.server.game.email.MailKit;
import com.xgame.logic.server.game.email.constant.EmailTemplet;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.AttributeDescrpitionManager;
import com.xgame.logic.server.game.war.entity.WarSoldierFactory;
import com.xgame.logic.server.game.world.entity.WorldMarch;



/**
 *gm代理
 *采用动态代理方式实现gm管理  让权限操作变得更好设计  
 *目前是在玩家线程执行的,后面根据合并的功能来决定线程是否独立
 *把代理实现和方法缓存等初始化操作 放在了类的最下部 这样是为了维护人员在写gm方法的时候 能够更一目了然的看到方法  专注于方法实现    不被其他地方打扰思维
 *为了方便输入gm指令  方法名一律首字母缩写    但要在方法注释里面写明全称
 *暂时使用injectUtil 管理 
 *2016-8-23  12:14:00
 *@author ye.yuan
 *
 */
@Slf4j
public final class GMProxySystem implements IGMProxySystem{
	
	/**
	 * 让自己变得nb
	 * @param player
	 */
	public void nb(Player player){
//		agi(player,-1);
//		aic(player, Integer.MAX_VALUE);
//		InjectorUtil.getInjector().dbCacheService.update(player);
		
		
		List<WorldMarch> worldMarchList = player.getWorldMarchManager().getWorldMarchByPlayerId(player.getId());
		if(worldMarchList != null) {
			for(WorldMarch worldMarch : worldMarchList) {
				worldMarch.executor.failReturn();
			}
		}
	}
	
	public void sst(Player player, String date) {
//		InjectorUtil.getInjector().shopManager.setServerStartTime(date);
		
	}
	
	/**
	 * 
	 * @param file
	 */
	@Override
	public void refresh(Player player, String file) {
		// 执行命令行脚本
		try {
			// 更新svn
			Process process = Runtime.getRuntime().exec(
					"svn co svn://slg_test_server.ftxgame.local/repos/SLG/config/program/" + InjectorUtil.getInjector().path);
			int result = process.waitFor();
			if(result !=0) {
				log.error("更新svn出错,错误码[{}]", result);
				return;
			}
			
			reloadConfig(player,file);
		} catch (Exception e) {
			log.error("执行svn命令报错....");
		}
	}
	
	/**
	 * 重新载入配置文件
	 * @param file
	 */
	public void reloadConfig(Player player,String file) {
		if(!StringUtils.isEmpty(file)) {
			File refile = new File(InjectorUtil.getInjector().configPath + File.separator + file + ConfigSystem.suffix);
			InjectorUtil.getInjector().configSystem.parse(refile);
		} else {
			InjectorUtil.getInjector().configSystem.start();
		}
		log.error("========刷新配置成功========文件名:[{}]",file);
	}
	
	@Override
	public void saf(Player player, int aid, int val) {
//		ActiveItem item = player.getTaskManager().getMyActiveInfo().activeItems.get(aid);
//		if(item != null) {
//			int number = activeConfigModel.factory.get(aid).number;
//			if(val > number) {
//				val = number;
//			}
//			item.finiCount = val;
//			player.getTaskManager().checkActiveFinishAll();
//		}
	}
	
	public void getSoilder(Player player, int itemId, int num) {
		
	}
	
	/**
	 * 添加全部物品
	 * AllGiveItem
	 * @param player
	 */
	public void agi(Player player,int num) {
		Iterator<ItemsPir> iterator2 = ItemsPirFactory.getInstance().getFactory().values().iterator();
		while (iterator2.hasNext()) {
			ItemsPir item = (ItemsPir) iterator2.next();
			if(num<0){
				ItemKit.addItem(player, item.getId(), item.getMax()-1, SystemEnum.COMMON, GameLogSource.GM);
			}else{
				ItemKit.addItem(player, item.getId(), num, SystemEnum.COMMON, GameLogSource.GM);
			}
		}
		InjectorUtil.getInjector().dbCacheService.update(player);
		player.send(ItemConverter.getMsgPlayerBag(player));
	}
	
	/**
	 * allIncreaseCurrency
	 * @param player
	 * @param x
	 */
	public void aic(Player player,int x) {
		CurrencyUtil.increase(player, x, CurrencyEnum.DIAMOND, GameLogSource.GM);
		CurrencyUtil.increase(player, x, CurrencyEnum.GLOD, GameLogSource.GM);
		CurrencyUtil.increase(player, x, CurrencyEnum.OIL, GameLogSource.GM);
		CurrencyUtil.increase(player, x, CurrencyEnum.RARE, GameLogSource.GM);
		CurrencyUtil.increase(player, x, CurrencyEnum.STEEL, GameLogSource.GM);
		InjectorUtil.getInjector().dbCacheService.update(player);
		CurrencyUtil.send(player);
	}
	
	@Override
	public void speak(Player player) {
		
	}
	
	@Override
	public void ad(Player player,int x) {
		CurrencyUtil.increase(player, x, CurrencyEnum.DIAMOND, GameLogSource.GM);
		InjectorUtil.getInjector().dbCacheService.update(player);
		CurrencyUtil.send(player);
	}
	
	@Override
	public void ao(Player player, int x) {
		CurrencyUtil.increase(player, x, CurrencyEnum.OIL, GameLogSource.GM);
		InjectorUtil.getInjector().dbCacheService.update(player);
		CurrencyUtil.send(player);
	}

	@Override
	public void as(Player player, int x) {
		CurrencyUtil.increase(player, x,CurrencyEnum.STEEL, GameLogSource.GM);
		InjectorUtil.getInjector().dbCacheService.update(player);
		CurrencyUtil.send(player);
	}

	@Override
	public void ar(Player player, int x) {
		CurrencyUtil.increase(player, x, CurrencyEnum.RARE, GameLogSource.GM);
		InjectorUtil.getInjector().dbCacheService.update(player);
		CurrencyUtil.send(player);
	}

	@Override
	public void am(Player player, int x) {
		CurrencyUtil.increase(player, x, CurrencyEnum.GLOD, GameLogSource.GM);
		InjectorUtil.getInjector().dbCacheService.update(player);
		CurrencyUtil.send(player);
	}
	
	/**
	 * addItem
	 * @param player
	 * @param sid
	 * @param num
	 */
	public void ai(Player player,int tId,int num){
		if(tId != 0 && num > 0) {
			ItemsPir itemConfigModel = ItemsPirFactory.get(tId);
			if(itemConfigModel != null) {
				ItemKit.addItemAndTopic(player, tId, num, SystemEnum.COMMON, GameLogSource.GM);
			}
			InjectorUtil.getInjector().dbCacheService.update(player);
		}
	}
	
	@Override
	public void task(Player player) {
//		TimerTaskHolder.getTimerTask(TimerTaskCommand.RUN_SOLDIER).register(player, 5000);
	}
	
	public void aj(Player player) {
		InjectorUtil.getInjector().scheduleSystem.fastAddJob(10, player, "jiba",InjectorUtil.getInjector().processor.getExecutor(player.getRoleId()));
	}
	
	
	public void vip(Player player,int exp) {
//		CurrencyUtil.VIP_EXP.increase(player, exp, LogFactory.ADD_VIP_EXP);
//		InjectorUtil.getInjector().dbCacheService.update(player);
	}
	
	
	/*************************************以上是gm方法实现**********************************************/
	
	
	/**
	 * 不能强类型调用gm方法的 统统走这里  用方法名调用
	 */
	public void invoke(Player player,String param){
		log.error("username:"+player.roleInfo().getBasics().getUserName()+",roleId:"+player.getId()+"(执行gm命令"+param+")");
		if(param==null)return;
		try {
			String[] split = param.split(" ");
			Method method = methodMap.get(split[0]);
			if(method!=null){
				Parameter[] parameters = method.getParameters();
				Object [] objArr = new Object[parameters.length];
				objArr[0] = player;
				for(int i=1;i<split.length;i++){
					Object dataType = MathUtil.getDataType(parameters[i].getType(), split[i]);
					if(dataType!=null){
						objArr[i] = dataType;
					}else {
						objArr[i] = split[i];
					}
				}
				method.invoke(this, objArr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.error("username:"+player.roleInfo().getBasics().getUserName()+",roleId:"+player.getId()+"(执行gm命令"+param+")");
	}
	
	/**
	 * 缓存方法
	 */
	private final Map<String, Method> methodMap = new HashMap<>();
	
	private GMProxySystem() {
		Method[] methods = getClass().getMethods();
		for(int i=0;i<methods.length;i++){
			methodMap.put(methods[i].getName(), methods[i]);
		}
	}
	
	/**
	 * 通过动态代理创建该类
	 * @return
	 */
	public static IGMProxySystem create(){
		return (IGMProxySystem)Proxy.newProxyInstance(
				GMProxySystem.class.getClassLoader(), 
				GMProxySystem.class.getInterfaces(), 
				new ProxyInvocationHandler(new GMProxySystem()));
	}

	@Override
	public void alog(Player player, int eid, int val) {
		
	}

	@Override
	public void tm(Player player,short type,String subject, String content, String adjunctJson) {
//		InjectorUtil.getInjector().mailSystem.sendMail(new TotalMail(player.getRoleId(), type, subject, content, adjunctJson));
//		player.testMail();
	}

	public void ace(Player player, long exp) {
		player.getCommanderManager().commanderUpLevel(exp);
	}

	@Override
	public void afi(Player player, int tId, int num, int fromId) {
		ItemKit.addItem(player, tId, num, SystemEnum.values()[fromId], GameLogSource.GM);
	}

	@Override
	public void ae1_5(Player player,int minLevel,int maxLevel) {
		Iterator<EquipmentPir> iterator = EquipmentPirFactory.getInstance().getFactory().values().iterator();
		while (iterator.hasNext()) {
			EquipmentPir configModel =  iterator.next();
			if(configModel.getType()>=1&&configModel.getType()<=5&&configModel.getLevel()>=minLevel&&configModel.getLevel()<=maxLevel){
				player.getEquipmentManager().addEquipment(configModel.getId(), GameLogSource.GM);
			}
		}
	}

	@Override
	public void anp(Player player, int nodeId) {
//		AttributeNodeEnum.values()[nodeId].print(player);
	}
	
	@Override
	public void sbm(Player player,int sampleId){
//		InjectorUtil.getInjector().mailSystem.sendMail(new BillboardWriteMail(player.getRoleId(), (short)MailEnum.BILLBOARD.ordinal(), 1, "鸡巴"));
	}
	
	public void blu(Player player,int level){
		Iterator<BuildControl> iterator = player.getCountryManager().getBuildControls().values().iterator();
		while (iterator.hasNext()) {
			BuildControl countryBuildControl = iterator.next();
			Iterator<XBuild> iterator2 = countryBuildControl.getBuildMap().values().iterator();
			while (iterator2.hasNext()) {
				XBuild xBuild = (XBuild) iterator2.next();
				countryBuildControl.resetLevel(player, xBuild.getUid(),level);
			}
		}
	}


	@Override
	public void weapon(Player player) {
		Iterator<PeiJianPir> iterator = PeiJianPirFactory.getInstance().getFactory().values().iterator();
		while(iterator.hasNext()){
			PeiJianPir next = iterator.next();
			player.getCustomWeaponManager().unlockPeijian(player, next.getId(), GameLogSource.GM);
		}
	}

	@Override
	public void ic(Player player, int type, int x) {
		CurrencyUtil.increase(player, x, CurrencyEnum.values()[type], GameLogSource.GM);
		CurrencyUtil.send(player);
	}

	@Override
	public void dc(Player player, int type, int x) {
		CurrencyUtil.decrement(player, x, CurrencyEnum.values()[type], GameLogSource.GM);
		CurrencyUtil.send(player);
	}

	@Override
	public void ui(Player player, int tId,int num) {
		ItemsPir itemConfigModel = ItemsPirFactory.get(tId);
		if(itemConfigModel != null) {
			ItemContext context = ItemKit.addItem(player, tId, num, SystemEnum.COMMON, GameLogSource.GM);
			Item item = context.getFinalItemList().get(0);
			player.getItemManager().useItem(item.getId(), item.getItemId(), 1, false);
//			Object cash = AttributeEnum.CASH_PRODUCE_RATE.playerMath(player, 50);
//			Object earth = AttributeEnum.EARTH_PRODUCE_RATE.playerMath(player, 50);
//			Object steel = AttributeEnum.STEEL_PRODUCE_RATE.playerMath(player, 50);
//			Object oil = AttributeEnum.OIL_PRODUCE_RATE.playerMath(player, 50);
//			System.out.println(cash+"...."+earth+"...."+steel+"......UI......."+oil);
		}
		
	}
	
	@Override
	public void clear(Player player) {
		player.roleInfo().getPlayerBag().getItemTable().clear();
		
		player.getCommanderManager().getCommanderData().getEquits().clear();
		player.roleInfo().getPlayerBag().getEquipmentMap().clear();
		player.roleInfo().getCurrency().setMoney(0);
		player.roleInfo().getCurrency().setRare(0);
		player.roleInfo().getCurrency().setOil(0);
		player.roleInfo().getCurrency().setSteel(0);
		player.roleInfo().getCurrency().setVitality(0);
		player.roleInfo().getCurrency().setDiamond(0);
		InjectorUtil.getInjector().dbCacheService.update(player);
		
		// 刷新玩家所有数据
		player.getLoginManager().clientLoginOver(player);
	}
	
	@Override
	public void camp(Player player) {
//		List<WorldSimpleSoldierBean> soldierBriefBeans = new ArrayList<WorldSimpleSoldierBean>();
//		WorldSimpleSoldierBean worldSimpleSoldierBean = new WorldSimpleSoldierBean();
//		SoldierBriefPro soldierBriefPro = new SoldierBriefPro();
//		
//		soldierBriefPro.soldierId = 120210;
//		soldierBriefPro.num = 100;
//		worldSimpleSoldierBean.index = "1";
//		
//		Vector2Bean bean = new Vector2Bean();
//		bean.x = 1;
//		bean.y =1;
//		worldSimpleSoldierBean.position = bean;
//		worldSimpleSoldierBean.soldiers = soldierBriefPro;
//		soldierBriefBeans.add(worldSimpleSoldierBean);
//		player.getWorldLogicManager().reqOnDestination(player, soldierBriefBeans, 40, 193, MarchType.TERRITORY.ordinal());
//		player.getReleationShipManager().searchPlayer(player, "fight");
		
//		player.getReleationShipManager().addFriend(player, 100010000000002L);
//		player.getReleationShipManager().deleleFriend(player, 100010000000002L);
//		InjectorUtil.getInjector().allianceManager.createAlliance(player, "1111", "abc", "asdfasdfas", "http://www.baidu.com", "en");

//		List<WorldSimpleSoldierBean> simpleSoldiers = new ArrayList<WorldSimpleSoldierBean>();
//		WorldSimpleSoldierBean soldierBean1 = new WorldSimpleSoldierBean();
//		soldierBean1.index = 1;
//		soldierBean1.position = new Vector2Bean();
//		soldierBean1.soldiers = new SoldierBriefPro();
//		soldierBean1.soldiers.soldierId = 120210;
//		soldierBean1.soldiers.num = 100;
//		simpleSoldiers.add(soldierBean1);
//		
//		if(simpleSoldiers != null && !simpleSoldiers.isEmpty()) {
//			for(WorldSimpleSoldierBean warSimpleSoldierBean : simpleSoldiers) {
//				Soldier soldier = player.getSoldierManager().getSoldier(warSimpleSoldierBean.soldiers.soldierId);
//				if(soldier != null) {
//					int num = warSimpleSoldierBean.soldiers.num;
//					if(soldier.getNum() < num) {
//						Language.ERRORCODE.send(player, ErrorCodeEnum.E500_WAR.CODE1, soldier.getNum());
//						return;
//					}
//					
//					SoldierBean soldierBean = SoldierConverter.converterSoldier(soldier, num);
//					soldierBean.num = warSimpleSoldierBean.soldiers.num;
//					player.getSoldierManager().dataChange(SoldierEnum.NUM, SoldierEnum.FOREIGN, soldier.getSoldierId(),num); 
//				}
//			}
//		}
//		
//		// 扣除资源
//		Player defendPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, 10010000000002L);
//		defendPlayer.roleInfo().getBasics().setAttackTimer(System.currentTimeMillis());
//		CurrencyUtil.decrementCurrency(defendPlayer, 0, 0, 100, 0, LogFactory.MATERIAL_DEL);
//		InjectorUtil.getInjector().dbCacheService.update(player);
//		
//		// 推送被打消息
//		ResCityStateMessage resUserInfoMessage = new ResCityStateMessage();
//		resUserInfoMessage.beAttackTime = (int)(player.roleInfo().getBasics().getAttackTimer() / 1000);
//		player.send(resUserInfoMessage);
//
//		// 初始化
//		Battle battle = InjectorUtil.getInjector().battleManager.initPVP(player, defendPlayer, simpleSoldiers, 100);
//	
//		ResWarDataMessage resWarDataMessage = new ResWarDataMessage();
//		resWarDataMessage.attackData = battle.getWarAttacker().getWarAttackData();
//		resWarDataMessage.defenData = battle.getWarDefender().getWarDefenData();
//		resWarDataMessage.battleId = battle.getBattleId();
//		resWarDataMessage.battleType = BattleType.WORLD_CITY.ordinal();
		
//		Vector2Bean bean = new Vector2Bean();
//		bean.x = 1;
//		bean.y = 1;
//		WarBuildFactory.initWarPVEBuilding(1100, 1, bean);

		AttributeConfMap map = new AttributeConfMap(0);
		AttributeParser.parse("106,4:20000", map);
		List<Integer> peijianList = new ArrayList<>();
		peijianList.add(1010001);
		peijianList.add(1010002);
		peijianList.add(1010003);
		WarSoldierFactory.initPVEWarSoldier(0, peijianList, map, new Vector2Bean());
	}

	@Override
	public void ft(Player player,int type, int taskId) {
		player.getTaskManager().gmFinishedTask(player, type, taskId);
	}

	@Override
	public void attr(Player player) {
		AttributeDescrpitionManager attributeManager = player.getAttributeDescrpitionManager();
		String json = attributeManager.attr(player);
		MailKit.sendSystemEmail(player.getId(), EmailTemplet.战斗异常_MAIL_ID, json);
	}
	
	/**
	 * 属性实体
	 * @author jacky.jiang
	 *
	 */
	class AttrDescription {
		public int attrId;
		public long uid;
		public String name;
		public double value;
	}

	@Override
	public void copy(Player player,int maxCopyId,int maxPointIndex,int star0) {
		if(star0 > 3){
			star0 = 3;
		}
		player.roleInfo().getMainCopyInfo().getCopyMap().clear();
		List<CopyPir> list = new ArrayList<>(CopyPirFactory.getInstance().getFactory().values());
		Collections.sort(list, CopySequence.COPYPIR_SORT);
		for(CopyPir copyPir : list){
			if(copyPir.getId() > maxCopyId){
				break;
			}
			MainCopy mainCopy = player.getCopyManager().initCopy(copyPir.getId());
			int star = 0;
			List<MainCopyPoint> pointList = new ArrayList<>(mainCopy.getPointMap().values());
			Collections.sort(pointList, CopySequence.MAINCOPYPOINT_SORT);
			int index = 0;
			for(MainCopyPoint point : pointList){
				index++;
				if(copyPir.getId() == maxCopyId){
					if(index > maxPointIndex){
						break;
					}
					point.setOpen(true);
					point.setPassNum(1);
					point.setStar(star0);
					star += star0;
				}else{
					point.setOpen(true);
					point.setPassNum(1);
					point.setStar(star0);
					star += star0;
				}
			}
			mainCopy.setStar(star);
			player.roleInfo().getMainCopyInfo().getCopyMap().put(mainCopy.getCopyId(), mainCopy);
		}
		InjectorUtil.getInjector().dbCacheService.update(player);
	}
	
	public void resetCopy(Player player){
		player.roleInfo().getMainCopyInfo().getCopyMap().clear();
		player.getCopyManager().initCopy(player);
		InjectorUtil.getInjector().dbCacheService.update(player);
	}
}
