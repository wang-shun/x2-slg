--ai类buff

Ai_ClearFocusEnemyBuff = class(Buff);


function Ai_ClearFocusEnemyBuff:ctor(buffCfgModel , owner , buffReleaser)
	 
end	

function Ai_ClearFocusEnemyBuff:destroy()
	local topSuper = getTopSuper(self);

	if topSuper ~= nil then
	    topSuper.destroy(self);
	end
end	

--buff生效
function Ai_ClearFocusEnemyBuff:innerActive()
	for i = 1 , self.owner.weaponList:getSize() , 1 do
		local weapon = self.owner.weaponList[i];
		weapon:clearAIFocusList();
	end
end
