FQuaternion = 
{
	x = 0, 
	y = 0, 
	z = 0, 
	w = 0,
	ClassName = "FQuaternion"
}

FQuaternion.__index = FQuaternion

function FQuaternion.new(x , y, z ,w) 
	local self = setmetatable({}, FQuaternion)
	self['x'] = x;
	self['y'] = y;
	self['z'] = z;
	self['w'] = w;
	return self
end

function FQuaternion:Clone()
	return FQuaternion.new(self.x, self.y, self.z, self.w)
end

function FQuaternion:Normalize()
	local quat = self:Clone()
	quat:SetNormalize()
	return quat
end

local _next = { 2, 3, 1 }
local rad2Deg = Mathf.Rad2Deg
local pi = Mathf.PI;
local half_pi = pi * 0.5;
local two_pi = 2 * pi;
local negativeFlip = -0.0001;
local positiveFlip = two_pi - 0.0001;

local function SanitizeEuler(euler)	
	if euler.x < negativeFlip then
		euler.x = euler.x + two_pi
	elseif euler.x > positiveFlip then
		euler.x = euler.x - two_pi
	end

	if euler.y < negativeFlip then
		euler.y = euler.y + two_pi
	elseif euler.y > positiveFlip then
		euler.y = euler.y - two_pi
	end

	if euler.z < negativeFlip then
		euler.z = euler.z + two_pi
	elseif euler.z > positiveFlip then
		euler.z = euler.z + two_pi
	end
end

function FQuaternion:ToEulerAngles()
	local x = self.x
	local y = self.y
	local z = self.z
	local w = self.w
		
	local check = 2 * (y * z - w * x)
	
	if check < 0.999 then
		if check > -0.999 then
			local v = FVector3.new( -math.asin(check), 
						math.atan2(2 * (x * z + w * y), 1 - 2 * (x * x + y * y)), 
						math.atan2(2 * (x * y + w * z), 1 - 2 * (x * x + z * z)))
			SanitizeEuler(v)
			v:Mul(rad2Deg)
			return v
		else
			local v = Vector3.New(half_pi, atan2(2 * (x * y - w * z), 1 - 2 * (y * y + z * z)), 0)
			SanitizeEuler(v)
			v:Mul(rad2Deg)
			return v
		end
	else
		local v = Vector3.New(-half_pi, atan2(-2 * (x * y - w * z), 1 - 2 * (y * y + z * z)), 0)
		SanitizeEuler(v)
		v:Mul(rad2Deg)
		return v		
	end
end




function FQuaternion:SetNormalize()
	local n = self.x * self.x + self.y * self.y + self.z * self.z + self.w * self.w
	
	if n ~= 1 and n > 0 then
		n = 1 / math.sqrt(n)
		self.x = self.x * n
		self.y = self.y * n
		self.z = self.z * n
		self.w = self.w * n		
	end
end

function FQuaternion.LookRotation(forward, up)
	local mag = forward:Magnitude()
	if mag < 1e-6 then
		print("error input forward to FQuaternion.LookRotation" , forward)
		return nil
	end
	
	forward = forward / mag
	up = up or FVector3.up;	
	local right = FVector3.Cross(up, forward)
	right:normalize()    

    	up = FVector3.Cross(forward, right)
    	right = FVector3.Cross(up, forward)	
 	
	local t = right.x + up.y + forward.z
    
	if t > 0 then		
		local x, y, z, w
		t = t + 1
		local s = 0.5 / math.sqrt(t)		
		w = s * t
		x = (up.z - forward.y) * s		
		y = (forward.x - right.z) * s
		z = (right.y - up.x) * s
		
		local ret = FQuaternion.new(x, y, z, w)	
		ret:Normalize()
		return ret
	else
		local rot = 
		{ 					
			{right.x, up.x, forward.x},
			{right.y, up.y, forward.y},
			{right.z, up.z, forward.z},
		}
	
		local q = {0, 0, 0}
		local i = 1		
		
		if up.y > right.x then			
			i = 2			
		end
		
		if forward.z > rot[i][i] then
			i = 3			
		end
		
		local j = _next[i]
		local k = _next[j]
		
		local t = rot[i][i] - rot[j][j] - rot[k][k] + 1
		local s = 0.5 / math.sqrt(t)
		q[i] = s * t
		local w = (rot[k][j] - rot[j][k]) * s
		q[j] = (rot[j][i] + rot[i][j]) * s
		q[k] = (rot[k][i] + rot[i][k]) * s
		
		local ret = FQuaternion.new(q[1], q[2], q[3], w)			
		ret:Normalize()
		return ret
	end
end
 