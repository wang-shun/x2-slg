SkillShooter = class(Object)

--技能发射器 , 一个武器 , 同时可以释放多个技能 , 所以用技能发射器管理技能的释放, weapon上维护所有的技能发射器
function SkillShooter:ctor(_skill , _owner , _weapon)
	print("技能发射器 , iiiiiiiiid =", _skill.skillId)
	self.skill = _skill;
	self.skillIndex = 0;
	-- 技能索引 , 用于 nextSkill
	self.curSkill = _skill;
	self.skMaxDamageTimeEveryAttack = _skill.attackNum;
	-- 每次技能攻击的最大攻击次数
	self.skAttackLen = _skill.attackLen;
	self.skReloadTime = _skill.reloadTime;
	self.skAttackRate = _skill.attackRate;
	self.skAreaType = _skill.areaType;
	self.owner = _owner;
	self.weapon = _weapon;
	self.curDamageTimeEveryAttack = 0;
	-- 当前已攻击次数
	self.nextShootTime = 0;
	-- 下次射击时间 cd时间
	self.nextCDTime = MathConst.MaxValue;
	self.skillTime = 0;
	self.projectileList = List.new();
	self.deadProjectileList = List.new();
	self.hasShot = false;

end	

function SkillShooter:fire(selfPos, tgt, delay)
	if self.owner.currentBattle.battleStarted then
		--printColor("--------------------------------------------------------->");
		if self:canMakeNewShoot() or not self.hasShot then
			self.hasShot = true;

			--printColor("-----------逻辑 发射子弹(释放技能)", self.curSkill.skillId, self.curSkill.isRoot, self.owner.ClassName, tgt.ClassName, tgt.id, self.weapon.showId,selfPos);

			self.curDamageTimeEveryAttack = self.curDamageTimeEveryAttack + 1;

			local projectile = Projectile.new();

			local isHit = AttrCalc.calcHitRate(self.owner, tgt, self.curSkill);

			projectile:fire(selfPos, self.curSkill, self.owner, tgt, self.weapon, delay, isHit);

			self.projectileList:add(projectile);

			self:setNextShootTime();

			self:setNextSkill();

			-- 处理被攻击者的反击
			if isHit and tgt.mainWeapon ~= nil then
				tgt.mainWeapon.weapomAI:counterAttackTarget(self.owner);
			else
				local groupBuffs = tgt.buffManager.buffMap:values();

				local groupBuffsLen = #groupBuffs;

				if groupBuffsLen > 0 then
					for i = 1, groupBuffsLen, 1 do
						-- print(".....................    " , groupBuffs[i]:getSize() , Mathf.RandomBetween(1 , groupBuffs[i]:getSize()))
						local activeBuff = groupBuffs[i][Mathf.RandomBetween(1, groupBuffs[i]:getSize())];
						activeBuff:extActive(self.curSkill,self);
						break;
						-- 根据buff组 随机一个生效
					end
				end
			end
		end
	end
end

-- 设置下一个技能
function SkillShooter:setNextSkill()
	print("设置下一个技能",self.weapon.showId , self.skill.skillId )
	if self.skill.nextSkillList ~= nil then
		self.skillIndex = self.skillIndex + 1;
		-- print("^^^^^^^^^111^^^^^^^^^^^^^^^^^   " ,  self.skillIndex , self.skill.nextSkillList:getSize() )
		if self.skillIndex > self.skill.nextSkillList:getSize() then
			self.skillIndex = 0;
			self.curSkill = self.skill;
		else
			self.curSkill = self.skill.nextSkillList[self.skillIndex];
		end


		-- print("^^^^^^^^^222^^^^^^^^^^^^^^^^^   " ,  self.curSkill.skillId  )
	end
end	

function SkillShooter:forceStopProjectile()
	for i = 1, self.projectileList:getSize(), 1 do
		self.projectileList[i]:forceStop();
	end
end

-- 是否能释放新的技能
function SkillShooter:canMakeNewShoot()
	 
	--print(">>>>>>>>>>>>  " ,	self:getSkillTime() , self.nextShootTime )
	if self:getSkillTime() < self.nextShootTime then
		return false;
	end	

	return true;
end

function SkillShooter:setNextShootTime()
   	--print("---setNextShootTime  " , self , self.curDamageTimeEveryAttack , self.skMaxDamageTimeEveryAttack, self.skReloadTime ,self.skAttackRate)
	if self.curDamageTimeEveryAttack == self.skMaxDamageTimeEveryAttack then
	    self.nextShootTime = self:getSkillTime() + self.skReloadTime / 1000;
	    self.nextCDTime = self.nextShootTime;
	    self.curDamageTimeEveryAttack = 0;

	    if self.curSkill.isRoot then
		self.weapon:SetBit(WeaponConst.BIT_CD_START);
	   end
	else
	    self.nextShootTime = self:getSkillTime() + self.skAttackRate / 1000;
	end
end

function SkillShooter:step(_deltaTime)
	self.skillTime = self.skillTime + _deltaTime;

	if self.skillTime > self.nextCDTime then
	    
		self.nextCDTime = MathConst.MaxValue;
	   	
	   	if self.curSkill.isRoot then
			self.weapon:SetBit(WeaponConst.BIT_CD_OVER);
		end
	end    

	 self:updateProjectile();

end	
 
function SkillShooter:getSkillTime()
	return self.skillTime;
end	

function SkillShooter:getAllProjectile()
	return self.projectileList;
end	

function SkillShooter:clearAllBit()
	if self.projectileList:getSize() > 0 then
		--print("####################SkillShooter:clearAllBit")
		for  i = 1 , self.projectileList:getSize() , 1 do   
			self.projectileList[i]:ClearBit(ProjectileConst.BIT_START_SHOOT);
			self.projectileList[i]:ClearBit(ProjectileConst.BIT_START_MOVE);
			self.projectileList[i]:ClearBit(ProjectileConst.BIT_START_IMPACT);
			self.projectileList[i]:ClearBit(ProjectileConst.BIT_START_FINISH);
			self.projectileList[i]:ClearBit(ProjectileConst.BIT_PROJECTILE_REMOVE);
		end
	end
end	

function SkillShooter:updateProjectile()
	--处理武器所属的弹药

	--print("处理武器所属的弹药 " , self.projectileList:getSize() , self.skill.skillId)
	if self.projectileList:getSize() > 0 then

	    for  i = 1 , self.projectileList:getSize() , 1 do   

	        if self.projectileList[i]:isFinish() then
	            self.deadProjectileList:add(self.projectileList[i]);
	         else
	         	self.projectileList[i]:step();
	        end
	    end

	    if self.deadProjectileList:getSize() > 0 then
	        for i = 1 , self.deadProjectileList:getSize() , 1 do  
	            print("----------------------projectileList:remove")
	            self.projectileList:remove(self.deadProjectileList[i]);

	            self.deadProjectileList[i]:remove();

	        end

	        self.deadProjectileList:clear();
	    end
	end 
end	

function SkillShooter:resetAttack()
	self.nextShootTime = 0;
	self.curDamageTimeEveryAttack = 0;
end

function SkillShooter:clear()
	print("&&&&&&&&&&&&&&SkillShooter:clear")
	for i = 1 , self.projectileList:getSize() , 1 do
	    self.projectileList[i]:remove();
	end    

	self.projectileList:clear();
end	

--出身即释放技能
function SkillShooter:proccessBronSkill()
	if self.skill.releaseType == 2 then
		self:fire(self.owner:getPosition() , self.owner , 0)
	end
end