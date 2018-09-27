----------------------------------------------
----建筑配置表管理器
----------------------------------------------
BuildingConfigManager = {};

local this = BuildingConfigManager;

function BuildingConfigManager.init(configSystem) 
	
	this.configMap = Map.new();

	print("11111111111111111111111111111111111------------", SLG_ResourceName.homeBuildingConfig, configSystem);
	local config_string = configSystem:getConfigContent(SLG_ResourceName.homeBuildingConfig , false);
	
	print("config_string------------", config_string);
	local config_table = ConfigUtil.warpNormalConfig(config_string);
	
	for i, v in pairs(config_table)  do
        local model = BuildingConfigModel.new();
        model.configID = tonumber(i); 
        model:warpData(v);
        this.configMap:put(model.configID,model);
	end
	
end

function BuildingConfigManager.getConfig(id)
	return this.configMap:value(id);
end

------------------------------------------------------------------------
-----创建建筑物的分类  [1功能、2防御、3资源]
------------------------------------------------------------------------

-----获取功能建筑列表
function BuildingConfigManager.getCreateBuildListByType(tabType)
	
	local buildList = {};

	for i = 1 , this.configMap:size() do

		local model  = this.configMap:valueIndex(i);

		if model.tabType == tabType then

			table.insert(buildList,model);

		end

	end

	table.sort( buildList , this.sortBuildingTable );

    return buildList;

end

--根据属性ID查找属性名称（后期需要配置）
function BuildingConfigManager.getAttrName(attrId)
	local str =Config.language.getText("1,血量;2,援建次数;3,全体攻击;4,造兵加速;5,科研加速;6,造兵数量;7,容量;8,总修理容量;9,战力（不再使用）;10,负载;11,税率;12,植入体生产加速;13,出征部队数量;14,关押数量;15,关押数量;16,收治伤病数量;17,雷达效果;18,奖励战力;19,防守部队数量;20,警戒范围;21,矿车数量;22,临时存储容量;23,黄金/h;24,钢材/h;25,石油/h;26,稀土/h;27,仓库容量;28,驻军容量{^}基地");
	local attrList = StrSplit(str,";");

	for i=1,#attrList do

		local attr = StrSplit(attrList[i],",");
		
		local id = tonumber(attr[1]);
		
		local name = tostring(attr[2]);

		if attrId == id then
		
			return name;
		
		end

	end

	return "未知属性";

end

------------------------------------------------------------------------------
------------------------------------------------------------------------------
------------------------------------------------------------------------------

--根据策划配置表排序 函数
function BuildingConfigManager.sortBuildingTable(a,b)
	return a.sortIndex > b.sortIndex;
end

--
function BuildingConfigManager.getAttrValueWithType(i_type, i_bid , i_lv)

    local config = Config.building.getConfig(i_bid);
    
    if config == nil then
        return 0;
    end
	
	if i_type == BuildingAttrDefine.mHp	then				--血量
		
        return config:getAttrByAttrIdAndLevel(AttributeDefine.hp,i_lv)
		
	elseif i_type == BuildingAttrDefine.helpCount  then
		
        return config:getHelpBuildCount(i_lv);

    elseif i_type == BuildingAttrDefine.armsCreateNumber   then

        return config:getProduceSoldierCount(i_lv);

    elseif i_type == BuildingAttrDefine.armsVolume   then

        return config:getRepairCount(i_lv);

    elseif i_type == BuildingAttrDefine.strength   then

        return config:getLevelUpAwardStrength(i_lv);

	elseif i_type == BuildingAttrDefine.attackForAll	then
		
        local all = config:getAllAttackValue(i_lv);
        return (all*100).."%";

    elseif i_type == BuildingAttrDefine.techSpeed  then

        local speed = config:getScientAddSpeed(i_lv);
        return (speed*100).."%";
		
	elseif i_type == BuildingAttrDefine.allArmsVolums	then    --怎么可以显示所有的呢 多不好
	 
		local tb_buildings = BuildingHelper.getCraeteNumberByBuildingId(i_bid)
		local i_all = 0
		for i = 1, #tb_buildings	do
			
			local i_mid = tb_buildings[i]
			local binfo = BuildingHelper.getBuildInfoByUID(i_mid);
            local cfg = Config.building.getConfig(binfo.configID);
            local num = cfg:getRepairCount(binfo.level);
            i_all = i_all+num;
		end
		return i_all;
		
	elseif i_type == BuildingAttrDefine.weight then

		return config:getTradeWeight(i_lv);

	elseif i_type == BuildingAttrDefine.revenue then

		return config:getTradeRate(i_lv);

    elseif i_type == BuildingAttrDefine.implantationEquipSpeedUp then

        return config:getEquipmentProduceSpeed(i_lv);

    elseif i_type == BuildingAttrDefine.detailInfo then
        local radarConfigItem=Config.radar.getConfig(i_lv);
		
        return radarConfigItem.description;
        
    elseif i_type == BuildingAttrDefine.awardFightPower then

        return config:getLevelUpAwardStrength(i_lv);

    elseif i_type == BuildingAttrDefine.defenceNumber then

        return config:getDefenSoldierCount(i_lv);
        
    elseif i_type == BuildingAttrDefine.alarmScope then

        return config:getWarnRange(i_lv);

    elseif i_type == BuildingAttrDefine.miningVehicleNum then

        return config:getMinerTruckCount(i_lv);

    elseif i_type == BuildingAttrDefine.totalCollect then

        return config:getCollectWeight(i_lv);

    elseif i_type == BuildingAttrDefine.totalMoneyPerHour then --采集速度

        return config:getCollectSpeed(ResType.T_Cash,i_lv);
         
    elseif i_type == BuildingAttrDefine.totalOilPerHour then

        return config:getCollectSpeed(ResType.T_Oil,i_lv);

    elseif i_type == BuildingAttrDefine.totalRarePerHour then

         return config:getCollectSpeed(ResType.T_Earth,i_lv);

    elseif i_type == BuildingAttrDefine.totalIronPerHour then

        return config:getCollectSpeed(ResType.T_Steel,i_lv);

    elseif i_type == BuildingAttrDefine.Store then

        return config:getStoreCapacity(i_lv);

	elseif i_type==BuildingAttrDefine.GarrisonCapacity then

		return config:getMassTroopCount(i_lv);

    else
        return 0;
    end

	
end


----刷新对应语言名字,描述
function BuildingConfigManager.resetLanguage()

    for i = 1 , this.configMap:size() do

        local model  = this.configMap:valueIndex(i);

        if model then
            model:changeLanguage();
        end

    end

end