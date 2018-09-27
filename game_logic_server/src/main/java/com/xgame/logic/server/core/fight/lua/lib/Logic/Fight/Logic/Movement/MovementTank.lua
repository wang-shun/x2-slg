MovementTank = class(MovementMobile)

function MovementTank:ctor()
	warn("MovementTank:ctor")
	self.ClassName = "MovementTank";
end	

function MovementTank:init()
	self.super.init(self); 
end	

function MovementTank:updateMovement(owner)

        if self:isMovementComplete() then
            return;
        end
        --[[
        for i = 0 , self.blockers:getSize() - 1, 1 do 
            local blockingTroop = self.blockers[i];
            local flag = false;
            if blockingTroop.getStartTime() + Movement.MAX_BLOCKER_TIMESTEPS < owner.currentBattle.simTimestep then
                flag = true;
            end
            if not BlockingTroop.IsCollidingWhileMoving(owner, blockingTroop.getBlocker(), BlockingTroop.CheckDistance(owner)) then
                flag = true;
            end

            if flag then
                self.blockers:removeAt(i);
                i = i - 1;
            end
        end
        if self.blockers:getSize() > 0 then
            self.mPathFollower:setHalfSpeed(true);
        else
            self.mPathFollower:setHalfSpeed(false);
        end
        ]]
        self.mPathFollower:followPathTime(BattleConst.SIM_STEP_TIME , owner.id);
end