----------------------------------------------------------------------------------
--配置表  建筑模型数据
----------------------------------------------------------------------------------

BuildingConfigModel = class(Object);

-------
function BuildingConfigModel:ctor()

	ClassName = "BuildingConfigModel";
	-------------------可直接访问的属性-------------------------------------------

	self.configID = nil;				--建筑ID	[int] (id)

	self.nameId = nil;

	self.name = nil;					--建筑名称 	[string] (name)

	self.type = nil;					--建筑类型 	[int] (type)

	self.descId = nil;
	
	self.desc = nil;					--建筑描述 	[string] (description)

	self.cd = nil;						--再生时间	[int] 秒 (CD)

	self.blasting = nil;				--是否可被爆破 [bool] (blasting) --0,1

	self.maxLevel = nil;				--建筑最高等级 [int] (max_lv)

	self.maxCount = nil;				--最大建筑数量	(max_num)

	self.size = nil;					--占地格子正方形边长  (size)

	self.sortIndex = nil;				--排序用 (index)

	self.destroyParam = nil;			--	基地摧毁参数

	self.sound = nil;					--音效

	self.clearTime = nil;				--清理需要时间（单位秒）(cost_time1)

	self.functionList = nil;			--点击建筑的菜单列表

	self.tabType = nil;					--创建界面的所述标签页  1功能、2防御、3资源 0 不可建造

	self.infoList = nil;				--建筑信息界面所显示的属性列表

	-------------------需要通过接口访问的数据-------------------------------------------

	self.main_num_table = nil;			--每级主基地对应可以建筑的最大数量 访问方式：getMaxCreateNumByHomeLevel	(main_num)

	self.requireTable = nil;			--升级建筑要求前置建筑物id（可空）	getRequireBuildByLevel (require_id)

	self.iconMap = nil;					--	图标信息 (icon)

	self.modelMap = nil;				--模型数据 (model)

	self.cost_cash_table = nil;			--升级消耗钞票(cost_cash)

	self.cost_earth_table = nil;		--升级消耗稀土(cost_earth)

	self.cost_steel_table = nil;		--升级消耗钢材(cost_steel)

	self.cost_oil_table = nil;			--升级消耗石油(cost_oil)

	self.cost_time_table = nil;			--升级需要时间（单位秒）(cost_time)

	self.cost_item_map = nil;			--升级消耗道具(道具ID，数量)(cost_item)

	self.clear_cost_res_table = nil;	--清理消耗资源 (cost_type);

	self.awardStrengthList = nil;		--升级战斗力奖励 (strength)

	self.awardExp = nil;				--升级道具奖励？ (exp)

	self.clearAwardTable = nil;			--清理奖励(clearance)

	self.attrMap = nil;					--属性(attr)

	------------------------------------------------------------------------------------------------------------
	--- v1  v2--------------------------------------------------------------------------------------------------
	------------------------------------------------------------------------------------------------------------

	--行政大楼（v1:援建次数，v2:全体攻击%）
	self.help_build_table =  nil;
	self.all_attack_table = nil;

	--科研大楼（v1：科研加速）
	self.scientific_add_speed_table = nil;

	--生物实验室（v1：植入体生产加速）
	self.equipment_produce_speed_table = nil;

	--外事联络处（V1：集结军队数量）
	self.mass_troop_count_table = nil;

	--监狱（v1:关押数量）
	self.prison_lock_count_table = nil;

	--贸易站（v1:贸易税率，v2:贸易最大负重）
	self.trade_rate_table = nil;
	self.trade_weight_table = nil;

	--军营（v1:出征数量）
	self.ride_count_table = nil;
	
	--旋翼战机工厂、履带战车工厂、轮式战车工厂（v1:造兵数量，v2:解锁系统兵种等级）
	self.produce_soldier_count_table = nil;
	self.open_soldier_level_map =nil;	--{level, id(global表中的id 对应一个系统兵种)}
	
	--修理厂（v1:修理空位数，v2:改造空位数）
	self.repair_count_table = nil;
	self.reform_count_table = nil;

	--防御驻地（v1:防守士兵数，v2:警戒范围）
	self.defen_soldier_table = nil;
	self.warn_range_table = nil;

	----银行-稀土仓库-石油仓库-钢材仓库（v1:仓库容量）
	self.store_capacity_table = nil;

	--勘探开发院（v1:矿车数量，v2:采集负重）
	self.miner_truck_table = nil;
	self.collect_weight_table = nil;

	--采矿车（v1:不同资源的采集速度（每秒））
	self.collect_speed_map = nil;	

	self.skillArr = nil; --建筑技能 list 结构

	self.ai = nil; --建筑ai
