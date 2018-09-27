Battle = class(Object)

BattleConst = 
{
	--SIM_STEP_TIME = 409 / 4096

	SIM_STEP_TIME = 1 / 30;
};

function Battle:ctor(_board)
	
	self.batteType = -1;	--战斗类型, BattleType类型
	self.debugMode = true;
	self.simTimestep = MathConst.Zero;
	self.projectileManager = nil;
	self.bkSimTimestep = MathConst.Zero;
	self.battleStarted = false;
	self.gameBoard = _board;
	self.projectileManager = ProjectileManager.new();

	self.needFindTargetEntityStack = List.new();
	self.tmpDelNeedFindTargetEntity = List.new();

	self.needUpdateWeaponAIEntityStack = List.new();
	self.tmpDelUpdateWeaponTargetEntity = List.new();
end	

function Battle:step(timeDelta)
        self:preStep(timeDelta);
        self:mainStep(timeDelta);
        self:postStep(timeDelta);
end
 
function Battle:preStep(timeDelta)
end

function Battle:mainStep(timeDelta)
	local troopNum = self.gameBoard.GetTroops():getSize();
	
	--print("------Battle:mainStep . troopNum = " , troopNum)
	for j = 1 , troopNum , 1 do
	    local troop = self.gameBoard.GetTroops()[j];
	    if not troop:isDead() then
	        troop:updateMovement();
	    end
	end

	--self.gameBoard.CleanupFallenBuildingsAndTroops();
end

function Battle:postStep(timeDelta)
        self:backgroundProcessing(timeDelta);
end

function Battle:requestUpdateWeaponAI(entity)
	--print("requestUpdateWeaponAI  " , entity.id)
	self.needUpdateWeaponAIEntityStack:add(entity);
end	

function Battle:requestTarget(entity)
	self.needFindTargetEntityStack:add(entity);
end	

function Battle:backgroundProcessing(timeDelta)
	self.gameBoard.GetCollisionMgr():updateCollision();

	if self.tmpDelNeedFindTargetEntity:getSize() >= 1 then
		self.tmpDelNeedFindTargetEntity:clear();
	end

	--目前一帧处理一个寻找目标请求
	 
	



	for i = 1 , self.needFindTargetEntityStack:getSize() , 1 do

		local entity = self.needFindTargetEntityStack[i];

		self.tmpDelNeedFindTargetEntity:add(entity);

		if not entity:isDead() and entity:getAI().aqNeedTarget then

			entity:getAI():batchAcquireTarget();

			break;
		else
		
		end	
	end


	for i = 1 , self.tmpDelNeedFindTargetEntity:getSize() , 1 do
		self.needFindTargetEntityStack:remove(self.tmpDelNeedFindTargetEntity[i]);
	end	

	--一帧只更新一个entity的weapon

	--print("帧只更新一个entity的weapon   " , self.needUpdateWeaponAIEntityStack:getSize() )

--[[
	for i = 1 , self.needUpdateWeaponAIEntityStack:getSize() , 1 do

		local entity = self.needUpdateWeaponAIEntityStack[i];

		if not entity:isDead() and entity:isEnabled() and entity.aqNeedUpdateWeaponAI then

			entity:batchUpdateWeaponAI(timeDelta);
		end	
	end

	self.needUpdateWeaponAIEntityStack:clear();
]]
	 
	if self.tmpDelUpdateWeaponTargetEntity:getSize() > 0 then
		self.tmpDelUpdateWeaponTargetEntity:clear();
	end
--print("list ------------  " , self.needUpdateWeaponAIEntityStack:getSize() )

	--for i=1 , self.needUpdateWeaponAIEntityStack:getSize()  , 1 do
	--	print("list -----print-------  " , self.needUpdateWeaponAIEntityStack[i])
	--end	


	if self.needUpdateWeaponAIEntityStack:getSize() > 0 then
		local proccessNum = self.needUpdateWeaponAIEntityStack:getSize();

		proccessNum = Mathf.Floor(proccessNum/3);

		if proccessNum == 0 then
			proccessNum = 1;
		end	

		for i = 1 , proccessNum , 1 do

			local entity = self.needUpdateWeaponAIEntityStack[i];

			self.tmpDelUpdateWeaponTargetEntity:add(entity);

			if not entity:isDead() and entity.aqNeedUpdateWeaponAI then
				
				--print("uuuuuuuuuuuu11111111    "  ,  entity.id)
				entity:batchUpdateWeaponAI(timeDelta);

				break;
			end	
		end


		for i = 1 , self.tmpDelUpdateWeaponTargetEntity:getSize() , 1 do
			--print("uuuuuuuuuuuu2222222222    "  ,  self.tmpDelUpdateWeaponTargetEntity[i].id)
			self.needUpdateWeaponAIEntityStack:remove(self.tmpDelUpdateWeaponTargetEntity[i]);
		end	
	end	
	 







        	--[[
	local troopNum = self.gameBoard.GetTroops():getSize();

	for i = 1 , troopNum , 1 do
		local current = self.gameBoard.GetTroops()[i];
		--print("loop  troops " ,current:isEnabled() , current:isDead()  ,current:getAI():getNeedsTarget() ,current:getPosition().x,current:getPosition().y)
		if current:isEnabled() and not current:isDead() and current:getAI():getNeedsTarget() then
	                    current:getAI():batchAcquireTarget();
	             end
	end 

	troopNum =  self.gameBoard.GetBuildings():getSize();
            
            for i = 1 , troopNum , 1 do
            		local current2 = self.gameBoard.GetBuildings()[i];

            		 if current2:isEnabled() and not current2:isDead()and current2:getAI() ~= nil then

	                    if current2:getAI():getNeedsTarget() then
	                        current2:getAI():batchAcquireTarget();
	                    end
		end
            end
            ]]
 
		 
        self.gameBoard.GetPather():batchPathing(self.gameBoard.GetTroops());
	 
        self.bkSimTimestep = self.simTimestep;
