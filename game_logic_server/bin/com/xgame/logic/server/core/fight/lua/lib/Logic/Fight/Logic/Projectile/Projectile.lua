Projectile = class(Object)

function Projectile:ctor()

	self.gameplayBitfield = 0;

	self.canMiss = false;

	-- 是否朝向目标
	self.turnToFace = false;

	-- 下次伤害时间
	self.nextDamageTime = 0;

	-- 炮弹到达后的持续时间
	self.lifeTime = 0;

	self.isForceStop = false;

	self.position = FVector3.new(0, 0, 0);

	self.tgt = nil;

	self.owner = nil;

	self.weapon = nil;

	self.isArrive = false;

	self.damageCD = 0;

	self.bulletSpeed = 0;

	self.isGuide = false;

	self.duration = 0;

	-- self.proccessTime = 0; -- 有效伤害的总时长

	self.sourcePos = FVector3.new(0, 0, 0);

	self.sourceTgtPos = FVector3.new(0, 0, 0);

	self.stopAttackAfterFirstImpact = false;

	self.mTimeToHit = 0;

	self.shootStartDuration = 0;

	self.shootAfterStart = false;

	self.shootProcessDuration = 0;

--	self.shootFinishDuration = 0;

--	self.lifeTimeAddtive = 0;

	self.doStartDelay = 0;

	self.isInnerInit = false;

	self.isMiss = false;
	-- 是否闪避

	self.isImpact = false;
	
	--子弹真正开始运动的一刻判定（cf）
	self.isBeforePreccessShoot=false;
	--从子弹抵达受伤开始到shootFinishDuration的时间
	self.shootProjectileFinishTime=MathConst.MaxValue;

	self.pid = Mathf.RandomBetween(1000 , 9999);

	self.delay = 0; --技能发射延迟 . 用于技能开始 和 节能结束

end	

function Projectile:getPosition()
	return self.position;
end

function Projectile:SetBit(bit)
	self.gameplayBitfield = Bit._or(self.gameplayBitfield, Bit._lshift(1, bit));
end

function Projectile:IsBitSet(bit)
	return Bit._and(self.gameplayBitfield, Bit._lshift(1, bit)) ~= 0;
end

function Projectile:ClearBit(bit)
	self.gameplayBitfield = Bit._and(self.gameplayBitfield, Bit._not(Bit._lshift(1, bit)));
end

function Projectile:getWeaponTime()
	-- print(self.weapon.ClassName , self.weapon.getWeaponTime)
	return self.weapon:getWeaponTime();
end

function Projectile:step()

	if self.tgt:isDead() then

		self:forceStop()

		return;

	end	

	if self:getWeaponTime() > self.doStartDelay and not self.isInnerInit then

		self:innerInit();
		
	end

	if not self.isInnerInit then
		return;
	end

	if not self:isFinish() then

		local preccessShoot = true;

		if self:getWeaponTime() < self.shootStartDuration and self.shootAfterStart then
			
			preccessShoot = false;
		
		end

		if preccessShoot then
			if not self.isBeforePreccessShoot then

				self.isBeforePreccessShoot = true;

				--if self.skill.isRoot then

				--printColor(self.pid .. "############################ProjectileConst.BIT_START_MOVE########################################################", self.skill.skillId, self.weapon.showId, self:getWeaponTime(),self.shootStartDuration,self.shootAfterStart)
					
				self:SetBit(ProjectileConst.BIT_START_MOVE);
				--end
			end
			if not self.isImpact then
				-- 更新子弹坐标 , 逻辑层面没有高度信息 , 只处理平面
				
				local f = Mathf.Clamp01((self.mTimeToHit -(self.nextDamageTime - self:getWeaponTime())) / self.mTimeToHit);
				--print("Mathf.Clamp01",self.mTimeToHit,self.nextDamageTime,self:getWeaponTime(),(self.nextDamageTime - self:getWeaponTime()),f);
				-- 如果是制导的 , 则锁定敌人, 否则 锁定敌人的初始坐标

				local mTgtPos = FVector3.Zero;

				if self.isGuide then
					self.position = FVector3.Lerp(self.sourcePos, self.tgt:getPosition(), f);

					mTgtPos = self.tgt:getPosition();
				else
					self.position = FVector3.Lerp(self.sourcePos, self.sourceTgtPos, f);

					mTgtPos = self.sourceTgtPos;
				end
				--print("preccessShoot " ,self.position, self.sourcePos,self.sourceTgtPos,f,FVector3.Distance(self.position, mTgtPos) , self.isArrive,self.isGuide,self.skill.skillId ,f , self.mTimeToHit , self.nextDamageTime,self:getWeaponTime())
				if not self.isArrive then
					if FVector3.Distance(self.position, mTgtPos) < 0.5 then
						self:markArrive();

						self.nextDamageTime = 0;

						self.lifeTime = self:getWeaponTime() + self.duration;

						----printColor("子弹到达  markArrive ", self.lifeTime, self.skill.skillId)
					end
				end
			end

			-- if self:getWeaponTime() >= self.nextDamageTime and not self.isImpact then
			if self:getWeaponTime() >= self.nextDamageTime then
				self:onImpact();
			end
			--onImpact后，到执行完
			if self:getWeaponTime()>=self.shootProjectileFinishTime then
				--if self.skill.isRoot then
				--printColor(self.pid .. "############################ProjectileConst.BIT_START_FINISH########################################################", self.skill.skillId, self.weapon.showId, self:getWeaponTime(),self.shootProjectileFinishTime)
				
				self:SetBit(ProjectileConst.BIT_START_FINISH);
				
				self.shootProjectileFinishTime=self:getWeaponTime()+MathConst.MaxValue;
				--end
			end
		end
	end
