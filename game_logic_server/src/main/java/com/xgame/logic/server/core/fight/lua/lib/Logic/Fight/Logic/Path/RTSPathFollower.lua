RTSPathFollower = class(Object)

function RTSPathFollower:ctor() 
            self.ClassName = "RTSPathFollower"
            self.mCurSpeed = MathConst.Four;
            self.pathFollower = nil;
            self.mMode = MoveModes.Uninitialized;
            self.direction = FVector3.new(0,0,0);
            self.halfSpeed = false;
            self.mTroop = nil;
            self.mCurTargetPos = nil;
            self.mDoingDeceleration = false;
            self.mCurStepStart = nil;
            self.mCurTime = MathConst.Zero;
            self.mLockFacing = nil;
            self.LEAD_DISTANCE = MathConst.One;
            self.mInitialAngle = nil;
            self.mTargetAngle = nil;
            self.mSleepTime = MathConst.Zero;
            self.mFarAngle = nil;
            self.mOrientTime = 0;
            self.NINETY_DEGREES = MathConst.Nine * 10;
            self.mFarOrientTime = nil;
            self.mAccelTime = MathConst.OneHalf;
            self.mAccelRate = MathConst.Ten;
            self.mDecelTime = MathConst.OneHalf;
            self.mDecelRate = MathConst.Ten;
            self.mStartDecelPos = nil;

            self.pathFollower = GridPathFollower.new();
end

function RTSPathFollower:getSpeed()
        return self.mCurSpeed;
end

function RTSPathFollower:getPath()
        return self.pathFollower.mPath;
end

function RTSPathFollower:getDirection()
        return self.direction;
end

function RTSPathFollower:getHalfSpeed()
        return this.halfSpeed;
end

function RTSPathFollower:setHalfSpeed(doHalf)
        if self.halfSpeed == doHalf then
            return;
        end
        self.halfSpeed = doHalf;
        if not self.halfSpeed and self.mMode == MoveModes.Cruise then
            self.mMode = MoveModes.Accelerate;
        end
        local n = self:moveSpeed();
        if self.mCurSpeed > n then
            self.mCurSpeed = n;
        end
end

function RTSPathFollower:moveSpeed()
        local fSpeed = (self.mTroop:getAI():getCurrentTarget() ~= nil) and self.mTroop:getMoveSpeed() or self.mTroop:getPatrolSpeed();
        if self.halfSpeed then
            fSpeed = fSpeed * 0.5;
        end
        return fSpeed;
end

function RTSPathFollower:updatePath(newPath)
        self.pathFollower:update(newPath, self.mTroop);
        self.mCurTargetPos = self.pathFollower:getPosition();
        if self.mMode == MoveModes.Cruise or self.mMode == MoveModes.Decelerate then
            self.mDoingDeceleration = false;
            self.mMode = MoveModes.Accelerate;
            self.mCurStepStart = self.mCurTime;
        end
end

function RTSPathFollower:endPath(troop)
        self.pathFollower:initialize(nil, troop);
end

function RTSPathFollower:isPathingComplete()
   -- print("self.mMode  " , self.mMode)
        return self.mMode == MoveModes.Finished or self.mMode == MoveModes.Uninitialized;
end

function RTSPathFollower:initRTSPathFollower(path , troop)
--print("-----  initRTSPathFollower1" , path)
        if path == nil then
            self.mMode = MoveModes.Sleep;
            return;
        end
--print("-----  initRTSPathFollower2" , troop)
        if troop == nil then
                self.mMode = MoveModes.Sleep;
                return;
        end
