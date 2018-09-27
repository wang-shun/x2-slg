--基础移动行为类, 不同的机体 , 移动效果会不一样

MovementMobile = class(Movement)

function MovementMobile:ctor()
            self.ClassName = "MovementMobile";
	self:init();
end	

function MovementMobile:init()
	self.mPathFollower = RTSPathFollower.new();
end	

function MovementMobile:getSpeed()
        if self.mPathFollower ~= nil then
            return self.mPathFollower:getSpeed();
        end
        return 0;
end

function MovementMobile:getPath()
	if self.mPathFollower ~= nil then
	    return self.mPathFollower:getPath();
	end
	return nil;
end

function MovementMobile:isHalfSpeed()
            return self.mPathFollower ~= nil and self.mPathFollower:getHalfSpeed();
end

function MovementMobile:getDirection()
	if self.mPathFollower ~= nil then
	    return self.mPathFollower:getDirection();
	end
	return FVector3.new(0,0,0);
end

function MovementMobile:destination(owner)
        if not self:isMovementComplete() and self.mPathFollower:getPath() ~= nil then
            local numWaypoints = self.mPathFollower:getPath():getNumWaypoints();
            return self.mPathFollower:getPath():getWaypoint(numWaypoints - 1);
        end
        return owner:getPosition();
end

function MovementMobile:setHalfSpeed(halfSpeed)
        self.mPathFollower:setHalfSpeed(halfSpeed);
end

function MovementMobile:updatePath(newPath)
        self.mPathFollower:updatePath(newPath);
end

function MovementMobile:setPath(path, owner)
  --  print("------------MovementMobile:setPath")
        local topSuper = getTopSuper(self);

        if topSuper ~= nil then
             topSuper.setPath(self , path, owner);
        end    
       
        if path == nil then
            self.mPathFollower:endPath(owner);
            return;
        end

        self.mPathFollower:endPath(owner);
 
        if not self.mPathFollower:isPathingComplete() and owner.troopAI.continueMovement then
            self.mPathFollower:continuePath(path, owner);
        else
            self.mPathFollower:startPath(path, owner);
        end
        owner.troopAI.continueMovement = false;
end

function MovementMobile:updateMovement(owner)
        if self:isMovementComplete() then
            return;
        end

        self.mPathFollower:followPathTime(BattleConst.SIM_STEP_TIME);
end

function MovementMobile:isMovementComplete()
        return self.mPathFollower:isPathingComplete();
end


function MovementMobile:projectPosition(owner, distance)
        if not self:isMovementComplete() then
            return self.mPathFollower:projectPosition(distance);
        end
        return owner:getPosition();
end

function MovementMobile:isMovementComplete()
            return self.mPathFollower:isPathingComplete();
end

function MovementMobile:stopMove()
    self.mPathFollower:doStop();
end









 



 


 



