----------------------------------------------
---防守方士兵数据
----------------------------------------------
WarDefenSoldierModel = class(Object);

function WarDefenSoldierModel:ctor(data)
	
	self.buildingUid = data.buildingUid;				--附属的建筑Uid
	
	self.soldier = WarSoldierModel.new(data.soldier);	--士兵数据
	
end
