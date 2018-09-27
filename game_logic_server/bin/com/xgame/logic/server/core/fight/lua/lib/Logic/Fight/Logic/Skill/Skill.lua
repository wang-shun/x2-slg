Skill = class(Object)

function Skill:ctor(skillConfigModel)

	self.skillId = skillConfigModel.id;
 
	self.bulletSpeed = skillConfigModel.bulletSpeed; --炮弹飞行速度    伤害间隔时间 = 距离/炮弹速度

	self.attackRate = skillConfigModel.attackRate; --cd时间内的攻击频率  毫秒

	self.reloadTime = skillConfigModel.reloadTime; --弹药装填时间  CD时间  毫秒

	self.attackNum = skillConfigModel.attackNum; --每次cd内的最大攻击次数

	self.damageCD = skillConfigModel.damageCD; --持续伤害时间间隔  毫秒

	self.attackLen = skillConfigModel.attackLen; -- 攻击距离

	--如果是矩形 , 则以攻击者为起点 , 攻击距离为长度, 算一条线;  如果是扇形 , 则以攻击者为圆心 , 攻击距离为半径取一个格子组成的三角形块组; 如果是圆形的话 , 则以子弹落点为圆心算一个圆
	self.areaType = skillConfigModel.areaType; --伤害区域类型 , 如果不为0 , 则是范围攻击 , 非范围攻击的, 如果目标 为null 则终止攻击行为 1 矩形 , 2扇形 , 3 圆形

	if skillConfigModel.isGuide == 0 then --是否制导 , 如果不制导 , 那么伤害计算是攻击时间达到时(距离/速度) , 运动是锁定的开始时确定的坐标 , 而不是人 ;  如果是制导类型的 , 那么伤害计算是在子弹到达后 , 根据当前子弹位置以及攻击范围和范围类型 , 看看哪些会是我的目标, 给其加buff , 锁定的是人
		self.isGuide = false;
	else
		self.isGuide = true;	
	end	
 

	if skillConfigModel.stopAttackAfterFirstImpact == 0 then-- 首次攻击后是否结束攻击 , 用于阻挡式攻击, 打到第一个人后就消失
		self.stopAttackAfterFirstImpact  = false;
	else
		self.stopAttackAfterFirstImpact  = true;
	end		
	 
	self.shootStartDuration = skillConfigModel.shootStartDuration;--技能的发起时长 发起动作和 发起特效 毫秒
   
	self.shootProcessDuration = skillConfigModel.shootProcessDuration; --技能伤害持续时间. shootStartDuration + shootProcessDuration +shootEndDuration 的总和时间到了后 , 技能要结束  毫秒  比如 往地上扔一个火圈
   
	self.shootFinishDuration = skillConfigModel.shootFinishDuration;--技能的结束时长  结束动作播放 毫秒

	self.lifeTimeAddtive = skillConfigModel.lifeTimeAddtive; --技能总时长增量 , 在shootStartDuration + shootProcessDuration +shootEndDuration 的总和时间 的基础上增加 , 用于受体特效展现等

	if skillConfigModel.shootAfterStart == 0 then--技能 射击运动(Process) 是否依赖发起结束
		self.shootAfterStart = false;
	else
		self.shootAfterStart = true;
	end		
	 
	self.effectGun=skillConfigModel.effectGun;	--枪口特效
	self.effectFly=skillConfigModel.effectFly;	--飞行特效
	self.effectSpecial=skillConfigModel.effectSpecial;	--特殊特效。
	--self.fxShoot = skillConfigModel.fxShoot;--飞行特效 

	self.effectHit = skillConfigModel.effectHit;--受体特效 

	self.mainWeaponShootInterval = skillConfigModel.mainWeaponShootInterval;--有多个主武器时 , 主武器的开始攻击间隔时间 , 毫秒

	self.shootSound = skillConfigModel.shootSound;

 	self.hitSound = skillConfigModel.hitSound;





 	self.skillLV = skillConfigModel.skillLV;

 	self.skillGroupId = skillConfigModel.skillGroupId;

 	self.releaseType = skillConfigModel.releaseType;

 	self.hitType = skillConfigModel.hitType;

 	self.value1 = skillConfigModel.value1;

 	self.value2 = skillConfigModel.value2;

 	self.buffEnemy = skillConfigModel.buffEnemy;

 	self.buffFriend = skillConfigModel.buffFriend;

 	self.buffSelf = skillConfigModel.buffSelf;

 	self.buffMaster = skillConfigModel.buffMaster;

 	self.nextCfgSkill = skillConfigModel.nextSkill;

 	self.nextSkillList = nil;

 	self.isRoot = false; --是否是技能list里的第一个技能 , 攻击动作只和 第一个技能有关

 	self.isBait = skillConfigModel.isBait;

 	self.isBlock = skillConfigModel.isBlock;
end