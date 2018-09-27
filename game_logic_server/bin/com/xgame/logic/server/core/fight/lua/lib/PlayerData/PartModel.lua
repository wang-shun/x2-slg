-----------------------------------------------------
----配件信息-----------------------------------------
-----------------------------------------------------

PartModel = class(Object)
  
function PartModel:ctor(partId) 
	
	self.i_tid = 0;
	
	self.i_id = 0;
	
	self.str_name = "";
	
	self.str_icon = "";
	
	self.str_showId = "";
 
	self.i_cao = 0;

	self.assembly_pos = 0;
	
	self.group = 0;

	self.pzDiretion = 1;

	self.quality = 0;
	
	self.smallSoldierID = 0;

	self.ctrlInfoList = nil;


	self.callback = nil;

	self.pathKey = nil;

	if partId ~= nil then

		print("-------------partId="..partId);

		local config = Config.customWeapon.getConfig(partId);
		
		if config ~= nil then

			self:initData(config,0,0);

		end

	end

end

--pj : CustomWeaponConfigModel
function PartModel:initData(pj , id , pos)
	
	self.i_tid = pj.id;
	
	self.i_id = id;
	
	self.quality = pj.quality;  
	
	self.str_name = pj.name;

	--self.colorName =  SpriteUtil.getQuilityString(self.str_name,self.quality);
	
	self.str_icon = pj.icon;
	
	self.str_showId = pj.showId;
	
	self.i_cao = pj.type5;
	
	self.assembly_pos = pos;
	
	self.group = pj.type6;  
  	
	self.bigSoldier = pj.type1;--所属大兵种

	self.pzDiretion = pj.fangXiang;

  	self.smallSoldierID = pj.type2;

  	--add by Alex 2017-2-20
	self.skillIdArr = nil;

	self.config = pj;

	if not IsNilOrEmpty(pj.SkillId) then
		self.skillIdArr = List.new();
		local _skillArr = StrSplit(pj.SkillId , ";");
		for i = 1 , #_skillArr , 1 do
			self.skillIdArr:add(tonumber(_skillArr[i]));
		end	
	end	

  	self.ai = tonumber(pj.ai);
end	

--获取武器类型
function PartModel:getArmsType()

	if tonumber(self.i_cao) == CustomWeapon.C_CAO3 then

		return ArmorType.mailArmor;--主武器

	elseif tonumber(self.i_cao) == CustomWeapon.C_CAO4 then

		return ArmorType.viceArmor;--副武器

	else

		return ArmorType.notArmor;--非武器

	end
end

--是否有灯光
function PartModel:isLight()
	
	if self.i_cao == CustomWeapon.C_CAO2 then
		
	end
	return false;

end

function PartModel:getWeaponPJCtrlInfo( weaponTz , callback)
	
	self.pathKey = weaponTz:getViewPath(self);

	self.callback = callback;
	
	if Config.customWeaponModel.configMap:containsKey(self.str_showId) == false then

		local path = SLG_ResourcePath.configPath;--ziJianConfigPath;
		
		local pjMeta = Config.pjConfig:getPjMetaConfig(self.str_showId);
		
		--print("#########################!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!111114444" )
		
		self:loadComplete(pjMeta , self.str_showId , 0);
		
		--ResourceManager:getConfig(self.str_showId, path, self.loadComplete , self);
	else

		if self.callback then

			local ctrlInfo = self:getCtrlInfo();
			self.callback(ctrlInfo);

		end

	end

end

function PartModel:loadComplete(s_cfg , s_cfgName , s_IDs) 
  
	local arr_s_dataArr = StrSplit(s_cfg , "\n");

    local resultList = {};

    for i = 2, #arr_s_dataArr do
        
        local s_rowData = arr_s_dataArr[i];
		
        if s_rowData ~= ""  then
 
            local mainDataArr = StrSplit(s_rowData , ";");
            
			local keyDataArr = StrSplit(mainDataArr[1] , ",");

			local _arr = StrSplit(mainDataArr[2],",");
			
			local pjCtrlInfo = WeaponPJCtrlConfig.new(_arr);			
			
			local _key = "";
			for j = 1 , #keyDataArr do
					
				if j ~= #keyDataArr then
					_key = _key.. keyDataArr[j].."/";
				else
					_key = _key .. keyDataArr[j];
				end	

			end	


			table.insert( resultList, {_key,pjCtrlInfo});
 			           
        end
    end
    Config.customWeaponModel.configMap:put(s_cfgName,resultList);   

    if self.callback then

    	local ctrlInfo = self:getCtrlInfo();
		self.callback(ctrlInfo);

	end

end

function PartModel:getCtrlInfo()
	
	local list = Config.customWeaponModel.configMap:value(self.str_showId);

	if list == nil or #list <= 0 then
		return nil;
	end
	for i = 1 , #list do

		local obj = list[i];
		local ctrlInfo = obj[2];

		if self.pathKey == obj[1] then

			return obj[2];

		end
	
	end
	return nil;
end

function PartModel:getWeaponSubCtrlPathList(weaponTz)

  local pathList = List:new();
  local pathStr = weaponTz:getViewPath(self);

  local ctrlConfig = self:getCtrlInfo();

  if ctrlConfig.subList then

	    local pJLen = #ctrlConfig.subList;        

	    for i = 1 , pJLen do

	        local name = ctrlConfig.subList[i];
	        local value = pathStr .. "/"..tostring(i).."/"..name;
	        pathList:add(value);
	        
	    end

	end
	
	return pathList;

end

-----------------------------------------------------
-----------------------------------------------------
-----------------------------------------------------