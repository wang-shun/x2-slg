HuoYanTaBuildingAI = class(BuildingAI);

function HuoYanTaBuildingAI:ctor(entity)
	self.ClassName = "HuoYanTaBuildingAI";
	self.entityOwner = entity;

	self.currentFiringTargetList = List.new();
	self.tmpTargetList = List.new();

end

function HuoYanTaBuildingAI:initBattle()
	printColor("*************************HuoYanTaBuildingAI:initBattle")
	self:setBaseCommand( DianCiTaTowerDefenseCommand.new(self.entityOwner) );
end	

--[[
function HuoYanTaBuildingAI:hasNoFireTarget()

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

function HuoYanTaBuildingAI:fire()

	self:cleanUpDeadTarget();

	--printColor("===========================>HuoYanTaBuildingAI:fire()",self.entityOwner:getAI().ClassName,self.entityOwner:getAI():getCurrentTarget(),self:getCurrentTarget());
	if self.entityOwner:getAI():getCurrentTarget() ~= nil then
		self.entityOwner.mainWeapon:weaponFire(self.entityOwner:getAI():getCurrentTarget());
	end

--	local curTarget = self.entityOwner.mainWeapon.weapomAI:findTarget();
--	if curTarget ~= nil then
--		self.entityOwner.mainWeapon:weaponFire(curTarget);
--	end
	--[[
	火焰塔只会攻击一个目标,  通过技能的areatype 实现多个目标攻击 , 所以ai里的找多个目标的处理 废止
	
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
		self.entityOwner.mainWeapon:weaponFire(self.currentFiringTargetList[1]);
	end

	]]

end		

function HuoYanTaBuildingAI:updateFire()

end	

function HuoYanTaBuildingAI:cleanUpDeadTarget()
	self.tmpTargetList:clear();

	for i = 1 , self.currentFiringTargetList:getSize() , 1 do
		local tgt = self.currentFiringTargetList[i];

		if tgt:isDead() or not tgt:isEnabled() then
			self.tmpTargetList:add(tgt);
		end	
	end	

	for i = 1 , self.tmpTargetList:getSize() , 1 do
		self.currentFiringTargetList:remove(self.tmpTargetList[i]);
	end	
end	

function HuoYanTaBuildingAI:stepAI(entityTime)
	--print("--------------------------HuoYanTaBuildingAI:stepAI  -------------------------- " , self.mCommandStack:getSize())
	local topSuper = getTopSuper(self);
	if topSuper ~= nil then
	    topSuper.stepAI(self , entityTime);
	end

	self:cleanUpDeadTarget();

end	