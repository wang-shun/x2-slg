------------------------------------------------------------------------------------------------------------
----士兵模型（前端包装类）----------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------

SoldierModel = class(Object);

function SoldierModel:ctor()
	self.soldierId = nil;
	self.num = nil;
	self.designMap = nil;	--图纸对象
	self.attrList = nil;	--属性列表
end

------------------------------------------------------------------------------------------------------------
----修改 赋值-----------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------

--修改数量
function SoldierModel:setNum(num)
	self.num = num;
end

--soldierBean = SoldierBean
function SoldierModel:setSoldierBean(soldierBean)
	self.soldierId = soldierBean.soldierId;
	self.num = soldierBean.num;
	self.designMap = nil;
end

--fullSoldierBean = FullSoldierBean
function SoldierModel:setFullSoldierBean(fullSoldierBean)
	self.soldierId = fullSoldierBean.soldier.soldierId;
	self.num = fullSoldierBean.soldier.num;
	self.designMap = DesignMapModel.new();
	self.designMap:setDesignMapBean(fullSoldierBean.designMap);
	self.attrList = fullSoldierBean.attrList;
	self.designMap:setAttrData(fullSoldierBean.attrList);
end

------------------------------------------------------------------------------------------------------------
----外部访问接口--------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------

--图纸只能通过此方法访问，不得直接访问designMap
function SoldierModel:getDesignMap()
	if self.designMap == nil then
		self.designMap = Player.designMap.getDesignMap(self.soldierId);
	end
	return self.designMap;
end

--克隆
function SoldierModel:clone()
	local soldierModel = SoldierModel.new();
	soldierModel.soldierId = self.soldierId;
	soldierModel.num = self.num;
	soldierModel.designMap = self.designMap;
	soldierModel.attrList = self.attrList;
	return soldierModel;
end

------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------