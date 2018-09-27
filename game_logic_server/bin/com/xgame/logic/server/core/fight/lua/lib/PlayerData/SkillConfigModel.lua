SkillConfigModel = class(Object);

function SkillConfigModel:ctor(t_data)

	self.id = tonumber(t_data["id"]); --技能id
	
	self.buffIds = StrSplit(t_data["buffId"] , ","); --buffid(可以填多个 , 逗号分隔)

	self.effectGun=t_data["effectGun"];	--枪口特效

	self.effectFly=t_data["effectFly"];	--飞行特效

	self.effectSpecial=t_data["effectSpecial"];	--特殊特效。

	self.effectHit = t_data["effectHit"];--受体特效 

	self.fxShoot = t_data["fxShoot"];--飞行特效

	self.fxHit = t_data["fxHit"];--受体特效路径

	self.bulletSpeed  = tonumber(t_data["bulletSpeed"]);--炮弹飞行速度   

	self.attackRate  = tonumber(t_data["attackRate"]);--cd时间内的攻击频率  毫秒

	self.reloadTime  = tonumber(t_data["reloadTime"]);--弹药装填时间  CD时间  毫秒

	self.attackNum  = tonumber(t_data["attackNum"]);--每次cd内的最大攻击次数

	self.damageCD  = tonumber(t_data["damageCD"]);--持续伤害时间间隔  毫秒 （填0表示不是持续伤害）

	self.attackLen  = tonumber(t_data["attackLen"]); --攻击距离（单位 格子）

	self.areaType =  tonumber(t_data["areaType"]);--伤害区域类型 , 如果为0则是单点攻击，如果不为0 , 则是填入的数值表示半径为N的圆形范围攻击 ( 非范围攻击的, 如果目标 为null 则终止攻击行为)(填入1.5表示对半径为1.5个格子的圆形为攻击半径 )

	self.isGuide  = tonumber(t_data["isGuide"]);--是否制导(0不自导，1自导) , 如果不制导 , 那么伤害计算是攻击时间达到时(距离/速度) , 运动是锁定的开始时确定的坐标 , 而不是人 ;  如果是制导类型的 , 那么伤害计算是在子弹到达后 , 
	--根据当前子弹位置以及攻击范围和范围类型 , 看看哪些会是我的目标, 给其加buff , 锁定的是人

	self.stopAttackAfterFirstImpact  = tonumber(t_data["stopAttackAfterFirstImpact"]);--首次攻击后是否结束攻击（0不结束，1结束） , 用于阻挡式攻击, 打到第一个人后就消失
 
	self.shootStartDuration  = tonumber(t_data["shootStartDuration"]);--技能的发起时长 发起动作和 发起特效 毫秒

	self.shootLoopDuration = tonumber(t_data["shootLoopDuration"]);--持续动作时长

	self.shootEndDuration = tonumber(t_data["shootEndDuration"]);--结束动作时长

---printColor("sssssssssssssssssssssss   " , self.shootLoopDuration , self.shootEndDuration)
	
	self.shootProcessDuration  = tonumber(t_data["shootProcessDuration"]); --技能伤害持续时间（填0表示没持续伤害）. shootStartDuration + shootProcessDuration +shootEndDuration 的总和时间到了后 , 技能要结束  毫秒  比如 往地上扔一个火圈

--	self.shootFinishDuration = tonumber(t_data["shootFinishDuration"]);--技能的结束时长  结束动作播放 毫秒

--	self.lifeTimeAddtive  = tonumber(t_data["lifeTimeAddtive"]);--技能总时长增量 , 在shootStartDuration + shootProcessDuration +shootEndDuration 的总和时间 的基础上增加 , 用于受体特效展现等

	self.shootAfterStart  = tonumber(t_data["shootAfterStart"]);--技能 射击运动(Process) 是否依赖发起结束（0不依赖，1依赖）

	self.mainWeaponShootInterval = tonumber(t_data["mainWeaponShootInterval"]); --有多个主武器时 , 主武器的开始攻击间隔时间 , 毫秒

	self.shootSound = t_data["shootSound"];--攻击音效(声音名;播放时长|声音名;播放时长)

	self.hitSound = t_data["hitSound"];--被击音效

	self.skillLV = tonumber(t_data["skillLV"]); --技能等级

	self.skillGroupId = tonumber(t_data["skillGroupId"]); --技能类型

	self.releaseType = tonumber(t_data["releaseType"]); --技能释放类型（1常规释放:获得目标后释放，2 出生即释放:无需目标(怪物用)）

	self.hitType = tonumber(t_data["hitType"]); -- 命中类型（1 战斗属性计算 2 固定命中率(数值读v1和v2 最终数值除10000) 3 特殊）

	self.value1 = tonumber(t_data["value1"]); --V1(V1+V2*技能LV)技能概率参数，没有则为空

	self.value2 = tonumber(t_data["value2"]); --V2技能概率参数，没有则为空

	self.buffEnemy = nil; --敌方（buff释放对象）

	if not IsNilOrEmpty(t_data["buffEnemy"]) then
		self.buffEnemy = StrSplit(t_data["buffEnemy"] , ";");
	end

	self.buffFriend = nil; --友方 buff , 不包括自己

	if not IsNilOrEmpty(t_data["buffFriend"]) then
		self.buffFriend = StrSplit(t_data["buffFriend"] , ";");
	end
	
	self.buffSelf = nil; --自己 buff 

	if not IsNilOrEmpty(t_data["buffSelf"]) then
		self.buffSelf = StrSplit(t_data["buffSelf"] , ";");
	end

	self.buffMaster = nil; --主人 buff 

	if not IsNilOrEmpty(t_data["buffMaster"]) then
		self.buffMaster = StrSplit(t_data["buffMaster"] , ";");
	end

	self.nextSkill = nil;     --后续技能ID（释放完当前技能后再释放后续技能）
	
	if not IsNilOrEmpty(t_data["nextSkill"]) then
		self.nextSkill = StrSplit(t_data["nextSkill"] , ";");
	end


	local numIsBlock = tonumber(t_data["isBlock"]);

	if  numIsBlock == 0 then
		self.isBlock = false;
	else
		self.isBlock = true;
	end		
 

	local numIsBait = tonumber(t_data["isBait"]);

	if numIsBait == 0 then
		self.isBait = false;
	else 
		self.isBait = true;
	end		

	self.shootSound = t_data["shootSound"]; --攻击音效(随机播放1个 声音名;声音名)

	self.hitSound = t_data["hitSound"]; --被击音效( 随机 兵种或建筑的材质类型：音效1,音效2；兵种或建筑的材质类型：音效1,音效2)1:mp4,mp5;2:mp6,mp7
end	