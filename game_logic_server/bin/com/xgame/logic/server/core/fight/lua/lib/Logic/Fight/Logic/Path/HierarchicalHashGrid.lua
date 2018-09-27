HierarchicalHashGrid = class(Object)

 
function HierarchicalHashGrid:ctor() 
            self.ClassName = "HierarchicalHashGrid";

            local FIRST_GRID_CELL_SIZE = 2 + 0.25;

            local GRID_FACTOR = 2;

	self.attackerGrids = Map.new();

	self.defenderGrids = Map.new();
 
	local num = 64;

	local fixedPointNumber = FIRST_GRID_CELL_SIZE;

	for i = 0 , 4 , 1 do
                self.defenderGrids:put(i , HashGrid.new(num, fixedPointNumber));
                self.attackerGrids:put(i , HashGrid.new(num, fixedPointNumber));
                num = Mathf.Floor(num / 2);
                fixedPointNumber = fixedPointNumber * GRID_FACTOR;
	end	
end	

function HierarchicalHashGrid:addEntity(entity)
        entity:updateBoundingBox_ONLY_FOR_HASH_GRID();
        local fVector = entity.boundingBox.maxBounds - entity.boundingBox.minBounds;
        local n = fVector.x > fVector.z and fVector.x or fVector.z;
        
        local ii = 0;
        for i = 0,3,1 do 
        	ii = i;
            if self.defenderGrids[i]:GetCellSize() >= n then
                break;
            end
        end

        if entity.team == EntityTeam.Defender then
            self.defenderGrids[ii]:AddEntity(entity);
        else
            self.attackerGrids[ii]:AddEntity(entity);
        end
end

function HierarchicalHashGrid:GetEntities(box, contained, testProc)
        return self:FindEntities(box, contained, testProc);
end

function HierarchicalHashGrid:GetEntities(box, contained, outList, testProc)
        self:FindEntitiesIntoList(box, contained, outList, testProc);
end

function HierarchicalHashGrid:EntityExistsIntersecting(box, testProc)
        local flag = false;
        for i = 0 , 4 , 1 do 
            flag = self.defenderGrids[i]:ExistsIntersecting(box, testProc);
            if flag then
                return true;
            end

            flag = self.attackerGrids[i]:ExistsIntersecting(box, testProc);
            
            if flag then
                return true;
            end
        end
        return flag;
end

function HierarchicalHashGrid:bestEnemyIntersect(box, rateProc, myTeam)
        local n = 0;
        local boardEntity = nil;
        for i = 0 , 4 , 1 do
            local zero = 0;
            if myTeam == EntityTeam.Attacker or myTeam == EntityTeam.AllTeams then
            	local rs = self.defenderGrids[i]:GridRateEntities(box, rateProc);
            	local boardEntity2 = rs.boardEntity;
            	zero = rs.bestScore;
		local flag = zero > n;

		if flag then
		    boardEntity = boardEntity2;
		    n = zero;
		end
            end

            if myTeam ~= EntityTeam.Attacker then
            	local rs = self.attackerGrids[i]:GridRateEntities(box, rateProc);
		local boardEntity2 = rs.boardEntity;
		zero = rs.bestScore;
		local flag2 = zero > n;

		if flag2 then
		    boardEntity = boardEntity2;
		    n = zero;
		end
            end
        end
        return boardEntity;
end

function HierarchicalHashGrid:removeEntity(entity)
        local fVector = entity.boundingBox.maxBounds - entity.boundingBox.minBounds;
        local n = fVector.x > fVector.z and fVector.x or fVector.z;
        
        local ii=0;
        for i = 0 , 3 , 1 do 
        	ii = i;
            if self.defenderGrids[i]:GetCellSize() >= n then
                break;
            end
        end

        if entity.team == EntityTeam.Defender then
            self.defenderGrids[i]:RemoveEntity(entity);
        else
            self.attackerGrids[i]:RemoveEntity(entity);
        end
end

function HierarchicalHashGrid:FindEntities(box, contained, testProc)
        local list = List.new();
        for i = 0 , 4 , 1 do 
            self.attackerGrids[i]:GridFindEntities(list, box, contained, testProc);
            self.defenderGrids[i]:GridFindEntities(list, box, contained, testProc);
        end
        return list;
end


function HierarchicalHashGrid:FindEntitiesIntoList(box, contained, outList, testProc)
        for i = 0 , 4 , 1 do 
            self.attackerGrids[i]:GridFindEntities(outList, box, contained, testProc);
            self.defenderGrids[i]:GridFindEntities(outList, box, contained, testProc);
        end
end

function HierarchicalHashGrid:updateCollision(b)
        self:UpdateBBoxes();
end

function HierarchicalHashGrid:UpdateBBoxes()
        for i = 0 , 3 , 1 do 
            self.attackerGrids[i]:UpdateBBoxes();
            self.defenderGrids[i]:UpdateBBoxes();
        end
end




















    





   

    

 




