package com.message;
	

import com.xgame.logic.server.game.alliance.handler.ReqAddTeamMemberHandler;
import com.xgame.logic.server.game.alliance.handler.ReqAllianceFightListHandler;
import com.xgame.logic.server.game.alliance.handler.ReqAllianceOfficeHandler;
import com.xgame.logic.server.game.alliance.handler.ReqApplyAllianceHandler;
import com.xgame.logic.server.game.alliance.handler.ReqApplyListHandler;
import com.xgame.logic.server.game.alliance.handler.ReqCancelApplyHandler;
import com.xgame.logic.server.game.alliance.handler.ReqChangeLeaderHandler;
import com.xgame.logic.server.game.alliance.handler.ReqChangeRankHandler;
import com.xgame.logic.server.game.alliance.handler.ReqCreateAllianceHandler;
import com.xgame.logic.server.game.alliance.handler.ReqDealApplyHandler;
import com.xgame.logic.server.game.alliance.handler.ReqDealInviteHandler;
import com.xgame.logic.server.game.alliance.handler.ReqDefendListHandler;
import com.xgame.logic.server.game.alliance.handler.ReqDeleteTeamMemberHandler;
import com.xgame.logic.server.game.alliance.handler.ReqDimissOfficeHandler;
import com.xgame.logic.server.game.alliance.handler.ReqDismissAllianceHandler;
import com.xgame.logic.server.game.alliance.handler.ReqDonateHandler;
import com.xgame.logic.server.game.alliance.handler.ReqDoneHelpHandler;
import com.xgame.logic.server.game.alliance.handler.ReqEditAllianceHandler;
import com.xgame.logic.server.game.alliance.handler.ReqEditPermissionHandler;
import com.xgame.logic.server.game.alliance.handler.ReqEditTeamHandler;
import com.xgame.logic.server.game.alliance.handler.ReqEditTitleHandler;
import com.xgame.logic.server.game.alliance.handler.ReqExchangeAllianceShopItemHandler;
import com.xgame.logic.server.game.alliance.handler.ReqGatherListHandler;
import com.xgame.logic.server.game.alliance.handler.ReqGetAllianceListHandler;
import com.xgame.logic.server.game.alliance.handler.ReqGetAllianceShopItemListHandler;
import com.xgame.logic.server.game.alliance.handler.ReqGetAllianceShopTreasureListHandler;
import com.xgame.logic.server.game.alliance.handler.ReqGetHelpeListHandler;
import com.xgame.logic.server.game.alliance.handler.ReqGetLeaguePlayersHandler;
import com.xgame.logic.server.game.alliance.handler.ReqHelpAllHandler;
import com.xgame.logic.server.game.alliance.handler.ReqInviteHandler;
import com.xgame.logic.server.game.alliance.handler.ReqKickMemberHandler;
import com.xgame.logic.server.game.alliance.handler.ReqLevelupAllianceHandler;
import com.xgame.logic.server.game.alliance.handler.ReqPlayerAllianceInfoHandler;
import com.xgame.logic.server.game.alliance.handler.ReqQueryAllianceMemeberHandler;
import com.xgame.logic.server.game.alliance.handler.ReqRankPlayerHandler;
import com.xgame.logic.server.game.alliance.handler.ReqRecommendPlayerHandler;
import com.xgame.logic.server.game.alliance.handler.ReqSearchAllianceHandler;
import com.xgame.logic.server.game.alliance.handler.ReqSendHelperHandler;
import com.xgame.logic.server.game.alliance.handler.ReqSendMessageHandler;
import com.xgame.logic.server.game.alliance.handler.ReqSetAutoJoinHandler;
import com.xgame.logic.server.game.alliance.handler.ReqSetOfficeHandler;
import com.xgame.logic.server.game.alliance.handler.ReqTrade2PlayerHandler;
import com.xgame.logic.server.game.alliance.handler.ReqViewTeamMemberHandler;
import com.xgame.logic.server.game.alliance.message.ReqAddTeamMemberMessage;
import com.xgame.logic.server.game.alliance.message.ReqAllianceFightListMessage;
import com.xgame.logic.server.game.alliance.message.ReqAllianceOfficeMessage;
import com.xgame.logic.server.game.alliance.message.ReqApplyAllianceMessage;
import com.xgame.logic.server.game.alliance.message.ReqApplyListMessage;
import com.xgame.logic.server.game.alliance.message.ReqCancelApplyMessage;
import com.xgame.logic.server.game.alliance.message.ReqChangeLeaderMessage;
import com.xgame.logic.server.game.alliance.message.ReqChangeRankMessage;
import com.xgame.logic.server.game.alliance.message.ReqCreateAllianceMessage;
import com.xgame.logic.server.game.alliance.message.ReqDealApplyMessage;
import com.xgame.logic.server.game.alliance.message.ReqDealInviteMessage;
import com.xgame.logic.server.game.alliance.message.ReqDefendListMessage;
import com.xgame.logic.server.game.alliance.message.ReqDeleteTeamMemberMessage;
import com.xgame.logic.server.game.alliance.message.ReqDimissOfficeMessage;
import com.xgame.logic.server.game.alliance.message.ReqDismissAllianceMessage;
import com.xgame.logic.server.game.alliance.message.ReqDonateMessage;
import com.xgame.logic.server.game.alliance.message.ReqDoneHelpMessage;
import com.xgame.logic.server.game.alliance.message.ReqEditAllianceMessage;
import com.xgame.logic.server.game.alliance.message.ReqEditPermissionMessage;
import com.xgame.logic.server.game.alliance.message.ReqEditTeamMessage;
import com.xgame.logic.server.game.alliance.message.ReqEditTitleMessage;
import com.xgame.logic.server.game.alliance.message.ReqExchangeAllianceShopItemMessage;
import com.xgame.logic.server.game.alliance.message.ReqGatherListMessage;
import com.xgame.logic.server.game.alliance.message.ReqGetAllianceListMessage;
import com.xgame.logic.server.game.alliance.message.ReqGetAllianceShopItemListMessage;
import com.xgame.logic.server.game.alliance.message.ReqGetAllianceShopTreasureListMessage;
import com.xgame.logic.server.game.alliance.message.ReqGetHelpeListMessage;
import com.xgame.logic.server.game.alliance.message.ReqGetLeaguePlayersMessage;
import com.xgame.logic.server.game.alliance.message.ReqHelpAllMessage;
import com.xgame.logic.server.game.alliance.message.ReqInviteMessage;
import com.xgame.logic.server.game.alliance.message.ReqKickMemberMessage;
import com.xgame.logic.server.game.alliance.message.ReqLevelupAllianceMessage;
import com.xgame.logic.server.game.alliance.message.ReqPlayerAllianceInfoMessage;
import com.xgame.logic.server.game.alliance.message.ReqQueryAllianceMemeberMessage;
import com.xgame.logic.server.game.alliance.message.ReqRankPlayerMessage;
import com.xgame.logic.server.game.alliance.message.ReqRecommendPlayerMessage;
import com.xgame.logic.server.game.alliance.message.ReqSearchAllianceMessage;
import com.xgame.logic.server.game.alliance.message.ReqSendHelperMessage;
import com.xgame.logic.server.game.alliance.message.ReqSendMessageMessage;
import com.xgame.logic.server.game.alliance.message.ReqSetAutoJoinMessage;
import com.xgame.logic.server.game.alliance.message.ReqSetOfficeMessage;
import com.xgame.logic.server.game.alliance.message.ReqTrade2PlayerMessage;
import com.xgame.logic.server.game.alliance.message.ReqViewTeamMemberMessage;
import com.xgame.logic.server.game.alliance.message.ResAddTeamMemberMessage;
import com.xgame.logic.server.game.alliance.message.ResAllLeaguePlayerMessage;
import com.xgame.logic.server.game.alliance.message.ResAllianceChangeMessage;
import com.xgame.logic.server.game.alliance.message.ResAllianceDataMessage;
import com.xgame.logic.server.game.alliance.message.ResAllianceFightListMessage;
import com.xgame.logic.server.game.alliance.message.ResAllianceHelpDeleteMessage;
import com.xgame.logic.server.game.alliance.message.ResAllianceHelpUpdateMessage;
import com.xgame.logic.server.game.alliance.message.ResAllianceOfficeMessage;
import com.xgame.logic.server.game.alliance.message.ResApplyAllianceMessage;
import com.xgame.logic.server.game.alliance.message.ResApplyListMessage;
import com.xgame.logic.server.game.alliance.message.ResCancelApplyMessage;
import com.xgame.logic.server.game.alliance.message.ResChangeLeaderMessage;
import com.xgame.logic.server.game.alliance.message.ResChangeRankMessage;
import com.xgame.logic.server.game.alliance.message.ResCreateAllianceMessage;
import com.xgame.logic.server.game.alliance.message.ResDealApplyMessage;
import com.xgame.logic.server.game.alliance.message.ResDealInviteMessage;
import com.xgame.logic.server.game.alliance.message.ResDefendListMessage;
import com.xgame.logic.server.game.alliance.message.ResDeleteTeamMemberMessage;
import com.xgame.logic.server.game.alliance.message.ResDimissOfficeMessage;
import com.xgame.logic.server.game.alliance.message.ResDismissAllianceMessage;
import com.xgame.logic.server.game.alliance.message.ResDonateMessage;
import com.xgame.logic.server.game.alliance.message.ResDoneHelpMessage;
import com.xgame.logic.server.game.alliance.message.ResEditAllianceMessage;
import com.xgame.logic.server.game.alliance.message.ResEditPermissionMessage;
import com.xgame.logic.server.game.alliance.message.ResEditTeamMessage;
import com.xgame.logic.server.game.alliance.message.ResEditTitleMessage;
import com.xgame.logic.server.game.alliance.message.ResExchangeAllianceShopItemMessage;
import com.xgame.logic.server.game.alliance.message.ResGatherListMessage;
import com.xgame.logic.server.game.alliance.message.ResGetAllianceListMessage;
import com.xgame.logic.server.game.alliance.message.ResGetAllianceShopItemListMessage;
import com.xgame.logic.server.game.alliance.message.ResGetAllianceShopTreasureListMessage;
import com.xgame.logic.server.game.alliance.message.ResGetHelpeListMessage;
import com.xgame.logic.server.game.alliance.message.ResInviteMessage;
import com.xgame.logic.server.game.alliance.message.ResKickMemberMessage;
import com.xgame.logic.server.game.alliance.message.ResLevelupAllianceMessage;
import com.xgame.logic.server.game.alliance.message.ResMemberKickedMessage;
import com.xgame.logic.server.game.alliance.message.ResNewApplyMessage;
import com.xgame.logic.server.game.alliance.message.ResNotifyInviteMessage;
import com.xgame.logic.server.game.alliance.message.ResNotifyPermissionChangeMessage;
import com.xgame.logic.server.game.alliance.message.ResPlayerAllianceInfoMessage;
import com.xgame.logic.server.game.alliance.message.ResQueryAllianceMemeberMessage;
import com.xgame.logic.server.game.alliance.message.ResRankPlayerMessage;
import com.xgame.logic.server.game.alliance.message.ResRecommendPlayerMessage;
import com.xgame.logic.server.game.alliance.message.ResSearchAllianceMessage;
import com.xgame.logic.server.game.alliance.message.ResSendHelperMessage;
import com.xgame.logic.server.game.alliance.message.ResSendMessageMessage;
import com.xgame.logic.server.game.alliance.message.ResSetAutoJoinMessage;
import com.xgame.logic.server.game.alliance.message.ResSetOfficeMessage;
import com.xgame.logic.server.game.alliance.message.ResViewTeamMemberMessage;
import com.xgame.logic.server.game.allianceext.handler.ReqAllianceAcitivityHandler;
import com.xgame.logic.server.game.allianceext.handler.ReqAllianceBoxHandler;
import com.xgame.logic.server.game.allianceext.handler.ReqAllianceBuildListHandler;
import com.xgame.logic.server.game.allianceext.handler.ReqAllianceBuildLvHandler;
import com.xgame.logic.server.game.allianceext.handler.ReqAllianceExerciseHandler;
import com.xgame.logic.server.game.allianceext.handler.ReqAllianceScienceDonateHandler;
import com.xgame.logic.server.game.allianceext.handler.ReqAllianceScienceListHandler;
import com.xgame.logic.server.game.allianceext.handler.ReqArmyShopItemsHandler;
import com.xgame.logic.server.game.allianceext.handler.ReqAssignRewardHandler;
import com.xgame.logic.server.game.allianceext.handler.ReqChangeArmyShopItemHandler;
import com.xgame.logic.server.game.allianceext.handler.ReqCreateAllianceBuildHandler;
import com.xgame.logic.server.game.allianceext.handler.ReqMoveAllianceBuildHandler;
import com.xgame.logic.server.game.allianceext.handler.ReqPlayerAllianceExtHandler;
import com.xgame.logic.server.game.allianceext.handler.ReqRefreshAllianceBoxHandler;
import com.xgame.logic.server.game.allianceext.handler.ReqRewardActivityResourceHandler;
import com.xgame.logic.server.game.allianceext.handler.ReqRewardAllianceBoxHandler;
import com.xgame.logic.server.game.allianceext.handler.ReqStopAllianceExerciseHandler;
import com.xgame.logic.server.game.allianceext.handler.ReqViewAllianceExplorerHandler;
import com.xgame.logic.server.game.allianceext.handler.ReqViewRewardHandler;
import com.xgame.logic.server.game.allianceext.message.ReqAcitivityRankMessage;
import com.xgame.logic.server.game.allianceext.message.ReqAllianceAcitivityMessage;
import com.xgame.logic.server.game.allianceext.message.ReqAllianceBoxMessage;
import com.xgame.logic.server.game.allianceext.message.ReqAllianceBuildListMessage;
import com.xgame.logic.server.game.allianceext.message.ReqAllianceBuildLvMessage;
import com.xgame.logic.server.game.allianceext.message.ReqAllianceExerciseMessage;
import com.xgame.logic.server.game.allianceext.message.ReqAllianceScienceDonateMessage;
import com.xgame.logic.server.game.allianceext.message.ReqAllianceScienceListMessage;
import com.xgame.logic.server.game.allianceext.message.ReqArmyShopItemsMessage;
import com.xgame.logic.server.game.allianceext.message.ReqAssignRewardMessage;
import com.xgame.logic.server.game.allianceext.message.ReqChangeArmyShopItemMessage;
import com.xgame.logic.server.game.allianceext.message.ReqCreateAllianceBuildMessage;
import com.xgame.logic.server.game.allianceext.message.ReqMoveAllianceBuildMessage;
import com.xgame.logic.server.game.allianceext.message.ReqRefreshAllianceBoxMessage;
import com.xgame.logic.server.game.allianceext.message.ReqRewardActivityResourceMessage;
import com.xgame.logic.server.game.allianceext.message.ReqRewardAllianceBoxMessage;
import com.xgame.logic.server.game.allianceext.message.ReqStopAllianceExerciseMessage;
import com.xgame.logic.server.game.allianceext.message.ReqViewAllianceExplorerMessage;
import com.xgame.logic.server.game.allianceext.message.ReqViewRewardMessage;
import com.xgame.logic.server.game.allianceext.message.ResAcitivityRankMessage;
import com.xgame.logic.server.game.allianceext.message.ResAllianceAcitivityMessage;
import com.xgame.logic.server.game.allianceext.message.ResAllianceBoxMessage;
import com.xgame.logic.server.game.allianceext.message.ResAllianceBuildListMessage;
import com.xgame.logic.server.game.allianceext.message.ResAllianceBuildLvMessage;
import com.xgame.logic.server.game.allianceext.message.ResAllianceExerciseMessage;
import com.xgame.logic.server.game.allianceext.message.ResAllianceScienceDonateMessage;
import com.xgame.logic.server.game.allianceext.message.ResAllianceScienceListMessage;
import com.xgame.logic.server.game.allianceext.message.ResArmyShopItemsMessage;
import com.xgame.logic.server.game.allianceext.message.ResAssignRewardMessage;
import com.xgame.logic.server.game.allianceext.message.ResChangeArmyShopItemMessage;
import com.xgame.logic.server.game.allianceext.message.ResCreateAllianceBuildMessage;
import com.xgame.logic.server.game.allianceext.message.ResMoveAllianceBuildMessage;
import com.xgame.logic.server.game.allianceext.message.ResPlayerAllianceExtMessage;
import com.xgame.logic.server.game.allianceext.message.ResRewardAllianceBoxMessage;
import com.xgame.logic.server.game.allianceext.message.ResViewAllianceExplorerMessage;
import com.xgame.logic.server.game.allianceext.message.ResViewRewardMessage;
import com.xgame.logic.server.game.armshop.handler.ReqProductSoldierHandler;
import com.xgame.logic.server.game.armshop.message.ReqProductSoldierMessage;
import com.xgame.logic.server.game.armshop.message.ResCampCreateMessage;
import com.xgame.logic.server.game.armshop.message.ResCampDesitoryMessage;
import com.xgame.logic.server.game.armshop.message.ResCampGiveSoldierMessage;
import com.xgame.logic.server.game.armshop.message.ResCampInfoMessage;
import com.xgame.logic.server.game.armshop.message.ResProductSoldierMessage;
import com.xgame.logic.server.game.awardcenter.handler.ReqGetAwardHandler;
import com.xgame.logic.server.game.awardcenter.message.ReqGetAwardMessage;
import com.xgame.logic.server.game.awardcenter.message.ResAwardCenterMessage;
import com.xgame.logic.server.game.awardcenter.message.ResGetAwardMessage;
import com.xgame.logic.server.game.bag.handler.ReqPlayerBagHandler;
import com.xgame.logic.server.game.bag.handler.ReqUseItemHandler;
import com.xgame.logic.server.game.bag.message.ReqPlayerBagMessage;
import com.xgame.logic.server.game.bag.message.ReqUseItemMessage;
import com.xgame.logic.server.game.bag.message.ResBoxItemListMessage;
import com.xgame.logic.server.game.bag.message.ResItemListMessage;
import com.xgame.logic.server.game.bag.message.ResPlayerBagMessage;
import com.xgame.logic.server.game.buff.message.ResClearBuffMessage;
import com.xgame.logic.server.game.buff.message.ResPlayerBuffMessage;
import com.xgame.logic.server.game.chat.handler.ReqAddBanPlayerHandler;
import com.xgame.logic.server.game.chat.handler.ReqApplyAddRoomHandler;
import com.xgame.logic.server.game.chat.handler.ReqBuyTyphonHandler;
import com.xgame.logic.server.game.chat.handler.ReqCreateChatRoomHandler;
import com.xgame.logic.server.game.chat.handler.ReqDealRoomApplyHandler;
import com.xgame.logic.server.game.chat.handler.ReqEditChatRoomHandler;
import com.xgame.logic.server.game.chat.handler.ReqExitChatRoomHandler;
import com.xgame.logic.server.game.chat.handler.ReqKickChatRoomHandler;
import com.xgame.logic.server.game.chat.handler.ReqRankChatRoomHandler;
import com.xgame.logic.server.game.chat.handler.ReqRemoveBanPlayerHandler;
import com.xgame.logic.server.game.chat.handler.ReqRoomMessageHandler;
import com.xgame.logic.server.game.chat.handler.ReqSearchChatRoomHandler;
import com.xgame.logic.server.game.chat.handler.ReqSendChatHandler;
import com.xgame.logic.server.game.chat.handler.ReqSingleRoomMessageHandler;
import com.xgame.logic.server.game.chat.message.ReqAddBanPlayerMessage;
import com.xgame.logic.server.game.chat.message.ReqApplyAddRoomMessage;
import com.xgame.logic.server.game.chat.message.ReqBuyTyphonMessage;
import com.xgame.logic.server.game.chat.message.ReqCreateChatRoomMessage;
import com.xgame.logic.server.game.chat.message.ReqDealRoomApplyMessage;
import com.xgame.logic.server.game.chat.message.ReqEditChatRoomMessage;
import com.xgame.logic.server.game.chat.message.ReqExitChatRoomMessage;
import com.xgame.logic.server.game.chat.message.ReqKickChatRoomMessage;
import com.xgame.logic.server.game.chat.message.ReqRankChatRoomMessage;
import com.xgame.logic.server.game.chat.message.ReqRemoveBanPlayerMessage;
import com.xgame.logic.server.game.chat.message.ReqRoomMessageMessage;
import com.xgame.logic.server.game.chat.message.ReqSearchChatRoomMessage;
import com.xgame.logic.server.game.chat.message.ReqSendChatMessage;
import com.xgame.logic.server.game.chat.message.ReqSingleRoomMessageMessage;
import com.xgame.logic.server.game.chat.message.ResApplyAddRoomMessage;
import com.xgame.logic.server.game.chat.message.ResBuyTyphonMessage;
import com.xgame.logic.server.game.chat.message.ResLoginChatMessage;
import com.xgame.logic.server.game.chat.message.ResRankChatRoomMessage;
import com.xgame.logic.server.game.chat.message.ResReceiveChatMessage;
import com.xgame.logic.server.game.chat.message.ResRoomChangeMessage;
import com.xgame.logic.server.game.chat.message.ResRoomMessageMessage;
import com.xgame.logic.server.game.chat.message.ResSearchChatRoomMessage;
import com.xgame.logic.server.game.chat.message.ResSingleRoomMessageMessage;
import com.xgame.logic.server.game.commander.handler.ReqAddCPointCommanderHandler;
import com.xgame.logic.server.game.commander.handler.ReqChangeNameHandler;
import com.xgame.logic.server.game.commander.handler.ReqChangeStyleHandler;
import com.xgame.logic.server.game.commander.handler.ReqGetStatisticHandler;
import com.xgame.logic.server.game.commander.handler.ReqInsertEquitHandler;
import com.xgame.logic.server.game.commander.handler.ReqLookOtherCommanderHandler;
import com.xgame.logic.server.game.commander.handler.ReqResetTalentHandler;
import com.xgame.logic.server.game.commander.handler.ReqSwitchTanlentHandler;
import com.xgame.logic.server.game.commander.handler.ReqTalentAddPointHandler;
import com.xgame.logic.server.game.commander.handler.ReqUninstellEquitHandler;
import com.xgame.logic.server.game.commander.message.ReqAddCPointCommanderMessage;
import com.xgame.logic.server.game.commander.message.ReqChangeNameMessage;
import com.xgame.logic.server.game.commander.message.ReqChangeStyleMessage;
import com.xgame.logic.server.game.commander.message.ReqGetStatisticMessage;
import com.xgame.logic.server.game.commander.message.ReqInsertEquitMessage;
import com.xgame.logic.server.game.commander.message.ReqLookOtherCommanderMessage;
import com.xgame.logic.server.game.commander.message.ReqResetTalentMessage;
import com.xgame.logic.server.game.commander.message.ReqSwitchTanlentMessage;
import com.xgame.logic.server.game.commander.message.ReqTalentAddPointMessage;
import com.xgame.logic.server.game.commander.message.ReqUninstellEquitMessage;
import com.xgame.logic.server.game.commander.message.ResAddCPointCommanderMessage;
import com.xgame.logic.server.game.commander.message.ResChangeNameMessage;
import com.xgame.logic.server.game.commander.message.ResChangeStyleMessage;
import com.xgame.logic.server.game.commander.message.ResCommanderMessage;
import com.xgame.logic.server.game.commander.message.ResEnergyMessage;
import com.xgame.logic.server.game.commander.message.ResInsertEquitMessage;
import com.xgame.logic.server.game.commander.message.ResLevelUpCommanderMessage;
import com.xgame.logic.server.game.commander.message.ResOtherCommanderMessage;
import com.xgame.logic.server.game.commander.message.ResResetTalentMessage;
import com.xgame.logic.server.game.commander.message.ResStatisticMessage;
import com.xgame.logic.server.game.commander.message.ResSwitchTanlentMessage;
import com.xgame.logic.server.game.commander.message.ResTalentAddPointMessage;
import com.xgame.logic.server.game.commander.message.ResUninstellEquitMessage;
import com.xgame.logic.server.game.copy.handler.ReqCopyFightingHandler;
import com.xgame.logic.server.game.copy.handler.ReqCopyRaidHandler;
import com.xgame.logic.server.game.copy.handler.ReqGetRewardBoxHandler;
import com.xgame.logic.server.game.copy.handler.ReqQueryCopyInfoHandler;
import com.xgame.logic.server.game.copy.handler.ReqWarDefendDataHandler;
import com.xgame.logic.server.game.copy.message.ReqCopyFightingMessage;
import com.xgame.logic.server.game.copy.message.ReqCopyRaidMessage;
import com.xgame.logic.server.game.copy.message.ReqGetRewardBoxMessage;
import com.xgame.logic.server.game.copy.message.ReqQueryCopyInfoMessage;
import com.xgame.logic.server.game.copy.message.ReqWarDefendDataMessage;
import com.xgame.logic.server.game.copy.message.ResCopyFightingMessage;
import com.xgame.logic.server.game.copy.message.ResCopyRaidMessage;
import com.xgame.logic.server.game.copy.message.ResGetRewardBoxMessage;
import com.xgame.logic.server.game.copy.message.ResQueryCopyInfoMessage;
import com.xgame.logic.server.game.copy.message.ResWarDefendDataMessage;
import com.xgame.logic.server.game.country.handler.ReqBuildCustomWeaponTzHandler;
import com.xgame.logic.server.game.country.handler.ReqBuildingCollectHandler;
import com.xgame.logic.server.game.country.handler.ReqCreateBuildHandler;
import com.xgame.logic.server.game.country.handler.ReqDefensAutoSetupHandler;
import com.xgame.logic.server.game.country.handler.ReqDefensSolderSetupHandler;
import com.xgame.logic.server.game.country.handler.ReqGetRepositoryHandler;
import com.xgame.logic.server.game.country.handler.ReqInvestigateInfoHandler;
import com.xgame.logic.server.game.country.handler.ReqLevelUpBuildHandler;
import com.xgame.logic.server.game.country.handler.ReqLevelUpTechHandler;
import com.xgame.logic.server.game.country.handler.ReqMoveBuildHandler;
import com.xgame.logic.server.game.country.handler.ReqRadarHandler;
import com.xgame.logic.server.game.country.handler.ReqRecoveryArmorHandler;
import com.xgame.logic.server.game.country.handler.ReqRemoveObstructHandler;
import com.xgame.logic.server.game.country.handler.ReqRewardRepositoryHandler;
import com.xgame.logic.server.game.country.handler.ReqSpeedRecoveryArmorHandler;
import com.xgame.logic.server.game.country.handler.ReqSwitchMineCarHandler;
import com.xgame.logic.server.game.country.handler.ReqUnDefensSolderSetupHandler;
import com.xgame.logic.server.game.country.handler.ResResourceHandler;
import com.xgame.logic.server.game.country.message.ReqBuildingCollectMessage;
import com.xgame.logic.server.game.country.message.ReqCreateBuildMessage;
import com.xgame.logic.server.game.country.message.ReqGetRepositoryMessage;
import com.xgame.logic.server.game.country.message.ReqLevelUpBuildMessage;
import com.xgame.logic.server.game.country.message.ReqMoveBuildMessage;
import com.xgame.logic.server.game.country.message.ReqRemoveObstructMessage;
import com.xgame.logic.server.game.country.message.ReqRewardRepositoryMessage;
import com.xgame.logic.server.game.country.message.ReqSwitchMineCarMessage;
import com.xgame.logic.server.game.country.message.ResAllCountryMessage;
import com.xgame.logic.server.game.country.message.ResCreateBuildMessage;
import com.xgame.logic.server.game.country.message.ResLevelUpBuildMessage;
import com.xgame.logic.server.game.country.message.ResMineCarBuildingMessage;
import com.xgame.logic.server.game.country.message.ResMineRepositoryMessage;
import com.xgame.logic.server.game.country.message.ResMoveBuildMessage;
import com.xgame.logic.server.game.country.message.ResRemoveBuildingMessage;
import com.xgame.logic.server.game.country.message.ResSwitchCarMessage;
import com.xgame.logic.server.game.country.message.ResUpdataCountryMessage;
import com.xgame.logic.server.game.cross.handler.ReqCrossLoginHandler;
import com.xgame.logic.server.game.cross.message.ReqCrossLoginMessage;
import com.xgame.logic.server.game.customweanpon.handler.ReqChangeDesignMapHandler;
import com.xgame.logic.server.game.customweanpon.handler.ReqCreateDesignMapHandler;
import com.xgame.logic.server.game.customweanpon.message.ReqBuildCustomWeaponTzMessage;
import com.xgame.logic.server.game.customweanpon.message.ReqChangeDesignMapMessage;
import com.xgame.logic.server.game.customweanpon.message.ReqCreateDesignMapMessage;
import com.xgame.logic.server.game.customweanpon.message.ResChangeDesignMapMessage;
import com.xgame.logic.server.game.customweanpon.message.ResCreateDesignMapMessage;
import com.xgame.logic.server.game.customweanpon.message.ResDesignMapUpdateMessage;
import com.xgame.logic.server.game.customweanpon.message.ResPartListMessage;
import com.xgame.logic.server.game.customweanpon.message.ResReBuildCustomWeaponTzMessage;
import com.xgame.logic.server.game.defensesoldier.message.ReqDefensAutoSetupMessage;
import com.xgame.logic.server.game.defensesoldier.message.ReqDefensSolderSetupMessage;
import com.xgame.logic.server.game.defensesoldier.message.ReqUnDefensSolderSetupMessage;
import com.xgame.logic.server.game.defensesoldier.message.ResDefensAutoSetupMessage;
import com.xgame.logic.server.game.defensesoldier.message.ResDefensSolderMessage;
import com.xgame.logic.server.game.defensesoldier.message.ResDefensSolderSetupMessage;
import com.xgame.logic.server.game.defensesoldier.message.ResUnDefensSolderSetupMessage;
import com.xgame.logic.server.game.duplicate.handler.ReqGetDupChapterInfoHandler;
import com.xgame.logic.server.game.duplicate.handler.ReqGetDupNoteInfoHandler;
import com.xgame.logic.server.game.duplicate.handler.ReqRewardBoxHandler;
import com.xgame.logic.server.game.duplicate.message.ReqGetDupChapterInfoMessage;
import com.xgame.logic.server.game.duplicate.message.ReqGetDupNoteInfoMessage;
import com.xgame.logic.server.game.duplicate.message.ReqRewardBoxMessage;
import com.xgame.logic.server.game.duplicate.message.ResDupChapterInfoMessage;
import com.xgame.logic.server.game.duplicate.message.ResDupChapterInfoUpdateMessage;
import com.xgame.logic.server.game.duplicate.message.ResDupNoteInfoMessage;
import com.xgame.logic.server.game.duplicate.message.ResRewardBoxMessage;
import com.xgame.logic.server.game.email.handler.ReqDeleteEmailHandler;
import com.xgame.logic.server.game.email.handler.ReqNewEmailFlagHandler;
import com.xgame.logic.server.game.email.handler.ReqQueryAllEmailHandler;
import com.xgame.logic.server.game.email.handler.ReqReadEmailHandler;
import com.xgame.logic.server.game.email.handler.ReqSaveEmailHandler;
import com.xgame.logic.server.game.email.handler.ReqSendEmailHandler;
import com.xgame.logic.server.game.email.message.ReqDeleteEmailMessage;
import com.xgame.logic.server.game.email.message.ReqNewEmailFlagMessage;
import com.xgame.logic.server.game.email.message.ReqQueryAllEmailMessage;
import com.xgame.logic.server.game.email.message.ReqReadEmailMessage;
import com.xgame.logic.server.game.email.message.ReqSaveEmailMessage;
import com.xgame.logic.server.game.email.message.ReqSendEmailMessage;
import com.xgame.logic.server.game.email.message.ResDeleteEmailMessage;
import com.xgame.logic.server.game.email.message.ResNewEmailFlagMessage;
import com.xgame.logic.server.game.email.message.ResNewEmailMessage;
import com.xgame.logic.server.game.email.message.ResQueryAllEmailMessage;
import com.xgame.logic.server.game.email.message.ResSaveEmailMessage;
import com.xgame.logic.server.game.email.message.ResSendEmailMessage;
import com.xgame.logic.server.game.equipment.handler.ReqBuyFragmentHandler;
import com.xgame.logic.server.game.equipment.handler.ReqBuySpecialEquipmentHandler;
import com.xgame.logic.server.game.equipment.handler.ReqCancelProduceFragmentHandler;
import com.xgame.logic.server.game.equipment.handler.ReqComposeEquipmentHandler;
import com.xgame.logic.server.game.equipment.handler.ReqComposeFragmentHandler;
import com.xgame.logic.server.game.equipment.handler.ReqDecomposeEquipmentHandler;
import com.xgame.logic.server.game.equipment.handler.ReqLevelUpEquipmentQualityHandler;
import com.xgame.logic.server.game.equipment.handler.ReqProduceFragmentHandler;
import com.xgame.logic.server.game.equipment.handler.ReqUnlockProducePositionHandler;
import com.xgame.logic.server.game.equipment.handler.ReqUnlockedHandler;
import com.xgame.logic.server.game.equipment.message.ReqBuyFragmentMessage;
import com.xgame.logic.server.game.equipment.message.ReqBuySpecialEquipmentMessage;
import com.xgame.logic.server.game.equipment.message.ReqCancelProduceFragmentMessage;
import com.xgame.logic.server.game.equipment.message.ReqComposeEquipmentMessage;
import com.xgame.logic.server.game.equipment.message.ReqComposeFragmentMessage;
import com.xgame.logic.server.game.equipment.message.ReqDecomposeEquipmentMessage;
import com.xgame.logic.server.game.equipment.message.ReqLevelUpEquipmentQualityMessage;
import com.xgame.logic.server.game.equipment.message.ReqProduceFragmentMessage;
import com.xgame.logic.server.game.equipment.message.ReqUnlockProducePositionMessage;
import com.xgame.logic.server.game.equipment.message.ReqUnlockedMessage;
import com.xgame.logic.server.game.equipment.message.ResBuildEquipmentResultMessage;
import com.xgame.logic.server.game.equipment.message.ResBuyFragmentMessage;
import com.xgame.logic.server.game.equipment.message.ResBuySpecialEquipmentResultMessage;
import com.xgame.logic.server.game.equipment.message.ResCancelFragmentResultMessage;
import com.xgame.logic.server.game.equipment.message.ResComposeEquipmentMessage;
import com.xgame.logic.server.game.equipment.message.ResDecomposeEquipmentMessage;
import com.xgame.logic.server.game.equipment.message.ResEquipmentBuidingResultMessage;
import com.xgame.logic.server.game.equipment.message.ResEquipmentBuildingInfoMessage;
import com.xgame.logic.server.game.equipment.message.ResFragmentComposeResultMessage;
import com.xgame.logic.server.game.equipment.message.ResLevelUpEquipmentQualityResultMessage;
import com.xgame.logic.server.game.equipment.message.ResModifyEquipmentResultMessage;
import com.xgame.logic.server.game.equipment.message.ResOutputEquipmentMessage;
import com.xgame.logic.server.game.equipment.message.ResProduceFragmentMessage;
import com.xgame.logic.server.game.equipment.message.ResProducingEquipmentMessage;
import com.xgame.logic.server.game.equipment.message.ResTakeOffEquipmentResultMessage;
import com.xgame.logic.server.game.equipment.message.ResUnlockResultMessage;
import com.xgame.logic.server.game.equipment.message.ResWearEquipmentResultMessage;
import com.xgame.logic.server.game.friend.handler.ReqAddBlackListHandler;
import com.xgame.logic.server.game.friend.handler.ReqAddFriendHandler;
import com.xgame.logic.server.game.friend.handler.ReqDeleteBlackListHandler;
import com.xgame.logic.server.game.friend.handler.ReqDeleteFriendHandler;
import com.xgame.logic.server.game.friend.handler.ReqGetReleationHandler;
import com.xgame.logic.server.game.friend.handler.ReqSearchPlayerHandler;
import com.xgame.logic.server.game.friend.message.ReqAddBlackListMessage;
import com.xgame.logic.server.game.friend.message.ReqAddFriendMessage;
import com.xgame.logic.server.game.friend.message.ReqDeleteBlackListMessage;
import com.xgame.logic.server.game.friend.message.ReqDeleteFriendMessage;
import com.xgame.logic.server.game.friend.message.ReqGetReleationMessage;
import com.xgame.logic.server.game.friend.message.ReqSearchPlayerMessage;
import com.xgame.logic.server.game.friend.message.ResAddBlackListMessage;
import com.xgame.logic.server.game.friend.message.ResAddFriendMessage;
import com.xgame.logic.server.game.friend.message.ResDeleteBlackListMessage;
import com.xgame.logic.server.game.friend.message.ResDeleteFriendMessage;
import com.xgame.logic.server.game.friend.message.ResGetFriendMessage;
import com.xgame.logic.server.game.friend.message.ResSearchPlayerMessage;
import com.xgame.logic.server.game.gameevent.handler.ReqEventDetailHandler;
import com.xgame.logic.server.game.gameevent.handler.ReqEventListHandler;
import com.xgame.logic.server.game.gameevent.handler.ReqHistoryRankHandler;
import com.xgame.logic.server.game.gameevent.message.ReqEventDetailMessage;
import com.xgame.logic.server.game.gameevent.message.ReqEventListMessage;
import com.xgame.logic.server.game.gameevent.message.ReqHistoryRankMessage;
import com.xgame.logic.server.game.gameevent.message.ResEventDetailMessage;
import com.xgame.logic.server.game.gameevent.message.ResEventListMessage;
import com.xgame.logic.server.game.gameevent.message.ResHistoryRankMessage;
import com.xgame.logic.server.game.modify.handler.ReqDestorySoldierHandler;
import com.xgame.logic.server.game.modify.handler.ReqReformSoldierHandler;
import com.xgame.logic.server.game.modify.message.ReqDestorySoldierMessage;
import com.xgame.logic.server.game.modify.message.ReqRecoveryArmorMessage;
import com.xgame.logic.server.game.modify.message.ReqReformSoldierMessage;
import com.xgame.logic.server.game.modify.message.ReqSpeedRecoveryArmorMessage;
import com.xgame.logic.server.game.modify.message.ResArmorsInfoMessage;
import com.xgame.logic.server.game.modify.message.ResDestorySoldierMessage;
import com.xgame.logic.server.game.modify.message.ResRecoveringArmorMessage;
import com.xgame.logic.server.game.modify.message.ResReformSoldierMessage;
import com.xgame.logic.server.game.modify.message.ResRepairAndReformSoldierMessage;
import com.xgame.logic.server.game.notice.message.ResNoticeMessage;
import com.xgame.logic.server.game.notice.message.ResTitleMessage;
import com.xgame.logic.server.game.player.handler.ReqCheckPlayerNameHandler;
import com.xgame.logic.server.game.player.handler.ReqLoginHandler;
import com.xgame.logic.server.game.player.handler.ReqSimplePlayerHandler;
import com.xgame.logic.server.game.player.handler.ReqTimeSystemHandler;
import com.xgame.logic.server.game.player.message.ReqCheckPlayerNameMessage;
import com.xgame.logic.server.game.player.message.ReqLoginMessage;
import com.xgame.logic.server.game.player.message.ReqSimplePlayerMessage;
import com.xgame.logic.server.game.player.message.ReqTimeSystemMessage;
import com.xgame.logic.server.game.player.message.ResCheckPlayerNameMessage;
import com.xgame.logic.server.game.player.message.ResCurrencyMessage;
import com.xgame.logic.server.game.player.message.ResSimplePlayerMessage;
import com.xgame.logic.server.game.player.message.ResTimeSystemMessage;
import com.xgame.logic.server.game.player.message.ResUserInfoMessage;
import com.xgame.logic.server.game.playerattribute.handler.ReqAttrbutesAddFromHandler;
import com.xgame.logic.server.game.playerattribute.handler.ReqAttrbutesAddHandler;
import com.xgame.logic.server.game.playerattribute.message.ReqAttrbutesAddFromMessage;
import com.xgame.logic.server.game.playerattribute.message.ReqAttrbutesAddMessage;
import com.xgame.logic.server.game.playerattribute.message.ResAttrbutesAddFromMessage;
import com.xgame.logic.server.game.playerattribute.message.ResAttrbutesAddMessage;
import com.xgame.logic.server.game.radar.message.ReqInvestigateInfoMessage;
import com.xgame.logic.server.game.radar.message.ReqRadarMessage;
import com.xgame.logic.server.game.radar.message.ResInvestigateInfoMessage;
import com.xgame.logic.server.game.radar.message.ResRadarMessage;
import com.xgame.logic.server.game.radar.message.ResRadarTimeEndMessage;
import com.xgame.logic.server.game.repair.handler.ReqRepairHandler;
import com.xgame.logic.server.game.repair.message.ReqRepairSoldierMessage;
import com.xgame.logic.server.game.repair.message.ResModSoldierMessage;
import com.xgame.logic.server.game.repair.message.ResRepairSoldierMessage;
import com.xgame.logic.server.game.shop.handler.ReqBuyItemHandler;
import com.xgame.logic.server.game.shop.message.ReqBuyItemMessage;
import com.xgame.logic.server.game.shop.message.ResBatchInfoMessage;
import com.xgame.logic.server.game.shop.message.ResBuyItemMessage;
import com.xgame.logic.server.game.shop.message.ResShopInfoMessage;
import com.xgame.logic.server.game.soldier.handler.ReqOtherPlayerSoldierInfoHandler;
import com.xgame.logic.server.game.soldier.message.ReqOtherPlayerSoldierInfoMessage;
import com.xgame.logic.server.game.soldier.message.ResOtherPlayerSoldierInfoMessage;
import com.xgame.logic.server.game.soldier.message.ResSoldierMessage;
import com.xgame.logic.server.game.soldier.message.ResUpdateSoldierMessage;
import com.xgame.logic.server.game.task.handler.ReqDayTaskHandler;
import com.xgame.logic.server.game.task.handler.ReqGetAchieveRewardHandler;
import com.xgame.logic.server.game.task.handler.ReqGetTimeRefreshTaskRewardHandler;
import com.xgame.logic.server.game.task.handler.ReqMedalTaskInfoHandler;
import com.xgame.logic.server.game.task.handler.ReqOpenTimeRefreshTaskHandler;
import com.xgame.logic.server.game.task.handler.ReqQueryActiveInfoHandler;
import com.xgame.logic.server.game.task.handler.ReqQueryTaskInfoHandler;
import com.xgame.logic.server.game.task.handler.ReqResetTaskHandler;
import com.xgame.logic.server.game.task.handler.ReqRewardActiveBoxHandler;
import com.xgame.logic.server.game.task.handler.ReqRewardTaskInfoHandler;
import com.xgame.logic.server.game.task.handler.ReqRewardTimeTaskHandler;
import com.xgame.logic.server.game.task.handler.ReqUseTaskMedalHandler;
import com.xgame.logic.server.game.task.handler.ResTaskMedalHandler;
import com.xgame.logic.server.game.task.message.ReqDayTaskMessage;
import com.xgame.logic.server.game.task.message.ReqGetAchieveRewardMessage;
import com.xgame.logic.server.game.task.message.ReqGetTimeRefreshTaskRewardMessage;
import com.xgame.logic.server.game.task.message.ReqMedalTaskInfoMessage;
import com.xgame.logic.server.game.task.message.ReqOpenTimeRefreshTaskMessage;
import com.xgame.logic.server.game.task.message.ReqQueryActiveInfoMessage;
import com.xgame.logic.server.game.task.message.ReqQueryTaskInfoMessage;
import com.xgame.logic.server.game.task.message.ReqResetTaskMessage;
import com.xgame.logic.server.game.task.message.ReqRewardActiveBoxMessage;
import com.xgame.logic.server.game.task.message.ReqRewardTaskInfoMessage;
import com.xgame.logic.server.game.task.message.ReqRewardTimeTaskMessage;
import com.xgame.logic.server.game.task.message.ReqUseTaskMedalMessage;
import com.xgame.logic.server.game.task.message.ResDayTaskMessage;
import com.xgame.logic.server.game.task.message.ResGetAchieveRewardMessage;
import com.xgame.logic.server.game.task.message.ResGetTimeRefreshTaskRewardMessage;
import com.xgame.logic.server.game.task.message.ResMedalTaskInfoMessage;
import com.xgame.logic.server.game.task.message.ResOpenTimeRefreshTaskMessage;
import com.xgame.logic.server.game.task.message.ResPushActiveTaskMessage;
import com.xgame.logic.server.game.task.message.ResPushBaseTaskMessage;
import com.xgame.logic.server.game.task.message.ResQueryActiveInfoMessage;
import com.xgame.logic.server.game.task.message.ResQueryTaskInfoMessage;
import com.xgame.logic.server.game.task.message.ResResetTaskMessage;
import com.xgame.logic.server.game.task.message.ResRewardActiveBoxMessage;
import com.xgame.logic.server.game.task.message.ResRewardTaskInfoMessage;
import com.xgame.logic.server.game.task.message.ResRewardTimeTaskMessage;
import com.xgame.logic.server.game.task.message.ResTaskMedalMessage;
import com.xgame.logic.server.game.task.message.ResUseTaskMedalMessage;
import com.xgame.logic.server.game.tech.message.ReqLevelUpTechMessage;
import com.xgame.logic.server.game.tech.message.ResAllTechMessage;
import com.xgame.logic.server.game.tech.message.ResLevelUpTechMessage;
import com.xgame.logic.server.game.timertask.handler.ReqBuildingForFreeHandler;
import com.xgame.logic.server.game.timertask.handler.ReqCancelTimerTaskHandler;
import com.xgame.logic.server.game.timertask.handler.ReqDiamondSpeedHandler;
import com.xgame.logic.server.game.timertask.handler.ReqUseSpeedItemHandler;
import com.xgame.logic.server.game.timertask.message.ReqBuildingForFreeMessage;
import com.xgame.logic.server.game.timertask.message.ReqCancelTimerTaskMessage;
import com.xgame.logic.server.game.timertask.message.ReqDiamondSpeedMessage;
import com.xgame.logic.server.game.timertask.message.ReqUseSpeedItemMessage;
import com.xgame.logic.server.game.timertask.message.ResAllTimerMessage;
import com.xgame.logic.server.game.vip.message.ResVipMessage;
import com.xgame.logic.server.game.war.handler.ReqWarDataHandler;
import com.xgame.logic.server.game.war.handler.ReqWarEndHandler;
import com.xgame.logic.server.game.war.handler.ReqWarSearchHandler;
import com.xgame.logic.server.game.war.message.ReqWarDataMessage;
import com.xgame.logic.server.game.war.message.ReqWarEndMessage;
import com.xgame.logic.server.game.war.message.ReqWarSearchMessage;
import com.xgame.logic.server.game.war.message.ResWarDataMessage;
import com.xgame.logic.server.game.war.message.ResWarSearchMessage;
import com.xgame.logic.server.game.warehouse.message.ResResourceMessage;
import com.xgame.logic.server.game.world.handler.ReqAllianceLeaderSpriteInfoHandler;
import com.xgame.logic.server.game.world.handler.ReqCancelTeamAttackHandler;
import com.xgame.logic.server.game.world.handler.ReqCollectListHandler;
import com.xgame.logic.server.game.world.handler.ReqDefensePlayerDeailHandler;
import com.xgame.logic.server.game.world.handler.ReqDeleteHandler;
import com.xgame.logic.server.game.world.handler.ReqEnterWorldMapHandler;
import com.xgame.logic.server.game.world.handler.ReqGetTeamBattleListHandler;
import com.xgame.logic.server.game.world.handler.ReqLookTroopInfoHandler;
import com.xgame.logic.server.game.world.handler.ReqMarchHandler;
import com.xgame.logic.server.game.world.handler.ReqMoveCityHandler;
import com.xgame.logic.server.game.world.handler.ReqMyCityDefenseHandler;
import com.xgame.logic.server.game.world.handler.ReqRefusePlayerDefenseHandler;
import com.xgame.logic.server.game.world.handler.ReqSendTeamBattleNoticeHandler;
import com.xgame.logic.server.game.world.handler.ReqServerInfoHandler;
import com.xgame.logic.server.game.world.handler.ReqSpriteInfoHandler;
import com.xgame.logic.server.game.world.handler.ReqTroopBackHandler;
import com.xgame.logic.server.game.world.handler.ReqUseTroopSpeedUpPropHandler;
import com.xgame.logic.server.game.world.handler.ReqUserTroopListHandler;
import com.xgame.logic.server.game.world.handler.ReqViewBattleTeamDetailHandler;
import com.xgame.logic.server.game.world.handler.ReqViewMarchHandler;
import com.xgame.logic.server.game.world.handler.ReqViewSpriteHandler;
import com.xgame.logic.server.game.world.message.ReqAddCollectMessage;
import com.xgame.logic.server.game.world.message.ReqCancelTeamAttackMessage;
import com.xgame.logic.server.game.world.message.ReqCollectListMessage;
import com.xgame.logic.server.game.world.message.ReqDefensePlayerDeailMessage;
import com.xgame.logic.server.game.world.message.ReqDeleteMessage;
import com.xgame.logic.server.game.world.message.ReqEnterWorldMapMessage;
import com.xgame.logic.server.game.world.message.ReqGetTeamBattleListMessage;
import com.xgame.logic.server.game.world.message.ReqLookTroopInfoMessage;
import com.xgame.logic.server.game.world.message.ReqMarchMessage;
import com.xgame.logic.server.game.world.message.ReqMoveCityMessage;
import com.xgame.logic.server.game.world.message.ReqMyCityDefenseMessage;
import com.xgame.logic.server.game.world.message.ReqRefusePlayerDefenseMessage;
import com.xgame.logic.server.game.world.message.ReqSendTeamBattleNotcieMessage;
import com.xgame.logic.server.game.world.message.ReqServerInfoMessage;
import com.xgame.logic.server.game.world.message.ReqSpriteInfoMessage;
import com.xgame.logic.server.game.world.message.ReqTroopBackMessage;
import com.xgame.logic.server.game.world.message.ReqUseTroopSpeedUpPropMessage;
import com.xgame.logic.server.game.world.message.ReqUserTroopListMessage;
import com.xgame.logic.server.game.world.message.ReqViewBattleTeamDetailMessage;
import com.xgame.logic.server.game.world.message.ReqViewMarchMessage;
import com.xgame.logic.server.game.world.message.ReqViewSpriteMessage;
import com.xgame.logic.server.game.world.message.ResAddCollectMessage;
import com.xgame.logic.server.game.world.message.ResAllianceLeaderSpriteInfoMessage;
import com.xgame.logic.server.game.world.message.ResCancelTeamAttackMessage;
import com.xgame.logic.server.game.world.message.ResCityStateMessage;
import com.xgame.logic.server.game.world.message.ResCollectListMessage;
import com.xgame.logic.server.game.world.message.ResDefensePlayerDeailMessage;
import com.xgame.logic.server.game.world.message.ResDeleteMessage;
import com.xgame.logic.server.game.world.message.ResGetTeamBattleListMessage;
import com.xgame.logic.server.game.world.message.ResLookTroopInfoMessage;
import com.xgame.logic.server.game.world.message.ResMarchMessage;
import com.xgame.logic.server.game.world.message.ResMoveCityMessage;
import com.xgame.logic.server.game.world.message.ResMyCityDefenseMessage;
import com.xgame.logic.server.game.world.message.ResQueryTerritoryMessage;
import com.xgame.logic.server.game.world.message.ResRefusePlayerDefenseMessage;
import com.xgame.logic.server.game.world.message.ResServerInfoMessage;
import com.xgame.logic.server.game.world.message.ResSpriteInfoMessage;
import com.xgame.logic.server.game.world.message.ResTroopBackMessage;
import com.xgame.logic.server.game.world.message.ResUpdateSpriteMessage;
import com.xgame.logic.server.game.world.message.ResUpdateVectorInfoMessage;
import com.xgame.logic.server.game.world.message.ResUseTroopSpeedUpPropMessage;
import com.xgame.logic.server.game.world.message.ResUserTroopListMessage;
import com.xgame.logic.server.game.world.message.ResViewBattleTeamDetailMessage;
import com.xgame.logic.server.game.world.message.ResViewMarchMessage;
import com.xgame.logic.server.game.world.message.ResViewSpriteMessage;
import com.xgame.logic.server.game.world.message.ResViewWorldTerritoryMessage;
import com.xgame.logic.server.gm.handler.ReqGMHandler;
import com.xgame.logic.server.gm.message.ReqGMMessage;

