----------------------------------------------------
----建筑模型数据
----------------------------------------------------
BuildingEntityModel = class(EntityModel);

--初始化数据

--uid 			建筑uid
--configID		建筑配置ID
--level			建筑等级
--type 			building表中的type字段
--state 		建筑状态
--resourceID	矿车的资源类型(矿车用)
function BuildingEntityModel:ctor(uid , configID , level , type , state , resourceID , skillArr , ai , _warAttr , destroyWeight , leftResNum , playerUid)
	self.uid = uid;
	
	self.configID = configID;
	
	self.type = type;
	
	self.level = level;
	
	if state ~= nil then
		self.state = state;
	else
		self.state = BuildingState.USEING;
	end

	self.resourceID = resourceID;

	self.ClassName = "BuildingEntityModel";

	self.skillArr = skillArr;

	self.ai = ai;

--	if((--[[self.configID==1301 or self.configID==1302 or self.configID==1303 or self.configID==1304 or self.configID==1305]]self.configID==1303) and _warAttr~=nil)then
--		printColor("--------------------------------------------- ",self.configID,_warAttr);
--		_warAttr.seekingNum=1;
--		_warAttr.seekingDistance=20;
--	end

	self:initWarAttr(_warAttr);

	self.destroyWeight = destroyWeight;

	self.leftResNum = leftResNum;

	self.warPlayerId = playerUid; --建筑所属的playerid(对于防守方来说, 是守方的uid)
end
