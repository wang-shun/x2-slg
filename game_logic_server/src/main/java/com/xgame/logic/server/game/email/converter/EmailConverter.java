package com.xgame.logic.server.game.email.converter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.constant.CurrencyEnum;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.customweanpon.converter.CustomWeaponConverter;
import com.xgame.logic.server.game.email.bean.AttackEmailInfo;
import com.xgame.logic.server.game.email.bean.AttackMemberInfo;
import com.xgame.logic.server.game.email.bean.AwardEmailInfo;
import com.xgame.logic.server.game.email.bean.BeScoutEmailInfo;
import com.xgame.logic.server.game.email.bean.CollectionEmailInfo;
import com.xgame.logic.server.game.email.bean.DefenseTowerPro;
import com.xgame.logic.server.game.email.bean.DefensiveEmailInfo;
import com.xgame.logic.server.game.email.bean.EmailInfo;
import com.xgame.logic.server.game.email.bean.EmailSignature;
import com.xgame.logic.server.game.email.bean.ReinforceEmailInfo;
import com.xgame.logic.server.game.email.bean.ScoutEmailInfo;
import com.xgame.logic.server.game.email.bean.SoldierEmailInfo;
import com.xgame.logic.server.game.email.bean.SoldierStateEmailInfo;
import com.xgame.logic.server.game.email.bean.TeamAttackEmailInfo;
import com.xgame.logic.server.game.email.bean.TerritoryEmailInfo;
import com.xgame.logic.server.game.email.bean.TradeEmailInfo;
import com.xgame.logic.server.game.email.data.investigate.InvestigateCommonMailData;
import com.xgame.logic.server.game.email.data.investigate.InvestigateResourceMailData;
import com.xgame.logic.server.game.email.data.investigate.InvestigateSetOffSoldier;
import com.xgame.logic.server.game.email.entity.UserEmail;
import com.xgame.logic.server.game.email.message.ResDeleteEmailMessage;
import com.xgame.logic.server.game.email.message.ResNewEmailFlagMessage;
import com.xgame.logic.server.game.email.message.ResQueryAllEmailMessage;
import com.xgame.logic.server.game.email.message.ResSaveEmailMessage;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.radar.entity.InvestigateData;
import com.xgame.logic.server.game.radar.entity.PlayerRadarInvestigate;
import com.xgame.logic.server.game.soldier.entity.Soldier;
import com.xgame.logic.server.game.war.bean.DefendSoldierBean;
import com.xgame.logic.server.game.war.bean.WarBuilding;
import com.xgame.logic.server.game.war.bean.WarResourceBean;
import com.xgame.logic.server.game.war.constant.WarType;
import com.xgame.logic.server.game.war.entity.Battle;
import com.xgame.logic.server.game.war.entity.WarAttacker;
import com.xgame.logic.server.game.war.entity.WarDefender;
import com.xgame.logic.server.game.war.entity.WarFightParam;
import com.xgame.logic.server.game.world.entity.MarchCollect;
import com.xgame.logic.server.game.world.entity.SpriteInfo;
import com.xgame.logic.server.game.world.entity.WorldMarch;
import com.xgame.logic.server.game.world.entity.model.WorldMarchSoldier;
import com.xgame.utils.EnumUtils;

/**
 * 邮件转换器
 * @author jacky.jiang
 *
 */
public class EmailConverter {
	
	public static String emailOfPlayerName(long playerId,String name){
		return "[u][url="+playerId+"]"+name+"[/url][/u]";
	}
	
	public static ResNewEmailFlagMessage resNewEmailFlagBuilder(boolean hasNewEmail){
		ResNewEmailFlagMessage info = new ResNewEmailFlagMessage();
		info.hasNewEmail = hasNewEmail;
		return info;
	}
	
