FaceFireCommand = class(AICommand)

function FaceFireCommand:ctor(troopOwner)
	self.ClassName = "FaceFireCommand";

	self.isFiring = false;

	--print("create**************FaceFireCommand************")
end	

function FaceFireCommand:init()
	self.super.init(self);	 
end	

function FaceFireCommand:isFinished()
	if self.troopOwner:getAI():getCurrentTarget() == nil then
		return true;
	elseif self.troopOwner:getAI():getCurrentTarget():isDead() then
		return true;
	end
			
	return false;
end

function FaceFireCommand:stepCommand()
	--print("create**************FaceFireCommand************")
	if not self.isFiring then
		--如果,不能越墙攻击 , 且与目标之间有墙的话,先拆掉墙
		if  self.troopOwner.troopAI.pathRequest ~= nil and not self.troopOwner:getCanShootOverWallsAlways() then
		--print("XXXXXXXXXXXXXXXXXXX2222222222222 " , self.troopOwner.troopAI.pathRequest.pathDataintermediateWall);
			if  self.troopOwner.troopAI.pathRequest.pathData ~= nil and self.troopOwner.troopAI.pathRequest.pathData.secondaryDestination then
				local boardEntity = self.troopOwner.board.pather:checkForWallsWhenFiring(self.troopOwner, self.troopOwner:getPosition(), self.troopOwner:getAI():getCurrentTarget());
				if boardEntity ~= nil then
					self.troopOwner:getAI():setMarkTarget(boardEntity);
					--print("AAAAAAAAAA" , boardEntity:getPosition().x , boardEntity:getPosition().z)
					--dbgFlag(boardEntity:getPosition().x , boardEntity:getPosition().z);
				end
			end
		end	

		self.isFiring = true;
	end
	-- print("BBBBBBBBB" , self.troopOwner:getAI():getCurrentTarget().model.configID)
	self.troopOwner:getAI():fireAtTarget(self.troopOwner:getAI():getCurrentTarget()); 
end	

function FaceFireCommand:onCommandFinished()
	--print("destroy**************FaceFireCommand************")
	 
end	
