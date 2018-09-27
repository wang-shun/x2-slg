-------------------------------------------
----战斗数据（只解析不存储）
-------------------------------------------
WarDataManager = {};

----接收搜索到的玩家数据
function WarDataManager.getSearchPlayer(data)
	
	local model = WarDefenModel.new(data.defenData);
	
    EventCenter.Broadcast(EGameEvent.War_Search_Back,model);

end

----接收到战斗数据
function WarDataManager.getWarData(data)
	
	local model = WarDataModel.new(data);
	
	-- GameStateMgr.gotoState(GameState.Battle , model);

	BattleLogicController.showWarData(model);

	EventCenter.Broadcast(EGameEvent.War_Start_Back,model);

end

----接收到战斗结束数据
function WarDataManager.getWarEndData(data)
	
	local model = WarResultModel.new(data);
	
	EventCenter.Broadcast(EGameEvent.War_End_Back,model);

end