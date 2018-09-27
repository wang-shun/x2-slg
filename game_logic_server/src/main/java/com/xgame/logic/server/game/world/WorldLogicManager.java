package com.xgame.logic.server.game.world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.xgame.config.fastPaid.FastPaidPir;
import com.xgame.config.fastPaid.FastPaidPirFactory;
import com.xgame.config.global.GlobalPir;
import com.xgame.config.global.GlobalPirFactory;
import com.xgame.config.items.ItemsPir;
import com.xgame.config.items.ItemsPirFactory;
import com.xgame.data.sprite.SpriteType;
import com.xgame.framework.network.server.CommandProcessor;
import com.xgame.gameconst.GlobalConstant;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.language.view.success.SuccessTipEnum;
import com.xgame.logic.server.core.utils.CurrencyUtil;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.core.utils.collection.ConcurrentHashSet;
import com.xgame.logic.server.core.utils.sequance.MarchSequance;
import com.xgame.logic.server.game.alliance.AllianceBattleTeamManager;
import com.xgame.logic.server.game.alliance.PlayerAllianceManager;
import com.xgame.logic.server.game.alliance.constant.AllianceTeamState;
import com.xgame.logic.server.game.alliance.enity.Alliance;
import com.xgame.logic.server.game.alliance.enity.AllianceBattleTeam;
import com.xgame.logic.server.game.alliance.enity.PlayerAlliance;
import com.xgame.logic.server.game.bag.ItemKit;
import com.xgame.logic.server.game.bag.constant.ItemIdConstant;
import com.xgame.logic.server.game.constant.CurrencyEnum;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.cross.CrossManager;
import com.xgame.logic.server.game.cross.entity.CrossWorldInfo;
import com.xgame.logic.server.game.email.bean.EmailSignature;
import com.xgame.logic.server.game.email.converter.EmailConverter;
import com.xgame.logic.server.game.notice.NoticeManager;
import com.xgame.logic.server.game.notice.constant.NoticeConstant;
import com.xgame.logic.server.game.player.PlayerManager;
import com.xgame.logic.server.game.player.constant.GameSessionKey;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.player.entity.eventmodel.ScoutEventObject;
import com.xgame.logic.server.game.playerattribute.PlayerAttributeManager;
import com.xgame.logic.server.game.playerattribute.calculation.AttributeCounter;
import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;
import com.xgame.logic.server.game.timertask.TimerTaskManager;
import com.xgame.logic.server.game.timertask.entity.job.data.CollectTimerTaskData;
import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;
import com.xgame.logic.server.game.war.bean.WarResourceBean;
import com.xgame.logic.server.game.war.bean.WarSoldierBean;
import com.xgame.logic.server.game.war.entity.Battle;
import com.xgame.logic.server.game.war.entity.queue.WarFinishQueue;
import com.xgame.logic.server.game.world.bean.SpriteBean;
import com.xgame.logic.server.game.world.bean.VectorInfo;
import com.xgame.logic.server.game.world.bean.WorldSimpleSoldierBean;
import com.xgame.logic.server.game.world.bean.WorldTerritoryBean;
import com.xgame.logic.server.game.world.constant.MarchState;
import com.xgame.logic.server.game.world.constant.MarchType;
import com.xgame.logic.server.game.world.constant.WorldConstant;
import com.xgame.logic.server.game.world.converter.WorldConverter;
import com.xgame.logic.server.game.world.entity.SpriteInfo;
import com.xgame.logic.server.game.world.entity.WorldMarch;
import com.xgame.logic.server.game.world.entity.march.IMarchExecutor;
import com.xgame.logic.server.game.world.entity.march.WorldMarchFactory;
import com.xgame.logic.server.game.world.entity.march.WorldMarchParam;
import com.xgame.logic.server.game.world.entity.march.concrete.AllianceBattleTeamMarch;
import com.xgame.logic.server.game.world.entity.march.concrete.ExplorerWorldMarch;
import com.xgame.logic.server.game.world.entity.march.concrete.TerritoryWorldMarch;
import com.xgame.logic.server.game.world.entity.sprite.PlayerSprite;
import com.xgame.logic.server.game.world.entity.sprite.ResourceSprite;
import com.xgame.logic.server.game.world.entity.sprite.WorldSprite;
import com.xgame.logic.server.game.world.entity.territory.AllianceTerritory;
import com.xgame.logic.server.game.world.message.ResDefensePlayerDeailMessage;
import com.xgame.logic.server.game.world.message.ResGetTeamBattleListMessage;
import com.xgame.logic.server.game.world.message.ResMarchMessage;
import com.xgame.logic.server.game.world.message.ResMoveCityMessage;
import com.xgame.logic.server.game.world.message.ResMyCityDefenseMessage;
import com.xgame.logic.server.game.world.message.ResRefusePlayerDefenseMessage;
import com.xgame.logic.server.game.world.message.ResTroopBackMessage;
import com.xgame.logic.server.game.world.message.ResUpdateSpriteMessage;
import com.xgame.logic.server.game.world.message.ResUseTroopSpeedUpPropMessage;
import com.xgame.logic.server.game.world.message.ResUserTroopListMessage;
import com.xgame.logic.server.game.world.message.ResViewBattleTeamDetailMessage;
import com.xgame.logic.server.game.world.message.ResViewMarchMessage;
import com.xgame.logic.server.game.world.message.ResViewWorldTerritoryMessage;
import com.xgame.utils.EnumUtils;
import com.xgame.utils.TimeUtils;

/**
 * 世界地图逻辑管理
 * @author jacky.jiang
 *
 */
@Slf4j
@Component
public class WorldLogicManager {
	
	@Autowired
	private WorldMarchManager worldMarchManager;
	@Autowired
	private MarchSequance marchSequance;
	@Autowired
	private SpriteManager spriteManager;
	@Autowired
	private CommandProcessor commandProcessor;
	@Autowired
	private AllianceBattleTeamManager allianceBattleTeamManager;
	@Autowired
	private CrossManager crossManager;
	@Autowired
	private PlayerAllianceManager playerAllianceManager;
	@Autowired
	private NoticeManager noticeManager;
	@Autowired
	private PlayerManager playerManager;
	@Autowired
	private TimerTaskManager timerTaskManager;
	
	/**
	 * 当前进入世界地图的玩家id列表
	 */
	private Set<Long> playerIds = new ConcurrentHashSet<>();
	
	/**
	 * 进入世界地图
	 * @param player
	 * @param enter
	 */
	public void enterWorld(Player player, boolean enter) {
		if(enter) {
			playerIds.add(player.getId());	
		} else {
			playerIds.remove(player.getId());
		}
	}
	
	/**
	 * 注册世界服
	 * @param uid
	 */
	public boolean registerWorld(Player player) {
		SpriteInfo spriteInfo = spriteManager.getPlayerBorn(player.getRoleId());
		if(spriteInfo == null) {
			return false;
		}
		
		//创建精灵
		PlayerSprite playerSprite = new PlayerSprite();
		playerSprite.setOwnerId(String.valueOf(player.getId()));
		playerSprite.setPlayerLevel(player.getLevel());
		GlobalPir globalPir = GlobalPirFactory.get(GlobalConstant.SHEILD_TIME);
		if(globalPir != null) {
			playerSprite.setShieldTime(0); 
		}
		
		spriteInfo = spriteManager.createSpriteInfo(spriteInfo.getIndex(), SpriteType.PLAYER, playerSprite);
		spriteManager.transferGridToSprite(spriteInfo.getX(), spriteInfo.getY(), spriteInfo, true, true);
		
		player.roleInfo().getBasics().setSpriteId(spriteInfo.getId());
		InjectorUtil.getInjector().dbCacheService.update(player);
		return true;
	}
	
