
FVector3 = {ClassName = "FVector3" , x = MathConst.Zero , y = MathConst.Zero,z = MathConst.Zero}


function FVector3.Distance(v1, v2)
        return (v2 - v1):magnitude();   
end

function FVector3.DistanceFast(v1, v2)
        return Mathf.Sqrt(FVector3.SqrDistanceFast(v1, v2));
end

function FVector3.SqrDistanceFast(v1, v2)
        local num = v2.x - v1.x;
        local num2 = v2.y - v1.y;
        local num3 = v2.z - v1.z;
        local rawValue = num * num + num2 * num2 + num3 * num3;
        return rawValue;
end

function FVector3.SqrDistance(v1, v2)
  --  print((v2 - v1):sqrMagnitude())
    return (v2 - v1):sqrMagnitude();    
end

function FVector3.Normalized(v)
    local fixedPointNumber = v:magnitude();
    return fixedPointNumber == MathConst.Zero and v or FVector3.new(v.x / fixedPointNumber, v.y / fixedPointNumber , v.z / fixedPointNumber);
end

function FVector3.Projection(v1, v2)
    local n = v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    return FVector3.new(v2.x * n, v2.y * n, v2.z * n);
end

function FVector3.Lerp(v1, v2, t)
        return FVector3.new(v1.x * (MathConst.One - t) + v2.x * t, v1.y * (MathConst.One - t) + v2.y * t, v1.z * (MathConst.One - t) + v2.z * t);
end

FVector3.__index = FVector3

function FVector3.new(x , y, z) 
 
    local self = setmetatable({}, FVector3)
    self['x'] = x;
    self['y'] = y;
    self['z'] = z;
    return self
end

FVector3.Zero = FVector3.new(MathConst.Zero , MathConst.Zero , MathConst.Zero); 

FVector3.UnitX = FVector3.new(MathConst.One , MathConst.Zero , MathConst.Zero); 

FVector3.UnitY = FVector3.new(MathConst.Zero , MathConst.One , MathConst.Zero); 
 
FVector3.One = FVector3.new(MathConst.One , MathConst.One , MathConst.Zero); 
 
FVector3.OneHalf = FVector3.new(MathConst.OneHalf , MathConst.OneHalf , MathConst.OneHalf);  

function FVector3:Clone()
	return FVector3.new(self.x, self.y, self.z)
end
 
function FVector3:add(v)
        self.x = self.x + v.x;
        self.y = self.y + v.y;
        self.z = self.z + v.z;
        return self;
end

function FVector3:sub(v)
        self.x = self.x - v.x;
        self.y = self.y - v.y;
        self.z = self.z - v.z;
        return self;
end

function FVector3:scale(scale)
        self.x = self.x * scale;
        self.y = self.y * scale;
        self.z = self.z* scale;
        return self;
end

function FVector3:normalize()
     
        local fixedPointNumber = self:magnitude();
        if  not (fixedPointNumber == MathConst.Zero) then
            self.x = self.x / fixedPointNumber;
            self.y = self.y / fixedPointNumber;
            self.z = self.z / fixedPointNumber;
        end
        return self;
end

function FVector3:normalizeAndReturnMagnitude()
        local fixedPointNumber = self:magnitude();
        if not fixedPointNumber == MathConst.Zero then
            self.x = self.x / fixedPointNumber;
            self.y = self.y / fixedPointNumber;
            self.z = self.z / fixedPointNumber;
        end
        return fixedPointNumber;
end

function FVector3:imAdd(v)
        return FVector3.new(self.x+v.x, self.y+v.y, self.z+v.z);
end

function FVector3:imSub(v)
        return FVector3.new(self.x-v.x, self.y-v.y, self.z-v.z);
end

function FVector3:imScale(scale)
    return FVector3.new(self.x*scale, self.y*scale, self.z*scale);
end	
 

function FVector3:imNormalize()
        local fixedPointNumber = self:magnitude();
        if not fixedPointNumber == MathConst.Zero then
            return FVector3.new(self.x / fixedPointNumber , self.y / fixedPointNumber, self.z / fixedPointNumber);
        end
        return FVector3.new(0,0,0);
end

