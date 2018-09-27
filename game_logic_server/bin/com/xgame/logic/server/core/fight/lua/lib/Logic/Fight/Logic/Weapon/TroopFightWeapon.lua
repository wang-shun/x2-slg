
----主武器的view 握有 武器架的 gameobject , 自身的朝向 通过武器架来体现 ,  logic 层还是只有主武器的weapon对象

TroopFightWeapon = class(FightWeapon)

 function TroopFightWeapon:ctor(_owner)

 	self.ClassName = "TroopFightWeapon";

 	self._rotationAxis = 1; --0 本身不旋转 ,  1 x  , 2 y , 3 z  配件绕哪个轴旋转

	self.viewPath = ""; -- 该武器配件在兵种预制体里的显示路径

	self.showId = ""; -- 模型id str

	self.maxRotation = 0;--轴向上的旋转角度限制

	self.minRotation = 0;--轴向上的旋转角度限制

	self.cherckRotationAngle = 0;--非轴向上的检测角度限制

	self.facing = 0;--当前世界朝向

	self.parentWeaponList = List.new();--父武器列表

    	self.selfV3 = FVector3.new(0,0,0);

    	self.targetV3 = FVector3.new(0,0,0);

            self.tmpV3 = FVector3.new(0,0,0);

            self.pj = nil;
 end	

 function TroopFightWeapon:getRotationAxis()
        return self._rotationAxis;
end

function TroopFightWeapon:setRotationAxis(v)
        self._rotationAxis = v;
end
 

function TroopFightWeapon:addParent(weapon)
        self.parentWeaponList:add(weapon);
end

function TroopFightWeapon:lostTarget()
        self:setMainTaget(nil);

        if self.skAreaType == 0 then
            self:stopAllAttack();
        end
end

function TroopFightWeapon:_setMainTaget(tgt)
        self.weapomAI:setMarkTarget(tgt);
        self:resetAttack();
end

function TroopFightWeapon:setMainTaget(tgt)
	--print("------------------------------------------setMainTaget")
    if self.weaponGrop:getSize() > 0 then
        for i = 1 , self.weaponGrop:getSize() , 1 do
             local wp = self.weaponGrop[i];
             wp:_setMainTaget(tgt);
        end    
        self:_setMainTaget(tgt);      
    else
        self:_setMainTaget(tgt);      
    end  
end   

function TroopFightWeapon:canFire()
	--print("------------------------------------------canFire")
        if self.weapomAI:getCurrentTarget() == nil then
           -- print("weapon 不能攻击  nil" );
            return false;
        elseif FVector3.Distance(self.owner:getSimPosition() , self:getMainTaget():getSimPosition()) > self.skAttackLen then
          --  print("weapon 不能攻击 " ,self.owner.areaSize    ,self.owner.cellx,self.owner.cellx, self.owner:getSimPosition().x,self.owner:getSimPosition().z,self.owner:getPosition().x,self.owner:getPosition().z, FVector3.Distance(self.owner:getPosition() , self:getMainTaget():getSimPosition()) , self.skAttackLen)
            return false;
        end
        return true;
end

function TroopFightWeapon:updateIdle(deltaTime)
	--处理待机
        if self.idle then
            self.facing = -1;
        end
end	

function TroopFightWeapon:step(deltaTime)
	 
	self.super.step(self,deltaTime);
	self:updateIdle(deltaTime);
end	

function TroopFightWeapon:_weaponFire(tgt , delay)
        if self:canFire() then
            local selfPos = self.owner:getPosition();
            self.super.fire(self,selfPos , tgt , delay);
        end
end    


function TroopFightWeapon:weaponFire(tgt)
 --   print("weapon 攻击" , tgt)
     --//处理攻击

    if self.weaponGrop:getSize() > 0 then
        for i = 1 , self.weaponGrop:getSize() , 1 do
             local wp = self.weaponGrop[i];
             wp:_weaponFire(tgt , i * self.mainWeaponShootInterval / 1000);
        end    
    else
        self:_weaponFire(tgt , 0);      
    end  
end    

function TroopFightWeapon:updatePose(isIdle , facing)
    self.idle = isIdle;

    if not self.idle then
        self.facing = facing;
    end    
end    

--根据角度检测目标是否可攻击 或者 丢失目标
function TroopFightWeapon:checkTargetResult(tgt , checkLen)

        local selfPos = self.owner:getPosition();

        local ctr = CheckTargetResult.new();
		--print("1 TroopFightWeapon:checkTargetResult" , self.isDataReady , self:getRotationAxis())
        if not self.isDataReady then
            ctr.canMark = false;
            return ctr;
        end    

        local fRoateAngle = 0;
		--print("2 TroopFightWeapon:checkTargetResult" , FVector3.Distance(selfPos, tgt:getSimPosition()) , checkLen)
        if FVector3.Distance(selfPos, tgt:getSimPosition()) > checkLen then
            ctr.canMark = false;

            return ctr;
        end

        if self:getRotationAxis() == WeaponRotationAxis.None then
            if tgt ~= nil then
                local posOffect = tgt:getSimPosition() - selfPos;
                
                self.tmpV3.x = posOffect.x;
                self.tmpV3.y = posOffect.y;
                self.tmpV3.z = posOffect.z;

                local qq =  FQuaternion.LookRotation(self.tmpV3);

                if qq ~= nil then
                    fRoateAngle = qq:ToEulerAngles().y;
                end

                ctr.facing = fRoateAngle;

                ctr.canMark = true;
            end   
        else
        
            if tgt ~= nil then
                    local posOffect = tgt:getSimPosition() - selfPos;
                    
                    self.tmpV3.x = posOffect.x;
                    self.tmpV3.y = posOffect.y;
                    self.tmpV3.z = posOffect.z;
 
                    if FQuaternion.LookRotation(self.tmpV3) ~= nil then
                        if self:getRotationAxis() == WeaponRotationAxis.X then
                            fRoateAngle = FQuaternion.LookRotation(self.tmpV3):ToEulerAngles().x;
                        elseif self:getRotationAxis() == WeaponRotationAxis.Y then
                            fRoateAngle = FQuaternion.LookRotation(self.tmpV3):ToEulerAngles().y;
                        elseif self:getRotationAxis() == WeaponRotationAxis.Z then
                             fRoateAngle = FQuaternion.LookRotation(self.tmpV3):ToEulerAngles().z;
                        end
                     else 
                      --print(self.tmpV3)   
                    end
                    ctr.facing = fRoateAngle;

                    ctr.canMark = true;
             end   
 
        end

        return ctr;
end