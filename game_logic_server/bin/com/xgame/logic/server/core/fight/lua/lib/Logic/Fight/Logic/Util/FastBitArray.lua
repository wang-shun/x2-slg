FastBitArrayTool = {}


function FastBitArrayTool.CopyOne(other)
    local num = other.myData:getSize();
    local fba = FastBitArray.new();
    fba.myData = List.new();
    for i=1,num,1 do
        fba.myData:add(other.myData[i]);
    end
    fba.myLength = other.myLength;
    return fba;
end




FastBitArray = class(Object)

function FastBitArray:ctor(theSize)
	if theSize ~= nil then
		self.myLength = theSize;
		self.myData = List0.new(); --- 这里的list 的index 从0开始
	 	local num = Mathf.Floor(theSize / 32) + 1;
	 	for i=1,num,1 do
	 		self.myData:add(0);
	 	end	
 	end
end	

FastBitArray.BITS_PER_DATA = 32;

function FastBitArray:getCount()
	return self.myLength;
end	

function FastBitArray:getValue(theposition)
 
        local mLeft = self.myData[Mathf.Floor(theposition / 32)];

        local mRight = Bit._lshift(1,Bit._and(theposition,31));
            
        if mLeft == nil then
            return false;   
        end

        return Bit._and(mLeft , mRight) ~= 0;
end

function FastBitArray:setValue(theposition , v)
	local mV =  self.myData[Mathf.Floor(theposition / 32)];
	if v then
		mV = Bit._or(mV , Bit._lshift(1 , theposition)); 
	else
		mV = Bit._and(mV , Bit._not(Bit._lshift(1 , theposition))); 
	end
	self.myData[Mathf.Floor(theposition / 32)] = mV;
end

function FastBitArray:getLength()
        return self.myLength;
end



function FastBitArray:commonOr(otherBits)
        for i = 0 , self.myData:getSize() - 1 , 1 do
            if i >= otherBits:getLength() then
                break;
            end
            self.myData[i] = Bit._or(self.myData[i] , otherBits.myData[i]);
        end
end


function FastBitArray:offsetOr(otherBits, myOffset)
        if myOffset == 0 then
            self:commonOr(otherBits);
            return;
        end

        local num = 0;
        local num2 = 0;
        local i = 0;
        local j = 0;

        if myOffset > 0 then
            while myOffset >= 32 do
                num = num + 1;
                myOffset = myOffset - 32;
            end
            num2 = myOffset;
        else
            while myOffset <= -32 do
                i = i + 1;
                myOffset = myOffset + 32;
            end
            j = -myOffset;
        end

        if j > 0 then
            while j < 32 do
                if otherBits:getValue(num * 32 + num2) then
                    self:set(i * 32 + j);
                end
                j = j + 1;
                num2 = num2 + 1;
            end
            i = i + 1;
        end

        while i < sefl.myData:getSize() do
            if num >= otherBits.myData:getSize() then
                break;
            end

            local num3 = Bit._rshift(otherBits.myData[num] , num2) ;
            num = num + 1;
            if num >= otherBits.myData:getSize() then          
                break;
            end

            num3 =  Bit._or(num3 , Bit._lshift(otherBits.myData[num] ,  32 - num2)); 
            self.myData[i] = Bit._or(self.myData[i] , num3);
            i = i + 1;
        end
end
 
function FastBitArray:invert()
        for i = 0 , self.myData:getSize() - 1 , 1 do
            self.myData[i] = Bit._not(self.myData[i]);
        end
end

function FastBitArray:setWithValue(theposition, theValue)
        if theValue then
            self:set(theposition);
        else
            self:clear(theposition);
        end
end

function FastBitArray:set(theposition)
        self.myData[Mathf.Floor(theposition / 32)] = Bit._or(self.myData[Mathf.Floor(theposition / 32)]  , Bit._lshift(1,theposition));
end

function FastBitArray:setAll(theValue)
        if theValue then
            for  i = 0 , self.myData:getSize() - 1 , 1 do
                self.myData[i] = 4294967295;
            end
        else
            self:clearEmpty();
        end
end

function FastBitArray:clear(theposition)
	local v = self.myData[Mathf.Floor(theposition / 32)];
	self.myData[Mathf.Floor(theposition / 32)] =  Bit._and(v , Bit._not(Bit._lshift(1,theposition)));
end
 
function FastBitArray:clearEmpty()
         
         for  i = 0 , self.myData:getSize() - 1 , 1 do
            self.myData[i] = 0;
         end
end