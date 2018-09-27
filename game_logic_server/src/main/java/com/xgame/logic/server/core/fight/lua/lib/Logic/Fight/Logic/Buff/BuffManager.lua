--buff管理类 , 每个entity有一个
BuffManager = class(Object);

function BuffManager:ctor(owner)
	self.ClassName = "BuffManager";

	self.owner = owner;

	self.buffTime = 0;

	self.buffList = List.new();

	self.delBuffList = List.new();

	self.buffMap = Map.new(); --key是buff组, value是 buffList
end

--在buffEnemy , buffFriend 中2选1
function BuffManager:buffSelector(skill , buffReleaser)
	if self.owner.team ~= buffReleaser.team then
		return skill.buffEnemy;
	elseif self.owner.team == buffReleaser.team then	
		--if buffReleaser.masterEntityId == self.owner.masterEntityId then
		--	return skill.buffMaster;
		--elseif buffReleaser.id == self.owner.id then
		--	return skill.buffSelf;
		--else
			return skill.buffFriend;	
		--end
	end

	return nil;		
end	

--添加buff时 , 处理自己和主人的buff
function BuffManager:addSelfAndMasterBuff(skill , buffReleaser , weapon)
	if not IsNilOrEmpty(skill.buffMaster) then
		local master = Board.getEntity(buffReleaser.masterEntityId);
		if master ~= nil then
			master:addExtBuff(skill , skill.buffMaster , buffReleaser , weapon);	
		end
	end
	
	if not IsNilOrEmpty(skill.buffSelf) then
		print("--------------增加自己buff" , #skill.buffSelf , skill.buffSelf[1])
		buffReleaser:addExtBuff(skill , skill.buffSelf , buffReleaser , weapon);		
	end	
end	

function BuffManager:extAddBuff(skill , buffStrid , buffReleaser , weapon)
	for i = 1 , #buffStrid , 1 do

		local buff = nil;
		
		local buffCfgModel = Config.buff.getConfig(tonumber(buffStrid[i]));

		print("buff id = " , buffStrid[i] , buffCfgModel , "skill id=" , skill.skillId)

		if buffCfgModel.buffGroup == BuffGroupConst.G_Attr_YanWuDan or buffCfgModel.buffGroup == BuffGroupConst.G_Attr_SingleDamage or buffCfgModel.buffGroup == BuffGroupConst.G_Attr_LastDamage or buffCfgModel.buffGroup == BuffGroupConst.G_Attr_AddHp or buffCfgModel.buffGroup == BuffGroupConst.G_Attr_AddHit then
			--属性类的buff
			buff = AttrBuff.new(buffCfgModel , self.owner , buffReleaser , skill , weapon);
		elseif buffCfgModel.buffGroup == BuffGroupConst.G_Skill_YouErDan then
			buff = Skill_YouErBuff.new(buffCfgModel , self.owner , buffReleaser , skill , weapon);
		elseif buffCfgModel.buffGroup == BuffGroupConst.G_Skill_LanJieDan then
			--被动技能类的buff
			buff = Skill_LanJieBuff.new(buffCfgModel , self.owner , buffReleaser , skill , weapon);
		elseif buffCfgModel.buffGroup == BuffGroupConst.G_Ai_ClearFocusEnemy then
			buff = Ai_ClearFocusEnemyBuff.new(buffCfgModel , self.owner , buffReleaser , skill , weapon);
		elseif buffCfgModel.buffGroup == BuffGroupConst.G_Ai_CeFan then
			buff = Ai_CeFanBuff.new(buffCfgModel , self.owner , buffReleaser , skill , weapon);
		elseif buffCfgModel.buffGroup == BuffGroupConst.G_Effect_XuanYun then
			buff = Effect_XuanYunBuff.new(buffCfgModel , self.owner , buffReleaser , skill , weapon);	
		end	

		if buff ~= nil then
			self:doAddBuff(buff , skill);
		end	
	end
end	

function BuffManager:addBuff(skill , buffReleaser , weapon)
	self:addSelfAndMasterBuff(skill , buffReleaser , weapon);

	local buffStrid = self:buffSelector(skill , buffReleaser); --这里只会处理 敌人和友方的buff

	if IsNilOrEmpty(buffStrid) then
		return;
	end	

	self:extAddBuff(skill , buffStrid , buffReleaser , weapon);	
end	

function BuffManager:doAddBuff(buff ,skill) 
	
	local newBuffModel = buff:getModelBuff();
	--print(newBuffModel.buffId , "~~~~~~~~~~~~~~~~~1~~~~~~~~~~~~~~~~~doAddBuff 111  " , buff.buffCfgModel.buffId)

	local canAddThisBuff = true;

	if newBuffModel.buffGroup ~= 5 and newBuffModel.buffGroup ~= 8 then
		for i = 1 , self.buffList:getSize() , 1 do
			local mBuff = self.buffList[i];
			local oldBuffModel = mBuff:getModelBuff();
			--print("doAddBuff 222  " , newBuffModel.coexist)
			--相同buff组不可共存(不同来源)
			--print(newBuffModel.buffId , "~~~~~~~~~~~2~~~~~~~~~~~~~~~~~~~~~~~doAddBuff 111  " , newBuffModel.coexist)
			if not newBuffModel.coexist then
				if buff:getBuffMaster() ~= mBuff:getBuffMaster() then
					if newBuffModel.buffGroup == mBuff.buffGroup then
						canAddThisBuff = false;
						break;
					end	
				end	
			end	
			--print(newBuffModel.buffId , "~~~~~~~~~~~3~~~~~~~~~~~~~~~~~~~~~~~doAddBuff 111  " , buff:getBuffMaster() == mBuff:getBuffMaster() ,canAddThisBuff )
			--print("doAddBuff 333  " , buff:getBuffMaster() == mBuff:getBuffMaster())
			--buff来源相同
			if buff:getBuffMaster() == mBuff:getBuffMaster() then
				--print(newBuffModel.buffId , "~~~~~~~~~~~4~~~~~~~~~~~~~~~~~~~~~~~doAddBuff 111  " , newBuffModel.coverNum ,canAddThisBuff )
				if newBuffModel.coverNum == 1 then
					--该buff不能叠加
					if newBuffModel.buffId == newBuffModel.buffId then
						canAddThisBuff = false;
						break;
					end	
				else
					--该buff已经达到最大叠加次数
					--print(newBuffModel.buffId , "~~~~~~~~~~~5~~~~~~~~~~~~~~~~~~~~~~~doAddBuff 111  " , mBuff:getCoverNum() , newBuffModel.coverNum ,canAddThisBuff )
					if oldBuffModel.buffId == newBuffModel.buffId then
						if mBuff:getCoverNum() >= newBuffModel.coverNum then
							canAddThisBuff = false;
							break;
						else

							buff = mBuff;
						end
					end
				end	
			end	
		end	
	end
	--print(buff.buffCfgModel.buffId , "~~~~~~~~~~~5~~~~~~~~~~~~~~~~~~~~~~~doAddBuff 111  " ,  canAddThisBuff )
	if canAddThisBuff then
		--print(buff.buffCfgModel.buffId , "~~~~~~~~~~~6~~~~~~~~~~~~~~~~~~~~~~~doAddBuff 111  " , AttrCalc.canAddBuff(buff , skill) )
		if AttrCalc.canAddBuff(buff , skill) then 
			--成功添加buff
			buff:increaseCoverNum();
			--print(buff.buffCfgModel.buffId , "~~~~~~~~~~~7~~~~~~~~~~~~~~~~~~~~~~~doAddBuff 111  " ,  buff:getCoverNum() )
			if buff:getCoverNum() == 1 then --首次增加的buff 才会加入buff列表
				self.buffList:add(buff);
				buff:SetBit(BuffConst.BIT_BUFF_START);
				if not self.buffMap:containsKey(buff.buffCfgModel.buffGroup) then
					self.buffMap:put(buff.buffCfgModel.buffGroup , List.new());
				end	
				self.buffMap[buff.buffCfgModel.buffGroup]:add(buff);
				--print("-------add buff buffid=" , buff.buffCfgModel.buffId , buff.buffCfgModel.buffGroup)
			end
		end
	end	
end		

function BuffManager:step(deltaTime)

	if self.delBuffList:getSize() > 0 then
		self.delBuffList:clear();
	end	

	self.buffTime = self.buffTime + deltaTime;

	for i = 1 , self.buffList:getSize() , 1 do
		if self.buffList[i]:isEnd() then
			self.delBuffList:add(self.buffList[i]);
		else
			self.buffList[i]:step(deltaTime);
		end	
	end

	for i = 1 , self.delBuffList:getSize() , 1 do

		self.delBuffList[i]:destroy();

		self.buffList:remove(self.delBuffList[i]);

		local groupId = self.delBuffList[i].buffCfgModel.buffGroup;

		local groupList = self.buffMap[groupId];

		groupList:remove(self.delBuffList[i]);

		print("-------remove buff buffid=" , self.delBuffList[i].buffCfgModel.buffId , "groupId=" , groupId);

		if groupList:getSize() == 0 then
			self.buffMap:remove(groupId);
		end	
	end	
end


