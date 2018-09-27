GridPather = class(Object)

GridPatherConst = {

	pathDelayCount = 0,

	noPathFrames = 0,

	PATHING_NUM = 5,

	PATH_FIND_DEPTH = 1000,
}

function GridPather.GetPathDelayCount()
	return GridPatherConst.pathDelayCount;
end	

function GridPather.GetNoPathFrames()
	return GridPatherConst.noPathFrames;
end	

--总的格子数
function GridPather:getTotalNodes()
        return Board.GetCellCountTotal();
end

function GridPather:ctor(board) 
	self.ClassName = "GridPather";
	self.curBoard = board;
	self.mOpenList = List0.new();
	self.mNodes = List0.new();

	for i = 0 , self:getTotalNodes()  - 1 , 1 do
		--对格子编号, 产生初始格子节点对象
		local gridPatherNode = GridPatherNode.new();
            	gridPatherNode:reset(i);
           	 	self.mNodes:add(gridPatherNode);
	end	
	self.mStartNode = nil;
	self.mTargetNodes = List.new();
	self.mInfantryParking2 = FastBitArray.new(self:getTotalNodes());
	self.mSecondaryTargetNodes = List0.new();
	self.mOpen = FastBitArray.new(self:getTotalNodes());
	self.mOnPath = FastBitArray.new(self:getTotalNodes());
	self.mParking = FastBitArray.new(self:getTotalNodes());

	--print("------------------------------------  gggg " , self:getTotalNodes() , self.mParking.myData:getSize())
	--print("GGGGG2222222GGGGGGG  " , self:getParking().myData:getSize())
	self.mClosed = FastBitArray.new(self:getTotalNodes());
	self.mWall = FastBitArray.new(self:getTotalNodes());
	self.mExpandedWall = FastBitArray.new(self:getTotalNodes());

	--print(self.curBoard.mStructureObstructions)
	self.mObstructed =  FastBitArrayTool.CopyOne(self.curBoard.mStructureObstructions);--FastBitArray.new(self:getTotalNodes());




	self.mPathAsFlyer = false;

	self.mIntermediateWall = nil;
	self.foundUnblockedCell = false;
	self.mClosestClosedNode = nil;
	self.mSecondaryNode = nil;
	self.moveThroughParkingCost = 0;
	self.mSecondaryTargetPenalty = 0;
	self.mWallPathCost = 0;
	self.mCanShootWalls = false;
	self.mCanRunOverWalls = false;
	self.mPathAsLargeTroop = false;
	self.mNumNodesProcessed = 0;
	self.blockPathWall = nil;
	self.offBlockPathWall = nil;
	self.closeWallPosition = nil;
	self.mWallForFiring = nil;
	self.mWallCount = 0;
	self.parkMode = 0;

end	


function GridPather:getObstructed()
        return self.mObstructed;
end

function GridPather:getOnPath()
        return self.mOnPath;
end

function GridPather:getOpen()
        return self.mOpen;
end

function GridPather:getClosed()
        return self.mClosed;
end

function GridPather:getParking()
        return self.mParking;
end

function GridPather:pathAsLarge(pather)
        return pather.troopAI:getParkingType() == ParkSize.FourGrid2x2;
end


function GridPather.exPathBlocked(obj , x, y, ndx)
        return obj.curBoard.mWallObstructions:getValue(ndx) or (obj.mPathAsLargeTroop and obj.mExpandedWall:getValue(ndx)) or obj.mObstructed:getValue(ndx);
end

function GridPather.exShotBlockedByWall(obj , x, y, ndx)
        return obj.curBoard.mWallObstructions:getValue(ndx);
end

function GridPather:requestMoveToPath(pather, destination, park)

	if park == nil then
		park = false;
	end	

	local pathType = PathType.MoveToLocation;

	if park then
	    pathType = PathType.MoveToLocationAndPark;
	end
	if pather.troopAI.pathRequest == nil then
	    pather.troopAI.pathRequest = AttackPathRequest.new();
	end
	pather.troopAI:setParkingPlace(false);
	pather.troopAI.pathRequest.pathRequested = true;
	pather.troopAI.pathRequest.pathAvailable = false;
	pather.troopAI.pathRequest.pathData:clear();
	pather.troopAI.pathRequest.target = nil;
	pather.troopAI.pathRequest.pathingTroop = pather;
	pather.troopAI.pathRequest.location = destination;
	pather.troopAI.pathRequest.pathType = pathType;
	if pather.currentBattle ~= nil then
	    pather.troopAI.pathRequest.simFrameRequested = pather.currentBattle.simTimestep;
	end
end

function GridPather:requestTargetPath(pather, target , parkingForWait)
	if parkingForWait == nil then
		parkingForWait = false;
	end
		
	local pathType = PathType.AttackTarget;

	if pather.troopAI.pathRequest == nil then
	    pather.troopAI.pathRequest = AttackPathRequest.new();
	end
--print("-- GridPather:requestTargetPath")
	pather.troopAI.pathRequest:clear(pather);
	pather.troopAI:setParkingPlace(false);
	pather.troopAI.pathRequest.parkingForWait = parkingForWait;
	pather.troopAI.pathRequest.pathRequested = true;
	pather.troopAI.pathRequest.pathAvailable = false;
	pather.troopAI.pathRequest.pathData:clear();
	pather.troopAI.pathRequest.target = target;
	pather.troopAI.pathRequest.pathType = pathType;
	if pather.currentBattle ~= nil then
	    pather.troopAI.pathRequest.simFrameRequested = pather.currentBattle.simTimestep;
	end
end

--批量寻路 , 默认每批次处理5个寻路请求
function GridPather:batchPathing(troops)
	local maxProccessPathingNum = GridPatherConst.PATHING_NUM;
	local i = 0;
	local waitingPathNum = 0;
	local battle = nil;
	local battleSimTimestep = 0;
	while i < maxProccessPathingNum do
	    local curMinPathRequestFrame = MathConst.MaxValue;
	    local troop = nil;
	    waitingPathNum = 0;
	    for j = 1 , troops:getSize() , 1 do
	        local troop2 = troops[j];
	        battle = troop2.currentBattle;
	        if  troop2.troopAI.pathRequest ~= nil and troop2.troopAI.pathRequest:needsPath() then
	            waitingPathNum = waitingPathNum + 1;
	            if troop2.troopAI.pathRequest:simFrame() < curMinPathRequestFrame then
	                curMinPathRequestFrame = troop2.troopAI.pathRequest:simFrame();
	                troop = troop2;
	            end
	        end
	    end

	    i = i + 1;

	    if battle ~= nil then
	        battleSimTimestep = battle.simTimestep;
	    end

	    if troop == nil then
	        break;
	    end

	    local canPath = true;

	    if troop.currentBattle ~= nil and curMinPathRequestFrame > battleSimTimestep then
	        canPath = false;
	    end

	    if canPath then
	        self:batchPath(troop);
	        troop.troopAI.pathRequest:didPathfind(battleSimTimestep);
	        if troop.troopAI.pathRequest.pathData.expensive then
	            i = i + maxProccessPathingNum;
	        end
	        waitingPathNum = waitingPathNum - 1;
	    end
	end

	if waitingPathNum > GridPatherConst.pathDelayCount then
	    GridPatherConst.pathDelayCount = waitingPathNum;
	end

	if waitingPathNum == 0 then
	    GridPatherConst.noPathFrames = GridPatherConst.noPathFrames + 1;
	    if GridPatherConst.noPathFrames > 2 then
	        GridPatherConst.pathDelayCount = 0;
	    end
	else
	    GridPatherConst.noPathFrames = 0;
	end
