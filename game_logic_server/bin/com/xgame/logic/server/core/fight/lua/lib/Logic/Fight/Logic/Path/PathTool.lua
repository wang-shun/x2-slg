PathTool = {};

local this = PathTool;

function PathTool.Park2Offset(park)
        local f = - 1;
        if park == ParkSize.FourGrid2x2 then
            local result = f * Board.CellHalfSize * 12;
            return result;
        end

        if park == ParkSize.TwoGrids then
            local result = f *  Board.CellHalfSize * 4;
            return result;
        end

        if park == ParkSize.OneGrid then
            local result = f *  Board.CellHalfSize * 2;
      --      print("------------------------------------------  " , result)
            return result;
        end

        return FVector3.new(0,0,0);
end

function PathTool.P2PerOffset1()
        return Board.TwoPerOffset;
end

function PathTool.P2PerOffset2()
        return -Board.TwoPerOffset;
end