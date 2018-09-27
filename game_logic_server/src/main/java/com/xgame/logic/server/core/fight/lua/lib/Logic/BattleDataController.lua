-----------------------------------------------
----战斗数据管理器-----------------------------
-----------------------------------------------

BattleDataController = class(Object);

----战斗数据
-- function WarDataModel:ctor(data)
-- 	self.battleId = data.battleId;							--战斗ID
-- 	self.attackData = WarAttackModel.new(data.attackData);	--进攻方数据
-- 	self.defenData = WarDefenModel.new(data.defenData);		--防守方数据
-- 	self.battleType = data.battleType;				--战斗类型 参见 BattleType
-- end


----进攻方数据
-- function  WarAttackModel:ctor(data)
-- 	self.uid = data.attackUid;			-- 进攻方ID
-- 	self.oilNum = data.oilNum;			-- 石油数量
-- 	self.soldiers = {};				-- 士兵列表
-- 	for i=1 , #data.soldiers do
-- 		local soldier = WarSoldierModel.new(data.soldiers[i]);
-- 		table.insert(self.soldiers , soldier);
-- 	end
-- end

----防守方数据
-- function  WarDefenModel:ctor(data)
-- 	self.uid = data.defendUid;				--防守方Uid
-- 	self.buildings = {};					--防守方建筑数据 WarBuildingModel...
-- 	for i=1,#data.buildings do
-- 		local buildingModel = WarBuildingModel.new(data.buildings[i]);
-- 		table.insert(self.buildings , buildingModel);
-- 	end
-- 	self.soldiers = {};						--敌方防守数据
-- 	for i=1 , #data.soldiers do
-- 		local soldier = WarDefenSoldierModel.new(data.soldiers[i]);
-- 		table.insert(self.soldiers , soldier);
-- 	end
-- end

--warDataModel == WarDataModel




function BattleDataController:ctor(warDataModel)

	self.data = warDataModel;	--战斗数据 WarDataModel

	self.isGameOver = false;	--战斗是否结束

	self.leftSoldierMap = Map.new();		--兵的剩余数量管理

	for i=1,#self.data.attackData.soldiers do

		local soldier = self.data.attackData.soldiers[i];

		local leftSoldier = WarAttackLeftSoldierModel.new(soldier);

		self.leftSoldierMap:put(leftSoldier.index,leftSoldier);

	end

	self.destroyRate = 0; --摧毁度 0~1

	self.attackerUid = self.data.attackData.uid; --攻方id

	self.defendUid = self.data.defenData.uid; --守方id
end

