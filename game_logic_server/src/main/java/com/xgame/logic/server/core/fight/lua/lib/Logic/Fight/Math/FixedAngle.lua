FixedAngle = {}

local this = FixedAngle;

FixedAngle.Degrees360 = 360;
FixedAngle.Degrees180 = 180;
FixedAngle.Degrees90 = 90;
FixedAngle.AtanLimitedMagicNumber = 0.27294921875;

FixedAngle.sAngles = nil;

function FixedAngle.NormalizeAngle(angle)
        while angle > FixedAngle.Degrees180 do
            angle = angle - FixedAngle.Degrees360;
        end

        while angle < -FixedAngle.Degrees180 do
            angle = angle + FixedAngle.Degrees360;
        end

        return angle;
end

function FixedAngle.AngleDegreesQ1(facing)
        if FixedAngle.sAngles == nil then
            FixedAngle.InitAngles();
        end

        facing:normalize();

        local num = 0;

        local i = 90;

        if facing.z < MathConst.OneHalf then
            while i > num + 1 do
                local num2 = (num + i) / 2;
                if FixedAngle.sAngles[Mathf.Floor(num2)].z >= facing.z then
                    i = num2;
                else
                    num = num2;
                end
            end
        else
            while i > num + 1 do
                local num3 = (num + i) / 2;
             
                if FixedAngle.sAngles[Mathf.Floor(num3)].x <= facing.x then
                    i = num3;
                else
                    num = num3;
                end    
            end
        end

        local fixedPointNumber = num;

        if num == i then
            fixedPointNumber = i;
        else
            local n = FixedAngle.tinyMagnitude(FixedAngle.sAngles[Mathf.Floor(i)] - FixedAngle.sAngles[Mathf.Floor(num)]);
            local v = FixedAngle.sAngles[Mathf.Floor(num)];
            local vec = facing - v;
            local n2 = FixedAngle.tinyMagnitude(vec);

            if n == 0 then
                n = 1;
            end    
            local n3 = n2 / n;
            fixedPointNumber = fixedPointNumber + n3;
        end
         
        return fixedPointNumber;
end

 
function FixedAngle.VectorForAngleQ1(angleDegrees)
    
        if FixedAngle.sAngles == nil then
            FixedAngle.InitAngles();
        end

        if angleDegrees == FixedAngle.Degrees90 then
            return FixedAngle.sAngles[90]; 
        end

        if angleDegrees == MathConst.Zero then
            return FixedAngle.sAngles[0]; 
        end
 
        local num = GetIntPart(angleDegrees);
        local n = angleDegrees - num;
        local num2 = num;
        local index = num2 + 1;
 
        local fVector = FixedAngle.sAngles[index] - FixedAngle.sAngles[num2];
        local result = FixedAngle.sAngles[num];
        local tmpV2 = FVector3.new(0,0,0);
        tmpV2.x = result.x + fVector.x * n;
        tmpV2.z = result.z + fVector.z * n;
 
        return tmpV2;
end

function FixedAngle.tinyMagnitude(vec)
        local n = vec:sqrMagnitude();
        if n < MathConst.OneHalf then
            local fVector = vec;
            fVector.x = fVector.x * 4096;
            fVector.z = fVector.z * 4096;
            return fVector:magnitude() / 4096;
        end
        return vec:magnitude();
end

function FixedAngle.AngleInDegrees(facing)

        local n = Mathf.Abs(facing.x);  
        local n2 = Mathf.Abs(facing.z);  
 
        if n == 0 and n2 == 0 then
            return MathConst.Zero;
        end

        if n > n2 then
            if facing.x > 0 then
                return FixedAngle.AtanApproxInDegrees(facing.z / facing.x);
            end
            local fixedPointNumber = FixedAngle.Degrees180 - FixedAngle.AtanApproxInDegrees(-facing.z / facing.x);
            if facing.z < 0 then
                    local rs = fixedPointNumber - FixedAngle.Degrees360;
                    return rs;
            end
            
            return fixedPointNumber;
        else
            if facing.z > 0 then
                local rs = FixedAngle.AtanApproxInDegrees(-facing.x / facing.z) + FixedAngle.Degrees90;
            
                return rs;
            end
            local rs = FixedAngle.AtanApproxInDegrees(-facing.x / facing.z) - FixedAngle.Degrees90;
            return rs;
        end
end

function FixedAngle.AtanApproxInDegrees(x)
    local rs = FixedAngle.AtanApprox(x) / MathConst.PiOver180;
   
    return rs;
end

function FixedAngle.AtanApprox(x)
        return MathConst.PiOverFour * x + FixedAngle.AtanLimitedMagicNumber * x * (MathConst.One - Mathf.Abs(x));
end

function FixedAngle.AngleInDegreesOld(facing)
        local facing2 = facing;
        local result = MathConst.Zero;
        if facing2.x < MathConst.Zero then
            facing2.x = - facing2.x;
            if facing2.z < MathConst.Zero then
                facing2.z = -facing2.z;
                result = -FixedAngle.Degrees180 + FixedAngle.AngleDegreesQ1(facing2);
            else
                result = FixedAngle.Degrees180 - FixedAngle.AngleDegreesQ1(facing2);
            end
        elseif facing2.z < MathConst.Zero then
            facing2.z = -facing2.z;
            result = -FixedAngle.AngleDegreesQ1(facing2);
        else
            result = FixedAngle.AngleDegreesQ1(facing2);
        end
        return result;
