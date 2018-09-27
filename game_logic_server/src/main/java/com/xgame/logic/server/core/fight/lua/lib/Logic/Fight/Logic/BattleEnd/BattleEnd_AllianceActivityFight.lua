BattleEnd_AllianceActivityFight = class(BattleEnd_Explorer)

function BattleEnd_AllianceActivityFight:ctor(battleDataManager , battleOverProccesser)

	self.battleEndTime = Config.rtsType.getConfig(BattleType.ALLIANCE_ACTIVITY_FIGHT).time; --Config.global.getNumber(500202);

	self.battleTime = 0;
	
	self.battleDataManager = battleDataManager
	
	self.battleOverProccesser = battleOverProccesser;
	--print("##########################$$$$$$$$$$$$$$$$$$$$$!!!!!!!!!!!!22" , battleDataManager)
end	