-------------------------------------------------------
----Board 管理entity
-------------------------------------------------------
Board = {
	
};


local this = Board;

Board.isInit = false;	-- 是否初始化

Board.entityMap = nil;	-- entity 集合

Board.createIndex = 1;

Board.isServerMode = false; --是否是服务器计算模式

--初始化
function Board.reset()

	if not this.isInit then

		this.isInit = true;

		Board.init();
	end

	this.clear();
end

function Board.init()

	this.isInit = true;

	this.createIndex = 1;

	this.entityMap = Map.new();

	---------------------add by Alex

	this.terrainObstructions = FastBitArray.new(Board.CellCountX * Board.CellCountY);

	this.mWallObstructions = FastBitArray.new(Board.CellCountX * Board.CellCountY);
	--print("-------*******************************------this.mStructureObstructions  " , Board.CellCountX * Board.CellCountY)
	this.mStructureObstructions = FastBitArray.new(Board.CellCountX * Board.CellCountY);

	this.pather = GridPather.new(this);

	this.collisionMgr = CollisionManager.new(this);

	this.troops = List.new();

	this.buildings = List.new();

	this.tmpEntityList = List.new();
	
	this.allStaticEntitys = List.new();

	this.entities = List.new();

	this.TwoPerOffset = FVector3.new(MathConst.OneHalf / MathConst.Three , 0 , -MathConst.OneHalf / MathConst.Three);

	this.buildSimTimestep = MathConst.Zero;

	
end	

--model = EntityParamModel
function Board.createEntityByParamModel(model)
	--print("cfgid     " , model.model.configID)
	return Board.createEntity(model.team,model.entityType,model.cellx,model.celly,model.mSize,model.mObstructionSize ,model.model);
end

--添加一个Entity
-- 
-- team 			分组  EntityTeam 中定义
-- entityType 		类型  EntityType 中定义
-- cellx			逻辑坐标x
-- celly			逻辑坐标y
-- mSize 			占地尺寸
-- mObstructionSize 阻挡尺寸
-- model 			EntityModel（包装过的数据模型）
function Board.createEntity(team,entityType,cellx,celly,mSize,mObstructionSize ,model)
	
	local entity = nil;
	
	local id = this.createEntityID(team,entityType);

	--print("----Board.createEntity  " , cellx , celly , mSize , id , entityType , EntityType.Building)

	if entityType == EntityType.Building then
		
		if BoardUtil.CheckIsBuildingAreaObstruction(cellx , celly , mSize) then

			print("无法创建建筑, 已有阻挡. " , model.configID , cellx , celly , mSize);

			return nil;
		end	

		entity = EntityTool.CreateBuildingFromType(model.configID , model.type , model.level);

		entity:init(id,team,entityType,cellx,celly,mSize,mObstructionSize,model);

		this.AddBuilding(entity);

	elseif entityType == EntityType.Troop then

		entity = EntityTool.CreateNewTroop(model.superType);

		entity:init(id,team,entityType,cellx,celly,mSize,mObstructionSize,model);

		Board.AddTroop(entity);
	else
		warn("Board.createEntity --> the entityType is not exist!          " .. entityType);
		return;
	end
	
	this.entityMap:put(entity.id,entity);
	
	return entity;
	
end

function Board.getDefenTroopList()
	
	local troopList = {};

	for i=1 , this.entityMap:size() do
		
		local entity = this.entityMap:valueIndex(i);
		
		if entity.entityType == EntityType.Troop and entity.model.buildingId > 0 then
			
			table.insert(troopList , entity);
			
		end

	end
	
	return troopList;
	
end

function Board.getTroopsByBuildingUID(uid)
	
	local troopList = {};

	local buildingEntity = this.getBuildingEntityByUID(uid);


	if buildingEntity == nil then

		return troopList;

	end
	
	for i=1 , this.entityMap:size() do
		
		local entity = this.entityMap:valueIndex(i);
		
		if entity.entityType == EntityType.Troop and entity.model.buildingId == buildingEntity.id then
			
			table.insert(troopList , entity);

		end

	end

	return troopList;

end

--根据 id 删除 Entity
function Board.removeEntity(id)
	local entity = this.entityMap:value(id);
	if entity ~= nil then
		BoardBlockManager.clearBlock(entity.id,entity.cellx,entity.celly,entity.areaSize,entity.areaSize);
		BoardUtil.SetObstructionForBuildingAtPosition(entity, false);
	end
	this.entityMap:remove(id);
end

--根据 uid 删除 Entity
function Board.removeEntityByUID(uid)
	for i=1 , this.entityMap:size() do
		local entity = this.entityMap:valueIndex(i);
		if entity.model~=nil and entity.model.uid~=nil and entity.model.uid==uid then
			this.removeEntity(entity.id);
			return;
		end
	end
end

--根据 uid 获取 BuildingEntity
function Board.getBuildingEntityByUID(uid)
	for i=1 , this.entityMap:size() do
		local entity = this.entityMap:valueIndex(i);
		if entity.model~=nil and entity.model.uid~=nil and entity.model.uid==uid then
			return entity;
		end
	end
end



--获取一个 Entity
function Board.getEntity(id)
	return this.entityMap:value(id);
end

--算法 : 生成 Entity 的 id 字段
function Board.createEntityID(group,entityType)

	local id =  group*10000 + entityType*1000 + this.createIndex;

	this.createIndex = this.createIndex+1;

	return id;

end

