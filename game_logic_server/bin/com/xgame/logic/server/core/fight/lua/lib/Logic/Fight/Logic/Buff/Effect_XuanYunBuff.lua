--ai类buff

Effect_XuanYunBuff = class(Buff);


function Effect_XuanYunBuff:ctor(buffCfgModel , owner , buffReleaser)
	 
end	

function Effect_XuanYunBuff:destroy()
	local topSuper = getTopSuper(self);

	if topSuper ~= nil then
	    topSuper.destroy(self);
	end

	self.owner:setPause(false);
end

--buff生效
function Effect_XuanYunBuff:innerActive()
	self.owner:setPause(true);	
end

