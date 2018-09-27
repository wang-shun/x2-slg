--武器ai  , 主要用于 副武器 ,  主武器的ai 体现在 主体上了

WeaponAI = class(EntityAI)

function WeaponAI:ctor(entity , weapon , aiModel)

	--print(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>创建 WeaponAI   " , entity)
	self.weapon = weapon;
	
	self.aiModel = aiModel;

	self.preFocusList = List.new(); -- 预索敌列表 , weapon 会一直查找目标, 保持preFocusList 有对象, 这个查找无需等待时间

	--索敌列表, 在preFocusList 等待 索敌时间后 ,  目标进入focusList , 如果focusList 已满 , 会留在preFocusList ,  weapon 只会从focusList 里拿目标进行攻击

	-- 索敌逻辑是这样:  
	-- 1 根据目标类型 和 目标优先级 , 找到一批可攻击的目标 
	-- 2 根据攻击距离最近原则 , 将这批目标里的某个目标放到preFocusList , 同时算出锁敌等待时间
	-- 3 索敌时间到达后 , 将目标放入到 focusList 里, weapon 会攻击focusList里的第一个目标
	-- 4 如果目标是墙的话,  秒锁 直接进入focusList , 进入focusList 的第一个, 原来的目标往后顺延
	-- 5 如果需要反击的话 ,  待反击的目标是可攻击的目标类型的话 ,  则进入preFocusList , 走锁定时间,  锁定时间到了之后 , 进入focusList 的第一个, 原来的目标往后顺延(只作用于主武器)
	self.focusList = List.new();

	self.tmpTargetList = List.new();

	self.weaponTime = 0;

	self.focusLen = MathConst.MaxValue;

	self.fightEnemy = aiModel:getFightEnemy(); --是否作用于敌方

	self.initiativeFindTarget = true;

	--print("$$$$$$$$$$%%%%%%%%%%%%%%%%%%%%%  WeaponAI")
end

--清空索敌列表
function WeaponAI:clearFocusList()
	--print("------------清空索敌列表")
	self.focusList:clear();
	self.preFocusList:clear();
	self.tmpTargetList:clear();
end	

function WeaponAI:stepAI(_weaponTime)
 
	self.weaponTime = _weaponTime;

	local topSuper = getTopSuper(self);

	if topSuper ~= nil then
	    topSuper.stepAI(self);
	end

	if self:getCurrentCommand() == nil then

	    local aICommand = nil;

	    if self.weapon.weaponType == ArmorType.mailArmor or self.weapon.weaponType == ArmorType.notArmor then
	    	aICommand = MainWeaponAttackCommand.new(self.entityOwner,self.weapon);
	    elseif self.weapon.weaponType == ArmorType.viceArmor then
	    	aICommand = SubWeaponAttackCommand.new(self.entityOwner,self.weapon);
	    end
	    
	 --   print("::::::::::::::::::     " , aICommand.ClassName)
	    if aICommand ~= nil then
	    	topSuper.pushCommand(self , aICommand);
	    end	
	end 
 
end

function  WeaponAI:proccessTarget()
	self:proccesFocusList();

	self:proccesFindTarget();

	self:proccesPreFocusList();
end	

function  WeaponAI:proccesFocusList()
	self.tmpTargetList:clear();

	for i = 1 , self.focusList:getSize() , 1 do
		local tmpTarget = self.focusList[i];
		if tmpTarget:isDead() then
			print("------------目标死亡" , tmpTarget.id , i)
			self.tmpTargetList:add(tmpTarget);			
		end
	end	

	for i = 1 , self.tmpTargetList:getSize() , 1 do
		self.focusList:remove(self.tmpTargetList[i]);
	end	

	self.tmpTargetList:clear();
end	

function  WeaponAI:proccesPreFocusList()
	-- 3 索敌时间到达后 , 将目标放入到 focusList 里, weapon 会攻击focusList里的第一个目标

	local _focusNum = AttrCalc.GetFocusNum(self.entityOwner);
	--如果索敌列表已满, 不处理prefocusList
	if self.focusList:getSize() >= _focusNum then
		return;
	end	

	if self.preFocusList:getSize() == 0 then
		return;
	end	 

	self.tmpTargetList:clear();
	print("weapon:" , self.weapon.showId , "------------------3  proccesPreFocusList getSize=" , self.preFocusList:getSize() , self.weaponTime , "索敌数量:" , _focusNum)
	for i = 1 , self.preFocusList:getSize() , 1 do
		local tmpTarget = self.preFocusList[i]["entity"];
		print("------------------3.1" ,  tmpTarget:isDead()  , "索敌数量:" , AttrCalc.GetFocusNum(self.entityOwner)  , self.entityOwner.ClassName)
		if not tmpTarget:isDead() then
			local time =  self.preFocusList[i]["time"];
			local forceFirst = self.preFocusList[i]["forceFirst"];
			if self.weaponTime >= time and self.focusList:getSize() < _focusNum then
				if forceFirst then
					print("-----------3.2 insert to focusList" , time , "self.focusList:getSize()=", self.focusList:getSize())
					self.focusList:insert(tmpTarget , 1);
				else	
					print("-----------3.2 add to focusList" , time , "self.focusList:getSize()=" ,self.focusList:getSize())
					self.focusList:add(tmpTarget);
				end	
				self.tmpTargetList:add(self.preFocusList[i]);
			end
		else
			self.tmpTargetList:add(self.preFocusList[i]);		
		end
	end	

	for i = 1 , self.tmpTargetList:getSize() , 1 do
		self.preFocusList:remove(self.tmpTargetList[i]);
	end	
	print("-----------3.3  self.focusList:getSize()=" ,self.focusList:getSize())
	self.tmpTargetList:clear();
end

function WeaponAI:proccesFindTarget( )
	self.tmpTargetList:clear();

	self.tmpTargetList = self.entityOwner.currentBattle:getCanAttackTargetList(self.entityOwner , self.aiModel , self.fightEnemy , self.weapon , function ( tgt )
		if self.focusList:contains(tgt) then
		    		return false;
		    	elseif self:existsInPreFocusList(tgt) then
		    		return false;
		    	else
		    		return true;
		    	end		
		end);
	
	print("---WeaponAI:proccesFindTarget-------1 " ,  self.tmpTargetList:getSize() )


	 
	-- 2 根据攻击距离最近原则 , 将这批目标里的某个目标放到preFocusList , 同时算出锁敌等待时间
	local tmpLen = 0;

	local maxLen = MathConst.MaxValue;
	
	local bestTarget = nil;

	for i = 1 , self.tmpTargetList:getSize() , 1 do

		local tmpTarget = self.tmpTargetList[i];

		local tmpLen = FVector3.Distance(tmpTarget:getSimPosition() , self.entityOwner:getPosition());

		if tmpLen < maxLen then
			bestTarget = tmpTarget;
			maxLen = tmpLen;
		end	
	end	
	
	--print("---------111111111 ")
	--print(self.weaponTime)
	--print(EntityTool)
	--print(self)
	--print(bestTarget)
	--print(self.troopOwner)
	--print("---------111111111 " , bestTarget ,  self.weaponTime + EntityTool.CalcFocusTime(self.troopOwner , bestTarget))
	if bestTarget ~= nil then
		------锁敌时间 秒  一个兵种上的所有配件的此数值一样   索敌时间=参数/(雷达强度*对手车体半径) 
		------这里的锁敌时间 要走公式 , 先写死
		print("-------------step 1 索敌时间=" , AttrCalc.GetFocusTime(self.entityOwner , bestTarget) , "  self.preFocusList:getSize()=" , self.preFocusList:getSize())
		self.preFocusList:add({ entity = bestTarget , time = self.weaponTime + AttrCalc.GetFocusTime(self.entityOwner , bestTarget) , forceFirst = false}); 
	end	
end

function  WeaponAI:removeFromPreFocusList(targetEntity)
	for j = 1 , self.preFocusList:getSize() , 1 do
		local tmpPreFocusItem = self.preFocusList[j];
		if tmpPreFocusItem.entity == targetEntity then
			self.preFocusList:remove(tmpPreFocusItem);			
		end	
	end
end	

function  WeaponAI:existsInPreFocusList(targetEntity)
	for j = 1 , self.preFocusList:getSize() , 1 do
		local tmpPreFocusItem = self.preFocusList[j];
		if tmpPreFocusItem.entity == targetEntity then
			return true;				
		end	
	end
end	

--如果ai表里的 "是否优先反击" 配的是1 的话, 在被攻击时(技能命中时), 就要调用这个方法 处理反击的事情 (芳芳 于2017-3-1 下午16:18 一边吃着零食一边紧缩眉头说的)
--5 如果需要反击的话 , 待反击的目标是可攻击的目标类型的话 ,  则进入preFocusList , 走锁定时间,  锁定时间到了之后 , 进入focusList 的第一个, 原来的目标往后顺延(只作用于主武器)
function  WeaponAI:counterAttackTarget(targetEntity)

	if not self.aiModel:getIsBack() then
		--ai决定不能反击
		return;
	end
		
	if targetEntity:isDead() then
	 	return;
	end

	self:removeFromPreFocusList(targetEntity);

	if self.focusList:contains(targetEntity) then
		self.focusList:remove(targetEntity);
		self.focusList:insert(1 , targetEntity);
		return;
	end	

	local canProccess = self.entityOwner.currentBattle:canAttack(self.entityOwner , self.aiModel , self.fightEnemy , self.weapon , nil , targetEntity);

	if canProccess then
		self.focusList:insert(1 , targetEntity);
	end	

end

--获取一个目标
function WeaponAI:findTarget()
	-- weapon 会攻击focusList里的第一个目标
	--print("WeaponAI:findTarget", self.preFocusList:getSize() ,self.focusList:getSize() )
	if self.focusList:getSize() > 0 then
		return self.focusList[1];
	end	
end   

--获取所有可攻击目标
function WeaponAI:findAllTarget()
	return self.focusList;
end 

function WeaponAI:insertTargetToFocusList(targetEntity)

	self.focusList:insert(1 , targetEntity);

	if self.focusList:getSize() > AttrCalc.GetFocusNum(self.entityOwner) then
		self.focusList:removeAt(self.focusList:getSize());
	end	

end	

function WeaponAI:setMarkTarget(tgt)
	-- 4 如果目标是墙的话,  秒锁 直接进入focusList , 进入focusList 的第一个, 原来的目标往后顺延
	self.mTarget = tgt;
 
	self:clearIdentifyCheck();

	if tgt ~= nil and self.mTarget:isWall() then
		self:insertTargetToFocusList(self.mTarget );
	end	
end