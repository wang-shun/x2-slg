--Buff 基础类
Buff = class(Object); 


function Buff:ctor(buffCfgModel , owner , buffReleaser , skill , weapon)

	self.weapon = weapon;

	self.buffCfgModel = buffCfgModel;

	self.owner = owner;

	self.buffReleaser = buffReleaser;

	self.coverNum = 0;--buff 叠加次数 , 默认0

	self.effectiveMultiple = 1; --效果倍数 , 如果buff叠加的话, 该值会增大

	self.lifeTime = 0;--失效时间

	self.triggerTime = 0; --下次触发时间

	self.buffTime = 0;

	self.gameplayBitfield = 0;

	self.skill = skill;
	
	self.activeCount = 0; --buff生效次数 , 至少生效一次

	self.validNum = self.buffCfgModel.validNum;

	self.validNum = self.validNum == -1 and 1 or self.validNum;

	self.happenFrequnce = self.buffCfgModel.happenFrequnce / 1000;

	self.happenFrequnce = self.happenFrequnce < 0 and 0 or self.happenFrequnce;

	self.continueTime = self.buffCfgModel.continueTime / 1000;

	self.continueTime = self.continueTime < 0 and 0 or self.continueTime;

	self:initBuffTime();


	self.skillShooter=nil;			--被动buff技能的发射器。
	--print("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH   " , self.buffCfgModel.continueTime)
end

--是否能有被动行为
function Buff:canPassiveBehavior()
	--[[
	if self.owner:isPause() then
	    return false;
	end    

	return true;
	]]

	return true;--芳总说 有眩晕类的buff 也不影响其他buff生效
end	

function Buff:initBuffTime()

	if self.continueTime ~= 0 then
		self.lifeTime = self.continueTime + self.buffTime;
	end	

	--self:updateTriggerTime();	
end

function Buff:ClearAllBit()
	--print(">>>>>>>>>>>>>>>>>>>>>>>>buff  ClearAllBit")
	self:ClearBit(BuffConst.BIT_BUFF_START);
	self:ClearBit(BuffConst.BIT_BUFF_DESTROY);
	self:ClearBit(BuffConst.BIT_BUFF_ACTIVE);
end	

function Buff:updateTriggerTime()
	if self.happenFrequnce ~= 0 then
		self.triggerTime = self.happenFrequnce + self.buffTime;
	end
end

function Buff:destroy()
	self:SetBit(BuffConst.BIT_BUFF_DESTROY);
end

function Buff:step(deltaTime)
	self.buffTime = self.buffTime + deltaTime;

	if self.buffTime >= self.triggerTime and self.validNum > self.activeCount then
		self:active(); --buff生效
	end	
end

--外部触发 , 用于被动技能buff
function Buff:extActive(skill,skillShooter)
	self.skillShooter=skillShooter;
end	

--buff执行
function Buff:active()
	if self.validNum > self.activeCount then
		--print(">>>>>>>>>>>>buff生效<<<<<<<<<<<<<< " , self.buffCfgModel.buffId , self.owner.id)
		self.activeCount = self.activeCount + 1;
		self:SetBit(BuffConst.BIT_BUFF_ACTIVE);
		self:updateTriggerTime();	
		self:innerActive();
	end
end

function Buff:innerActive()
end	

function Buff:isEnd()
	--print(">>>>>>>>>>>>buff isEnd<<<<<<<<<<<<<< " , self.buffCfgModel.buffId , self.owner.id , self.buffTime ,self.lifeTime , self.activeCount)
	if self.buffTime >= self.lifeTime and self.activeCount >= 1 then
		return true;
	end	

	--if self.validNum <= self.activeCount then
	--	return true;
	--end
		
	return false;
end

function Buff:getModelBuff()
	return self.buffCfgModel;
end

function Buff:getBuffMaster()
	return self.owner;
end

function Buff:getCoverNum()
	return self.coverNum;
end

--增加效果叠加数量
function Buff:increaseEffectiveMultiple()
	self.effectiveMultiple = self.effectiveMultiple + 1;
end	

--减少效果叠加数量
function Buff:reduceEffectiveMultiple()
	self.effectiveMultiple = self.effectiveMultiple - 1;
end	

function Buff:getCoverNum()
	return self.coverNum;
end	

--叠加数量自曾
function Buff:increaseCoverNum()
	self.coverNum = self.coverNum + 1;
	--如果叠加数量大于1 的话, 表明有多个buff叠加 , 此时处理 叠加类型
	if self.coverNum > 1 then
		if self:getModelBuff().addType == 1 then
			--效果叠加
			self:increaseEffectiveMultiple();

			self.lifeTime = self.continueTime + self.buffTime; --时间重置

			--print(">>>>>效果叠加" , self.effectiveMultiple)
		elseif self:getModelBuff().addType == 2 then
			--时间叠加
			self.lifeTime = self.lifeTime + self.continueTime; ---累加

			--print(">>>>>时间叠加" , self.lifeTime)
		end
	end		
end


function Buff:SetBit(bit)
        self.gameplayBitfield = Bit._or( self.gameplayBitfield , Bit._lshift(1,bit));
end

function Buff:IsBitSet(bit)
        return   Bit._and(self.gameplayBitfield , Bit._lshift(1,bit)) ~= 0;      
end

function Buff:ClearBit(bit)
        self.gameplayBitfield  = Bit._and(self.gameplayBitfield ,  Bit._not(Bit._lshift(1,bit)));
end