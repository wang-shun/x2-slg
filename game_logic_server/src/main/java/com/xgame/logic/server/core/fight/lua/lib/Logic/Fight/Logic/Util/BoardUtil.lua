BoardUtil = {}

local this = BoardUtil;

function BoardUtil.ConvertGridCoordsToGridIndex(xIndex, yIndex)
        return yIndex * Board.CellCountX + xIndex;
end

function BoardUtil.SetObstructionForBuildingAtPositionWithObstructions(obstructions, building, obstructed)
 
        local xMin = Mathf.Floor(building:getPosition().x);
        local xMax = xMin + building:getObstacleSize();

        local yMin = Mathf.Floor(building:getPosition().z);
        local yMax = yMin + building:getObstacleSize();

        for x = xMin , xMax-1 , 1 do
            for y = yMin , yMax-1 , 1 do
                local theposition = this.ConvertGridCoordsToGridIndex(x, y);

         --       print("------------------do set    " , x ,y ,obstructed  , theposition)
                 obstructions:setValue(theposition,obstructed);

                 --[[
                 if obstructed then
                    local gg = UnityEngine.GameObject.Instantiate(GameObject.Find("flag"));
                    gg.transform.parent = GameObject.Find("bb").transform;
                    gg.transform.localPosition = Vector3.New(x,0,y);
                  end  
                  ]]
            end
        end
 
      --  print("settttttttttt333333 " , BoardUtil.TestParkingBitsForTroop(obstructions, 39,44, ParkSize.OneGrid))
end

--设置建筑的阻挡信息, 墙和其他建筑的阻挡分开, 寻路时会有选择的合并,  阻挡尺寸为0的 不参与阻挡信息设置
function BoardUtil.SetObstructionForBuildingAtPosition(building, _obstructed)
        if building:getObstacleSize() > 0 then
            local obstructed = _obstructed and not building.CanBeMovedThru;

            if building.superType == BuildingType.B_WeiQiang then
                this.SetObstructionForBuildingAtPositionWithObstructions(Board.mWallObstructions, building, obstructed);
                -- print("设置建筑的阻挡信息 " ,building.superType )
            else
                this.SetObstructionForBuildingAtPositionWithObstructions(Board.mStructureObstructions, building, obstructed);
            end
       end 
end
 

function BoardUtil.SetParkingBits(obstructionArray, position, twoByTwo)
        local _x = (not twoByTwo) and 1 or 2;
        local _y = _x;
        local cellX = 0;
        local cellY = 0;
        local rsPos = this.GetCellCoordinates(position, cellX, cellY); 
        cellX = rsPos.x;
        cellY = rsPos.z;
        local offest = 0;
        if twoByTwo then
            offest = -1;
        end

        for i = 0 ,  _y , 1 do
            for  j = 0 ,  _x , 1 do
                local cellIndex = this.ConvertGridCoordsToGridIndex(cellX + j + offest, cellY + i + offest);

                --print("VV " , position.x, position.y ,position.z, (cellX + j + offest), (cellY + i + offest) , cellIndex , obstructionArray:getCount() )

                if cellIndex >= 0 and cellIndex < obstructionArray:getCount() then
                    obstructionArray:setValue(cellIndex , true);
                end
            end
        end
end

function BoardUtil.ClampGridCoords(x , y)
        if x < 0 then
            x = 0;
        elseif x > Board.CellCountX - 1 then
            x = Board.CellCountX - 1;
        end

        if y < 0 then
            y = 0;
        elseif y > Board.CellCountY - 1 then
            y = Board.CellCountY - 1;
        end

        return {x=x , z=y};
end

function BoardUtil.GetCellCoordinates(pos)
        x = Mathf.Floor((pos.x - Board.GetMIN_X()) / Board.CELL_WIDTH);
        y = Mathf.Floor((pos.z - Board.GetMIN_Y()) / Board.CELL_WIDTH);
        local rsPos = this.ClampGridCoords(x , y);
        return {x = rsPos.x , z = rsPos.z};
end

function BoardUtil.SetParkingBitsForTroop(obstructionArray, position, parkSizeType)
    --print("------------------  "  ,position.x , position.y , position.z )
        position = position - PathTool.Park2Offset(parkSizeType);
        if parkSizeType == ParkSize.OneGrid or parkSizeType == ParkSize.HalfGrid then
            this.SetParkingBits(obstructionArray, position, false);
        elseif parkSizeType == ParkSize.TwoGrids then
            this.SetParkingBits(obstructionArray, position, false);
        elseif parkSizeType == ParkSize.FourGrid2x2 then
            this.SetParkingBits(obstructionArray, position, true);
        else
            this.SetParkingBits(obstructionArray, position, false);
        end
end

 

function BoardUtil.GetCellIndex(pos)
        local num = Mathf.Floor((pos.x - Board.GetMIN_X()) / Board.CELL_WIDTH);
        local num2 = Mathf.Floor((pos.z - Board.GetMIN_Y()) / Board.CELL_WIDTH);
      --print("$$ " , num,num2 , pos.x , pos.z)
        local rsPos = this.ClampGridCoords(num, num2);

        
        return rsPos.z * Board.CellCountX + rsPos.x;
end

function BoardUtil.ConvertGridIndexToGridCoords(index)
       local x = index % Board.CellCountX;
       local y = index / Board.CellCountX;

        return {x = Mathf.Floor(x) , z = Mathf.Floor(y)}
