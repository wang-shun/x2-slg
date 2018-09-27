BattleEnd_AdapterPvp = class(Object)

function BattleEnd_AdapterPvp:ctor(battleDataManager , battleOverProccesser)

	self.battleEndTime = Config.rtsType.getConfig(BattleType.COUNTRY_SEARCH).time; --Config.global.getNumber(500202);

	print("战斗结束::::::::::::::::::  时间 pvp " , self.battleEndTime , BattleType.COUNTRY_SEARCH , Config.rtsType.getConfig(BattleType.COUNTRY_SEARCH) , Config.rtsType.getConfig(BattleType.COUNTRY_SEARCH).id);

	self.battleTime = 0;
	
	self.battleDataManager = battleDataManager;

	self.battleOverProccesser = battleOverProccesser;
	
	--print("##########################$$$$$$$$$$$$$$$$$$$$$!!!!!!!!!!!!22" , battleDataManager)
end

function BattleEnd_AdapterPvp:checkBattleOverTime(board , timeDelta)

	print(">>>>>>>>>>>>>>  BattleEnd_AdapterPvp.checkBattleOverTime " , self.battleTime , self.battleEndTime)
	
	self.battleTime = self.battleTime  + timeDelta;

	if self.battleTime > self.battleEndTime then
		self:doBattleOver(board);	
	end	
end	

--处理战斗结束
function BattleEnd_AdapterPvp:checkBattleOver(board , allBuildings)
	local isAttackerAllOver = true;

	local isDefenAllOver = true;

	local isBattleOver = false;

	--self.battleTime = self.battleTime  + timeDelta;
	--print(">>>>>>>>>>>>>>  sss22 " , self.battleTime , self.battleEndTime)
	local allEntityNum = board.allStaticEntitys:getSize();
		
	for i = 1 , allEntityNum , 1 do
		local current = board.allStaticEntitys[i];
		
		if current.entityType == EntityType.Building then
			if not current:isWall() and current.model.configID ~= BuildingType.B_JunYing then
				if current.team == EntityTeam.Defender then
					isDefenAllOver = isDefenAllOver and current:isDead();
				elseif  current.team == EntityTeam.Attacker then	
					isAttackerAllOver = isAttackerAllOver and current:isDead();
				end	
			end
		else
			if current.team == EntityTeam.Defender then
				isDefenAllOver = isDefenAllOver and current:isDead();
			elseif  current.team == EntityTeam.Attacker then	
				isAttackerAllOver = isAttackerAllOver and current:isDead();
			end
		end
	end

	if isDefenAllOver then
		isBattleOver = true;
	elseif isAttackerAllOver and not Board.getIsServerMode() and BattleLogicController.isEmpty() then
		isBattleOver = true;
	elseif isAttackerAllOver and Board.getIsServerMode() and self.battleDataManager:isEmpty() then	
		isBattleOver = true;
	end
	
	--print(">>>>>>>>>>>>>>  sss" , isBattleOver)
	if isBattleOver then
		self:doBattleOver(board);	
	end	
end	

function BattleEnd_AdapterPvp:doBattleOver(board)

	local starStr = Config.global.getString(500203);

	local starArr = StrSplit(starStr , ",");

	local maxDestroyHp = 0;

	for i = 1 , board.allStaticEntitys:getSize() , 1 do

		local buildingEntity = board.allStaticEntitys[i];
		
		if buildingEntity.entityType == EntityType.Building then

			local buildingEntityModel = buildingEntity.model;

			local destroyHp = buildingEntityModel:getAttrMaxHealth() - buildingEntity:getHealth();
			
			print("-----------战斗结束时的 血量处理 " , buildingEntity.model:getAttrMaxHealth() , buildingEntity:getHealth() , buildingEntityModel.destroyWeight)

			destroyHp = destroyHp * buildingEntityModel.destroyWeight;

			maxDestroyHp = maxDestroyHp + destroyHp;
		end
	end

	self.battleDataManager.destroyRate = maxDestroyHp / self.battleOverProccesser.buildingMaxDestroy;

	print("雷达扫描pvp 战斗结束 maxDestroyHp=" , maxDestroyHp  , " | buildingMaxDestroy = " , self.battleOverProccesser.buildingMaxDestroy , " | destroyRate=",self.battleDataManager.destroyRate );

	if self.battleDataManager.destroyRate < tonumber(starArr[1]) then
		print("雷达扫描pvp 战斗结束  失败" , self.battleDataManager.destroyRate , tonumber(starArr[1]) , maxDestroyHp , self.battleOverProccesser.buildingMaxDestroy);
		
		self.battleDataManager:battleOver(false);
		
		if not Board.getIsServerMode() then
			BattleLogicController.gameOver(false); 
		end
		return;
	end
	
	local finalStar = 1;

	if self.battleDataManager.destroyRate == tonumber(starArr[#starArr]) then
		finalStar = #starArr;
	else 	
		local lastStarRate = 0;

		for i = 1 , #starArr , 1 do
			if i > 1 then
				lastStarRate = tonumber(starArr[i-1]);
			end
			
			if self.battleDataManager.destroyRate >= lastStarRate and self.battleDataManager.destroyRate < tonumber(starArr[i]) then
				finalStar = i;
				break;
			end	
		end
	end

	print("雷达扫描pvp 战斗结束 胜利 :" , finalStar);
	
	self.battleDataManager:battleOver(true);
	
	if not Board.getIsServerMode() then
		BattleLogicController.gameOver(true , finalStar); 
	end
end	