end

function GridPather:batchPath(troop)


        if not troop.troopAI.pathRequest:needsPath() then
            return;
        end

        local pathType = troop.troopAI.pathRequest.pathType;

        if pathType == PathType.MoveToLocation or pathType == PathType.MoveToLocationAndPark then
	troop.troopAI.pathRequest.pathData = self:pathToDestination(troop, troop.troopAI.pathRequest.location, pathType == PathType.MoveToLocationAndPark);
	troop.troopAI.pathRequest.pathAvailable = true;
	troop.troopAI.pathRequest.pathRequested = false;
	--troop.troopAI.pathRequest.pathData.path =  self:optimizePath(troop.troopAI.pathRequest.pathData.path);

          --   print("path over PathType.MoveToLocation" ,  troop.troopAI.pathRequest.pathData.path:getNumWaypoints())
        elseif pathType == PathType.AttackTarget then
         
            troop.troopAI.pathRequest.pathData = self:queuedTargetPathWithTroopObstructions(troop, troop.troopAI.pathRequest.target , troop.troopAI.pathRequest.parkingForWait);
            troop.troopAI.pathRequest.pathAvailable = true;

         --    print("path over PathType.AttackTarget" ,  troop.troopAI.pathRequest.pathData.path)
         --   troop.troopAI.pathRequest.pathData.path =  self:optimizePath(troop.troopAI.pathRequest.pathData.path);
            troop.troopAI.pathRequest.pathRequested = false;

 	 --[[
            for i=0 , troop.troopAI.pathRequest.pathData.path:getNumWaypoints() -1 , 1 do
            		local vv =  troop.troopAI.pathRequest.pathData.path:getWaypoint(i);
            		--dbgFlag(vv.x , vv.z)
	            	if  troop.troopAI.pathRequest.pathData.intermediateWall ~= nil then

	            		print("path result :::::::::::::::::::::::::::::::::: " , troop.troopAI.pathRequest.pathData.intermediateWall:getPosition().x,troop.troopAI.pathRequest.pathData.intermediateWall:getPosition().z ,  troop.troopAI.pathRequest.pathData.secondaryDestination, troop.troopAI.pathRequest.pathData.destinationInRange, troop.id , vv.x , vv.z);
	           		else
	           			print("path result :::::::::::::::::::::::::::::::::: " ,  troop.troopAI.pathRequest.pathData.secondaryDestination, troop.troopAI.pathRequest.pathData.destinationInRange, troop.id , vv.x , vv.z);
	            		
	           		end	
            end	
 
            print("path over PathType.AttackTarget" ,  troop.troopAI.pathRequest.pathData.path:getNumWaypoints())
        	 ]]
        else
            print("Bad path request type.");
            troop.troopAI.pathRequest.pathData.path = nil;
        end

        local path = troop.troopAI.pathRequest.pathData.path;
        if path ~= nil and path:getNumWaypoints() > 0 then
            local waypoint = path:getWaypoint(path:getNumWaypoints() - 1);
            troop.troopAI:setParkingPlacePos(waypoint, 0);
        elseif troop.troopAI.pathRequest.pathData.noParking then
            troop.troopAI.pathRequest.pathData.expensive = false;
        else
            troop.troopAI.pathRequest.pathData.expensive = true;
        end
end