--print("-----  initRTSPathFollower3" , path:getNumWaypoints())
        if path:getNumWaypoints() < 1 then
            this.mMode = MoveModes.Sleep;
            return;
        end
        
        self.mLockFacing = false;
        self.mTroop = troop;
        self.mDoingDeceleration = false;
        self.LEAD_DISTANCE = troop:getMoveSpeed() / 2;
        self.mCurSpeed = MathConst.Zero;
        self.mMode = MoveModes.Orientate;
        self.pathFollower:followPath(self.LEAD_DISTANCE);
        self.mCurTargetPos = self.pathFollower:getPosition();
        self.mDoingDeceleration = false;
        local position = self.mTroop:getPosition();
        local facing = (self.mCurTargetPos - position):normalize();
        self.mInitialAngle = self.mTroop.facingInDegrees;
        self.mTargetAngle = FixedAngle.AngleInDegrees(facing);
       
        local fixedPointNumber = FixedAngle.NormalizeAngle(self.mTargetAngle - self.mInitialAngle);
        self.mTargetAngle = self.mInitialAngle + fixedPointNumber;
        --  print("XXXXXXXXXXXXXXXXXXXXX2  " , fixedPointNumber  ,  self.mTargetAngle , self.mInitialAngle )
        self.mSleepTime = MathConst.Zero;
        local waypoint = path:getWaypoint(path:getNumWaypoints());
 
        if (waypoint - position):magnitude() < (MathConst.ThreeQuarters * Board.CELL_WIDTH) then
            fixedPointNumber = MathConst.Zero;
            self.mTargetAngle = self.mInitialAngle;
            self.mMode = MoveModes.Skate;
        else
            self.mFarAngle = self.mInitialAngle;
            self.mCurTime = MathConst.Zero;
            self.mCurStepStart = MathConst.Zero;
            self.mOrientTime = Mathf.Abs(fixedPointNumber / troop:getTurnSpeed());
      --    print("XXXXXXXXXXXXXXXXXXXXX3  " , fixedPointNumber , self.NINETY_DEGREES , troop:getTurnSpeed(), self.mOrientTime)
            if Mathf.Abs(fixedPointNumber) > self.NINETY_DEGREES then
                self.mMode = MoveModes.FarOrientate;
                self.mFarOrientTime = self.mOrientTime / 2;
                self.mOrientTime = self.mOrientTime - self.mFarOrientTime;
                self.mFarAngle = self.mTargetAngle - fixedPointNumber / 2;
            else
                self.mMode = MoveModes.Orientate;
            end



        --   print("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC   " ,  self.mMode)
        end
end

function RTSPathFollower:maxMovingTurnDegrees()
            return MathConst.One * 120;
end

function RTSPathFollower:startPath(path , troop)

       -- print("--------------startPath")
        self.pathFollower:initialize(path, troop);
        self:initRTSPathFollower(path, troop);
        self.mAccelTime = self.mTroop:getAccelTime();
        self.mAccelRate = ((not (self.mAccelTime > 0)) and 20 or (self:moveSpeed() / self.mAccelTime));
        self.mDecelTime = self.mTroop:getDecelTime();
        self.mDecelRate = ((not (self.mDecelTime > 0)) and 20 or (self:moveSpeed() / self.mDecelTime));
end

function RTSPathFollower:continuePath(path , troop)

        local flag = true;
        if self:isPathingComplete() or self.pathFollower.mPath == nil or self.mTroop == nil then
            flag = false;
        elseif self.mCurSpeed < self:moveSpeed() / 10 then
            flag = false;
        end

        if not flag then
            self:startPath(path , troop);
            return;
        end

        self.mDoingDeceleration = false;
        self.mMode = MoveModes.Accelerate;
        self.mLockFacing = false;
        self.mTroop = troop;
        self.LEAD_DISTANCE = troop:getMoveSpeed() / 2;
        self.pathFollower:initialize(path, troop);
        self.pathFollower:followPath(self.LEAD_DISTANCE);
        self.mCurTargetPos = self.pathFollower:getPosition();
        self.mDoingDeceleration = false;
        local position = self.mTroop:getPosition();
        local facing = (self.mCurTargetPos - position):normalize();
        self.mInitialAngle = self.mTroop.facingInDegrees;
        self.mTargetAngle = FixedAngle.AngleInDegrees(facing);
        local number = FixedAngle.NormalizeAngle(self.mTargetAngle - self.mInitialAngle);
        if Mathf.Abs(number) > self:maxMovingTurnDegrees() then
            self:startPath(path, troop);
            return;
        end

        self.mCurTime = MathConst.Zero;
        self.mCurStepStart = MathConst.Zero;
        self.mSleepTime = MathConst.Zero;
end

function RTSPathFollower:doStrafe(deltaTime)
        self.mMode = MoveModes.Finished;
end

function RTSPathFollower:doStop()
        self.mMode = MoveModes.Finished;
end

function RTSPathFollower:updateFacing(newFacingVec)
       
        if self.mLockFacing then
            return true;
        end

        local newFacing = FixedAngle.AngleInDegrees(newFacingVec);

       --  print("updateFAceing-------   " , newFacing)

        return self:updateFacingNumber(newFacing);

end

