------------------------------------------
----士兵数据
------------------------------------------
WarSoldierModel = class(Object);

function WarSoldierModel:ctor(data)

	self.ClassName = "WarSoldierModel";

	self.index = data.index;								--序列号

	self.soldier = SoldierModel.new();

	self.soldier:setFullSoldierBean( data.soldier );	

	self.hurtNum = data.soldier.hurtNum;						--伤兵数量	

	
	self.warAttr = WarAttrModel.new(data.warAttr);					--战斗属性



	self.fightPower = data.fightPower;						--伤亡士兵的战力(rts无用)
	self.deadNum = data.deadNum;						--死亡士兵数量(rts无用)
	self.playerId = data.playerId;							--玩家id
	self.position = data.position;							--位置(实时 rts 无用)

	print("self.num:"..tostring(self.num).." self:"..tostring(self));
end
