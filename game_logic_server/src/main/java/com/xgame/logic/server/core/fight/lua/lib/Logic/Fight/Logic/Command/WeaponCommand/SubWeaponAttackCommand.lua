SubWeaponAttackCommand = class(AICommand)


function SubWeaponAttackCommand:ctor(troopOwner , weapon)
	self.ClassName = "SubWeaponAttackCommand";
	self.troopOwner = troopOwner;
	self.weapon = weapon;
	self:init();
end

function SubWeaponAttackCommand:isFinished()
	return false;
end

--副武器不会主动找目标 , 所以这里会根据可攻击类型 和 攻击距离找目标 , 如果目标超出攻击距离 , 则会找下一个目标
--副武器找目标是 根据自身的攻击距离找的
function SubWeaponAttackCommand:stepCommand()
 	--print("step**************SubWeaponAttackCommand************")
	self.super.stepCommand(self);	 

	local tgt = self.weapon:getMainTaget();

	if tgt ~= nil and tgt:isDead() then
		--目标死亡, 丢失
		self.weapon:lostTarget();

		self.weapon:updatePose(true);

	elseif tgt == nil then
		--print("sub----------捕获一个新目标")
		----------捕获一个新目标
		tgt = self.weapon.weapomAI:findTarget();

		if tgt ~= nil then
			local ctr = self.weapon:checkTargetResult(tgt , self.weapon.skAttackLen);
 
			if ctr.canMark then
				self.weapon:setMainTaget(tgt);
			end
		end	
	else

		--距离过远 , 或角度不匹配 都会导致副武器的目标丢失
		 
		local ctr = self.weapon:checkTargetResult(tgt , self.weapon.skAttackLen);
 
		if ctr.canMark then
			self.weapon:updatePose(false, ctr.facing );

			self.weapon:weaponFire(tgt);	
		else
			self.weapon:lostTarget();

			self.weapon:updatePose(true);
		end	
	end	 
end