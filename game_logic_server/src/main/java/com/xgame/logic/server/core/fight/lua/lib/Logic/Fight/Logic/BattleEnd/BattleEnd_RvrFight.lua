BattleEnd_RvrFight = class(BattleEnd_Explorer)

function BattleEnd_RvrFight:ctor(battleDataManager , battleOverProccesser)

	self.battleEndTime = Config.rtsType.getConfig(BattleType.RVR_FIGHT).time; --Config.global.getNumber(500202);

	self.battleTime = 0;
	
	self.battleDataManager = battleDataManager;

	self.battleOverProccesser = battleOverProccesser;
	
	--print("##########################$$$$$$$$$$$$$$$$$$$$$!!!!!!!!!!!!22" , battleDataManager)
end	