function RTSPathFollower:updateFacingNumber(newFacing)
        if self.mLockFacing then
            return true;
        end
 
        local result = true;
        local fixedPointNumber = FixedAngle.NormalizeAngle(newFacing - self.mTroop.facingInDegrees);
 
        if fixedPointNumber < 0 then
            fixedPointNumber =  -fixedPointNumber;
        end

        local fifty = MathConst.Fifty;

        if fixedPointNumber > fifty then
        
            result = false;
        end
     -- print("$$$$$$$$$$$$   " , newFacing , FixedAngle.FacingForAngleInDegrees(newFacing))
        self.mTroop.facingInDegrees = newFacing;
        return result;
end

function RTSPathFollower:doFarOrientate(deltaTime)
     --   print("-----------------doFarOrientate ");
        local fixedPointNumber = MathConst.One;
        if self.mFarOrientTime > MathConst.Zero then
            fixedPointNumber = (self.mCurTime - self.mCurStepStart) / self.mFarOrientTime;
        end
        if fixedPointNumber >= MathConst.One then
            self:updateFacing(FixedAngle.FacingForAngleInDegrees(self.mFarAngle));
            self.mInitialAngle = self.mFarAngle;
            self.mMode = MoveModes.Orientate;
            self.mCurStepStart = self.mFarOrientTime;
            return;
        end
        
        local lerp = self.mInitialAngle * (MathConst.One - fixedPointNumber) + self.mFarAngle * fixedPointNumber;
        self:updateFacing(FixedAngle.FacingForAngleInDegrees(lerp));
end

function RTSPathFollower:doOrientate(deltaTime)
        --print("-----------------doOrientate");
        local fixedPointNumber = MathConst.One;
        if self.mOrientTime > MathConst.Zero then
            fixedPointNumber = (self.mCurTime - self.mCurStepStart) / self.mOrientTime;
        end

        if fixedPointNumber >= MathConst.One then
            self:updateFacing(FixedAngle.FacingForAngleInDegrees(self.mTargetAngle));
            self.mMode = MoveModes.Accelerate;
            self.mCurStepStart = self.mCurStepStart + self.mOrientTime;
            return;
        end

        local lerp = self.mInitialAngle * (MathConst.One - fixedPointNumber) + self.mTargetAngle * fixedPointNumber;
        self:updateFacing(FixedAngle.FacingForAngleInDegrees(lerp));
        local n = MathConst.Six / 10;
        if fixedPointNumber >= n then
            self.mMode = MoveModes.Accelerate;
            self.mCurStepStart = self.mCurStepStart + self.mOrientTime;
        end
end

function RTSPathFollower:followPathTime(deltaTime , id)
   --print("HHH " ,  self.mMode , deltaTime)
        if self.mMode == MoveModes.Finished then
                self.mCurSpeed = MathConst.Zero;
                self.direction = FVector3.Zero;
                return;
       end

        if self.mMode == MoveModes.Uninitialized then
            return;
        end
 
        self.mCurTime = self.mCurTime  + deltaTime;
        if self.mMode == MoveModes.Accelerate then
            self:doAccelerate(deltaTime);
        elseif self.mMode == MoveModes.Cruise then
            self:doCruise(deltaTime);
            if self.mCurSpeed < self:moveSpeed() then
                self.mMode = MoveModes.Accelerate;
            end

            if self.mCurSpeed > (self:moveSpeed()) then
                self.mCurSpeed = self:moveSpeed();
            end

            if self.mMode == MoveModes.Decelerate then
               self:doDecelerate(deltaTime);
            end
        elseif self.mMode == MoveModes.Orientate then
            self:doOrientate(deltaTime);
        elseif self.mMode == MoveModes.FarOrientate then
            self:doFarOrientate(deltaTime);
        elseif self.mMode == MoveModes.Strafe then
            self:doStrafe(deltaTime);
        elseif self.mMode == MoveModes.Decelerate then
            self:doDecelerate(deltaTime);
        elseif self.mMode == MoveModes.Skate then
            self:doSkate(deltaTime);
        elseif self.mMode == MoveModes.Sleep then
            self.direction = FVector3.new(0,0,0);
            self.mCurSpeed = MathConst.Zero;
            self.mTroop.troopAI.mAccelerating = false;
            self.mTroop.troopAI.mDecelerating = false;
            self.mSleepTime =  self.mSleepTime  - deltaTime;
            if self.mSleepTime < MathConst.Zero then
                self.mMode = MoveModes.Finished;
            end
        else
            self.mCurSpeed = MathConst.Zero;
        end

         
end

