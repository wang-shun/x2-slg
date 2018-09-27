GridPathFollower = class(Object)

function GridPathFollower:ctor() 
            self.ClassName = "GridPathFollower"
            self.mCurrentWaypoint = 0
            self.mPosition = FVector3.new(0,0,0);
            self.mPath = nil;
end	

function GridPathFollower:getValidPath()
        return self.mPath ~= nil;
end

function GridPathFollower:getPosition()
    return self.mPosition;
end

function GridPathFollower:isEndOfPath()
        return self.mPath ~= nil and self.mCurrentWaypoint >= self.mPath:getNumWaypoints();
end

function GridPathFollower:update(newPath, troop)
        if newPath:getNumWaypoints() < 2 then
            return;
        end

        self.mPath = newPath;
        self.mPosition = self.mPath:getWaypoint(1);
end
 
function GridPathFollower:initialize(path, troop)
        self.mPath = path;
        if troop ~= nil then
            self.mPosition = troop:getPosition();
            if path ~= nil and troop.troopAI ~= nil and troop.troopAI.continueMovement then
                self.mPosition = path:getWaypoint(0);
            end
        end

        self.mCurrentWaypoint = 0;
end

function GridPathFollower:getCurrentWaypoint()
        return self.mCurrentWaypoint;
end

function GridPathFollower:followPath(dist)
        if self:getValidPath() then
            local fVector = self:getPosition();
            local curDist = dist;
            while curDist > 0 and not self:isEndOfPath() do
                if self.mCurrentWaypoint >= self.mPath:getNumWaypoints() then
                    self.mPosition = self.mPath:getWaypoint(self.mPath:getNumWaypoints() - 1);
                    return;
                end
                local waypoint = self.mPath:getWaypoint(self.mCurrentWaypoint);
                local v = waypoint - fVector;
                local vLength = v:magnitude();
                if vLength > 0 then
                    if curDist >= vLength then
                        fVector = waypoint;
                        curDist = curDist - vLength;
                        self.mCurrentWaypoint = self.mCurrentWaypoint + 1;
                    else
                        v:normalize();
                        fVector = fVector + v * curDist;
                        curDist = 0;
                    end
                else
                    self.mCurrentWaypoint = self.mCurrentWaypoint + 1;
                end    
            end
            self.mPosition = fVector;
        end
end