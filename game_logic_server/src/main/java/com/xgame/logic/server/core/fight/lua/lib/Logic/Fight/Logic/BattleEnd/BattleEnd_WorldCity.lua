BattleEnd_WorldCity = class(BattleEnd_AdapterPvp)

function BattleEnd_WorldCity:ctor(battleDataManager , battleOverProccesser)

	self.battleEndTime = Config.rtsType.getConfig(BattleType.WORLD_CITY).time; --Config.global.getNumber(500202);

	print("战斗结束::::::::::::::::::  时间 BattleEnd_WorldCity " , self.battleEndTime);

	self.battleTime = 0;
	
	self.battleDataManager = battleDataManager;

	self.battleOverProccesser = battleOverProccesser;
	
	--print("##########################$$$$$$$$$$$$$$$$$$$$$!!!!!!!!!!!!22" , battleDataManager)
end