function GridPather:queuedTargetPathWithTroopObstructions(pather, target , parkingForWait)
	self:initialize(pather, true, false);
	local result = AttackPathData.new();
	result.path = nil;
	result.destinationInRange = false;
	result.intermediateWall = nil;
	result.secondaryDestination = false;
	result.expensive = false;
	result.noParking = false;
	 
	local v = FVector3.Zero;
	v = PathTool.Park2Offset(pather.troopAI:getParkingType());
	for i = 1 , 2 , 1 do
	    self.parkMode = i;
	    self:updateParking(self.curBoard.GetTroops(), pather.team, true, pather);
	    if self.parkMode ~= ParkT.NORMAL_PARKING then
	        if self.parkMode == ParkT.RELAXED_PARKING then

	            self.mObstructed:clearEmpty();
	            self.mWall:clearEmpty();
	            self.mObstructed:commonOr(self.curBoard.mStructureObstructions);
	            self.mObstructed:commonOr(self.curBoard.terrainObstructions);
	            if self.mCanShootWalls then
	                self.mWall:commonOr(self.curBoard.mWallObstructions);
	          --      print("@@@@@@@@@@@@@@@@@@@@   " , self.mWall:getValue(2645) ,  self:getWall(2645))
	            else
	                self.mObstructed:commonOr(self.curBoard.mWallObstructions);
	            end
	        end
	    end

	    local fVector = pather:getPosition();
	    if pather.troopAI.continueMovement then
	        fVector = pather.troopAI:getMovement():projectPosition(pather, Board.CELL_WIDTH);
	    end

	    local parkSize = self:adjustSize(pather.troopAI:getParkingType());
	    fVector = fVector - PathTool.Park2Offset(parkSize);
	    local cellIndex = BoardUtil.GetCellIndex(fVector);
	    self.mStartNode = self:getNode(cellIndex);
	    self.mClosestClosedNode = nil;
	    local position = target:getPosition();
	    local isWall = target:isWall();
	    local twoPerGrid = pather:getTwoPerGrid();

	    --print("-------------------- twoPerGrid = " , twoPerGrid)
	    local rangeFromCenter = pather:rangeFromCenter(target);
	--    print("::::::::::::::::rangeFromCenter" , rangeFromCenter)
	    local v2 = FVector3.new(rangeFromCenter , 0 , rangeFromCenter);
	    v2 = v2 + Board.CellHalfSize;

	    local searchRange = 0;

	    if parkingForWait then
	        searchRange = 4;
	    end

	  --  local xMinRange = position.x - v2.x - searchRange;
	 --   local xMaxRange = position.x + target:getObstacleSize() + v2.x + searchRange;
	 --   local yMinRange = position.z - v2.z - searchRange;
	 --   local yMaxRange = position.z + target:getObstacleSize() + v2.z + searchRange;
	   
	     local simPos = target:getSimPosition();
	    local xMinRange = simPos.x - v2.x - searchRange;
	    local xMaxRange = simPos.x + target:getObstacleSize() + v2.x + searchRange;
	    local yMinRange =  simPos.z - v2.z - searchRange;
	    local yMaxRange = simPos.z + target:getObstacleSize() + v2.z + searchRange;

 

	    local canShootWall = pather:canShootOverWallsAlways(); 
	    local rsPos = BoardUtil.GetCellCoordinates(target:getSimPosition());
	    local x = rsPos.x;
	    local y = rsPos.z;

	  --  print("start path xMin=" , xMinRange , "xMax=" , xMaxRange - 1 , "yMin=" , yMinRange , "yMax=" , yMaxRange - 1 , rangeFromCenter)
	
 
	    for  _x = xMinRange , xMaxRange - 1  , 1 do
	        for _y = yMinRange ,  yMaxRange - 1 , 1 do
	   -- print("&&&" ,"_x",_x , "_y",_y)
	     
	      	while true do
	      		local cellIndex2 = BoardUtil.GetCellIndex(FVector3.new(_x, 0,_y));
			local FVector3 = BoardUtil.GetCellCenterWithIndex(cellIndex2);
			FVector3 = FVector3 + v;
			if target ~= nil then
			    local isTargetOutOfRangeForPath = false;

			    if twoPerGrid then
			        if pather:targetOutOfRange(FVector3 + PathTool.P2PerOffset1(), target) then
			            isTargetOutOfRangeForPath = true;
			        end

			        if pather:targetOutOfRange(FVector3 + PathTool.P2PerOffset2(), target) then
			            isTargetOutOfRangeForPath = true;
			        end
			    else
			        isTargetOutOfRangeForPath = pather:targetOutOfRange(FVector3, target);
			    end

			    if isTargetOutOfRangeForPath then
			    	--print("break a" , _x , _y )
			        	break;
			    end
			end

			local rsPos = BoardUtil.ConvertGridIndexToGridCoords(cellIndex2);
			local cellX = rsPos.x;
			local cellY = rsPos.z;


			
			if twoPerGrid then
			    if self:getParking():getValue(cellIndex2) and self:getIParking2():getValue(cellIndex2) then
			    	--print("break b" , _x , _y )
			        	break;
			    end
			else
				--print("GGGGGGGGGGGG  " , self:getParking().myData:getSize())
			    if BoardUtil.TestParkingBitsForTroop(self:getParking(), cellX, cellY, parkSize) then
			    	--print("break c" , _x , _y )
			        	break;
			    end

			    if BoardUtil.TestParkingBitsForTroop(self:getObstructed(), cellX, cellY, parkSize) then
			    	--print("break d" , _x , _y )
			        	break;
			    end
			end

			local isShotBlockedByWall = false;

			if not canShootWall then
			    isShotBlockedByWall = self:shotBlockedByWall(cellX, cellY, x, y, pather);
			end

			if isShotBlockedByWall and isWall then
			    isShotBlockedByWall = false;
			end

		--	print("final " , _x , _y , "self.mCanShootWalls=",self.mCanShootWalls,"isShotBlockedByWall=",isShotBlockedByWall,"self:getObstructed():getValue(cellIndex2) =",self:getObstructed():getValue(cellIndex2)," self.mWall:getValue(cellIndex2)=", self.mWall:getValue(cellIndex2) ,"self.mExpandedWall:getValue(cellIndex2)=",self.mExpandedWall:getValue(cellIndex2)  , cellIndex2)
			-- dbgFlag(_x,_y,"flag2")
			if (self.mCanShootWalls or not isShotBlockedByWall) and ((not self:getObstructed():getValue(cellIndex2) and not self.mWall:getValue(cellIndex2)) and not self.mExpandedWall:getValue(cellIndex2)) then
			    local item = self:getNode(cellIndex2);
			    if isShotBlockedByWall then
			        self.mSecondaryTargetNodes:add(item);
			    else
			        self.mTargetNodes:add(item);
			    end
			end
		
			break;
	      	end	
	        end
	    end
	     
	    if self.mTargetNodes:getSize() > 0 then
	        break;
	    end
	end

  
	self.parkMode = ParkT.NORMAL_PARKING;

	if self.mTargetNodes:getSize() == 0 and self.mSecondaryTargetNodes:getSize() == 0 then
		result.path = nil;
		result.destinationInRange = false;
		result.noParking = true;
		return result;
	end

	if self.mTargetNodes:getSize() == 0 then
	    self.mSecondaryTargetPenalty = 0;
	end

	--print("self.mTargetNodes:getSize()  = " , self.mTargetNodes:getSize() )

	if self.mStartNode ~= nil then
 
	    self:initializeOpenList(pather);
	    self:processOpenList();
 
	    result.expensive = self.mNumNodesProcessed > Mathf.Floor(GridPatherConst.PATH_FIND_DEPTH / 16);
	    local item = self.mClosestClosedNode;
	    if target ~= nil and self.mCanShootWalls and not self.mPathAsFlyer and not self.mCanRunOverWalls then
	        self:checkForWallTargets();
	        if self.mIntermediateWall == nil then
	            self:checkParkingPlaceForWalls();
	        end
--print("&&&&&&&&&&&&&&&&&&&&&&&&    " ,  self.mIntermediateWall)
	        if self.mIntermediateWall ~= nil then
	            result.path = self:createPathToTarget();
	            self:offsetPath(pather, result.path, pather:getPosition());
	            result.destinationInRange = false;
	            result.intermediateWall = self.mIntermediateWall;
	            return result;
	        end
	    end

	    result.noParking = false;
	    if self.mTargetNodes:contains(item) then
	        result.destinationInRange = true;
	    else
	        if not self.mSecondaryTargetNodes:contains(item) then
	            result.destinationInRange = false;
	            result.secondaryDestination = false;
	            result.path = nil;
	            return result;
	        end
	        result.destinationInRange = true;
	        result.secondaryDestination = true;
	    end
	    result.path = self:createPathToTarget();
	    self:offsetPath(pather, result.path, pather:getPosition());
	end

	if result.path ~= nil and self.mPathAsFlyer then
	    local gridPath = GridPath.new(false);
	    gridPath:addWaypoint(result.path:getWaypoint(0));
	    gridPath:addWaypoint(result.path:getWaypoint(result.path:getNumWaypoints() - 1));
	    result.path = gridPath;
	end
	return result;
	 
end

function GridPather:examineCellsInLine(x0, y0, x1, y1, func_examineProc)
        local xStep = 0;
        local yStep = 0;
        local diffX = x1 - x0;

        if diffX < 0 then
            diffX = -diffX;
        end

        local diffY = y1 - y0;

        if diffY < 0 then
            diffY = -diffY;
        end

        if x0 < x1 then
            xStep = 1;
        else
            xStep = -1;
        end

        if y0 < y1 then
            yStep = 1;
        else
            yStep = -1;
        end

        local diffXY = diffX - diffY;

        while true do
            if ((x0 >= 0) and (y0 >= 0)) and ((x0 < Board.CellCountX) and (y0 < Board.CellCountY)) then
                local ndx = y0 * Board.CellCountX + x0;
                if func_examineProc(self, x0, y0, ndx) then
                    return true;
                end
            end

            if (x0 == x1) and (y0 == y1) then
                break;
            end

            local num = 2 * diffXY;
            if num > -diffY then
                diffXY = diffXY - diffY;
                x0 = x0 + xStep;
            end

            if (x0 == x1) and (y0 == y1) then
                if ((x0 >= 0) and (y0 >= 0)) and ((x0 < Board.CellCountX) and (y0 < Board.CellCountY)) then
                    local index = y0 * Board.CellCountX + x0;
                    if func_examineProc(self,x0, y0, index) then
                        return true;
                    end
                end
                break;
            end

            if num < diffX then
                diffXY = diffXY + diffX;
                y0 = y0 + yStep;
            end
        end
        return false;
end

function GridPather:shotBlockedByWall(x0, y0, x1, y1, pather)
        return not self.mPathAsFlyer and self:examineCellsInLine(x0, y0, x1, y1, self.exShotBlockedByWall);
end

function GridPather:getNode(index)
        return (index < 0 or index >= self.mNodes:getSize()) and nil or self.mNodes[index];
end

