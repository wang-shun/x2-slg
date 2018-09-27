BattleEnd_Explorer = class(Object)

function BattleEnd_Explorer:ctor(battleDataManager , battleOverProccesser)

	self.battleEndTime = Config.rtsType.getConfig(BattleType.EXPLORER).time; --Config.global.getNumber(500202);

	self.battleTime = 0;
	
	self.battleDataManager = battleDataManager;

	self.battleOverProccesser = battleOverProccesser;

	self.isAttackerAllOver = true;

	self.isDefenAllOver = true;
	
	--print("##########################$$$$$$$$$$$$$$$$$$$$$!!!!!!!!!!!!22" , battleDataManager)
end	

function BattleEnd_Explorer:checkBattleOverTime(board , timeDelta)
	print(">>>>>>>>>>>>>>  BattleEnd_Explorer.checkBattleOverTime " , self.battleTime , self.battleEndTime)
	
	self.battleTime = self.battleTime  + timeDelta;

	if self.battleTime > self.battleEndTime then
		self:doBattleOver(board);	
	end
end	

function BattleEnd_Explorer:doBattleOver(board)
	local isWin = true;

	if self.isAttackerAllOver and not self.isDefenAllOver then
		isWin = false;
	elseif not self.isAttackerAllOver and self.isDefenAllOver then	
		isWin = true;
	elseif not self.isAttackerAllOver and not self.isDefenAllOver then
		isWin = false;
	else 
		isWin = false;
	end		

	self.battleDataManager:battleOver(isWin);
end	

--处理战斗结束
function BattleEnd_Explorer:checkBattleOver(board , allBuildings)


	local isBattleOver = false;

	--print(">>>>>>>>>>>>>>  sss22 " , self.battleTime , self.battleEndTime)
	local allEntityNum = board.allStaticEntitys:getSize();
		
	for i = 1 , allEntityNum , 1 do
		local current = board.allStaticEntitys[i];
		
		if current.entityType == EntityType.Troop then
			if current.team == EntityTeam.Defender then
				self.isDefenAllOver = self.isDefenAllOver and current:isDead();
			elseif  current.team == EntityTeam.Attacker then	
				self.isAttackerAllOver = self.isAttackerAllOver and current:isDead();
			end
		end
	end

	if self.isDefenAllOver then
		isBattleOver = true;
	elseif self.isAttackerAllOver and self.battleDataManager:isEmpty() then
		isBattleOver = true;
	end	
	--print(">>>>>>>>>>>>>>  sss" , isBattleOver)
	if isBattleOver then
		--战斗结束后，有存活兵种的一方获胜，若双方都有存活士兵，则防守方胜利。

		self:doBattleOver(board);
	end	
end	