function FVector3:magnitude()
	local n = Mathf.Sqrt(self.x * self.x + self.y * self.y + self.z * self.z);
        	return n;
end

function FVector3:sqrMagnitude()
        return self.x * self.x + self.y * self.y+ self.z * self.z;
end

function FVector3:dotProduct(v)
      return self.x * v.x + self.y * v.y+ self.z * v.z;
end

--[Comment]
--其实就是上面的magnitude，但是为了兼容。所以增多一个这个方法。
--备注:可以考虑自定义方法中改成大写。
function FVector3:Magnitude()
    return self:magnitude();
end
    
function FVector3:equals(obj)
        if typeOfLua(obj , "FVector3") then
            return self.x == obj.x and self.y == obj.y and self.z == obj.z;
        end
        return false;
end

function FVector3:ToString()
    return self.x .. "," .. self.y .. "," .. self.z;
end
    
function FVector3:isLeftOf(v1, v2)
        local n = v1.x * v2.y* v2.z - v2.x * v1.y* v2.z;
        if n > 0 then
        
            return 1;
        end
        if n < 0 then
        
            return -1;
        end
        return 0;
end

function FVector3.__add(v1 , v2)
	return FVector3.new(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);	
end
    
function FVector3.__sub(v1 , v2)
	return FVector3.new(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
end	
   
function FVector3.__unm(v1)
	return FVector3.new(-v1.x, -v1.y, -v1.z);
end	

function FVector3.Cross(lhs, rhs)
    local x = lhs.y * rhs.z - lhs.z * rhs.y
    local y = lhs.z * rhs.x - lhs.x * rhs.z
    local z = lhs.x * rhs.y - lhs.y * rhs.x
    return FVector3.new(x,y,z)    
end
    
function FVector3.__mul(n1 , n2)
 
    if typeOfLua(n1 , "FVector3") and not typeOfLua(n2 , "FVector3") then   
        
 	return FVector3.new(n1.x * n2, n1.y * n2, n1.z * n2);
    elseif not typeOfLua(n1 , "FVector3") and typeOfLua(n2 , "FVector3") then    
   	return FVector3.new(n2.x * n1, n2.y * n1, n2.z * n1);
    end
    return FVector3.new(0,0,0);
end    
 
function FVector3.__div(n1 , n2)
    if typeOfLua(n1 , "FVector3") and not typeOfLua(n2 , "FVector3") then    
 	return FVector3.new(n1.x / n2, n1.y / n2, n1.z / n2);
    elseif not typeOfLua(n1 , "FVector3") and typeOfLua(n2 , "FVector3") then    
   	return FVector3.new(n2.x * n1, n2.y * n1, n2.z * n1);
    end
    return FVector3.new(0,0,0);
end    
    
function FVector3.__eq(v1, v2)
        return v1.x == v2.x and v1.y == v2.y and v1.z == v2.z;
end

function FVector3:Set(x,y,z) 
    self.x = x or 0
    self.y = y or 0
    self.z = z or 0
end

function FVector3:MulQuat(quat)     
    local num   = quat.x * 2
    local num2  = quat.y * 2
    local num3  = quat.z * 2
    local num4  = quat.x * num
    local num5  = quat.y * num2
    local num6  = quat.z * num3
    local num7  = quat.x * num2
    local num8  = quat.x * num3
    local num9  = quat.y * num3
    local num10 = quat.w * num
    local num11 = quat.w * num2
    local num12 = quat.w * num3
    
    local x = (((1 - (num5 + num6)) * self.x) + ((num7 - num12) * self.y)) + ((num8 + num11) * self.z)
    local y = (((num7 + num12) * self.x) + ((1 - (num4 + num6)) * self.y)) + ((num9 - num10) * self.z)
    local z = (((num8 - num11) * self.x) + ((num9 + num10) * self.y)) + ((1 - (num4 + num5)) * self.z)
    
    self:Set(x, y, z)   
    return self
end


function FVector3:Mul(q)
    if type(q) == "number" then
        self.x = self.x * q
        self.y = self.y * q
        self.z = self.z * q
    else
        self:MulQuat(q)
    end
    
    return self
end

FVector3.up = FVector3.new(0,1,0);