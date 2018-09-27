AttackTargetCommand = class(AICommand)

function AttackTargetCommand:ctor(troopOwner)
	self.ClassName = "AttackTargetCommand";

	-- print("create**************AttackTargetCommand************")
end	

function AttackTargetCommand:init()
	self.super.init(self);	 
end	

function AttackTargetCommand:isFinished()
	return self.troopOwner:isDead() or self.troopOwner:getAI():getCurrentTarget() == nil or self.troopOwner:getAI():getCurrentTarget():isDead();
end

function AttackTargetCommand:stepCommand()
           -- print("step**************AttackTargetCommand************"  , self.troopOwner:getAI():targetAliveAndInShortRange() , self.troopOwner:getAI():isMoving())
	if self:isFinished() then
		return;
	end

	if self.troopOwner:getAI():targetAliveAndInShortRange() then
		CommandTool.HandleTargetInRange(self.troopOwner);
		return;
	elseif not self.troopOwner:isDead() and not self.troopOwner:getAI():isMoving() then
		local newCommand2 = AttackApproachCommand.new(self.troopOwner);
		self.troopOwner:getAI():pushCommand(newCommand2);
	end
end	

function AttackTargetCommand:onCommandFinished()
	--warn("**AttackTargetCommand*")
	--print("destroy**************AttackTargetCommand************")
	 
end	
