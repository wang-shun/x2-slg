AttackApproachCommand = class(AICommand)

function AttackApproachCommand:ctor(troopOwner)
	self.ClassName = "AttackApproachCommand";

 
	--print("create**************AttackApproachCommand************")
 
end	

function AttackApproachCommand:init()
	self.super.init(self);	 
end	

function AttackApproachCommand:isFinished()
 
	local _isFinish = false;

	if self.troopOwner:getAI():getCurrentTarget() == nil then
		_isFinish = true;
	elseif self.troopOwner:isDead() then
		_isFinish = true;
	elseif self.troopOwner:getAI():targetAliveAndInShortRange() then
		_isFinish = true;
	end	

	if self.troopOwner:getAI().pathRequest ~= nil then
		--print("DD" ,  self.troopOwner:getAI().pathRequest.pathAvailable , self.troopOwner:getAI():isMoving())
		_isFinish = self.troopOwner:getAI().pathRequest.pathAvailable and self.troopOwner:getAI():isMoving();
	end

	return _isFinish;
end
 
function AttackApproachCommand:stepCommand()
	--  print("step**************AttackApproachCommand************  " , self.troopOwner:getAI():isMoving(),self.troopOwner:getAI():getCurrentTarget()  )
	if not self.troopOwner:isDead() and self.troopOwner:getAI():getCurrentTarget() ~= nil then 
		if self.troopOwner:getAI().pathRequest == nil or not self.troopOwner:getAI().pathRequest.pathAvailable then
			self.troopOwner.board.GetPather():requestTargetPath(self.troopOwner, self.troopOwner:getAI():getCurrentTarget());
			--print("__________________path  ",self.troopOwner , self.troopOwner:getAI():getCurrentTarget(), self.troopOwner:getAI():getCurrentTarget():getPosition().x , self.troopOwner:getAI():getCurrentTarget():getPosition().z )
			--self.troopOwner.board.GetPather():requestMoveToPath(self.troopOwner, FVector3.new(50,0,49) , true);
			return;
		end

		if self.troopOwner:getAI().pathRequest.pathAvailable and not self.troopOwner:getAI():isMoving() then
	 		--print("step**************AttackApproachCommand   start  rts  move************  ",self.troopOwner:getAI().pathRequest.pathAvailable ,self.troopOwner:getAI().pathRequest.pathData.path)
 			local pathData = self.troopOwner:getAI().pathRequest.pathData;

 			--print("XXXXXXXXXXXXXXX!100000000000000   " , pathData.intermediateWall , pathData.path);

 			if self.troopOwner:canShootWalls() then
 				pathData = self.troopOwner.board.pather:pathWallCheck(self.troopOwner, pathData , false);
 			end

 			if pathData.path == nil then
 				self.troopOwner:getAI().pathRequest.pathAvailable = false;
 			else 
	 			--print("XXXXXXXXXXXXXXX!11111111111111   " , pathData.path.mGridPathWaypoints);

	 			--for i=0,pathData.path.mGridPathWaypoints:getSize() , 1 do
	 			--	print(pathData.path.mGridPathWaypoints[i])
	 			--end	

	 			--路径上有 wall , 先拆掉wall
	 			if pathData.intermediateWall ~= nil then
	 				self.troopOwner:getAI():setMarkTarget(pathData.intermediateWall);
	 				self.troopOwner:getAI().pathRequest.pathAvailable = false;
	 				return;
	 			end	
	 			--print("^^^^^^^^^^^^^^^^^^" , self.troopOwner:getAI():getMovement())
				self.troopOwner:getAI():getMovement():setPath(pathData.path, self.troopOwner);
			end																																																			
		end	
	end	
end	

function AttackApproachCommand:onCommandFinished()
	 self.troopOwner:getAI().pathRequest.pathAvailable = false;
	--print("destroy**************AttackApproachCommand************")
	self.super.onCommandFinished(self);	 
end	
