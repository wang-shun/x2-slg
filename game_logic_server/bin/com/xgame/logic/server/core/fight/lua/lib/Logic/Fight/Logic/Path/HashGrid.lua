HashGrid = class(Object)

function HashGrid:ctor(hashSize , cellSize) 
            self.ClassName = "HashGrid";
	local i = 2;

	while i < hashSize do
		i = i * 2;
	end	

	hashSize = i;

	self.hashMask = hashSize - 1;

	self.hashArraySize = hashSize;

	self.cellDimension = cellSize;



--print("WWWWWWWWWWWWWWW     " ,cellSize ,  1 / cellSize , self)
	self.invCellDimension = 1 / cellSize;

	self.invCellRaw = self.invCellDimension * 4096;

	self.hashArray = Map.new();

	self.hashCells = Map.new();

	self.hashCellsUsed = 0;
end	

function HashGrid:GetCellSize()
            return self.cellDimension;
end

function HashGrid:UpdateBBoxes()
            local i = 0;
          --  for i = 0 ,  self.hashCellsUsed -1 , 1 do
            while i <= self.hashCellsUsed - 1 do
                local flag = self.hashCells[i]:UpdateBBoxes(self);
                if flag then
                    i = i - 1;
                end
                 i = i + 1;
            end
end

function HashGrid:getHashIndex(x , y)
	return x *1000000 + y;
end	

function HashGrid:RemoveHashCell(cell, xHash, yHash)

            local selfIndexInHashCells = cell.selfIndexInHashCells;

            if selfIndexInHashCells < 0 or selfIndexInHashCells >= self.hashCellsUsed then
                print("HashGrid - bad cell ndx.");
                return;
            end

            if self.hashCellsUsed == 0 then
                print("HashGrid - bad hashCellsUsed.");
                return;
            end

            if self.hashArray[self:getHashIndex(xHash, yHash)] ~= cell then
                print("Bad hash grid - hash mismatch.");
            end

            if self.hashCells[selfIndexInHashCells] ~= cell then
                print("Bad hash grid - cell mismatch.");
            end

            self.hashArray:put(self:getHashIndex(xHash, yHash) , nil);
           
            self.hashCellsUsed = self.hashCellsUsed - 1;
            local hashCell = self.hashCells[self.hashCellsUsed];
            self.hashCells:put(self.hashCellsUsed , self.hashCells[selfIndexInHashCells]);
            self.hashCells[self.hashCellsUsed].selfIndexInHashCells = self.hashCellsUsed;
            self.hashCells:put(selfIndexInHashCells , hashCell);
            self.hashCells[selfIndexInHashCells].selfIndexInHashCells = selfIndexInHashCells;
end

function HashGrid:AddEntity(entity)
--    print("BBBBBBBBBBBB!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ",entity , entity:getPosition().x , entity:getPosition().z , entity.boundingBox.minBounds.x , entity.boundingBox.minBounds.z ,          entity.boundingBox:getLeft()  , entity.boundingBox:getBottom() ,  entity.boundingBox:getTop() ,entity.boundingBox:getRight()    ,entity.areaSize)
            local _x = self:HashCoord(entity.boundingBox.minBounds.x);
            local _y = self:HashCoord(entity.boundingBox.minBounds.z);

            local hashCell = self.hashArray[self:getHashIndex(_x, _y)];
      --       print(_x,_y,self:getHashIndex(_x, _y),hashCell , self)
            if hashCell == nil then
                hashCell = self:AllocateCell();

      --          print("AAAAAAAAADDDDDDDDD$$$$$$  " , self:getHashIndex(_x, _y) , entity)
                self.hashArray:put(self:getHashIndex(_x, _y) , hashCell);
            end

            if hashCell:Contains(entity) then
                return;
            end
            hashCell:Add(entity);
end

function HashGrid:RemoveEntity(entity)
            local _x = self:HashCoord(entity.boundingBox.minBounds.x);
            local _y = self:HashCoord(entity.boundingBox.minBounds.z);
            local hashCell = self.hashArray[self:getHashIndex(_x, _y)];
            if hashCell == nil then
                return;
            end

            if not hashCell:Contains(entity) then
                return;
            end

            hashCell:Remove(entity);

            if hashCell:Empty() then
                self:RemoveHashCell(hashCell, _x, _y);
            end
end

function HashGrid:GridRateEntities(box, func_ratetProc)
	local bestScore = 0;
            local boardEntity = nil;
            local minX = self:HashCoord(box.minBounds.x);
            local minY = self:HashCoord(box.minBounds.z);
            local maxX = Mathf.Round((box.maxBounds.x - box.minBounds.x) * self.invCellDimension) + 1;
            if maxX >= self.hashArraySize then
                maxX = self.hashArraySize - 1;
            end

            local maxY = Mathf.Round((box.maxBounds.z - box.minBounds.z) * self.invCellDimension) + 1;
            if maxY >= self.hashArraySize then
                maxY = self.hashArraySize - 1;
            end