function GridPather:getNeighbors(node)
      
	local rsPos =  BoardUtil.ConvertGridIndexToGridCoords(node.Index);
	local num = rsPos.x;
 	local num2 = rsPos.z;
	local flag = num2 + 1 < Board.CellCountY;

	--print("GridPather:getNeighbors        num2=  " , num2 , Board.CellCountX , "  num =" , num , node.Index , self.mNodes[node.Index + Board.CellCountX] , self.mNodes:getSize())

	local flag2 = num2 - 1 >= 0;
	local flag3 = num + 1 < Board.CellCountX;
	local flag4 = num - 1 >= 0;
	local _N = (not flag) and nil or self.mNodes[node.Index + Board.CellCountX];
	local _S = (not flag2) and nil or self.mNodes[node.Index - Board.CellCountX];
	local _E = (not flag3) and nil or self.mNodes[node.Index + 1];
	local _W = (not flag4) and nil or self.mNodes[node.Index - 1];
	local _NE = (not flag or not flag3) and nil or self.mNodes[node.Index + Board.CellCountX + 1];
	local _NW = (not flag or not flag4) and nil or self.mNodes[node.Index + Board.CellCountX - 1];
	local _SE = (not flag2 or not flag3) and nil or self.mNodes[node.Index - Board.CellCountX + 1];
	local _SW = (not flag2 or not flag4) and nil or self.mNodes[node.Index - Board.CellCountX - 1];

	return {N=_N , S=_S , E=_E , W=_W , NE=_NE , NW=_NW , SE=_SE , SW=_SW};
end

function GridPather:addToOpenList(node)
	
	--print("OOOOOOOOOOOOOOOOOOOO o1 " , Mathf.Floor(node.Index / 32) ,  self:getOpen().myData[Mathf.Floor(node.Index / 32)] , Bit._lshift(1,Mathf.Floor(node.Index / 32)) ,     Bit._or(0  , Bit._lshift(1,Mathf.Floor(node.Index / 32))))
	self:getOpen():setWithValue(node.Index, true);
	--print("OOOOOOOOOOOOOOOOOOOO 1 " ,node.Index  , self:getOpen():getValue(node.Index) , self:getOpen().myData[Mathf.Floor(node.Index / 32)])
	self.mOpenList:add(node);
end

function GridPather:calculateDistanceToTarget(node)
 
	local rsPos = BoardUtil.ConvertGridIndexToGridCoords(node.Index)
	local num = rsPos.x;
	local num2 = rsPos.z;
	local v = FVector3.new(num, 0 ,num2);
	local fixedPointNumber = MathConst.MaxValue;
	local count = self.mTargetNodes:getSize();
	for i = 1 , count  , 1 do

		rsPos = BoardUtil.ConvertGridIndexToGridCoords(self.mTargetNodes[i].Index)
		local num3 = rsPos.x;
		local num4 = rsPos.z;

		local v2 = FVector3.new(num3, 0 ,num4);
		local fixedPointNumber2 = FVector3.SqrDistance(v, v2);
		if fixedPointNumber2 < fixedPointNumber then
		    fixedPointNumber = fixedPointNumber2;
		end
	end

	if self.mSecondaryNode ~= nil then
	    return Mathf.Sqrt(fixedPointNumber);
	end

	count = self.mSecondaryTargetNodes:getSize();

	for j = 0 , count - 1 , 1 do 
	    rsPos = BoardUtil.ConvertGridIndexToGridCoords(self.mSecondaryTargetNodes[j].Index);
	    local num3 = rsPos.x;
	    local num4 = rsPos.z;
	    local v3 = FVector3.new(num3, 0 ,num4);
	    local fixedPointNumber3 = FVector3.SqrDistance(v, v3);
	    if fixedPointNumber3 < fixedPointNumber then
	        fixedPointNumber = fixedPointNumber3;
	    end
	end
	return Mathf.Sqrt(fixedPointNumber);
end

function GridPather:initializeOpenList(pather)
        self:addToOpenList(self.mStartNode);
        self.mStartNode.Parent = nil;
        self.mStartNode.mDistanceFromSource = 0;
        self.mStartNode.mDistanceToTarget = self:calculateDistanceToTarget(self.mStartNode);
        self.mStartNode.mPathingScore = self.mStartNode.mDistanceFromSource + self.mStartNode.mDistanceToTarget;
        if pather ~= nil then
            local v = FixedAngle.FacingForAngleInDegrees(pather.facingInDegrees);
            local cellIndex = BoardUtil.GetCellIndex(v * Board.CELL_WIDTH + pather:getPosition());
            local node = self:getNode(cellIndex);
            if node == nil or node == self.mStartNode then
                return;
            end

            if self.mObstructed:getValue(node.Index) then
                return;
            end

            if self.mWall:getValue(node.Index) then
                return;
            end

            if self.mExpandedWall:getValue(node.Index) then
                return;
            end
            node.Parent = self.mStartNode;
            node.mDistanceFromSource = -0.25;
            node.mDistanceToTarget = self:calculateDistanceToTarget(node);
            node.mPathingScore = node.mDistanceFromSource + node.mDistanceToTarget;
            self:addToOpenList(node);
        end
end

function GridPather:getBestNodeFromOpenList()
        local n = MathConst.MaxValue;
        local gridPatherNode = nil;

        local len = self.mOpenList:getSize();
-- print("FFFFFFFFF  0 " , len)
        for i = 0 , len - 1 , 1 do
        	local current = self.mOpenList[i];
        	if current.mPathingScore < n then
                n = current.mPathingScore;
                gridPatherNode = current;
            end
        end	
        if gridPatherNode ~= nil then
        	--print("OOOOOOOOOOOOOOOOOOOO 222" , gridPatherNode.Index)
            self:getOpen():setWithValue(gridPatherNode.Index, false);
            self.mOpenList:remove(gridPatherNode);
        end

      --  print("--use   " ,gridPatherNode.Index )
        return gridPatherNode;
end

