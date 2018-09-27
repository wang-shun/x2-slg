----------------------------------------------
----家园中 部队在某建筑周围警戒
----------------------------------------------
WarningAroundBuildingCommand = class(AICommand)

function WarningAroundBuildingCommand:ctor(troopOwner)
	self.ClassName = "WarningAroundBuildingCommand";

	self.isChangePath = false;

	self.randomPathTime = Mathf.RandomBetween(6,12);

	self.nextPathTime = 0 + self.randomPathTime;
end	

function WarningAroundBuildingCommand:init()
	self.super.init(self);
end	

function WarningAroundBuildingCommand:isFinished()
	return false;
end

function WarningAroundBuildingCommand:stepCommand(entityTime)
 
	local buildingEntity = self.troopOwner.board.getEntity(self.troopOwner.model.buildingId);

	if self.troopOwner:getAI().movement:isMovementComplete() and  entityTime > self.nextPathTime then
		self.nextPathTime = entityTime + self.randomPathTime;

		local p = self:getPoint(buildingEntity,Mathf.RandomBetween(1,3));
 
		self.troopOwner.board.GetPather():requestMoveToPath(self.troopOwner, p , true);

		self.troopOwner:getAI():setMarkTarget(buildingEntity);

		self.isChangePath = true;
		return;
	else
		local posBuilding = buildingEntity:getViewFVector3();

		local posOwner = self.troopOwner:getPosition();

		local dis =  FVector3.Distance(posOwner, posBuilding);

		local isBlock = BoardUtil.CheckIsBuildingObstruction( math.floor(posOwner.x) , math.floor(posOwner.z) );

		if self.targetFVector3 == nil then

			self.targetFVector3 = posBuilding;

			--self.troopOwner.board.GetPather():requestTargetPath(self.troopOwner, buildingEntity);

			local p = self:getPoint(buildingEntity,self.troopOwner.model.index);

			self.troopOwner.board.GetPather():requestMoveToPath(self.troopOwner, p , true);

			self.troopOwner:getAI():setMarkTarget(buildingEntity);
			
			self.isChangePath = true;

			return;

		else

			if self.targetFVector3 ~= posBuilding   then

				self.targetFVector3 = posBuilding;
				
				--self.troopOwner.board.GetPather():requestTargetPath(self.troopOwner, buildingEntity);

				local p = self:getPoint(buildingEntity , self.troopOwner.model.index);

				self.troopOwner.board.GetPather():requestMoveToPath(self.troopOwner, p,true);
				
				self.troopOwner:getAI():setMarkTarget(buildingEntity);
				
				self.isChangePath = true;

				return;
			end
			
		end
	end

	if self.isChangePath then
		if self.troopOwner:getAI().pathRequest ~= nil and self.troopOwner:getAI().pathRequest.pathAvailable then
			self.troopOwner:getAI():getMovement():setPath(self.troopOwner:getAI().pathRequest.pathData.path, self.troopOwner);
			self.isChangePath = false;
		end
	end
	
end


function WarningAroundBuildingCommand:getPoint(building , index)
		
	local v3 = FVector3.new();

	v3.y = 0;

	if index == 1 then

		v3.x = building.cellx;

		v3.z = building.celly;

	elseif index == 2 then

		v3.x = building.cellx;

		v3.z = building.celly + 4;

	else
		v3.x = building.cellx + 5;

		v3.z = building.celly + 2;
	end

	return v3;
end

function WarningAroundBuildingCommand:onCommandFinished()
	self.super.onCommandFinished(self);
end	
