TankEntity = class(TroopEntity);


function TankEntity:ctor()
	self.ClassName = "TankEntity";

	print("创建兵种   :::::::::::::    TankEntity")
end	

function TankEntity:initializeAI()
	self.troopAI = TankAI.new(self);
	self.entityAI = self.troopAI;
end

function TankEntity:visitStep(timeDelta)
 
	self.super.visitStep(self,timeDelta);
end	