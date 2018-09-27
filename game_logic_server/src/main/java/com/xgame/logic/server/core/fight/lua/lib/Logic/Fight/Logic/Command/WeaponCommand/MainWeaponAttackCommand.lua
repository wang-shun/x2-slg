MainWeaponAttackCommand = class(AICommand)


function MainWeaponAttackCommand:ctor(troopOwner , weapon)
	self.ClassName = "MainWeaponAttackCommand";
	self.troopOwner = troopOwner;
	self.weapon = weapon;
	self:init();
--print("------------------MainWeaponAttackCommand")
	self.findNewTargetDelayFrame = 0;
end


function MainWeaponAttackCommand:isFinished()
	return false;
end

--主武器会主动找目标, 所以这里只会根据可攻击类型找目标 , 找到后直到目标嗝屁 , 才会找下一个目标
--主武器找目标是全屏找的
function MainWeaponAttackCommand:stepCommand()
 	--print("step**************WeaponAttackCommand************")
	self.super.stepCommand(self);	 

	local tgt = self.weapon:getMainTaget();
 
	if self.findNewTargetDelayFrame > 0 then
		self.findNewTargetDelayFrame = self.findNewTargetDelayFrame - 1;
	end	

	if tgt ~= nil and tgt:isDead() then
		--目标死亡, 丢失
		self.weapon:lostTarget();

		self.weapon:updatePose(true);

		self.findNewTargetDelayFrame = 30; --延迟30帧 找新目标
	elseif tgt == nil then
		if self.findNewTargetDelayFrame <= 0 then
			--print("----------捕获一个新目标", self.weapon.ClassName , self.weapon.weapomAI.ClassName)
			tgt = self.weapon.weapomAI:findTarget();
			--print("----------捕获一个新目标", tgt)
			if tgt ~= nil then
				local ctr = self.weapon:checkTargetResult(tgt , self.weapon.skAttackLen);
	 
				if ctr.canMark then
					--print("----------捕获一个新目标222")
					self.weapon:setMainTaget(tgt);
				end	
			end
		end
	else
		local ctr = self.weapon:checkTargetResult(tgt , MathConst.MaxValue);
 
		--正常来说主武器 在有逻辑过滤后的目标后(攻击类型匹配) 不会回到待机姿态
		if ctr.canMark then

			self.weapon:updatePose(false, ctr.facing);

			--self.weapon:weaponFire();
		end	
	end	 
end