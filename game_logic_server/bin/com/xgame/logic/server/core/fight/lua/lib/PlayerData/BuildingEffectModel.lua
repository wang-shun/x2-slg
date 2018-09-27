------------------------------------------
----建筑效果(例： 援建次数)
------------------------------------------
BuildingEffectModel = class(Object);

function BuildingEffectModel:ctor(name,valueList,isOpenSoldier)
	
	self.name = name;					--名字
	
	self.valueList = valueList;			--值
	
	self.isOpenSoldier = isOpenSoldier;	--是否是

	if self.isOpenSoldier == nil then
		self.isOpenSoldier = false;
	end

end



function BuildingEffectModel:getValueByLevel(level)

	if self.isOpenSoldier ~= nil and self.isOpenSoldier == true then

		local id = self.valueList[tonumber(level)];

		if id ~= nil then

			local soldier = Config.global.getSystemSoldier(id);

			if soldier ~= nil then
				return soldier.name;
			end

		end

		return nil;

	end 

	return self.valueList[tonumber(level)];

end