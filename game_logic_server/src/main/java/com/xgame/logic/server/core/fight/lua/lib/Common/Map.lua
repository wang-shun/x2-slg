
Map = class(Object)
--创建一个Map
function Map:ctor()
	--logDebug("Map ctor");
	local object = {}
	setmetatable(object, self)
	self.__index = self
	
	--用一个table来保存key
	self.__keyList = {}
	
	--return object
end

--插入元素[如果已存在该key,则用新的value覆盖原有的value，与STL的“[key] = value 类似”]
function Map:put(key,value)
	--logDebug("Map put["..key.."="..value.."]");
	if(self[key] == nil) then
		self[key] = value
		table.insert(self.__keyList, key)
	else
		self[key] = nil
		self[key] = value
	end
	return self
end


--返回map中元素的个数
function Map:size()
    return #self.__keyList
end


--如果map为空则返回true
function Map:empty()
    if (self:size() == 0) then
		return true
	else
		return false
	end
end

--删除一个元素,根据key删除value【只实现stl的其中一个重载】
function Map:remove(key)
	if(self[key] == nil) then
		return self
	else
        for i = 1, #self.__keyList do
			if(self.__keyList[i] == key) then
				table.remove(self.__keyList, i)
				break;
			end
		end
		self[key] = nil
	end
end

--删除所有元素
function Map:clear()
	for i = 1, self:size() do
		self[self.__keyList[1] ] = nil
		table.remove(self.__keyList, 1)
	end
end

--获取key
function Map:key(index)
	return self.__keyList[index]
end

function Map:keys()
	return self.__keyList;
end

function Map:values()
	local _values = {};

	for i = 1, self:size() do
		_values[i] = self:value(self.__keyList[i]);
	end	

	return _values;
end

--根据key返回对应的value[lua特有，stl无此功能]
function Map:value(key)
	return self[key]
end

-- 返回指定下标的的值
function Map:valueIndex(index)
	return self[self.__keyList[index]]
end

function Map:copyTo(newMap)
	local len = self:size();

	for i = 1 , len do
		if not newMap:containsKey(self.__keyList[i]) then
			newMap:put(self.__keyList[i] , self:valueIndex(i));
		end	
	end	
end


--检查是否包含
function Map:containsKey(key)
	if(self[key]==nil) then
		return false;
	else
		return true;
	end
end

--返回迭代器函数[lua特有，stl无此功能]
function Map:iter()
	local i = 0
	local n = self:size()
	return function()
		i = i + 1
		if(i <= n) then
			return self.__keyList[i]
		else
			return nil
		end
	end
end


--传入key，返回key所在的迭代函数[lua特有，stl无此功能]
function Map:find(key)
	local idx = 0
	local n = self:size()
	for i = 1, n do
		if(self.__keyList[i] == key) then
			idx = i - 1
			break
		end
	end
	return function()
		idx = idx + 1
		if(idx <= n) then
			return self.__keyList[idx]
		else
			return nil
		end
	end
end

function Map:sortKey( )
	table.sort( self.__keyList )
end
function Map:toString()
	local str=("[Map size:");
	str=str..self:size().."\n";
	for i = 1, self:size() do
		local key=self:key(i);
		local value=self:value(key);
		if(type(value)~="userdata" and type(value)~="table") then
			str=str..key.."="..value.."\n";
		else
			str=str..key.."="..type(value).."\n"
		end
	end
	str=str.."]";
	return str;
end

function Map:get(key)
	return self[key]
end


function testMap()
	local map=Map.new();
	map:put("a", 1)
	map:put("b", 2)
	map:put("a", 2)
	--logDebug("map size:"..#map);
	--logError(getBoolean(map:containsKey("sss")));
	--logError(getBoolean(map:containsKey("a")));
	local a=map:containsKey("c");
	if(not a) then
		--logDebug("yes");
	end
	local b=map:containsKey("a");
	if(not b) then
		--logDebug("is bug");
	end
	map:toString();
end