--print("CCCCCCC  " ,minX,maxX ,minY,maxY)
            for i=minX - 1 , minX + maxX , 1 do 
                for j=minY - 1 ,  minY + maxY , 1 do 
                    local _x = Bit._and( i , self.hashMask);
                    local _y = Bit._and( j , self.hashMask);
                   -- print("CCCCCC22222C  "  ,_x , _y , i , j  , self:getHashIndex(_x, _y) ,self.hashArray[self:getHashIndex(_x, _y)] , self )
                    if self.hashArray[self:getHashIndex(_x, _y)] ~= nil then
                        local entities = self.hashArray[self:getHashIndex(_x, _y)].entities;

                      --  print("66666666666666666     " ,entities:getSize()  )
                        for k=0 , entities:getSize() - 1 , 1 do  
                            local boardEntity2 = entities[k];
                         --    print("77777777777777777777     " ,self.hashArray[self:getHashIndex(_x, _y)]  , boardEntity2 , box:intersectsFast(boardEntity2.boundingBox))
                            if box:intersectsFast(boardEntity2.boundingBox) then
                       --         print("*****0*******"  )

                                local targetScore = func_ratetProc(boardEntity2);

                         --       print("*****1********"  ,targetScore)
                                if targetScore ~= 0 then
                                    local flag = targetScore > bestScore;
                          --            print("******2*******"  ,flag , bestScore)
                                    if targetScore == bestScore then
                                        flag = true;
                                    end

                                    if flag then
                                        bestScore = targetScore;
                                        boardEntity = boardEntity2;
                                    end
                                end
                            end
                        end
                    end
                end
            end
            return {boardEntity = boardEntity , bestScore = bestScore};
end

function HashGrid:GridFindEntities(l, box, contained, testProc)
            local _x = self:HashCoord(box.minBounds.x);
            local _y = self:HashCoord(box.minBounds.z);
            local _w = Mathf.Round((box.maxBounds.x - box.minBounds.x) * self.invCellDimension) + 1;
            if _w >= self.hashArraySize then
                _w = self.hashArraySize - 1;
            end

            local _h = Mathf.Round((box.maxBounds.x - box.minBounds.x) * self.invCellDimension) + 1;
            if _h >= self.hashArraySize then
                _h = self.hashArraySize - 1;
            end

            for i =  _x - 1 , _x + _w , 1 do 
                for j = _y - 1 , _y + _h , 1 do 
                    local __x = Bit._and(i , self.hashMask);
                    local __y = Bit._and(j , self.hashMask);
                    if self.hashArray[self:getHashIndex(__x, __y)] ~= nil then
                        local entities = self.hashArray[self:getHashIndex(__x, __y)].entities;
                        for k = 0 , entities:getSize() - 1 , 1 do 
                            local boardEntity = entities[k];
                            local flag = false;
                            if contained then
                                if box:containsFast(boardEntity.boundingBox) and (testProc == nil or testProc(boardEntity)) then
                                    flag = true;
                                end
                            elseif box:intersectsFast(boardEntity.boundingBox) and (testProc == nil or testProc(boardEntity)) then
                                flag = true;
                            end

                            if flag then
                                l:add(boardEntity);
                            end
                        end
                    end
                end
            end
end

function HashGrid:ExistsIntersecting(box, testProc)
            local _x = self:HashCoord(box.minBounds.x);
            local _y = self:HashCoord(box.minBounds.z);
            local _w = Mathf.Round((box.maxBounds.x - box.minBounds.x) * self.invCellDimension) + 1;
            if _w >= self.hashArraySize then
                _w = self.hashArraySize - 1;
            end

            local _h = Mathf.Round((box.maxBounds.x - box.minBounds.x) * self.invCellDimension) + 1;
            if _h >= self.hashArraySize then
                _h = self.hashArraySize - 1;
            end

            for i = _x - 1 , _x + _w , 1 do  
                for j = _y - 1 , _y + _h , 1 do 
                    local __x = Bit._and(i , self.hashMask);
                    local __y = Bit._and(j , self.hashMask);
                    if self.hashArray[self:getHashIndex(__x, __y)] ~= nil then
                        local entities = self.hashArray[self:getHashIndex(__x, __y)].entities;
                        for k = 0 , entities:getSize() -1  do  
                            local boardEntity = entities[k];
                            if box:intersectsFast(boardEntity.boundingBox) and testProc(boardEntity) then
                                return true;
                            end
                        end
                    end
                end
            end
            return false;
end

function HashGrid:AllocateCell()
            local num = self.hashCellsUsed;
            self.hashCellsUsed = self.hashCellsUsed + 1;

            if self.hashCells[num] == nil then
                self.hashCells:put(num , HashCell.new());
                self.hashCells[num].selfIndexInHashCells = num;
            end

            if self.hashCells[num].entities == nil then
                self.hashCells[num].entities = List0.new();
            end

            self.hashCells[num].selfIndexInHashCells = num;

            return self.hashCells[num];
end

function HashGrid:HashCoord(c)

 
            return Mathf.Round(Bit._and(Bit._rshift(Mathf.Round(c * 4096 * self.invCellRaw)  , 24)  , self.hashMask));
end