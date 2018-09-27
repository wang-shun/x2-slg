----------------------------------------------
----数据模型
----------------------------------------------
EntityModel = class(Object);

function EntityModel:ctor(uid)
	self.uid = uid; ---entity的唯一id  , 战斗返回需要
	
	self.health = MathConst.MaxValue;
	
	self.maxHealth = MathConst.MaxValue;

	self.ClassName = "EntityModel";

	self.warAttr = nil; --战斗属性 参见 WarAttr.lua

	--self.logicNum = 1; --逻辑数量, 对于战斗力的兵来说, 一个entityModel 可能包含多个逻辑entity , hp的属性数值都要乘以 该值 , 才是最终的结果
 	
 	self.troopNum = 1;		--兵数量 , 逻辑数量, 对于战斗力的兵来说, 一个entityModel 可能包含多个逻辑entity , hp的属性数值都要乘以 该值 , 才是最终的结果

 	self.masterPlayerUid = 0;

 	self.masterBuildingId = 0;

 	self.warPlayerId = 0; --玩家id , 该entity是哪个玩家的 , 目前建筑的playerid 是守方id , 战斗返回需要
	
	self:innerInitAttr();
end	

function EntityModel:innerInitAttr()

		self.health = 0; 			--血量
		self.maxHealth = 0; 					--最大血量

		self.singleHealth = 0;					--单兵血量

		self.attack = 0;					--攻击(104)
		self.defend = 0;  					--防御
		self.power = 0;					--功率
		self.load = 0;					--负载
	 
		self.electricityDamage = 0; 		--电磁伤害
		self.electricityDefense = 0;		--电磁抗性
		self.energyDamage = 0;			--动能伤害
		self.energyDefense = 0;			--动能抗性

		self.heatDefense = 0;			--热能抗性
		self.heatDamage = 0;			--热能伤害
		self.laserDamage = 0;			--激光伤害
		self.laserDefense = 0;			--激光抗性
		self.hit = 0;						--命中(命中等级)
		self.dodge = 0;					--闪避(闪避等级)
		self.crit = 0;						--暴击
		self.critical = 0;					--暴击等级
		self.toughness = 0;				--韧性等级
		 

		self.speedBase = 0;				--移动速度
		self.weight = 0;					--采集负重
		self.seekingDistance = 0;		--索敌距离
		self.seekingNum = 0;			--索敌数量
		self.seekingTime = 0;			--索敌时间
		self.radarIntensity = 0;			--雷达强度
		self.radius = 0;					--车体半径
		self.powerConsume = 0;			--消耗功率
		self.load_consume = 0;			--消耗负载

		self.hitExt = 0 --命中扩展
		self.dodgeExt = 0; --闪避扩展
end

function EntityModel:initWarAttr(_warAttr)

--[[
_warAttr.hp = 9999;
	_warAttr.attack = 100;
	_warAttr.defend = 80;
	_warAttr.seekingNum = 2;
	_warAttr.seekingTime = 3;
	_warAttr.speedBase = 30;
	_warAttr.radius = 1;
	_warAttr.seekingDistance = 100;
]]

	self.warAttr = _warAttr;

	if _warAttr ~= nil then 
	print("**********************************************888888888888************   " ,  _warAttr.hp ,  self.troopNum , self.uid)
		self.health = _warAttr.hp * self.troopNum; 			--血量
		self.maxHealth = self.health; 					--最大血量

		self.singleHealth = _warAttr.hp;					--单兵血量

		self.attack = _warAttr.attack;					--攻击(104)
		self.defend = _warAttr.defend;  					--防御
		self.power = _warAttr.power;					--功率
		self.load = _warAttr.load;					--负载
	 
		self.electricityDamage = _warAttr.electricityDamage; 		--电磁伤害
		self.electricityDefense = _warAttr.electricityDefense;		--电磁抗性
		self.energyDamage = _warAttr.energyDamage;			--动能伤害
		self.energyDefense = _warAttr.energyDefense;			--动能抗性

		self.heatDefense = _warAttr.heatDefense;			--热能抗性
		self.heatDamage = _warAttr.heatDamage;			--热能伤害
		self.laserDamage = _warAttr.laserDamage;			--激光伤害
		self.laserDefense = _warAttr.laserDefense;			--激光抗性
		self.hit = _warAttr.hit;						--命中(命中等级)
		self.dodge = _warAttr.dodge;					--闪避(闪避等级)
		self.crit = _warAttr.crit;						--暴击
		self.critical = _warAttr.critical;					--暴击等级
		self.toughness = _warAttr.toughness;				--韧性等级
		 

		self.speedBase = _warAttr.speedBase;				--移动速度
		self.weight = _warAttr.weight;					--采集负重
		self.seekingDistance = _warAttr.seekingDistance;		--索敌距离
		self.seekingNum = _warAttr.seekingNum;			--索敌数量
		self.seekingTime = _warAttr.seekingTime;			--索敌时间
		self.radarIntensity = _warAttr.radarIntensity;			--雷达强度
		self.radius = _warAttr.radius;					--车体半径
		self.powerConsume = _warAttr.powerConsume;			--消耗功率
		self.load_consume = _warAttr.load_consume;			--消耗负载

		self.hitExt = 0 --命中扩展
		self.dodgeExt = 0; --闪避扩展

	end	
