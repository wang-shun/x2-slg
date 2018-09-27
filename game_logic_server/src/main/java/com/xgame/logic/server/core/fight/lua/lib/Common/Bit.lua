 
Bit={data32={}}  
for i=1,32 do  
    Bit.data32[i]=2^(32-i)  
end  
  
function Bit:d2b(arg)  --Bit:d2b  
    local   tr={}  
    for i=1,32 do  
        if arg >= self.data32[i] then  
        tr[i]=1  
        arg=arg-self.data32[i]  
        else  
        tr[i]=0  
        end  
    end  
    return   tr  
end   
  
function    Bit:b2d(arg)  --Bit:b2d 
    local   nr=0  
    for i=1,32 do  
        if arg[i] ==1 then  
        nr=nr+2^(32-i)  
        end  
    end  
    return  nr  
end    
--异或
function    Bit._xor(a,b)  --Bit:xor 
    --[[
    local   op1=self:d2b(a)  
    local   op2=self:d2b(b)  
    local   r={}  
  
    for i=1,32 do  
        if op1[i]==op2[i] then  
            r[i]=0  
        else  
            r[i]=1  
        end  
    end  
    return  self:b2d(r)  
    ]]
    return bit32.bxor(a,b)   
end  

-- 与运算
function Bit._and(a,b)  --Bit:_and  
    --[[local   op1=self:d2b(a)  
    local   op2=self:d2b(b)  
    local   r={}  
      
    for i=1,32 do  
        if op1[i]==1 and op2[i]==1  then  
            r[i]=1  
        else  
            r[i]=0  
        end  
    end  
    return  self:b2d(r)  ]]

    return bit32.band(a,b)   
end 
--或运算
function    Bit._or(a,b)  --Bit:_or  
   --[[ local   op1=self:d2b(a)  
    local   op2=self:d2b(b)  
    local   r={}  
      
    for i=1,32 do  
        if  op1[i]==1 or   op2[i]==1   then  
            r[i]=1  
        else  
            r[i]=0  
        end  
    end  
    return  self:b2d(r)  
    ]]

    return bit32.bor(a,b)   
end 
--取反
function    Bit._not(a)  --Bit:_not  
    --[[local   op1=self:d2b(a)  
    local   r={}  
  
    for i=1,32 do  
        if  op1[i]==1   then  
            r[i]=0  
        else  
            r[i]=1  
        end  
    end  
    return  self:b2d(r)  
    ]]

     return bit32.bnot(a)
end 
--a 右移 n 位
function    Bit._rshift(a,n)  --Bit:_rshift 
    --[[if n == 0 then 
        return a 
    end
    local   op1=self:d2b(a)  
    local   r=self:d2b(0)  
      
    if n < 32 and n > 0 then  
        for i=1,n do  
            for i=31,1,-1 do  
                op1[i+1]=op1[i]  
            end  
            op1[1]=0  
        end  
    r=op1  
    end  
    return  self:b2d(r)  
    ]]

    return bit32.rshift(a,n)
end  
-- a 左移 n 位
function    Bit._lshift(a,n)   --Bit:_lshift  
   --[[ if n == 0 then 
        return a 
    end
    local   op1=self:d2b(a)  
    local   r=self:d2b(0)  
      
    if n < 32 and n > 0 then  
        for i=1,n   do  
            for i=1,31 do  
                op1[i]=op1[i+1]  
            end  
            op1[32]=0  
        end  
    r=op1  
    end  
    return  self:b2d(r)  
    ]]

    return bit32.lshift(a,n);
end 
  
--soruce中第unit位(从右到左)是否拥有状态.
function    Bit:getLogicStatus(soruce, unit)
    local logicStatus = Bit:_lshift(1,unit-1);
    return logicStatus == Bit:_and(soruce,logicStatus);
end

--状态标识设置
--设置soruce中第unit位(从右到左)为isTrue状态.
function    Bit:setLogicStatus(soruce, unit, isTrue)
    local logicStatus = Bit:_lshift(1,unit - 1);
    local state;
    if isTrue then
        state = Bit:_or(soruce,logicStatus);
    else
        state = Bit:_and(soruce,Bit:_not(logicStatus)); 
    end
    return state;
end

function    Bit:print(ta)  
    local   sr=""  
    for i=1,32 do  
        sr=sr..ta[i]  
    end  
    print(sr)  
end 