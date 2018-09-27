-------------------------------------------
----Entity参数
-------------------------------------------
EntityParamModel = class(Object);

function EntityParamModel:ctor()
	self.team = nil;
	self.entityType = nil;
	self.cellx = nil;
	self.celly = nil;
	self.mSize = nil;
	self.mObstructionSize = nil;
	self.model = nil;
end