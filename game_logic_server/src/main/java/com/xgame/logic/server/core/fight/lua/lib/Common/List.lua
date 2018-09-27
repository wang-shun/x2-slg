--定义List
 
require "lib/Common/functions"

List = class(Object)

function List:ctor()
      --print("List ctor");
			local object = {}
			setmetatable(object, self)
			self.__index = self
			--用一个table来保存key
			--self.__keyList = {}
			self.key=1;
			self.__classtype = "List"
end

function List:forEach(func)
	local len = self:getSize();
	for i=1,len do
		func(self[i]);
	end
end	

function List:sort(func)
	table.sort( self, func )
end	

function List:find(func) 
	for i=1,#self do
		if func(self[i]) then
			return self[i];
		end
	end				
end	

function List:findAll(func) 
	local _values = List.new();
	for i=1,#self do
		if func(self[i]) then
			_values:add(self[i]);
		end
	end				
	return _values;
end	
function List:add(value)
		local key=self.key;
    --print("List add["..key.."=".."]");
		--[[if(self[key] == nil) then
		--table.insert(self.__keyList, key)
		end]]
		self[key] = value
		self.key = self.key+1;
end

function List:addRange(addList)
		if(addList==nil) then
			return;
		end
		for i=1,#addList do
			self:add(addList[i]);
		end
end

--[Comment]
--添加一系列参数到List中
function List:addParams(...)
	for _,v in ipairs({...}) do
		if(v~=nil)then
			self:add(v);
		end
	end
	return self;
end
function List:getRange(index , count)
	if index > #self or index < 1 or count > #self then
		return nil;
	end

	local _values = List.new();

	local j = 1;

	for i=index,count do
		_values[j] = self[i];
		j = j+1;
	end	

	return _values;
end

function List:reverse() 

	local len = self:getSize();
 	
	local lenLoop;

	if len % 2 == 0 then
		lenLoop = len / 2;
	else 
		lenLoop = GetIntPart(len / 2);
	end		

	for i = 1 , lenLoop do
		local tmp = self[len - i + 1];
		self[len - i + 1] = self[i];
		self[i] = tmp;
	end
  
end	

function List:contains(value) 
		for i=1,#self do
			if self[i] == value then
				return true;
			end
		end
		return false;
end

function List:indexOf(value)
		for i=1,#self do
			if self[i] == value then
				return i;
			end
		end
		return -1;
end

function List:getSize()
    return self.key-1;
end 

function List:clear()
	--for i = 1,self.key-1 do
		--logDebug("List clear "..self.__keyList[1]..","..self[self.__keyList[1] ]);
		--self[self.__keyList[1] ] = nil
		--table.remove(self.__keyList, 1)
	--end
	for i=1,#self do
		self[i]=nil;
	end
	self.key=1;
end

--替换
function List:replace(index,newValue)
		self[index] = newValue
end 

function List:remove(value)
	local index = self:indexOf(value);

	if index ~= nil and index>0 then
		self:removeAt(index);
	end	
end	

function List:insert(v, iter)

	local len=self:getSize();

	if(v<1 or v>len+1) then
		error("is bug List:insert index:"..v);
		return;
	end

	if v == len then
		self[len+1] = self[len];
	else
		local _p;
		
		for i=v,#self do
			if _p ~= nil then
				local __p = self[i];
				self[i] = _p;
				_p = __p;
			else 
				_p = self[i];
			end	
		end

		if _p ~= nil then
			self[len+1] = _p;
		end	
	end	
	
	self[v] = iter;

	self.key=self.key+1;
end	

--删除
function List:removeAt(index)
	local len=self:getSize();
	if(index<1 or index>len) then
		error("is bug List:removeAt index:"..index..",self.key:"..self.key);
		return;
	end
	for i=index,#self do
		if(index+1<=len) then
			self[i]=self[i+1];
		end
	end
	self[len]=nil;
	self.key=self.key-1;
	if(#self~=len-1) then
		error("is bug2 List:removeAt index:"..index..",self.key:"..self.key);
	end
end 
--
function List:toString()
		local str="[List size:"..#self;
		for i=1,#self do
			if(type(self[i])~="table" and type(self[i])~="userdata") then
				str=str.."self["..i.."]="..self[i].." ";
			end
		end
		str=str.."]"
		return str;
end 

--测试用例
function testList()
	--[[
	for i=0,0 do
		--logError("xxxxx");
	end
	]]
	--[[
	local list=List.new();
	list:add(1);
	list:add(2);
	list:add(2);
	list:add(4);
	list:clear();
	list:add(5);
	list:add(6);
	for i=#list,1,-1 do
		logError(i);
		if(list[i]==2 or list[i]==5 ) then
			list:removeAt(i);
			i=i-1;
		end
		i=i+1;
	end
	logError(list:toString());
	]]
	--[[
	for i=1,#list do
		--logDebug(type(list[i]));
		if(type(list[i])~="table") then
		logDebug("list["..i.."]="..list[i]);
		end
	end
	local list=List.new();
	list:add(ActionRand.new(1,10));
	list:add(ActionRand.new(2,10));
	list:add(ActionRand.new(3,80));
	local randomUtil=RandomUtil.new();
	for i=1,10 do
		local ret=randomUtil:randomList(list,randomUtil:next(100));
		logDebug("randomList result action:"..ret.action..",weight:"..ret.weight);
	end
	
	local combationList=List.new();
	local combation=List.new();
	combation:add(1);
	combation:add(2);
	combation:add(3);
	combationList:add(combation);
	combation=List.new();
	combation:add(1);
	combation:add(2);
	combation:add(4);
	combationList:add(combation);
	logDebug(#combationList);
	for i=1,#combationList do
		logDebug(#combationList[i]);
		for j=1,#combationList[i] do
			logDebug(combationList[i][j]);
		end	
	end
	]]
end