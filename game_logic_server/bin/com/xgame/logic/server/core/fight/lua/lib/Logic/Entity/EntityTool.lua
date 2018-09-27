EntityTool = {}

local this = EntityTool

function EntityTool.CreateNewTroop(troopType)
        local troop = nil;
 
        --创建具体的兵种
        --function Entity:init(id,name,team,entityType,cellx,celly,areaSize,obstructionSize,model)
        --if troopType == TroopType.Tank or troopType == TroopType.DuiDiMianShangZhiShengJi then
        troop = TankEntity.new();
       -- end

        if troop ~= nil then
            troop.superType = troopType;
            return troop;
        end

        print("Invalid troop type: " ..  troopType.ToString());

        return nil;
end


-- building对象产生类, 根据建筑类型(building表里的 type字段) 
function EntityTool.CreateBuildingFromType(tid, buildingType, initialLevel)

	local building = nil;

	if buildingType == BuildingEntityType.Res then
		building = ResBuildingEntity.new();
	else
		building = BuildingEntity.new();
	end
	building.facingInDegrees = FixedAngle.Degrees90;
	building.superType = tid;
	building.level = initialLevel;
	building.buildingEntityType = buildingType;
	return building;
end




-------------------根据参数生成entity ai 对象
function EntityTool.CreateWeaponAI(entity , weapon , aiid)
    local aiModel = AIModel.new();

    print("------------------CreateWeaponAI  aiid=" , aiid)
 
    local aiConfigModel = Config.ai.getConfig(aiid);
	
	if(aiConfigModel==nil)then printColor("======================================================================================no ai id in logic/ai.txt",aiid);end

    aiModel:setCanShootWall(aiConfigModel.canShootWall);

    aiModel:setAttackBuildings(aiConfigModel.attackBuildings);

    aiModel:setWishAttackBuildings(aiConfigModel.priorityBuildings);

    aiModel:setAttackTroops(aiConfigModel.attackTroops);

    aiModel:setWishAttackTroops(aiConfigModel.priorityTroops);

    aiModel:setCanShootOverWallsAlways(aiConfigModel.canShootOverWall);

    aiModel:setFly(aiConfigModel.isFly);

    aiModel:setCanRunOverWalls(false);

    local weaponAI = WeaponAI.new(entity, weapon , aiModel);

    return weaponAI;
--[[


    if aiid == 0 then

        aiModel:setCanShootWall(true);
        aiModel:setAttackBuildings("0_1,1_1,2_1,3_1,4_1,6_1,61_1,62_1,63_1,64_1,65_1 , 8_1");
        aiModel:setWishAttackBuildings("4,1");

        aiModel:setAttackTroops("0_1,1_1,2_1,3_1,4_1,6_1,17_1,18_1");
        aiModel:setWishAttackTroops("4,1");

        aiModel:setCanShootOverWallsAlways(true);
        aiModel:setCanRunOverWalls(false);
        
    elseif aiid == 1 then
        aiModel:setCanShootWall(false);
        aiModel:setAttackBuildings("0_1,1_1,2_1,3_1,4_1,6_1,61_1,62_1,63_1,64_1,65_1 , 8_1");
        aiModel:setWishAttackBuildings("4,1");

        aiModel:setAttackTroops("0_1,1_1,2_1,3_1,4_1,6_1,17_1,18_1");
        aiModel:setWishAttackTroops("4,1");

        aiModel:setCanShootOverWallsAlways(false);
        aiModel:setCanRunOverWalls(false);
      
        aiModel:setFly(true);
     end   

     ]]

end    


-------------------根据参数生成entity skill 对象
function EntityTool.CreateWeaponSkill(skillid)

    local skillConfigModel = Config.skill.getConfig(skillid);

    print("根据参数生成entity skill 对象 " , skillid , skillConfigModel)

    if skillConfigModel == nil then 
        error("skillConfigModel is nil  id = " ..  skillid);
        return nil;
    end
    
    local skill = Skill.new(skillConfigModel);

    if not IsNilOrEmpty(skill.nextCfgSkill) then

        skill.nextSkillList = List.new();

        for i = 1 , #skill.nextCfgSkill , 1 do

            local subSkill = EntityTool.CreateWeaponSkill(tonumber(skill.nextCfgSkill[i]));

            skill.nextSkillList:add(subSkill);

        end 
    end    
    
    return skill;

