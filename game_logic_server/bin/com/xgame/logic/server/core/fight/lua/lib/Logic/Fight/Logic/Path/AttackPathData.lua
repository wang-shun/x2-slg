AttackPathData = class(Object)

function AttackPathData:ctor()
	self.ClassName = "AttackPathData";
	self.path = nil;
	self.intermediateWall = nil;
	self.destinationInRange = false;
	self.secondaryDestination = false;
	self.expensive = false;
	self.noParking = false;

end	

function AttackPathData:clear()
	--warn("(((((((((((((((((((( AttackPathData:clear ")
        self.path = nil;
        self.intermediateWall = nil;
        self.noParking = false;
        self.expensive = false;
        self.secondaryDestination = false;
        self.destinationInRange = false;
end