end

function Projectile:markArrive()
	-- print("子弹到达  ");
	self.isArrive = true;
end

function Projectile:isFinish()
--	--printColor("====>Projectile:isFinish()",self.isForceStop,self.isArrive,self:getWeaponTime(),self.lifeTime);
	
	if self.isForceStop or (self.isArrive and self:getWeaponTime() >= self.lifeTime) then
		return true;
	end

--	if self.tgt ~= nil and self.tgt:isDead() and self.shootProcessDuration == 0 then
--		-- 对于火圈这种技能, 目标死亡, 火圈还是存在
--		return true;
--	end

--	if self.owner:isDead() then
--		return true;
--	end

	return false;
end

function Projectile:forceStop()
	-- print("####  Projectile:forceStop " , self.skill.skillId);
	self.isForceStop = true;
end

function Projectile:onImpact()
	-- if self:getWeaponTime() <= self.proccessTime then
	print("子弹计算伤害 加buff  onImpact", self.areaType, self.skill.skillId)

	self.isImpact = true;

	self.nextDamageTime = self:getWeaponTime() + self.damageCD;

	self.shootProjectileFinishTime=self:getWeaponTime() +self.shootProcessDuration; --+ self.shootFinishDuration;

	--if self.skill.isRoot then

	self:SetBit(ProjectileConst.BIT_START_IMPACT);

	--printColor(self.pid .. "############################ProjectileConst.BIT_START_IMPACT########################################################", self.skill.skillId, self.weapon.showId, self:getWeaponTime(),self.doStartDelay)
	--end

	if self.areaType == 0 then
		-- self.tgt:setHealth(self.tgt:getHealth() - self.tgt:getMaxHealth() * 0.1);

		self.tgt:addBuff(self.skill, self.owner, self.weapon);

	elseif self.areaType > 0 then
		-- 伤害区域类型 , 如果为0则是单点攻击，如果不为0 , 则是填入的数值表示半径为N的圆形范围攻击 ( 非范围攻击的, 如果目标 为null 则终止攻击行为)
		-- (填入1.5表示对半径为1.5个格子的圆形为攻击半径 ) .  只有主体target会播受体特效, 其他的target不会播受体

		-- 可攻击的目标列表
		 
		local tmpTargetList = self.owner.currentBattle:getCanAttackTargetList(self.owner, self.weapon.weapomAI.aiModel, self.weapon.weapomAI.fightEnemy, self.weapon, nil);
		--print("可攻击的目标列表::::::::::::::::::::::::1:::::::::::::::::::::::::::::::::::::::      " , tmpTargetList:getSize())
		-- 范围过滤
		tmpTargetList = self.owner.currentBattle:getTargetInRange(self.areaType, self.position, tmpTargetList);
		--print("可攻击的目标列表::::::::::::::::::::::::::::::::::2:::::::::::::::::::::::::::::      " , tmpTargetList:getSize())

		for i = 1, tmpTargetList:getSize(), 1 do
			-- tmpTargetList[i]:setHealth(tmpTargetList[i]:getHealth() - tmpTargetList[i]:getMaxHealth() * 0.1);
			tmpTargetList[i]:addBuff(self.skill, self.owner);
		end
	end

	--    print("伤害计算 加buff    set BIT_START_IMPACT " , self.tgt:isDead() ,  self.tgt:getHealth() , self.tgt:getMaxHealth());

	if self.stopAttackAfterFirstImpact then
		self:forceStop();
	end
	-- end