function RTSPathFollower:doAccelerate(deltaTime)
       -- print("-----------------doAccelerate");
        self.mTroop.troopAI.mAccelerating = true;
        self.mTroop.troopAI.mDecelerating = false;

        self.mCurSpeed = self.mCurSpeed + self.mAccelRate * deltaTime;
        local flag = self:adjFacing();
        if flag and self.mCurSpeed > (self:moveSpeed() / 2) then
            self.mCurSpeed = self:moveSpeed() / 2;
            self.mTroop.troopAI.mAccelerating = false;
        end

        if self.mCurSpeed >= self:moveSpeed() then
            self.mCurSpeed = self:moveSpeed();
            self.mMode = MoveModes.Cruise;
        end

        local fixedPointNumber = deltaTime * self.mCurSpeed;
        if self.pathFollower:isEndOfPath() or self.pathFollower.mPath == nil then
            if self.mCurSpeed < (self:moveSpeed() / 2) then
                self.mCurSpeed = self:moveSpeed() / 2;
            end
            self.mMode = MoveModes.Decelerate;
            self.mCurStepStart = self.mCurTime;
        else
            self.pathFollower:followPath(fixedPointNumber);
        end
        self.mCurTargetPos = self.pathFollower:getPosition();
        local position = self.mTroop:getPosition();
        local fVector = self.mCurTargetPos - position;
        local n = fVector:magnitude();
        if self.pathFollower:isEndOfPath() and (n - fixedPointNumber) < MathConst.OneHundredth then
            self.mTroop:updatePositionAndBoundingBox(self.mCurTargetPos);
            self.mLockFacing = true;
            self.mMode = MoveModes.Sleep;
            return;
        end
        local v = nil;
        if flag then
            v = FixedAngle.FacingForAngleInDegrees(self.mTroop.facingInDegrees);
        else
            v = fVector:normalize();
        end

        self.direction = v;
        self.mTroop:updatePositionAndBoundingBox(position + v * fixedPointNumber);
end

function RTSPathFollower:doSkate(deltaTime)
        --print("-----------------doSkate");
        self.mCurTargetPos = self.pathFollower:getPosition();
        local position = self.mTroop:getPosition();
        local fVector = self.mCurTargetPos - position;
        local fixedPointNumber = deltaTime * self:moveSpeed() / 4;
        local n = fVector:magnitude();
        if (n - fixedPointNumber) < MathConst.OneHundredth then
            self.mTroop:updatePositionAndBoundingBox(self.mCurTargetPos);
            self.mMode = MoveModes.Sleep;
            self.mLockFacing = true;
            return;
        end

        local v = fVector:normalize();
        if self.mTroop:isMustFaceToFire() then
            self:adjFacing();
        end
        self.direction = v;
        self.mTroop:updatePositionAndBoundingBox(position + v * fixedPointNumber);
end

function RTSPathFollower:doDecelerate(deltaTime)
    --    print("-----------------doDecelerate");
        self.mTroop.troopAI.mAccelerating = false;
        self:adjFacing();
        if not self.mDoingDeceleration then
            self.mCurTargetPos = self.pathFollower:getPosition();
            local position = self.mTroop:getPosition();
            local fVector = self.mCurTargetPos - position;
            local n = self.mCurSpeed * self.mDecelTime;
            n = n / 4;
            local fixedPointNumber = deltaTime * self.mCurSpeed;
            local n2 = fVector:magnitude();
            if n2 > (n + fixedPointNumber) then
                local v = fVector:normalize();
                self.mTroop:updatePositionAndBoundingBox(position + v * fixedPointNumber);
                self.mTroop.troopAI.mDecelerating = (n2 <= n + fixedPointNumber);
                return;
            end
            self.mDoingDeceleration = true;
            self.mCurStepStart = self.mCurTime - deltaTime;
            self.mStartDecelPos = self.mTroop:getPosition();
        end

        self.mTroop.troopAI.mDecelerating = true;
        local fixedPointNumber2 = MathConst.One;
        if self.mDecelTime > MathConst.Zero then
            fixedPointNumber2 = (self.mCurTime - self.mCurStepStart)  / self.mDecelTime;
        end

        if fixedPointNumber2  >= MathConst.One then
            self.mMode = MoveModes.Sleep;
            self.mCurStepStart = self.mCurTime;
            self.mTroop:updatePositionAndBoundingBox(self.mCurTargetPos);
            self.mLockFacing = true;
            return;
        end

        fixedPointNumber2 = Mathf.Sqrt(fixedPointNumber2);
        local FVector3 = FVector3.Lerp(self.mStartDecelPos, self.mCurTargetPos, fixedPointNumber2);
        self.mCurSpeed = (FVector3 - self.mTroop:getPosition()):magnitude() / Board.SIM_STEP_TIME();
        self.direction = FVector3 - self.mTroop:getPosition();
        self.mTroop:updatePositionAndBoundingBox(FVector3);
