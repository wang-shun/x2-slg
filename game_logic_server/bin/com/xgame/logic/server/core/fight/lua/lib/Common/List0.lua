List0 = class(Object)

function List0:ctor()
	local object = {}
	setmetatable(object, self)
	self.__index = self
	self.key=0;
	self.__classtype = "List"
	self.index = 0;
end

function List0:forEach(func)
	local len = self:getSize();
	for i=0,len-1 do
		func(self[i]);
	end
end	

function List0:sort(func)
	table.sort( self, func )
end	

function List0:find(func) 

	local len = self:getSize();

	for i=0 , len-1 do
		if func(self[i]) then
			return self[i];
		end
	end				
end	

 	
function List0:add(value)
		local key=self.key;
		self[key] = value
		self.key = self.key+1;
end

function List0:addRange(addList)
		if(addList==nil) then
			return;
		end

		local len = addList.index == 1 and 0 or 1;
		len = #addList - len;

		for i=addList.index,len do
			self:add(addList[i]);
		end
end

function List0:contains(value) 
		local len = self:getSize();
	 
		for i=0,len-1 do
			if self[i] == value then
				return true;
			end
		end
		return false;
end

function List0:indexOf(value)
		local len = self:getSize();
		for i=0,len-1 do
			if self[i] == value then
				return i;
			end
		end
		return -1;
end

 
function List0:getSize()
	return self.key;	
end 

function List0:clear()
	--for i = 1,self.key-1 do
		--logDebug("List clear "..self.__keyList[1]..","..self[self.__keyList[1] ]);
		--self[self.__keyList[1] ] = nil
		--table.remove(self.__keyList, 1)
	--end
	for i=0,#self do
		self[i]=nil;
	end
	self.key=self.index;
end

--替换
function List0:replace(index,newValue)
		self[index] = newValue
end 

function List0:remove(value)
	local index = self:indexOf(value);
 
	if index ~= nil and index>=self.index then
		self:removeAt(index);
	end	
end	
 

 
function List0:removeRange(index , count)
	if index + count <= self:getSize() then
		while count > 0 do
			self:removeAt(index);
			count = count - 1;
		end	
	end 	
end	
--删除
function List0:removeAt(index)
	local len = self:getSize();
	if(index<self.index or index>=len) then
		print("is bug List:removeAt index:"..index..",self.key:"..self.key);
		return;
	end

	 
	for i=index,#self do
		if(index+1<=len) then
			self[i]=self[i+1];
		end
	end
	self[len]=nil;
	self.key=self.key-1;
	if(#self~=self:getSize()-1) then
		 
		print("is bug2 List:removeAt index:"..index..",self.key:"..self.key,":" , #self , self:getSize() , self.index);
	end
end 