	public static CollectionEmailInfo collectionEmailInfoBuilder(Player player,Vector2Bean position,MarchCollect marchCollect){
		CollectionEmailInfo collectionEmailInfo = new CollectionEmailInfo();
		collectionEmailInfo.targetPosition = position;
		collectionEmailInfo.type = marchCollect.getResourceType().ordinal();
		collectionEmailInfo.value = marchCollect.getCollectNum();
		collectionEmailInfo.level  = marchCollect.getLevel();
		collectionEmailInfo.collectSignature = emailSignatureBuilder(player, player.getLocation().getX(), player.getLocation().getY());
		
		return collectionEmailInfo;
	}
	
	public static DefensiveEmailInfo defensiveEmailInfoBuilder(Player player,List<Soldier> list,long resMoney,long resOil,long resRare,long resSteel){
		DefensiveEmailInfo info = new DefensiveEmailInfo();
		info.signature = emailSignatureBuilder(player, player.getLocation().getX(), player.getLocation().getY());
		info.resMoney = resMoney;
		info.resOil = resOil;
		info.resRare = resRare;
		info.resSteel = resSteel;
		for(Soldier soldier : list){
			info.soldierList.add(soldierEmailInfoBuilder(soldier));
		}
		
		return info;
	}
	
	public static TradeEmailInfo tradeEmailInfoBuilder(Player player,int resMoney,int resOil,int resRare,int resSteel){
		TradeEmailInfo info = new TradeEmailInfo();
		info.signature = emailSignatureBuilder(player, player.getLocation().getX(), player.getLocation().getY());
		info.resMoney = resMoney;
		info.resOil = resOil;
		info.resRare = resRare;
		info.resSteel = resSteel;
		return info;
	}
	
	public static ReinforceEmailInfo reinforceEmailInfoBuilder(Player player,Player beReinforce,WorldMarch attackWorldMarch,int targetX,int targetY){
		ReinforceEmailInfo info = new ReinforceEmailInfo();
		info.reinforceSignature = emailSignatureBuilder(player, player.getLocation().getX(), player.getLocation().getY());
		info.beReinforceSignature = emailSignatureBuilder(beReinforce,targetX,targetY);
		
		info.totalFightPower = attackWorldMarch.getWorldMarchSoldier().getFightPower(player);
		
		for(Soldier soldier : attackWorldMarch.getWorldMarchSoldier().getSoldiers().values()){
			info.soldierList.add(soldierEmailInfoBuilder(soldier));
		}
		return info;
	}
	
	public static BeScoutEmailInfo beScoutEmailInfoBuilder(Player attackPlayer,Player beAttackPlayer,int x,int y, int type, int level,int emailTempletId){
		BeScoutEmailInfo beScoutEmailInfo = new BeScoutEmailInfo();
		EmailSignature emailSignature = EmailConverter.emailSignatureBuilder(attackPlayer,attackPlayer.getLocation().getX(), attackPlayer.getLocation().getY());
		beScoutEmailInfo.scoutSignature = emailSignature;
		Vector2Bean vector2Bean = new Vector2Bean();
		vector2Bean.x = x;
		vector2Bean.y = y;
		beScoutEmailInfo.targetPosition = vector2Bean;
		beScoutEmailInfo.resourceLevel = level;
		beScoutEmailInfo.resourceType = type;
		return beScoutEmailInfo;
	}
	
	private static List<SoldierEmailInfo> soldierList(List<InvestigateSetOffSoldier> soldierList){
		List<SoldierEmailInfo> list = new ArrayList<>();
		for(InvestigateSetOffSoldier s : soldierList){
			SoldierEmailInfo soldierEmailInfo = new SoldierEmailInfo();
			soldierEmailInfo.num = s.num;
			soldierEmailInfo.soldierId = s.soldierId;
			soldierEmailInfo.loaction = s.loaction.toVectorBean();
			soldierEmailInfo.designMapBean = s.designMapBean;
			list.add(soldierEmailInfo);
		}
		
		return list;
	}
	