end

function RTSPathFollower:doCruise(deltaTime)
    --    print("-----------------doCruise");
        self.mTroop.troopAI.mAccelerating = false;
        self.mTroop.troopAI.mDecelerating = false;
        local flag = self:adjFacing();
        if flag then
            self.mMode = MoveModes.Accelerate;
        
            self:doAccelerate(deltaTime);
            return;
        end
--print("sssssssssssssssssssss" , self.mCurSpeed)
        local fixedPointNumber = deltaTime * self.mCurSpeed;
        if self.pathFollower:isEndOfPath() or self.pathFollower.mPath == nil then
            self.mMode = MoveModes.Decelerate;
            self.mCurStepStart = self.mCurTime;
            return;
        end

        self.pathFollower:followPath(fixedPointNumber);
        self.mCurTargetPos = self.pathFollower:getPosition();
        local position = self.mTroop:getPosition();
        local fVector = self.mCurTargetPos - position;
        if fVector:magnitude() < (Board.CELL_WIDTH) then
            self.pathFollower:followPath(Board.CELL_WIDTH / 4);
        end

        local n = fVector:magnitude();
        if (n - fixedPointNumber) < MathConst.OneHundredth and self.pathFollower:isEndOfPath() then
            self.mTroop:updatePositionAndBoundingBox(self.mCurTargetPos);
            self.mMode = MoveModes.Sleep;
            self.mLockFacing = true;
            return;
        end

        if fVector:magnitude() < MathConst.OneHundredth then
            return;
        end

        local v = fVector:normalize();
        self:adjFacing();
        self.direction = v;
        self.mTroop:updatePositionAndBoundingBox(position + v * fixedPointNumber);
end

function RTSPathFollower:adjFacing()
        local mRequiredRotation = self:requiredRotation();
        local mMaxRotation = self:maxRotation();
        local result = false;
     --   print("mRequiredRotation = " , mRequiredRotation , "   mMaxRotation = " , mMaxRotation , " self.mTroop.facingInDegrees=" , self.mTroop.facingInDegrees )
        if mRequiredRotation < 0 then
            if mRequiredRotation < -mMaxRotation then
                mRequiredRotation = mRequiredRotation - mMaxRotation;
                result = true;
            end
        elseif mRequiredRotation > mMaxRotation then
            mRequiredRotation = mMaxRotation;
            result = true;
        end

        self:updateFacingNumber(self.mTroop.facingInDegrees + mRequiredRotation);

        return result;
end

function RTSPathFollower:requiredRotation()
        local facing = self.mCurTargetPos - self.mTroop:getPosition();
        local n = FixedAngle.AngleInDegrees(facing);
        if facing:magnitude() < (Board.CELL_WIDTH / 2) then
            n = self.mTroop.facingInDegrees;
        end
        return FixedAngle.NormalizeAngle(n - self.mTroop.facingInDegrees);
end

function RTSPathFollower:maxRotation()
        local sIM_STEP_TIME = BattleConst.SIM_STEP_TIME;
        return sIM_STEP_TIME * self.mTroop:getTurnSpeed();
end

function RTSPathFollower:projectPosition(distance)
    local fVector = FVector3.new(0,0,0);
    if self.mTroop == nil then
        return fVector;
    end
    fVector = self.mTroop.getPosition();
    if self.pathFollower == nil or self.pathFollower.mPath == nil then
        return fVector;
    end
    local fixedPointNumber = distance;
    local num = self.pathFollower:getCurrentWaypoint();
    if num > self.pathFollower.mPath.getNumWaypoints() then
        num = self.pathFollower.mPath.getNumWaypoints();
    end

    while fixedPointNumber > 0 and num <= self.pathFollower.mPath:getNumWaypoints() do
        local waypoint = self.pathFollower.mPath:getWaypoint(num);
        local v = waypoint - fVector;
        local fixedPointNumber2 = v:magnitude();
        if fixedPointNumber2 > 0 then
            if fixedPointNumber >= fixedPointNumber2 then
                fVector = waypoint;
                fixedPointNumber = fixedPointNumber - fixedPointNumber2;
                num = num + 1;
            else
                v:normalize();
                fVector = fVector + v * fixedPointNumber;
                fixedPointNumber = 0;
            end
        else
            num = num + 1;
        end
    end
    return fVector;
end
 

















 