/**
 *
 *2016-11-28  21:36:00
 *@author ye.yuan
 *
 */
public enum MessageEnum {

	/**
	 * 客户端请求消息和处理器
	 */
	Req100200(100,200,ReqCreateBuildHandler.class,ReqCreateBuildMessage.class),
	Req100203(100,203,ReqRemoveObstructHandler.class,ReqRemoveObstructMessage.class),
	Req100204(100,204,ReqLevelUpBuildHandler.class,ReqLevelUpBuildMessage.class),
	Req100205(100,205,ReqBuildingCollectHandler.class,ReqBuildingCollectMessage.class),
	Req100206(100,206,ReqMoveBuildHandler.class,ReqMoveBuildMessage.class),
	Req100207(100,207,ReqSwitchMineCarHandler.class,ReqSwitchMineCarMessage.class),
	Req100208(100,208,ReqRewardRepositoryHandler.class,ReqRewardRepositoryMessage.class),
	Req100209(100,209,ReqGetRepositoryHandler.class,ReqGetRepositoryMessage.class),
	Req101201(101,201,ReqProductSoldierHandler.class,ReqProductSoldierMessage.class),
	Req102201(102,201,ReqOtherPlayerSoldierInfoHandler.class,ReqOtherPlayerSoldierInfoMessage.class),
	Req103200(103,200,ReqGMHandler.class,ReqGMMessage.class),
	Req105201(105,201,ReqRepairHandler.class,ReqRepairSoldierMessage.class),
	Req107200(107,200,ReqLevelUpTechHandler.class,ReqLevelUpTechMessage.class),
	Req108200(108,200,ReqCancelTimerTaskHandler.class,ReqCancelTimerTaskMessage.class),
	Req108201(108,201,ReqUseSpeedItemHandler.class,ReqUseSpeedItemMessage.class),
	Req108202(108,202,ReqDiamondSpeedHandler.class,ReqDiamondSpeedMessage.class),
	Req108203(108,203,ReqBuildingForFreeHandler.class,ReqBuildingForFreeMessage.class),
	Req109200(109,200,ReqUseItemHandler.class,ReqUseItemMessage.class),
	Req109201(109,201,ReqPlayerBagHandler.class,ReqPlayerBagMessage.class),
	Req111201(111,201,ReqAttrbutesAddHandler.class,ReqAttrbutesAddMessage.class),
	Req111202(111,202,ReqAttrbutesAddFromHandler.class,ReqAttrbutesAddFromMessage.class),
	Req120200(120,200,ReqBuyItemHandler.class,ReqBuyItemMessage.class),
	Req121200(121,200,ReqEnterWorldMapHandler.class,ReqEnterWorldMapMessage.class),
	Req121201(121,201,ReqViewSpriteHandler.class,ReqViewSpriteMessage.class),
	Req121202(121,202,ReqViewMarchHandler.class,ReqViewMarchMessage.class),
	Req121203(121,203,ReqSpriteInfoHandler.class,ReqSpriteInfoMessage.class),
	Req121204(121,204,ReqMarchHandler.class,ReqMarchMessage.class),
	Req121205(121,205,ReqLookTroopInfoHandler.class,ReqLookTroopInfoMessage.class),
	Req121221(121,221,ReqUseTroopSpeedUpPropHandler.class,ReqUseTroopSpeedUpPropMessage.class),
	Req121222(121,222,ReqTroopBackHandler.class,ReqTroopBackMessage.class),
	Req121223(121,223,ReqUserTroopListHandler.class,ReqUserTroopListMessage.class),
	Req121224(121,224,ReqAllianceLeaderSpriteInfoHandler.class,ReqAddCollectMessage.class),
	Req121225(121,225,ReqCollectListHandler.class,ReqCollectListMessage.class),
	Req121226(121,226,ReqDeleteHandler.class,ReqDeleteMessage.class),
	Req121228(121,228,ReqMoveCityHandler.class,ReqMoveCityMessage.class),
	Req121229(121,229,ReqGetTeamBattleListHandler.class,ReqGetTeamBattleListMessage.class),
	Req121230(121,230,ReqViewBattleTeamDetailHandler.class,ReqViewBattleTeamDetailMessage.class),
	Req121231(121,231,ReqMyCityDefenseHandler.class,ReqMyCityDefenseMessage.class),
	Req121232(121,232,ReqDefensePlayerDeailHandler.class,ReqDefensePlayerDeailMessage.class),
	Req121233(121,233,ReqCancelTeamAttackHandler.class,ReqCancelTeamAttackMessage.class),
	Req121234(121,234,ReqRefusePlayerDefenseHandler.class,ReqRefusePlayerDefenseMessage.class),
	Req121235(121,235,ReqSendTeamBattleNoticeHandler.class,ReqSendTeamBattleNotcieMessage.class),
	Req121251(121,251,ReqServerInfoHandler.class,ReqServerInfoMessage.class),
	Req130204(130,204,ReqGetStatisticHandler.class,ReqGetStatisticMessage.class),
	Req130300(130,300,ReqChangeNameHandler.class,ReqChangeNameMessage.class),
	Req130301(130,301,ReqChangeStyleHandler.class,ReqChangeStyleMessage.class),
	Req130302(130,302,ReqInsertEquitHandler.class,ReqInsertEquitMessage.class),
	Req130303(130,303,ReqUninstellEquitHandler.class,ReqUninstellEquitMessage.class),
	Req130304(130,304,ReqTalentAddPointHandler.class,ReqTalentAddPointMessage.class),
	Req130305(130,305,ReqResetTalentHandler.class,ReqResetTalentMessage.class),
	Req130308(130,308,ReqLookOtherCommanderHandler.class,ReqLookOtherCommanderMessage.class),
	Req130309(130,309,ReqSwitchTanlentHandler.class,ReqSwitchTanlentMessage.class),
	Req130310(130,310,ReqAddCPointCommanderHandler.class,ReqAddCPointCommanderMessage.class),
	Req132300(132,300,ReqDefensSolderSetupHandler.class,ReqDefensSolderSetupMessage.class),
	Req132301(132,301,ReqDefensAutoSetupHandler.class,ReqDefensAutoSetupMessage.class),
	Req132302(132,302,ReqUnDefensSolderSetupHandler.class,ReqUnDefensSolderSetupMessage.class),
	Req133100(133,100,ResResourceHandler.class,ResResourceMessage.class),
	Req200301(200,301,ReqGetAwardHandler.class,ReqGetAwardMessage.class),
	Req201107(201,107,ResTaskMedalHandler.class,ResTaskMedalMessage.class),
	Req201201(201,201,ReqQueryTaskInfoHandler.class,ReqQueryTaskInfoMessage.class),
	Req201202(201,202,ReqRewardTaskInfoHandler.class,ReqRewardTaskInfoMessage.class),
	Req201203(201,203,ReqQueryActiveInfoHandler.class,ReqQueryActiveInfoMessage.class),
	Req201204(201,204,ReqRewardActiveBoxHandler.class,ReqRewardActiveBoxMessage.class),
	Req201205(201,205,ReqMedalTaskInfoHandler.class,ReqMedalTaskInfoMessage.class),
	Req201206(201,206,ReqUseTaskMedalHandler.class,ReqUseTaskMedalMessage.class),
	Req201208(201,208,ReqDayTaskHandler.class,ReqDayTaskMessage.class),
	Req201209(201,209,ReqRewardTimeTaskHandler.class,ReqRewardTimeTaskMessage.class),
	Req201210(201,210,ReqResetTaskHandler.class,ReqResetTaskMessage.class),
	Req201211(201,211,ReqOpenTimeRefreshTaskHandler.class,ReqOpenTimeRefreshTaskMessage.class),
	Req201212(201,212,ReqGetTimeRefreshTaskRewardHandler.class,ReqGetTimeRefreshTaskRewardMessage.class),
	Req201215(201,215,ReqGetAchieveRewardHandler.class,ReqGetAchieveRewardMessage.class),
	Req300200(300,200,ReqComposeEquipmentHandler.class,ReqComposeEquipmentMessage.class),
	Req300201(300,201,ReqDecomposeEquipmentHandler.class,ReqDecomposeEquipmentMessage.class),
	Req300202(300,202,ReqProduceFragmentHandler.class,ReqProduceFragmentMessage.class),
	Req300203(300,203,ReqBuyFragmentHandler.class,ReqBuyFragmentMessage.class),
	Req300204(300,204,ReqCancelProduceFragmentHandler.class,ReqCancelProduceFragmentMessage.class),
	Req300205(300,205,ReqUnlockProducePositionHandler.class,ReqUnlockProducePositionMessage.class),
	Req300206(300,206,ReqUnlockedHandler.class,ReqUnlockedMessage.class),
	Req300207(300,207,ReqLevelUpEquipmentQualityHandler.class,ReqLevelUpEquipmentQualityMessage.class),
	Req300208(300,208,ReqComposeFragmentHandler.class,ReqComposeFragmentMessage.class),
	Req300209(300,209,ReqBuySpecialEquipmentHandler.class,ReqBuySpecialEquipmentMessage.class),
	Req301201(301,201,ReqRadarHandler.class,ReqRadarMessage.class),
	Req301301(301,301,ReqInvestigateInfoHandler.class,ReqInvestigateInfoMessage.class),
	Req410201(410,201,ReqQueryAllEmailHandler.class,ReqQueryAllEmailMessage.class),
	Req410202(410,202,ReqSendEmailHandler.class,ReqSendEmailMessage.class),
	Req410203(410,203,ReqDeleteEmailHandler.class,ReqDeleteEmailMessage.class),
	Req410204(410,204,ReqReadEmailHandler.class,ReqReadEmailMessage.class),
	Req410205(410,205,ReqSaveEmailHandler.class,ReqSaveEmailMessage.class),
	Req410206(410,206,ReqNewEmailFlagHandler.class,ReqNewEmailFlagMessage.class),
	Req500201(500,201,ReqWarSearchHandler.class,ReqWarSearchMessage.class),
	Req500202(500,202,ReqWarDataHandler.class,ReqWarDataMessage.class),
	Req500203(500,203,ReqWarEndHandler.class,ReqWarEndMessage.class),
	Req600200(600,200,ReqBuildCustomWeaponTzHandler.class,ReqBuildCustomWeaponTzMessage.class),
	Req600201(600,201,ReqCreateDesignMapHandler.class,ReqCreateDesignMapMessage.class),
	Req600202(600,202,ReqChangeDesignMapHandler.class,ReqChangeDesignMapMessage.class),
	Req601201(601,201,ReqDestorySoldierHandler.class,ReqDestorySoldierMessage.class),
	Req601202(601,202,ReqReformSoldierHandler.class,ReqReformSoldierMessage.class),
	Req601203(601,203,ReqRecoveryArmorHandler.class,ReqRecoveryArmorMessage.class),
	Req601204(601,204,ReqSpeedRecoveryArmorHandler.class,ReqSpeedRecoveryArmorMessage.class),
	Req1002201(1002,201,ReqSendChatHandler.class,ReqSendChatMessage.class),
	Req1002202(1002,202,ReqCreateChatRoomHandler.class,ReqCreateChatRoomMessage.class),
	Req1002203(1002,203,ReqApplyAddRoomHandler.class,ReqApplyAddRoomMessage.class),
	Req1002204(1002,204,ReqExitChatRoomHandler.class,ReqExitChatRoomMessage.class),
	Req1002205(1002,205,ReqDealRoomApplyHandler.class,ReqDealRoomApplyMessage.class),
	Req1002206(1002,206,ReqKickChatRoomHandler.class,ReqKickChatRoomMessage.class),
	Req1002207(1002,207,ReqEditChatRoomHandler.class,ReqEditChatRoomMessage.class),
	Req1002208(1002,208,ReqRankChatRoomHandler.class,ReqRankChatRoomMessage.class),
	Req1002209(1002,209,ReqSearchChatRoomHandler.class,ReqSearchChatRoomMessage.class),
	Req1002211(1002,211,ReqRoomMessageHandler.class,ReqRoomMessageMessage.class),
	Req1002212(1002,212,ReqSingleRoomMessageHandler.class,ReqSingleRoomMessageMessage.class),
	Req1002213(1002,213,ReqAddBanPlayerHandler.class,ReqAddBanPlayerMessage.class),
	Req1002214(1002,214,ReqRemoveBanPlayerHandler.class,ReqRemoveBanPlayerMessage.class),
	Req1002215(1002,215,ReqBuyTyphonHandler.class,ReqBuyTyphonMessage.class),
	Req1003202(1003,202,ReqSimplePlayerHandler.class,ReqSimplePlayerMessage.class),
	Req1003203(1003,203,ReqTimeSystemHandler.class,ReqTimeSystemMessage.class),
	Req1003204(1003,204,ReqLoginHandler.class,ReqLoginMessage.class),
	Req1003300(1003,300,ReqCheckPlayerNameHandler.class,ReqCheckPlayerNameMessage.class),
	Req1005201(1005,201,ReqCrossLoginHandler.class,ReqCrossLoginMessage.class),
	Req1006201(1006,201,ReqAddFriendHandler.class,ReqAddFriendMessage.class),
	Req1006202(1006,202,ReqGetReleationHandler.class,ReqGetReleationMessage.class),
	Req1006203(1006,203,ReqDeleteFriendHandler.class,ReqDeleteFriendMessage.class),
	Req1006204(1006,204,ReqAddBlackListHandler.class,ReqAddBlackListMessage.class),
	Req1006205(1006,205,ReqDeleteBlackListHandler.class,ReqDeleteBlackListMessage.class),
	Req1006206(1006,206,ReqSearchPlayerHandler.class,ReqSearchPlayerMessage.class),
	Req1007201(1007,201,ReqSearchAllianceHandler.class,ReqSearchAllianceMessage.class),
	Req1007202(1007,202,ReqApplyAllianceHandler.class,ReqApplyAllianceMessage.class),
	Req1007204(1007,204,ReqGetAllianceListHandler.class,ReqGetAllianceListMessage.class),
	Req1007205(1007,205,ReqCreateAllianceHandler.class,ReqCreateAllianceMessage.class),
	Req1007206(1007,206,ReqPlayerAllianceInfoHandler.class,ReqPlayerAllianceInfoMessage.class),
	Req1007207(1007,207,ReqCancelApplyHandler.class,ReqCancelApplyMessage.class),
	Req1007208(1007,208,ReqInviteHandler.class,ReqInviteMessage.class),
	Req1007210(1007,210,ReqDealInviteHandler.class,ReqDealInviteMessage.class),
	Req1007211(1007,211,ReqLevelupAllianceHandler.class,ReqLevelupAllianceMessage.class),
	Req1007212(1007,212,ReqDonateHandler.class,ReqDonateMessage.class),
	Req1007213(1007,213,ReqAllianceOfficeHandler.class,ReqAllianceOfficeMessage.class),
	Req1007215(1007,215,ReqAddTeamMemberHandler.class,ReqAddTeamMemberMessage.class),
	Req1007216(1007,216,ReqDeleteTeamMemberHandler.class,ReqDeleteTeamMemberMessage.class),
	Req1007217(1007,217,ReqEditTeamHandler.class,ReqEditTeamMessage.class),
	Req1007218(1007,218,ReqChangeLeaderHandler.class,ReqChangeLeaderMessage.class),
	Req1007219(1007,219,ReqKickMemberHandler.class,ReqKickMemberMessage.class),
	Req1007221(1007,221,ReqEditTitleHandler.class,ReqEditTitleMessage.class),
	Req1007222(1007,222,ReqEditAllianceHandler.class,ReqEditAllianceMessage.class),
	Req1007223(1007,223,ReqRecommendPlayerHandler.class,ReqRecommendPlayerMessage.class),
	Req1007225(1007,225,ReqSendMessageHandler.class,ReqSendMessageMessage.class),
	Req1007226(1007,226,ReqApplyListHandler.class,ReqApplyListMessage.class),
	Req1007227(1007,227,ReqDealApplyHandler.class,ReqDealApplyMessage.class),
	Req1007228(1007,228,ReqGatherListHandler.class,ReqGatherListMessage.class),
	Req1007229(1007,229,ReqDefendListHandler.class,ReqDefendListMessage.class),
	Req1007230(1007,230,ReqAllianceFightListHandler.class,ReqAllianceFightListMessage.class),
	Req1007231(1007,231,ReqSendHelperHandler.class,ReqSendHelperMessage.class),
	Req1007232(1007,232,ReqGetHelpeListHandler.class,ReqGetHelpeListMessage.class),
	Req1007233(1007,233,ReqDoneHelpHandler.class,ReqDoneHelpMessage.class),
	Req1007234(1007,234,ReqDismissAllianceHandler.class,ReqDismissAllianceMessage.class),
	Req1007235(1007,235,ReqQueryAllianceMemeberHandler.class,ReqQueryAllianceMemeberMessage.class),
	Req1007236(1007,236,ReqViewTeamMemberHandler.class,ReqViewTeamMemberMessage.class),
	Req1007237(1007,237,ReqHelpAllHandler.class,ReqHelpAllMessage.class),
	Req1007238(1007,238,ReqChangeRankHandler.class,ReqChangeRankMessage.class),
	Req1007239(1007,239,ReqSetOfficeHandler.class,ReqSetOfficeMessage.class),
	Req1007240(1007,240,ReqRankPlayerHandler.class,ReqRankPlayerMessage.class),
	Req1007241(1007,241,ReqEditPermissionHandler.class,ReqEditPermissionMessage.class),
	Req1007244(1007,244,ReqSetAutoJoinHandler.class,ReqSetAutoJoinMessage.class),
	Req1007245(1007,245,ReqDimissOfficeHandler.class,ReqDimissOfficeMessage.class),
	Req1007250(1007,250,ReqGetAllianceShopItemListHandler.class,ReqGetAllianceShopItemListMessage.class),
	Req1007251(1007,251,ReqExchangeAllianceShopItemHandler.class,ReqExchangeAllianceShopItemMessage.class),
	Req1007252(1007,252,ReqGetAllianceShopTreasureListHandler.class,ReqGetAllianceShopTreasureListMessage.class),
	Req1007261(1007,261,ReqTrade2PlayerHandler.class,ReqTrade2PlayerMessage.class),
	Req1007262(1007,262,ReqGetLeaguePlayersHandler.class,ReqGetLeaguePlayersMessage.class),
	Req1009201(1009,201,ReqEventListHandler.class,ReqEventListMessage.class),
	Req1009202(1009,202,ReqEventDetailHandler.class,ReqEventDetailMessage.class),
	Req1009203(1009,203,ReqHistoryRankHandler.class,ReqHistoryRankMessage.class),
	Req1210200(1210,200,ReqAllianceBuildListHandler.class,ReqAllianceBuildListMessage.class),
	Req1210201(1210,201,ReqCreateAllianceBuildHandler.class,ReqCreateAllianceBuildMessage.class),
	Req1210202(1210,202,ReqMoveAllianceBuildHandler.class,ReqMoveAllianceBuildMessage.class),
	Req1210203(1210,203,ReqAllianceBuildLvHandler.class,ReqAllianceBuildLvMessage.class),
	Req1210204(1210,204,ReqAllianceScienceListHandler.class,ReqAllianceScienceListMessage.class),
	Req1210206(1210,206,ReqAllianceScienceDonateHandler.class,ReqAllianceScienceDonateMessage.class),
	Req1210207(1210,207,ReqAllianceExerciseHandler.class,ReqAllianceExerciseMessage.class),
	Req1210208(1210,208,ReqStopAllianceExerciseHandler.class,ReqStopAllianceExerciseMessage.class),
	Req1210209(1210,209,ReqViewAllianceExplorerHandler.class,ReqViewAllianceExplorerMessage.class),
	Req1210210(1210,210,ReqAllianceAcitivityHandler.class,ReqAllianceAcitivityMessage.class),
	Req1210211(1210,211,ReqPlayerAllianceExtHandler.class,ReqAcitivityRankMessage.class),
	Req1210212(1210,212,ReqRewardActivityResourceHandler.class,ReqRewardActivityResourceMessage.class),
	Req1210213(1210,213,ReqAllianceBoxHandler.class,ReqAllianceBoxMessage.class),
	Req1210214(1210,214,ReqRewardAllianceBoxHandler.class,ReqRewardAllianceBoxMessage.class),
	Req1210215(1210,215,ReqRefreshAllianceBoxHandler.class,ReqRefreshAllianceBoxMessage.class),
	Req1210216(1210,216,ReqViewRewardHandler.class,ReqViewRewardMessage.class),
	Req1210217(1210,217,ReqAssignRewardHandler.class,ReqAssignRewardMessage.class),
	Req1210218(1210,218,ReqArmyShopItemsHandler.class,ReqArmyShopItemsMessage.class),
	Req1210219(1210,219,ReqChangeArmyShopItemHandler.class,ReqChangeArmyShopItemMessage.class),
	Req2017201(2017,201,ReqGetDupChapterInfoHandler.class,ReqGetDupChapterInfoMessage.class),
	Req2017202(2017,202,ReqGetDupNoteInfoHandler.class,ReqGetDupNoteInfoMessage.class),
	Req2017203(2017,203,ReqRewardBoxHandler.class,ReqRewardBoxMessage.class),
	Req2100201(2100,201,ReqQueryCopyInfoHandler.class,ReqQueryCopyInfoMessage.class),
	Req2100202(2100,202,ReqCopyFightingHandler.class,ReqCopyFightingMessage.class),
	Req2100203(2100,203,ReqGetRewardBoxHandler.class,ReqGetRewardBoxMessage.class),
	Req2100204(2100,204,ReqCopyRaidHandler.class,ReqCopyRaidMessage.class),
	Req2100205(2100,205,ReqWarDefendDataHandler.class,ReqWarDefendDataMessage.class),
	
	
	/**
	 * 发送给客户端的消息
	 */
	Res100100(100,100,ResCreateBuildMessage.class),
	Res100101(100,101,ResAllCountryMessage.class),
	Res100102(100,102,ResLevelUpBuildMessage.class),
	Res100103(100,103,ResRemoveBuildingMessage.class),
	Res100104(100,104,ResMineCarBuildingMessage.class),
	Res100105(100,105,ResMineRepositoryMessage.class),
	Res100107(100,107,ResSwitchCarMessage.class),
	Res100108(100,108,ResMoveBuildMessage.class),
	Res100109(100,109,ResUpdataCountryMessage.class),
	Res101101(101,101,ResProductSoldierMessage.class),
	Res101102(101,102,ResCampGiveSoldierMessage.class),
	Res101103(101,103,ResCampCreateMessage.class),
	Res101104(101,104,ResCampInfoMessage.class),
	Res101400(101,400,ResCampDesitoryMessage.class),
	Res102100(102,100,ResSoldierMessage.class),
	Res102101(102,101,ResOtherPlayerSoldierInfoMessage.class),
	Res102150(102,150,ResUpdateSoldierMessage.class),
	Res105100(105,100,ResModSoldierMessage.class),
	Res105101(105,101,ResRepairSoldierMessage.class),
	Res107100(107,100,ResAllTechMessage.class),
	Res107101(107,101,ResLevelUpTechMessage.class),
	Res108100(108,100,ResAllTimerMessage.class),
	Res109100(109,100,ResPlayerBagMessage.class),
	Res109101(109,101,ResItemListMessage.class),
	Res109102(109,102,ResBoxItemListMessage.class),
	Res111101(111,101,ResAttrbutesAddMessage.class),
	Res111102(111,102,ResAttrbutesAddFromMessage.class),
	Res120100(120,100,ResShopInfoMessage.class),
	Res120101(120,101,ResBatchInfoMessage.class),
	Res120102(120,102,ResBuyItemMessage.class),
	Res121101(121,101,ResViewSpriteMessage.class),
	Res121102(121,102,ResViewMarchMessage.class),
	Res121103(121,103,ResSpriteInfoMessage.class),
	Res121104(121,104,ResMarchMessage.class),
	Res121105(121,105,ResLookTroopInfoMessage.class),
	Res121106(121,106,ResUpdateVectorInfoMessage.class),
	Res121107(121,107,ResUpdateSpriteMessage.class),
	Res121121(121,121,ResUseTroopSpeedUpPropMessage.class),
	Res121122(121,122,ResTroopBackMessage.class),
	Res121123(121,123,ResUserTroopListMessage.class),
	Res121124(121,124,ResAddCollectMessage.class),
	Res121125(121,125,ResCollectListMessage.class),
	Res121126(121,126,ResDeleteMessage.class),
	Res121127(121,127,ResCityStateMessage.class),
	Res121128(121,128,ResMoveCityMessage.class),
	Res121129(121,129,ResGetTeamBattleListMessage.class),
	Res121130(121,130,ResViewBattleTeamDetailMessage.class),
	Res121131(121,131,ResMyCityDefenseMessage.class),
	Res121132(121,132,ResDefensePlayerDeailMessage.class),
	Res121133(121,133,ResCancelTeamAttackMessage.class),
	Res121134(121,134,ResRefusePlayerDefenseMessage.class),
	Res121136(121,136,ResAllianceLeaderSpriteInfoMessage.class),
	Res121151(121,151,ResServerInfoMessage.class),
	Res121161(121,161,ResQueryTerritoryMessage.class),
	Res121162(121,162,ResViewWorldTerritoryMessage.class),
	Res130100(130,100,ResCommanderMessage.class),
	Res130102(130,102,ResLevelUpCommanderMessage.class),
	Res130103(130,103,ResEnergyMessage.class),
	Res130104(130,104,ResStatisticMessage.class),
	Res130400(130,400,ResChangeNameMessage.class),
	Res130401(130,401,ResChangeStyleMessage.class),
	Res130402(130,402,ResInsertEquitMessage.class),
	Res130403(130,403,ResUninstellEquitMessage.class),
	Res130404(130,404,ResTalentAddPointMessage.class),
	Res130405(130,405,ResResetTalentMessage.class),
	Res130408(130,408,ResOtherCommanderMessage.class),
	Res130409(130,409,ResSwitchTanlentMessage.class),
	Res130410(130,410,ResAddCPointCommanderMessage.class),
	Res132100(132,100,ResDefensSolderMessage.class),
	Res132400(132,400,ResDefensSolderSetupMessage.class),
	Res132401(132,401,ResDefensAutoSetupMessage.class),
	Res132402(132,402,ResUnDefensSolderSetupMessage.class),
	Res200100(200,100,ResAwardCenterMessage.class),
	Res200401(200,401,ResGetAwardMessage.class),
	Res201101(201,101,ResQueryTaskInfoMessage.class),
	Res201102(201,102,ResRewardTaskInfoMessage.class),
	Res201103(201,103,ResQueryActiveInfoMessage.class),
	Res201104(201,104,ResRewardActiveBoxMessage.class),
	Res201105(201,105,ResMedalTaskInfoMessage.class),
	Res201106(201,106,ResUseTaskMedalMessage.class),
	Res201108(201,108,ResDayTaskMessage.class),
	Res201109(201,109,ResRewardTimeTaskMessage.class),
	Res201110(201,110,ResResetTaskMessage.class),
	Res201111(201,111,ResOpenTimeRefreshTaskMessage.class),
	Res201112(201,112,ResGetTimeRefreshTaskRewardMessage.class),
	Res201113(201,113,ResPushBaseTaskMessage.class),
	Res201114(201,114,ResPushActiveTaskMessage.class),
	Res201115(201,115,ResGetAchieveRewardMessage.class),
	Res300100(300,100,ResComposeEquipmentMessage.class),
	Res300101(300,101,ResDecomposeEquipmentMessage.class),
	Res300102(300,102,ResProduceFragmentMessage.class),
	Res300103(300,103,ResBuyFragmentMessage.class),
	Res300104(300,104,ResCancelFragmentResultMessage.class),
	Res300105(300,105,ResUnlockResultMessage.class),
	Res300106(300,106,ResModifyEquipmentResultMessage.class),
	Res300107(300,107,ResBuildEquipmentResultMessage.class),
	Res300108(300,108,ResLevelUpEquipmentQualityResultMessage.class),
	Res300109(300,109,ResFragmentComposeResultMessage.class),
	Res300110(300,110,ResBuySpecialEquipmentResultMessage.class),
	Res300111(300,111,ResWearEquipmentResultMessage.class),
	Res300112(300,112,ResTakeOffEquipmentResultMessage.class),
	Res300113(300,113,ResEquipmentBuidingResultMessage.class),
	Res300114(300,114,ResProducingEquipmentMessage.class),
	Res300115(300,115,ResEquipmentBuildingInfoMessage.class),
	Res300116(300,116,ResOutputEquipmentMessage.class),
	Res301101(301,101,ResRadarMessage.class),
	Res301102(301,102,ResRadarTimeEndMessage.class),
	Res301401(301,401,ResInvestigateInfoMessage.class),
	Res410100(410,100,ResNewEmailMessage.class),
	Res410101(410,101,ResQueryAllEmailMessage.class),
	Res410102(410,102,ResSendEmailMessage.class),
	Res410103(410,103,ResDeleteEmailMessage.class),
	Res410105(410,105,ResSaveEmailMessage.class),
	Res410106(410,106,ResNewEmailFlagMessage.class),
	Res500101(500,101,ResWarSearchMessage.class),
	Res500102(500,102,ResWarDataMessage.class),
	Res600100(600,100,ResReBuildCustomWeaponTzMessage.class),
	Res600101(600,101,ResCreateDesignMapMessage.class),
	Res600102(600,102,ResChangeDesignMapMessage.class),
	Res600103(600,103,ResPartListMessage.class),
	Res600104(600,104,ResDesignMapUpdateMessage.class),
	Res601101(601,101,ResDestorySoldierMessage.class),
	Res601102(601,102,ResReformSoldierMessage.class),
	Res601103(601,103,ResRepairAndReformSoldierMessage.class),
	Res601104(601,104,ResArmorsInfoMessage.class),
	Res601106(601,106,ResRecoveringArmorMessage.class),
	Res1001100(1001,100,ResPlayerBuffMessage.class),
	Res1001101(1001,101,ResClearBuffMessage.class),
	Res1002101(1002,101,ResReceiveChatMessage.class),
	Res1002104(1002,104,ResApplyAddRoomMessage.class),
	Res1002102(1002,102,ResRoomChangeMessage.class),
	Res1002103(1002,103,ResApplyAddRoomMessage.class),
	Res1002108(1002,108,ResRankChatRoomMessage.class),
	Res1002110(1002,110,ResLoginChatMessage.class),
	Res1002113(1002,113,ResRoomChangeMessage.class),
	Res1002114(1002,114,ResSingleRoomMessageMessage.class),
	Res1002118(1002,118,ResBuyTyphonMessage.class),
	Res1002115(1002,115,ResBuyTyphonMessage.class),
	Res1003100(1003,100,ResCurrencyMessage.class),
	Res1003101(1003,101,ResUserInfoMessage.class),
	Res1003102(1003,102,ResSimplePlayerMessage.class),
	Res1003103(1003,103,ResTimeSystemMessage.class),
	Res1003400(1003,400,ResCheckPlayerNameMessage.class),
	Res1004100(1004,100,ResTitleMessage.class),
	Res1004101(1004,101,ResNoticeMessage.class),
	Res1006101(1006,101,ResAddFriendMessage.class),
	Res1006102(1006,102,ResGetFriendMessage.class),
	Res1006103(1006,103,ResDeleteFriendMessage.class),
	Res1006104(1006,104,ResAddBlackListMessage.class),
	Res1006105(1006,105,ResDeleteBlackListMessage.class),
	Res1006106(1006,106,ResSearchPlayerMessage.class),
	Res1007100(1007,100,ResAllianceDataMessage.class),
	Res1007101(1007,101,ResSearchAllianceMessage.class),
	Res1007102(1007,102,ResApplyAllianceMessage.class),
	Res1007103(1007,103,ResNewApplyMessage.class),
	Res1007104(1007,104,ResGetAllianceListMessage.class),
	Res1007105(1007,105,ResCreateAllianceMessage.class),
	Res1007106(1007,106,ResPlayerAllianceInfoMessage.class),
	Res1007107(1007,107,ResCancelApplyMessage.class),
	Res1007108(1007,108,ResInviteMessage.class),
	Res1007109(1007,109,ResNotifyInviteMessage.class),
	Res1007110(1007,110,ResDealInviteMessage.class),
	Res1007111(1007,111,ResLevelupAllianceMessage.class),
	Res1007112(1007,112,ResDonateMessage.class),
	Res1007113(1007,113,ResAllianceOfficeMessage.class),
	Res1007115(1007,115,ResAddTeamMemberMessage.class),
	Res1007116(1007,116,ResDeleteTeamMemberMessage.class),
	Res1007117(1007,117,ResEditTeamMessage.class),
	Res1007118(1007,118,ResChangeLeaderMessage.class),
	Res1007119(1007,119,ResKickMemberMessage.class),
	Res1007120(1007,120,ResMemberKickedMessage.class),
	Res1007121(1007,121,ResEditTitleMessage.class),
	Res1007122(1007,122,ResEditAllianceMessage.class),
	Res1007123(1007,123,ResRecommendPlayerMessage.class),
	Res1007125(1007,125,ResSendMessageMessage.class),
	Res1007126(1007,126,ResApplyListMessage.class),
	Res1007127(1007,127,ResDealApplyMessage.class),
	Res1007128(1007,128,ResGatherListMessage.class),
	Res1007129(1007,129,ResDefendListMessage.class),
	Res1007130(1007,130,ResAllianceFightListMessage.class),
	Res1007131(1007,131,ResSendHelperMessage.class),
	Res1007132(1007,132,ResGetHelpeListMessage.class),
	Res1007133(1007,133,ResDoneHelpMessage.class),
	Res1007134(1007,134,ResDismissAllianceMessage.class),
	Res1007135(1007,135,ResQueryAllianceMemeberMessage.class),
	Res1007136(1007,136,ResViewTeamMemberMessage.class),
	Res1007138(1007,138,ResChangeRankMessage.class),
	Res1007139(1007,139,ResSetOfficeMessage.class),
	Res1007140(1007,140,ResRankPlayerMessage.class),
	Res1007141(1007,141,ResEditPermissionMessage.class),
	Res1007142(1007,142,ResNotifyPermissionChangeMessage.class),
	Res1007143(1007,143,ResAllianceHelpUpdateMessage.class),
	Res1007144(1007,144,ResSetAutoJoinMessage.class),
	Res1007145(1007,145,ResDimissOfficeMessage.class),
	Res1007150(1007,150,ResGetAllianceShopItemListMessage.class),
	Res1007151(1007,151,ResExchangeAllianceShopItemMessage.class),
	Res1007152(1007,152,ResGetAllianceShopTreasureListMessage.class),
	Res1007160(1007,160,ResAllLeaguePlayerMessage.class),
	Res1007161(1007,161,ResAllianceHelpDeleteMessage.class),
	Res1007199(1007,199,ResAllianceChangeMessage.class),
	Res1008100(1008,100,ResVipMessage.class),
	Res1009101(1009,101,ResEventListMessage.class),
	Res1009102(1009,102,ResEventDetailMessage.class),
	Res1009103(1009,103,ResHistoryRankMessage.class),
	Res1210100(1210,100,ResAllianceBuildListMessage.class),
	Res1210101(1210,101,ResCreateAllianceBuildMessage.class),
	Res1210102(1210,102,ResMoveAllianceBuildMessage.class),
	Res1210103(1210,103,ResAllianceBuildLvMessage.class),
	Res1210104(1210,104,ResAllianceScienceListMessage.class),
	Res1210105(1210,105,ResPlayerAllianceExtMessage.class),
	Res1210106(1210,106,ResAllianceScienceDonateMessage.class),
	Res1210107(1210,107,ResAllianceExerciseMessage.class),
	Res1210109(1210,109,ResViewAllianceExplorerMessage.class),
	Res1210110(1210,110,ResAllianceAcitivityMessage.class),
	Res1210111(1210,111,ResAcitivityRankMessage.class),
	Res1210113(1210,113,ResAllianceBoxMessage.class),
	Res1210114(1210,114,ResRewardAllianceBoxMessage.class),
	Res1210116(1210,116,ResViewRewardMessage.class),
	Res1210117(1210,117,ResAssignRewardMessage.class),
	Res1210118(1210,118,ResArmyShopItemsMessage.class),
	Res1210119(1210,119,ResChangeArmyShopItemMessage.class),
	Res2017101(2017,101,ResDupChapterInfoMessage.class),
	Res2017102(2017,102,ResDupNoteInfoMessage.class),
	Res2017103(2017,103,ResRewardBoxMessage.class),
	Res2017104(2017,104,ResDupChapterInfoUpdateMessage.class),
	Res2100101(2100,101,ResQueryCopyInfoMessage.class),
	Res2100102(2100,102,ResCopyFightingMessage.class),
	Res2100103(2100,103,ResGetRewardBoxMessage.class),
	Res2100104(2100,104,ResCopyRaidMessage.class),
	Res2100105(2100,105,ResWarDefendDataMessage.class),
	;
	
	private MessageEnum(int functionId,int id,Class<?> handlerClass,Class<?> messageClass) {}
	private MessageEnum(int functionId,int id,Class<?> messageClass) {}
}
