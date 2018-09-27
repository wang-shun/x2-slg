local _class = {}
function class(super)
	local class_type = {}
	class_type.ctor = false
	
	--[[if super ~= nil and _G.next(super) ~= nil then
		class_type.super = super
	else 
		class_type.super = {}
	end	
	]]

	class_type.super = super
	
	class_type.new = function(...) 


		local obj = {}

		local meta = {__index = _class[class_type], cls_type = class_type}
		setmetatable(obj, meta)
		obj.super = _class[super]

		do
			local create;
			create = function(c, ...)
				if c ~= nil and c.super then

					create(c.super, ...)
				end
				 
				if c.ctor then
					c.ctor(obj, ...)
				end
				 
			end
			create(class_type, ...)
		end
		 
		return obj
	end

	local vtbl = {}
	vtbl.super = _class[super]
	_class[class_type] = vtbl
 
	setmetatable(class_type, {__newindex =
		function(t, k, v)
			vtbl[k] = v
		end
		, cls_type = super
	})
 
	if super and _class[super] then
		setmetatable(vtbl, {__index =
			function(t, k)
				local ret = _class[super][k]
				vtbl[k] = ret
				return ret
			end
		})
	end

	return class_type
end

function getClass()
	return _class
end

 -- 基类
Object = class()

function Object:ctor()	-- 定义 BaseClass 的构造函数
	--logDebug("Object ctor")
end

function Object:toString()
	--logDebug("[Object toString]")
end

 