function GridPather:processOpenList()

        local bestNodeFromOpenList = self:getBestNodeFromOpenList();
        self.mNumNodesProcessed = 0;
        local flag = false;
        while bestNodeFromOpenList ~= nil and not flag and self.mNumNodesProcessed < GridPatherConst.PATH_FIND_DEPTH do
        --	print(" self.mNumNodesProcessed   = " ,  self.mNumNodesProcessed)
            self.mClosed:setWithValue(bestNodeFromOpenList.Index, true);
            if self.mTargetNodes:contains(bestNodeFromOpenList) then
                if self.mClosestClosedNode == nil or not self.mTargetNodes.contains(self.mClosestClosedNode) then
                    self.mClosestClosedNode = bestNodeFromOpenList;
                end
                break;
            end
           
            if (self.mClosestClosedNode == nil or bestNodeFromOpenList.mDistanceToTarget < self.mClosestClosedNode.mDistanceToTarget) and not flag then
                self.mClosestClosedNode = bestNodeFromOpenList;
            end

            if not self.foundUnblockedCell and not self.mObstructed:getValue(bestNodeFromOpenList.Index) then
                self.foundUnblockedCell = true;
            end
	
			--print("--check$$ 11 " , bestNodeFromOpenList.Index)
            local nodeRS = self:getNeighbors(bestNodeFromOpenList);
            local nodeN = nodeRS.N;
            local nodeS = nodeRS.S;
            local nodeE = nodeRS.E;
            local nodeW = nodeRS.W;
            local nodeNE = nodeRS.NE;
            local nodeNW = nodeRS.NW;
            local nodeSE = nodeRS.SE;
            local nodeSW = nodeRS.SW;
          
            local flag2 = self.mPathAsFlyer;
            if self.mObstructed:getValue(bestNodeFromOpenList.Index) and not self.foundUnblockedCell then
                flag2 = true;
            end

            if nodeN ~= nil and not self.mClosed:getValue(nodeN.Index) and (flag2 or not self.mObstructed:getValue(nodeN.Index)) then
                self:visitNeighbor(bestNodeFromOpenList, nodeN, false);
            end
     
            if nodeS ~= nil and not self.mClosed:getValue(nodeS.Index) and (flag2 or not self.mObstructed:getValue(nodeS.Index)) then
            	self:visitNeighbor(bestNodeFromOpenList, nodeS, false);
            end

            if nodeE ~= nil and not self.mClosed:getValue(nodeE.Index) and (flag2 or not self.mObstructed:getValue(nodeE.Index)) then
                self:visitNeighbor(bestNodeFromOpenList, nodeE, false);
            end

            if nodeW ~= nil and not self.mClosed:getValue(nodeW.Index) and (flag2 or not self.mObstructed:getValue(nodeW.Index)) then
                self:visitNeighbor(bestNodeFromOpenList, nodeW, false);
            end

            if nodeNE ~= nil and not self.mClosed:getValue(nodeNE.Index) and (flag2 or not self.mObstructed:getValue(nodeNE.Index)) then
                self:visitNeighbor(bestNodeFromOpenList, nodeNE, true);
            end

            if nodeNW ~= nil and not self.mClosed:getValue(nodeNW.Index) and (flag2 or not self.mObstructed:getValue(nodeNW.Index)) then
                self:visitNeighbor(bestNodeFromOpenList, nodeNW, true);
            end

            if nodeSE ~= nil and not self.mClosed:getValue(nodeSE.Index) and (flag2 or not self.mObstructed:getValue(nodeSE.Index)) then
                self:visitNeighbor(bestNodeFromOpenList, nodeSE, true);
            end

            if nodeSW ~= nil and not self.mClosed:getValue(nodeSW.Index) and (flag2 or not self.mObstructed:getValue(nodeSW.Index)) then
                self:visitNeighbor(bestNodeFromOpenList, nodeSW, true);
            end

            if self.mSecondaryNode == nil and self.mSecondaryTargetNodes:contains(bestNodeFromOpenList) then
                self.mSecondaryNode = bestNodeFromOpenList;
            end

            if self.mSecondaryNode ~= nil and self.mWall:getValue(bestNodeFromOpenList.Index) then
                self.mSecondaryTargetPenalty = 0;
            end

            if self.mSecondaryNode ~= nil and self.mSecondaryNode.mPathingScore + self.mSecondaryTargetPenalty < bestNodeFromOpenList.mPathingScore then
                self.mClosestClosedNode = self.mSecondaryNode;

                break;
            end
            bestNodeFromOpenList = self:getBestNodeFromOpenList();
            --local rsPos = BoardUtil.ConvertGridIndexToGridCoords(bestNodeFromOpenList.Index);
            --print(":::bestNodeFromOpenList:::" , bestNodeFromOpenList.Index ,rsPos.x ,rsPos.y , rsPos.z)
            self.mNumNodesProcessed = self.mNumNodesProcessed + 1;
        end
        --UnityEngine.Debug.LogError("mNumNodesProcessed = " + mNumNodesProcessed);
end

function GridPather:visitNeighbor(currentNode, neighborNode, diagonal)
--	print("************** visitNeighbor ********************")
        local orientation = 1;
        if diagonal then
            orientation = orientation + Mathf.Floor(40 / 4096) * 42;
        end

        local fDistance = currentNode.mDistanceFromSource + orientation;
        local fCost = 0;
        local wallCost = self.mWallPathCost;
        local n = Mathf.Floor(wallCost / 3);
        if self:getParking():getValue(neighborNode.Index) then
            fCost = fCost + self.moveThroughParkingCost;
        end

        if not self.mCanRunOverWalls then
        --	print("####  " ,  self.mWall:getValue(neighborNode.Index)  , neighborNode.Index)
            if self.mWall:getValue(neighborNode.Index) then
                fCost = fCost + wallCost;
            elseif self.mExpandedWall:getValue(neighborNode.Index) then
                fCost = fCost + n;
            end
        end

        if not self.mPathAsFlyer and self.mObstructed:getValue(neighborNode.Index) then
            if not self.mObstructed:getValue(currentNode.Index) then
                return;
            end
            fCost = fCost + 5 * (wallCost + orientation);
        end

        fDistance = fDistance + fCost;
  --      print("************22222222*****************" , fDistance , neighborNode.Index , self:getOpen():getValue(neighborNode.Index) )
        if self:getOpen():getValue(neighborNode.Index) then
        	    --print("::11  " , neighborNode.Index)
            if fDistance < neighborNode.mDistanceFromSource then
                neighborNode.Parent = currentNode;
            
                neighborNode.mDistanceFromSource = fDistance;
                neighborNode.mDistanceToTarget = self:calculateDistanceToTarget(neighborNode);
                neighborNode.mPathingScore = neighborNode.mDistanceFromSource + neighborNode.mDistanceToTarget;
                if self:getParking():getValue(neighborNode.Index) then
                    neighborNode.mPathingScore = neighborNode.mPathingScore + 0.5;
                end
            end
        else
        	--print("::parent22  " , neighborNode.Index , currentNode.Index , self:getOpen():getValue(neighborNode.Index))
            self:addToOpenList(neighborNode);
            neighborNode.Parent = currentNode;
            
            neighborNode.mDistanceFromSource = fDistance;
            neighborNode.mDistanceToTarget = self:calculateDistanceToTarget(neighborNode);
            neighborNode.mPathingScore = neighborNode.mDistanceFromSource + neighborNode.mDistanceToTarget;
            if self:getParking():getValue(neighborNode.Index) then
                neighborNode.mPathingScore = neighborNode.mPathingScore + 0.5;
            end
        end
end
 
function GridPather:getWall(ndx)
	if self.closestWall == nil then
	    self.closestWall = self.closestWallEntity;
	end

	local rsPos = BoardUtil.ConvertGridIndexToGridCoords(ndx);
	local x = rsPos.x;
	local y = rsPos.z;
	--print("111:::  "  ,x , y)

	rsPos = BoardUtil.ClampGridCoords(x,y);
	x = rsPos.x;
	y = rsPos.z;

	--print("222:::  "  ,x , y)

	local v = FVector3.new(x, 0 ,y);
	self.closeWallPosition = v; 
	local box = FBoundingBox2d.new(self.closeWallPosition, Board.CELL_WIDTH);
	return self.curBoard.GetCollisionMgr():findBestEnemyIntersects(box, function(theEntity) 

	--	print("----------closestWallEntity1 " , theEntity )
		        if theEntity == nil then
		            return 0;
		        end
	--	print("----------closestWallEntity2 " , theEntity:isDead()  )
		        if theEntity:isDead() then
		            return 0;
		        end
	--	print("----------closestWallEntity3 " ,  theEntity:isWall() )
		        if not theEntity:isWall() then
		            return 0;
		        end
	--	print("----------closestWallEntity4 " , self.closeWallPosition.x,self.closeWallPosition.z  , (self.closeWallPosition - theEntity:getPosition()):sqrMagnitude() )
		        return MathConst.MaxValue - (self.closeWallPosition - theEntity:getPosition()):sqrMagnitude();


	end	, EntityTeam.Attacker);
