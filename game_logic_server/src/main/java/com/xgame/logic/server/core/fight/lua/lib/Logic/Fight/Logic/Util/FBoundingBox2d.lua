FBoundingBox2d = class(Object)

function FBoundingBox2d:ctor(center , size)
 
	local v = FVector3.One * (size - 1);

	self.minBounds = center;

	self.maxBounds = center + v;
end

function FBoundingBox2d:getTop()
            return self.maxBounds.z;
end

function FBoundingBox2d:getBottom()
            return self.minBounds.z;
end

function FBoundingBox2d:getRight()
            return self.maxBounds.x;
end

function FBoundingBox2d:getLeft()
            return self.minBounds.x;
end

function FBoundingBox2d:getApproxRadius()
        local fVector = self.maxBounds - self.minBounds;
        return (fVector.x + fVector.z) * 0.25;
end

function FBoundingBox2d:getWidth()
        return self.maxBounds.x - self.minBounds.x;
end

function FBoundingBox2d:getHeight()
        return self.maxBounds.z - self.minBounds.z;
end

function FBoundingBox2d:initialize()
        self.maxBounds = FVector3.new(0,0,0);
        self.minBounds = FVector3.new(0,0,0);
end
 

function FBoundingBox2d:addPoint(point)
        if point.x > self.maxBounds.x then
            self.maxBounds.x = point.x;
        end

        if point.z > self.maxBounds.z then
            self.maxBounds.z = point.z;
        end

        if point.x < self.minBounds.x then
            self.minBounds.x = point.x;
        end

        if point.z < self.minBounds.z then
            self.minBounds.z = point.z;
        end
end

function FBoundingBox2d:merge(other)
        if other.maxBounds.x > self.maxBounds.x then
            self.maxBounds.x = other.maxBounds.x;
        end

        if other.maxBounds.z > self.maxBounds.z then
            self.maxBounds.z = other.maxBounds.z;
        end

        if other.minBounds.x < self.minBounds.x then
            self.minBounds.x = other.minBounds.x;
        end

        if other.minBounds.z < self.minBounds.z then
            self.minBounds.z = other.minBounds.z;
        end
end

function FBoundingBox2d:contains(point)
        return point.x >= self.minBounds.x and point.z >= self.minBounds.z and point.x <= self.maxBounds.x and point.z <= self.maxBounds.z;
end

function FBoundingBox2d:contains(box)
        return box:getRight() <= self:getRight() and box:getLeft() >= self:getLeft() and box:getBottom() >= self:getBottom() and box:getTop() <= self:getTop();
end

function FBoundingBox2d:containsFast(box)

	local a = Mathf.Round(self.maxBounds.x - box.maxBounds.x);
	local b = Mathf.Round(box.minBounds.x - self.minBounds.x);
	local c = Mathf.Round(box.minBounds.z - self.minBounds.z);
	local d = Mathf.Round(self.maxBounds.z - box.maxBounds.z);
	local num =  Bit._or(Bit._or(Bit._or(a,b) , c) , d);
	return Bit._rshift(num , 63) == 0;
end

function FBoundingBox2d:intersects(box)

--print(box:getLeft()  , box:getBottom() ,  box:getTop() , box:getRight()           ,       self:getLeft()  , self:getBottom() ,  self:getTop() , self:getRight()  )

        return (box:getLeft() == self:getLeft() and box:getBottom() == self:getBottom()) or (box:getLeft() < self:getRight() and box:getRight() > self:getLeft() and box:getTop() > self:getBottom() and box:getBottom() < self:getTop());
end

function FBoundingBox2d:intersectsFast(box)
       --long num = Mathf.RoundToInt(box.minBounds.x - this.minBounds.x) | Mathf.RoundToInt(box.minBounds.y - this.minBounds.y);
       --long num2 = Mathf.RoundToInt(box.minBounds.x - this.maxBounds.x) & Mathf.RoundToInt(this.minBounds.x - box.maxBounds.x) & Mathf.RoundToInt(this.minBounds.y - box.maxBounds.y) & Mathf.RoundToInt(box.minBounds.y - this.maxBounds.y);
       --return num == 0L || num2 >> 63 != 0L;

        return self:intersects(box);
end