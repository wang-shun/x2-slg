----------------------------------------------------
---- Entity 基类 保存基础网格信息
----------------------------------------------------
Entity = class(Object);

 

----类型定义
EntityType =
{
	Building = 1,
	-- 建筑
	Troop = 2,-- 部队
}

-- 兵种定义
SoldierType =
{
	Tank = 1,
	-- 坦克
	Chariot = 2,
	-- 战车
	Aircraft = 3,-- 飞机
}
 
function Entity:ctor()
	self.ClassName = "Entity";
end

-- [初始化数据]
--
-- id 				唯一ID
-- team 		    entity 的阵营信息 , 区分敌我
-- entityType 		类型  EntityType 中定义
-- cellx			逻辑坐标x
-- celly			逻辑坐标y
-- areaSize 		占地宽度
-- model 			EntityModel（包装过的数据模型）

function Entity:init(id, team, entityType, cellx, celly, areaSize, obstructionSize, model)

	self.id = id;

	self.team = team;

	self.entityType = entityType;

	self.cellx = cellx;

	self.celly = celly;

	self.model = model;

	-----------------------add by Alex
	self.gameplayBitfield = 0;

	self.currentBattle = nil;
	-- 当前 entity 对应的战斗对象

	self.entityAI = nil;
	-- 当前entity 的ai对象 , 根据情况, 某些类型的entity 会有自己特殊的ai , 这里的ai是指的兵种ai (包括建筑ai) , ai的各种行为在ai的 command里体现

	self.position = FVector3.new(0, 0, 0);
	-- entity的位置

	self.calculatedBoundingBox = false;
	-- 位置和尺寸信息是否已更新 , 如果为false , 则在接下来的某帧里会更新

	self.areaSize = areaSize;
	-- 标准占地尺寸 , 用于park 和家园建筑

	self.obstacleSize = obstructionSize;
	-- 阻挡占位尺寸, 用于 path

	self.simPosition = FVector3.new(self.cellx + Mathf.Floor(self.areaSize / 2), 0, self.celly + Mathf.Floor(self.areaSize / 2));

	self.boundingBox = nil;
	-- 标准包围盒

	self.pathObstructionBoundingBox = nil;
	-- 阻挡包围盒

	self.board = nil;
	-- entity 对应的board 对象

	self.facingInDegrees = 0;
	-- 朝向角度

	self.faceToFire = true;
	-- 是否要正面攻击

	self:setPosition(FVector3.new(self.cellx, 0, self.celly));

	self:updatePositionAndBoundingBox(self:getPosition());

	self.entityTime = 0;
	-- //时钟

	self.mainWeapon = nil;

	self.aqNeedUpdateWeaponAI = false;

	self.buffManager = BuffManager.new(self);

	-- 主人id , 用于士兵 或 无人机等
	self.masterEntityId = -1;

	self.troopAssmblyType = -1;
	-- 兵种配件类型(来自配件表里的 大兵种 , 默认-1 表示不是通过配件产生的兵种 )

	self.weaponList = List.new();

	self._isPause = false;
	-- 是否行为暂定(暂停范围 : 移动 , command)

	self.warKillNum = 0;
	-- 杀敌数
end

-- 销毁
function Entity:dispose()

end

-- 改变阵营
function Entity:changeTeam()

	print("改变阵营 1 ", self.team)
	if self.team == EntityTeam.Defender then
		self.team = EntityTeam.Attacker;
	elseif self.team == EntityTeam.Attacker then
		self.team = EntityTeam.Defender;
	end
	print("改变阵营 2 ", self.team)

end
 
--------------------------------------add by Alex

function Entity:isWall()
	return false;
end

function Entity:getAI()
	return self.entityAI;
end

function Entity:isDead()
	return self.model:getHealth() <= 0;
end

function Entity:getMaxHealth()
	return self.model.maxHealth;
end    

function Entity:addBuff(skill, buffReleaser, weapon)
	print("增加buff skillid=", skill.skillId)
	self.buffManager:addBuff(skill, buffReleaser, weapon);
end    

function Entity:addExtBuff(skill, buffStrid, buffReleaser, weapon)
	print("增加 extbuff ", buffReleaser.id, self.id)
	self.buffManager:extAddBuff(skill, buffStrid, buffReleaser, weapon);
end 

function Entity:setHealth(v, attackerEntity)
	if v <= 0 then
		-- print("------------------------设置血量  " , v , self.ClassName);
		self:SetBit(EntitySign.BIT_DESTROY);
		print("------------------------逻辑死亡  ", v, self.id);
		self.model:setHealth(0);

		-- 如果不是建筑的话, 让攻击者的击杀数量加1 , 无人机和步兵战车 这种主武器离体释放的entity死亡不算击杀
		if self.entityType ~= EntityType.Building and self.masterEntityId == -1 then
			attackerEntity.warKillNum = attackerEntity.warKillNum + 1;
		end

		Board.CheckGameOver();
	else
		print("------------------------设置血量  ", v, self.ClassName)
		self.model:setHealth(v);
		self:SetBit(EntitySign.BIT_BLOOD_CHANGE);
	end
end

function Entity:getSingleHealth()
	return self.model:getSingleHealth();
end    

function Entity:getHealth()
	return self.model:getHealth();
end

function Entity:getPosition()
	return self.position;
end

function Entity:getSimPosition()
	return self.simPosition;
end

function Entity:updateSimPostion()
	-- self.simPosition = FVector3.new(self.cellx + Mathf.Floor(self.areaSize / 2) , 0 , self.celly + Mathf.Floor(self.areaSize / 2));

	self.simPosition.x = self.cellx + Mathf.Floor(self.areaSize / 2);

	self.simPosition.z = self.celly + Mathf.Floor(self.areaSize / 2);
