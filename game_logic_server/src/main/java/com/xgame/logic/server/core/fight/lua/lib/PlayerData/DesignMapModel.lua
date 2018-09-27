-------------------------------------------------------------------------------------
--图纸模型（前端包装类）-------------------------------------------------------------
-------------------------------------------------------------------------------------

DesignMapModel = class(Object);

function DesignMapModel:ctor()

	--固有属性(服务器保存的属性)----------------------------------------

	self.id = nil;             	--ID(i_soldierId)

	self.type = nil;           	--图纸类型:1坦克2战车3飞机(LTType)

	self.systemIndex = nil;    	--系统兵种序列号(DiPanIndex)

	self.index = nil;          	--位置顺序(pos_id)

	self.buildIndex = nil;     	--建造顺序(build_index)

	self.version = nil;        	--版本号

	self.name = nil;           	--图纸名字(str_name)

	self.unlock = nil;		   	--是否解锁（1解锁0未解锁）

	self.partList = List.new();--配件列表(list_pj)

	--扩展字段(客户端扩展)-----------------------------------------------

	self.buildingConfigID = nil;  --该图纸对应的建筑配置ID

	self.baseFight = 0;           --基础战力

	self.attrMap = nil;           --属性

	self.fightModelRatio = 1;     --战斗模型比例

	self.icon = nil;              --图标

	self.viewPositionUrl = nil;	  --用来表示该图纸的逻辑位置(type..systemIndex..index) 字符串表示

	self.logicPositionUrl = nil;  --用来表示该图纸的逻辑位置(type..systemIndex..buildIndex) 字符串表示
	-----------------------------------------------------------------------
end

----designMapBean = DesignMapBean
function DesignMapModel:setDesignMapBean(designMapBean)

	----1 固有字段
	self.id = designMapBean.id;

	self.type = designMapBean.type;

	self.systemIndex = designMapBean.systemIndex;

	self.index = designMapBean.index;

	self.buildIndex = designMapBean.buildIndex;

	self.version = designMapBean.version;

	self.name = designMapBean.name; 

	self.unlock = designMapBean.unlock;
	
	self.partList = List.new();

	----2 扩展字段
  
	--建筑配置ID
	if self.type == 1 then
		self.buildingConfigID = BuildingType.B_TanKe;   --坦克
	elseif self.type == 2 then
		self.buildingConfigID = BuildingType.B_ZhanChe;   --战车
	else
		self.buildingConfigID = BuildingType.B_ZhiShengJi;  --飞机
	end

	self.viewPositionUrl =  self.type..self.systemIndex..self.index;	  --用来表示该图纸的逻辑位置(type..systemIndex..index) 字符串表示

	self.logicPositionUrl =  self.type..self.systemIndex..self.buildIndex;	  --用来表示该图纸的逻辑位置(type..systemIndex..buildIndex) 字符串表示

	self.baseFight = 0;

	for i = 1, #designMapBean.partList do

		local partBean = designMapBean.partList[i];

    	if partBean then

			local weaponPJ = PartModel.new();

			local pjCfg = Config.customWeapon.getConfig(partBean.partId);

			if pjCfg then

				weaponPJ:initData(pjCfg , 1 , partBean.position);

				self.partList:add(weaponPJ);

				self.baseFight = self.baseFight + pjCfg.gs_bonus;

				if pjCfg.type5 == CustomWeapon.C_CAO0 then

					self.fightModelRatio = pjCfg.model1;

				end

			end

		end  

	end

	if self.buildIndex == 0 then
		self:createName();
	end 
	self:createTZIcon();
end

----属性要通过外部传递，图纸本身不带属性信息(要挪动到兵信息里面)
function DesignMapModel:setAttrData(attrList)
	self.attrMap = Map.new();
	for i = 1, #attrList do
		local attrData = AttrValueModel.new(attrList[i]);
		self.attrMap:put(attrData.id , attrData);
	end
end

function DesignMapModel:getAttribute()    
	local lenArr = self.partList:getSize();
	local attr = Map.new()
	for i = 1 , lenArr do
		local pj = self.partList[i];    
		local arr_s_1 = Config.customWeapon.getConfig(pj.i_tid).attr;
		if arr_s_1 then
			for j = 1, #arr_s_1 do 
				local dt = arr_s_1[j];
				if dt then
					local value = 0;
					if attr:containsKey(dt.id) then
						value = attr:value(dt.id);
					end
					value = value + dt.value;
					if value > 0 then
						attr:put(dt.id,value);
					end
				end
			end
		end
	end
	return attr;
