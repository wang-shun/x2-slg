BattleEnd_TeamAttack = class(BattleEnd_Explorer)

function BattleEnd_TeamAttack:ctor(battleDataManager , battleOverProccesser)

	self.battleEndTime = Config.rtsType.getConfig(BattleType.TEAM_ATTACK).time; --Config.global.getNumber(500202);

	self.battleTime = 0;
	
	self.battleDataManager = battleDataManager;

	self.battleOverProccesser = battleOverProccesser;
	
	--print("##########################$$$$$$$$$$$$$$$$$$$$$!!!!!!!!!!!!22" , battleDataManager)
end	