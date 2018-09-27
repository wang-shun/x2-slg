AICommand = class(Object)


function AICommand:ctor(troopOwner)
	self.ClassName = "AICommand";
	self.troopOwner = troopOwner;
	self:init();
end	

function AICommand:init()
	 
end	

function AICommand:pauseCommand()
end	

function AICommand:startCommand()
end	

function AICommand:stepCommand()
end	

function AICommand:isFinished()
	return false;
end	

function AICommand:onCommandFinished()
end	

function AICommand:resumeCommand()
end	