end

----解析原表数据
function BuildingConfigModel:warpData(t_data)

	BuildingConfigParser.warpData(self,t_data);

end

-----------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------
--------------对外接口-------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------

--根据主基地等级获取最大建筑数量
function BuildingConfigModel:getMaxCreateNumByHomeLevel(homeBaseLevel)
	return tonumber(self.main_num_table[homeBaseLevel]);
end

--获取升级到某等级的前置条件
-- 返回 RequireBuildingModel 数组 -- {id , level}
-- 返回 nil 则没有前置条件
function BuildingConfigModel:getRequireBuildByLevel(level)
	return self.requireTable[level];
end

--返回建筑物图标
function BuildingConfigModel:getIconName(level)
	
	local keys = self.iconMap:keys();

	for i=#keys,1,-1 do

		local curLevel = keys[i];
		
		if level >= curLevel then
			return self.iconMap:value(curLevel);
		end

	end

	return nil;

end

--获取建筑模型数据
function BuildingConfigModel:getModelName(level)
	
	-- return self.configID.."";
	if level <= 0 then
		level = 1;
	end
	
	local keys = self.modelMap:keys();

	for i=#keys,1,-1 do

		local curLevel = keys[i];
		
		if level >= curLevel then
			return self.modelMap:value(curLevel);
		end

	end

	return nil;
end

--根据[等级]获取升级需要的[金钱]
function BuildingConfigModel:getLevelUpCostCash(level)
	
	local costValue = self.cost_cash_table[tonumber(level)];
	
	if costValue==nil then
	
		costValue = 0;
	
	end

	return costValue;
end

--根据[等级]获取升级需要的[稀土]
function BuildingConfigModel:getLevelUpCostEarth(level)

	local costValue = self.cost_earth_table[tonumber(level)];
	
	if costValue==nil then
	
		costValue = 0;
	
	end

	return costValue;

end

--根据[等级]获取升级需要的[钢材]
function BuildingConfigModel:getLevelUpCostSteel(level)

	local costValue = self.cost_steel_table[tonumber(level)];
	
	if costValue==nil then
	
		costValue = 0;
	
	end

	return costValue;

end

--根据[等级]获取升级需要的[石油]
function BuildingConfigModel:getLevelUpCostOil(level)

	local costValue = self.cost_oil_table[tonumber(level)];
	
	if costValue==nil then
	
		costValue = 0;
	
	end

	return costValue;

end

--根据[等级]获取升级需要的[道具列表] table = {ConfigGoodsModel...}
--没有时返回 nil
function BuildingConfigModel:getLevelUpCostItemTable(level)
	return self.cost_item_map[level];
end

--根据[等级]获取升级需要的[时间]
function BuildingConfigModel:getLevelUpCostTime(level)
	
	local costValue = self.cost_time_table[tonumber(level)];
	
	if costValue==nil then
	
		costValue = 0;
	
	end

	return costValue;

end

--根据[等级]获取升级奖励的[战斗力]
function BuildingConfigModel:getLevelUpAwardStrength(level)
	return self.awardStrengthList[level];
end

--根据[等级]获取升级奖励的[道具列表] table = {ConfigGoodsModel...}
function BuildingConfigModel:getLevelUpAwardItems(level)
	return self.awardExp[level];
end

--获取清理所需要的资源列表
--返回 {ConfigResModelModel...} 只有三草才有的属性
function BuildingConfigModel:getClearCostResTable()
	return self.clear_cost_res_table;
end

--获取清理的[奖励道具列表]
--返回{ConfigGoodsModel...}
function BuildingConfigModel:getClearAwardResTable()
	return self.clearAwardTable;
end