end

function Projectile:getShootStartDuration()
	return self.shootStartDuration;
end

function Projectile:getSourcePos()
	return self.sourcePos;
end

function Projectile:getCurrentTgt()
	return self.tgt;
end


function Projectile:getImpactPos()
	if self.isGuide then
		return self.tgt:getSimPosition();
	else
		return self.sourceTgtPos;
	end
end

function Projectile:getTargetPosition()
	return self.tgt:getPosition();
end	

-- 得到总的飞行时间
function Projectile:getTimeToHit()
	return self.mTimeToHit;
end

function Projectile:innerInit()
	self.isInnerInit = true;
	self.isBeforePreccessShoot=false;

	self.areaType = self.skill.areaType;
	self.attackLen = self.skill.attackLen;


	self.shootProcessDuration = self.skill.shootProcessDuration / 1000;
--	self.shootFinishDuration = self.skill.shootFinishDuration / 1000;
---	self.lifeTimeAddtive = self.skill.lifeTimeAddtive / 1000;
	self.stopAttackAfterFirstImpact = self.skill.stopAttackAfterFirstImpact;

	self.sourceTgtPos = self.tgt:getSimPosition();

	--if self.skill.isRoot then
	--printColor(self.pid .. "############################ProjectileConst.BIT_START_SHOOT########################################################", self.skill.skillId, self.weapon.showId, self:getWeaponTime(),self.doStartDelay)
	
	self:SetBit(ProjectileConst.BIT_START_SHOOT);
	--end

--	--printColor("发射  set BIT_START_SHOOT ", "          ", self.skill.skillId, self.weapon.showId, self.skill.shootStartDuration);
	self.damageCD = self.skill.damageCD / 1000;

	if self.damageCD == 0 then --0表示不是持续伤害
		self.damageCD = MathConst.MaxValue;
	end	 

	self.bulletSpeed = self.skill.bulletSpeed;
	self.mTimeToHit = self:timeToHit(self.sourcePos, self.tgt:getSimPosition());
	local shootStartTime = self.skill.shootStartDuration / 1000;
	self.shootStartDuration = self:getWeaponTime() + shootStartTime;
	self.shootAfterStart = self.skill.shootAfterStart;

	if self.skill.shootAfterStart then
		self.nextDamageTime = shootStartTime + self:getWeaponTime() + self.mTimeToHit;
	else
		self.nextDamageTime = self:getWeaponTime() + self.mTimeToHit;
	end

	self.duration = self.shootProcessDuration;
	-- self.duration = self.skill.shootFinishDuration / 1000;

	self.isGuide = self.skill.isGuide;

	--[[
    //if (!this.isGuide)
    //{
    //    this.lifeTime = this.nextDamageTime + this.duration + this.lifeTimeAddtive;
    //}
    //else
    //{
    //    //如果是制导类的,stayTime 是子弹到达目标后, 用当前时间 + 持续时间
    //    this.lifeTime = float.MaxValue;
    //}
    ]]

	self.lifeTime = MathConst.MaxValue;

	-- self.proccessTime = MathConst.MaxValue;
end 

function Projectile:fire(selfPos, _skill, _owner, _tgt, _weapon, delay, _isMiss)

	if delay == nil then
		self.delay = 0;
	else
		self.delay = delay;	
	end

	-- 	print(delay);

	self.isMiss = _isMiss;
	-- 此次攻击是否miss
	self.sourcePos = selfPos;
	self.owner = _owner;
	self.tgt = _tgt;
	self.weapon = _weapon;
	self.skill = _skill;
	self.doStartDelay = self:getWeaponTime() + self.delay;
	self:SetBit(ProjectileConst.BIT_START_PROJECTILE);
end

function Projectile:isRoot()
	return self.skill.isRoot;
end	

function Projectile:getSkill()
	return self.skill;
end

-- 特效首次到达目标的时间
function Projectile:timeToHit(from, to)
	print("timeToHit",from,to,self.bulletSpeed);
	if self.bulletSpeed == 0 then
		return 0;
	end
	return FVector3.Distance(from, to) / self.bulletSpeed;
end

function Projectile:remove()
	--if self.skill.isRoot then
	self:SetBit(ProjectileConst.BIT_PROJECTILE_REMOVE);
	--end
end

function Projectile:stopAttack()
end

--[[
-- 该技能当前是否会阻止 新的技能释放
function Projectile:blockSkillQueue()

	
	if self.shootFinishDuration > 0 then
		return self:getWeaponTime() < self.lifeTime;
	end

	return self:getWeaponTime() < self:getShootStartDuration();
end
]]




































    