	/**
	 * 获取x,y范围内的精灵
	 * @param x
	 * @param y
	 * @return
	 */
	public CrossWorldInfo getRangeSpriteInfo(int x, int y) {
		CrossWorldInfo crossWorldInfo = new CrossWorldInfo();
		List<SpriteBean> spriteBeans = new ArrayList<SpriteBean>();
		List<WorldTerritoryBean> territoryBeans = new ArrayList<WorldTerritoryBean>();
		List<SpriteInfo> spriteInfos = spriteManager.getScreeeSpriteInfo(x, y);
		if(spriteInfos != null) {
			for(SpriteInfo spriteInfo : spriteInfos) {
				if(spriteInfo.getSpriteType() != SpriteType.NONE.getType()) {
					spriteBeans.add(WorldConverter.converterSprite(spriteInfo, false));
				}
				
				// 只有地图生成的不可走点才需要返回给客户端
				if(spriteInfo.getSpriteType() == SpriteType.BLOCK.getType()) {
					if(spriteManager.checkMapBlock(spriteInfo.getIndex())) {
						spriteBeans.add(WorldConverter.converterSprite(spriteInfo, false));
					}
				}
				
				// 精灵领地信息
				WorldSprite worldSprite = spriteInfo.getParam();
				if(worldSprite != null && worldSprite.getAllianceId() > 0) {
					territoryBeans.add(WorldConverter.converterWoldTerritoryBean(spriteInfo));
				}
			}
			
			crossWorldInfo.setSpriteBeans(spriteBeans);
		}
		
		crossWorldInfo.setTerritoryBeanList(territoryBeans);
		return crossWorldInfo;
	}
	
