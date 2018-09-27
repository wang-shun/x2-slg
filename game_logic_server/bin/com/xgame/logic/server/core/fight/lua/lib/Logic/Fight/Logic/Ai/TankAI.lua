TankAI = class(TroopAI)

function TankAI:ctor()
	self.ClassName = "TankAI";
end	

function TankAI:stepAI(entityTime)
 	if self.entityOwner:isDead() then 
 		return;
 	end
 	self.super.stepAI(self,entityTime);
end	

function TankAI:initMovement(v)
    self.movement = MovementTank.new();
end    