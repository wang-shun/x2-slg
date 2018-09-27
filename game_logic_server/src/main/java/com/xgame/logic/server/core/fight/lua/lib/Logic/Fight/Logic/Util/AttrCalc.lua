------------战斗相关的属性数值计算类
AttrCalc = {};

local this = AttrCalc;

this.GM_AttackScale = 100; --最终伤害缩放倍数 , 用于调试

--计算命中率

--命中类型（1 战斗属性计算 2 固定命中率(数值读v1和v2 最终数值除10000) 3 特殊）
--V1技能概率参数，没有则填0
--V2技能概率参数，没有则填0

--命中率=(V1+V2*技能LV) 对固定命中率来说
--命中率=命中等级/（命中等级+对手闪避等级*参数）【分母为零时，分母=1】  对于走属性来说
--公式模型:命中等级/max(（命中等级+对手闪避等级*参数，1）
-- return  true  命中 ,  false 未命中
function AttrCalc.calcHitRate(attacker , defender, skill)
        if skill.hitType == 1 then
        	--战斗属性计算 , 这里得到的是0~1 的值 需要乘以 100 , 再用0~100随机
        	local hitRate = attacker.model:getAttrHit() / Mathf.Max((attacker.model:getAttrHit() + defender.model:getAttrDodge() * Config.global.getNumber(500702)),1);

        	hitRate = hitRate * 100;

        	local randomValue = Mathf.RandomBetween(0 , 100);

        	--print("计算命中率 走属性 : hitRate=" , hitRate ,  "randomValue=" , randomValue);

        	if randomValue <= hitRate then
        		return true;
        	end
        	
        	return false;	

        elseif skill.hitType == 2 then
        	--固定命中率 , 这里的值是 0 ~ 100 的 , 直接判断
        	local hitRate = skill.value1 + skill.value2 * skill.skillLV;

        	local randomValue = Mathf.RandomBetween(0 , 100);

        	print("计算命中率 走固定数值 : hitRate=" , hitRate ,  "randomValue=" , randomValue);

        	if randomValue <= hitRate then
        		return true;
        	end
        	
        	return false;
        else
        	--特殊

        	return true;
        end
end

------------------获得锁敌时间 , 秒  索敌时间=参数/(雷达强度*对手车体半径)   【分母为零时，分母=1】
--公式模型:索敌时间=(参数*min(雷达强度*对手车体半径,1))/max(雷达强度*对手车体半径,1
function AttrCalc.GetFocusTime(selfEntity , targetEntity)
    --print("计算索敌时间 : " , Config.global.getNumber(500705)  , selfEntity.model:getAttrRadarIntensity() , selfEntity.model:getAttrRadius() , selfEntity.ClassName);

    local v = selfEntity.model:getAttrRadarIntensity() * targetEntity.model:getAttrRadius(); --不为0的话, 肯定大于1

    return Config.global.getNumber(500705) * Mathf.Min(v , 1) / Mathf.Max(v,1);
end

----获得索敌数量
function AttrCalc.GetFocusNum(selfEntity) 
        return selfEntity.model:getAttrSeekingNum();
end

----通过buff触发几率 判断buff是否可增加
function AttrCalc.canAddBuff(buff , skill)
    local rate = buff:getModelBuff().happenValue1 + buff:getModelBuff().happenValue2 * skill.skillLV;

    if Mathf.RandomBetween(0 , 100) <= rate then
        return true;
    end
    
    return false;    
end    

--暴击概率=释放者暴击等级/（释放者暴击等级+对手韧性等级*参数） 【分母为零时，分母=1】 0~1
--公式模型:暴击等级/max(（暴击等级+对手韧性等级*参数）,1)
function AttrCalc.isCrit(buffOwner , buffReleaser)
    local rate = buffReleaser.model:getAttrCritical() / Mathf.Max((buffReleaser.model:getAttrCritical() + buffOwner.model:getAttrToughness() * Config.global.getNumber(500704)),1);

    if Mathf.RandomBetween(0 , 1) <= rate then
        return true;
    end
    
    return false;    
end    

--计算实际属性伤害(电磁伤害 + 动能伤害 + 热能伤害 + 激光伤害)
--实际属性伤害=属性伤害^2/(属性伤害+对手属性抗性*参数) 【分母为零时，分母=1】
--公式模型:属性伤害^2/max((属性伤害+对手属性抗性*参数),1)
function AttrCalc.calcAttrDamage(buffOwner , buffReleaser)

    --电磁伤害
    local dianCiAttrDamage = Mathf.Pow(buffReleaser.model:getAttrElectricityDamage() , 2) / Mathf.Max((buffReleaser.model:getAttrElectricityDamage() + buffOwner.model:getAttrElectricityDefense() * Config.global.getNumber(500701)),1);

    --动能伤害
    local dongNengAttrDamage = Mathf.Pow(buffReleaser.model:getAttrEnergyDamage() , 2) / Mathf.Max((buffReleaser.model:getAttrEnergyDamage() + buffOwner.model:getAttrEnergyDefense() * Config.global.getNumber(500701)),1);

    --热能伤害
    local reNengAttrDamage = Mathf.Pow(buffReleaser.model:getAttrHeatDamage() , 2) / Mathf.Max((buffReleaser.model:getAttrHeatDamage() + buffOwner.model:getAttrHeatDefense() * Config.global.getNumber(500701)),1);

    --激光伤害
    local jiGuangAttrDamage = Mathf.Pow(buffReleaser.model:getAttrLaserDamage() , 2) / Mathf.Max((buffReleaser.model:getAttrLaserDamage() + buffOwner.model:getAttrLaserDefense() * Config.global.getNumber(500701)),1);

    local finalAttrDamage = dianCiAttrDamage + dongNengAttrDamage + reNengAttrDamage  + jiGuangAttrDamage;

    return finalAttrDamage;
end 

--实际物理伤害=物理伤害^2*(物理伤害+对手防御*参数+) 【分母为零时，分母=1】
--公式模型:物理伤害^2/max((物理伤害+对手防御*参数),1)
function AttrCalc.calcRealPhyhicsDamage(buffOwner , buffReleaser)
       local v = Mathf.Pow(buffReleaser.model:getAttrAttack() , 2) / Mathf.Max((buffReleaser.model:getAttrAttack() + buffOwner.model:getAttrDefend() * Config.global.getNumber(500700)),1);
       
       print("--------物理伤害 " , buffReleaser.model:getAttrAttack() , v)
       return v;
end   

--计算106的减益 最终伤害
function AttrCalc.calcFinalHPDamage(buff , buffOwner , buffReleaser , skill)
    local critValue = 0; --暴击参数

    if  AttrCalc.isCrit(buffOwner , buffReleaser) then
        critValue = 1;
    end

    local attrDamage = AttrCalc.calcAttrDamage(buffOwner , buffReleaser);--实际属性伤害(电磁伤害 + 动能伤害 + 热能伤害 + 激光伤害)
    
    --伤害= (attrValue1 / 100) *（实际物理伤害+实际属性伤害）*（1+兵种克制参数）*（attackRate /1000）*（1+暴击伤害倍数*暴击参数）+ attrValue2
    local damage = (buff.buffCfgModel.attrValue1 / 100) * (AttrCalc.calcRealPhyhicsDamage(buffOwner , buffReleaser) + attrDamage) * (1 + AttrCalc.getTroopKeZhiValue(buffOwner , buffReleaser)) * (skill.attackRate / 1000) * (1 + AttrCalc.getCritDamageScale(buffReleaser) * critValue) + buff.buffCfgModel.attrValue2;
 
    damage = damage * Mathf.Ceil(buffReleaser:getHealth() / buffReleaser:getSingleHealth());
    
    print("106 伤害11 " , damage ,  AttrCalc.getTroopKeZhiValue(buffOwner , buffReleaser),attrDamage, skill.skillId,critValue , AttrCalc.getCritDamageScale(buffReleaser)  ,  Mathf.Ceil(buffReleaser:getHealth() / buffReleaser:getSingleHealth()) , attrDamage , AttrCalc.calcRealPhyhicsDamage(buffOwner , buffReleaser)  ,  AttrCalc.getTroopKeZhiValue(buffOwner , buffReleaser) , skill.attackRate , AttrCalc.getCritDamageScale(buffReleaser))

    return damage * this.GM_AttackScale;
end    

--暴击伤害倍数=暴击伤害等级/参数
function AttrCalc.getCritDamageScale(buffReleaser)
    local critDamageScale = buffReleaser.model:getAttrCritical() / Config.global.getNumber(500703);

    return critDamageScale;
end

--获得兵种克制参数    大兵种（1履带2轮式3战机）
function AttrCalc.getTroopKeZhiValue(buffOwner , buffReleaser)
    if buffReleaser.troopAssmblyType == 1 and buffOwner.troopAssmblyType == 1 then
        return Config.global.getNumber(500801);--坦克攻击坦克
    elseif buffReleaser.troopAssmblyType == 1 and buffOwner.troopAssmblyType == 2 then
        return Config.global.getNumber(500802);--坦克攻击战车
    elseif buffReleaser.troopAssmblyType == 1 and buffOwner.troopAssmblyType == 3 then
        return Config.global.getNumber(500803);--坦克攻击飞机
    elseif buffReleaser.troopAssmblyType == 2 and buffOwner.troopAssmblyType == 1 then
        return Config.global.getNumber(500804);--战车攻击坦克
    elseif buffReleaser.troopAssmblyType == 2 and buffOwner.troopAssmblyType == 2 then
        return Config.global.getNumber(500805);--战车攻击战车
    elseif buffReleaser.troopAssmblyType == 2 and buffOwner.troopAssmblyType == 3 then
        return Config.global.getNumber(500806);--战车攻击飞机
    elseif buffReleaser.troopAssmblyType == 3 and buffOwner.troopAssmblyType == 1 then
        return Config.global.getNumber(500807);--飞机攻击坦克
    elseif buffReleaser.troopAssmblyType == 3 and buffOwner.troopAssmblyType == 2 then
        return Config.global.getNumber(500808);--飞机攻击战车
    elseif buffReleaser.troopAssmblyType == 3 and buffOwner.troopAssmblyType == 3 then
        return Config.global.getNumber(500809);--飞机攻击飞机
    else
       return Config.global.getNumber(500800);    
    end
end    