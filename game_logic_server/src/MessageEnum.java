package com.message;
	

import com.xgame.logic.server.gm.handler.ReqGMHandler;
import com.xgame.logic.server.gm.message.ReqGMMessage;
import com.xgame.logic.server.module.awardCenter.handler.ReqGetAwardHandler;
import com.xgame.logic.server.module.awardCenter.message.ReqGetAwardMessage;
import com.xgame.logic.server.module.battle.handler.ReqBattleEnd;
import com.xgame.logic.server.module.battle.message.ReqWarEndMessage;
import com.xgame.logic.server.module.battle.handler.ReqBattleStart;
import com.xgame.logic.server.module.battle.message.ReqWarDataMessage;
import com.xgame.logic.server.module.battle.handler.ReqWarDataHandler;
import com.xgame.logic.server.module.battle.message.ReqWarDataMessage;
import com.xgame.logic.server.module.battle.handler.ReqWarEndHandler;
import com.xgame.logic.server.module.battle.message.ReqWarEndMessage;
import com.xgame.logic.server.module.battle.handler.ResWarEndHandler;
import com.xgame.logic.server.module.battle.message.ResWarEndMessage;
import com.xgame.logic.server.module.commander.handler.ReqAddBlacklistHandler;
import com.xgame.logic.server.module.commander.message.ReqAddBlacklistMessage;
import com.xgame.logic.server.module.commander.handler.ReqAddCPointCommanderHandler;
import com.xgame.logic.server.module.commander.message.ReqAddCPointCommanderMessage;
import com.xgame.logic.server.module.commander.handler.ReqCancelBlacklistHandler;
import com.xgame.logic.server.module.commander.message.ReqCancelBlacklistMessage;
import com.xgame.logic.server.module.commander.handler.ReqChangeNameHandler;
import com.xgame.logic.server.module.commander.message.ReqChangeNameMessage;
import com.xgame.logic.server.module.commander.handler.ReqChangeStyleHandler;
import com.xgame.logic.server.module.commander.message.ReqChangeStyleMessage;
import com.xgame.logic.server.module.commander.handler.ReqInsertEquitHandler;
import com.xgame.logic.server.module.commander.message.ReqInsertEquitMessage;
import com.xgame.logic.server.module.commander.handler.ReqLookOtherCommanderHandler;
import com.xgame.logic.server.module.commander.message.ReqLookOtherCommanderMessage;
import com.xgame.logic.server.module.commander.handler.ReqResetTalentHandler;
import com.xgame.logic.server.module.commander.message.ReqResetTalentMessage;
import com.xgame.logic.server.module.commander.handler.ReqSwitchTanlentHandler;
import com.xgame.logic.server.module.commander.message.ReqSwitchTanlentMessage;
import com.xgame.logic.server.module.commander.handler.ReqTalentAddPointHandler;
import com.xgame.logic.server.module.commander.message.ReqTalentAddPointMessage;
import com.xgame.logic.server.module.commander.handler.ReqUninstellEquitHandler;
import com.xgame.logic.server.module.commander.message.ReqUninstellEquitMessage;
import com.xgame.logic.server.module.country.handler.ReqBuildCustomWeaponTzHandler;
import com.xgame.logic.server.module.country.message.ReqBuildCustomWeaponTzMessage;
import com.xgame.logic.server.module.country.handler.ReqBuildingCollectHandler;
import com.xgame.logic.server.module.country.message.ReqBuildingCollectMessage;
import com.xgame.logic.server.module.country.handler.ReqCampDesitoryHandler;
import com.xgame.logic.server.module.country.message.ReqCampDesitoryMessage;
import com.xgame.logic.server.module.country.handler.ReqCampOutputHandler;
import com.xgame.logic.server.module.country.message.ReqCampOutputMessage;
import com.xgame.logic.server.module.country.handler.ReqCountryHandleHandler;
import com.xgame.logic.server.module.country.message.ReqCountryHandleMessage;
import com.xgame.logic.server.module.country.handler.ReqCreateBuildHandler;
import com.xgame.logic.server.module.country.message.ReqCreateBuildMessage;
import com.xgame.logic.server.module.country.handler.ReqDestoryArmorHandler;
import com.xgame.logic.server.module.country.message.ReqDestoryArmorMessage;
import com.xgame.logic.server.module.country.handler.ReqGetLeaguePlayersHandler;
import com.xgame.logic.server.module.country.message.ReqGetLeaguePlayersMessage;
import com.xgame.logic.server.module.country.handler.ReqGetRepositoryHandler;
import com.xgame.logic.server.module.country.message.ReqGetRepositoryMessage;
import com.xgame.logic.server.module.country.handler.ReqLevelUpBuildHandler;
import com.xgame.logic.server.module.country.message.ReqLevelUpBuildMessage;
import com.xgame.logic.server.module.country.handler.ReqLevelUpTechHandler;
import com.xgame.logic.server.module.country.message.ReqLevelUpTechMessage;
import com.xgame.logic.server.module.country.handler.ReqModSoldierHandler;
import com.xgame.logic.server.module.country.message.ReqModSoldierMessage;
import com.xgame.logic.server.module.country.handler.ReqMoveBuildHandler;
import com.xgame.logic.server.module.country.message.ReqMoveBuildMessage;
import com.xgame.logic.server.module.country.handler.ReqReBuildCustomWeaponTzHandler;
import com.xgame.logic.server.module.country.message.ReqReBuildCustomWeaponTzMessage;
import com.xgame.logic.server.module.country.handler.ReqRecoveryArmorHandler;
import com.xgame.logic.server.module.country.message.ReqRecoveryArmorMessage;
import com.xgame.logic.server.module.country.handler.ReqReformArmorHandler;
import com.xgame.logic.server.module.country.message.ReqReformArmorMessage;
import com.xgame.logic.server.module.country.handler.ReqRemoveObstructHandler;
import com.xgame.logic.server.module.country.message.ReqRemoveObstructMessage;
import com.xgame.logic.server.module.country.handler.ReqRewardRepositoryHandler;
import com.xgame.logic.server.module.country.message.ReqRewardRepositoryMessage;
import com.xgame.logic.server.module.country.handler.ReqSpeedRecoveryArmorHandler;
import com.xgame.logic.server.module.country.message.ReqSpeedRecoveryArmorMessage;
import com.xgame.logic.server.module.country.handler.ReqSwitchMineCarHandler;
import com.xgame.logic.server.module.country.message.ReqSwitchMineCarMessage;
import com.xgame.logic.server.module.country.handler.ReqTrade2PlayerHandler;
import com.xgame.logic.server.module.country.message.ReqTrade2PlayerMessage;
import com.xgame.logic.server.module.defenseSoldier.handler.ReqDefensAutoSetupHandler;
import com.xgame.logic.server.module.defenseSoldier.message.ReqDefensAutoSetupMessage;
import com.xgame.logic.server.module.defenseSoldier.handler.ReqDefensSolderSetupHandler;
import com.xgame.logic.server.module.defenseSoldier.message.ReqDefensSolderSetupMessage;
import com.xgame.logic.server.module.defenseSoldier.handler.ReqUnDefensSolderSetupHandler;
import com.xgame.logic.server.module.defenseSoldier.message.ReqUnDefensSolderSetupMessage;
import com.xgame.logic.server.module.equipments.handler.ReqBuyFragmentHandler;
import com.xgame.logic.server.module.equipments.message.ReqBuyFragmentMessage;
import com.xgame.logic.server.module.equipments.handler.ReqBuySpecialEquipmentHandler;
import com.xgame.logic.server.module.equipments.message.ReqBuySpecialEquipmentMessage;
import com.xgame.logic.server.module.equipments.handler.ReqCancelProduceFragmentHandler;
import com.xgame.logic.server.module.equipments.message.ReqCancelProduceFragmentMessage;
import com.xgame.logic.server.module.equipments.handler.ReqComposeEquipmentHandler;
import com.xgame.logic.server.module.equipments.message.ReqComposeEquipmentMessage;
import com.xgame.logic.server.module.equipments.handler.ReqComposeFragmentHandler;
import com.xgame.logic.server.module.equipments.message.ReqComposeFragmentMessage;
import com.xgame.logic.server.module.equipments.handler.ReqDecomposeEquipmentHandler;
import com.xgame.logic.server.module.equipments.message.ReqDecomposeEquipmentMessage;
import com.xgame.logic.server.module.equipments.handler.ReqLevelUpEquipmentQualityHandler;
import com.xgame.logic.server.module.equipments.message.ReqLevelUpEquipmentQualityMessage;
import com.xgame.logic.server.module.equipments.handler.ReqProduceFragmentHandler;
import com.xgame.logic.server.module.equipments.message.ReqProduceFragmentMessage;
import com.xgame.logic.server.module.equipments.handler.ReqUnlockedHandler;
import com.xgame.logic.server.module.equipments.message.ReqUnlockedMessage;
import com.xgame.logic.server.module.equipments.handler.ReqUnlockProducePositionHandler;
import com.xgame.logic.server.module.equipments.message.ReqUnlockProducePositionMessage;
import com.xgame.logic.server.module.items.handler.ReqPlayerBagHandler;
import com.xgame.logic.server.module.items.message.ReqPlayerBagMessage;
import com.xgame.logic.server.module.items.handler.ReqUseItemHandler;
import com.xgame.logic.server.module.items.message.ReqUseItemMessage;
import com.xgame.logic.server.module.items.handler.ResItemListHandler;
import com.xgame.logic.server.module.items.message.ResItemListMessage;
import com.xgame.logic.server.module.items.handler.ResPlayerBagHandler;
import com.xgame.logic.server.module.items.message.ResPlayerBagMessage;
import com.xgame.logic.server.module.login.handler.ReqCheckPlayerNameHandler;
import com.xgame.logic.server.module.login.message.ReqCheckPlayerNameMessage;
import com.xgame.logic.server.module.login.handler.ReqLoginHandler;
import com.xgame.logic.server.module.login.message.ReqLoginMessage;
import com.xgame.logic.server.module.mail.handler.ReqAllServerSendEmailHandler;
import com.xgame.logic.server.module.mail.message.ReqAllServerSendEmailMessage;
import com.xgame.logic.server.module.mail.handler.ReqDeleteEmailHandler;
import com.xgame.logic.server.module.mail.message.ReqDeleteEmailMessage;
import com.xgame.logic.server.module.mail.handler.ReqGroupSendEmailHandler;
import com.xgame.logic.server.module.mail.message.ReqGroupSendEmailMessage;
import com.xgame.logic.server.module.mail.handler.ReqReadEmailHandler;
import com.xgame.logic.server.module.mail.message.ReqReadEmailMessage;
import com.xgame.logic.server.module.mail.handler.ReqSaveEmailHandler;
import com.xgame.logic.server.module.mail.message.ReqSaveEmailMessage;
import com.xgame.logic.server.module.mail.handler.ReqSendEmailHandler;
import com.xgame.logic.server.module.mail.message.ReqSendEmailMessage;
import com.xgame.logic.server.module.shop.handler.ReqBuyItemHandler;
import com.xgame.logic.server.module.shop.message.ReqBuyItemMessage;
import com.xgame.logic.server.module.task.handler.ReqGetActiveRewardsHandler;
import com.xgame.logic.server.module.task.message.ReqGetActiveRewardsMessage;
import com.xgame.logic.server.module.time.handler.ReqTimeSystemHandler;
import com.xgame.logic.server.module.time.message.ReqTimeSystemMessage;
import com.xgame.logic.server.module.timer.handler.ReqBuildingForFreeHandler;
import com.xgame.logic.server.module.timer.message.ReqBuildingForFreeMessage;
import com.xgame.logic.server.module.timer.handler.ReqCancelTimerTaskHandler;
import com.xgame.logic.server.module.timer.message.ReqCancelTimerTaskMessage;
import com.xgame.logic.server.module.timer.handler.ReqDiamondSpeedHandler;
import com.xgame.logic.server.module.timer.message.ReqDiamondSpeedMessage;
import com.xgame.logic.server.module.timer.handler.ReqUseSpeedItemHandler;
import com.xgame.logic.server.module.timer.message.ReqUseSpeedItemMessage;
import com.xgame.logic.server.module.world.handler.ReqEnterBigSpaceHandler;
import com.xgame.logic.server.module.world.message.ReqEnterBigSpaceMessage;
import com.xgame.logic.server.module.world.handler.ReqLookTroopInfoHandler;
import com.xgame.logic.server.module.world.message.ReqLookTroopInfoMessage;
import com.xgame.logic.server.module.world.handler.ReqOnDestinationHandler;
import com.xgame.logic.server.module.world.message.ReqOnDestinationMessage;
import com.xgame.logic.server.module.world.handler.ReqSpriteSimpleInfoHandler;
import com.xgame.logic.server.module.world.message.ReqSpriteSimpleInfoMessage;
import com.xgame.logic.server.module.world.handler.ReqTroopBackHandler;
import com.xgame.logic.server.module.world.message.ReqTroopBackMessage;
import com.xgame.logic.server.module.world.handler.ReqUseResSpeedUpPropHandler;
import com.xgame.logic.server.module.world.message.ReqUseResSpeedUpPropMessage;
import com.xgame.logic.server.module.world.handler.ReqUseTroopSpeedUpPropHandler;
import com.xgame.logic.server.module.world.message.ReqUseTroopSpeedUpPropMessage;
import com.xgame.logic.server.module.world.handler.ReqViewSpriteHandler;
import com.xgame.logic.server.module.world.message.ReqViewSpriteMessage;
/**
 *
 *2016-11-28  21:36:00
 *@author ye.yuan
 *
 */