end

function GridPather:exPathBlockedByWall(x, y, ndx)

--print("^^^^^^^^^^^^^^^^^  " , x,y,ndx ,  self.mWall:getValue(ndx) ,  self:getWall(ndx))

        if self.mWall:getValue(ndx) then
            local wall = self:getWall(ndx);
            if wall ~= nil then
                self.blockPathWall = wall;
                --print("~~~~~1~~~~~ " , ndx , wall);
            end
        elseif self.mExpandedWall:getValue(ndx) then
            local wall2 = self:getWall(ndx);

            if wall2 ~= nil then
            	--print("~~~~~2~~~~~ " , ndx , wall);
                self.offBlockPathWall = wall2;
            end
 
            wall2 = self:getWall(ndx + 1);
            if wall2 ~= nil then
                self.offBlockPathWall = wall2;
            end

            wall2 = self:getWall(ndx + Board.CellCountX);
            if wall2 ~= nil then
                self.offBlockPathWall = wall2;
            end
     
        end

        --[[
        //if (this.offBlockPathWall != null)
        //{
        //    UnityEngine.Debug.LogError("!!!!!!11111111!!!!!!!!    " + this.offBlockPathWall.Position);
        //}

        //if (this.blockPathWall != null)
        //{
        //    UnityEngine.Debug.LogError("!!!!!2222222!!!!!!!!!    " + this.blockPathWall.Position);
        //}
        ]]
        return self.blockPathWall ~= nil or self.offBlockPathWall ~= nil;
end

function GridPather:checkForWallTargets()
        self.mIntermediateWall = nil;
        if not self.mCanShootWalls then
            return;
        end

        self.blockPathWall = nil;
        self.offBlockPathWall = nil;
        
        local parent = self.mClosestClosedNode;

        while parent ~= nil do
	local rsPos = BoardUtil.ConvertGridIndexToGridCoords(parent.Index);
	local x = rsPos.x;
	local y = rsPos.z;
	if parent.Parent == nil then
		break;
	end	
	--print("dddd##################1  " , x , y ,parent.Index)
	--dbgFlag(x,y);
	self:exPathBlockedByWall(x,y,parent.Index);
	--print("dddd##################2  " , x , y )
	parent = parent.Parent
        end	
--print("################################  " , self.offBlockPathWall , self.blockPathWall  )
        if self.offBlockPathWall ~= nil then
            self.mIntermediateWall = self.offBlockPathWall;
        end

        if self.blockPathWall ~= nil then
            self.mIntermediateWall = self.blockPathWall;
        end
end

function GridPather:pathToDestination(pather, destination, park)
        self:initialize(pather, true, false);
        self:updateParking(self.curBoard.GetTroops(), pather.team, true, pather);
        local result = AttackPathData.new();
        result.path = nil;
        result.destinationInRange = false;
        result.intermediateWall = nil;
        result.secondaryDestination = false;
        result.expensive = false;
        result.noParking = false;
        local fVector = pather:getPosition();
        if pather.troopAI.continueMovement then
            fVector = pather.troopAI:getMovement().projectPosition(pather, Board.CELL_WIDTH);
        end
        fVector = fVector + PathTool.Park2Offset(pather.troopAI:getParkingType());
        self.moveThroughParkingCost = Board.CELL_WIDTH;
        if park then
            self.moveThroughParkingCost = self.moveThroughParkingCost  * 3;
        end

        local cellIndex = BoardUtil.GetCellIndex(fVector);
        self.mStartNode = self:getNode(cellIndex);
        local cellIndex2 = BoardUtil.GetCellIndex(destination);
        local node = self:getNode(cellIndex2);
        self.mTargetNodes:add(node);
        self.mSecondaryTargetPenalty = 0;
        if self.mStartNode ~= nil then
            self:initializeOpenList(pather);
            self:processOpenList();
            result.expensive = self.mNumNodesProcessed > Mathf.Floor(GridPatherConst.PATH_FIND_DEPTH/16);
            node = self.mClosestClosedNode;
            if self.mCanShootWalls and not self.mPathAsFlyer and not self.mCanRunOverWalls then
                self:checkForWallTargets();
                if self.mIntermediateWall == nil then
                    self:checkParkingPlaceForWalls();
                end

                if self.mIntermediateWall ~= nil then
                    result.path = self:createPathToTarget();
                    self:offsetPath(pather, result.path, pather:getPosition());
                    result.destinationInRange = false;
                    result.intermediateWall = self.mIntermediateWall;
                    return result;
                end
            end

            if self.mTargetNodes:contains(node) then
                result.destinationInRange = true;
            end

            result.path = self:createPathToTarget();

            if park then
                self:adjustForParking(result.path, pather);
            end

            self:offsetPath(pather, result.path, pather:getPosition());
        end
        return result;
end

function GridPather:checkParkingPlaceForWalls()
        local range = (not self.mPathAsLargeTroop) and 1 or 2;
        local gridPatherNode = self.mClosestClosedNode;
 
        local rsPos = BoardUtil.ConvertGridIndexToGridCoords(gridPatherNode.Index);

        local cellX = rsPos.x;
        local cellY = rsPos.z;

        if self.mPathAsFlyer or self.mCanRunOverWalls then
            return;
        end

        local offest = 0;

        if self.mPathAsLargeTroop then
            offest = -1;
        end

        for i = 0 , range-1 , 1 do
            for j = 0 , range-1 , 1 do
                local cellIndex = BoardUtil.ConvertGridCoordsToGridIndex(cellX + j + offest, cellY + i + offest);
                if cellIndex >= 0 and cellIndex < self.mWall:getCount() then
                    if self.mWall:getValue(cellIndex) then
                        self.mIntermediateWall = self:getWall(cellIndex);
                        if self.mIntermediateWall ~= nil then
                            return;
                        end
                    end
                end
            end
        end
end

function GridPather:createPathToTarget()
        local gridPatherNode = self.mClosestClosedNode;
        if gridPatherNode ~= nil then
            local infantry = self:getParking():getValue(gridPatherNode.Index);
            local gridPath = GridPath.new(infantry);
            local parent = self.mClosestClosedNode;
            if self.mStartNode == parent then
                gridPath:addWaypoint(BoardUtil.GetCellCenterWithIndex(parent.Index));
                gridPath:addWaypoint(BoardUtil.GetCellCenterWithIndex(parent.Index));
                self:getOnPath():setValue(parent.Index , true);
            else
                gridPath:addWaypoint(BoardUtil.GetCellCenterWithIndex(parent.Index));
                self:getOnPath():setValue(parent.Index, true);
                parent = parent.Parent;
                while parent ~= nil and parent.Parent ~= nil do
                    gridPath:addWaypoint(BoardUtil.GetCellCenterWithIndex(parent.Index));
                    self:getOnPath():setValue(parent.Index , true);
                    parent = parent.Parent;
                end
                gridPath:addWaypoint(BoardUtil.GetCellCenterWithIndex(parent.Index));
                self:getOnPath():setValue(parent.Index , true);
                gridPath:reverse();
            end
            return gridPath;
        end
        return nil;
end

