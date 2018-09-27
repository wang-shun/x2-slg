-------------------------------------------------
---- 战场控制器
-------------------------------------------------
BattleLogicController = {};

local this = BattleLogicController;
this.isActive = false;

this.controller = nil;

this.dataController = nil;

--data = WarDefenModel（搜索的形式进入战斗场景查看）
function BattleLogicController.enter(callBack,data)
	this.showSearchData(data , callBack);
end

--处理战斗结束
function BattleLogicController.checkBattleOver(board , checkBattleOver)
	this.dataController:checkBattleOver(board,checkBattleOver);
end

----真正战斗数据返回
function BattleLogicController.showWarData(data , callBack)

	this.data = data;
	
	if this.controller == nil then
		this.controller = BattleBoardViewController.new(this);
	end
	
	--
	this.dataController = BattleDataController.new(data);

	BoardView.clear();

	this.dataController:init();

	BoardView.SetBoard(Board , this.dataController.battle);

	BoardView.createScene(
		function()
			this.init();
			if callBack then
				callBack();
			end
	 	end
	 );

	Util.ClearMemory();
end

----搜索玩家时的数据展示
--data = WarDefenModel
function BattleLogicController.showSearchData(data , callBack)

	if this.controller == nil then
		this.controller = BattleBoardViewController.new(this);
	end

	--初始化 Board
	this.data = data;	--WarDefenModel

	Board.reset();
	
	BoardView.clear();

	this.battle = Battle.new(Board);
	
	--防守方建筑数据
	local buildingList = this.data.buildings;

	--local buildingEntityList = List.new();

	for i=1 , #buildingList do
		
		local param = EntityFactory.createBattleDefenBuildingEntity(buildingList[i]);

		local building = Board.createEntityByParamModel(param);

		--buildingEntityList:add(building);
	end

	--初始化守方的建筑最大摧毁度
	-- this.battleOverProccesser:initBuildingDestroyDegree(buildingEntityList); 不需要了吧？

	--防守驻军数据 WarDefenSoldierModel... 
	local soldierList = this.data.soldiers;
	
	for i=1 , #soldierList do

		local soldier = soldierList[i];

		local paramList = EntityFactory.createBattleDefenTroopEntityList(soldier);

		for j = 1 , #paramList do

			local troop = Board.createEntityByParamModel(paramList[j]);
			
			troop.currentBattle = this.battle;

			troop.troopAI:setParkingType(ParkSize.OneGrid);
			
			troop.troopAI:setBaseCommand( WarningAroundBuildingCommand.new(troop) );
		end
	end	
	
	--------------------------------------------------------------------------------------------------------------

	BoardView.SetBoard(Board , this.battle);
	
	-- 创建场景视图
	BoardView.createScene(
		function()
			if callBack~=nil then
				callBack();
			end
			this.controller:showEnterEffect();
		end
	);

end

function BattleLogicController.init()
	this.initEvent();
	UIManager.showView(UI.RTSWarView,this.data);
	this.ui = UIManager.getView(UI.RTSWarView);
	UIManager.hideView(UI.MainUIView);
end

function BattleLogicController.initEvent()
	EventCenter.AddListener(EGameEvent.War_End_Back , this , this.warEndBack); 
end

function BattleLogicController.removeEvent()
	EventCenter.RemoveListener(EGameEvent.War_End_Back , this , this.warEndBack); 
end

function BattleLogicController.showEnterEffect()
	this.controller:setFieldOfView(24);
	this.controller:enter();
end

--isAttackerWin 进攻方是否胜利
function BattleLogicController.gameOver(isAttackerWin)

	if this.dataController.isGameOver then
		return;
	end

	local winUid = this.data.attackData.uid;

	if isAttackerWin==nil or not isAttackerWin then
		local winUid = this.data.defenData.uid;
	end

	CSHandler.sendMessage(CSHandler.CS_ReqWarEnd , {winUid = winUid , battleId = this.data.battleId});

	this.dataController.isGameOver = true;

end

--是否放光了所有的兵
function BattleLogicController.isEmpty()
	return this.dataController:isEmpty();
end

--获得车体的初始朝向, 默认朝向44,44
function BattleLogicController.getStartFacing(pos)
	local sim_facing = (FVector3.new(44,0,44) - pos):normalize();

	local sim_targetAngle = FixedAngle.AngleInDegrees(sim_facing);

	local newFacing = FixedAngle.AngleInDegrees(FixedAngle.FacingForAngleInDegrees(sim_targetAngle));

	return newFacing;
end

function BattleLogicController.putSoldier(pos)
	
	if this.ui ~= nil and this.ui:isOpen() then

		local data = this.ui:getSoldierData();

		if data == nil then
			return;
		end
		
		local troop = this.dataController:putSoldier(pos.x,pos.y,data.index,data.num);
		
		BoardView.createEntityView(troop);
	end

end

function BattleLogicController.warEndBack(luaObj , warResultModel)
	UIManager.showView(UI.RTSResultView,warResultModel);
end

function BattleLogicController.exit()
	if this.battle.currentBattle ~= nil then
		this.battle.currentBattle.battleStarted = false;
	end
	this.controller:exit();
	this.removeEvent();
	this.ui = nil;
	this.isActive = false;
	UIManager.hideView(UI.RTSWarView);
end