BuildingAI = class(EntityAI);

function BuildingAI:ctor(entity)
	self.ClassName = "BuildingAI";
	self.entityOwner = entity;
end

function BuildingAI:initBattle()
	 
end	

function BuildingAI:fire()
end	


 function BuildingAI:noticeAllWeaponFindTarget()
    if self.entityOwner.mainWeapon ~= nil then

    	if self.entityOwner.mainWeapon.weapomAI ~= nil and  self.entityOwner.mainWeapon.weapomAI.initiativeFindTarget then
           
    		self.entityOwner.mainWeapon.weapomAI:proccessTarget();
    	end	
       	return self.entityOwner.mainWeapon.weapomAI:findTarget();
    end    

    return nil;
end   

function BuildingAI:setMarkTarget(tgt)
	--printColor("---------------------tgt:", tgt);
	self.mTarget = tgt;
	self:clearIdentifyCheck();

	if self.entityOwner:getMainWeapon() ~= nil then
		self.entityOwner:getMainWeapon():setMainTaget(tgt);
	end
end