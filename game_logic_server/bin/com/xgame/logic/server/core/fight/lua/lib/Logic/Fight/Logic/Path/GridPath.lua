GridPath = class(Object)

function GridPath:ctor(infantry) 
            self.ClassName = "GridPath";
            self.mGridPathWaypoints = List0.new();
            self.mInfantry = infantry;
            self.tmpGridPathWaypoints = List0.new();
end            

function GridPath:getNumWaypoints()
        return self.mGridPathWaypoints:getSize();
end

function GridPath:getWaypoint(index)
        return (index < 0 or index >= self:getNumWaypoints())  and FVector3.Zero or self.mGridPathWaypoints[index];
end

function GridPath:addWaypoint(waypoint)
       --  print("::::DDDDDDDDDDD::::::::   " , waypoint.x , ":" ,waypoint.z);
        self.mGridPathWaypoints:add(waypoint);
end

function GridPath:reverse()
	local len = self.mGridPathWaypoints:getSize() / 2;
	for i = 0 , len-1 , 1 do
	    local value = self.mGridPathWaypoints[i];
	    self.mGridPathWaypoints[i] = self.mGridPathWaypoints[self.mGridPathWaypoints:getSize() - 1 - i];
	    self.mGridPathWaypoints[self.mGridPathWaypoints:getSize() - 1 - i] = value;
	end
end

function GridPath:clear()
        self.mGridPathWaypoints:clear();
end

function GridPath:infanty2Path()
        return self.mInfantry;
end

function GridPath:copyFrom(gridPath)
        self.mGridPathWaypoints:clear();
        self.mGridPathWaypoints:addRange(gridPath.mGridPathWaypoints);
end

function GridPath:trimTo(newNumWaypoints)
        if newNumWaypoints < 0 then
            return;
        end

        if self:getNumWaypoints() <= newNumWaypoints then
            return;
        end
        local count = self:getNumWaypoints() - newNumWaypoints;
        self.mGridPathWaypoints:removeRange(newNumWaypoints, count);
end

function GridPath:addLerpOffsets(startingOffset, endingOffset)

        --self.tmpGridPathWaypoints:clear();

        local fixedPointNumber = (self:getNumWaypoints() <= 1) and 1 or (self:getNumWaypoints() - 1);

        if fixedPointNumber > 2 then
            fixedPointNumber = 2;
        end

        local lastP = nil;
        for i = 0 , self:getNumWaypoints() - 1, 1 do
            local lerp =  i / fixedPointNumber;
            if lerp > 1 then
                lerp = 1;
            end
            local list = self.mGridPathWaypoints;
            local v = list[i];
          -- print("****Lerp*****  " ,startingOffset.x , startingOffset.z, endingOffset.x , endingOffset.z , lerp)
            --list[i] = v + FVector3.Lerp(startingOffset, endingOffset, lerp);


            local curP = v + FVector3.Lerp(startingOffset, endingOffset, lerp);

  
 
            if lastP ~= nil then 
                local __x = curP.x-lastP.x;
                local __z = curP.z-lastP.z;
                if Mathf.Abs(__x) > 1 or Mathf.Abs(__z) > 1 then
                    local tmpP = FVector3.new(lastP.x + __x / 2 , curP.y , lastP.z + __z / 2 );
                    self.tmpGridPathWaypoints:add(tmpP);
                end    
            end    

            self.tmpGridPathWaypoints:add(curP);

            lastP = curP;
        end

        self.mGridPathWaypoints:clear();

        for i = 0 , self.tmpGridPathWaypoints:getSize() - 1, 1 do
            self.mGridPathWaypoints:add(self.tmpGridPathWaypoints[i]);
        end    
 
end

function GridPath:modifyPoint(pt, ndx)
        if ndx >= 0 and ndx < self:getNumWaypoints() then
            self.mGridPathWaypoints[ndx] = pt;
        end
end