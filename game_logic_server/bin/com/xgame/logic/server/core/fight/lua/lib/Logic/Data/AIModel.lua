------来自策划的AI表数据 , 主副武器会有自己的ai  . 车体的ai来主武器 , 某些情况下 也会来自副武器 , 通过替换aimodel 来实现ai 切换

AIModel = class(Object);

function AIModel:ctor()
	self.ClassName = "AIModel";

	self.ai_ID = 0; --秀芳说她要改关联

             --可攻击的建筑 : 建筑类型_攻击加成 , 建筑类型_攻击加成 
             --同时也可以实现建筑攻击建筑
            self.ai_attackBuildings = List.new();

            --建筑攻击优先级  , 越前面攻击优先级越高
            self.ai_wishAttackBuildings = List.new();

            self.ai_attackTroops = List.new();
            
            self.ai_wishAttackTroops = List.new();

            self.ai_mCanShootOverWallsAlways = false; -- 是否可以越墙攻击

	self.mCanRunOverWalls = false; -- 是否可以越墙寻路

	self.flying = false;

	self.isBack = false; --是否能反击

	self.fightEnemy = true; --是否作用于敌方

	self.ai_canShootWall = true; --是否可攻击墙
end

function AIModel:setFightEnemy(v) 
	self.fightEnemy = v;
end

function AIModel:getFightEnemy() 
	return self.fightEnemy;
end	

function AIModel:setIsBack(v) 
	self.isBack = v;
end

function AIModel:getIsBack() 
	return self.isBack;
end

function AIModel:setFly(v) 
	self.flying = v;
end

function AIModel:getFly() 
	return self.flying;
end

--可攻击的建筑（填入建筑type,多个用分号分隔)    建筑类型_攻击加成  :  0_1,1_1,2_1,5_1,6_1  0_2,1_1,2_1,3_1,5_1,61_1,62_1,63_1,64_1,65_1
function AIModel:setAttackBuildings(v) 
	self.ai_attackBuildings:clear();
	if not IsNilOrEmpty(v) then
		local strs = StringSplit(v , ",");
		for i = 1 , #strs , 1 do
			local subStrs = StringSplit(strs[i] , "_");
			self.ai_attackBuildings:add({ mType = tonumber(subStrs[1]) , mAdd = tonumber(subStrs[2]) });
		end	
	end
end

---1,2,3,4,5
function AIModel:setWishAttackBuildings(v) 
	self.ai_wishAttackBuildings:clear();
	if not IsNilOrEmpty(v) then
		local strs = StringSplit(v , ",");
		for i = 1 , #strs , 1 do
			self.ai_wishAttackBuildings:add({ mType = tonumber(strs[1]) });
		end	
	end
end	

 
 
function AIModel:setAttackTroops(v) 
	self.ai_attackTroops:clear();
	if not IsNilOrEmpty(v) then
		local strs = StringSplit(v , ",");
		for i = 1 , #strs , 1 do
			local subStrs = StringSplit(strs[i] , "_");
			self.ai_attackTroops:add({ mType = tonumber(subStrs[1]) , mAdd = tonumber(subStrs[2]) });
		end	
	end
end

---1,2,3,4,5
function AIModel:setWishAttackTroops(v) 
	self.ai_wishAttackTroops:clear();
	if not IsNilOrEmpty(v) then
		local strs = StringSplit(v , ",");
		for i = 1 , #strs , 1 do
			self.ai_wishAttackTroops:add({ mType = tonumber(strs[1]) });
		end	
	end
end

 
function AIModel:getWishAttackBuildings()
	return self.ai_wishAttackBuildings;
end

function AIModel:getAttackBuildings()
	return self.ai_attackBuildings;
end

function AIModel:getWishAttackTroops()
	return self.ai_wishAttackTroops;
end

function AIModel:getAttackTroops()
	return self.ai_attackTroops;
end	

function AIModel:setAIID(v) 
	self.ai_ID = v;
end

function AIModel:getAIID() 
	return self.ai_ID;
end	

function AIModel:setCanShootOverWallsAlways(v)
	self.ai_mCanShootOverWallsAlways = v;
end

function AIModel:getCanShootOverWallsAlways()
	return self.ai_mCanShootOverWallsAlways;
end

function AIModel:setCanShootWall(v)
	self.ai_canShootWall = v;
end

function AIModel:getCanShootWall()
	return self.ai_canShootWall;
end

function AIModel:setCanRunOverWalls(v)
	self.mCanRunOverWalls = v;
end

function AIModel:getCanRunOverWalls()
	return self.mCanRunOverWalls;
end