public enum MessageEnum {

	
	Req200103(200,103,ReqGMHandler,ReqGMMessage),
	Req201200(201,200,ReqGetAwardHandler,ReqGetAwardMessage),
	Req202500(202,500,ReqBattleEnd,ReqWarEndMessage),
	Req201500(201,500,ReqBattleStart,ReqWarDataMessage),
	Req201500(201,500,ReqWarDataHandler,ReqWarDataMessage),
	Req202500(202,500,ReqWarEndHandler,ReqWarEndMessage),
	Req102500(102,500,ResWarEndHandler,ResWarEndMessage),
	Req200130(200,130,ReqAddBlacklistHandler,ReqAddBlacklistMessage),
	Req310130(310,130,ReqAddCPointCommanderHandler,ReqAddCPointCommanderMessage),
	Req201130(201,130,ReqCancelBlacklistHandler,ReqCancelBlacklistMessage),
	Req300130(300,130,ReqChangeNameHandler,ReqChangeNameMessage),
	Req301130(301,130,ReqChangeStyleHandler,ReqChangeStyleMessage),
	Req302130(302,130,ReqInsertEquitHandler,ReqInsertEquitMessage),
	Req308130(308,130,ReqLookOtherCommanderHandler,ReqLookOtherCommanderMessage),
	Req305130(305,130,ReqResetTalentHandler,ReqResetTalentMessage),
	Req309130(309,130,ReqSwitchTanlentHandler,ReqSwitchTanlentMessage),
	Req304130(304,130,ReqTalentAddPointHandler,ReqTalentAddPointMessage),
	Req303130(303,130,ReqUninstellEquitHandler,ReqUninstellEquitMessage),
	Req200600(200,600,ReqBuildCustomWeaponTzHandler,ReqBuildCustomWeaponTzMessage),
	Req205100(205,100,ReqBuildingCollectHandler,ReqBuildingCollectMessage),
	Req300101(300,101,ReqCampDesitoryHandler,ReqCampDesitoryMessage),
	Req200101(200,101,ReqCampOutputHandler,ReqCampOutputMessage),
	Req202100(202,100,ReqCountryHandleHandler,ReqCountryHandleMessage),
	Req200100(200,100,ReqCreateBuildHandler,ReqCreateBuildMessage),
	Req201601(201,601,ReqDestoryArmorHandler,ReqDestoryArmorMessage),
	Req202400(202,400,ReqGetLeaguePlayersHandler,ReqGetLeaguePlayersMessage),
	Req209100(209,100,ReqGetRepositoryHandler,ReqGetRepositoryMessage),
	Req204100(204,100,ReqLevelUpBuildHandler,ReqLevelUpBuildMessage),
	Req200107(200,107,ReqLevelUpTechHandler,ReqLevelUpTechMessage),
	Req200105(200,105,ReqModSoldierHandler,ReqModSoldierMessage),
	Req206100(206,100,ReqMoveBuildHandler,ReqMoveBuildMessage),
	Req201600(201,600,ReqReBuildCustomWeaponTzHandler,ReqReBuildCustomWeaponTzMessage),
	Req203601(203,601,ReqRecoveryArmorHandler,ReqRecoveryArmorMessage),
	Req202601(202,601,ReqReformArmorHandler,ReqReformArmorMessage),
	Req203100(203,100,ReqRemoveObstructHandler,ReqRemoveObstructMessage),
	Req208100(208,100,ReqRewardRepositoryHandler,ReqRewardRepositoryMessage),
	Req204601(204,601,ReqSpeedRecoveryArmorHandler,ReqSpeedRecoveryArmorMessage),
	Req207100(207,100,ReqSwitchMineCarHandler,ReqSwitchMineCarMessage),
	Req201400(201,400,ReqTrade2PlayerHandler,ReqTrade2PlayerMessage),
	Req301132(301,132,ReqDefensAutoSetupHandler,ReqDefensAutoSetupMessage),
	Req300132(300,132,ReqDefensSolderSetupHandler,ReqDefensSolderSetupMessage),
	Req302132(302,132,ReqUnDefensSolderSetupHandler,ReqUnDefensSolderSetupMessage),
	Req203300(203,300,ReqBuyFragmentHandler,ReqBuyFragmentMessage),
	Req209300(209,300,ReqBuySpecialEquipmentHandler,ReqBuySpecialEquipmentMessage),
	Req204300(204,300,ReqCancelProduceFragmentHandler,ReqCancelProduceFragmentMessage),
	Req200300(200,300,ReqComposeEquipmentHandler,ReqComposeEquipmentMessage),
	Req208300(208,300,ReqComposeFragmentHandler,ReqComposeFragmentMessage),
	Req201300(201,300,ReqDecomposeEquipmentHandler,ReqDecomposeEquipmentMessage),
	Req207300(207,300,ReqLevelUpEquipmentQualityHandler,ReqLevelUpEquipmentQualityMessage),
	Req202300(202,300,ReqProduceFragmentHandler,ReqProduceFragmentMessage),
	Req206300(206,300,ReqUnlockedHandler,ReqUnlockedMessage),
	Req205300(205,300,ReqUnlockProducePositionHandler,ReqUnlockProducePositionMessage),
	Req201109(201,109,ReqPlayerBagHandler,ReqPlayerBagMessage),
	Req200109(200,109,ReqUseItemHandler,ReqUseItemMessage),
	Req101109(101,109,ResItemListHandler,ResItemListMessage),
	Req100109(100,109,ResPlayerBagHandler,ResPlayerBagMessage),
	Req300110(300,110,ReqCheckPlayerNameHandler,ReqCheckPlayerNameMessage),
	Req200104(200,104,ReqLoginHandler,ReqLoginMessage),
	Req203410(203,410,ReqAllServerSendEmailHandler,ReqAllServerSendEmailMessage),
	Req201410(201,410,ReqDeleteEmailHandler,ReqDeleteEmailMessage),
	Req204410(204,410,ReqGroupSendEmailHandler,ReqGroupSendEmailMessage),
	Req202410(202,410,ReqReadEmailHandler,ReqReadEmailMessage),
	Req205410(205,410,ReqSaveEmailHandler,ReqSaveEmailMessage),
	Req200410(200,410,ReqSendEmailHandler,ReqSendEmailMessage),
	Req200120(200,120,ReqBuyItemHandler,ReqBuyItemMessage),
	Req202201(202,201,ReqGetActiveRewardsHandler,ReqGetActiveRewardsMessage),
	Req200113(200,113,ReqTimeSystemHandler,ReqTimeSystemMessage),
	Req203108(203,108,ReqBuildingForFreeHandler,ReqBuildingForFreeMessage),
	Req200108(200,108,ReqCancelTimerTaskHandler,ReqCancelTimerTaskMessage),
	Req202108(202,108,ReqDiamondSpeedHandler,ReqDiamondSpeedMessage),
	Req201108(201,108,ReqUseSpeedItemHandler,ReqUseSpeedItemMessage),
	Req204121(204,121,ReqEnterBigSpaceHandler,ReqEnterBigSpaceMessage),
	Req206121(206,121,ReqLookTroopInfoHandler,ReqLookTroopInfoMessage),
	Req205121(205,121,ReqOnDestinationHandler,ReqOnDestinationMessage),
	Req202121(202,121,ReqSpriteSimpleInfoHandler,ReqSpriteSimpleInfoMessage),
	Req208121(208,121,ReqTroopBackHandler,ReqTroopBackMessage),
	Req209121(209,121,ReqUseResSpeedUpPropHandler,ReqUseResSpeedUpPropMessage),
	Req207121(207,121,ReqUseTroopSpeedUpPropHandler,ReqUseTroopSpeedUpPropMessage),
	Req201121(201,121,ReqViewSpriteHandler,ReqViewSpriteMessage),
	;
	
	
	
	private MessageEnum(int functionId,int id,Class<?> handlerClass,Class<?> messageClass) {}
	private MessageEnum(int functionId,int id,Class<?> messageClass) {}
}