--从路径点的末端往前找可以停车的位置
function GridPather:adjustForParking(path, pather)
        local i = path:getNumWaypoints() - 1;
        local minNumOffest = Mathf.Floor(i / 4) + 1;
        local targetSizeParkingIndex = 0;
        local oneSizeParkingIndex = 0;
        local parkSizeType = pather.troopAI:getParkingType();
        while i >= 1 do
            local waypoint = path:getWaypoint(i);
            local cellIndex = BoardUtil.GetCellIndex(waypoint);
 
            local resPos = BoardUtil.ConvertGridIndexToGridCoords(cellIndex);

            local x = resPos.x;
            local y = resPos.z;


            if not BoardUtil.TestParkingBitsForTroop(self:getParking(), x, y, parkSizeType) then
                targetSizeParkingIndex = i;
                break;
            end

            if not BoardUtil.TestParkingBitsForTroop(self:getParking(), x, y, ParkSize.OneGrid) then
                oneSizeParkingIndex = i;
            end

            i = i - 1;

            if i < path:getNumWaypoints() - minNumOffest then
                break;
            end
        end

        if targetSizeParkingIndex > 0 then
            i = targetSizeParkingIndex;
        elseif oneSizeParkingIndex > 0 then
            i = oneSizeParkingIndex;
        else
            i = path:getNumWaypoints() - 1;
        end

        if i <= 0 then
            i = 1;
        end

        pather.troopAI:setParkingPlacePos(path:getWaypoint(i - 1), 0);
        path:trimTo(i);
end

function GridPather:convertGridCoordsToGridIndex(xIndex, yIndex)
        return yIndex * Board.CellCountX + xIndex;
end

function GridPather:addOffsets(pathToTarget, startPos, endingOffset)
        if pathToTarget ~= nil then
            local startingOffset = startPos - pathToTarget:getWaypoint(0);
            pathToTarget:addLerpOffsets(startingOffset, endingOffset);
        end
end

function GridPather:offsetPath(pather, path, startPos)

	--print(pather.ClassName , pather.getTwoPerGrid)
        local twoPerGrid = pather:getTwoPerGrid();
        local fVector = FVector3.new(0,0,0);
        local flag = false;
        if self.mClosestClosedNode ~= nil then
            flag = self:getParking():getValue(self.mClosestClosedNode.Index);
        end

        if twoPerGrid then
            if flag then
                fVector = fVector + PathTool.P2PerOffset1();
            else
                fVector = fVector + PathTool.P2PerOffset2();
            end
        else
            fVector = PathTool.Park2Offset(pather.troopAI:getParkingType());
        end
        self:addOffsets(path, startPos, fVector);
end

function GridPather:getIParking2()
        return self.mInfantryParking2;
end

function GridPather:adjustSize(parkSize)
        if self.parkMode == ParkT.NORMAL_PARKING then
            return parkSize;
        end

        if self.parkMode == ParkT.RELAXED_PARKING then

        	if parkSize == ParkSize.OneGrid then
        		return ParkSize.HalfGrid;
        	elseif parkSize == ParkSize.TwoGrids then
        	            return ParkSize.OneGrid;
        	elseif parkSize == ParkSize.FourGrid2x2 then
        	            return ParkSize.TwoGrids;
        	else
        	   	return parkSize;
        	end	
        else
            if self.parkMode ~= ParkT.RELAXED_PARKING then
                return parkSize;
            end

            if parkSize == ParkSize.OneGrid then
            	return ParkSize.HalfGrid;
            elseif parkSize == ParkSize.TwoGrids then
            	return ParkSize.HalfGrid;
            elseif parkSize == ParkSize.FourGrid2x2 then
            	return ParkSize.OneGrid;
            else 
            	return parkSize;
            end	
            
        end
end

function GridPather:setParkBitsForUnit(patherTeam, curTroop, PARK_LIMIT_SQR)
        local isFlying = curTroop:isFlying();
        if isFlying ~= self.mPathAsFlyer then
            return;
        end

        if curTroop.troopAI == nil or curTroop:isDead() then
            return;
        end

        local hasParkingPlace = curTroop.troopAI:hasParkingPlace();

        local parkSizeType = self:adjustSize(curTroop.troopAI:getParkingType());

        if hasParkingPlace then

            local parkingPlace = curTroop.troopAI:getParkingPlace();

            local twoPerGrid = curTroop.getTwoPerGrid;

            if patherTeam ~= curTroop.team and (parkingPlace - curTroop:getPosition()):magnitude() > PARK_LIMIT_SQR then
                return;
            end

            BoardUtil.SetParkingBitsForTroop(self:getParking(), parkingPlace, parkSizeType);

            if twoPerGrid and curTroop.troopAI.infantryParkPlace2 then
                BoardUtil.SetParkingBitsForTroop(self:getIParking2(), parkingPlace, parkSizeType);
            end
        end

        if curTroop.troopAI:getHasIntermediateParkingPlace() and patherTeam == curTroop.team then
            BoardUtil.SetParkingBitsForTroop(self:getParking(), curTroop.troopAI:getIntermediateParkingPlace(), parkSizeType);
        end
end

function GridPather:updateParking(troops, patherTeam, clear, pather)
	if clear == nil then
		clear = true;
	end

	local parkingSize = Board.CELL_WIDTH;

	parkingSize = parkingSize * parkingSize;

	if  clear then
	    self:getParking():clearEmpty();

	    self:getIParking2():clearEmpty();
	end

	for  i = 1  , troops:getSize() , 1 do
	    local troop = troops[i];
	 
	    if not troop:isDead() then
	        if troop ~= pather then
	            self:setParkBitsForUnit(patherTeam, troop, parkingSize);
	        end
	    end
	end
end

function GridPather:initialize(pather, isBatch, rallyPath)

	if isBatch == nil then
		isBatch = true;
	end

	if rallyPath == nil then
		rallyPath = false;
	end
		
	self.mIntermediateWall = nil;
	self.mOpenList:clear();
	self.foundUnblockedCell = false;
	self.mStartNode = nil;
	self.mTargetNodes:clear();
	self.mClosestClosedNode = nil;
	self.mSecondaryTargetNodes:clear();
	self.mSecondaryNode = nil;
	print("XXXXXXXXXXXXXXXXXXXXXXXXX1")
	self:getOpen():setAll(false);
	self:getOnPath():setAll(false);
	self.mClosed:setAll(false);
	self:getParking():setAll(false);
	self.mObstructed:setAll(false);
	self.mWall:setAll(false);
	self.mExpandedWall:setAll(false);
	self.moveThroughParkingCost = 0;

	if pather ~= nil then
	    self.mSecondaryTargetPenalty = 1;
	    self.mWallPathCost = 1;
	    self.mCanShootWalls = pather:canShootWalls();

	    if pather.team == EntityTeam.Defender then
	        self.mCanShootWalls = false;
	    end

	    self.mPathAsFlyer = pather:isFlying();
	    self.mCanRunOverWalls = pather:canRunOverWalls();
	    self.mPathAsLargeTroop = self:pathAsLarge(pather);
	else
	    self.mSecondaryTargetPenalty = 1;
	    self.mWallPathCost = 1;
	    self.mCanShootWalls = false;
	    self.mPathAsFlyer = false;
	    self.mCanRunOverWalls = false;
	    self.mPathAsLargeTroop = false;
	end

	if rallyPath then
	    self.mCanShootWalls = false;
	    self.mCanRunOverWalls = false;
	    self.mPathAsLargeTroop = true;
	end

	if not self.mPathAsFlyer then
	    self.mObstructed:commonOr(self.curBoard.mStructureObstructions);
	    self.mObstructed:commonOr(self.curBoard.terrainObstructions);
	    if self.mCanShootWalls then
	        self.mWall:commonOr(self.curBoard.mWallObstructions);
	    else
	        self.mObstructed:commonOr(self.curBoard.mWallObstructions);
	    end
	end

	if self.mPathAsLargeTroop then
	    self:getOpen():commonOr(self.mObstructed);
	    self.mObstructed:offsetOr(self:getOpen(), -1);
	    self.mObstructed:offsetOr(self:getOpen(), -Board.CellCountX);
	    self.mObstructed:offsetOr(self:getOpen(), -1 - Board.CellCountX);
	    if  self.mCanShootWalls then
	        self.mExpandedWall:commonOr(self.mWall);
	        self.mExpandedWall:offsetOr(self.mWall, -1);
	        self.mExpandedWall:offsetOr(self.mWall, -Board.CellCountX);
	        self.mExpandedWall:offsetOr(self.mWall, -1 - Board.CellCountX);
	    end
	end
	print("XXXXXXXXXXXXXXXXXXXX2")
	self:getOpen():setAll(false);
