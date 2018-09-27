--------------------------------------
----战斗属性
--------------------------------------
WarAttrModel = class(Object);

function WarAttrModel:ctor(data)

	self.attack = data.attack;				--攻击
	
	self.defend = data.defend;				--防御
	
	self.hp = data.hp;					--血量***************

	self.power = data.power;				--功率

	self.load = data.load;					--负载

	self.electricityDamage = data.electricityDamage;	--电磁伤害

	self.electricityDefense = data.electricityDefense;		--电磁抗性

	self.energyDamage = data.energyDamage;		--动能伤害

	self.energyDefense = data.energyDefense;		--动能抗性

	self.heatDefense = data.heatDefense;			--热能抗性

	self.heatDamage = data.heatDamage;			--热能伤害

	self.laserDamage = data.laserDamage;			--激光伤害

	self.laserDefense = data.laserDefense;			--激光抗性

	self.hit = data.hit;					--命中

	self.dodge = data.dodge;				--闪避

	self.crit = data.crit;					--暴击

	self.critical = data.critical;				--暴击等级

	self.toughness = data.toughness;			--韧性等级

	self.speedBase = data.speedBase;			--移动速度

	self.weight = data.weight;				--采集负重

	self.seekingDistance = data.seekingDistance;		--索敌距离

	self.seekingNum = data.seekingNum;			--索敌数量

	self.radarIntensity = data.radarIntensity;			--雷达强度

	self.radius = data.radius;				--车体半径

	self.powerConsume = data.powerConsume;		--消耗功率

	self.load_consume = data.loadConsume;		--消耗负载

	
end