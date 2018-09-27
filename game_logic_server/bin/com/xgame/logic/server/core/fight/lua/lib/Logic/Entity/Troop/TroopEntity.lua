TroopEntity = class(Entity);


function TroopEntity:ctor()
    self.ClassName = "TroopEntity";

    self.troopAI = nil;

    self.targetingType = TargetingType.Enemies;

    self.initialPosition = FVector3.new(0,0,0);
    
    self.mName = nil;

    self.superType = nil;

end

function TroopEntity:dispose()

    local topSuper = getTopSuper(self);

    if topSuper ~= nil then
        topSuper:dispose();
    end

    for i = 1 , self.weaponList:getSize() , 1 do
        self.weaponList[i]:clear();
    end    

    self.weaponList:clear();
end    

function TroopEntity:createWeapon(tz , pj , isPaoTai) 

    local _weapon = TroopFightWeapon.new(self);

    _weapon.pj = pj;

    local initiativeFindTarget = true;

    if not isPaoTai then
        _weapon.viewPath = tz:getViewPath(pj);			--获取武器路径
       
        _weapon.weaponType = pj:getArmsType();			--获取武器类型

      --  print("FFFFFFFFFweaponTypeFFFFFFFFFFFFFF   " , _weapon.weaponType  , _weapon.viewPath)
    else
        _weapon.viewPath = tz:getBatteryPath();			--获取炮台路径
       
        _weapon.weaponType = ArmorType.notArmor;		--非武器
    end    


   pj:getWeaponPJCtrlInfo(tz , function(weaponPJCtrlConfig)
                --非武器类的配件不处理
            if weaponPJCtrlConfig == nil then
                return nil;
            end
            
            _weapon.maxRotation = weaponPJCtrlConfig.maxRotation;		--轴向上的旋转角度限制
		
            _weapon.minRotation = weaponPJCtrlConfig.minRotation;		--

            _weapon.cherckRotationAngle = weaponPJCtrlConfig.cherckRotationAngle;	--非轴向上的检测角度限制

            if _weapon.weaponType ~= ArmorType.mailArmor then
                    _weapon:setRotationAxis(weaponPJCtrlConfig.rotationAxis);		--非主武器 0 本身不旋转 ,  1 x  , 2 y , 3 z  配件绕哪个轴旋转
            end
            _weapon.isDataReady = true;
        end);



    if _weapon.weaponType == ArmorType.mailArmor then 
        initiativeFindTarget = false;
        _weapon:setRotationAxis(WeaponRotationAxis.None);
    end

    -----------------------alex ----test


    --[[
    local tmpSkillID = 0 ;
    if pj.str_showId == "T1Z_1" or  pj.str_showId == "T1Z_6" then
        tmpSkillID = 0;
    elseif pj.str_showId == "TXgs_1" or pj.str_showId == "TXjq_1" then     
        tmpSkillID = 2;
    elseif pj.str_showId == "TXdd_6" or pj.str_showId == "TXdd_4" or pj.str_showId == "F11Z_6" or pj.str_showId == "TXdd_1" or pj.str_showId == "W6Z_1"  then     
        tmpSkillID = 3;   
    elseif pj.str_showId == "F13Z_1" or pj.str_showId == "W6Z_6" then     
        tmpSkillID = 5;     
    end 
    ]]    

    --[[
    if self.model.soldier:getDesignMap():getSmallSoldierId() == TroopType.DuiDiMianShangZhiShengJi then
         _weapon:setAI(EntityTool.CreateWeaponAI(self , _weapon , 1));
    else
        _weapon:setAI(EntityTool.CreateWeaponAI(self , _weapon , 0));
    end
    ]]
print("XXXXXXXXXXXXXXXXXXXX    " , pj.i_tid , pj.ai)
if pj.skillIdArr  ~= nil then
    print("XXXXXXXXXXX22XXXXXXXXX    " , pj.skillIdArr:getSize())
end    
    _weapon:setAI(EntityTool.CreateWeaponAI(self , _weapon , pj.ai));

    _weapon.weapomAI.initiativeFindTarget = initiativeFindTarget;


    --处理武器配件的技能
    if pj.skillIdArr ~= nil then
        local skillList = List.new();

        for i = 1 , pj.skillIdArr:getSize() , 1 do
            local skill = EntityTool.CreateWeaponSkill(tonumber(pj.skillIdArr[i]));
            skillList:add(skill);
        end 
        _weapon:setSkill(skillList);
    end    
    return _weapon;
end    

