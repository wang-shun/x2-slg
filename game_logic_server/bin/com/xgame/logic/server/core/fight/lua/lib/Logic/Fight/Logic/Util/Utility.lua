Utility = {};

function Utility.SimToWorldSpace(simX, simY, simZ)
        return Vector3.New(simX, simY, simZ);
end

function Utility.SimToWorldSpaceV2(v, worldY)
         return Utility.SimToWorldSpace(v.x, v.y, worldY);
end

function Utility.SimFacingToWorldRotation(facingInDegrees)
	 
        return Quaternion.AngleAxis((FixedAngle.Degrees90 - facingInDegrees), Vector3.up);
end

function Utility.SimFacingToWorldRotationAxis(facingInDegrees , axis)
     
        return Quaternion.AngleAxis((facingInDegrees), axis);
end

function Utility.SimFacingToWorldRotationWithParent(facingInDegrees , parentAngles, axis)
 
        local angle = facingInDegrees;

        for i = 1 , parentAngles:getSize() , 1 do 
            angle = angle - parentAngles[i];
        end

        local eAngle = Vector3.zero;

        if Vector3.up == axis then
            eAngle.y = angle;
        elseif Vector3.left == axis then
        
            eAngle.x = angle;
        
        elseif Vector3.forward == axis then
        
            eAngle.z = angle;
        end

        return Quaternion.Euler(eAngle);
      
end

--[Comment]
--解析skill配置中的effectSpecial。
--value｛scope(范畴：0世界/1本地),position(预制体位置:1行动/2炮台/3车外),prefab｝
function Utility.analysisSpecialEffect(effectSpecial)
	local list=List.new();
	if(IsNilOrEmpty(effectSpecial))then
		return nil;
	end
	local effects=StrSplit(effectSpecial,";");
	local t={};
	for i=1,#effects do
		 t={};
		local scs=StrSplit(effects[i],":");
		t.scope=tonumber(scs[1]);
		t.position=tonumber(scs[2]);
		t.prefab=scs[3]..".prefab";
		list:add(t);
	end
	local t=list[math.random(1,list:getSize())];
	return t;
end
--[Comment]
--解析受体特效
--(范畴：0世界/1本地):prefabName;(范畴：0世界/1本地):prefabName(范畴：0世界/1本地)&1#3#prefabName
function Utility.analysisSkillEffect(effectHit)
    local list=List.new();
	if(IsNilOrEmpty(effectHit))then
		return nil;
	end
	local effects=StrSplit(effectHit,";");
	local t={};
	for i=1,#effects do
		 t={};
		local scs=StrSplit(effects[i],":");
		t.scope=tonumber(scs[1]);
		t.prefab=scs[2]..".prefab";
		list:add(t);
	end
	local t=list[math.random(1,list:getSize())];
	return t;
end