--[[
    if skillid == 0 then
        skill.attackLen = 4
        skill.damageCD = 0;
        skill.bulletSpeed = 300;
        skill.attackRate = 0;
        skill.attackNum = 1;
        skill.reloadTime = 2000;
        skill.shootProcessDuration = 0;
        skill.areaType = 0;
        skill.shootFinishDuration = 0;
        skill.lifeTimeAddtive = 1000;
        skill.isGuide = false;
        skill.shootAfterStart = false;
        skill.shootStartDuration = 1000;
        skill.fxShoot = "fx_HuoPao_Shoot.prefab";
        skill.fxHit = "fx_HuoPao_Hit.prefab";

         skill.shootSound = "fx_fight_huoPao;2100";
        
    elseif skillid == 1 then
        skill.attackLen = 20
        skill.damageCD = 150;
        skill.bulletSpeed = 999;
        skill.attackRate = 0;
        skill.attackNum = 0;
        skill.reloadTime = 2000;
        skill.shootProcessDuration = 2000;
        skill.areaType = 0;
        skill.shootFinishDuration = 0;
        skill.lifeTimeAddtive = 1000;
        skill.isGuide = false;
        skill.shootAfterStart = false;
        skill.shootStartDuration = 2000;
        skill.fxShoot = "fx_Light_Shoot.prefab";
        skill.fxHit = "fx_HuoPao_Hit.prefab";
     elseif skillid == 2 then
        skill.attackLen = 10
        skill.damageCD = 0
        skill.bulletSpeed = 300;
        skill.attackRate = 200;
        skill.attackNum = 5;
        skill.reloadTime = 1000;
        skill.shootProcessDuration = 0;
        skill.areaType = 0;
        skill.shootFinishDuration = 0;
        skill.lifeTimeAddtive = 200;
        skill.isGuide = false;
        skill.shootAfterStart = false;
        skill.shootStartDuration = 200;
        skill.mainWeaponShootInterval = 400;
        skill.fxShoot = "fx_Gun_Shoot.prefab";
        skill.fxHit = "fx_Gun_Hit.prefab";    
        skill.shootSound = "fight_fx_gun;1280";
    elseif skillid == 3 then
        skill.attackLen = 10
        skill.damageCD = 0
        skill.bulletSpeed = 10;
        skill.attackRate = 500;
        skill.attackNum = 3;
        skill.reloadTime = 2500;
        skill.shootProcessDuration = 0;
        skill.areaType = 0;
        skill.shootFinishDuration = 0;
        skill.lifeTimeAddtive = 500;
        skill.isGuide = true;
        skill.shootAfterStart = false;
        skill.shootStartDuration = 0;
        skill.fxShoot = "fx_Missile6_Shoot.prefab";
        skill.fxHit = "fx_Missile_Hit.prefab";    
        skill.shootSound = "fight_fx_missile;3000";
     elseif skillid == 4 then
        skill.attackLen = 8
        skill.damageCD = 500
        skill.bulletSpeed = 300;
        skill.attackRate = 0;
        skill.attackNum = 0;
        skill.reloadTime = 1000;
        skill.shootProcessDuration = 5000;
        skill.areaType = 1;
        skill.shootFinishDuration = 0;
        skill.lifeTimeAddtive = 200;
        skill.isGuide = false;
        skill.shootAfterStart = false;
        skill.shootStartDuration = 100;
 
        skill.fxShoot = "fx_Fire_Attack.prefab";
      

      elseif skillid == 5 then
        skill.attackLen = 7
        skill.damageCD = 0
        skill.bulletSpeed = 50;
        skill.attackRate = 200;
        skill.attackNum = 6;
        skill.reloadTime = 100;
        skill.shootProcessDuration = 0;
        skill.areaType = 0;
        skill.shootFinishDuration = 0;
        skill.lifeTimeAddtive = 300;
        skill.isGuide = false;
        skill.shootAfterStart = false;
        skill.shootStartDuration = 0;
        skill.fxShoot = "fx_rocket_Shoot.prefab";
        skill.fxHit = "fx_rocket_Hit.prefab";       
        skill.shootSound = "fx_fight_rocket;1100";
        


elseif skillid == 6 then
        skill.attackLen = 12
        skill.damageCD = 0
        skill.bulletSpeed = 10;
        skill.attackRate = 500;
        skill.attackNum = 6;
        skill.reloadTime = 1000;
        skill.shootProcessDuration = 0;
        skill.areaType = 0;
        skill.shootFinishDuration = 0;
        skill.lifeTimeAddtive = 300;
        skill.isGuide = true;
        skill.shootAfterStart = false;
        skill.shootStartDuration = 0;
        skill.fxShoot = "fx_Missile_Tower_Shoot.prefab";
        skill.fxHit = "fx_Missile_Hit.prefab";    
        skill.shootSound = "fight_fx_missile;3000";

   

            
    end
    ]]
end    

-------------------根据参数 设置 troop entity 的 移动属性 
function EntityTool.SetupMovement(movement , troopEntityModel)

    print("------根据参数 设置 troop entity 的 移动属性" , troopEntityModel:getAttrSpeedBase());

    movement:setMoveSpeed(troopEntityModel:getAttrSpeedBase()); --移动速度

    movement:setPatrolSpeed(troopEntityModel:getAttrSpeedBase()); --巡逻速度, 芳芳说跟移动速度一样

    local soliderType = troopEntityModel.soldier:getDesignMap():getSoliderType();

    local globalData = nil;

    if soliderType == SoldierType.Tank then
        globalData = Config.global.getString(502000);
    elseif soliderType == SoldierType.Chariot then
        globalData = Config.global.getString(502001);
    elseif soliderType == SoldierType.Aircraft then
        globalData = Config.global.getString(502002);    
    end    

    print("globalData = " , globalData)

    if not IsNilOrEmpty(globalData) then
        local globalDataArr = StrSplit(globalData , ";");

        movement:setTurnSpeed(tonumber(globalDataArr[1]));

        movement:setAccelTime(tonumber(globalDataArr[2]));
    end    
 
--[[
--------------------test
    if dpPJ:getSmallSoldierId() == TroopType.Tank then
        movement:setMoveSpeed(0.8);

        movement:setPatrolSpeed(1);

        movement:setTurnSpeed(20);

        movement:setAccelTime(0.0001);
   elseif dpPJ:getSmallSoldierId() == TroopType.DuiKongDanDianZhiShengJi or dpPJ:getSmallSoldierId() == TroopType.DuiDiDanDianZhiShengJi or dpPJ:getSmallSoldierId() == TroopType.DuiDiMianShangZhiShengJi  then
        movement:setMoveSpeed(3);

        movement:setPatrolSpeed(1);

        movement:setTurnSpeed(60);

        movement:setAccelTime(0.0001);     

    else
          movement:setMoveSpeed(2);

        movement:setPatrolSpeed(1);

        movement:setTurnSpeed(60);

        movement:setAccelTime(0.0001);
    end 
    ]]   
end    