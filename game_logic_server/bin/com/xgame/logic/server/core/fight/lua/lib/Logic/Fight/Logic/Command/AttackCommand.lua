AttackCommand = class(AICommand)


function AttackCommand:ctor(troopOwner)
	self.ClassName = "AttackCommand";

	print("create**************AttackCommand************")
end	

function AttackCommand:init()
	self.super.init(self);	 
end	

function AttackCommand:startCommand()
	self.super.startCommand(self);	 
end	

function AttackCommand:stepCommand() 
	--print("**************AttackCommand:stepCommand************" , self.troopOwner:getAI():getCurrentTarget())
	if self.troopOwner:getAI():getCurrentTarget() == nil then
		local cmd = FindTargetCommand.new(self.troopOwner);
		 self.troopOwner:getAI():pushCommand(cmd);
	elseif not self.troopOwner:isDead() then
		if self.troopOwner:getAI():targetAliveAndInShortRange() then
			CommandTool.HandleTargetInRange(self.troopOwner);
			return;
		end
		
		local newCommand = AttackTargetCommand.new(self.troopOwner);
		self.troopOwner:getAI():pushCommand(newCommand);	
	end	 
end


function AttackCommand:pauseCommand()
	--print("**************AttackCommand:pauseCommand************")
end		

function AttackCommand:isFinished()
	return false;
end

function AttackCommand:onCommandFinished()
	self.super.onCommandFinished(self);	 
end	

function AttackCommand:resumeCommand()
	self.super.resumeCommand(self);	 
end	