--车体是由配件组成, 其中的武器配件在这里初始化 , 以及武器ai
--配件初始化完毕后 , 就要通过 主武器(有时候是副武器 ) 来初始化 兵种的ai
function TroopEntity:initWeapon() 
--print("66666666666666666666666666666666666666666666666666666666" , self.model.weaponTZ.partList:getSize() )
    for i = 1 , self.weaponList:getSize() , 1 do
         self.weaponList[i]:clear();
    end   

    self.weaponList:clear();

    local _mainWeaponPJ = nil;

    local effectiveMainWeaponList = List.new();

    self.troopAssmblyType = self.model.soldier:getDesignMap():getSoliderType();  

    --将每个武器实例化成weapon 对象
     print("将每个武器实例化成weapon 对象" , self.model.soldier:getDesignMap().partList:getSize() )
    for i = 1 , self.model.soldier:getDesignMap().partList:getSize() , 1 do

        local _pj = self.model.soldier:getDesignMap().partList[i];
        
        if _pj:getArmsType() ~= ArmorType.notArmor then --只处理武器类配件

            local _weapon = self:createWeapon(self.model.soldier:getDesignMap() , _pj , false);

            if _weapon ~= nil then

                if _weapon.weaponType == ArmorType.mailArmor then
                       if _mainWeaponPJ == nil then
                            _mainWeaponPJ = _pj;
                       end 

                       effectiveMainWeaponList:add(_weapon);
                end    

                 _weapon.showId = _pj.str_showId;

                self.weaponList:add(_weapon);
            end   
        end     

    end   

    --print("将炮台也实例化成weapon对象, 数据来自主武器    " , _mainWeaponPJ)
    --将炮台也实例化成weapon对象, 数据来自主武器
    self.mainWeapon = self:createWeapon(self.model.soldier:getDesignMap() , _mainWeaponPJ , true);
print("主武器的攻击距离 : " , self.mainWeapon:getAttackLen())
    self.mainWeapon.showId = self.model.soldier:getDesignMap():getBatteryShowId();

    self.mainWeapon.weaponGrop = effectiveMainWeaponList;

    self.weaponList:add(self.mainWeapon);
end    

--[Comment]
--将炮台也实例化成weapon对象, 数据来自主武器
function TroopEntity:getMainWeapon()
    return self.mainWeapon;
end    

function TroopEntity:init(id,team,entityType,cellx,celly,areaSize,obstructionSize,model)
        local topSuper = getTopSuper(self);

        if topSuper ~= nil then
            topSuper.init(self , id,team,entityType,cellx,celly,areaSize,obstructionSize,model);
        end   

        self.initialPosition = FVector3.new(cellx , 0 , celly);

        local facing = FixedAngle.AngleInDegrees(FVector3.Zero - self.initialPosition);
        self.facingInDegrees = facing;
end

function TroopEntity:canShootOverWallsAlways()
        return self.mainWeapon.weapomAI.aiModel:getCanShootOverWallsAlways();
end

function TroopEntity:batchUpdateWeaponAI(timeDelta)
    local topSuper = getTopSuper(self);

    if topSuper ~= nil then
        topSuper.batchUpdateWeaponAI(self);
    end

     
end    

function TroopEntity:ClearAllWeaponBit()
    local weaponLen = self.weaponList:getSize();

    for i = 1 , weaponLen , 1 do
        self.weaponList[i]:clearAllBit();
    end  
end  
 
function TroopEntity:visitStep(timeDelta)
        local topSuper = getTopSuper(self);
        if topSuper ~= nil then
            topSuper.visitStep(self,timeDelta);
        end

        --self:updateMovement();
end

function TroopEntity:updateMovement()
        if not self:isPause() then
            if self.troopAI:getMovement() ~= nil then
                self.troopAI:getMovement():updateMovement(self);
            end
        end
end

function TroopEntity:isFlying()
        return self.mainWeapon.weapomAI.aiModel:getFly();
end

function TroopEntity:canShootWalls()
      --return this.team == EntityTeam.Attacker && this.targetingType != TargetingType.Allies;
        return self.mainWeapon.weapomAI.aiModel:getCanShootWall();
end

function TroopEntity:canRunOverWalls()
        return self.mainWeapon.weapomAI.aiModel:getCanRunOverWalls();
end

 

function TroopEntity:getTwoPerGrid()
            return self.troopAI:getParkingType() == ParkSize.HalfGrid;
end


function TroopEntity:getMoveSpeed()
        return self.troopAI:getMovement():getMoveSpeed();
end

function TroopEntity:getPatrolSpeed()
        return self.troopAI:getMovement():getPatrolSpeed();
end

function TroopEntity:getTurnSpeed()
        return self.troopAI:getMovement():getTurnSpeed();
end

function TroopEntity:getAccelTime()
        return self.troopAI:getMovement():getAccelTime();
end

function TroopEntity:getDecelTime()
        return self.troopAI:getMovement():getDecelTime();
end
 
function TroopEntity:getCanShootOverWallsAlways()
    return self.mainWeapon.weapomAI.aiModel:getCanShootOverWallsAlways();
end

--给副武器插入目标  
function TroopEntity:forceSetViceWeaponTarget(tgt)
    if  self.weaponList ~= nil then
        for i = 1 ,  self.weaponList:getSize() , 1 do
            local weapon = self.weaponList[i];
            if weapon.weaponType == ArmorType.viceArmor then
                weapon.weapomAI:setMarkTarget(tgt);
            end    
        end 
    end   
end

