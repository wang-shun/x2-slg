FindTargetCommand = class(AICommand)

function FindTargetCommand:ctor(troopOwner)
	self.ClassName = "FindTargetCommand";

	self.MAX_COUNT = 3;

	self.count = 0;
	--print("create**************FindTargetCommand************")
	--troopOwner:getAI():requestTarget();
end	

function FindTargetCommand:init()
	self.super.init(self);	 
end	

function FindTargetCommand:isFinished()

	if self.troopOwner:getAI():getCurrentTarget() ~= nil then
		self.troopOwner:getAI():getMovement():stopMove();
		return true;
	elseif self.count > 3 then
		return true;
	end		

	return false;
end

function FindTargetCommand:stepCommand()
 	--print("step**************FindTargetCommand************")

	self.count = self.count + 1;

	self.super.stepCommand(self);	 

	self.troopOwner:getAI():checkTargetSearchResults();
end	

function FindTargetCommand:onCommandFinished()
--	print("destroy**************FindTargetCommand************")
	self.super.onCommandFinished(self);	 
end	
