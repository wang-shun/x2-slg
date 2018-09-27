BlockingTroop = class(Object)

function BlockingTroop:ctor(troop , board)
            self.ClassName = "BlockingTroop";
	self.blocker = troop;
	self.startTime = board.simTimestep;

end	

function BlockingTroop:getBlocker()
	return self.blocker;
end

function BlockingTroop:getStartTime()
            return self.startTime;
end

    

function BlockingTroop.IsCollidingWhileMoving(t1, t2, checkDistance)
        return (t1:getPosition() - t2:getPosition()):sqrMagnitude() < checkDistance * checkDistance;
end

function BlockingTroop.IsMovingAway(t1, t2)
        local direction = t1.troopAI:getMovement():getDirection();
        local direction2 = t2.troopAI:getMovement():getDirection();
        if direction == FVector3.Zero or direction2 == FVector3.Zero then
            return true;
        end
        local fVector = t2:getPosition() - t1:getPosition();
        if fVector == FVector3.Zero then
            return true;
        end
        local v = t1:getPosition() - t2:getPosition();
        local flag = FVector3.DotProduct(direction, fVector) <= 0;
        local flag2 = FVector3.DotProduct(direction2, v) <= 0;
        return flag and flag2;
end

function BlockingTroop.CheckDistance(t1)
        local boundingBox = t1.boundingBox;
        local n = boundingBox:getRight() - boundingBox:getLeft() + (boundingBox:getTop() - boundingBox:getBottom());
        return n * 0.5;
end

function BlockingTroop.IsInFront(t1, t2)
        if BlockingTroop.IsMovingAway(t1, t2) then
            return false;
        end

        local direction = t1.troopAI:getMovement():getDirection();
        local direction2 = t2.troopAI:getMovement():getDirection();
        if FVector3.DotProduct(direction, direction2) <= 0 then
            return true;
        end
        local fVector = t2:getPosition() - t1:getPosition();
        local n = FVector3.DotProduct(direction, fVector);
        local n2 = FVector3.DotProduct(-1 * direction2, fVector);
        return n < n2 or (n <= n2);
end