--根据等级获取属性列表（目前只配置一条）{AttrConfigModel...}
function BuildingConfigModel:getAttrListByLevel(level)

	local data = {};

	for i=1, self.attrMap:size() do

		local tableList = self.attrMap:valueIndex(i);

		if tableList[level] ~= nil then

			table.insert(data,tableList[level]);

		end

	end

	return data;

end

--根据等级获取属性列表（目前只配置一条） return number
function BuildingConfigModel:getAttrByAttrIdAndLevel(id,level)

	local value = 0;

	for i=1, self.attrMap:size() do

		local tableList = self.attrMap:valueIndex(i);

		local attrModel = tableList[level];

		if attrModel ~= nil and attrModel.id==id then

			return attrModel.value;

		end

	end

	return 0;

end



-----------------------------------------------------------------------------------------------
--v1 v2 ---------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------

--行政大楼（v1:援建次数）
function BuildingConfigModel:getHelpBuildCount(level)

	if level == nil then
		level = self:getCurBuildMaxLevel();
	end

	if level == 0 then
		return 0;
	end

	return self.help_build_table[level];
end

--行政大楼（v2:全体攻击%）
function BuildingConfigModel:getAllAttackValue(level)

	if level == nil then
		level = self:getCurBuildMaxLevel();
	end

	if level == 0 then
		return 0;
	end

	return self.all_attack_table[level];
end

--科研大楼（v1：科研加速）
function BuildingConfigModel:getScientAddSpeed(level)

	if level == nil then
		level = self:getCurBuildMaxLevel();
	end

	if level == 0 then
		return 0;
	end

	return self.scientific_add_speed_table[level];
end

--生物实验室（v1：植入体生产加速）
function BuildingConfigModel:getEquipmentProduceSpeed(level)

	if level == nil then
		level = self:getCurBuildMaxLevel();
	end

	if level == 0 then
		return 0;
	end

	return self.equipment_produce_speed_table[level];
end

--外事联络处（V1：集结军队数量）
function BuildingConfigModel:getMassTroopCount(level)

	if level == nil then
		level = self:getCurBuildMaxLevel();
	end

	if level == 0 then
		return 0;
	end

	return self.mass_troop_count_table[level];
end

--监狱（v1:关押数量）
function BuildingConfigModel:getPrisonLockCount(level)

	if level == nil then
		level = self:getCurBuildMaxLevel();
	end

	if level == 0 then
		return 0;
	end

	return self.prison_lock_count_table[level];
end

--贸易站（v1:贸易税率）
function BuildingConfigModel:getTradeRate(level)

	if level == nil then
		level = self:getCurBuildMaxLevel();
	end

	if level == 0 then
		return 0;
	end

	return self.trade_rate_table[level];
end

--贸易站（v2:贸易最大负重）
function BuildingConfigModel:getTradeWeight(level)

	if level == nil then
		level = self:getCurBuildMaxLevel();
	end

	if level == 0 then
		return 0;
	end

	return self.trade_weight_table[level];
end

--军营（v1:出征数量）
function BuildingConfigModel:getRideCount(level)

	if level == nil then
		level = self:getCurBuildMaxLevel();
	end

	if level == 0 then
		return 0;
	end


	return self.ride_count_table[level];
end

--旋翼战机工厂、履带战车工厂、轮式战车工厂（v1:造兵数量）
function BuildingConfigModel:getProduceSoldierCount(level)

	if level == nil then
		level = self:getCurBuildMaxLevel();
	end

	if level == 0 then
		return 0;
	end

	return self.produce_soldier_count_table[level];
end

--旋翼战机工厂、履带战车工厂、轮式战车工厂（v2:解锁系统兵种等级）
--获取当前等级解锁的兵
function BuildingConfigModel:getCurOpenSoldierByLevel(level)

	if level == nil then
		level = self:getCurBuildMaxLevel();
	end

	if level == 0 then
		return {};
	end

	return self.open_soldier_level_map:value(level);
end

--旋翼战机工厂、履带战车工厂、轮式战车工厂（v2:解锁系统兵种等级）
--获取当前等级解锁的兵 根据顺序取
function BuildingConfigModel:getCurOpenSoldierGlobalIndex(index)
print("ssssssssssssssssss  " , index)
	if index == nil then
		index = 1;
	end
