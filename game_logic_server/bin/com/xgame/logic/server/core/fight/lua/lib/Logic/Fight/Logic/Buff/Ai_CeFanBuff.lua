--ai类buff

Ai_CeFanBuff = class(Buff);


function Ai_CeFanBuff:ctor(buffCfgModel , owner , buffReleaser)
		 
end	

function Ai_CeFanBuff:destroy()
	local topSuper = getTopSuper(self);

	if topSuper ~= nil then
	    topSuper.destroy(self);
	end

	--[[
	self.owner:changeTeam();

	for i = 1 , self.owner.weaponList:getSize() , 1 do
		local weapon = self.owner.weaponList[i];
		weapon:clearTarget();
		weapon:clearAIFocusList();
	end
	]]
end

function Ai_CeFanBuff:doActive()
	--先清空索敌列表 , 和目标
	for i = 1 , self.owner.weaponList:getSize() , 1 do
		local weapon = self.owner.weaponList[i];
		weapon:clearAIFocusList();
		weapon:clearTarget();
	end	

	--改变宿主阵营
	self.owner:changeTeam();
end	

--buff生效
function Ai_CeFanBuff:innerActive()
	self:doActive();
end
