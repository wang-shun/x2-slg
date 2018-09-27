-------------------------------------
----entity 参数生成器
-------------------------------------
EntityFactory = {
	
 
};

-------------------------------------------------------------------------------------------------------------------------
----建筑
-------------------------------------------------------------------------------------------------------------------------
function EntityFactory.createHomeBuildingEntity(buildInfo)
	
	local buildTransform = Player.home.getBuildTransform(buildInfo.uid);
		
	local buildingConfig = Config.building.getConfig(buildInfo.configID);
	
	--参数组装-------------------------------------
	
	local team = EntityTeam.Defender; 				--分组

	local entityType = EntityType.Building;		--类型

	local cellx = 0;							--坐标x

	local celly = 0;							--坐标y

	--勘探开发院 坐标前端写死(后面要改到服务端)
	-- if buildInfo.configID == BuildingType.B_Prospect then
	-- 	cellx = 19;
	-- 	celly = 63;
	-- 	mObstructionSize = 0;
	-- else
	if buildInfo.configID == BuildingType.B_MiningVehicle then	--采矿车
		cellx = 17;
		celly = 64;
		mObstructionSize = 0;
	else
		cellx = buildTransform.position.x;
		celly = buildTransform.position.y;
	end

	local mSize = buildingConfig.size;			--尺寸
	local mObstructionSize = BoardUtil.GetObstructionSize(mSize);	--阻挡尺寸(战斗用)
	local uid = buildInfo.uid;
	local configID = buildingConfig.configID;
	local level = buildInfo.level;
	local childType = buildingConfig.type;
	local state = buildInfo.state;
	local resourceID = buildInfo.resourceID;
	local model = BuildingEntityModel.new(uid , configID , level , childType , state , resourceID , buildingConfig.skillArr , buildingConfig.ai);
	
	local rsModel = EntityParamModel.new(team,entityType,cellx,celly,mSize,mObstructionSize,model);
	--print("::" ,rsModel.cellx,rsModel.celly,rsModel.mSize)
	return rsModel;

end


function EntityFactory.createPreBuildingEntity(configID , x , y)
	
	local buildingConfig = Config.building.getConfig(configID);
	
	--参数组装-------------------------------------

	local team = EntityTeam.Defender; 			--分组

	local entityType = EntityType.Building;		--类型

	local cellx = x;							--坐标x

	local celly = y;							--坐标y

	local mSize = buildingConfig.size;			--尺寸

	local mObstructionSize = BoardUtil.GetObstructionSize(mSize);	--阻挡尺寸(战斗用)

	local uid = Player.home.getSimBuildingUid(configID);
	
	local configID = buildingConfig.configID;
	
	local level = 1;
	
	local childType = buildingConfig.type;
	
	local state = BuildingState.PRE_BUILDING;
	
	local model = BuildingEntityModel.new(uid , configID , level , childType , state,nil , buildingConfig.skillArr , buildingConfig.ai);
	
	return EntityParamModel.new(team,entityType,cellx,celly,mSize,mObstructionSize,model);

end


--驻军部队
function EntityFactory.createHomeDefenTroopEntity(soldierData)

	local paramList = {};

	local soldier = Player.soldier.getSoldier(soldierData.soldierId);

	local num = soldierData.num;

	local buildId = soldierData.buildId;

	local buildingEntity = Board.getBuildingEntityByUID(buildId);

	if soldier ~= nil and buildingEntity~= nil then

		-- 一个防御驻地的兵最多分解为三个实体（平均分配数量）
		local aveList = BoardUtil.GetAverageSoldierList(num , 1);--老谭说改成1个

		local bcx = buildingEntity.cellx;

		local bcy = buildingEntity.celly;

		local bcz = buildingEntity:getAreaSize();

		--兵种位置分配
		local posList = BoardUtil.GetDefenTroopPostionList(bcx,bcy,bcz,bcz);

		for j=1 , #aveList do
			
			local param = EntityFactory.createTroopEntity( EntityTeam.Defender , posList[j].x , posList[j].y , soldier , aveList[j] , j , nil , buildingEntity.id , 0 , buildId);
			
			table.insert( paramList , param);

		end

	end

	return paramList;

end


---------------------------------------------------------------------------------------------------------------------------------
----部队
---------------------------------------------------------------------------------------------------------------------------------

--战斗建筑
function EntityFactory.createBattleDefenBuildingEntity(warBuilding , defenerUid)

	local buildInfo = warBuilding.buildInfo;

	local buildingConfig = Config.building.getConfig(warBuilding.buildInfo.configID);

	--参数组装----------------------------------------------------------------------------
	
	local team = EntityTeam.Defender; 			--分组

	local entityType = EntityType.Building;		--类型

	local cellx = warBuilding.cellx;			--坐标x

	local celly = warBuilding.celly;			--坐标y

	local mSize = buildingConfig.size;			--尺寸

	local mObstructionSize = BoardUtil.GetObstructionSize(mSize);	--阻挡尺寸(战斗用)

	local uid = buildInfo.uid;
	
	local configID = buildingConfig.configID;
	
	local level = buildInfo.level;
	
	local childType = buildingConfig.type;
	
	local state = buildInfo.state;
	
	local resourceID = buildInfo.resourceID;

	local destroyWeight = buildingConfig.destroyParam;
	
	local model = BuildingEntityModel.new(uid , configID , level , childType , state , resourceID , buildingConfig.skillArr , buildingConfig.ai , warBuilding.warAttr , destroyWeight , buildInfo.leftResNum , defenerUid);

 	--print("---建筑血量------hp-----------    " , configID , warBuilding.warAttr.hp)

	return EntityParamModel.new(team,entityType,cellx,celly,mSize,mObstructionSize,model);