end

--过滤出目标集合内 , 指定范围的目标
function Battle:getTargetInRange(range , currentPosition , entityList)
	print("过滤出目标集合内 , 指定范围的目标   " , range)
	local rsEntityList = List.new();

	for i=1 , entityList:getSize() do

		local entity = entityList[i];

		if not entity:isDead() then

			local distance = (entity:getSimPosition() - currentPosition):magnitude() ;

            			if distance <= range then
            				rsEntityList:add(entity);		
            			end
		end
	end

	return rsEntityList;
end

function Battle:canAttack(owner , aiModel , isFightEnemy , weapon , canAddFunc , tmpTarget)
	local buildingTargetArgs = aiModel:getAttackBuildings();

	local troopTargetArgs = aiModel:getAttackTroops();

	local canAttack = true;

	if isFightEnemy and tmpTarget.team == owner.team then
		canAttack = false;
	elseif not isFightEnemy and tmpTarget.team ~= owner.team then
		canAttack = false;
	elseif canAddFunc ~= nil and not canAddFunc(tmpTarget) then	
		canAttack = false;
	end
	
	--print("Battle:canAttack" , canAttack , tmpTarget.entityType);
	
	if canAttack then
		if tmpTarget.entityType == EntityType.Building then

			local tmpTargetModel = tmpTarget.model;

			local targetBuildingConfig = Config.building.getConfig(tmpTargetModel.configID);

			for i = 1 , buildingTargetArgs:getSize() , 1 do
				if targetBuildingConfig ~= nil and targetBuildingConfig.type == buildingTargetArgs[i].mType then -- 可攻击类型匹配
					local ctr = weapon:checkTargetResult(tmpTarget , MathConst.MaxValue); --判断该武器是否可以攻击到 , 获取目标时 , 检测距离为全场景
					--print("weapon:checkTargetResult&&&&&&&&&&&&&&    " , ctr.canMark , weapon.ClassName , tmpTarget:getSimPosition(),weapon.owner:getPosition()  )
					if not ctr.canMark then
						canAttack = false;
					end	
				end
			end	
		end
		
		if tmpTarget.entityType == EntityType.Troop then
			local tmpTargetModel = tmpTarget.model;

			for i = 1 , troopTargetArgs:getSize() , 1 do
				--print("aaaaaaaaaa" ,   troopTargetArgs[i] ,  tmpTargetModel.designMap:getSmallSoldierId()  )
				if troopTargetArgs[i].mType== tmpTargetModel.soldier:getDesignMap():getSmallSoldierId() then -- 可攻击类型匹配
					--print("bbbbbbb" ,  self.weapon:checkTargetResult(tmpTarget , self.focusLen))
					local ctr = weapon:checkTargetResult(tmpTarget , MathConst.MaxValue); --判断该武器是否可以攻击到 , 获取目标时 , 检测距离为全场景
					if not ctr.canMark then
						canAttack = false;
					end	
				end
			end
		end
	end	

 	return canAttack;
end	


--根据目标类型 和 优先级 过滤出目标

