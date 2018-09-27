Movement = class(Object)

Movement.MAX_BLOCKER_TIMESTEPS = 10;

function Movement:ctor()
	self.ClassName = "Movement";
	self.blockers = List0.new();
	self.mStartingPath = false;


	self.moveSpeed = 0;
	self.patroleSpeed = 0;
	self.turnSpeed = 0;
	self.accelTime = 0;
	self.decelTime = 0;
	 
end	


function Movement:getMoveSpeed()
        return self.moveSpeed;
end

function Movement:setMoveSpeed(v)
        self.moveSpeed = v;
end

function Movement:getPatrolSpeed()
        return self.patroleSpeed;
end

function Movement:setPatrolSpeed(v)
        self.patroleSpeed = v;
end

function Movement:getTurnSpeed()
        return self.turnSpeed;
end

function Movement:setTurnSpeed(v)
        self.turnSpeed = v;
end

function Movement:getAccelTime()
        return self.accelTime;
end

function Movement:setAccelTime(v)
        self.accelTime = v;
end

function Movement:getDecelTime()
        return self.decelTime;
end

function Movement:setDecelTime(v)
        self.decelTime = v;
end



-----------------------logic data

function Movement:getSpeed()
        return 0;
end

function Movement:isHalfSpeed()
	return false;
end

function Movement:getPath()
	return nil;
end

function Movement:getDirection()
	return FVector3.new(0,0,0);
end

function Movement:setPath(path, owner)
        if path == nil then
            self.mStartingPath = false;
            return;
        end
        self.mStartingPath = true;
end

function Movement:addBlocker(b)
        if self:hasBlocker(b) then
            return;
        end
        self.blockers:add(BlockingTroop.new(b, b.currentBattle));
end

function Movement:hasBlocker(b)
	local len = self.blockers:getSize();

	for i = 0 , len-1 , 1 do
		current = self.blockers[i];
		if current:getBlocker() == b then
			return true;
		end	
	end	
	return false;
end

function Movement:updateMovement(owner)
end

function Movement:isMovementComplete()
        return true;
end

function Movement:updatePath(newPath)
end

function Movement:setHalfSpeed(halfSpeed)
end

function Movement:destination(owner)
        return owner:getPosition();
end

function Movement:projectPosition(owner, distance)
        return owner:getPosition();
end

function Movement:stopMove()
   
end