---------------------------------------------------------
----战斗单位 数据模型
---------------------------------------------------------
TroopEntityModel = class(EntityModel);

function TroopEntityModel:ctor()
	self.ClassName = "TroopEntityModel";

	self.superType = nil;

	self.index = 0;			--操作区的编号

	self.oneAttack = 0;		--单兵伤害

	self.oneDefen = 0;		--单兵防御

	self.oneHealth = 0;		--单兵血量

	self.buildingId = 0;	--如果是防守驻军，则此属性代表 防御驻地的 entity.id 

	self.warBuildingId = 0;	--如果是防守驻军，则此属性代表 防御驻地的 uid ,  战斗返回需要

	self.soldier = nil;	-- SoldierData

end
------------------------------wjg---------------------------------------------------------
 

--这里的warAttr 里的属性都是单兵的, 在战斗中 , 都要乘以实际的兵的数量troopNum
function TroopEntityModel:initAttr(index , buildingId , warBuildingId , troopNum , _warAttr)
	print("************************88888888889999999999999******************* , " , self.uid , troopNum)
	self.index = index;
	self.buildingId = buildingId;
	self.troopNum = troopNum;
 	self.warBuildingId = warBuildingId;
	--super
	self:initWarAttr(_warAttr);
	 
end

--设置士兵信息
--soldierData
function TroopEntityModel:setSoldier(soldier)
	self.soldier = soldier;
	self.uid = soldier.soldierId
end

--获取图纸
function TroopEntityModel:getSoldier()
	return self.soldier;
end 

--获取伤害
function TroopEntityModel:getAttack()
	local curHp = self.health / self.maxHealth;
	local curNum = math.ceil(self.troopNum * curHp / self.maxHealth);
	return curNum*self.oneAttack;
end

--获取防御
function TroopEntityModel:getDefen()
	local curHp = self.health / self.maxHealth;
	local curNum = math.ceil(self.troopNum * curHp / self.maxHealth);
	return curNum*self.oneDefen;
end
