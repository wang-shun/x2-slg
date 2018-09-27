package com.xgame.logic.server.game.world.entity.march.concrete;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import com.xgame.config.global.GlobalPirFactory;
import com.xgame.data.sprite.SpriteType;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.utils.CurrencyUtil;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.customweanpon.converter.CustomWeaponConverter;
import com.xgame.logic.server.game.email.bean.EmailSignature;
import com.xgame.logic.server.game.email.constant.EmailTemplet;
import com.xgame.logic.server.game.email.data.investigate.InvestigateCommonMailData;
import com.xgame.logic.server.game.email.data.investigate.InvestigateResourceMailData;
import com.xgame.logic.server.game.email.data.investigate.InvestigateSetOffSoldier;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.PlayerAttributeManager;
import com.xgame.logic.server.game.radar.entity.InvestigateData;
import com.xgame.logic.server.game.radar.entity.PlayerRadarInvestigate;
import com.xgame.logic.server.game.radar.entity.RadarUtil;
import com.xgame.logic.server.game.soldier.entity.Soldier;
import com.xgame.logic.server.game.war.bean.DefendSoldierBean;
import com.xgame.logic.server.game.world.constant.MarchType;
import com.xgame.logic.server.game.world.entity.MarchCollect;
import com.xgame.logic.server.game.world.entity.SpriteInfo;
import com.xgame.logic.server.game.world.entity.Vector2;
import com.xgame.logic.server.game.world.entity.WorldMarch;
import com.xgame.logic.server.game.world.entity.model.WorldMarchSoldier;
import com.xgame.logic.server.game.world.entity.sprite.ResourceSprite;
import com.xgame.logic.server.game.world.entity.sprite.WorldSprite;
import com.xgame.utils.TimeUtils;


/**
 * 侦查行军
 * @author jacky.jiang
 *
 */
@Slf4j
public class ScoutWorldMarch extends AbstractWorldMarch {

	/**
	 * 被侦查玩家签名
	 */
	private EmailSignature beScoutSignature;
	
	/**
	 * 行军前精灵类型
	 */
	private int spriteType;

	/**
	 * 资源类型
	 */
	private int resourceType;
	
	public ScoutWorldMarch(Player player, int targetPointId, MarchType marchType, int resourceType,EmailSignature beScoutSignature,int spriteType) {
		super(player, targetPointId, marchType, null, null);
		this.resourceType = resourceType;
		this.beScoutSignature = beScoutSignature;
		this.spriteType = spriteType;
	}
	
	@Override
	public boolean checkMarch() {
		Player player = this.getPlayer();
		if(player.getCountryManager().getRadarBuildControl() == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E121_WORLD.CODE11);
			return false;
		}
		
