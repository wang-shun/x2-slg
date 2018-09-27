----武器基础类 , 主武器 和 副武器(离体的有自主行为的 在special 目录下 , 步兵, 无人机)

FightWeapon = class(Object)

function FightWeapon:ctor(_owner)
    self.ClassName = "FightWeapon";

    --self.curSkill = nil;--当前的技能

    self.owner = _owner; --持有武器的 entity 

    self.skAttackLen = 0;

    self.skShootStartDuration = 0;

    self.mainWeaponShootInterval = 0;

    self.weaponTime = 0;-- //武器时钟

    self.isDestroy = false;

    self.weapomAI = nil; -- 武器自己的ai , 主武器的ai model 参数 等于 炮台的,  等于兵种的  ;  某些情况下副武器 会 担当这个角色,  通过替换 aimodel 来实现

    self.idle = true;		--可以理解为待机。

    self.weaponGrop = List.new(); --附属武器组 , 用于主武器 , 因为兵种的mainWeapon 是炮台 , 而mainWeapon 攻击时 需要通知所有的主武器  

    self.weaponType = ArmorType.notArmor; --1 主武器 , 2 副武器 , 3 非武器

    --self.singleTgt = nil;

    self.gameplayBitfield = 0;

    self.isDataReady = false;

    self.skillShooterList = List.new();

end	

function FightWeapon:getMainTaget()
        return self.weapomAI:getCurrentTarget();
end

function FightWeapon:clearTarget()
    --self.singleTgt = nil;
    self.weapomAI:clearTarget();
end    

function FightWeapon:clearAIFocusList()
    self.weapomAI:clearFocusList();
end    

function FightWeapon:setAI(ai)
     self.weapomAI = ai;
    -- print("FightWeapon:setAI.................")
end    

function FightWeapon:setSkill(_skillList)
    if _skillList ~= nil then
        for i = 1 , _skillList:getSize() , 1 do

            if i == 1 then
                _skillList[i].isRoot = true; --将list里的第一个技能 设置成root 技能
                self.skAttackLen = _skillList[i].attackLen; --取第一个技能的攻击距离
                print(">>>>>>-取第一个技能的攻击距离>>>>>>>>>>>   " ,  _skillList[i].shootStartDuration , _skillList[i].attackLen)
                self.skShootStartDuration = _skillList[i].shootStartDuration;
                self.mainWeaponShootInterval = _skillList[i].mainWeaponShootInterval;
            end  

             if self.weaponType ~= ArmorType.notArmor then
                local skillShooter = SkillShooter.new(_skillList[i] , self.owner , self);
                self.skillShooterList:add(skillShooter);
            end    
        end   
    end    

    --战斗状态下, 才处理出生即释放的技能
    if self.owner.currentBattle ~= nil and self.owner.currentBattle.battleStarted then
        self:proccessBronSkill();
    end     


 
        --self.curSkill = _skill;
        --self.skMaxDamageTimeEveryAttack = _skill.attackNum;
        --self.skReloadTime = _skill.reloadTime;
       --self.skAttackRate = _skill.attackRate;
        --self.skAreaType = _skill.areaType;

    

end

--处理出生即释放的技能
function FightWeapon:proccessBronSkill()
    for i = 1 , self.skillShooterList:getSize() , 1 do  

        local shooter = self.skillShooterList[i];

        shooter:proccessBronSkill();
    end
end

function FightWeapon:getAttackLen()
    return self.skAttackLen;
end

function FightWeapon:stopAllAttack()
    for i = 1 , self.skillShooterList:getSize() , 1 do  

        local shooter = self.skillShooterList[i];

        shooter:forceStopProjectile();
    end    
end

function FightWeapon:canFire()
        return false;
end
 
function FightWeapon:fire(selfPos , tgt , delay)
	--print("fightWeapon 开火")
    if self.weaponType == ArmorType.notArmor then
        return;
    end  

    --self.singleTgt = tgt;
