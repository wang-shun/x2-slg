local this = SLG_ResourceDefine;


SLG_ResourcePath = {

	moduleuiPath = "Assets/GameAssets/Prefab/ModuleUI/",

	commonPath = "Assets/GameAssets/Prefab/CommonUI/",

	worldMapPath = "Assets/GameAssets/Prefab/WorldMap/",

	homeCampPath = "Assets/GameAssets/Prefab/HomeCamp/",

	-- homeCampBuildingsPath = "Assets/GameAssets/Prefab/HomeCamp/Buildings/",
	homeCampBuildingsPath = "Assets/GameAssets/Prefab/HomeCamp/BuildingModel/",

	homeWarningAreaPath = "Assets/GameAssets/Prefab/HomeCamp/quyu/",

	BuildingPath = "Assets/GameAssets/Prefab/Buildings/",

	configPath = "Assets/GameAssets/Config/",

	ziJianConfigPath = "Assets/GameAssets/Config/zijian/",

	weaponAssemblyPath = "Assets/GameAssets/Prefab/WeaponAssembly/",

	texturePath = "Assets/GameAssets/Texture/UI/Loading/",
	UITexturePath= "Assets/GameAssets/Texture/UI/",

    effectPath = "Assets/GameAssets/Prefab/Effect/",

    fightEffectPath = "Assets/GameAssets/Prefab/Effect/Fight/",

    weaponTexturePath = "Assets/GameAssets/Texture/Icon/CustomWeapon/",

    commonTexturePath = "Assets/GameAssets/Texture/Icon/Common/",

    uiEffectPath = "Assets/GameAssets/Prefab/Effect/UIEffect/",

	-- 地区旗帜
	regionFlagTexturePath = "Assets/GameAssets/Texture/Icon/League/",
	headTexturePath = "Assets/GameAssets/Texture/Icon/Head/",
	--mainui各个功能模块的texture
	mainuiTexturePath="Assets/GameAssets/Texture/",
	-- 图像

	soundPath = "Assets/GameAssets/Audio/",



	uiPath = "Assets/GameAssets/Prefab/UI/",
};

