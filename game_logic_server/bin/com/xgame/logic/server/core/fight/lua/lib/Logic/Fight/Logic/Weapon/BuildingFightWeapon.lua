BuildingFightWeapon = class(FightWeapon)

function BuildingFightWeapon:ctor(_owner)
	self.ClassName = "BuildingFightWeapon";

	self.weaponType = ArmorType.buildingArmor;

	self.lastTarget = nil;
end

function BuildingFightWeapon:weaponFire(tgt)

	local topSuper = getTopSuper(self);

	local selfPos = self.owner:getPosition();

	self.lastTarget = tgt;

	--topSuper.fire(self, selfPos, tgt);

            topSuper.fire(self,selfPos , tgt);
end	



function BuildingFightWeapon:_setMainTaget(tgt)
        self.weapomAI:setMarkTarget(tgt);
        self:resetAttack();
end

function BuildingFightWeapon:setMainTaget(tgt)
	 self:_setMainTaget(tgt);      
----	printColor("------------------------------------------setMainTaget",tgt.id)
--    if self.weaponGrop:getSize() > 0 then
--        for i = 1 , self.weaponGrop:getSize() , 1 do
--             local wp = self.weaponGrop[i];
--             wp:_setMainTaget(tgt);
--        end    
--        self:_setMainTaget(tgt);      
--    else
--        self:_setMainTaget(tgt);      
--    end  
end   


--[[
function BuildingFightWeapon:weaponFireMultiple(tgtArr)

	local topSuper = getTopSuper(self);

	local selfPos = self.owner:getPosition();

	self.lastTarget = tgt;

	--topSuper.fireMultiple(self, selfPos, tgtArr);

	 if not self.hasShot or self:getWeaponTime() >= self.nextShootTime then
            		self.hasShot = true;
                	topSuper.fireMultiple(self,selfPos , tgtArr);
            end
end	
]]

--[[
function BuildingFightWeapon:getLastTarget()
	return self.lastTarget;
end	
]]