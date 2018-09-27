HashCell = class(Object)

function HashCell:ctor() 
            self.ClassName = "HashCell";
	self.selfIndexInHashCells = 0;

	self.entities = nil;
end	

function HashCell:Empty()
	return self.entities:getSize() == 0;
end

function HashCell:Contains(item)
	return self.entities:contains(item);
end

function HashCell:Add(item)
 
	self.entities:add(item);
end

function HashCell:Remove(item)
           self.entities.Remove(item);
end

function HashCell:UpdateBBoxes(grid)

            local xHash = -1;

            local yHash = -1;

            if self.entities:getSize() == 0 then
                return false;
            end

            local i = 0;
            --for i = 0 , self.entities:getSize() - 1, 1 do
            while i < self.entities:getSize() - 1 do
                local boardEntity = self.entities[i];

                if i == 0 then
                    xHash = grid:HashCoord(boardEntity.boundingBox.minBounds.x);
                    yHash = grid:HashCoord(boardEntity.boundingBox.minBounds.z);
                end

                if boardEntity:getBBoxDirty() then
                    local num = grid:HashCoord(boardEntity.boundingBox.minBounds.x);
                    local num2 = grid:HashCoord(boardEntity.boundingBox.minBounds.z);
                    boardEntity:updateBoundingBox_ONLY_FOR_HASH_GRID();
                    local num3 = grid:HashCoord(boardEntity.boundingBox.minBounds.x);
                    local num4 = grid:HashCoord(boardEntity.boundingBox.minBounds.z);
                    if num ~= num3 or num2 ~= num4 then
                        local value = self.entities[self.entities:getSize() - 1];
                        self.entities[i] = value;
                        self.entities:removeAt(self.entities:getSize() - 1);
                        i = i - 1;
                        grid:AddEntity(boardEntity);
                    end
                end

                i = i + 1;
            end

            if self.entities:getSize() == 0 then
                grid:RemoveHashCell(self, xHash, yHash);
                return true;
            end
            return false;
end
 












 