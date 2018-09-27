BattleEnd_MonsterInvasion = class(BattleEnd_Explorer)

function BattleEnd_MonsterInvasion:ctor(battleDataManager , battleOverProccesser)

	self.battleEndTime = Config.rtsType.getConfig(BattleType.MONSTER_INVASION).time; --Config.global.getNumber(500202);

	self.battleTime = 0;
	
	self.battleDataManager = battleDataManager
	
	self.battleOverProccesser = battleOverProccesser;
	--print("##########################$$$$$$$$$$$$$$$$$$$$$!!!!!!!!!!!!22" , battleDataManager)
end	