	public static ScoutEmailInfo scoutEmailInfoBuilder(Player player,InvestigateResourceMailData investigateResourceMailData,int x,int y){
		ScoutEmailInfo scoutEmailInfo = new ScoutEmailInfo();
		Player beScoutPlayer  = InjectorUtil.getInjector().dbCacheService.get(Player.class, investigateResourceMailData.getBeScoutPlayerId());
		if(beScoutPlayer != null) {
			EmailSignature scoutSignature = EmailConverter.emailSignatureBuilder(beScoutPlayer, x, y);
			scoutEmailInfo.beScoutSignature = scoutSignature;
		}
	
		if(investigateResourceMailData.getResourceType() == CurrencyEnum.GLOD.ordinal()) {
			scoutEmailInfo.resMoney = investigateResourceMailData.getValue();
		} else if(investigateResourceMailData.getResourceType() == CurrencyEnum.OIL.ordinal()) {
			scoutEmailInfo.resOil = investigateResourceMailData.getValue();
		} else if(investigateResourceMailData.getResourceType() == CurrencyEnum.STEEL.ordinal()) {
			scoutEmailInfo.resSteel = investigateResourceMailData.getValue();
		} else if(investigateResourceMailData.getResourceType() == CurrencyEnum.RARE.ordinal()) {
			scoutEmailInfo.resRare = investigateResourceMailData.getValue();
		}
		scoutEmailInfo.soldierList = soldierList(investigateResourceMailData.getSoldierList());
		Vector2Bean targetPosition = new Vector2Bean();
		targetPosition.x = x;
		targetPosition.y = y;
		scoutEmailInfo.targetPosition = targetPosition;
		scoutEmailInfo.resourceType = investigateResourceMailData.getResourceType();
		scoutEmailInfo.resourceLevel = investigateResourceMailData.getResourceLevel();
		
		return scoutEmailInfo;
	}
	
	public static ScoutEmailInfo scoutEmailInfoBuilder(Player player,PlayerRadarInvestigate investigateResourceMailData,int x,int y){
		EmailSignature scoutSignature = new EmailSignature();
		Player beScoutPlayer  = InjectorUtil.getInjector().dbCacheService.get(Player.class, investigateResourceMailData.getBeScoutPlayerId());
		if(beScoutPlayer != null) {
			scoutSignature = EmailConverter.emailSignatureBuilder(beScoutPlayer, beScoutPlayer.getLocation().getX(), beScoutPlayer.getLocation().getY());
		}
		ScoutEmailInfo scoutEmailInfo = new ScoutEmailInfo();
		scoutEmailInfo.beScoutSignature = scoutSignature;
		scoutEmailInfo.resMoney = investigateResourceMailData.getMoney();
		scoutEmailInfo.resOil = investigateResourceMailData.getOil();
		scoutEmailInfo.resRare = investigateResourceMailData.getRare();
		scoutEmailInfo.resSteel = investigateResourceMailData.getSteel();
		InvestigateData investigateData = investigateResourceMailData.getInvestigateData();
		List<WarBuilding> warBuildings = investigateData.getTowerBuilds();
		for (WarBuilding warBuilding : warBuildings) {
			DefenseTowerPro towerPro = new DefenseTowerPro();
			towerPro.defnseId = warBuilding.building.sid;
			towerPro.defnseLevel = warBuilding.building.level;
			towerPro.loaction = warBuilding.transform.vector2Bean;
			scoutEmailInfo.defenseTowers.add(towerPro);
		}
		
		Iterator<DefendSoldierBean> iterator2 = investigateData.getRadrSoldiers().iterator();
		while (iterator2.hasNext()) {
			DefendSoldierBean next = iterator2.next();
			SoldierEmailInfo soldierEmailInfo = new SoldierEmailInfo();
			soldierEmailInfo.num = next.soldier.soldier.soldier.num;
			soldierEmailInfo.soldierId = next.soldier.soldier.soldier.soldierId;
			soldierEmailInfo.loaction = next.soldier.position;
			scoutEmailInfo.soldierList.add(soldierEmailInfo);
		}
		Vector2Bean targetPosition = new Vector2Bean();
		targetPosition.x = x;
		targetPosition.y = y;
		scoutEmailInfo.targetPosition = targetPosition;
		
		return scoutEmailInfo;
	}
	
