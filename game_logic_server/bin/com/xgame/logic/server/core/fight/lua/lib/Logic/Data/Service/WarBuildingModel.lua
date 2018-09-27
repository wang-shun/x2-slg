--------------------------------------
----战斗中建筑数据模型
--------------------------------------
WarBuildingModel = class(Object);

function WarBuildingModel:ctor(data)
	
	self.buildInfo = BuildInfo.new();			--建筑信息
	self.buildInfo:warpData(data.building);

	self.cellx = data.transform.vector2Bean.x;	--建筑坐标x
	self.celly = data.transform.vector2Bean.y;	--建筑坐标y

	self.warAttr = WarAttrModel.new(data.warAttr);	--建筑战斗属性

end