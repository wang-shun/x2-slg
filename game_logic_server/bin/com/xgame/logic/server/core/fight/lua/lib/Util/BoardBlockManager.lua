---------------------------------------------------------------------
----格子阻挡管理器
---------------------------------------------------------------------
BoardBlockManager = {};

local this = BoardBlockManager;

this.blockMap = Map.new();

----获取占地格子列表
function BoardBlockManager.getOccupiedList(x,y,w,h)

	local max_x = x+w-1;
	
	local max_y = y+h-1;
	
	local indexList = List.new();
	
	for i = x , max_x do
	
		for j = y , max_y do
	
			local index = this.posToIndex(i,j);
	
			indexList:add(index);
	
		end
	
	end
	return indexList;

end

function BoardBlockManager.getEntityList(x,y)

	local index = this.posToIndex(x,y);

	return this.blockMap:value(index);

end


----设置阻挡信息
function BoardBlockManager.setBlock(entityId,x,y,w,h)

	local indexList = this.getOccupiedList(x,y,w,h);

	for i = 1 , indexList:getSize() do

		local index = indexList[i];

		local entityList = this.blockMap:value(index);

		if entityList == nil then

			entityList = List.new();

			this.blockMap:put(index,entityList);

		end

		entityList:add(entityId);

	end

end

----清除阻挡信息
function BoardBlockManager.clearBlock(entityId,x,y,w,h)

	local indexList = this.getOccupiedList(x,y,w,h);

	for i = 1 , indexList:getSize() do

		local index = indexList[i];
		if index and this.blockMap and this.blockMap:containsKey(index) then
			local entityList = this.blockMap:value(index);

			if entityList ~= nil then

				entityList:remove(entityId);

				if entityList:getSize() == 0 then

					this.blockMap:remove(index);

				end

			end
		end

	end

end

----检测当前视图是否可放置
function BoardBlockManager.checkViewCurrentPosIsAvailable(entityId , x , y , w , h) 
	
	local indexList = this.getOccupiedList(x,y,w,h);
	
	for i = 1 , indexList:getSize() do
		
		local idList = this.blockMap:value(indexList[i]);
		
		if idList ~= nil then

			for j = 1 , idList:getSize() do

				if idList[j] ~= entityId and idList[j] ~= nil then
				
					return false;
				
				end

			end

		end

	end	
	
	return true;

end	

----是否在自由建造区
function BoardBlockManager.isInFreeArea(x , y , w , h) 
	if (x < 22 or x > (65 - w) + 1) or (y < 22 or y > (65 - h) + 1) then 
		return false;
	end	
	return true;
end


function BoardBlockManager.getCreatePostion(x,y,w,h,r)
	
	if this.isEmpty(x,y,w,h) then
		return Vector2(x,y);
	end

	for i=1 , r do 
				
		for a = x - i , x + i  do

			for b = y - i , y + i do

				if math.abs(a-x) == i or math.abs(b-y) == i then

					if this.isEmpty(a,b,w,h) then
						return Vector2(a,b);
					end

				end 
				

			end

		end

	end

	return Vector2(x,y);

end




----检测当前区域是否为空地
function BoardBlockManager.isEmpty(x , y , w , h) 
	
	local indexList = this.getOccupiedList(x,y,w,h);
	
	for i = 1 , indexList:getSize() do
		
		local idList = this.blockMap:value(indexList[i]);
		
		if idList ~= nil then

			for j = 1 , idList:getSize() do

				if idList[j] ~= nil then
				
					return false;
				
				end

			end

		end

	end	
	
	return true;
	
end	

----根据尺寸获取实际可活动范围
function BoardBlockManager.getMoveRange(w , h)
	
	local minX = 22 + this.getPosOffect(w);

	local maxX =  (65 - w) + 1 + this.getPosOffect(w);
	
	local minZ = 22 + this.getPosOffect(h); 
	
	local maxZ = (65 - h) + 1 + this.getPosOffect(h); 

	return {minx = minX , maxx = maxX , minz = minZ , maxz = maxZ};

end	

----根据尺寸换算实际坐标增量
function BoardBlockManager.getPosOffect(i_size)
	return (i_size - 1) * 0.5;
end

----[坐标] -> [唯一ID]  return int
function BoardBlockManager.posToIndex(x,y)
	return tonumber((2000+x)..(2000+y));
end

----[唯一ID] -> [坐标]  return Vector2
function BoardBlockManager.indexToPos(index)
	local x = math.floor(index/10000);
	local y = index % x;
	return Vector2(x%2000,y%2000);
end

----清除所有障碍记录
function BoardBlockManager.clear()
	if this.blockMap~=nil then
		this.blockMap:clear();
	else
		this.blockMap = Map.new();
	end
end