	public static ScoutEmailInfo scoutEmailInfoBuilder(Player player,InvestigateCommonMailData investigateCampMailData) {
		EmailSignature scoutSignature = new EmailSignature();
		ScoutEmailInfo scoutEmailInfo = new ScoutEmailInfo();
		Player beScoutPlayer  = InjectorUtil.getInjector().dbCacheService.get(Player.class, investigateCampMailData.getBeScoutPlayerId());
		if(beScoutPlayer != null) {
			scoutSignature = EmailConverter.emailSignatureBuilder(beScoutPlayer, beScoutPlayer.getLocation().toVectorBean().x, player.getLocation().toVectorBean().y);
			scoutEmailInfo.beScoutSignature = scoutSignature;
		}
		scoutEmailInfo.soldierList = soldierList(investigateCampMailData.getSoldierList());
		scoutEmailInfo.targetPosition = investigateCampMailData.getLocation();
		scoutEmailInfo.finishedTime = investigateCampMailData.getFinishedTime();
		return scoutEmailInfo;
	}
	
	public static TerritoryEmailInfo territoryEmailInfoBuidler(Player player,WorldMarch attackWorldMarch,int targetX,int targetY){
		TerritoryEmailInfo info = new TerritoryEmailInfo();
		EmailSignature signature = EmailConverter.emailSignatureBuilder(player, player.getLocation().toVectorBean().x, player.getLocation().toVectorBean().y);
		info.signature = signature;
		
		Vector2Bean targetPosition = new Vector2Bean();
		targetPosition.x = targetX;
		targetPosition.y = targetY;
		info.targetPosition = targetPosition;
		for(Soldier soldier : attackWorldMarch.querySoldierList()){
			SoldierEmailInfo soldierEmailInfo = soldierEmailInfoBuilder(soldier);
			info.soldierList.add(soldierEmailInfo);
		}
		
		return info;
	}
	
	public static SoldierEmailInfo soldierEmailInfoBuilder(Soldier soldier){
		SoldierEmailInfo soldierEmailInfo = new SoldierEmailInfo();
		soldierEmailInfo.soldierId = soldier.getSoldierId();
		soldierEmailInfo.num = soldier.getNum();
		soldierEmailInfo.designMapBean = CustomWeaponConverter.converterDesignMapBean(soldier.getDesignMap());
		return soldierEmailInfo;
	}
	
	public static TeamAttackEmailInfo teamAttackEmailInfo(Battle battle){
		TeamAttackEmailInfo info = new TeamAttackEmailInfo();
		Player attackPlayer = battle.getWarAttacker().getPlayer();
		Player defendPlayer = battle.getWarDefender().getPlayer();
		WarAttacker warAttacker = battle.getWarAttacker();
		WarDefender warDefender = battle.getWarDefender();
		WarFightParam warFightParam = battle.getWarFightParam();
		EmailSignature attackSignature = EmailConverter.emailSignatureBuilder(attackPlayer,warFightParam.getAttackSpriteInfo().getX(), warFightParam.getAttackSpriteInfo().getY());
		EmailSignature defendSignature = EmailConverter.emailSignatureBuilder(defendPlayer,warFightParam.getTargetSpriteInfo().getX(), warFightParam.getTargetSpriteInfo().getY());
		info.redSignature = attackSignature;
		info.blueSignature = defendSignature;
		info.totalResource = 999;//TODO 测试数据
		int resMoney = 0,resOil = 0,resRare = 0,resSteel = 0;
		
		for(WorldMarch wm : warFightParam.getAttackMarchList()){
			long playerId = Long.parseLong(wm.getOwnerUid());
			WarResourceBean warResourceBean = battle.getWarResource().get(playerId);
			if(warResourceBean != null){
				resMoney += warResourceBean.moneyNum;
				resOil += warResourceBean.oilNum;
				resRare += warResourceBean.rareNum;
				resSteel += warResourceBean.steelNum;
			}
			//进攻方成员兵力战况
			WorldMarchSoldier worldMarchSoldier = warAttacker.getWorldMarchSoldierMap().get(playerId);
			addAttackMember(worldMarchSoldier,info.redSoldierList);
		}
		//防守方成员兵力战况
		List<Soldier> defenderSoldiers = warDefender.getSoldierBeans();
		AttackMemberInfo defenderMemberInfo = new AttackMemberInfo();
		defenderMemberInfo.signature = EmailConverter.emailSignatureBuilder(defendPlayer,defendPlayer.getLocation().toVectorBean().x, defendPlayer.getLocation().toVectorBean().y);
		int fight = 0;
		for(Soldier soldier : defenderSoldiers){
			SoldierStateEmailInfo soldierStateEmailInfo = EmailConverter.soldierStateEmailInfoBuilder(soldier);
			defenderMemberInfo.attackSoldierList.add(soldierStateEmailInfo);
			fight+=soldier.getLostFightPower();
		}
		defenderMemberInfo.loseFightPower = fight;
		info.blueSoldierList.add(defenderMemberInfo);
		Map<Long, WorldMarchSoldier> reinforce = warDefender.getReinforce();
		for(WorldMarchSoldier worldMarchSoldier : reinforce.values()){
			addAttackMember(worldMarchSoldier,info.blueSoldierList);
		}
		
		info.resMoney = resMoney;
		info.resOil = resOil;
		info.resRare = resRare;
		info.resSteel = resSteel;
		info.winner = String.valueOf(battle.getWinPlayerId()).equals(attackPlayer.getId().toString()) ? 1 : 2;
		
		return info;
	}
	