end


function BoardUtil.GetCellCenter(pos)
        return this.GetCellCenterWithIndex(this.GetCellIndex(pos));
end

function BoardUtil.GetCellCenterWithIndex(index)
        local rsPos = this.ConvertGridIndexToGridCoords(index);

        local x = rsPos.x;
        local y = rsPos.z;

        rsPos = this.ClampGridCoords(x, y);
        local v = FVector3.new(rsPos.x, 0 ,rsPos.z);
        v.x = v.x + Board.GetMIN_X();
        v.z = v.z + Board.GetMIN_Y();
        return v + Board.CellHalfSize;
end


--根据停车尺寸 , 检测是否是阻挡点 , true 是 , false 不是
function BoardUtil.TestParkingBitsForTroop(obstructionArray, x, y, parkSizeType)
   
        if parkSizeType == ParkSize.OneGrid or parkSizeType == ParkSize.HalfGrid then
            return this.TestParkingBits(obstructionArray, x, y, false);
        end

        if parkSizeType == ParkSize.TwoGrids then
            if this.TestParkingBits(obstructionArray, x, y, false) then
                return true;
            end

            x = x - 1;

            if this.TestParkingBits(obstructionArray, x, y, false) then
                return true;
            end

            x = x + 2;

            if this.TestParkingBits(obstructionArray, x, y, false) then
                return true;
            end

            x = x - 1;
            y = y - 1;
            if this.TestParkingBits(obstructionArray, x, y, false) then
                return true;
            end

            y = y + 2;
            return this.TestParkingBits(obstructionArray, x, y, false);
        else
            if parkSizeType == ParkSize.FourGrid2x2 then
                return this.TestParkingBits(obstructionArray, x, y, true);
            end

            print("Unknown parking size type.");

            return this.TestParkingBits(obstructionArray, x, y, false);
        end
end

--检测是否是阻挡点 , true 是 , false 不是
function BoardUtil.TestParkingBits(obstructionArray, x, y, twoByTwo)
        local range = (not twoByTwo) and 1 or 2;
        local offest = 0;
        if twoByTwo then
            offest = -1;
        end
        --print("start do check " , x,y , offest,range)
        for i = 0 , range - 1 , 1 do
            for j = 0 , range - 1, 1 do
                local cellIndex = this.ConvertGridCoordsToGridIndex(x + j + offest, y + i + offest);
                --print("do check  " ,x + j + offest  , y + i + offest)
                if cellIndex >= 0 and cellIndex < obstructionArray:getCount() and obstructionArray:getValue(cellIndex) then
                    return true;
                end
             end   
        end
        return false;
end

--获得阻挡尺寸,  策划需求 , 阻挡尺寸是 标准尺寸 - 1 
function BoardUtil.GetObstructionSize(areaSize)
    local obstructionSize = areaSize - 1 <= 0 and 1 or areaSize - 1;
    return obstructionSize;
end    

--检测某个点是否是阻挡 , x,y 是tile 坐标
function BoardUtil.CheckIsBuildingObstruction( x , y )

    if x < 0 or y < 0 or x > Board.CellCountX or y > Board.CellCountY then
        return true;
    end    

    local cellIndex = BoardUtil.ConvertGridCoordsToGridIndex(x, y);
--print("DDDD  " , Board.mStructureObstructions:getValue(cellIndex)   , x,y)
    if Board.mStructureObstructions:getValue(cellIndex)  then
        return true;
    end

    if Board.mWallObstructions:getValue(cellIndex)  then
        return true;
    end

    return false;
end

--检测建筑区域是否是阻挡 , x,y 是tile 坐标 , size是建筑标准尺寸
function BoardUtil.CheckIsBuildingAreaObstruction( x , y , size)

    for i = x , x + size - 1 , 1 do
        for j = y , y + size - 1 , 1 do
            if this.CheckIsBuildingObstruction(i , j) then
                return true;
            end    
        end
    end

    return false;    
end    

--根据兵的数量 和最大单位数 求一个均匀分配数量的数组
--例如： 10 个兵最多分3组 返回： 3,3,4
function BoardUtil.GetAverageSoldierList( num , maxGroup)

    if num == nil or num<1 then
        return {0};
    end

    if maxGroup == nil or maxGroup<1 then
        maxGroup = 1;
    end

    local list = {};
    
    if num <= maxGroup then
        for i=1,num do
            table.insert(list , 1);
        end
        return list;
    end

    local averageNum = math.floor(num/maxGroup);

    local leftNum = num%maxGroup;

    for i=1,maxGroup do
        if i==maxGroup then
            table.insert(list , averageNum+leftNum);
        else
            table.insert(list , averageNum);
        end
    end

    return list;

end

----根据建筑位置 计算建筑内驻军的站位列表(默认最大3个兵)
function BoardUtil.GetDefenTroopPostionList(building_x,building_y,building_w,building_h)
    
    local posList = {};

    posList[1] = {x=building_x+0.5, y=building_y};
    
    posList[2] = {x=building_x+2, y=building_y+0.5};
    
    posList[3] = {x=building_x+0.5, y=building_y+2};

    return posList;

end

function BoardUtil.ClearAllObstructions()
    Board.terrainObstructions:clearEmpty(); 

    Board.mWallObstructions:clearEmpty();  
 
    Board.mStructureObstructions:clearEmpty();  
end    