end

function FixedAngle.FacingForAngleInDegrees(angleIn)
      --  warn("----------FacingForAngleInDegrees angleIn = ".. angleIn:ToString() )
        local fixedPointNumber = FixedAngle.NormalizeAngle(angleIn);
        if fixedPointNumber > FixedAngle.Degrees90 then
          
            local result = FixedAngle.VectorForAngleQ1(FixedAngle.Degrees180 - fixedPointNumber);
          
            result.x = -result.x;
           
            return result;
        end

        if fixedPointNumber >= MathConst.Zero then
            
            return FixedAngle.VectorForAngleQ1(fixedPointNumber);
        end

        if fixedPointNumber >= -FixedAngle.Degrees90 then
            local result2 = FixedAngle.VectorForAngleQ1(-fixedPointNumber);
            result2.z = -result2.z;
            return result2;
        end

        local result3 = FixedAngle.VectorForAngleQ1(-(-FixedAngle.Degrees180 - fixedPointNumber));
 
        result3.x = -result3.x;
        result3.z = -result3.z;
      
        return result3;
end


--得到真实角度, 实际用于显示的
function FixedAngle.GetFixAngle(angleIn)
        if Mathf.Abs(angleIn) > 180 then
            if angleIn < 0 then
                angleIn = 360 + angleIn;
            end
        end
        return angleIn;
end

function FixedAngle.InitAngles()
        if FixedAngle.sAngles ~= nil then
            return;
        end

        FixedAngle.sAngles = List0.new();

        local aBase = MathConst.Base;

        local num = 4096;
        local num2 = 0;
        local x_ = num / aBase;
        local y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0 ,y_));
        num = 4095;
        num2 = 71;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 4094;
        num2 = 143;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 4090;
        num2 = 214;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 4086;
        num2 = 286;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 4080;
        num2 = 357;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 4074;
        num2 = 428;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 4065;
        num2 = 499;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 4056;
        num2 = 570;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 4046;
        num2 = 641;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 4034;
        num2 = 711;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 4021;
        num2 = 782;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 4006;
        num2 = 852;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 3991;
        num2 = 921;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 3974;
        num2 = 991;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 3956;
        num2 = 1060;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 3937;
        num2 = 1129;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 3917;
        num2 = 1198;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 3896;
        num2 = 1266;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 3873;
        num2 = 1334;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 3849;
        num2 = 1401;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 3824;
        num2 = 1468;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 3798;
        num2 = 1534;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 3770;
        num2 = 1600;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 3742;
        num2 = 1666;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 3712;
        num2 = 1731;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 3681;
        num2 = 1796;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 3650;
        num2 = 1860;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 3617;
        num2 = 1923;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 3582;
        num2 = 1986;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 3547;
        num2 = 2048;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 3511;
        num2 = 2110;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 3474;
        num2 = 2171;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 3435;
        num2 = 2231;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 3396;
        num2 = 2290;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 3355;
        num2 = 2349;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 3314;
        num2 = 2408;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 3271;
        num2 = 2465;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 3228;
        num2 = 2522;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 3183;
        num2 = 2578;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 3138;
        num2 = 2633;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 3091;
        num2 = 2687;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 3044;
        num2 = 2741;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 2996;
        num2 = 2793;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 2946;
        num2 = 2845;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 2896;
        num2 = 2896;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 2845;
        num2 = 2946;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 2793;
        num2 = 2996;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 2741;
        num2 = 3044;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 2687;
        num2 = 3091;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 2633;
        num2 = 3138;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 2578;
        num2 = 3183;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 2522;
        num2 = 3228;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 2465;
        num2 = 3271;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 2408;
        num2 = 3314;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 2349;
        num2 = 3355;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 2290;
        num2 = 3396;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 2231;
        num2 = 3435;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 2171;
        num2 = 3474;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 2110;
        num2 = 3511;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 2048;
        num2 = 3547;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 1986;
        num2 = 3582;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 1923;
        num2 = 3617;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 1860;
        num2 = 3650;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 1796;
        num2 = 3681;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 1731;
        num2 = 3712;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 1666;
        num2 = 3742;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 1600;
        num2 = 3770;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 1534;
        num2 = 3798;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 1468;
        num2 = 3824;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 1401;
        num2 = 3849;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 1334;
        num2 = 3873;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 1266;
        num2 = 3896;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 1198;
        num2 = 3917;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 1129;
        num2 = 3937;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 1060;
        num2 = 3956;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 991;
        num2 = 3974;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 921;
        num2 = 3991;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 852;
        num2 = 4006;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 782;
        num2 = 4021;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 711;
        num2 = 4034;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 641;
        num2 = 4046;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 570;
        num2 = 4056;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 499;
        num2 = 4065;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 428;
        num2 = 4074;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 357;
        num2 = 4080;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 286;
        num2 = 4086;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 214;
        num2 = 4090;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 143;
        num2 = 4094;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 71;
        num2 = 4095;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
        num = 0;
        num2 = 4096;
        x_ = num / aBase;
        y_ = num2 / aBase;
        FixedAngle.sAngles:add(FVector3.new(x_, 0, y_));
end