print(self.open_soldier_level_map ,  self.open_soldier_level_map:size()) 
print(self.open_soldier_level_map[index])
	return self.open_soldier_level_map:valueIndex(index);
end


--旋翼战机工厂、履带战车工厂、轮式战车工厂（v2:解锁系统兵种等级）
--获取当前等级解锁的所有兵
function BuildingConfigModel:getAllOpenSoldierByLevel(level)

	if level == nil then
		level = self:getCurBuildMaxLevel();
	end

	if level == 0 then
		return {};
	end

	local open_table = {};
	
	for i=1,#self.open_soldier_level_map:size() do
		
		local lv = self.open_soldier_level_map:keys()[i];
		
		if lv<=level then

			table.insert(open_table,self.open_soldier_level_map:valueIndex(i));
		
		end

	end

	return open_table;

end


--修理厂（v1:修理空位数）
function BuildingConfigModel:getRepairCount(level)

	if level == nil then
		level = self:getCurBuildMaxLevel();
	end

	if level == 0 then
		return 0;
	end

	return self.repair_count_table[level];
end

--修理厂（v2:改造空位数）
function BuildingConfigModel:getReformCount(level)

	if level == nil then
		level = self:getCurBuildMaxLevel();
	end

	if level == 0 then
		return 0;
	end

	return self.reform_count_table[level];
end

--防御驻地（v1:防守士兵数）
function BuildingConfigModel:getDefenSoldierCount(level)

	if level == nil then
		level = self:getCurBuildMaxLevel();
	end

	if level == 0 then
		return 0;
	end

	return self.defen_soldier_table[level];
end

--防御驻地（v2:警戒范围）
function BuildingConfigModel:getWarnRange(level)
	
	if level == nil then
		level = self:getCurBuildMaxLevel();
	end

	if level == 0 then
		return 0;
	end

	return self.warn_range_table[level];
end

--银行-稀土仓库-石油仓库-钢材仓库（v1:仓库容量）
function BuildingConfigModel:getStoreCapacity(level)

	if level == nil then
		level = self:getCurBuildMaxLevel();
	end

	if level == 0 then
		return 0;
	end

	return self.store_capacity_table[level];
end

--勘探开发院（v1:矿车数量）
function BuildingConfigModel:getMinerTruckCount(level)

	if level == nil then
		level = self:getCurBuildMaxLevel();
	end

	if level == 0 then
		return 0;
	end

	return self.miner_truck_table[level];
end

--勘探开发院（v2:采集负重）
function BuildingConfigModel:getCollectWeight(level)

	if level == nil then
		level = self:getCurBuildMaxLevel();
	end

	if level == 0 then
		return 0;
	end

	return self.collect_weight_table[level];
end

--采矿车（v1:不同资源的采集速度（每小时））
--根据等级和资源类型获取 采集速度
function BuildingConfigModel:getCollectSpeed(resType,level)

	if level == nil then
		level = self:getCurBuildMaxLevel();
	end

	if level == 0 then
		return 0;
	end

	local collect_table = self.collect_speed_map:value(resType);
	
	if collect_table==nil then
		return 0;
	end

	return collect_table[level];

end

function BuildingConfigModel:getCurBuildMaxLevel()

	local buildingList =  Player.home.getBuildByCfgID(self.configID);

	local maxLevel = 0;

	for i=1,buildingList:getSize() do

		local buildInfo = buildingList[i];

		if buildInfo.level > maxLevel then
			
			maxLevel = buildInfo.level;

		end 

	end

	return maxLevel;

end

function BuildingConfigModel:getOpenArmLevelTable( )
	local tb = {};

	for i=1,self.open_soldier_level_map:size() do
		
		local lv = self.open_soldier_level_map:keys()[i];
		table.insert(tb,lv);
	end
	table.sort( tb )

	return tb;
end

function BuildingConfigModel:changeLanguage()
	self.name = Config.chLanguage.getText(self.nameId).."";	
	self.desc = Config.chLanguage.getText(self.descId).."";
end

-----------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------