--print("self.skillShooterList:getSize() = " , self.skillShooterList:getSize())
    for i = 1 , self.skillShooterList:getSize() , 1 do  

        local shooter = self.skillShooterList[i];

        shooter:fire(selfPos , tgt , delay);
    end  
end

--获得当前的单个目标
function  FightWeapon:getSingleTgt()
    --[[
    if self.singleTgt ~= nil then
        if self.singleTgt:isDead() then
            self.singleTgt = nil;
        end    
    end  

    return self.singleTgt;
    ]]

    return self:getMainTaget();
end


--[[
function FightWeapon:fireMultiple(selfPos , tgtArr)
    if self:canMakeNewShoot() then
        self.singleTgt = nil;
        --print("-----------逻辑 仙法.火龙连弹" , tgtArr)
        self.curDamageTimeEveryAttack = self.curDamageTimeEveryAttack + 1;

        for i = 1 , tgtArr:getSize() , 1 do
                local projectile = Projectile.new();
                projectile:fire(selfPos, self.curSkill, self.owner, tgtArr[i], self , 0);
                self.projectileList:add(projectile);
               
        end    

        self:setNextShootTime();
    end  
end
]]

function FightWeapon:getAllSkillShooter()
    return self.skillShooterList;
end    


--[[
function FightWeapon:getLastProjectile()
    --print(" self.projectileList:getSize()=" ,  self.projectileList:getSize())
        if self.projectileList:getSize() > 0 then
            return self.projectileList[self.projectileList:getSize()];
        end

        return nil;
end
]]

--[[
function FightWeapon:weaponFireMultiple(tgtArr)
    --print("weapon 攻击")
     --//处理攻击

end
]]  

function FightWeapon:weaponFire(tgt)
    --print("weapon 攻击")
     --//处理攻击

end    

function FightWeapon:clearAllBit()
    for i = 1 , self.skillShooterList:getSize() , 1 do  

        local shooter = self.skillShooterList[i];

        shooter:clearAllBit();
    end  

    self:ClearWPBit(WeaponConst.BIT_CD_OVER);

    self:ClearWPBit(WeaponConst.BIT_CD_START);
end

function FightWeapon:stepAI(deltaTime)

    -- self.weaponTime = self.weaponTime + deltaTime;

     self.weapomAI:stepAI(self.weaponTime);
end    

function FightWeapon:step(deltaTime)
        self.weaponTime = self.weaponTime + deltaTime;
      --  print(" self.weaponTime " ,  self.weaponTime , deltaTime)
        for i = 1 , self.skillShooterList:getSize() , 1 do  

            local shooter = self.skillShooterList[i];

            shooter:step(deltaTime);
        end
end

function FightWeapon:getWeaponTime()
        return self.weaponTime;
end

--重置所有战斗参数
function FightWeapon:resetAttack()
--    print("--重置所有战斗参数")
    for i = 1 , self.skillShooterList:getSize() , 1 do  

        local shooter = self.skillShooterList[i];

        shooter:resetAttack();
    end
end

function FightWeapon:clear()
    self.isDestroy = true;

    for i = 1 , self.skillShooterList:getSize() , 1 do  

        local shooter = self.skillShooterList[i];

        shooter:clear();
    end
end

function FightWeapon:SetBit(bit)
        self.gameplayBitfield = Bit._or( self.gameplayBitfield , Bit._lshift(1,bit));
end

function FightWeapon:IsBitSet(bit)
        return   Bit._and(self.gameplayBitfield , Bit._lshift(1,bit)) ~= 0;      
end

function FightWeapon:ClearWPBit(bit)
        self.gameplayBitfield  = Bit._and(self.gameplayBitfield ,  Bit._not(Bit._lshift(1,bit)));
end

function FightWeapon:checkTargetResult(tgt , checkLen)
     local ctr = CheckTargetResult.new();
     ctr.canMark = true;
     return ctr;
end 
