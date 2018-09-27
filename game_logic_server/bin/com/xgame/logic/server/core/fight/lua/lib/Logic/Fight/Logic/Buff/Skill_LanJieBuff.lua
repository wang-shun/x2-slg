Skill_LanJieBuff = class(Buff);

function Skill_LanJieBuff:ctor(buffCfgModel , owner , buffReleaser)
	self.attrValue = 0; 
end	

function Skill_LanJieBuff:destroy()
	local topSuper = getTopSuper(self);

	if topSuper ~= nil then
	    topSuper.destroy(self);
	end
end

--外部触发 , 用于被动技能buff
function Skill_LanJieBuff:extActive(skill,skillShooter)
	self.super.extActive(self,skill,skillShooter);
	if self.canActive and self:canPassiveBehavior() and skill.isBlock then
		self.canActive = false;
		self:SetBit(BuffConst.BIT_BUFF_YOUER);
		self:SetBit(BuffConst.BIT_BUFF_ACTIVE);
		self:updateTriggerTime();	
		self.activeCount = self.activeCount + 1;
	end	
end	

--buff生效
function Skill_LanJieBuff:innerActive()
	self.canActive = true;
end