	/**
	 * 出征
	 * @param player
	 * @param soldierBriefBeans
	 * @param x
	 * @param y
	 * @param type
	 * @param teamId
	 * @param remainTime 根据参数取具体的集结进攻的时间
	 * @param warResourceBean
	 */
	public void reqOnDestination(Player player, List<WorldSimpleSoldierBean> soldierBriefBeans, int x,int y, int type, String teamId, int remainTime, WarResourceBean warResourceBean) {
		MarchType marchType = EnumUtils.getEnum(MarchType.class, type);
		if(MarchType.SCOUT == marchType){
			EventBus.getSelf().fireEvent(new ScoutEventObject(player));
		}
		int targetId = y * SpriteManager.xGridNum + x;
		
		WorldMarchParam worldMarchParam = new WorldMarchParam();
		worldMarchParam.setPlayer(player);
		worldMarchParam.setTargetPoint(targetId);
		worldMarchParam.setMarchType(marchType);
		worldMarchParam.setSoldierBriefBeans(soldierBriefBeans);
		worldMarchParam.setTeam(teamId);
		worldMarchParam.setRemainTime(WorldConstant.ASSEMBLE_WAIT_TIME[remainTime]);
		worldMarchParam.setTradeResource(warResourceBean);
		
		SpriteInfo spriteInfo = player.getSpriteManager().getVisibleGrid(targetId);
		if (spriteInfo != null) {
			WorldSprite worldSprite = (WorldSprite)spriteInfo.getParam();
			if(!StringUtils.isEmpty(worldSprite.getOwnerId())) {
				Player defPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class,Long.parseLong(worldSprite.getOwnerId()));
				if (defPlayer != null) {
					EmailSignature defSignature = EmailConverter.emailSignatureBuilder(defPlayer,spriteInfo.getX(),spriteInfo.getY());
					worldMarchParam.setDefSignature(defSignature);
				}
			} else if(worldSprite.getOwnerMarchId() > 0) {
				WorldMarch worldMarch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, worldSprite.getOwnerMarchId());
				if(worldMarch != null) {
					Player defPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class,Long.parseLong(worldMarch.getOwnerUid()));
					if (defPlayer != null) {
						EmailSignature defSignature = EmailConverter.emailSignatureBuilder(defPlayer,spriteInfo.getX(),spriteInfo.getY());
						worldMarchParam.setDefSignature(defSignature);
					}
				}
			}
			worldMarchParam.setSpriteType(spriteInfo.getSpriteType());
		}
		
		IMarchExecutor worldMarch = WorldMarchFactory.createWorldMarch(worldMarchParam);
		this.getWorldExecutor().execute(new Runnable() {
			@Override
			public void run() {
				WorldMarch march = worldMarch.march();
				if(march == null) {
					return;
				}

				VectorInfo vectorInfo = WorldConverter.converterVectorInfo(march, true);
				ResMarchMessage resOnDestinationMessage = new ResMarchMessage();
				resOnDestinationMessage.vectorInfo = vectorInfo;
				player.send(resOnDestinationMessage);
				
				if(type == MarchType.SCOUT.ordinal()) {
					Language.SUCCESSTIP.send(player, SuccessTipEnum.E121_WORLD.CODE3);
				} else if(type == MarchType.EXPLORER.ordinal()) {
					Language.SUCCESSTIP.send(player, SuccessTipEnum.E121_WORLD.CODE5);
				} else if(type == MarchType.CITY_FIGHT.ordinal()) {
					Language.SUCCESSTIP.send(player, SuccessTipEnum.E121_WORLD.CODE2);
				} else {
					Language.SUCCESSTIP.send(player, SuccessTipEnum.E121_WORLD.CODE6);
				}
			}
		});
	}
	
	/**
	 * 定点迁城
	 * @param player
	 * @param x
	 * @param y
	 */
	public void moveFixCity(Player player, int x, int y,int moveCityType) {
		//随机传送走单独逻辑
		if(moveCityType == 3){
			this.moveRandomCity(player);
		}else{
			if(this.beforeMoveCity(player, x, y)){
				SpriteInfo targetSprite = spriteManager.getGrid(x, y);
				//六级前(包括六级)使用新手迁城道具
				int type = 0;
				FastPaidPir paidPir = null;
				if(moveCityType == 0) {
					paidPir = FastPaidPirFactory.get(ItemIdConstant.NEW_PLAYER_TRANSFER);
					if(player.getLevel() > 6) {
						Language.ERRORCODE.send(player, ErrorCodeEnum.E121_WORLD.CODE31.get());
						return;
					}
					
					if (ItemKit.checkRemoveItemByTid(player, ItemIdConstant.NEW_PLAYER_TRANSFER, 1)) {
						type  = WorldConstant.MOVE_CITY_DEDUCT_TYPE_ITEM;
					} else {
						Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE12.get());
						return;
					}
					
					this.moveCity(player, x, y, type, paidPir);
				}else if(moveCityType == 1) {
					//定点传送道具
					paidPir = FastPaidPirFactory.get(ItemIdConstant.FIX_POSITION_TRANSFER);
					if (ItemKit.checkRemoveItemByTid(player, ItemIdConstant.FIX_POSITION_TRANSFER, 1)) {
						type  = WorldConstant.MOVE_CITY_DEDUCT_TYPE_ITEM;
					} else if (null == paidPir){
						Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE6.get(),FastPaidPir.class.getSimpleName(),ItemIdConstant.FIX_POSITION_TRANSFER);
						return;
					} else if (CurrencyUtil.verify(player, paidPir.getPrice() , CurrencyEnum.DIAMOND)) {
						type = WorldConstant.MOVE_CITY_DEDUCT_TYPE_DIAMOND;
					} else {
						Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE16.get());
						return;
					}
					this.moveCity(player, x, y, type, paidPir);
				} else if (moveCityType == 2) {
					//判断是否为军团领土
					boolean flag = false;
					if(player.getAllianceId() > 0) {
						Alliance alliance = InjectorUtil.getInjector().dbCacheService.get(Alliance.class, player.getAllianceId());
						AllianceTerritory allianceTerritory = alliance.getAllianceTerritory();
						if(allianceTerritory != null && allianceTerritory.getTerritory() != null && allianceTerritory.getTerritory().contains(targetSprite.getIndex())) {
							flag = true;
						}
					}
					
					if(!flag) {
						Language.ERRORCODE.send(player, ErrorCodeEnum.E121_WORLD.CODE32.get());
						return;
					}
					
					//军团传送道具
					paidPir = FastPaidPirFactory.get(ItemIdConstant.ALLIANCE_TRANSFER);
					if (ItemKit.checkRemoveItemByTid(player, ItemIdConstant.ALLIANCE_TRANSFER, 1)) {
						type  = WorldConstant.MOVE_CITY_DEDUCT_TYPE_ITEM;
					} else if (null == paidPir){
						Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE6.get(),FastPaidPir.class.getSimpleName(),ItemIdConstant.ALLIANCE_TRANSFER);
						return;
					} else if (CurrencyUtil.verify(player, paidPir.getPrice() , CurrencyEnum.DIAMOND)) {
						type = WorldConstant.MOVE_CITY_DEDUCT_TYPE_DIAMOND;
					} else {
						Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE16.get());
						return;
					}
					
					this.moveCity(player, x, y, type, paidPir);
				}
			}
		}
	}
	
	/**
	 * 随机迁城
	 * @param player
	 */
	public void moveRandomCity(Player player){
		Vector2Bean v2b = spriteManager.getRandomPosition();
		if(this.beforeMoveCity(player, v2b.x, v2b.y)) {
			int type = 0;
			FastPaidPir paidPir = FastPaidPirFactory.get(ItemIdConstant.RANDOM_TRANSFER);
			if (ItemKit.checkRemoveItemByTid(player, ItemIdConstant.RANDOM_TRANSFER, 1)) {
				type  = WorldConstant.MOVE_CITY_DEDUCT_TYPE_ITEM;
			} else if (null == paidPir){
				Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE6.get(),FastPaidPir.class.getSimpleName(),ItemIdConstant.RANDOM_TRANSFER);
				return;
			} else if (CurrencyUtil.verify(player, paidPir.getPrice() , CurrencyEnum.DIAMOND)) {
				type = WorldConstant.MOVE_CITY_DEDUCT_TYPE_DIAMOND;
			} else {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE16.get());
				return;
			}
			this.moveCity(player, v2b.x, v2b.y, type, paidPir);
		}
	}
	
	/**
	 * 
	 * @param player
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean beforeMoveCity(Player player, int x, int y){
		
		SpriteInfo targetSprite = spriteManager.getCanUseGrid(x, y);
		if(targetSprite == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E121_WORLD.CODE10);
			return false;
		}
		
		WorldSprite targWorldSprite = targetSprite.getParam();
		if(targWorldSprite == null ) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E121_WORLD.CODE28);
			return false;
		}
		
		if(!targetSprite.canUse()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E121_WORLD.CODE29);
			return false;
		}
		
		//　判断是否同盟领地
		long allianceId = player.getAllianceId();
		if(allianceId > 0) {
			if(targWorldSprite.getAllianceId() > 0 && targWorldSprite.getAllianceId() != allianceId) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E121_WORLD.CODE29);
				return false;
			}
		}
		
		SpriteInfo sourceSprite = player.getSprite();
		if(sourceSprite == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E121_WORLD.CODE10);
			return false;
		}
		
		// 有出征的部队
		List<WorldMarch> worldMarchs = worldMarchManager.getWorldMarchByPlayerId(player.getId());
		if(worldMarchs != null) {
			for(WorldMarch worldMarch : worldMarchs) {
				if(worldMarch.getMarchState() == MarchState.BACK) {
					Language.ERRORCODE.send(player, ErrorCodeEnum.E121_WORLD.CODE16);
					return false;
				}
				
				if(worldMarch.getTargetId() == targetSprite.getIndex()) {
					Language.ERRORCODE.send(player, ErrorCodeEnum.E121_WORLD.CODE42);
					return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * 迁移城市
	 * @param player
	 * @param x
	 * @param y
	 */
	public void moveCity(Player player, int x, int y, int type, FastPaidPir paidPir) {
		SpriteInfo sourceSprite = player.getSprite();
		if(sourceSprite == null || sourceSprite.getParam() == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E121_WORLD.CODE5);
			return;
		}
		
		if(type == 0 || paidPir == null) {
			return;
		}
		
		// 清空之前格子信息
		this.getWorldExecutor().execute(new Runnable() {
			@Override
			public void run() {
				// 处理当前有玩家攻打我。
				WorldSprite worldSprite = sourceSprite.getParam();
				if(worldSprite == null) {
					return;
				}
				
				List<WorldMarch> destionMarchs = worldMarchManager.getDestinationWorldMarch(sourceSprite.getIndex());
				if(destionMarchs != null && !destionMarchs.isEmpty()) {
					worldSprite.setMoveCity(true);
					InjectorUtil.getInjector().dbCacheService.update(sourceSprite);
					for(WorldMarch worldMarch : destionMarchs) {
						if(worldMarch.getTaskId() > 0 && Long.valueOf(worldMarch.getOwnerUid()) != player.getId() && worldMarch.getMarchState() == MarchState.MARCH) {
							CountDownLatch countDownLatch = new CountDownLatch(1);
							worldSprite.setCountDownLatch(countDownLatch);
							Player marchPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, Long.valueOf(worldMarch.getOwnerUid()));
							if(marchPlayer != null) {
								player.getTimerTaskManager().removeTimerTask(marchPlayer, worldMarch.getTaskId());	
							}
							
							// 到达目的地
							worldMarch.getExecutor().toDestination();
							
							try {
								countDownLatch.await(20, TimeUnit.MILLISECONDS);
							} catch(Exception e) {
								log.error("迁城军队异常", e);
							}
						}
					}
				}
				
				if(!beforeMoveCity(player, x, y)) {
					return;
				}
				
				WorldSprite newWorldSprite = new WorldSprite();
			
				// 精灵信息
				int beforeIndex = player.getLocation().getIndex();
				int index =  y * SpriteManager.xGridNum +x;
				SpriteInfo spriteInfo = SpriteInfo.valueOf(InjectorUtil.getInjector().idSequance.genId(), index, SpriteType.NONE, newWorldSprite);
				newWorldSprite.setAllianceId(worldSprite.getLastAllianceId());
				
				// 把之前的格子上的东西去掉
				spriteManager.transferGridToSprite(sourceSprite.getX(), sourceSprite.getY(), spriteInfo, false, true);
				
				// 在当前格子上放置玩家
				SpriteInfo targetInfo = InjectorUtil.getInjector().spriteManager.getVisibleGrid(x,y);
				WorldSprite targetWorldSprite = targetInfo.getParam();
				worldSprite.setLastAllianceId(targetWorldSprite.getAllianceId());
				
				spriteManager.transferGridToSprite(x, y, sourceSprite, true, true);
				InjectorUtil.getInjector().dbCacheService.update(targetInfo);
				
				// 处理当前城市的驻军跟随迁城
				dealAllianceCityDefenseByMoveCity(player, beforeIndex, index);
				
				// 扣除玩家道具
				player.async(new Runnable() {
					@Override
					public void run() {
						if(type == WorldConstant.MOVE_CITY_DEDUCT_TYPE_ITEM) {
							ItemKit.removeItemByTid(player, paidPir.getId(), 1, GameLogSource.MOVE_CITY);
						} else if(type == WorldConstant.MOVE_CITY_DEDUCT_TYPE_DIAMOND) {
							CurrencyUtil.decrement(player, paidPir.getPrice() , CurrencyEnum.DIAMOND, GameLogSource.MOVE_CITY);
							CurrencyUtil.send(player);
						}
					}
				});
				
				// 推送野外信息
				pushToWorldSprites(spriteInfo, Lists.newArrayList(player.getId()));
				pushToWorldSprites(sourceSprite, Lists.newArrayList(player.getId()));
				
				// 返回迁城
				ResMoveCityMessage resMoveCityMessage = new ResMoveCityMessage();
				resMoveCityMessage.viewCenter = new Vector2Bean();
				resMoveCityMessage.viewCenter.x = x;
				resMoveCityMessage.viewCenter.y = y;
				player.send(resMoveCityMessage);
				
				// 提送领地变化
				player.getWorldLogicManager().pushWorldTerritory(player, spriteInfo);
			}
		});
	}
	
	/**
	 * 获取部队信息
	 * @param marchId
	 * @return
	 */
	public List<WarSoldierBean> getWarSoldierBean(long marchId) {
		WorldMarch worldMarch = worldMarchManager.get(marchId);
		if(worldMarch != null) {
			return WorldConverter.converterWarSoldier(worldMarch);
		}
		return null;
	}
 	
	/**
	 * 获取行军队列信息
	 * @param player
	 * @param x
	 * @param y
	 * @return
	 */
	public List<VectorInfo> getWorldMarch(int x, int y) {
		return worldMarchManager.getVectorInfo(x, y);
	}
	
	/**
	 * 玩家地图信息
	 * @param x
	 * @param y
	 */
	public SpriteBean getSpriteInfo(int x, int y) {
		SpriteInfo spriteInfo = spriteManager.getGrid(x, y);
		log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%{}", JsonUtil.toJSON(spriteInfo));
		if(spriteInfo == null || spriteInfo.getEnumSpriteType() == SpriteType.BLOCK) {
			return null;
		}

		SpriteBean spriteBean = WorldConverter.converterSprite(spriteInfo, true);
		return spriteBean;
	}
	
	/**
	 * 获取玩家出征部队信息
	 * @param player
	 * @return
	 */
	public List<VectorInfo> getPlayerMarch(Player player) {
		List<VectorInfo> vectorInfos = new ArrayList<VectorInfo>();
		List<WorldMarch> worldMarchs = worldMarchManager.getWorldMarchByPlayerId(player.getRoleId());
		if(worldMarchs != null && !worldMarchs.isEmpty()) {
			for(WorldMarch worldMarch : worldMarchs) {
				vectorInfos.add(WorldConverter.converterVectorInfo(worldMarch, true));
			}
		}
		
		//
		log.info("###################################{}", JsonUtil.toJSON(worldMarchs));
		return vectorInfos;
	}
	
	/**
	 * 行军队列返回
	 * @param player
	 * @param marchId
	 */
	public void marchBack(Player player, long marchId, int itemId) {

		WorldMarch worldMarch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, marchId);
		if(worldMarch == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E121_WORLD.CODE6);
			return;
		}
		
		if(Long.valueOf(worldMarch.getOwnerUid()) != player.getId().longValue()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E121_WORLD.CODE26);
			return;
		}
		
		if(worldMarch.getMarchState() == MarchState.BACK) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E121_WORLD.CODE27);
			return;
		}
		
		ItemsPir config = ItemsPirFactory.get(ItemIdConstant.MARCH_BACK_ITEM);
		if(config == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE6.get(), ItemsPir.class.getSimpleName(), String.valueOf(ItemIdConstant.MARCH_BACK_ITEM));
			return;
		}
		
		if(worldMarch.getMarchState() == MarchState.MARCH) {
			FastPaidPir paidPir = FastPaidPirFactory.get(ItemIdConstant.MARCH_BACK_ITEM);
			if (ItemKit.checkRemoveItemByTid(player, ItemIdConstant.MARCH_BACK_ITEM, 1)) {
				ItemKit.removeItemByTid(player, ItemIdConstant.MARCH_BACK_ITEM, 1, GameLogSource.MARCH);
			} else if (null == paidPir) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE6.get(), FastPaidPir.class.getSimpleName(), ItemIdConstant.MARCH_BACK_ITEM);
				return;
			} else if (CurrencyUtil.verify(player, paidPir.getPrice() , CurrencyEnum.DIAMOND)) {
				CurrencyUtil.decrement(player, paidPir.getPrice() , CurrencyEnum.DIAMOND, GameLogSource.MARCH);
				CurrencyUtil.send(player);
			} else {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE16.get());
				return;
			}
		}
		
		// 战斗中不能撤军
		if(worldMarch.getBattleEndTime() > TimeUtils.getCurrentTimeMillis()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E121_WORLD.CODE27);
			return;
		}
		
		// 返回
		getWorldExecutor().execute(new Runnable() {
			@Override
			public void run() {
				if(worldMarch.getMarchState() == MarchState.MARCH) {
					worldMarch.executor.failReturn();
				} else {
					if(worldMarch.getMarchState() == MarchState.OCCUPY) {
						if(worldMarch.getExlporerTaskId() > 0) {
							player.getTimerTaskManager().removeTimerTask(player, worldMarch.getExlporerTaskId());
						}
						
						if(worldMarch.getOccupyTaskId() > 0) {
							player.getTimerTaskManager().removeTimerTask(player, worldMarch.getOccupyTaskId());
						}
					}
					
					if(worldMarch.getMarchType() == MarchType.EXPLORER) {
						worldMarch.getExecutor().failReturn();
					} else {
						worldMarch.getExecutor().handleReturn();	
					}
				}
				
				ResTroopBackMessage resTroopBackMessage = new ResTroopBackMessage();
				resTroopBackMessage.vectorInfo = WorldConverter.converterVectorInfo(worldMarch, true);
				player.send(resTroopBackMessage);
			}
		});
	}
	
	/**
	 * 查看部队信息
	 * @param player
	 * @param x
	 * @param y
	 */
	public void viewMarch(Player player, int x, int y) {
		  // 返回出征信息
        ResViewMarchMessage resViewMarchMessage = new ResViewMarchMessage();
        List<VectorInfo> vectorInfos = this.getWorldMarch(x, y);
        resViewMarchMessage.vectorInfoList = vectorInfos;
        player.send(resViewMarchMessage);
	}
	
	/**
	 * 行军加速
	 * @param player
	 * @param itemId
	 */
	public void speedUp(Player player, int itemId, long marchId) {
		
		if(player.getPlayerSession().getStringAttr(GameSessionKey.SPEEDUP) != null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE14);
			return;
		}
		
		if (itemId <= 0 || marchId <= 0) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE15);
			return;
		}
		
		WorldMarch worldMarch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, marchId);
		if(worldMarch == null || (worldMarch.getMarchArrivalTime() - TimeUtils.getCurrentTimeMillis()) <= 10 * 1000) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE25);
			return;
		}
		
		if(worldMarch.getMarchState() != MarchState.MARCH && worldMarch.getMarchState() != MarchState.BACK) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E121_WORLD.CODE25);
			return;
		}
		
		ItemsPir itemsPir = ItemsPirFactory.get(itemId);
		if(itemsPir == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE6,ItemsPir.class.getSimpleName(),itemId);
			return;
		}
		
		FastPaidPir paidPir = FastPaidPirFactory.get(itemId);
		if (ItemKit.checkRemoveItemByTid(player, itemId, 1)) {
			ItemKit.removeItemByTid(player, itemId, 1, GameLogSource.MARCH);
		} else if (null == paidPir) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE6.get(), FastPaidPir.class.getSimpleName(), itemId);
			return;
		} else if (CurrencyUtil.verify(player, paidPir.getPrice() , CurrencyEnum.DIAMOND)) {
			CurrencyUtil.decrement(player, paidPir.getPrice() , CurrencyEnum.DIAMOND, GameLogSource.MARCH);
			CurrencyUtil.send(player);
		} else {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE16.get());
			return;
		}
		
		player.getPlayerSession().addAttribute(GameSessionKey.SPEEDUP, GameSessionKey.SPEEDUP);
		
		// 加速
		getWorldExecutor().execute(new Runnable() {
			@Override
			public void run() {
				worldMarch.executor.speedUp(Double.valueOf(itemsPir.getV2().toString()));

				ResUseTroopSpeedUpPropMessage resUseTroopSpeedUpPropMessage = new ResUseTroopSpeedUpPropMessage();
				resUseTroopSpeedUpPropMessage.vectorInfo = WorldConverter.converterVectorInfo(worldMarch, true);
				player.send(resUseTroopSpeedUpPropMessage);
				
				player.getPlayerSession().removeAttribute(GameSessionKey.SPEEDUP);
			}
		});
	}
	
	/**
	 * 获取联盟战队信息
	 * @param player
	 * @param allianceId
	 */
	public void getTeamBattleList(Player player, long allianceId, int type) {
		
		Alliance alliance = InjectorUtil.getInjector().dbCacheService.get(Alliance.class, allianceId);
		if(alliance == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(playerAlliance.getAllianceId() != alliance.getId()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		if(type == WorldConstant.QUERY_BATTLE_TEAM_ATTACK) {
			ResGetTeamBattleListMessage resGetTeamBattleListMessage = new ResGetTeamBattleListMessage();
			resGetTeamBattleListMessage.type = type;
			List<AllianceBattleTeam> allianceBattleTeams = allianceBattleTeamManager.getAllianceBattleTeamList(allianceId);
			if(allianceBattleTeams != null && !allianceBattleTeams.isEmpty()) {
				for(AllianceBattleTeam allianceBattleTeam : allianceBattleTeams) {
					if(allianceBattleTeam.getAllianceTeamState() == AllianceTeamState.WAIT_MARCH) {
						Player leaderPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, allianceBattleTeam.getLeaderId());
						if(StringUtils.isBlank(allianceBattleTeam.getTargetUid())) {
							continue;
						}
						
						Player defendPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, Long.valueOf(allianceBattleTeam.getTargetUid()));
						resGetTeamBattleListMessage.attackTeamList.add(WorldConverter.converterTeamAttackBean(allianceBattleTeam, leaderPlayer, defendPlayer, null));
					}
				}
			}
			player.send(resGetTeamBattleListMessage);
		} else {
			ResGetTeamBattleListMessage resGetTeamBattleListMessage = new ResGetTeamBattleListMessage();
			resGetTeamBattleListMessage.type = type;
			Set<Long> playerIds = alliance.getAllianceMember();
			if(playerIds != null && !playerIds.isEmpty()) {
				for(Long id : playerIds) {
					Player destionPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, id);
					if(destionPlayer != null) {
						List<WorldMarch> worldMarchs = worldMarchManager.getDestinationWorldMarch(destionPlayer.getWorldPoint());
						if(worldMarchs != null && !worldMarchs.isEmpty()) {
							for(WorldMarch worldMarch : worldMarchs) {
								if((worldMarch.getMarchType() == MarchType.CITY_FIGHT || worldMarch.getMarchType() == MarchType.TEAM_ATTACK) && worldMarch.getMarchState() == MarchState.MARCH) {
									Player attackPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, Long.valueOf(worldMarch.getOwnerUid()));
									if(attackPlayer != null)  {
										resGetTeamBattleListMessage.attackTeamList.add(WorldConverter.converterTeamAttackBean(null, attackPlayer, destionPlayer, worldMarch));
									}
								}
							}
						}	
					}
				}
			}
			player.send(resGetTeamBattleListMessage);
		}
	}
	
	/**
	 * 查看战队详情
	 * @param player
	 * @param battleTeamId
	 * @param type 1代表集结进攻 2 集结防御
	 */
	public void viewBattleTeamListDetail(Player player, String battleTeamId, int type) {
		if(type == 1) {
			ResViewBattleTeamDetailMessage resViewBattleTeamDetailMessage = new ResViewBattleTeamDetailMessage();
			resViewBattleTeamDetailMessage.type = type;
			resViewBattleTeamDetailMessage.teamId =battleTeamId;
			AllianceBattleTeam allianceBattleTeam = InjectorUtil.getInjector().dbCacheService.get(AllianceBattleTeam.class, battleTeamId);
			if(allianceBattleTeam != null) {
				Collection<Long> collection = allianceBattleTeam.getMemberMarch().values();
				if(collection != null) {
					for(Long worldMarchId : collection) {
						WorldMarch worldMarch2 = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, worldMarchId);
						if(worldMarch2 != null) {
							Player ownerPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, worldMarch2.getOwnerUid());
							resViewBattleTeamDetailMessage.teamPlayerList.add(WorldConverter.converterTeamPlayerBean(allianceBattleTeam.getId(), worldMarch2, ownerPlayer));
						}
					}
				}
			}
			player.send(resViewBattleTeamDetailMessage);
		} else {
			Player targetPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, Long.valueOf(battleTeamId));
			ResViewBattleTeamDetailMessage resViewBattleTeamDetailMessage = new ResViewBattleTeamDetailMessage();
			resViewBattleTeamDetailMessage.type = type;
			resViewBattleTeamDetailMessage.teamId = battleTeamId;
			List<WorldMarch> worldMarchs = worldMarchManager.getReinforceWorldMarch(targetPlayer.getWorldPoint());
			if(worldMarchs != null && !worldMarchs.isEmpty()) {
				for(WorldMarch worldMarch : worldMarchs) {
					Player ownerPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, worldMarch.getOwnerUid());
					resViewBattleTeamDetailMessage.teamPlayerList.add(WorldConverter.converterTeamPlayerBean("", worldMarch, ownerPlayer));
				}
			}
			player.send(resViewBattleTeamDetailMessage);
		}
	}
	
	/***
	 * 获取城市驻防信息
	 * @param player
	 */
	public void getCityDefense(Player player) {
		ResMyCityDefenseMessage resMyCityDefenseMessage = new ResMyCityDefenseMessage();
		List<WorldMarch> worldMarchs = worldMarchManager.getReinforceOccupy(player.getWorldPoint());
		if(worldMarchs != null && !worldMarchs.isEmpty()) {
			for(WorldMarch worldMarch : worldMarchs) {
				Player ownerPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, worldMarch.getOwnerUid());
				resMyCityDefenseMessage.teamPlayerList.add(WorldConverter.converterTeamPlayerBean("", worldMarch, ownerPlayer));
			}
		}
		player.send(resMyCityDefenseMessage);
	}
	
	
	/**
	 * 获取我的
	 * @param player
	 */
	public void getCityDefenseDetail(Player player) {
		ResDefensePlayerDeailMessage resMyCityDefenseMessage = new ResDefensePlayerDeailMessage();
		List<WorldMarch> worldMarchs = worldMarchManager.getReinforceOccupy(player.getWorldPoint());
		if(worldMarchs != null && !worldMarchs.isEmpty()) {
			for(WorldMarch worldMarch : worldMarchs) {
				resMyCityDefenseMessage.warSoldierList.addAll(WorldConverter.converterWarSoldier(worldMarch));
			}
		}
		player.send(resMyCityDefenseMessage);
	}
	
	/**
	 * 取消集结进攻
	 * @param player
	 * @param teamId
	 */
	public void cancelTeamAttack(Player player, String teamId) {
		AllianceBattleTeam allianceBattleTeam = InjectorUtil.getInjector().dbCacheService.get(AllianceBattleTeam.class, teamId);
		
		if(allianceBattleTeam.getLeaderId() != player.getId()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE29);
			return;
		}
		
		if(allianceBattleTeam.getAllianceTeamState() != AllianceTeamState.WAIT_MARCH) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E121_WORLD.CODE34);
			return;
		}

		WorldMarch mainMarch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, allianceBattleTeam.getMarchId());
		if(mainMarch == null) {
			// 状态容错
			InjectorUtil.getInjector().dbCacheService.delete(allianceBattleTeam);
			Language.ERRORCODE.send(player, ErrorCodeEnum.E121_WORLD.CODE35);
			return;
		}
		
		if(mainMarch.getMarchState() != MarchState.MASS) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E121_WORLD.CODE36);
			return;
		}

		this.getWorldExecutor().execute(new Runnable() {
			@Override
			public void run() {
				if(allianceBattleTeam != null) {
					((AllianceBattleTeamMarch)mainMarch.getExecutor()).dealDismissAllianceBattleTeam(player, mainMarch, allianceBattleTeam , false);
				}
			}
		});
	
	}
	
	/**
	 * 遣返玩家
	 * @param player
	 * @param playerIds
	 */
	public void refusePlayerDefense(Player player, List<Long> playerIds) {
		List<WorldMarch> worldMarchs = worldMarchManager.getReinforceWorldMarch(player.getWorldPoint());
		if(worldMarchs != null && !worldMarchs.isEmpty()) {
			for(Long playerId : playerIds) {
				boolean flag = false; 
				for(WorldMarch worldMarch : worldMarchs) {
					if(Long.valueOf(worldMarch.getOwnerUid()).equals(playerId)) {
						flag = true;
					}
				}
				
				if(!flag) {
					Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE30);
				}
			}
		}
		
		ResRefusePlayerDefenseMessage resRefusePlayerDefenseMessage = new ResRefusePlayerDefenseMessage();
		resRefusePlayerDefenseMessage.playerId = playerIds;
		player.send(resRefusePlayerDefenseMessage);
	}
	
	/**
	 * 发送集结进攻公告
	 * @param player
	 */
	public void sendAllianceBattleNotice(Player player, String teamId) {
		AllianceBattleTeam allianceBattleTeam = InjectorUtil.getInjector().dbCacheService.get(AllianceBattleTeam.class, teamId);
		if(allianceBattleTeam.getLeaderId() != player.getId()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE29);
			return;
		}
		
		Alliance alliance = InjectorUtil.getInjector().dbCacheService.get(Alliance.class, allianceBattleTeam.getAllianceId());
		if(alliance == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE29);
			return;	
		}
		
		Player targetPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, Long.valueOf(allianceBattleTeam.getTargetUid()));
		if(targetPlayer != null) {
			noticeManager.sendAllianceNotice(player, alliance, NoticeConstant.TEAM_ATTACK, player.getName(), targetPlayer.getName());
		}
	}
	
	/**
	 * 发送当前出征的队伍信息
	 * @param player
	 */
	public void send(Player player) {
		ResUserTroopListMessage resUserTroopListMessage = new ResUserTroopListMessage();
		List<WorldMarch> worldMarchs = worldMarchManager.getWorldMarchByPlayerId(player.getId());
		if(worldMarchs != null && !worldMarchs.isEmpty()) {
			for(WorldMarch worldMarch : worldMarchs) {
				resUserTroopListMessage.vectorInfo.add(WorldConverter.converterVectorInfo(worldMarch, true));
			}
		}
		player.send(resUserTroopListMessage);
	} 
	
	/**
	 * 移除玩家
	 * @param playerId
	 */
	public void offline(long playerId) {
		playerIds.remove(playerId);
	}

	public Set<Long> getWorldPlayerIds() {
		return playerIds;
	}
	
	/**
	 * 获取世界地图线程
	 * @return
	 */
	public ExecutorService getWorldExecutor() {
		return commandProcessor.getMapExecutor();
	}
	
	
	/**
	 * 推送被打
	 * @param player
	 */
	public void pushAttack(Player player) {
		SpriteInfo spriteInfo = player.getSprite();
		if(spriteInfo != null) {
			pushToWorldSprites(spriteInfo, Lists.newArrayList(player.getId()));
		}
	}
	
	/**
	 * 推送世界精灵
	 * @param player
	 * @param spriteInfo
	 */
	public void pushToWorldSprites(SpriteInfo spriteInfo, List<Long> addPlayerIds) {
		ResUpdateSpriteMessage resUpdateVectorInfoMessage = new ResUpdateSpriteMessage();
		resUpdateVectorInfoMessage.spriteBean.add(WorldConverter.converterSprite(spriteInfo, false));
		
		// 推送到世界
		Set<Long> playerIds = this.getWorldPlayerIds();
		if(addPlayerIds != null && !addPlayerIds.isEmpty()){
			playerIds.addAll(addPlayerIds);
		}
		InjectorUtil.getInjector().sessionManager.writePlayers(playerIds, resUpdateVectorInfoMessage);
	}
	
	/**
	 * 推送战斗
	 * @param player
	 * @param spriteInfo
	 */
	public void pushBattleSprite(SpriteInfo spriteInfo) {
		this.pushWorldSprites(Lists.newArrayList(spriteInfo));
	}
	
	/**
	 * 推送世界地图精灵列表
	 * @param spriteInfos
	 */
	public void pushWorldSprites(List<SpriteInfo> spriteInfos) {
		ResUpdateSpriteMessage resUpdateVectorInfoMessage = new ResUpdateSpriteMessage();
		for(SpriteInfo spriteInfo : spriteInfos) {
			resUpdateVectorInfoMessage.spriteBean.add(WorldConverter.converterSprite(spriteInfo, false));	
		}
		
		// 推送到世界
		Set<Long> playerIds = getWorldPlayerIds();
		InjectorUtil.getInjector().sessionManager.writePlayers(playerIds, resUpdateVectorInfoMessage);
	}
	
	/**
	 * 推送领地信息
	 */
	public void pushWorldTerritory(Player player, SpriteInfo spriteInfo) {
		this.pushWorldTerritorys(player, Lists.newArrayList(spriteInfo));
	}
	
	/**
	 * 推送多个精灵信息变更
	 * @param player
	 * @param spriteInfos
	 */
	public void pushWorldTerritorys(Player player, List<SpriteInfo> spriteInfos) {
		ResViewWorldTerritoryMessage resViewWorldTerritoryMessage = new ResViewWorldTerritoryMessage();
		resViewWorldTerritoryMessage.queryType = 2;
		if(spriteInfos != null) {
			for(SpriteInfo spriteInfo : spriteInfos) {
				resViewWorldTerritoryMessage.territoryBean.add(WorldConverter.converterWoldTerritoryBean(spriteInfo));
			}
		}
		
		// 推送到世界
		Set<Long> playerIds = player.getWorldLogicManager().getWorldPlayerIds();
		playerIds.add(player.getId());
		InjectorUtil.getInjector().sessionManager.writePlayers(playerIds, resViewWorldTerritoryMessage);
	}
	
	
	/**
	 * 解散联盟
	 * @param player
	 * @param alliance
	 */
	public void dismissAlliance(Player player, Alliance alliance) {
		getWorldExecutor().execute(new Runnable() {
			@Override
			public void run() {
				List<SpriteInfo> spriteInfos = new ArrayList<>();
				AllianceTerritory allianceTerritory = alliance.getAllianceTerritory();
				if(allianceTerritory != null && allianceTerritory.getTerritory() != null && !allianceTerritory.getTerritory().isEmpty()) {
					for(int index : allianceTerritory.getTerritory()) {
						SpriteInfo spriteInfo = InjectorUtil.getInjector().spriteManager.getGrid(index);
						if(spriteInfo != null && spriteInfo.getParam() != null) {
							WorldSprite worldSprite = spriteInfo.getParam();
							if(worldSprite.getAllianceId() == alliance.getId()) { 
								worldSprite.setAllianceId(0);
								if(spriteInfo.getSpriteType() == SpriteType.TERRITORY.getType()) {
									if(worldSprite.getOwnerMarchId() > 0) {
										spriteInfo.setSpriteType(SpriteType.CAMP.getType());
									} else {
										spriteInfo.setSpriteType(SpriteType.NONE.getType());
									}
								}
								InjectorUtil.getInjector().dbCacheService.update(spriteInfo);
							}
							spriteInfos.add(spriteInfo);
						}
					}
				}
				
				// 推送世界地图领地信息变更
				pushWorldTerritorys(player, spriteInfos);
				pushWorldSprites(spriteInfos);
			}
		});
	}
	
	/**
	 * 处理推出联盟
	 * 1.处理发起集结防御进攻推出联盟
	 * 2.处理参与集结防御推出联盟
	 * 
	 */
	public void dealDismissAllianceTeamBattle(Player player) {
		// 如果我发起集结进攻
		List<WorldMarch> worldMarchs = player.getWorldMarchManager().getWorldMarchByPlayerId(player.getId());
		List<WorldMarch> myAttackWorldMarch = new ArrayList<>();
		List<WorldMarch> myJoinAttackWorldMarch = new ArrayList<>();
		List<WorldMarch> myJoinDefenseWorldMarch = new ArrayList<>();
		
		// 行军队列信息 
		if(worldMarchs != null && !worldMarchs.isEmpty()) {
			for(WorldMarch worldMarch : worldMarchs) {
				// 我发起的集结进攻
				if(worldMarch.getMarchType() == MarchType.TEAM_ATTACK) {
					myAttackWorldMarch.add(worldMarch);
				}
				
				// 参与别人集结进攻
				if(worldMarch.getMarchType() == MarchType.ALLIANCE_MEMBER_ASSEMBLY) {
					myJoinAttackWorldMarch.add(worldMarch);
				}
				
				// 我参与的集结防御
				if(worldMarch.getMarchType() == MarchType.MARCH_REINFORCE) {
					myJoinDefenseWorldMarch.add(worldMarch);
				}
			}
		}

		// 驻防在我这里的玩家
		List<WorldMarch> myDefenseWorldMarch = player.getWorldMarchManager().getReinforceOccupy(player.getWorldPoint());
		
		// 处理世界地图的队伍处理
		getWorldExecutor().execute(new Runnable() {
			@Override
			public void run() {
				if(myAttackWorldMarch != null) {
					for(WorldMarch worldMarch : myAttackWorldMarch) {
						AllianceBattleTeam allianceBattleTeam = InjectorUtil.getInjector().dbCacheService.get(AllianceBattleTeam.class, worldMarch.getTeamId());
						if(allianceBattleTeam.getLeaderId() != player.getId()) {
							continue;
						}
						
						if(allianceBattleTeam.getAllianceTeamState() != AllianceTeamState.WAIT_MARCH) {
							continue;
						}

						WorldMarch mainMarch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, allianceBattleTeam.getMarchId());
						if(mainMarch == null) {
							continue;
						}
						
						if(mainMarch.getMarchState() != MarchState.MASS) {
							continue;
						}
						
						cancelTeamAttack(player, worldMarch.getTeamId());
					}
				}
				
				if(myJoinAttackWorldMarch != null) {
					for(WorldMarch worldMarch : myJoinAttackWorldMarch) {
						AllianceBattleTeam allianceBattleTeam = InjectorUtil.getInjector().dbCacheService.get(AllianceBattleTeam.class, worldMarch.getTeamId());
						if(allianceBattleTeam.getLeaderId() != player.getId()) {
							continue;
						}
						
						if(allianceBattleTeam.getAllianceTeamState() != AllianceTeamState.WAIT_MARCH) {
							continue;
						}

						WorldMarch mainMarch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, allianceBattleTeam.getMarchId());
						if(mainMarch == null) {
							continue;
						}
						
						if(mainMarch.getMarchState() != MarchState.MASS) {
							continue;
						}
						
						// 处理参与的集结进攻的返回
						worldMarch.getExecutor().handleReturn();
					}
				}
				
				if(myJoinDefenseWorldMarch != null) {
					for(WorldMarch worldMarch : myJoinDefenseWorldMarch) {
						if(worldMarch.getBattleEndTime() <= TimeUtils.getCurrentTimeMillis()) {
							worldMarch.getExecutor().handleReturn();
						}
					}
				}
				
				if(myDefenseWorldMarch != null) {
					for(WorldMarch worldMarch : myDefenseWorldMarch) {
						if(worldMarch.getBattleEndTime() <= TimeUtils.getCurrentTimeMillis()) {
							worldMarch.getExecutor().handleReturn();
						}
					}
				}
			}
		});
		
	}
	
	/**
	 * 处理占领
	 * @param player
	 * @param spriteInfo
	 */
	public void dealOccupy(Player player, SpriteInfo spriteInfo) {
		//  处理出征的玩家
		List<WorldMarch> worldMarchs = player.getWorldMarchManager().getWorldMarchByPlayerId(player.getId());
		if(worldMarchs != null && !worldMarchs.isEmpty()) {
			for(WorldMarch worldMarch : worldMarchs) {
				if(worldMarch.getMarchType() == MarchType.TERRITORY && worldMarch.getMarchState() == MarchState.OCCUPY) {
					if(worldMarch.getOccupyTaskId() > 0) {
						TimerTaskData timerTaskData = InjectorUtil.getInjector().dbCacheService.get(TimerTaskData.class, worldMarch.getOccupyTaskId());
						if(timerTaskData != null) {
							getWorldExecutor().execute(new Runnable() {
								@Override
								public void run() {
									((TerritoryWorldMarch)worldMarch.getExecutor()).dealExitAlliance();
								}
							});
						}
					}
				}
			}
		}
		pushToWorldSprites(spriteInfo, Lists.newArrayList(player.getId()));
	}

	/**
	 * 切换联盟领地
	 * @param spriteInfo
	 * @param allianceId
	 */
	public void changeAllianceTerritory (SpriteInfo spriteInfo, boolean dissmiss, long leaderPlayerId) {
		getWorldExecutor().execute(new Runnable() {
			@Override
			public void run() {
				WorldSprite worldSprite = spriteInfo.getParam();
				if (worldSprite != null) {
					if(dissmiss) {
						worldSprite.setAllianceId(0);
						worldSprite.setTerritoryPlayerId(0);
						InjectorUtil.getInjector().dbCacheService.update(spriteInfo);
					} else {
						worldSprite.setTerritoryPlayerId(leaderPlayerId);
						InjectorUtil.getInjector().dbCacheService.update(spriteInfo);
					}
				}
			}
		});
	}

	/**
	 * 改变基地联盟信息
	 * @param spriteInfo
	 * @param player
	 * @param join 是否是加入联盟 true标识加入，false 标识退出
	 */
	public void changeAllianceSprite(SpriteInfo spriteInfo, Player player,  boolean join) {
		// 更新地图精灵
		getWorldExecutor().execute(new Runnable() {
			@Override
			public void run() {
				WorldSprite worldSprite = spriteInfo.getParam();
				if(join) {
					worldSprite.setAllianceId(player.getAllianceId());
				} else {
					worldSprite.setAllianceId(0);
				}
				InjectorUtil.getInjector().dbCacheService.update(spriteInfo);
				pushWorldTerritory(player, spriteInfo);
			}
		});
	}
	
	/**
	 * 改变世界地图玩家等级
	 * @param player
	 */
	public void changeWorldPlayerLevel(Player player) {
		getWorldExecutor().execute(new Runnable() {
			@Override
			public void run() {
				SpriteInfo spriteInfo = player.getSprite();
				if (spriteInfo != null && spriteInfo.getEnumSpriteType() == SpriteType.PLAYER) {
					PlayerSprite playerSprite = spriteInfo.getParam();
					if (playerSprite != null) {
						playerSprite.setPlayerLevel(player.getLevel());
						InjectorUtil.getInjector().dbCacheService.update(spriteInfo);
					}
				}
			}
		});
	}
	
	/**
	 * 处理精灵上战斗切换
	 * @param spriteInfo
	 * @return
	 */
	public boolean dealNextBattleAction(SpriteInfo spriteInfo,  WarFinishQueue warFinishQueue) {
		WorldSprite worldSprite = spriteInfo.getParam();
		if(worldSprite != null) {
			worldSprite.setCurrentBattleStartTime(0);
			worldSprite.setCurrentBattleEndTime(0);
			worldSprite.setBattleSoldierIds(null);
			worldSprite.setAttackMarchId(0);
			InjectorUtil.getInjector().dbCacheService.update(spriteInfo);
			
			// 推送精灵变更
			pushToWorldSprites(spriteInfo,null);
	
			// 推送战斗结束
			Battle battle  = warFinishQueue.getBattle();
			if(battle != null) {
				battle.getWarHandler().fightEnd(battle, warFinishQueue.getWarEndReport());
			}
			
			Queue<Long> queue = worldSprite.getBattleWaitQueue();
			if(queue != null && !queue.isEmpty()) {
				try {
					while(true) {
						if(queue.isEmpty()) {
							break;
						}
						
						Long id = queue.poll();
						WorldMarch worldMarch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, id);
						if(worldMarch != null) {
							if(worldMarch.getExecutor().initBattle()) {
								return true;
							}
						}
					}
				} finally {
					// 更新精灵信息
					InjectorUtil.getInjector().dbCacheService.update(spriteInfo);
				}
			}
		}
		
		return false;
	}
	
	/**
	 * 判断当前点周围是否存在联盟领地
	 * @param targetSprite
	 * @return
	 */
	public boolean checkAllianceTerritory(Player player, int targetId) {
		boolean flag = false;
		int x = targetId % WorldConstant.X_GRIDNUM;
		int y = targetId / WorldConstant.X_GRIDNUM;
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				if (i == x && j == y) {
					continue;
				}
				
				if (x < 0 || j < 0 || x >= WorldConstant.X_GRIDNUM || y >= WorldConstant.Y_GRIDNUM) {
					continue;
				}

				SpriteInfo temp = player.getSpriteManager().getGrid(j * WorldConstant.X_GRIDNUM + i);
				if (temp != null && temp.getEnumSpriteType() != SpriteType.BLOCK) {
					WorldSprite tempWorldSprite = temp.getParam();
					if (tempWorldSprite.getAllianceId() == player.getAllianceId()) {
						flag = true;
					} else if(!StringUtils.isEmpty(tempWorldSprite.getOwnerId())) {
						flag = true;
					}
				}
			}
		}
		return flag;
//		if(!flag) {
//			Language.ERRORCODE.send(player, ErrorCodeEnum.E121_WORLD.CODE15);
//			return false;
//		}
	}
	
	/**
	 * 处理迁城城市坐标
	 * @param player
	 * @param index
	 */
	private void dealAllianceCityDefenseByMoveCity(Player player, int beforeIndex, int afterIndex) {
		List<WorldMarch> worldMarchList = worldMarchManager.getReinforceOccupy(beforeIndex);
		if(worldMarchList != null && !worldMarchList.isEmpty()) {
			for(WorldMarch worldMarch : worldMarchList) {
				worldMarchManager.removeDestMarch(beforeIndex, worldMarch.getId());
				worldMarch.setTargetId(afterIndex);
				InjectorUtil.getInjector().dbCacheService.update(worldMarch);
				
				// 添加到目的地行军
				worldMarchManager.addDestMarch(afterIndex, worldMarch.getId());
			}
		}
	}
	
}
