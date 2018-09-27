TroopAI = class(EntityAI)

function TroopAI:ctor(owner)

	self.ClassName = "TroopAI";

	self.pathRequest = nil;

	self.mHasParkingPlace = false;

	self.parkingType = ParkSize.OneGrid;

	self.mParkingPlace = FVector3.new(0,0,0);

	self.infantryParkPlace2 = false;

	self.hasIntermediateParkingPlace = false;

	self.mInfantryParkingPlace2 = false;

	self.intermediateParkingPlace = FVector3.new(0,0,0);

	self.continueMovement = false;

	self.movement = nil;

	self.mAccelerating = false;

	self.mDecelerating = false;

	self.parkingPlaceDirection = 0;

	self.troopOwner = owner;
end	

function TroopAI:stepAI(entityTime)
 
        local topSuper = getTopSuper(self);
        if topSuper ~= nil then
            topSuper.stepAI(self,entityTime);
        end

        if self:getCurrentCommand() == nil then
            local aICommand = AttackCommand.new(self.troopOwner);
            topSuper.pushCommand(self , aICommand);
        end 
end

function TroopAI:getMovement()
        return self.movement;
end

function TroopAI:hasParkingPlace()
         return self.mHasParkingPlace;
end

function TroopAI:setParkingPlace(v)
        self.mHasParkingPlace = v;
end

function TroopAI:getParkingType()
        return self.parkingType;
end

function TroopAI:setParkingType(v)
        self.parkingType = v;
end

function TroopAI:getParkingPlace()
        return self.mParkingPlace;
end

function TroopAI:getHasIntermediateParkingPlace()
        return self.hasIntermediateParkingPlace;
end

function TroopAI:setHasIntermediateParkingPlace(v)
        self.hasIntermediateParkingPlace = v;
        if not v then
            self.mInfantryParkingPlace2 = false;
        end
end

function TroopAI:getIntermediateParkingPlace()
        return self.intermediateParkingPlace;
end

function TroopAI:setIntermediateParkingPlace(parkingPlace)
        self.intermediateParkingPlace = parkingPlace;
        self.hasIntermediateParkingPlace = true;
end

function TroopAI:setParkingPlacePos(parkingPlace, directionDegrees)
        self.mParkingPlace = parkingPlace;
        self.parkingPlaceDirection = directionDegrees;
        self.mInfantryParkingPlace2 = false;
        self:setParkingPlace(true);
end

function TroopAI:isMoving()
    return self.movement ~= nil and not self.movement:isMovementComplete();
end

--这里要处理 所有主武器 的攻击 , 方法的触发是 机体到达主武器的攻击范围
function TroopAI:fireAtTarget(target)
    self.troopOwner.mainWeapon:weaponFire(target);
end    
 

function TroopAI:noticeAllWeaponFindTarget()
    for i = 1 , self.entityOwner.weaponList:getSize() , 1 do
        if self.entityOwner.weaponList[i].weapomAI ~= nil  and  self.entityOwner.weaponList[i].weapomAI.initiativeFindTarget then
            self.entityOwner.weaponList[i].weapomAI:proccessTarget();
        end    
    end   

    if self.entityOwner.mainWeapon ~= nil then
        return self.entityOwner.mainWeapon.weapomAI:findTarget();
    end    

    return nil;
end   

function TroopAI:setMarkTarget(tgt)
      --  print("::::::::::::::::::::::强制设置目标::::::  " , tgt , self.entityOwner , self.troopOwner:getMainWeapon() )
        self.mTarget = tgt;
        self:clearIdentifyCheck();

        if self.troopOwner:getMainWeapon() ~= nil then
            self.troopOwner:getMainWeapon():setMainTaget(tgt);
            self.troopOwner:forceSetViceWeaponTarget(tgt);
        end    
end

function TroopAI:initMovement(v)
    self.movement = MovementMobile.new();
end    