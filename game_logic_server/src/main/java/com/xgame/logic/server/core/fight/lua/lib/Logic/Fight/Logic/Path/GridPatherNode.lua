GridPatherNode = class(Object)

function GridPatherNode:ctor()
                        self.ClassName = "GridPatherNode";
	self.Index = 0;
	self.Parent = nil;
	self.mDistanceFromSource = 0;
	self.mDistanceToTarget = 0;
	self.mPathingScore = 0;
end	


function GridPatherNode:reset(index)
        self.mDistanceFromSource = 0;
        self.mPathingScore = 0;
        self.mDistanceToTarget = 0;
        self.Parent = nil;
        self.Index = index;
end

function GridPatherNode.Compare(a, b)
        return ( not (a.mPathingScore < b.mPathingScore) ) and ( ( not (a.mPathingScore > b.mPathingScore) ) and 0 or 1 ) or -1;
end