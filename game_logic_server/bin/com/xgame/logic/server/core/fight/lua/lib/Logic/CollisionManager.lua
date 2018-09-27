CollisionManager = class(Object);

function CollisionManager:ctor(inBoard)
	self.board = inBoard;
        	self.hHashGrid = HierarchicalHashGrid.new();
end	

function CollisionManager:addEntity(entity)
        self.hHashGrid:addEntity(entity);
end

 
function CollisionManager:removeEntity(entity)
        self.hHashGrid:removeEntity(entity);
end

function CollisionManager:updateCollision()
        self.hHashGrid:updateCollision(self.board);
end

function CollisionManager:findBestEnemyIntersects(box, func_rateProc, myTeam)
        return self.hHashGrid:bestEnemyIntersect(box, func_rateProc, myTeam);
end

function CollisionManager:getEntitiesWithinRadius( center, radius, outList)
	outList:clear();
	local box = FBoundingBox2d.new(center, radius * 2);
	self:getEntitiesIntersecting(box, outList, nil);
end

function CollisionManager:getEntitiesIntersecting(box, outList, testProc)
	self:getEntities(box, false, outList, testProc);
end

function CollisionManager:getEntities( box,  contained,outList,  testProc)
	self.hHashGrid:getEntities(box, contained, outList, testProc);
end