SLG_ResourceName = {
	mapRootName = "MapRoot.prefab",
	-------野外地图
	homeRootName = "GameHomeBase.prefab",
	-------家园地图
	homeBuildingName = "junying.prefab",
	--------主基地  信息、功能
	homeBaseFuncView = "HomeBase/HomeBaseFuncView.prefab",

	--------科技馆 UI
	techlogyFuncView = "Techlogy/TechlogyScisearchView.prefab",
	techlogySecondView = "Techlogy/TechResearchSecondView.prefab",
	techlogyThirdView = "Techlogy/TechResearchThirdView.prefab",

	--------兵工厂
	armsFactoryFuncView = "UI_ArmsFactory/ArmsFactoryFuncView.prefab",
	armsFactoryFuncSecondView = "UI_ArmsFactory/ArmsFactoryFuncSecondView.prefab",
	armsFactoryDiamondSpeedView = "UI_ArmsFactory/ArmsFactoryDiamondSpeedView.prefab",


	--------植入体实验室
	equipmentView = "Equipment/EquipmentView.prefab",
	equipmentTypeView = "Equipment/EquipmentTypeView.prefab",
	equipmentProduceView = "Equipment/EquipmentProduceView.prefab",
	equipmentPackageView = "Equipment/EquipmentPackageView.prefab",
	equipmentComposeView = "Equipment/EquipmentComposeView.prefab",
	equipmentFragmentComposeView = "Equipment/EquipmentFragmentComposeView.prefab",
	equipmentFragmentProduceView = "Equipment/EquipmentFragmentProduceView.prefab",
	takeOffEquipmentView = "Equipment/TakeOffEquipmentView.prefab",

	-------------------勘探开发院子
	prospectView = "Prospect/ProspectView.prefab",
	speedUpItemView = "CommonUI/CommonUseResItem/MiningVehicleSpeedUpView.prefab",

	----------自建武器ui
	customWeaponMainView = "CustomWeapon/CustomWeaponMainView.prefab",
	customWeaponDPView = "CustomWeapon/CustomWeaponDPView.prefab",
	customWeaponAssemblyView = "CustomWeapon/CustomWeaponAssemblyView.prefab",
	customWeaponIcon = "CustomWeapon/WeapnTUIcon.prefab",
	customWeaponTuView = "CustomWeapon/TuSelectOperView.prefab",
	customWeaponEditView = "CustomWeapon/WeaponEditView.prefab",
	customWeaponChangeView = "CustomWeapon/CustomWeaponChangeView.prefab",
	customWeaponSuccView = "CustomWeapon/CustomWeaponSuccView.prefab",
	customWeaponIconSmall = "CustomWeapon/WeapnTUIconSmall.prefab",

	---------修理厂
	repairFactoryFuncView = "UI_RepairFactory/RepairFactory.prefab",

	---------贸易站
	tradePlayersView = "Trade/TradePlayersView.prefab",
	tradeView = "Trade/TradeView.prefab",
	---------野外
	wildEmptyInfoView = "WildMap/WildEmptyInfoView.prefab",
	wildResLeagueInfoView = "WildMap/WildResLeagueInfoView.prefab",
	wildResMyInfoView = "WildMap/WildResMyInfoView.prefab",
	wildResNormalInfoView = "WildMap/WildResNormalInfoView.prefab",
	wildResOtherInfoView = "WildMap/WildResOtherInfoView.prefab",
	wildRoleInfoView = "WildMap/WildRoleInfoView.prefab",
	--------------------common
	CommonItemIcon = "CommonUI/CommonItemIcon.prefab",
	CommonHeadIcon = "CommonUI/CommonHeadIcon.prefab",
	CommonArmIcon = "CommonUI/CommonArmIcon.prefab",

	-- 邮件界面
	emailViewName = "Email/EmailView.prefab",
	commEmailContent = "Email/EmailContent.prefab",
	writeEmailContent = "Email/WriteEmailView.prefab",
	replayEmailContent = "Email/ReplayEmailView.prefab",



	-- 改装厂
	refittingFactoryView = "RefittingFactory/RefittingFactoryView.prefab",
	refittingDestroyView = "RefittingFactory/DestroyView.prefab",
	refittingItem = "RefittingFactory/RefittingItem.prefab",
	refittingOperItem = "RefittingFactory/RefittingOperItem.prefab",
	ReformItem = "RefittingFactory/ReformItem.prefab",


	-- UI界面的特效
	composeEquipmentEffect = "composeEquipmentEffect.prefab",
	composeMaterialBgEffect = "composeMaterialBgEffect.prefab",
	composeMaterialConsumeEffect = "composeMaterialConsumeEffect.prefab",
	equipmentBuildingEffect = "equipmentBuildingEffect.prefab",
	equipmentPersonLighting = "equipmentPersonLighting.prefab",

	---------------------------config-------------------------
	baseResUVTex = "SLGResTex",						--地表層，surface這個層（建築、山、樹林）
	baseBlockUVTex = "SLGMapTex",					--地層，groudID這個圖集。（湖泊、沙漠、草地）
	baseSurfaceUVTex = "SLGMapSurfaceTex",			--地表上層（在第表層上面）

	itemsConfig = "items",
	-- 物品配置
	activeConfig = "active",
	serverConfig = "server",
	-- 活跃度配置
	activeRewardsConfig = "activeRewards",
	-- 活跃度奖励配置
	terrainConfig = "terrain",
	-- 地形配置
	baseBlockConfig = "MapSLGBaseBlock",
	ziYuanDianConfig = "ziYuanDian",
	homeBuildingConfig = "building",
	-- 建筑配置
	surfaceBlockConfig = "SurfaceBlock",
	scienceConfig = "science",
	-- 科技馆配置
	peijianConfig = "peiJian",
	-- 配件配置
	peijianTabConfig = "peiJianTab",
	-- 配件标签配置
	globalConfig = "global",
	-- 全局配置
	equipmentsConfig = "equipment",
	-- 装备配置
	vipConfig = "Vip",
	-- Vip配置
	LoadingTipConfig = "LoadingTipConfig",
	-- LoadingTipConfig
	CostDiamondConfig = "costDiamond",
	-- 配件模型配置
	libraryConfig = "library",
	-- 属性配置
	fast_paidConfig = "fast_paid",
	-- 快速购买配置
	radarConfig = "radar",
	-- 雷达配置
	commanderInfo = "commanderInfo",
	--
	talent = "talent",
	--
	commanderBaseInfo = "exp",
	-- 指挥官基础信息
	marchingTroops = "marchingTroops",
	-- 统帅等级表
	mailLanguage = "mailLanguage",
	-- 邮件模板

	--服务器配置
	server = "server",
	--服务器组配置
	serverGroup = "serverGroup",

	attrBonusSource = "attrBonusSource",
	-- 属性定义表
	errorCode = "ErrorCode",
	-- 错误码配置
	language = "language",
	-- 语种配置管理
	-- cx_language_ZhCN = "CXlanguage_ZhCN",    --中文语言包配置
	-- cx_language_ZhTW = "CXlanguage_ZhTW",    --台湾语言包配置
	-- ch_language_ZhCN = "CHlanguage_ZhCN",    --策划中文语言包配置
	-- ch_language_ZhTW = "CHlanguage_ZhTW",    --策划台湾语言包配置
	sensitive = "sensitive",
	-- 屏蔽词配置
	army = "army",
	-- 军团升级
	armyBuilding = "armyBuilding",
	-- 语言包配置
	armyDonate = "armyDonate",
	-- 军团捐献
	flag = "flag",
	-- 军团捐献

	skill = "skill",
	skillAction = "t_skill_action",
	skillRange = "t_skill_range",
	skillMethod = "t_skill_method",
	skillEffect = "t_skill_effect",

	skillBullet = "t_bullet",
	skillBulletTrajectory = "t_bullet_trajectory",

	armDescConfig = "zhuangJiaMS",
	helpConfig = "help",

	taskConfig = "task",
	dailyTaskConfig = "daily_task",
	--副本 章
	duplicateCopy="duplicate/copy",	
	--副本 节		
	duplicateCopyPoint="duplicate/copyPoint",
	--副本 节 怪物		
	duplicateCopyMonster="duplicate/copyMonster",

	--军团商店道具配置
	armyShopItemConfig = "armyShopItem",
	--军团商店兑换条件配置
	armyShopTreasure = "armyShopTreasure",

	--事件
	event = "event",
	--事件排名
	eventRank = "eventRank",
	--事件类型
	eventTask = "eventTask",
	buff = "buff",
	ai = "ai",
	rtsType = "RtsType"
	
};

-----------界面子资源名字
SLG_ResChildName = {


}

SLG_Texture =
{

}