end

--战斗驻军部队
---soldier   :  WarDefenSoldierModel
function EntityFactory.createBattleDefenTroopEntityList(soldier)
	
	local paramList = {};
	
	local buildingUid = soldier.buildingUid;--附属的建筑Uid , 驻防
	
	local num = soldier.soldier.soldier.num;

	----兵种位置分配
	local buildingEntity = Board.getBuildingEntityByUID(buildingUid);

	local _x = 0;

	local _y = 0;

	local _bid = 0;

	if buildingEntity == nil then

		print("防守驻军 对应的建筑 不存在 ， 看到这个日志来找我(金贵)。。。" , num);

		_x = soldier.soldier.position.x;

		_y = soldier.soldier.position.y;

		local param = EntityFactory.createTroopEntity( EntityTeam.Defender , _x , _y , soldier.soldier.soldier , num , soldier.soldier.index , soldier.soldier.warAttr , _bid , soldier.soldier.playerId , buildingUid);
				
		table.insert( paramList , param);

		return paramList;

	else
		local bcx = buildingEntity.cellx;

		local bcy = buildingEntity.celly;

		local bcz = buildingEntity:getAreaSize();

		local posList = BoardUtil.GetDefenTroopPostionList(bcx,bcy,bcz,bcz);

		_bid = buildingEntity.id;

		--  一个防御驻地的兵最多分解为三个实体（平均分配数量）
		local aveList = BoardUtil.GetAverageSoldierList(num , 1);

		print("一个防御驻地的兵最多分解为三个实体（平均分配数量）" , buildingUid , num)

		for j=1 , #aveList do

			_x = posList[j].x;

			_y = posList[j].y;

			local param = EntityFactory.createTroopEntity( EntityTeam.Defender , _x , _y , soldier.soldier.soldier , aveList[j] , soldier.soldier.index , soldier.soldier.warAttr , _bid , soldier.soldier.playerId , buildingUid);
				
			table.insert( paramList , param);

		end	

		return paramList;
	end
end

----战斗中创建建兵
-- RTSWarView:getSoldierData()   :   {soldier = soldier , num = num ,index = index};
-- customSoldierData  :   自定义 {soldier = soldier , num = num ,index = index}  
-- customSoldierData.soldier = WarSoldierModel
-- customSoldierData.soldier.soldier = SoldierData
function EntityFactory.createBattleAttackTroopEntity(customSoldierData , x , y)
	print("-------1---------" , customSoldierData.ClassName)
	print("-------2---------" , customSoldierData.soldier.ClassName)
	print("-------3---------" , customSoldierData.soldier.soldier.ClassName)
	return EntityFactory.createTroopEntity( EntityTeam.Attacker , x , y , customSoldierData.soldier.soldier , customSoldierData.num , customSoldierData.index , customSoldierData.soldier.warAttr , 0 , customSoldierData.soldier.playerId , 0);
end	

--team 					分组
--soldierData 			兵数据
--cellx					x
--celly					y
--num 					数量
--attrModel             攻防属性
--buildingEntityId   	建筑ID(防守驻军才有的属性)
--warPlayerId  兵所属的玩家id
function EntityFactory.createTroopEntity(team , cellx , celly , soldierData , num , index , attr , buildingEntityId , warPlayerId , warBuildingUid)

	local mSize = 1;

	local mObstructionSize = 1;

	--TroopEntityModel参数组装-----
	local troopModel = TroopEntityModel.new();

	troopModel:setSoldier(soldierData);
		
	--troopModel:setAttackRange(3);
	
	--troopModel:setCanShootWall(true);
	
	troopModel.superType = soldierData:getDesignMap():getSmallSoldierId();

	if buildingEntityId == nil then
		buildingEntityId = 0;
	end

	troopModel:initAttr(index , buildingEntityId , warBuildingUid , num , attr);

	troopModel.warPlayerId = warPlayerId;

	return EntityParamModel.new(team,EntityType.Troop,cellx,celly,mSize,mObstructionSize,troopModel);
end


-------------------------------------------------------------------------------------------------------------
---出征------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------

--data == RadarBuildPro
function EntityFactory.createRideBuildingEntity(data)
	
	print("factory === > " , data.buildUid , data.buildSid , data.level);
	local configID = data.buildSid;

	local uid = data.buildUid;

	local level = data.level;

	local cellx = data.soldierLoction.x;

	local celly = data.soldierLoction.y;

	local buildingConfig = Config.building.getConfig(configID);

	local childType = 1;
	
	local mSize = 3;

	if buildingConfig ~= nil then
		mSize = buildingConfig.size;			--尺寸
		childType = buildingConfig.type;
	else
		mSize = 3;
		childType = 1;
	end

	local mObstructionSize = BoardUtil.GetObstructionSize(mSize);
	
	local team = EntityTeam.None; 				--分组

	local entityType = EntityType.Building;		--类型

	if configID == BuildingType.B_MiningVehicle then --采矿车
		cellx = 17;
		celly = 64;
		mObstructionSize = 0;
	end

	local model = BuildingEntityModel.new(uid , configID , level , childType,nil , buildingConfig.skillArr , buildingConfig.ai);

	local rsModel = EntityParamModel.new(team,entityType,cellx,celly,mSize,mObstructionSize,model);
	
	return rsModel;
end