--清除所有数据
function Board.clear()
print("-------------------------------清除所有数据")
	this.createIndex = 1;
	
	for i = 1 , this.entityMap:size() do
		this.entityMap:valueIndex(i):dispose();
	end

	this.entityMap:clear();

	this.entities:clear();

	this.troops:clear();

	this.buildings:clear();

	BoardBlockManager.clear();

	BoardUtil.ClearAllObstructions();
	
	this.allStaticEntitys:clear();
	
end

---------------------------------------Alex

Board.CellCountX = 87;

Board.CellCountY = 87;

Board.CELL_WIDTH = 1;

Board.CELL_HEIGHT = 1;

Board.CellHalfSize = FVector3.new(MathConst.OneHalf , 0 , MathConst.OneHalf);

Board.CELL_HALF_WIDTH = MathConst.OneHalf;

Board.CELL_HALF_HEIGHT = MathConst.OneHalf;

function Board.GetCellCountTotal()
    return Board.CellCountX * Board.CellCountY;
end

function Board.GetMAX_X()
        return Board.GetMIN_X() + Board.CellCountX * Board.CELL_WIDTH;
end

function Board.GetMIN_X()
        return Board.CELL_WIDTH - Board.CELL_HALF_WIDTH;
end

function Board.GetMAX_Y()
        return Board.GetMIN_Y() + Board.CellCountY * Board.CELL_HEIGHT;
end

function Board.GetMIN_Y()
        return Board.CELL_HEIGHT - Board.CELL_HALF_HEIGHT;
end

function Board.GetTroops()
        return this.troops;
end

function Board.GetPather()
        return this.pather;
end

function Board.AddTroop(troop)
	--print("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$AddTroop")
        this.troops:add(troop);
        troop:initializeAI();
        troop.troopAI:initMovement(nil);
        troop:initWeapon();--------------初始化配件 weapon
        EntityTool.SetupMovement(troop.troopAI:getMovement() , troop.model); ------------初始化移动属性
        this.AddEntityToLists(troop);
        troop:link(this);
        --print("***************&&&&&&&&&&&&&&&&&&&&&&&&&&&&addTroop " , troop.position.x , troop.position.y , troop.position.z , troop.simPosition.x,troop.simPosition.y,troop.simPosition.z,troop.areaSize)
        
        this.allStaticEntitys:add(troop);
end

function Board.AddEntityToLists(entity)
	--print("####################### add entity")
	this.entities:add(entity);
end

function Board.AddBuilding(building)
        building:updateBoundingBox_ONLY_FOR_HASH_GRID();
        this.buildings:add(building);
        this.AddBuildingToLists(building);
        BoardUtil.SetObstructionForBuildingAtPosition(building, true);
        building:initializeAI();
        building:initWeapon();
        this.AddEntityToLists(building);
        building:link(this);
        
        this.allStaticEntitys:add(building);
        --print("***************&&&&&&&&&&&&&&&&&&&&&&&&&&&&addBuilding " , building.position.x , building.position.y , building.position.z , building.simPosition.x,building.simPosition.y,building.simPosition.z,building.areaSize)
end

function Board.getIsServerMode()
	return this.isServerMode;
end

function Board.CheckGameOver()
	 
	if Board.getIsServerMode() then

		ServerFightManager.dataController:checkBattleOver(this);
	else
		BattleLogicController.checkBattleOver(this);
	end	
end

function Board.CheckGameOverTime(timeDelta)
	 
	if Board.getIsServerMode() then

		ServerFightManager.dataController:checkBattleOverTime(this , this.timeDelta);
	else
		BattleLogicController.checkBattleOverTime(this);
	end	
end

function Board.CleanupFallenBuildingsAndTroops()
	this.tmpEntityList:clear();

	local buildingNum = this.buildings:getSize();
	for i = 1 , buildingNum , 1 do
		local current = this.buildings[i];
		if current:isDead() then
	                BoardUtil.SetObstructionForBuildingAtPosition(current, false);
	                this.tmpEntityList:add(current);
	                current:clearAllBit();
	                current:dispose();
	            end
	end

	for i = 1 , this.tmpEntityList:getSize() , 1 do
		this.buildings:remove(this.tmpEntityList[i]);
		this.entities:remove(this.tmpEntityList[i]);
	end	

	------------------troop

	this.tmpEntityList:clear();

	local troopNum = this.troops:getSize();
	for i = 1 , troopNum , 1 do
		local current = this.troops[i];
		if current:isDead() then
	                this.tmpEntityList:add(current);
	                current:clearAllBit();
	                current:dispose();
	            end
	end

	for i = 1 , this.tmpEntityList:getSize() , 1 do
		this.troops:remove(this.tmpEntityList[i]);
		this.entities:remove(this.tmpEntityList[i]);
	end	

end

function Board.GetBuildings()
        return this.buildings;
end

--建筑物归类存放
function Board.AddBuildingToLists(building)
end
function Board.VisitStep(timeDelta)

	this.timeDelta = timeDelta;

	this.CleanupFallenBuildingsAndTroops();
	
	for i = 1 , this.entities:getSize() , 1 do
		local current = this.entities[i];
		current:clearAllBit();
	end	
 
	for i = 1 , this.entities:getSize() , 1 do
		local current = this.entities[i];
		if not current:isDead() then

			current:visitStep(timeDelta);
	            end
	end	

	this.CheckGameOverTime(timeDelta);
 
	this.buildSimTimestep = this.buildSimTimestep + 1;
end

function Board.GetCollisionMgr()
        return this.collisionMgr;
end