	/**
	 * 添加成员战斗信息
	 * @param worldMarchSoldier
	 * @param soldierList
	 */
	private static void addAttackMember(WorldMarchSoldier worldMarchSoldier,List<AttackMemberInfo> soldierList){
		if(worldMarchSoldier != null){
			AttackMemberInfo attackMemberInfo = new AttackMemberInfo();
			Player memberPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, worldMarchSoldier.getPlayerId());
			if(memberPlayer != null){
				attackMemberInfo.signature = EmailConverter.emailSignatureBuilder(memberPlayer,memberPlayer.getLocation().toVectorBean().x, memberPlayer.getLocation().toVectorBean().y);
				int fight = 0;
				Iterator<Soldier> iterator = worldMarchSoldier.querySoldierList().iterator();
				while (iterator.hasNext()) {
					Soldier soldier = iterator.next();
					SoldierStateEmailInfo soldierStateEmailInfo = EmailConverter.soldierStateEmailInfoBuilder(soldier);
					attackMemberInfo.attackSoldierList.add(soldierStateEmailInfo);
					fight+=soldier.getLostFightPower();
				}
				attackMemberInfo.loseFightPower = fight;
				soldierList.add(attackMemberInfo);
			}
		}
	}
	
	public static AttackEmailInfo attackEmailInfoBuilder(Battle battle,int attEmailId,int defEmail,WarType warType,Object... params){
		WarAttacker warAttacker = battle.getWarAttacker();
		WarDefender warDefender = battle.getWarDefender();
		
		AttackEmailInfo attackEmailInfo = new AttackEmailInfo();
		
		Player attackPlayer = battle.getWarAttacker().getPlayer();
		EmailSignature redEmailSignature = EmailConverter.emailSignatureBuilder(attackPlayer, attackPlayer.getLocation().toVectorBean().x, attackPlayer.getLocation().toVectorBean().y);
		attackEmailInfo.redSignature = redEmailSignature;
		
		Player defendPlayer = battle.getWarDefender().getPlayer();
		EmailSignature blueEmailSignature = EmailConverter.emailSignatureBuilder(defendPlayer, defendPlayer.getLocation().toVectorBean().x, defendPlayer.getLocation().toVectorBean().y);
		attackEmailInfo.blueSignature = blueEmailSignature;
		SpriteInfo defendSpriteInfo = battle.getWarFightParam().getTargetSpriteInfo();
		if(defendSpriteInfo != null){
			Vector2Bean targetPosition = new Vector2Bean();
			targetPosition.x = defendSpriteInfo.getX();
			targetPosition.y = defendSpriteInfo.getY();
			attackEmailInfo.targetPosition = targetPosition;
		}
		WarResourceBean warResourceBean = battle.getWarResource().get(warAttacker.getPlayer().getRoleId());
		attackEmailInfo.winderId = battle.getWinPlayerId();
		if(warResourceBean != null) {
			attackEmailInfo.resMoney = warResourceBean.moneyNum;
			attackEmailInfo.resOil = warResourceBean.oilNum;
			attackEmailInfo.resRare = warResourceBean.rareNum;
			attackEmailInfo.resSteel = warResourceBean.steelNum;
		}
		int fight = 0;
		WorldMarchSoldier worldMarchSoldier = warAttacker.getWorldMarchSoldierMap().get(warAttacker.getAttackId());
		if(worldMarchSoldier != null) {
			Iterator<Soldier> iterator = worldMarchSoldier.querySoldierList().iterator();
			while (iterator.hasNext()) {
				Soldier soldier = iterator.next();
				SoldierStateEmailInfo soldierStateEmailInfo = EmailConverter.soldierStateEmailInfoBuilder(soldier);
				attackEmailInfo.attackSoldierList.add(soldierStateEmailInfo);
				fight+=soldier.getLostFightPower();
			}
			attackEmailInfo.senderLost = fight;
		}
		
		// TODO 要么是基地防守玩家 要么是野外防守玩家,如果既有基地又有野外
		fight = 0;
		Map<Integer, Soldier> soldierMap = warDefender.getDefendSoldierMap();
		if(soldierMap != null && !soldierMap.isEmpty())  {
			Iterator<Soldier> iterator2 = warDefender.getDefendSoldierMap().values().iterator();
			while (iterator2.hasNext()) {
				Soldier soldier = iterator2.next();
				SoldierStateEmailInfo soldierStateEmailInfo = EmailConverter.soldierStateEmailInfoBuilder(soldier);
				attackEmailInfo.defenSoldierList.add(soldierStateEmailInfo);
				fight+=soldier.getLostFightPower();
			}
		} else {
			Iterator<WorldMarchSoldier> iterator3 = warDefender.getReinforce().values().iterator();
			while (iterator3.hasNext()) {
				WorldMarchSoldier soldier = iterator3.next();
				for(Soldier reinSoldier : soldier.getSoldiers().values()) {
					SoldierStateEmailInfo soldierStateEmailInfo = EmailConverter.soldierStateEmailInfoBuilder(reinSoldier);
					attackEmailInfo.defenSoldierList.add(soldierStateEmailInfo);
					fight+=reinSoldier.getLostFightPower();
				}
			}
			
		}
		
		attackEmailInfo.receiverLost = fight;
		if(warType == WarType.EXPLORER){
			attackEmailInfo.resourceType = (int)params[0];
			attackEmailInfo.resourceLevel = (int)params[1];
			int resourceNum = (int)params[2];
			CurrencyEnum currencyEnum = EnumUtils.getEnum(CurrencyEnum.class, attackEmailInfo.resourceType);
			switch(currencyEnum) {
				case GLOD:
					attackEmailInfo.resMoney = resourceNum;
					break;
				case OIL:
					attackEmailInfo.resOil = resourceNum;
					break;
				case RARE:
					attackEmailInfo.resRare = resourceNum;
					break;
				case STEEL:
					attackEmailInfo.resSteel = resourceNum;
					break;
				default:
					break;
			}
		}
		return attackEmailInfo;
	}
	
	public static SoldierStateEmailInfo soldierStateEmailInfoBuilder(Soldier soldier){
		SoldierStateEmailInfo soldierStateEmailInfo = new SoldierStateEmailInfo();
		soldierStateEmailInfo.soldierId = soldier.getSoldierId();
		soldierStateEmailInfo.deathNum = soldier.getDeadNum();
		soldierStateEmailInfo.injuredNum = soldier.getInjureNum();
		soldierStateEmailInfo.aliveNum = soldier.getNum();
		soldierStateEmailInfo.designMapBean = CustomWeaponConverter.converterDesignMapBean(soldier.getDesignMap());
		
		return soldierStateEmailInfo;
	}
	
	public static EmailSignature emailSignatureBuilder(Player player,int x,int y){
		EmailSignature emailSignature = new EmailSignature();
		if(player != null){
			emailSignature.gangId = player.getAllianceId();
			emailSignature.allianceAbbr = player.getAllianceAbbr();
			emailSignature.allianceName = player.getAllianceName();
			emailSignature.playerId = player.getId();
			emailSignature.serverId = player.getServer();
			emailSignature.playerName = player.getName();
			emailSignature.headId = player.getHeadImg();
		}
		
		Vector2Bean position = new Vector2Bean();
		position.x = x;
		position.y = y;
		emailSignature.position = position;
		
		return emailSignature;
	}
	
	public static ResDeleteEmailMessage resDeleteEmailMessageBuilder(List<Long> ids){
		ResDeleteEmailMessage info = new ResDeleteEmailMessage();
		info.idList = ids;
		return info;
	}
	
	public static ResSaveEmailMessage resSaveEmailBuilder(EmailInfo emailInfo,boolean isSave){
		ResSaveEmailMessage info = new ResSaveEmailMessage();
		info.emailInfo = emailInfo;
		info.isSave = isSave;
		
		return info;
	}
	
	
	public static ResQueryAllEmailMessage resQueryAllEmailMessageBuilder(List<EmailInfo> list,int tag1Num,int tag2Num,int tag3Num,int tag4Num,int tag){
		ResQueryAllEmailMessage info = new ResQueryAllEmailMessage();
		info.emailInfoList = list;
		info.tag1Num = tag1Num;
		info.tag2Num = tag2Num;
		info.tag3Num = tag3Num;
		info.tag4Num = tag4Num;
		info.tag = tag;
		
		return info;
	}
	
	
	/**
	 * 邮件转换器
	 * @param userEmail
	 * @return
	 */
	public static EmailInfo converterEmailInfo(UserEmail userEmail,boolean isSenderSignature) {
		EmailInfo emailInfo = new EmailInfo();
		emailInfo.id = userEmail.getId();
		emailInfo.time = userEmail.getCreateTime().getTime();
		emailInfo.type = userEmail.getType();
		emailInfo.templateId = userEmail.getTemplateId();
		emailInfo.state = userEmail.getMailState();
		long playerId;
		if(isSenderSignature){
			playerId = userEmail.getSenderId();
		}else{
			playerId = userEmail.getTargetId();
		}
		Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class, playerId);
		if(player != null){
			EmailSignature emailSignature = new EmailSignature();
			emailSignature.gangId = player.getAllianceId();
			emailSignature.allianceAbbr = player.getAllianceAbbr();
			emailSignature.allianceName = player.getAllianceName();
			emailSignature.headId = player.getHeadImg();
			emailSignature.serverId = player.getServer();
			emailSignature.playerId = player.getId();
			emailSignature.playerName = player.getName();
			emailSignature.position = player.getLocation().toVectorBean();
			
			emailInfo.senderSignature = emailSignature;
		}
		if(userEmail.getAddition() != null && !"".equals(userEmail.getAddition())){
			AwardEmailInfo awardEmailInfo = new AwardEmailInfo();
			String[] arr = userEmail.getAddition().split(",");
			for(String itemStr : arr){
				String[] item = itemStr.split("_");
				awardEmailInfo.id = Integer.parseInt(item[0]);
				awardEmailInfo.num = Integer.parseInt(item[1]);
				emailInfo.awardList.add(awardEmailInfo);
			}
		}
		emailInfo.title = userEmail.getTitle();
		emailInfo.content = userEmail.getContent();
		emailInfo.isRead = userEmail.isReaded();
		return emailInfo;
	}
	
	
	/**
	 * 转换邮件信息
	 * @param userEmailList
	 * @return
	 */
	public static List<EmailInfo> converterEmailInfoList(List<UserEmail> userEmailList,boolean isSenderSignature) { 
		List<EmailInfo> emailInfos = new ArrayList<>();
		for(UserEmail userEmail : userEmailList) {
			emailInfos.add(converterEmailInfo(userEmail,isSenderSignature));
		}
		return emailInfos;
	}

}