end

function DesignMapModel:getTzRepairConsume()
	local lenArr = self.partList:getSize();    
	local cash = 0;
	local earth = 0;
	local steel = 0;
	local oil = 0;
	for i = 1 , lenArr do
		local pj = self.partList[i];    
		local pjCfg = Config.customWeapon.getConfig(pj.i_tid);
		if pjCfg then
			if pjCfg.fix_cost_cash then
				cash = cash + pjCfg.fix_cost_cash;
			end
			if pjCfg.fix_cost_earth then
				earth = earth + pjCfg.fix_cost_earth;
			end
			if pjCfg.fix_cost_steel then
				steel = steel + pjCfg.fix_cost_steel;
			end
			if pjCfg.fix_cost_oil then
				oil = oil + pjCfg.fix_cost_oil;
			end
		end
	end
	local costData = {};
	costData[ResDefine.gold] = cash;
	costData[ResDefine.oil] = oil;
	costData[ResDefine.earth] = earth;
	costData[ResDefine.steel] = steel;
	return costData;
end

--获取士兵的石油消耗 
function DesignMapModel:getOilConsume()
	local lenArr = self.partList:getSize();    
	local oil = 0;
	for i = 1 , lenArr do
		local pj = self.partList[i];   
		local pjCfg = Config.customWeapon.getConfig(pj.i_tid);
		if pjCfg then
			if pjCfg.camp_cost_oil then
				oil = oil + pjCfg.camp_cost_oil;
			end
		end
	end
	return oil;
end

function DesignMapModel:getTzRepairTime()
	local lenArr = self.partList:getSize();    
	local totalTime = 0;
	for i = 1 , lenArr do
		local pj = self.partList[i];    
		local pjCfg = Config.customWeapon.getConfig(pj.i_tid);
		if pjCfg then
			if pjCfg.fix_time then
				totalTime = totalTime + pjCfg.fix_time;
			end      
		end
	end
	return totalTime;
end

----创建图纸兵种的消耗时间
function DesignMapModel:getTzCreateTime()
	local lenArr = self.partList:getSize();
	local totalTime = 0;
	for i = 1 , lenArr do
		local pj = self.partList[i];    
		local pjCfg = Config.customWeapon.getConfig(pj.i_tid);
		if pjCfg then
			if pjCfg.time then
				totalTime = totalTime + pjCfg.time;
			end      
		end
	end
	return totalTime;
end

-------创建图纸兵种的消耗
function DesignMapModel:getTzConsume()    
	local lenArr = self.partList:getSize();    
	local cash = 0;
	local earth = 0;
	local steel = 0;
	local oil = 0;
	for i = 1 , lenArr do
		local pj = self.partList[i];    
		local pjCfg = Config.customWeapon.getConfig(pj.i_tid);
		if pjCfg then
			if pjCfg.cost_cash then
				cash = cash + pjCfg.cost_cash;
			end
			if pjCfg.cost_earth then
				earth = earth + pjCfg.cost_earth;
			end
			if pjCfg.cost_steel then
				steel = steel + pjCfg.cost_steel;
			end
			if pjCfg.cost_oil then
				oil = oil + pjCfg.cost_oil;
			end
		end
	end
	local costData = {};
	costData[ResDefine.gold] = cash;
	costData[ResDefine.oil] = oil;
	costData[ResDefine.earth] = earth;
	costData[ResDefine.steel] = steel;  
	return costData;
end

