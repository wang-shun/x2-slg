---------------------------------------------------------
----建筑信息
---------------------------------------------------------
BuildInfo = class(Object)

--建筑状态
BuildingState = 
{   
    --预创建
    PRE_BUILDING = 0,
    --创建中
    CREATEING = 1,
    --使用中
    USEING = 2,
    --升级中
    LEVEL_UP = 3,
    --移除中
    REMOVEING = 4,
    --生产
    OUTPUT = 5,
    --销毁
    DESTROY = 7,
    --取消销毁
    CANCEL_DESTROY = 8,
}

function BuildInfo:ctor()
	self.uid = 0
	self.configID = 0
	self.level = 0
	self.state = BuildingState.CREATEING
end

function BuildInfo:warpData(data)
	self.uid = data.uid;
	self.configID = data.sid;
	self.level = data.level;
	self.state = data.state;
	if data.sid == BuildingType.B_MiningVehicle then
	    self.resourceID = tonumber(data.ext);
	end

end