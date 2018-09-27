WarAttackLeftSoldierModel = class(Object);

--soldier WarSoldierModel
function WarAttackLeftSoldierModel:ctor(soldier)

	self.soldier = soldier;					--WarSoldierModel

	self.index = soldier.index;				--六个位置中的index

	self.allNum = soldier.soldier.num;		--总数量

	self.curNum = self.allNum;				--剩余数量

end