--  创建图纸名
function DesignMapModel:createName()
	if self.buildIndex == 0 then
		local config = Config.building.getConfig(self.buildingConfigID);
		if config ~= nil then        
			local configId = config:getCurOpenSoldierGlobalIndex(self.systemIndex);
			if configId ~= 0 then
				local arrList = GlobalConfigManager.getTable(tonumber(configId),";");
				--self.name = Config.chLanguage.getText(tonumber(arrList[#arrList]));
			end
		end 
		return self.name;
	end
	local nameStr = "";
	local globalInfo = Config.global.getString(13);
	local arr_s_1 = StrSplit(globalInfo, ";");
  
	--第一部分
	if arr_s_1 then
		for j = 1, #arr_s_1 do 
			local dt = StrSplit(arr_s_1[j] , ":");
			if self.type == tonumber(dt[1]) then
				local dd = StrSplit(dt[2] , ",");
				nameStr = nameStr..Config.chLanguage.getText(tonumber(dd[self.systemIndex]));
				break;
			end
		end
	end
  
  	--第二部分
	globalInfo = Config.global.getString(14);
	arr_s_1 = StrSplit(globalInfo, ";");
	-- arr_s_1 = StrSplit(globalInfos , ";");
	if arr_s_1 then
		for j = 1, #arr_s_1 do 
			local dt = StrSplit(arr_s_1[j] , ":");
			if self:getSmallSoldierId() == tonumber(dt[1]) then
				nameStr = nameStr..Config.chLanguage.getText(tonumber(dt[2]));
				break;
			end
		end
	end
  
  --第三部分
	globalInfo = Config.global.getString(15);
	arr_s_1 = StrSplit(globalInfo, ";");
	if arr_s_1 then   
		nameStr = nameStr..Config.chLanguage.getText(tonumber(arr_s_1[self.buildIndex]));
	end
	self.name = nameStr;
	return self.name;
end

--创建图纸Icon
function DesignMapModel:createTZIcon()
	local nameStr = "";
	local smallID = self:getSmallSoldierId();
	local globalInfo = Config.global.getString(12);
	local arr_s_1 = StrSplit(globalInfo, ";");
	if arr_s_1 then
		for j = 1, #arr_s_1 do 
		local dt = StrSplit(arr_s_1[j] , ":");
			if smallID == tonumber(dt[1]) then
			local dd = StrSplit(dt[2] , ",");
			nameStr = dd[self.systemIndex];
			break;
			end
		end
	end
	if nameStr==nil or nameStr=="" then
		nameStr = "zxhp3";
		warn("WeaponTZ createTZIcon false!");
	end
	self.icon = nameStr;
	return self.icon;
end

function DesignMapModel:getSmallSoldierId()
	local lenArr = self.partList:getSize();
	for i = 1,lenArr do
		local pJ = self.partList[i];
		if pJ.i_cao == CustomWeapon.C_CAO2 then
			return Config.customWeapon.getConfig(pJ.i_tid).type2;
		end
	end
	return 0;
end

function DesignMapModel:getAtrrValueByAttrIdAndCao(attrId,cao)
	local lenArr = self.partList:getSize();
	local value = 0;
	for i = 1 , lenArr do   
		local pj = self.partList[i];
		local attrArr = Config.customWeapon.getConfig(pj.i_tid).attr;
		for j = 1, #attrArr do 
			if attrArr[j].id == attrId then
				value = value + attrArr[j].value;
			end
		end
	end
	return value;
end
--根据槽位获取配件列表
function DesignMapModel:getPJListByCao( cao )
	local list = List:new();
	local lenArr = self.partList:getSize();
	local value = 0;
	for i = 1 , lenArr do
		local pj = self.partList[i];   
		if pj.i_cao == cao then
			list:add(pj);
		end
	end
	return list;
end
--获取打开前的配件信息
function DesignMapModel:getOpenPreList( cao )
	local list = List:new();
	local lenArr = self.partList:getSize();
	local value = 0;
	for i = 1 , lenArr do
		local pj = self.partList[i];  
		if pj.i_cao == cao then
			list:add(pj);
		end
	end
	return list;
end
--获取士兵战力
function DesignMapModel:getFight()
	local zhanli = 0;
	local lenArr = self.partList:getSize();
	for i = 1 , lenArr do
		local pj = self.partList[i];
		local attrArr = Config.customWeapon.getConfig(pj.i_tid).attr;
		for j = 1 , #attrArr do
			local model = attrArr[j];
			local libId = model.id;
			local libValue = model.value;      
			local libCfg = Config.library.getConfig(libId);
			if not IsNilOrEmpty(libCfg.gs_para) then
				zhanli = zhanli + libValue * libCfg.gs_para;
			end 
		end  
	end 
	return math.floor(zhanli);
end

function DesignMapModel:getAllPreList( )
	local list = List:new();
	local lenArr = self.partList:getSize();
	local value = 0;
	for i = 1 , lenArr do
		local pj = self.partList[i]; 
		list:add(pj);
	end
	return list;
end

--*** 只克隆简单数据，可共显示样式和数量等信息
function DesignMapModel:clone()
	local model = DesignMapModel.new();

	model.id = self.id;

	model.type = self.type;

	model.systemIndex = self.systemIndex;

	model.index = self.index;

	model.buildIndex = self.buildIndex;

	model.version = self.version;

	model.name = self.name;

	model.unlock = self.unlock;

	--扩展字段(客户端扩展)-----------------------------------------------

	model.buildingConfigID = self.buildingConfigID;

	model.baseFight = self.baseFight;

	model.fightModelRatio = self.fightModelRatio;

	model.icon = self.icon;

	model.viewPositionUrl = self.viewPositionUrl;

	model.logicPositionUrl = self.logicPositionUrl;

	model.attrMap = self.attrMap;

	model.partList = List.new();

	for i = 1 , self.partList:getSize() do

		local partModel = self.partList[i];

		local newPart = PartModel.new();

		local pjCfg = Config.customWeapon.getConfig(partModel.i_tid);

		newPart:initData(pjCfg , 1 , partModel.assembly_pos);

		model.partList:add(newPart);

	end

	return model;
	
end

--获取饼图列表值
function DesignMapModel:getPielist()
	local value1 = 0;
	local value2 = 0;
	local value3 = 0;
	local value4 = 0;
	local value5 = 0;
	local value6 = 0;
	local value7 = 0;

	local list = {};
	local lenArr = self.partList:getSize();
	for i = 1 , lenArr do
		local pj = self.partList[i];   
		local pjCfg = Config.customWeapon.getConfig(pj.i_tid);
		if pjCfg then
			value1 = value1 + pjCfg.pie_type_1;
			value2 = value2 + pjCfg.pie_type_2;
			value3 = value3 + pjCfg.pie_type_3;
			value4 = value4 + pjCfg.pie_type_4;
			value5 = value5 + pjCfg.pie_type_5;
			if pjCfg.pie_type_6 ~= nil and pjCfg.pie_type_6 ~= "" then
				value6 = value6 + pjCfg.pie_type_6;
			end
			if pjCfg.pie_type_7 ~= nil and pjCfg.pie_type_7 ~= "" then
				value7 = value7 + pjCfg.pie_type_7;
			end        
		end
	end
	local rateList = Config.global.getTable(500724,";");
	value1 = value1 / tonumber(rateList[1]);
	value2 = value2 / tonumber(rateList[2]);
	value3 = value3 / tonumber(rateList[3]);
	value4 = value4 / tonumber(rateList[4]);
	--value5 = value5 / maxValue;
	value6 = value5 * value6;
	value7 = value5 * value7;
	value6 = value6 / tonumber(rateList[5]);
	value7 = value7 / tonumber(rateList[6]);

	if value1 > 1 then
		value1 = 1;
	end

	if value2 > 1 then
		value2 = 1;
	end

	if value3 > 1 then
		value3 = 1;
	end

	if value4 > 1 then
		value4 = 1;
	end

	if value6 > 1 then
		value6 = 1;
	end

	if value7 > 1 then
		value7 = 1;
	end
	table.insert(list,value3);
	table.insert(list,value4);
	table.insert(list,value6);
	table.insert(list,value7);
	table.insert(list,value1);
	table.insert(list,value2);
	return list;
end


--根据配件获取配件路径
function DesignMapModel:getViewPath(weaponPJ)
	local pJLen = self.partList:getSize();
	local dpStr = "";
	local wqjStr = "";
	local pathStr = "";
	for i = 1 , pJLen do
		local pj = self.partList[i];
		if tonumber(pj.i_cao) == CustomWeapon.C_CAO0 then
			dpStr = pj.str_showId;      
		end
		if tonumber(pj.i_cao) == CustomWeapon.C_CAO2 then
			wqjStr = pj.str_showId;      
		end
	end
	if tonumber(weaponPJ.i_cao) == CustomWeapon.C_CAO1 then--行动部件
		pathStr = dpStr.."/xingdong/"..weaponPJ.assembly_pos.."/"..weaponPJ.str_showId;
	elseif tonumber(weaponPJ.i_cao) == CustomWeapon.C_CAO3 then--主武器
		pathStr = dpStr.."/paotai/1/"..wqjStr.."/Zwuqi/"..weaponPJ.assembly_pos.."/"..weaponPJ.str_showId;
	elseif tonumber(weaponPJ.i_cao) == CustomWeapon.C_CAO4 then--副武器
		pathStr = dpStr.."/paotai/1/"..wqjStr.."/Fwuqi/"..weaponPJ.assembly_pos.."/"..weaponPJ.str_showId;
	elseif tonumber(weaponPJ.i_cao) == CustomWeapon.C_CAO2 then--炮台
		pathStr = dpStr.."/paotai/1/"..wqjStr;
	elseif tonumber(weaponPJ.i_cao) == CustomWeapon.C_CAO7 then--车体外部
		pathStr = dpStr.."/chewia/"..weaponPJ.assembly_pos.."/"..weaponPJ.str_showId;
	end
  return pathStr;
end


--获取炮台路径
function DesignMapModel:getBatteryPath()
    local pJLen = self.partList:getSize();
    local dpStr = "";
    local wqjStr = "";
    local pathStr = "";
    for i = 1 , pJLen do
        local pj = self.partList[i];
        if tonumber(pj.i_cao) == CustomWeapon.C_CAO0 then
            dpStr = pj.str_showId;      
        end
        if tonumber(pj.i_cao) == CustomWeapon.C_CAO2 then
            wqjStr = pj.str_showId;      
        end
    end
    pathStr = dpStr.."/paotai/1/"..wqjStr;
    return pathStr;
end

--获取行动部件路径列表
function DesignMapModel:getMovePJPathList()
    local pJLen = self.partList:getSize();
    local dpStr = "";
    local list = List.new();
    for i = 1 , pJLen do
        local pj = self.partList[i];
        if tonumber(pj.i_cao) == CustomWeapon.C_CAO0 then
            dpStr = pj.str_showId;    
            break;  
        end
    end
    local pathStr = "";
    for i = 1 , pJLen do
        local pj = self.partList[i];
        if tonumber(pj.i_cao) == CustomWeapon.C_CAO1 then
            pathStr = dpStr.."/xingdong/"..pj.assembly_pos.."/"..pj.str_showId;  
            list:add(pathStr);
        end

    end
    return list;
end


--根据路径获取控制器信息 --WeaponPJCtrlConfig  返回对象
function DesignMapModel:getWeaponPJCtrlInfoByPath(pathStr)
	local list = Config.customWeaponModel.getConfig(pathStr);
	if list ~= nil then
		return list[7];
	end
	return nil;
end

--获取配件子控制器路径列表,主武器，副武器和炮台存在
function DesignMapModel:getWeaponSubCtrlPathList(weaponPJ)
	local pathList = List:new();
	local pathStr = self:getViewPath(weaponPJ);
	if pathStr ~= "" then
		local list = Config.customWeaponModel.getConfig(pathStr);
		local ctrlConfig = list[7];
		if ctrlConfig.subList then
		    local pJLen = #ctrlConfig.subList;        
		    for i = 1 , pJLen do
		        local name = ctrlConfig.subList[i];
		        local value = pathStr .. "/"..tostring(i).."/"..name;
		        pathList:add(value);
		    end
		end
	end
	return pathList;
end

--获取炮台配件showId
function DesignMapModel:getBatteryShowId()
	local pJLen = self.partList:getSize();
	for i = 1 , pJLen do
		local pj = self.partList[i];
		if tonumber(pj.i_cao) == CustomWeapon.C_CAO2 then
			return pj.str_showId;      
		end
	end
	return nil;
end

--获取底盘配件showId
function DesignMapModel:getChassisShowId()
	local pJLen = self.partList:getSize();
	for i = 1 , pJLen do
		local pj = self.partList[i];
		if tonumber(pj.i_cao) == CustomWeapon.C_CAO0 then
			return pj.str_showId;      
		end
	end
	return nil;
end

--获取炮台配件
function DesignMapModel:getBatteryWeaponPJ()
	local pJLen = self.partList:getSize();
	for i = 1 , pJLen do
		local pj = self.partList[i];
		if tonumber(pj.i_cao) == CustomWeapon.C_CAO2 then
			return pj;      
		end
	end
	return nil;
end

--获取底盘配件
function DesignMapModel:getChassisWeaponPJ()
	local pJLen = self.partList:getSize();
	for i = 1 , pJLen do
		local pj = self.partList[i];
		if tonumber(pj.i_cao) == CustomWeapon.C_CAO0 then
			local cfg = Config.customWeapon.getConfig(pj.i_tid);
			return cfg;   
		end
	end
	return nil;
end

--获取有模型的配件列表
function DesignMapModel:getWeaponPJListHavPrefab()
	local list = List:new();
	local pJLen = self.partList:getSize();
	for i = 1 , pJLen do
		local pj = self.partList[i];
		if pj.i_cao ~= CustomWeapon.C_CAO5 and pj.i_cao ~= CustomWeapon.C_CAO6 and pj.i_cao ~= CustomWeapon.C_CAO8 then
			list:add(pj); 
		end
	end
	return list;
end

--武器架上能安装的主武器类型  type6
function DesignMapModel:getMainArmyType6()
	local pJLen = self.partList:getSize();
	for i = 1 , pJLen do
		local pj = self.partList[i];
		if pj.i_cao == CustomWeapon.C_CAO2 then
			local cfg = Config.customWeapon.getConfig(pj.i_tid);
			return tonumber(StrSplit(cfg.cao3 , ";")[2]);
		end
	end
	return 0;
end

--获取兵种类型（大兵种）
function DesignMapModel:getSoliderType()
  if self.buildingConfigID == BuildingType.B_TanKe then
    return SoldierType.Tank;
  elseif self.buildingConfigID == BuildingType.B_ZhanChe then
    return SoldierType.Chariot;
  else
    return SoldierType.Aircraft;
  end
end

----获取描述信息
function DesignMapModel:getDesc()
  local lenArr = self.partList:getSize();
    local content = "";
    for i = 1 , lenArr do
        local pj = self.partList[i];
        if pj.i_cao == CustomWeapon.C_CAO2 then
            local desc = Config.ArmDesc.getArmDesc(self.systemIndex, pj.smallSoldierID);
            if desc ~= nil then
                content = content..desc;
            end       
        end
    end
    return content;
end

--是否是系统兵种
function DesignMapModel:isSystemDesignMap()
	return self.index == 0;
end

--是否解锁(只有系统兵种才有未解锁状态，自建兵种都是解锁状态)
function DesignMapModel:isUnLock()
	if not self:isSystemDesignMap() then
		return true;
	end
	return self.unlock == 1;
end

--获取解锁等级
function DesignMapModel:getOpenLevel()
	local config = Config.building.getConfig(self.buildingConfigID);
	local levels = config:getOpenArmLevelTable();
   	return levels[self.systemIndex];
end

----是否有主武器
function DesignMapModel:hasZWQ()
	for i = 1,self.partList:getSize() do
		local pJ = self.partList[i];
		if pJ.i_cao == CustomWeapon.C_CAO3 then
			return true;    
		end
	end 
	return false;
end

----是否有武器架
function DesignMapModel:hasWQJ()
	for i = 1,self.partList:getSize() do
		local pJ = self.partList[i];
		if pJ.i_cao == CustomWeapon.C_CAO2 then
			return true;    
		end
	end 
	return false;
end

----行动部件是否满足数量（比如坦克要有两条履带才能行走）
function DesignMapModel:hasEnoughRunPart()

	local curNum = 0;	--当前行动部位数量
	
	local needNum = 1;	--需要的行动部位数量

	for i = 1,self.partList:getSize() do

		local partModel = self.partList[i];
		
		if partModel.i_cao == CustomWeapon.C_CAO1 then

			curNum = curNum + 1;  
		
		end

		local pjCfg = Config.customWeapon.getConfig(partModel.i_tid);
		
		if tonumber(partModel.i_cao) == CustomWeapon.C_CAO0 then
		
			needNum = tonumber(pjCfg["cao"..tostring(CustomWeapon.C_CAO1)]);
		
		end

	end 

	return curNum >= needNum;

end

-------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------