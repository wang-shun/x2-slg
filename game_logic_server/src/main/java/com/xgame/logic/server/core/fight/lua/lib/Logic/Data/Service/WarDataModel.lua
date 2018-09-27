-------------------------------------------
----战斗数据
-------------------------------------------
WarDataModel = class(Object);

function WarDataModel:ctor(data)
	
	self.battleId = data.battleId;							--战斗ID
	
	self.attackData = WarAttackModel.new(data.attackData);	--进攻方数据
	
	self.defenData = WarDefenModel.new(data.defenData);		--防守方数据

	self.battleType = data.battleType;				--战斗类型 参见 BattleType

end