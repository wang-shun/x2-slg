BattleEnd_BossFight = class(BattleEnd_Explorer)

function BattleEnd_BossFight:ctor(battleDataManager , battleOverProccesser)

	self.battleEndTime = Config.rtsType.getConfig(BattleType.BOSS_FIGHT).time; --Config.global.getNumber(500202);

	self.battleTime = 0;
	
	self.battleDataManager = battleDataManager;

	self.battleOverProccesser = battleOverProccesser;
	
	--print("##########################$$$$$$$$$$$$$$$$$$$$$!!!!!!!!!!!!22" , battleDataManager)
end	