---------------------------------------------------------------
--建筑升级前置条件模型
---------------------------------------------------------------
RequireBuildingModel = class(Object);

function RequireBuildingModel:ctor(id,level)
	self.id = id;		--前置建筑的configID
	self.level = level;	--前置建筑的等级
end