function Battle:getCanAttackTargetList(owner , aiModel , isFightEnemy , weapon , canAddFunc)

	--print("根据目标类型 和 优先级 过滤出目标 1    " , isFightEnemy)
	local wishBuildingTargetArgs = aiModel:getWishAttackBuildings();

	local wishTroopTargetArgs = aiModel:getWishAttackTroops();

	local allBuildingEntityList = owner.board.GetBuildings();

	local allTroopEntityList = owner.board.GetTroops();

	local troopNum = allTroopEntityList:getSize();

	local buildingNum =  allBuildingEntityList:getSize();

	local sourceTgtList = List.new();

	local tmpAllEntityList = List.new();

	local tmpTargetList = List.new();

	for i = 1 , buildingNum , 1 do
		local tmpTarget = allBuildingEntityList[i];
		if not tmpTarget:isDead() then
			if isFightEnemy and tmpTarget.team ~= owner.team then
				tmpAllEntityList:add(tmpTarget);
			elseif not isFightEnemy and tmpTarget.team == owner.team then
				tmpAllEntityList:add(tmpTarget);
			end	
		end	
	end	

	for i = 1 , troopNum , 1 do
		local tmpTarget = allTroopEntityList[i];
		if not tmpTarget:isDead() then
			if isFightEnemy and tmpTarget.team ~= owner.team then
				tmpAllEntityList:add(tmpTarget);
			elseif not isFightEnemy and tmpTarget.team == owner.team then
				tmpAllEntityList:add(tmpTarget);
			end	
		end	
	end

	local allLen = tmpAllEntityList:getSize();

	--print("1 weapon ai  获取一个目标  allLen" , allLen , allBuildingEntityList:getSize() , isFightEnemy , owner.team);
	for i = 1 , allLen , 1 do
		local tmpTarget = tmpAllEntityList[i];

		if tmpTarget.entityType == EntityType.Building then
			local tmpTargetModel = tmpTarget.model;
			local targetBuildingConfig = Config.building.getConfig(tmpTargetModel.configID);
			if targetBuildingConfig ~= nil and wishBuildingTargetArgs:contains(targetBuildingConfig.type) and tmpTarget.team ~= owner.team then
				sourceTgtList:add(tmpTarget);
			end	
		elseif tmpTarget.entityType == EntityType.Troop then
			-- print("weapon ai  获取一个目标  tz type" , tmpTarget.model.designMap:getSmallSoldierId());
			 
			if wishTroopTargetArgs:contains(tmpTarget.model.soldier:getDesignMap():getSmallSoldierId()) then
				sourceTgtList:add(tmpTarget);
			end
		end	
	end	

	if sourceTgtList:getSize() == 0 then
		sourceTgtList = tmpAllEntityList; -- 如果没有优先攻击目标的话, 从所有entity中找
	end

	allLen = sourceTgtList:getSize();
	--print("2 weapon ai  获取一个目标  allLen" , allLen  );
	local canAdd = true;

	for j = 1 , allLen , 1 do
		local tmpTarget = sourceTgtList[j];

		canAdd = self:canAttack(owner , aiModel , isFightEnemy , weapon , canAddFunc , tmpTarget);
		--print("3 weapon ai canAdd" , canAdd  );
		if canAdd then
			tmpTargetList:add(tmpTarget);
		end	
	end	    	

	--[[
	--1 根据目标类型 和 目标优先级 , 找到一批可攻击的目标 
	for i = 1 , buildingTargetArgs:getSize() , 1 do
		for j = 1 , allLen , 1 do
		    	local tmpTarget = sourceTgtList[j];

		    	canAdd = true;

		    	if isFightEnemy and tmpTarget.team == owner.team then
				canAdd = false;
			elseif not isFightEnemy and tmpTarget.team ~= owner.team then
				canAdd = false;
			elseif canAddFunc ~= nil and not canAddFunc(tmpTarget) then	
				canAdd = false;
			end		

			if canAdd then
				--print("*****************    " , tmpTarget.entityType) 
				if tmpTarget.entityType == EntityType.Building then

					local tmpTargetModel = tmpTarget.model;

					local targetBuildingConfig = Config.building.getConfig(tmpTargetModel.configID);
					 
					if targetBuildingConfig ~= nil and targetBuildingConfig.type == buildingTargetArgs[i].mType then -- 可攻击类型匹配
						local ctr = weapon:checkTargetResult(tmpTarget , MathConst.MaxValue); --判断该武器是否可以攻击到 , 获取目标时 , 检测距离为全场景
						
						--print("&&&&&&&&&&&&&&    " , ctr.canMark , weapon.ClassName , tmpTarget:getSimPosition(),weapon.owner:getPosition()  )
						if ctr.canMark then
							tmpTargetList:add(tmpTarget);
						end	
					end
				end
			end	
		end    
	end   

	--print("3 weapon ai  获取一个目标  allLen" , tmpTargetList:getSize());
	--troop
	for i = 1 , troopTargetArgs:getSize() , 1 do
		for j = 1 , allLen , 1 do
		    	local tmpTarget = sourceTgtList[j];

		    	canAdd = true;

		    	if isFightEnemy and tmpTarget.team == owner.team then
				canAdd = false;
			elseif not isFightEnemy and tmpTarget.team ~= owner.team then
				canAdd = false;
			elseif canAddFunc ~= nil and not canAddFunc(tmpTarget) then	
				canAdd = false;	
			end		

			if canAdd then
				if tmpTarget.entityType == EntityType.Troop then
					local tmpTargetModel = tmpTarget.model;
					--print("aaaaaaaaaa" ,   troopTargetArgs[i] ,  tmpTargetModel.designMap:getSmallSoldierId()  )
					if troopTargetArgs[i].mType== tmpTargetModel:getDesignMap():getSmallSoldierId() then -- 可攻击类型匹配
						--print("bbbbbbb" ,  self.weapon:checkTargetResult(tmpTarget , self.focusLen))
						local ctr = weapon:checkTargetResult(tmpTarget , MathConst.MaxValue); --判断该武器是否可以攻击到 , 获取目标时 , 检测距离为全场景
						if ctr.canMark then
							tmpTargetList:add(tmpTarget);
						end	
					end
				end
			end
		end    
	end 
	]]

	return tmpTargetList;
end