end    

function Entity:setPosition(v)

	self.position = v;

	self.cellx = v.x;

	self.celly = v.z;

	self.calculatedBoundingBox = false;

	self:updateSimPostion();
end

function Entity:getObstacleSize()

	return self.obstacleSize;
end

function Entity:setObstacleSize(v)

	self.obstacleSize = v;
end

function Entity:getAreaSize()
	return self.areaSize;
end

function Entity:setAreaSize(v)
	self.areaSize = v;
	self:updateSimPostion();
end

function Entity:updateBoundingBox_ONLY_FOR_HASH_GRID()
	local result = false;
	if not self.calculatedBoundingBox then
		self.boundingBox = FBoundingBox2d.new(self:getPosition(), self:getAreaSize());
		self.pathObstructionBoundingBox = FBoundingBox2d.new(self:getPosition(), self:getObstacleSize());
		self.calculatedBoundingBox = true;
		result = true;
	end
	return result;
end

-- 暂停范围 : 移动 , command , ai , weaponai 
function Entity:setPause(v)
	self._isPause = v;
	print("----------暂停范围   ", v);
end

function Entity:isPause()
	return self._isPause;
end    

function Entity:ClearAllWeaponBit()
end    

function Entity:ClearAllBuffBit()
	for i = 1, self.buffManager.buffList:getSize(), 1 do
		self.buffManager.buffList[i]:ClearAllBit();
	end
end


function Entity:clearAllBit()
	if self:IsBitSet(EntitySign.BIT_DESTROY) then
		print("Entity:clearAllBit---------------------------------------------------------------------------------------------")
	end

	self:ClearBit(EntitySign.BIT_DESTROY);
	self:ClearBit(EntitySign.BIT_REDUCERESNUM);
	self:ClearBit(EntitySign.BIT_BLOOD_CHANGE);

	self:ClearAllWeaponBit();
	self:ClearAllBuffBit();
end    

function Entity:visitStep(timeDelta)
	self.entityTime = self.entityTime + timeDelta;

	self.buffManager:step(timeDelta);

	-- 更新所有武器
	local weaponLen = self.weaponList:getSize();
	-- print("更新所有武器     " , weaponLen , self)
	for i = 1, weaponLen, 1 do
		self.weaponList[i]:step(timeDelta);
	end

	if self:isPause() then
		return;
	end

	if self.entityAI ~= nil then
		-- print(self.entityAI.ClassName)
		self.entityAI:stepAI(self.entityTime);
		self.entityAI:requestTarget();
	end

	if self.currentBattle ~= nil and self.weaponList ~= nil and self.weaponList:getSize() > 0 then
		--  print("BBBBBB   " , self.id , self.aqNeedUpdateWeaponAI)
		if not self.aqNeedUpdateWeaponAI then
			self.aqNeedUpdateWeaponAI = true;
			self.currentBattle:requestUpdateWeaponAI(self);
		end
	end
end

function Entity:getBBoxDirty()
	return not self.calculatedBoundingBox;
end

function Entity:link(board)
	if board == nil then
		self.board = board;
		return;
	end

	local flag = false;

	if self.board == board then
		flag = false;
	else
		flag = true;
		if self.board ~= nil then
			self.board.GetCollisionMgr():removeEntity(self);
		end
	end

	self.board = board;

	if flag then
		board.GetCollisionMgr():addEntity(self);
	end
end

function Entity:canShootWalls()
	return false;
end

function Entity:updatePositionAndBoundingBox(newPosition)
	-- self.position = newPosition;
	self:setPosition(newPosition);
	self.calculatedBoundingBox = false;
end

function Entity:isMustFaceToFire()
	return self.faceToFire;
end

-- 是否是墙
function Entity:isWall()
	return false;
end


-- 相对目标的半径的最远攻击距离
function Entity:rangeFromCenter(targetEntity)
	if self.mainWeapon ~= nil then
		local range = targetEntity.boundingBox:getApproxRadius();
		print("相对目标的半径的最远攻击距离 = ", range, self.mainWeapon:getAttackLen())
		return range + self.mainWeapon:getAttackLen();
	end

	return MathConst.MaxValue;
end

-- 寻路位置 相对目标 距离 是否大于寻路者的 攻击距离 , 确保寻路到攻击距离处
function Entity:targetOutOfRange(pos, targetEntity)
	-- check weapon range
	if self.mainWeapon ~= nil then
		local distance =(targetEntity:getSimPosition() - pos):magnitude();
		-- print("self.mainWeapon:getAttackLen() = " , self.mainWeapon:getAttackLen())
		if distance > self.mainWeapon:getAttackLen() then
			return true;
		end
	end
	return false;
end



function Entity:initializeAI()
end

function Entity:SetBit(bit)
	self.gameplayBitfield = Bit._or(self.gameplayBitfield, Bit._lshift(1, bit));
end

function Entity:IsBitSet(bit)
	return Bit._and(self.gameplayBitfield, Bit._lshift(1, bit)) ~= 0;
end

function Entity:ClearBit(bit)
	self.gameplayBitfield = Bit._and(self.gameplayBitfield, Bit._not(Bit._lshift(1, bit)));
end

function Entity:batchUpdateWeaponAI(timeDelta)
	self.aqNeedUpdateWeaponAI = false;
	-- 更新所有武器AI
	local weaponLen = self.weaponList:getSize();
	-- print("更新所有武器     " , weaponLen , self)
	for i = 1, weaponLen, 1 do
		self.weaponList[i]:stepAI(timeDelta);
	end
	--   print("CCCCCCCCC   " , self.id , self.aqNeedUpdateWeaponAI)
end    