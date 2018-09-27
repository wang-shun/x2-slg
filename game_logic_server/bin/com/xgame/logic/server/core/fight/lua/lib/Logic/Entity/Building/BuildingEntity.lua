BuildingEntity = class(Entity)
 
function BuildingEntity:ctor()
	self.ClassName = "BuildingEntity";
	self.superType = BuildingType.Undefined;--建筑模板类型, 同building表里的 id字段
	self.level = 0;
	self._canBeMovedThru = false; --是否可被穿透 (地雷 等)
	self.buildingEntityType = BuildingEntityType.Other; --建筑类型 , 同building表里的 type字段

	self.playerDamagerMap = Map.new();
end	

function BuildingEntity:setHealth(v , attackerEntity)

	local oldHp = self:getHealth();

	local topSuper = getTopSuper(self);

	if topSuper ~= nil then
		topSuper.setHealth(self , v , attackerEntity);
	end

	local newHp = self:getHealth();
	------------记录某个玩家对建筑造成的伤害


print("GFGGGGGGGGG " , attackerEntity.model.warPlayerId)
	if newHp < oldHp then
		local curValue = 0;

		if self.playerDamagerMap:containsKey(attackerEntity.model.warPlayerId) then
			print("BuildingEntity:setHealth   " , self.playerDamagerMap,attackerEntity.ClassName, attackerEntity.model)
			print(attackerEntity.model.warPlayerId)
			curValue = self.playerDamagerMap:get(attackerEntity.model.warPlayerId);
		end

		curValue = curValue + Mathf.Abs(oldHp - newHp);

		self.playerDamagerMap:put(attackerEntity.model.warPlayerId , curValue);
		
		Board.CheckGameOver();
	end	
end	

function BuildingEntity:dispose()

    local topSuper = getTopSuper(self);

    if topSuper ~= nil then
        topSuper:dispose();
    end

    if self.mainWeapon ~= nil then
    	self.mainWeapon:clear();
    end
 
end  

function BuildingEntity:getMainWeapon()
    return self.mainWeapon;
end  

function BuildingEntity:init(id, group, entityType, cellx, celly, areaSize, obstructionSize, model)
	local topSuper = getTopSuper(self);

	if topSuper ~= nil then
		topSuper.init(self, id, group, entityType, cellx, celly, areaSize, obstructionSize, model)
	end

	self.buildingEntityType = model.type;

	self.level = model.level;

	self.position = FVector3.new(cellx, 0, celly);

	if obstructionSize > 0 then
		BoardBlockManager.setBlock(id, cellx, celly, areaSize, areaSize);
	end
end	

-- 初始化建筑基本ai , 如有特殊ai的 , 在子类里重写 , 家园里的建筑也可以有ai
function BuildingEntity:initializeAI()
	if self.model.configID == BuildingType.B_DianCiTa then
		self.entityAI = DianCiTaBuildingAI.new(self);
	elseif self.model.configID == BuildingType.B_HuoYanPao or self.model.configID == BuildingType.B_FangKongPaoTa or self.model.configID == BuildingType.B_JianJiPaoTa or self.model.configID == BuildingType.B_JingFangPaoTa then
		self.entityAI = HuoYanTaBuildingAI.new(self);
	else
		self.entityAI = BuildingAI.new(self);
	end
end

function BuildingEntity:canBeMovedThru()
        return self._canBeMovedThru;
end	

function BuildingEntity:getViewFVector3()
	local xx = self.cellx + BoardBlockManager.getPosOffect(self:getAreaSize());
	local zz = self.celly + BoardBlockManager.getPosOffect(self:getAreaSize());
	return FVector3.new(xx , 0 , zz);
end

--是否是围墙
function BuildingEntity:isWall()
	return self.buildingEntityType == BuildingEntityType.Wall;
end

--是否是矿车
function BuildingEntity:isVehicle()
	return self.model.configID == BuildingType.B_MiningVehicle;
end

function BuildingEntity:initWeapon()

	-- print("yyyyyyyyyyyyyyyyyyyyyyyy    " , self.model.ClassName ,self.model.configID , self.model.skillArr , self.model.ai)

	if not IsNilOrEmpty(self.model.skillArr) and not IsNilOrEmpty(self.model.ai) then


		------------电磁塔 需要虚拟出 2个weapon , 这个待处理

		self.mainWeapon = BuildingFightWeapon.new(self);

		self.weaponList:add(self.mainWeapon);

		self.mainWeapon.isDataReady = true;

		local firstSkillAttackLen = nil;

		--处理武器配件的技能
		local skillList = List.new();

		for i = 1 , self.model.skillArr:getSize() , 1 do
			local skill = EntityTool.CreateWeaponSkill(tonumber(self.model.skillArr[i]));

			skillList:add(skill);
		 
			if firstSkillAttackLen == nil then
				firstSkillAttackLen = skill.attackLen;
			end	
		end	

		local buildingWeaponAI = EntityTool.CreateWeaponAI(self, self.mainWeapon, self.model.ai);

		buildingWeaponAI.focusLen = firstSkillAttackLen;

		self.mainWeapon:setAI(buildingWeaponAI);

		self.mainWeapon:setSkill(skillList);

		printColor("建筑物主武器的攻击距离 : " , self.mainWeapon:getAttackLen())

		--[[
		if self.model.type == BuildingEntityType.Tower_DianCi then ---电磁塔
			self.mainWeapon = BuildingFightWeapon.new(self);
			self.mainWeapon.isDataReady = true;	

			 -----------------------alex ----test
			local skill = EntityTool.CreateWeaponSkill(1);
			self.mainWeapon:setSkill(skill);
			local buildingWeaponAI = EntityTool.CreateWeaponAI(self , self.mainWeapon , 1);
			buildingWeaponAI.focusLen = skill.attackLen;
		    	self.mainWeapon:setAI(buildingWeaponAI);
		elseif self.model.type == BuildingEntityType.Tower_Fire  then
			self.mainWeapon = BuildingFightWeapon.new(self);


			 -----------------------alex ----test
			local skill = EntityTool.CreateWeaponSkill(4);
			self.mainWeapon:setSkill(skill);
			local buildingWeaponAI = EntityTool.CreateWeaponAI(self , self.mainWeapon , 1);
			buildingWeaponAI.focusLen = skill.attackLen;
		    	self.mainWeapon:setAI(buildingWeaponAI);   	
		elseif self.model.type == BuildingEntityType.Tower_Missile  then
			self.mainWeapon = BuildingFightWeapon.new(self);


			 -----------------------alex ----test
			local skill = EntityTool.CreateWeaponSkill(6);
			self.mainWeapon:setSkill(skill);
			local buildingWeaponAI = EntityTool.CreateWeaponAI(self , self.mainWeapon , 1);
			buildingWeaponAI.focusLen = skill.attackLen;
		    	self.mainWeapon:setAI(buildingWeaponAI);   	
		end
		]]
	end	
end

function BuildingEntity:batchUpdateWeaponAI(timeDelta)
	local topSuper = getTopSuper(self);

	if topSuper ~= nil then
		topSuper.batchUpdateWeaponAI(self);
	end
	--[[
	--更新武器AI
	if self.mainWeapon ~= nil then
		self.mainWeapon:stepAI(timeDelta);
	end
	]]
end  

function BuildingEntity:visitStep(timeDelta)

	local topSuper = getTopSuper(self);
	if topSuper ~= nil then
	    topSuper.visitStep(self,timeDelta);
	end
end  

function BuildingEntity:ClearAllWeaponBit()
	if self.mainWeapon ~= nil then
		self.mainWeapon:clearAllBit();
	end	
end  

 