DianCiTaTowerDefenseCommand = class(AICommand)


function DianCiTaTowerDefenseCommand:ctor(troopOwner)
	self.ClassName = "DianCiTaTowerDefenseCommand";

	 print("**************DianCiTaTowerDefenseCommand************")
end	

function DianCiTaTowerDefenseCommand:init()
	self.super.init(self);	 
end	

function DianCiTaTowerDefenseCommand:startCommand()
	self.super.startCommand(self);	 
end	

function DianCiTaTowerDefenseCommand:stepCommand() 
	--print("**************DianCiTaTowerDefenseCommand:stepCommand************" , self.troopOwner:getAI():hasNoFireTarget())

	if self.troopOwner:getAI():getCurrentTarget() == nil then
		local curTarget = self.troopOwner.mainWeapon.weapomAI:findTarget();
		if curTarget ~= nil then
			self.troopOwner:getAI():setMarkTarget(curTarget);
		end
	end
			
	self.troopOwner:getAI():fire();
end


function DianCiTaTowerDefenseCommand:pauseCommand()
	print("**************DianCiTaTowerDefenseCommand:pauseCommand************")
end		

function DianCiTaTowerDefenseCommand:isFinished()
	return false;
end

function DianCiTaTowerDefenseCommand:onCommandFinished()
	print("**************DianCiTaTowerDefenseCommand:onCommandFinished************")
	self.super.onCommandFinished(self);	 
end	

function DianCiTaTowerDefenseCommand:resumeCommand()
	self.super.resumeCommand(self);	 
end	
