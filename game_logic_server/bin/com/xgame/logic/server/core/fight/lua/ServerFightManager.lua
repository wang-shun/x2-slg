ServerFightManager = {};

local this = ServerFightManager;

function ServerFightManager.FightCalc(warAttackModel , warReport , fps , calcTime)
	
	print("****************************board step2 " , Board.entities:getSize())
	
	this.dataController = BattleDataController.new(warAttackModel);
	
	this.dataController:init();
	
	Board.isServerMode = true;
	
	print("[battle_calc]自动放兵...");
	
	for i = 1 , #warAttackModel.attackData.soldiers , 1 do
		local warSoldierModel = warAttackModel.attackData.soldiers[i];
		
		local soldierData = warSoldierModel.soldier;
		
		print("放兵:x=" , warSoldierModel.position.x , " y=" , warSoldierModel.position.y , warSoldierModel.index , soldierData.num , soldierData.soldierId);
		
		this.dataController:putSoldier(warSoldierModel.position.x , warSoldierModel.position.y , warSoldierModel.index , soldierData.num);
	end
 	
 	local cTime = os.date("%c");
	print("[battle_calc]开始计算... startTime=" , cTime , "fps=" , fps , "calcTime=" , calcTime);
	
	local calcCount = calcTime * fps;
	
	local timeDelta = 1 / fps;
	
	
	print(this.dataController.battle.gameBoard == Board , "****************************board step2 " , this.dataController.battle.gameBoard.entities:getSize())
	
 
	for i = 1 , calcCount , 1 do
	
		this.dataController.battle.gameBoard.VisitStep(timeDelta); ---数据层 更新
		
		this.dataController.battle:step(timeDelta);-----battle更新  寻路, ai , weapon
		
		if this.dataController:isBattleOver() then
		print("------------over----------")
			break;
		end
	
	end
	
	cTime = os.date("%c");
	
	print("[battle_calc]计算结束... endTime=" , cTime); 
	
	print("[battle_calc]组装战报.....");
	
	local innerBattleReport = this.dataController:getBattleReport();
	
	warReport:setWinUid(innerBattleReport.winPlayerId);
	
	warReport:setDestroyLevel(innerBattleReport.cuiHuiDu);
	
    for i = 1 , innerBattleReport.battleSoldierReportList:getSize() , 1 do
    	local luaSoldier = innerBattleReport.battleSoldierReportList[i];
    	local javaSoldierData = luajava.newInstance("com.xgame.logic.server.game.war.entity.report.WarSoldierInfo");
    	javaSoldierData:setPlayerId(luaSoldier.playerId);
    	javaSoldierData:setBuildUid(luaSoldier.buildUid);
    	javaSoldierData:setUid(luaSoldier.uid);
    	javaSoldierData:setNum(luaSoldier.num);
    	javaSoldierData:setDeadNum(luaSoldier.deadNum);
    	javaSoldierData:setIndex(luaSoldier.index);
    	
    	if luaSoldier.team == EntityTeam.Attacker then
    		warReport:getWarEntityReport():getAttackSoldierBean():add(javaSoldierData);
    	else
    		warReport:getWarEntityReport():getDefendSoldierBean():add(javaSoldierData);
    	end	
    end

	for i = 1 , innerBattleReport.battleBuildReportList:getSize() , 1 do
		local luaBuild = innerBattleReport.battleBuildReportList[i];
		local javaBuildData = luajava.newInstance("com.xgame.logic.server.game.war.entity.report.WarBuildingInfo");
		
		javaBuildData:setUid(luaBuild.uid);
		javaBuildData:setLostHp(luaBuild.lostHp);
		
		print(luaBuild.playerDamageMap)
		local damageMapKeys = luaBuild.playerDamageMap:keys();
		local buildDamageLen = #damageMapKeys;
		
		for j = 1 , buildDamageLen , 1 do
			print("%%%%%%%%%%%%%%建筑%%%%%%%%%%%%%%%   " , damageMapKeys[j] , luaBuild.playerDamageMap[damageMapKeys[j]])
			javaBuildData:getPlayerDamagerMap():put(tostring(damageMapKeys[j]) , Mathf.Ceil(luaBuild.playerDamageMap[damageMapKeys[j]]));
		end
		
		warReport:getWarEntityReport():getBuildingReport():add(javaBuildData);
	end
	
	local playerReportArray = innerBattleReport.battlePlayerReportMap:values();
	
	print("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%   " , innerBattleReport.battlePlayerReportMap:size())
	
	for i = 1 , #playerReportArray , 1 do
		local javaWarPlayerReport = luajava.newInstance("com.xgame.logic.server.game.war.entity.report.WarPlayerReport");
		local luaPlayerReport = playerReportArray[i];
		javaWarPlayerReport:setKilledNum(luaPlayerReport.killedNum);
		javaWarPlayerReport:setDeadNum(luaPlayerReport.deadNum);
		javaWarPlayerReport:setPlayerId(luaPlayerReport.playerId);
		warReport:getWarPlayerReport():add(javaWarPlayerReport);
	end
	
	
	print("[battle_calc]组装战报完毕.");
	
end