function BattleDataController:init()

	--初始化 Board
	Board.reset();

	self.battle = Battle.new(Board);

	self.battle.battleType = self.data.battleType;

	self.battle.battleStarted = true;

	self.battleOverProccesser = BattleOverProccesser.new(self.data.battleType , self);

	print("B BattleDataController:init()" , self.battleOverProccesser)

	---------------------------------------------------------------------------------
	--进攻方数据 self.data.attackData = WarAttackModel (战斗UI界面使用)
	---------------------------------------------------------------------------------
	
	---------------------------------------------------------------------------------
	--防守方数据 self.data.defenData = WarDefenModel
	---------------------------------------------------------------------------------

	--防守方建筑数据
	local buildingList = self.data.defenData.buildings;


	local buildingEntityList = List.new();
	
	for i=1 , #buildingList do
		
		local param = EntityFactory.createBattleDefenBuildingEntity(buildingList[i] , self.data.defenData.uid);

		local building = Board.createEntityByParamModel(param);

		building.entityAI:initBattle(); --初始化战斗

		building.currentBattle = self.battle;
		
		buildingEntityList:add(building);
		 
	end
	print("守方士兵数据 WarDefenSoldierModel.. " , #self.data.defenData.soldiers)
	--防守驻军数据 WarDefenSoldierModel... 
	local soldierList = self.data.defenData.soldiers;
	
	for i=1 , #soldierList do

		local soldier = soldierList[i];

		local paramList = EntityFactory.createBattleDefenTroopEntityList(soldier);

		for j = 1 , #paramList do

			local troop = Board.createEntityByParamModel(paramList[j]);

			troop.currentBattle = self.battle;
			
			troop.troopAI:setParkingType(ParkSize.OneGrid);
			
			troop.troopAI:setBaseCommand( AttackCommand.new(troop) );

		end

	end	
	
	--初始化守方的建筑最大摧毁度
	self.battleOverProccesser:initBuildingDestroyDegree(buildingEntityList);  
	
	--------------------------------------------------------------------------------------------------------------
end

--x,y 	放兵坐标
--index 兵的组ID
--num 	数量
function BattleDataController:putSoldier(x,y,index,num)
	
	local leftSoldier = self.leftSoldierMap:value(index);

	if leftSoldier.curNum<num then
		print("剩余兵数量不足" , leftSoldier.curNum , num);
		return;
	end

	local data = {soldier = leftSoldier.soldier , num = num ,index = index};

	local param = EntityFactory.createBattleAttackTroopEntity(data , x , y);

	local troop = Board.createEntityByParamModel(param);

	troop.currentBattle =  self.battle;

	troop.facingInDegrees = self:getStartFacing(troop:getPosition());

	troop.troopAI:setParkingType(ParkSize.OneGrid);

	--这里创建完指定数量的兵后, 要从leftSoldier里扣除同样数量的 Alex
	leftSoldier.curNum = leftSoldier.curNum - num;

	--BoardView.createEntityView(troop);

	return troop;
end

--获得车体的初始朝向, 默认朝向44,44
function BattleDataController:getStartFacing(pos)
	local sim_facing = (FVector3.new(44,0,44) - pos):normalize();

	local sim_targetAngle = FixedAngle.AngleInDegrees(sim_facing);

	local newFacing = FixedAngle.AngleInDegrees(FixedAngle.FacingForAngleInDegrees(sim_targetAngle));

	return newFacing;
end


--是否放光了所有的兵
function BattleDataController:isEmpty()

	for i=1 , self.leftSoldierMap:size() do

		local leftSoldier = self.leftSoldierMap:valueIndex(i);

		if leftSoldier.curNum>0 then

			return false;

		end

	end

	return true;
end

--isAttackerWin 进攻方是否胜利
function BattleDataController:gameOver(isAttackerWin)

	if self.isGameOver then
		return;
	end

	local winUid = self.data.attackData.uid;

	if isAttackerWin==nil or not isAttackerWin then
		local winUid = self.data.defenData.uid;
	end

	CSHandler.sendMessage(CSHandler.CS_ReqWarEnd , {winUid = winUid , battleId = self.data.battleId});

end

function BattleDataController:checkBattleOverTime(board , timeDelta)
	print("AAAABattleDataController:checkBattleOverTime" )
	print(self.battleOverProccesser)
	print(self.battleOverProccesser.checkBattleOverTime)
	print(board , timeDelta)
	
	self.battleOverProccesser:checkBattleOverTime(board , timeDelta);
end	

--处理战斗结束
function BattleDataController:checkBattleOver(board)
	print("BBBBBattleDataController:checkBattleOver" )
	print(self.battleOverProccesser)
	self.battleOverProccesser:checkBattleOver(board);
end

function BattleDataController:exit()
	if self.battle ~= nil then
		self.battle.battleStarted = false;
	end
	self.battle = nil;
end

--处理战斗结束
function BattleDataController:battleOver(bIsWin)
	self.isGameOver = true;
	self.bIsWin = bIsWin;
	print("ssssssssssssssssssssssssssssss   " , bIsWin)
	--if self.battle:getIsServerMode() then
		--------组织战报数据
	--	self:getBattleReport(bIsWin);
	--end
end

--组织战报数据 , 结构适配WarEndReport.java
function BattleDataController:getBattleReport()
	------处理获胜一方id(攻方id or 守方id)
	local winPlayerId = 0;

print("HHHHHHHHH  " ,  self.bIsWin , self.attackerUid , self.defendUid)
	if self.bIsWin then
		winPlayerId = self.attackerUid;
	else
		winPlayerId = self.defendUid;
	end	

	------摧毁度
	local cuiHuiDu = self.destroyRate * 100;

	local battleSoldierReportList = List.new();

	local battleBuildReportList = List.new();

	local battlePlayerReportMap = Map.new();

	------士兵的战报   Mathf.Ceil(buffReleaser:getHealth() / buffReleaser:getSingleHealth());
	for i=1 , Board.allStaticEntitys:getSize() , 1 do
		local entity = Board.allStaticEntitys[i];

		if entity.masterEntityId == -1 then
			if entity.entityType == EntityType.Troop and entity.masterEntityId == -1 then
				local _deadNum = Mathf.Ceil((entity:getMaxHealth() - entity:getHealth()) / entity:getSingleHealth());
				
				print(">>>>>>>>>>1111111士兵的战报1166666611>>>>>>>>>>>>  " , entity.model.warPlayerId , entity.model.buildingId , entity.model.warBuildingId , entity:getMaxHealth() , entity:getHealth() , entity:getSingleHealth() , entity.model.uid , entity.team)
				
				battleSoldierReportList:add({ playerId=entity.model.warPlayerId , buildUid=entity.model.warBuildingId , uid=entity.model.uid , num=entity.model.troopNum , deadNum=_deadNum , team=entity.team , index=entity.model.index });

				local playerReportInfo = nil;
				if not battlePlayerReportMap:containsKey(entity.model.warPlayerId) then
					playerReportInfo = {killedNum = entity.warKillNum , deadNum=_deadNum , playerId=entity.model.warPlayerId};
					battlePlayerReportMap:put(entity.model.warPlayerId , playerReportInfo);
				else
					playerReportInfo = battlePlayerReportMap[entity.model.warPlayerId];
					playerReportInfo.killedNum = playerReportInfo.killedNum + entity.warKillNum;
					playerReportInfo.deadNum = playerReportInfo.deadNum + _deadNum;
				end	
			else
				print("----------建筑战报--------- " , entity.model.uid , entity.entityType , entity.ClassName , entity:getMaxHealth() , entity:getHealth())
				battleBuildReportList:add({uid=entity.model.uid , lostHp=entity:getMaxHealth() - entity:getHealth() , playerDamageMap=entity.playerDamagerMap});
			end
		end	
	end

	return {winPlayerId=winPlayerId , cuiHuiDu=cuiHuiDu , battleSoldierReportList=battleSoldierReportList , battleBuildReportList=battleBuildReportList , battlePlayerReportMap=battlePlayerReportMap};
end	

function BattleDataController:isBattleOver()
	return self.isGameOver;
end
