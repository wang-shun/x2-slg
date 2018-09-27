BuffConfigModel = class(Object);

function BuffConfigModel:ctor(t_data)
	self.buffId = tonumber(t_data.Id);
	
	--buff效果类型（1：属性变化类,2:触发被动技能类，3：改变AI行为类，4特殊效果类:如控制类buff)
	self.buffEffectType = tonumber(t_data.buffEffectType);

	--buff组（用于判断相同组的buff效果是否可叠加）
	self.buffGroup = tonumber(t_data.buffGroup);

	--属填属性id(血量：106，命中：115，闪避：116)
	self.attrId = tonumber(t_data.attrId);

	--属性变化的数值(百分比+固定点数  id 1110的buff 填入 100;0  表示对目标造成100%*自己的属性+0点的伤害)
	self.attrValue = t_data.attrValue;

	self.attrValue1 = 0;

	self.attrValue2 = 0;

	if not IsNilOrEmpty(self.attrValue) then
		local attrValues = StrSplit(self.attrValue , ";");
		self.attrValue1 = tonumber(attrValues[1]);
		self.attrValue2 = tonumber(attrValues[2]);
	end	

	--增减益（1 增 2 减）  
	self.zengJian = tonumber(t_data.zengJian);

	--buff触发几率 （Value1+Value2*SkillLv，填50；10  表示  50%+10%*技能等级）
	local happenValue = StrSplit(t_data.happenValue , ";");

	self.happenValue1 = tonumber(happenValue[1]);

	self.happenValue2 = tonumber(happenValue[2]);
	
	--持续时间（只用于持续类buff，非持续类buff填-1）
	self.continueTime = tonumber(t_data.continueTime);

	--持续时间内有效次数（只用于持续类buff，非持续类buff填-1）
	self.validNum = tonumber(t_data.validNum);

	--触发间隔（只用于持续类buff，非持续类buff填-1）
	self.happenFrequnce = tonumber(t_data.happenFrequnce);

	--相同buff组最大可以叠加次数(新buff直接替换老buff) （同一个来源）
	self.coverNum = tonumber(t_data.coverNum);

	--叠加类型（1 效果 2 时间)
	self.addType = tonumber(t_data.addType);

	--BUFF图标
	self.buffPicture = t_data.buffPicture;

	--出生特效
	self.startEffect = t_data.startEffect;
	
	--相同buff组是否可共存(不同来源)
	local _coexist = tonumber(t_data.coexist);

	if _coexist == 1 then
		self.coexist = true;
	else
		self.coexist = false;
	end		
end	