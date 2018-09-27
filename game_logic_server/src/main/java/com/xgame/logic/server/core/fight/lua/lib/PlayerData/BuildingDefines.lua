BuildingDefines = {};

---------点击建筑的所有功能配置-------------------
BuildingFunction = {
	Info = 1,				--信息
	LevelUp = 2,			--升级
	Increase = 3,			--增益
	State = 4,				--状态
	Produce = 5,			--生产
	TechLevelUp = 6,		--科技升级
	WeaponMap = 7,			--装甲蓝图
	Create = 8,				--制造
	Bag = 9,				--储物箱
	CampTroop = 10,			--驻军
	CBW = 11,				--生化配件(Chemical and Biological Weapon)
	Relive = 12,			--复活
	Shop = 13,				--商店
	EnemyReport = 14,		--敌情
	Jailed = 15,			--关押
	Trade = 16,				--贸易
	CampTroopWeapon = 17,	--驻军装甲
	Repair = 18,			--修理
	Reform = 19,			--改造
	DefenTroop = 20,		--驻防
	Transfom = 21,			--传送
	Capacity = 22,			--容量
	Extraction = 23, 		--开采
	SpeedUp = 24,			--提速
	ExtractionSteel = 25,	--采钢
	ExtractionGold = 26, 	--采金
	ExtractionOil = 27, 	--采油
	ExtractionRare = 28, 	--采土
	Rotate = 29, 			--旋转
	Order = 30, 			--整列
	Reset = 31, 			--重新布置
	Clear = 32, 			--清理

};

-----------------------------------------------
BuildingAttrDefine = {
    
    mHp = 1,                        --血量
    helpCount = 2,                  --援建次数
    attackForAll = 3,               --全体攻击
    armsCreateSpeed = 4,            --造兵加速
    techSpeed = 5,                  --科研加速  
    armsCreateNumber = 6,           --造兵数量
    armsVolume = 7,                 --容量
    allArmsVolums = 8,              --总修理容量
    strength = 9,                   --战力
    weight = 10,                    --负载
    revenue = 11,                   --税率
    implantationEquipSpeedUp = 12,  --植入体生产加速
    massArmsNumber = 13,            --集结军队数量
    lockUpNumber = 14,              --关押数量
    askForArmsNumber = 15,          --关押数量
    treatArmsNumber = 16,           --收治伤病数量
    detailInfo = 17,                -- 详细信息
    awardFightPower = 18,           -- 奖励战斗力
    defenceNumber = 19,             -- 防守数量
    alarmScope = 20,                -- 警戒范围
    miningVehicleNum = 21,          -- 矿车数量
    totalCollect = 22,              -- 采集
    totalMoneyPerHour = 23,         -- 每小时金钱的产量
    totalIronPerHour = 24,          -- 刚才的产量
    totalOilPerHour = 25,           -- 石油的产量
    totalRarePerHour = 26,          -- 稀土的产量
    Store = 27,                     -- 仓库容量
	GarrisonCapacity=28,			-- 驻军容量
}