		if(this.getSoldierBriefBeans() != null && !this.getSoldierBriefBeans().isEmpty()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E121_WORLD.CODE19);
			return false;
		}
		
		int num = PlayerAttributeManager.get().matchQueue(player.getId());
		List<WorldMarch> worldMarchs = player.getWorldMarchManager().getWorldMarchByPlayerId(player.getRoleId());
		if(num <= 0 || (worldMarchs != null && worldMarchs.size() >= num)) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E121_WORLD.CODE1, num);
			return false;
		}
		
		// 侦查消耗资源
		int[] scout = GlobalPirFactory.getInstance().scout;
		int level = player.getCountryManager().getMainBuildControl().getDefianlBuild().getLevel();
		int cost = scout[1] * level * level + scout[2] * level + scout[3];
		
		if(!CurrencyUtil.verifyFinal(player, scout[0], cost)) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E121_WORLD.CODE7);
			return false;
		}
		
		if(checkSameAllianceMarch()) {
			Language.ERRORCODE.send(getPlayer(), ErrorCodeEnum.E121_WORLD.CODE24);
			return false;
		}
		
		//  侦查扣除资源
		CurrencyUtil.decrementCurrency(player,  scout[0], cost, GameLogSource.MARCH);
		CurrencyUtil.send(player);
		
		
		return true;
	}
	
	@Override
	public void updateWorldMarch() {
		WorldMarch worldMarch = this.getWorldMarch();
		worldMarch.setTargetSubType(resourceType);
	}

	@Override
	public void toDestination() {
		SpriteInfo target = getTargetByTargetId();
		if(this.getMarchType() == MarchType.SCOUT) {
			try {
				if(spriteType != target.getEnumSpriteType().getType()){
					int emailTempletId = 0;
					if(spriteType ==  SpriteType.PLAYER.getType()){
						emailTempletId = EmailTemplet.侦查失败基地_MAIL_ID;
					}else if(spriteType ==  SpriteType.RESOURCE.getType()){
						emailTempletId = EmailTemplet.侦查失败资源_MAIL_ID;
					}else if(spriteType ==  SpriteType.MONSTER.getType()){
						emailTempletId = EmailTemplet.侦查报告恐怖分子_MAIL_ID;
					}else if(spriteType ==  SpriteType.CAMP.getType()){
						emailTempletId = EmailTemplet.侦查失败扎营_MAIL_ID;
					}else if(spriteType ==  SpriteType.TERRITORY.getType()){
						emailTempletId = EmailTemplet.侦查失败占领_MAIL_ID;
					}
					if(emailTempletId > 0){
						this.getPlayer().getPlayerMailInfoManager().sendPositionException(this.getPlayer(), beScoutSignature,emailTempletId);
					}
				}else{
					if(target.getEnumSpriteType() == SpriteType.PLAYER) {
						log.info("到达指定侦查地点,被侦查玩家id：[{}]...", target.getId());
						WorldSprite worldSprite = (WorldSprite)target.getParam();
						Player beAttackPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class,Long.parseLong(worldSprite.getOwnerId()));
						Player attackPlayer = this.getPlayer();
						if(beAttackPlayer == null) {
							this.failReturn();
							return;
						}
						// 发送邮件
						attackPlayer.getPlayerMailInfoManager().beScout(attackPlayer, beAttackPlayer, target.getX(),target.getY(), 0, 0, EmailTemplet.基地被侦察报告_MAIL_ID);
						if(attackPlayer.getAllianceId() !=0 && attackPlayer.getAllianceId() == beAttackPlayer.getAllianceId()){
							//1.同盟不能侦查
							this.getPlayer().getPlayerMailInfoManager().sendPositionException(this.getPlayer(), beScoutSignature,EmailTemplet.侦查失败基地_MAIL_ID);
						}else if(beAttackPlayer.roleInfo().getRadarData().getAvoidInvestTime() > 0 && beAttackPlayer.roleInfo().getRadarData().getAvoidInvestTime() > TimeUtils.getCurrentTimeMillis()){
							//1.判断是否开启了侦查保护
							this.getPlayer().getPlayerMailInfoManager().sendPositionException(this.getPlayer(), beScoutSignature,EmailTemplet.侦查失败基地_MAIL_ID);
						} else if(beAttackPlayer.roleInfo().getRadarData().getInvestPretendTime() > 0 && beAttackPlayer.roleInfo().getRadarData().getInvestPretendTime() > TimeUtils.getCurrentTimeMillis()) {
							//2.判断是否开启了侦查伪装
							//返回兵力加倍侦查信息
							InvestigateData investigateData = RadarUtil.toRadarBuild(beAttackPlayer);
							//兵力数量翻倍
							for(DefendSoldierBean dsb : investigateData.getRadrSoldiers()) {
								dsb.soldier.soldier.soldier.num = dsb.soldier.soldier.soldier.num * 2;
							}
							investigateData.setSpriteType(SpriteType.PLAYER.getType());
							PlayerRadarInvestigate playerRadarInvestigate = new PlayerRadarInvestigate(beAttackPlayer, this.getWorldMarch().getDestination(), investigateData, beAttackPlayer.roleInfo().getCurrency().getMoney(), beAttackPlayer.roleInfo().getCurrency().getOil(), beAttackPlayer.roleInfo().getCurrency().getRare(), beAttackPlayer.roleInfo().getCurrency().getSteel());
							this.getWorldMarch().setAttach(playerRadarInvestigate);
						} else {
							// 正常侦查
							// 侦查信息
							InvestigateData investigateData = RadarUtil.toRadarBuild(beAttackPlayer);
							investigateData.setSpriteType(SpriteType.PLAYER.getType());
							PlayerRadarInvestigate playerRadarInvestigate = new PlayerRadarInvestigate(beAttackPlayer, this.getWorldMarch().getDestination(), investigateData,beAttackPlayer.roleInfo().getCurrency().getMoney(), beAttackPlayer.roleInfo().getCurrency().getOil(), beAttackPlayer.roleInfo().getCurrency().getRare(), beAttackPlayer.roleInfo().getCurrency().getSteel());
							this.getWorldMarch().setAttach(playerRadarInvestigate);
						}
					} else if(target.getEnumSpriteType() == SpriteType.RESOURCE) {
						Player scoutPlayer = this.getPlayer();
						ResourceSprite resourceSprite = target.getParam();
						WorldMarch beScoutWorldMarch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, resourceSprite.getOwnerMarchId());
						Player beScoutPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class,Long.valueOf(beScoutWorldMarch.getOwnerUid()));
						if(beScoutPlayer == null) {
							this.failReturn();
							return;
						}
						// 有玩家在采集
						if(resourceSprite != null && resourceSprite.getOwnerMarchId() > 0) {
							//发送采集者被侦查报告
							scoutPlayer.getPlayerMailInfoManager().beScout(scoutPlayer, beScoutPlayer, target.getX(), target.getY(), resourceSprite.getResourceType().ordinal(), resourceSprite.getLevel(), EmailTemplet.资源点被侦察报告_MAIL_ID);
							if(scoutPlayer.getAllianceId() !=0 && scoutPlayer.getAllianceId() == beScoutPlayer.getAllianceId()){
								//1.同盟不能侦查
								this.getPlayer().getPlayerMailInfoManager().sendPositionException(this.getPlayer(), beScoutSignature,EmailTemplet.侦查失败资源_MAIL_ID);
							}else{
								//组织侦查报告数据
								InvestigateResourceMailData resourceMailData = new InvestigateResourceMailData();
								resourceMailData.setBeScoutPlayerId(beScoutPlayer.getId());
								resourceMailData.setLocation(beScoutWorldMarch.getDestination());
								MarchCollect failMarchCollect = ((ExplorerWorldMarch)beScoutWorldMarch.executor).getMarchCollect(beScoutPlayer, beScoutWorldMarch, target);
								if (failMarchCollect != null) {
									resourceMailData.setValue(failMarchCollect.getCollectNum());
								}
								
								resourceMailData.setSoldierList(investigateSetOffSoldiers(beScoutWorldMarch));
								resourceMailData.setResourceType(resourceSprite.getResourceType().ordinal());
								resourceMailData.setResourceLevel(resourceSprite.getLevel());
								this.getWorldMarch().setAttach(resourceMailData);
							}
						}
					}else if(target.getEnumSpriteType() == SpriteType.MONSTER) {
						//TODO 恐怖分子
					}else if(target.getEnumSpriteType() == SpriteType.CAMP) {
						Player scoutPlayer = this.getPlayer();
						WorldSprite worldSprite = this.getTargetSpriteInfo().getParam();
						WorldMarch beScoutWorldMarch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, worldSprite.getOwnerMarchId());
						long targetPlayerId = Long.parseLong(beScoutWorldMarch.getOwnerUid());
						Player beScoutPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class,targetPlayerId);
						boolean flag = true;
						if(beScoutPlayer != null){
							scoutPlayer.getPlayerMailInfoManager().beScout(scoutPlayer, beScoutPlayer,target.getX(),target.getY(), 0,  0, EmailTemplet.营地被侦察报告_MAIL_ID);
							if(scoutPlayer.getAllianceId() !=0 && scoutPlayer.getAllianceId() == beScoutPlayer.getAllianceId()){
								//1.同盟不能侦查
								this.getPlayer().getPlayerMailInfoManager().sendPositionException(this.getPlayer(), beScoutSignature,EmailTemplet.侦查失败扎营_MAIL_ID);
								flag = false;
							}
						}
						if(flag){
							this.getWorldMarch().setAttach(investigateCommonMailData(beScoutWorldMarch,target.getEnumSpriteType()));
						}
					} else if (target.getEnumSpriteType() == SpriteType.TERRITORY) {
						Player scoutPlayer = this.getPlayer();
						WorldSprite worldSprite = this.getTargetSpriteInfo().getParam();
						WorldMarch worldMarch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, worldSprite.getOwnerMarchId());
						long targetPlayerId = Long.parseLong(worldMarch.getOwnerUid());
						Player beScoutPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class,targetPlayerId);
						boolean flag = true;
						if (beScoutPlayer != null) {
							scoutPlayer.getPlayerMailInfoManager().beScout(scoutPlayer, beScoutPlayer,target.getX(),target.getY(), 0, 0, EmailTemplet.领地被侦察报告_MAIL_ID);
							if(scoutPlayer.getAllianceId() !=0 && scoutPlayer.getAllianceId() == beScoutPlayer.getAllianceId()){
								//1.同盟不能侦查
								this.getPlayer().getPlayerMailInfoManager().sendPositionException(this.getPlayer(), beScoutSignature,EmailTemplet.侦查失败占领_MAIL_ID);
								flag = false;
							}
						}
						if(flag){
							this.getWorldMarch().setAttach(investigateCommonMailData(worldMarch,target.getEnumSpriteType()));
						}
					}
				}
			} catch(Exception e) {
				log.error("侦查到达目的地异常：", e);
			}
		}
		super.toDestination();
		this.handleReturn();
	}
	
	@Override
	public void backReturnHome() {
		SpriteInfo target = this.getTargetByTargetId();
		if(this.getWorldMarch().getAttach() != null) {
			try {
				log.info("侦查军队到家，生成侦查报告...");
				if(getWorldMarch().getAttach() instanceof PlayerRadarInvestigate) {
					Player player = getPlayer();
					PlayerRadarInvestigate playerRadarInvestigate = (PlayerRadarInvestigate)getWorldMarch().getAttach();
					if(playerRadarInvestigate.getBeScoutPlayerId() > 0) {
						player.getPlayerMailInfoManager().scoutOfPlayer(player, playerRadarInvestigate, target.getX(), target.getY());
					} else {
						player.getPlayerMailInfoManager().sendScoutProtect(player);
					}
				} else if(getWorldMarch().getAttach() instanceof InvestigateResourceMailData) {
					Player player = getPlayer();
					InvestigateResourceMailData investigateResourceMailData = (InvestigateResourceMailData)getWorldMarch().getAttach();
					player.getPlayerMailInfoManager().scoutOfResource(player, investigateResourceMailData, target.getX(), target.getY());
				}else if(getWorldMarch().getAttach() instanceof InvestigateCommonMailData) {
					Player player = getPlayer();
					InvestigateCommonMailData investigateCampMailData = (InvestigateCommonMailData)getWorldMarch().getAttach();
					player.getPlayerMailInfoManager().scoutOfCommon(player, investigateCampMailData);
				}
			} catch(Exception e) {
				log.error("侦查返回异常：", e);
			}
		}
		super.backReturnHome();
	}

	@Override
	public double getMarchSpeed() {
		return PlayerAttributeManager.get().scoutMarchSpeed(getPlayer().getId());
	}

	
	private InvestigateCommonMailData investigateCommonMailData(WorldMarch worldMarch, SpriteType spriteType) {
		InvestigateCommonMailData investigateCampMailData = new InvestigateCommonMailData();
		investigateCampMailData.setSoldierList(investigateSetOffSoldiers(worldMarch));
		investigateCampMailData.setLocation(worldMarch.getDestination());
		investigateCampMailData.setBeScoutPlayerId(Long.parseLong(worldMarch.getOwnerUid()));
		investigateCampMailData.setFinishedTime(worldMarch.getMarchArrivalTime() + worldMarch.getExploreTime() * 1000);
		investigateCampMailData.setSpriteType(spriteType);
		return investigateCampMailData;
	}
	
	private List<InvestigateSetOffSoldier> investigateSetOffSoldiers(WorldMarch worldMarch) {
		WorldMarchSoldier marchSoldier = worldMarch.getWorldMarchSoldier();
		Iterator<Soldier> iterator2 = marchSoldier.querySoldierList().iterator();
		List<InvestigateSetOffSoldier> list = new ArrayList<>();
		while (iterator2.hasNext()) {
			Soldier soldier = (Soldier) iterator2.next();
			InvestigateSetOffSoldier investigateSoldier = new InvestigateSetOffSoldier();
			investigateSoldier.num = soldier.getNum();
			investigateSoldier.soldierId = soldier.getSoldierId();
			investigateSoldier.loaction = new Vector2(soldier.getVector().x, soldier.getVector().y);
			investigateSoldier.color = soldier.getIndex();
			investigateSoldier.designMapBean = CustomWeaponConverter.converterDesignMapBean(soldier.getDesignMap());
			list.add(investigateSoldier);
		}
		return list;
	}
}