end	

function EntityModel:setHealth(v)
	if v<0 then
		v = 0;
	end
		
	self.health = v;
end

function EntityModel:getSingleHealth()
	return self.singleHealth;
end


function EntityModel:getHealth()
	return self.health;
end

function EntityModel:getAttrMaxHealth()
	return self.maxHealth;
end

function EntityModel:getAttrAttack()
	return self.attack;
end

function EntityModel:getAttrDefend()
	return self.defend;
end

function EntityModel:getAttrPower()
	return self.power;
end

function EntityModel:getAttrLoad()
	return self.load;
end

function EntityModel:getAttrElectricityDamage()
	return self.electricityDamage;
end

function EntityModel:getAttrElectricityDefense()
	return self.electricityDefense;
end

function EntityModel:getAttrEnergyDamage()
	return self.energyDamage;
end

function EntityModel:getAttrEnergyDefense()
	return self.energyDefense;
end



function EntityModel:getAttrHeatDefense()
	return self.heatDefense;
end

function EntityModel:getAttrHeatDamage()
	return self.heatDamage;
end

function EntityModel:getAttrLaserDamage()
	return self.laserDamage;
end

function EntityModel:getAttrLaserDefense()
	return self.laserDefense;
end

function EntityModel:getAttrHit()

	local _hit = self.hit + self.hitExt;

	if _hit < 0 then
		_hit = 0;
	end
		
	return _hit;
end

function EntityModel:getAttrBaseHit()
	return self.hit;
end

 --命中扩展
function EntityModel:addHitExt(v)
	self.hitExt = self.hitExt + v;
end	

function EntityModel:getAttrDodge()

	local _dodge = self.dodge + self.dodgeExt;

	if _dodge < 0 then
		_dodge = 0;
	end
		
	return _dodge;

end

function EntityModel:getAttrBaseDodge()
	return self.dodge;
end

--闪避扩展
function EntityModel:addDodgeExt(v)
	self.dodgeExt = self.dodgeExt + v;
end	

function EntityModel:getAttrDodge()
	return self.crit;
end

function EntityModel:getAttrCritical()
	return self.critical;
end

function EntityModel:getAttrToughness()
	return self.toughness;
end

 

function EntityModel:getAttrSpeedBase()
	return self.speedBase;
end
 
function EntityModel:getAttrWeight()
	return self.weight;
end

function EntityModel:getAttrSeekingDistance()
	return self.seekingDistance;
end

function EntityModel:getAttrSeekingNum()
	return self.seekingNum;
end

function EntityModel:getAttrSeekingTime()
	return self.seekingTime;
end

function EntityModel:getAttrRadarIntensity()
	return self.radarIntensity;
end

function EntityModel:getAttrRadius()
	return self.radius;
end

function EntityModel:getAttrPowerConsume()
	return self.powerConsume;
end

function EntityModel:getAttrLoadConsume()
	return self.load_consume;
end

