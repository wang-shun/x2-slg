BattleEnd_Territory = class(BattleEnd_Explorer)

function BattleEnd_Territory:ctor(battleDataManager , battleOverProccesser)

	self.battleEndTime = Config.rtsType.getConfig(BattleType.TERRITORY).time; --Config.global.getNumber(500202);

	self.battleTime = 0;
	
	self.battleDataManager = battleDataManager;

	self.battleOverProccesser = battleOverProccesser;
	
	--print("##########################$$$$$$$$$$$$$$$$$$$$$!!!!!!!!!!!!22" , battleDataManager)
end	