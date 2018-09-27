--------------------------------------------
----entity参数模型
--------------------------------------------
EntityParamModel = class(Object);

function EntityParamModel:ctor(team,entityType,cellx,celly,mSize,mObstructionSize,model)
	self.team 				= team;
	self.entityType 		= entityType;
	self.cellx 				= cellx;
	self.celly 				= celly;
	self.mSize 				= mSize;
	self.mObstructionSize 	= mObstructionSize;
	self.model 				= model;
end

