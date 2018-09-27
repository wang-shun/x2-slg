DianCiTaBuildingAI = class(BuildingAI);

function DianCiTaBuildingAI:ctor(entity)
	self.ClassName = "DianCiTaBuildingAI";
	self.entityOwner = entity;

	self.currentFiringTargetList = List.new();
	self.tmpTargetList = List.new();

end

function DianCiTaBuildingAI:initBattle()
	printColor("*************************DianCiTaBuildingAI:initBattle")
	self:setBaseCommand( DianCiTaTowerDefenseCommand.new(self.entityOwner) );
end	

--[[
function DianCiTaBuildingAI:hasNoFireTarget()

	local allTarget = self.entityOwner.mainWeapon.weapomAI:findAllTarget();
--print("%%%%%%%%%%%%     " , allTarget:getSize() ,  self.entityOwner.mainWeapon.weapomAI.preFocusList:getSize())
	for i = 1 , allTarget:getSize() , 1 do
		local tgt = allTarget[i];

		if not self.currentFiringTargetList:contains(tgt) then
			return true;
		end	
	end
	
	return false;
end	
]]

function DianCiTaBuildingAI:fire()

	self:cleanUpDeadTarget();

	--local curTarget = self.entityOwner.mainWeapon.weapomAI:findTarget();

	if self.entityOwner:getAI():getCurrentTarget() ~= nil then
		self.entityOwner.mainWeapon:weaponFire(self.entityOwner:getAI():getCurrentTarget());
	end
	--if curTarget ~= nil then
	--self.entityOwner.mainWeapon:weaponFire(self.entityOwner:getAI():getCurrentTarget());
	--end
	--[[
	电磁塔  会虚拟出2个weapon , 每个weapon 只会攻击一个目标,  故原来的找多个目标的处理 废止

	local allTarget = self.entityOwner.mainWeapon.weapomAI:findAllTarget();
 
	local tgt = nil;

	for i = 1 , allTarget:getSize() , 1 do
		 
		if not self.currentFiringTargetList:contains(allTarget[i]) then
			 
			if  not allTarget[i]:isDead() and  allTarget[i]:isEnabled() and not self.entityOwner:targetOutOfRange(self.entityOwner:getSimPosition() , allTarget[i])  then
				tgt = allTarget[i];
				break;
			end
		end	
	end
 
	if tgt ~= nil then
		--print("-------------------------------------------------------------------------------------------------------------------  diancita  fire " , tgt)
		self.currentFiringTargetList:add(tgt);
	end

	if self.currentFiringTargetList:getSize() > 0 then
		self.entityOwner.mainWeapon:weaponFireMultiple(self.currentFiringTargetList);
	end

	]]
end	

function DianCiTaBuildingAI:updateFire()

end	

function DianCiTaBuildingAI:cleanUpDeadTarget()
	self.tmpTargetList:clear();

	for i = 1 , self.currentFiringTargetList:getSize() , 1 do
		local tgt = self.currentFiringTargetList[i];

		if tgt:isDead() then
			self.tmpTargetList:add(tgt);
		end	
	end	

	for i = 1 , self.tmpTargetList:getSize() , 1 do
		self.currentFiringTargetList:remove(self.tmpTargetList[i]);
	end	
end	

function DianCiTaBuildingAI:stepAI(entityTime)
	--print("--------------------------DianCiTaBuildingAI:stepAI  -------------------------- " , self.mCommandStack:getSize())
	local topSuper = getTopSuper(self);
	if topSuper ~= nil then
	    topSuper.stepAI(self , entityTime);
	end

	self:cleanUpDeadTarget();

end	