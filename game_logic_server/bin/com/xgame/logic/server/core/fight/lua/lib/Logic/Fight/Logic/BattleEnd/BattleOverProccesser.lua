BattleOverProccesser = class(Object)

function BattleOverProccesser:ctor(battleType , battleDataManager)
	self.battleType = battleType;

	self.buildingMaxDestroy = 0;--建筑的总的摧毁值

	self.battleEndHandle = nil;

	self.totalBobResMap = Map.new(); --这场战斗掠夺的资源 key:资源类型(ResType) , value:资源值
	print("IUIUIUIUIUIUI   " ,  self.battleType )
	if self.battleType == BattleType.COUNTRY_SEARCH then
		self.battleEndHandle = BattleEnd_AdapterPvp.new(battleDataManager , self);
	elseif self.battleType == BattleType.WORLD_CITY then
		self.battleEndHandle = BattleEnd_WorldCity.new(battleDataManager, self);
	elseif self.battleType == BattleType.EXPLORER then
		self.battleEndHandle = BattleEnd_Explorer.new(battleDataManager, self);	
	elseif self.battleType == BattleType.TEAM_ATTACK then
		self.battleEndHandle = BattleEnd_TeamAttack.new(battleDataManager, self);	
	elseif self.battleType == BattleType.CAMP then
		self.battleEndHandle = BattleEnd_Camp.new(battleDataManager, self);		
	elseif self.battleType == BattleType.TERRITORY then
		self.battleEndHandle = BattleEnd_Territory.new(battleDataManager, self);	
	elseif self.battleType == BattleType.ALLIANCE_ACTIVITY_FIGHT then
		self.battleEndHandle = BattleEnd_AllianceActivityFight.new(battleDataManager, self);	
	elseif self.battleType == BattleType.RVR_FIGHT then
		self.battleEndHandle = BattleEnd_RvrFight.new(battleDataManager, self);	
	elseif self.battleType == BattleType.MONSTER_INVASION then
		self.battleEndHandle = BattleEnd_Monster_Invasion.new(battleDataManager, self);		
	elseif self.battleType == BattleType.BOSS_FIGHT then
		self.battleEndHandle = BattleEnd_BossFight.new(battleDataManager, self);	
	elseif self.battleType == BattleType.FUBEN_FIGHT then
		self.battleEndHandle = BattleEnd_FubenFight.new(battleDataManager, self);
	elseif self.battleType == BattleType.WORLD_MOSTER then
		self.battleEndHandle = BattleEnd_WorldMonster.new(battleDataManager, self);	
	else 
		self.battleEndHandle = BattleEnd_Explorer.new(battleDataManager, self);							
	end	
	
	--print("##########################$$$$$$$$$$$$$$$$$$$$$!!!!!!!!!!!! " , battleDataManager)
	
	self.allBuildings = nil;
	
	self.battleDataManager = battleDataManager;
end	

--初始化建筑的总的摧毁值
--allBuildings  List<BuildingEntity>
function BattleOverProccesser:initBuildingDestroyDegree(allBuildings)

	self.allBuildings = allBuildings;
	
	for i = 1 , allBuildings:getSize() , 1 do

		local buildingEntity = allBuildings[i];

		local buildingEntityModel = buildingEntity.model;

		self.buildingMaxDestroy = self.buildingMaxDestroy + buildingEntity.model:getAttrMaxHealth() * buildingEntityModel.destroyWeight;
	end	

	print("建筑 总的摧毁值: " , self.buildingMaxDestroy);
end

--处理战斗结束
function BattleOverProccesser:checkBattleOver(board)
	print("--处理战斗结束 ssss" , self.battleEndHandle)
	if self.battleEndHandle ~= nil then
		return self.battleEndHandle:checkBattleOver(board , self.allBuildings);
	end 
end

 function BattleOverProccesser:checkBattleOverTime(board , timeDelta)
 	if self.battleEndHandle ~= nil then
 	print("gggggggggggg");
 	print(self.battleEndHandle)
 	print(self.battleEndHandle.checkBattleOverTime)
		return self.battleEndHandle:checkBattleOverTime(board , timeDelta);
	end
 end	