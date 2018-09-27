-----主体ai , 数据 来自 ai表 , 主体 ai 会影响 下面的武器 ai

EntityAI = class(Object)

function EntityAI:ctor(entity)
            self.ClassName = "EntityAI";
	self.entityOwner = entity;
	self.aqNeedTarget = false;
            self.aqTarget = nil;  --主武器的目标
            self.aqGotTarget = false;

 
	self.mTarget = nil;
	self.mCommandStack = List0.new();
            self.currentCommand = nil;

end	

function EntityAI:clearTarget()
    self.aqTarget = nil;
    self.mTarget = nil;
end    

function EntityAI:stepAI(entityTime)
    
    if not self.entityOwner:isPause() then
        local _command = self:getCurrentCommand();
        if _command ~= nil then
    --print(_command.ClassName)
            _command:stepCommand(entityTime);

            local command2 = self:getCurrentCommand();

            if _command == command2 and _command:isFinished() then
                self:popCommand();
            end
        end
    end

    self:cleanUpDeadTarget();
end	

function EntityAI:cleanUpDeadTarget()
    if self.mTarget ~= nil and self.mTarget:isDead() then
         self.mTarget = nil;
    end    
end    

function EntityAI:getNeedsTarget()
        return self.aqNeedTarget;
end

function EntityAI:getCurrentTarget()
        return self.mTarget;
end

function EntityAI:setMarkTarget(tgt)
     --print("::::设置目标111111111::::::  " , tgt , self.entityOwner)
        self.mTarget = tgt;
        self:clearIdentifyCheck();
end

---设置初始command
function EntityAI:setBaseCommand(cmd)
    self:pushCommand(cmd);
end

function EntityAI:pushCommand(newCommand)
            local num = self.mCommandStack:getSize();
            if num > 0 then
                num = num - 1;
                self.mCommandStack[num]:pauseCommand();
            end
            self.mCommandStack:add(newCommand);
            newCommand:startCommand();
      --      print("start ai &&&&&&&&&  " , newCommand.ClassName)
end

function EntityAI:getCurrentCommand()
    if self.mCommandStack:getSize() > 0 then
        return self.mCommandStack[self.mCommandStack:getSize() - 1];
    end
    return nil;
end

function EntityAI:popCommand()
    local num = self.mCommandStack:getSize();
    if num == 0 then
        return;
    end

    num = num - 1;

    self.mCommandStack[num]:onCommandFinished();

    self.mCommandStack:removeAt(num);

    num = num - 1;

    if num >= 0 then
        self.mCommandStack[num]:resumeCommand();
    end
end    


function EntityAI:requestTarget()

    if self.entityOwner.currentBattle ~= nil and self.entityOwner.mainWeapon ~= nil and self.entityOwner.mainWeapon.weapomAI ~= nil then
 
        if self.aqNeedTarget then
            return;
        end

        self.aqTarget = nil;

        self.aqNeedTarget = true;
 
        self.entityOwner.currentBattle:requestTarget(self.entityOwner);
    end
end    

--让entity 上所有的weapon 找目标
function EntityAI:batchAcquireTarget()
 
    if not self.aqNeedTarget then
        return;
    end

    self.aqNeedTarget = false;

    self.aqTarget = self:noticeAllWeaponFindTarget();

    self.aqGotTarget = true;
end

--通知所有weapon 寻找目标
function EntityAI:noticeAllWeaponFindTarget()
    return nil;
end    

function EntityAI:checkTargetSearchResults()
    if self.aqGotTarget then

        self.aqGotTarget = false;

        self.aqNeedTarget = false;

        if self.aqTarget ~= nil then
            self:setMarkTarget(self.aqTarget);
        end    
    end
end    

function EntityAI:clearIdentifyCheck()
    self.aqNeedTarget = false;
    self.aqGotTarget = false;
    self.aqTarget = nil;
end


--当前目标存活 , 且在有效攻击范围内
function EntityAI:targetAliveAndInShortRange()
            local currentTarget = self:getCurrentTarget();
            return currentTarget ~= nil and not currentTarget:isDead() and not self.entityOwner:targetOutOfRange(self.entityOwner:getPosition(), currentTarget);
end