end



function GridPather:pathBlocked(ndx1, ndx2)
       
        local resPos = BoardUtil.ConvertGridIndexToGridCoords(ndx1);
        local x = resPos.x;
        local y = resPos.z;

        resPos = BoardUtil.ConvertGridIndexToGridCoords(ndx2);

        local x2 = resPos.x;
        local y2 = resPos.z;
        return self:examineCellsInLine(x, y, x2, y2, self.exPathBlocked);
end

function GridPather:optimizePath(path)
        if path:getNumWaypoints() < 3 then
            return path;
        end

        local gridPath = GridPath.new(path:infanty2Path());

        local waypoint = path:getWaypoint(0);

        gridPath:addWaypoint(waypoint);

        local cellIndex = BoardUtil.GetCellIndex(waypoint);

        for i = 1 ,  path:getNumWaypoints() - 2 , 1 do  
            local waypoint2 = path:getWaypoint(i + 1);
            local cellIndex2 = BoardUtil.GetCellIndex(waypoint2);
            if self:pathBlocked(cellIndex, cellIndex2) then
                waypoint = path:getWaypoint(i);
                gridPath:addWaypoint(waypoint);
                cellIndex = BoardUtil.GetCellIndex(waypoint);
            end
        end
        gridPath:addWaypoint(path:getWaypoint(path:getNumWaypoints() - 1));
        return gridPath;
end

function GridPather:pathWallCheck(pather, pathData , checkParkingPlace)
        if checkParkingPlace == nil then
        	checkParkingPlace = true;
        end	

        if pathData.path == nil then
            return pathData;
        end

        if pathData.intermediateWall ~= nil then
            return pathData;
        end

        if pathData.path:getNumWaypoints() < 1 then
            return pathData;
        end

        self.blockPathWall = nil;

        self.offBlockPathWall = nil;

        local waypoint = pathData.path:getWaypoint(0);

        for i = 0 , pathData.path:getNumWaypoints() - 1 , 1 do  

            waypoint = pathData.path:getWaypoint(i);

           -- local cellIndex = BoardUtil.GetCellIndex(waypoint);-- Mathf.Round(waypoint.z) * Board.CellCountX + Mathf.Round(waypoint.x);--
 	
 	local cellIndex = Mathf.Ceil(waypoint.z) * Board.CellCountX + Mathf.Ceil(waypoint.x);

            local resPos = BoardUtil.ConvertGridIndexToGridCoords(cellIndex);

            local x =  resPos.x;

            local y = resPos.z;

            self:exPathBlockedByWall(x, y, cellIndex); 

     --    print("TTTTTTTTTTTTTTTTTT   check  " , x, y, cellIndex ,waypoint.x,waypoint.z , self.offBlockPathWall , self.blockPathWall )
	--dbgFlag(x,y,"flag2")
            pathData.intermediateWall = self.offBlockPathWall;

            if self.blockPathWall ~= nil then
                pathData.intermediateWall = self.blockPathWall;
            end

            if pathData.intermediateWall ~= nil then
                return pathData;
            end
        end

        if pathData.intermediateWall == nil and checkParkingPlace then 
            pathData.intermediateWall = self:checkPathParkingPlace(pather, waypoint);
        end

        return pathData;
end

function GridPather:checkPathParkingPlace(pather, parkingPlace)

        local num = BoardUtil.GetCellIndex(parkingPlace);

        local boardEntity = nil;

        if self:pathAsLarge(pather) then
        --   print("#############111")
            local resPos = BoardUtil.GetCellCoordinates(parkingPlace);

            local _x = resPos.x;

            local _y = resPos.z;

            for i = _x - 1 , _x , 1 do

                for j = _y - 1 , _y , 1 do

                    if i >= 0 and i < Board.CellCountX and j >= 0 and j < Board.CellCountY then

                        num = BoardUtil.ConvertGridCoordsToGridIndex(i, j);

                        if self.mWall:getValue(num) then

                            boardEntity = self:getWall(num);

                            if boardEntity ~= nil then

                                return boardEntity;

                            end

                        end

                    end

                end

            end

        elseif self.mWall:getValue(num) then
	--print("#############222" , num)
            boardEntity = self:getWall(num);

        end

        return boardEntity;
end

function GridPather:oKToParkHere(pather, pos)

        self:updateParking(self.curBoard.GetTroops(), pather.team, true, pather);
 
        local resPos = BoardUtil.GetCellCoordinates(pos);

        local x = resPos.x;

        local y = resPos.z;

        return not BoardUtil.TestParkingBitsForTroop(self:getParking(), x, y, pather.troopAI:getParkingType());
end

function GridPather:checkForWallsWhenFiring(owner, pos, target)

        if target == nil then

            return nil;

        end

        self.mWallForFiring = nil;

        if target ~= nil then

            self.mWallCount = 0;

            if self.mPathAsFlyer then

                return nil;

            end
        --    print("AAAAAAAAAAAAAAAAAA00000   ",pos.x , pos.z)
            local resPos = BoardUtil.GetCellCoordinates(pos);
 --print("AAAAAAAAAAAAAAAAAA11111   ",resPos.x , resPos.z)
            local x = resPos.x;

            local y = resPos.z;
        --  print("AAAAAAAAAAAAAAAAAA222222   ",target:getPosition().x , target:getPosition().z)
            resPos = BoardUtil.GetCellCoordinates(target:getPosition());
 --print("AAAAAAAAAAAAAAAAAA333333   ",resPos.x , resPos.z)
            local x2 = resPos.x;

            local y2 = resPos.z;

            self:examineCellsInLine(x, y, x2, y2, function(obj , x, y, ndx )

--print("HHH" , ndx, x,y ,self.curBoard.mWallObstructions:getValue(ndx)  )
	--	print("waaalllllllllllllllllllll000000    " , x, y)
		if self.curBoard.mWallObstructions:getValue(ndx) then
		    local wall = self:getWall(ndx);
		    if wall ~= nil then
		        self.mWallCount = self.mWallCount + 1;
		    end

		    if self.mWallForFiring == nil then

		--    	print("waaalllllllllllllllllllll    " , x, y)
		        self.mWallForFiring = wall;
		    end
		end
		return false;
            end);
        end

        return self.mWallForFiring;
end

 