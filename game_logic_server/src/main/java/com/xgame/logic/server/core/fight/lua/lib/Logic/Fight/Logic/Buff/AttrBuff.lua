--属性buff

AttrBuff = class(Buff);

function AttrBuff:ctor(buffCfgModel , owner , buffReleaser)
	self.attrValue = 0; 
end	

function AttrBuff:destroy()
	local topSuper = getTopSuper(self);

	if topSuper ~= nil then
	    topSuper.destroy(self);
	end

	print(">>>>>>>>>>>buff消失时>>>>>>>>>>>>>异动属性:" ,self:getModelBuff().attrId, self.attrValue)
	--buff消失时, 增加的非106属性值 扣除
	if self:getModelBuff().attrId == AttrDefine.hit then
		self.owner.model:addHitExt(-self.attrValue);
	elseif self:getModelBuff().attrId == AttrDefine.dodge then
		self.owner.model:addDodgeExt(-self.attrValue);	
	end
end

--buff生效
function AttrBuff:innerActive()
	if self:getModelBuff().attrId == AttrDefine.hp then
		if self:getModelBuff().zengJian == 2 then --只有属性等于106 且是减益时才关注暴击

			local damage = AttrCalc.calcFinalHPDamage(self , self.owner , self.buffReleaser , self.skill);
			
			local newHp = self.owner:getHealth() - damage;

			print("扣血 剩余血量 : " , newHp , damage)

			self.owner:setHealth(newHp , self.buffReleaser);
		elseif self:getModelBuff().zengJian == 1 then --增益 加血
			local addHp = (self:getModelBuff().attrValue1 / 100) * self.owner:getHealth() + self:getModelBuff().attrValue2;

			local newHp = self.owner:getHealth() + addHp;

			print("加血 剩余血量 : " , newHp , addHp)

			self.owner:setHealth(newHp , self.buffReleaser);
		end
	elseif self:getModelBuff().attrId == AttrDefine.hit then
		--命中
		self.attrValue = (self:getModelBuff().attrValue1 / 100) * self.owner.model:getAttrBaseHit() + self:getModelBuff().attrValue2;
		 
 		print(self:getModelBuff().attrValue1 , self:getModelBuff().attrValue2 ,  self.owner.model:getAttrBaseHit() , self.owner.id)
 		if self:getModelBuff().zengJian == 2 then
			self.attrValue = -self.attrValue;
		end

		print(">>>>>>>>>>>>>>>>>>>>>>>>异动命中:" , self.attrValue ,  self.owner.id)
		self.owner.model:addHitExt(self.attrValue);	
				
	elseif self:getModelBuff().attrId == AttrDefine.dodge then--烟雾弹
		--闪避
		self.attrValue = (self:getModelBuff().attrValue1 / 100) * self.owner.model:getAttrBaseDodge() + self:getModelBuff().attrValue2;
 		
 		if self:getModelBuff().zengJian == 2 then
			self.attrValue = -self.attrValue;
		end
		
		print(">>>>>>>>>>>>>>>>>>>>>>>>异动闪避:" , self.attrValue)
		self.owner.model:addDodgeExt(self.attrValue);	
	end
end
