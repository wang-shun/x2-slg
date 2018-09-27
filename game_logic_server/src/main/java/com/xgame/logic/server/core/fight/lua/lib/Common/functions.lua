require "lib/Common/print_r"
 
--输出日志--
function log(...)
	print(...)
    print(debug.traceback())
end

--[Comment]
--颜色打印输出。
--加这个的目的是即不需要用error导致error暂停，又达到重点显示的目的。
--在需要用error做显示的地方就可以用这个方法代替。
--（参考建议：少用）
function printColor(...)
    local str = "";
    for i = 1, select('#', ...) do
        local arg = select(i, ...)
        if(type(arg) == "table")    then
            local tv = getTableStr(arg);
            str = str..tv;
            -- str = str..tostring(arg);
        elseif(arg == nil)     then
            str = str.."nil";
        else
            str = str..tostring(arg);
        end
        str = str.."     ";
    end
	str="<color=#FB8B14>"..str.."</color>"
    str = str .. "\n"..debug.traceback();
	--Util.Log(str);
end
  
--打印字符串--

function printDetail(...) 
    local str = "";
    for i = 1, select('#', ...) do
        local arg = select(i, ...)
        if(type(arg) == "table")    then
            local tv = getTableStr(arg);
            str = str..tv;
            -- str = str..tostring(arg);
        elseif(arg == nil)     then
            str = str.."nil";
        else
            str = str..tostring(arg);
        end
        str = str.."     ";
    end
    
    print(str);
    --str = str .. "\n".. 
    debug.traceback()
end

 

function handler(obj, method)
    return function(...)
        if obj.o_handler then
            return method(obj.o_handler, ...)
        else
            return method(obj, ...)
        end        
    end
end

--错误日志--
function error(str) 
    --Util.Log(debug.traceback());
    --Util.LogError(str);
end

function wprint(...)
    local str = "";
    for i = 1, select('#', ...) do
        
        local arg = select(i, ...)
        if(type(arg) == "table")    then
            local tv = getTableStr(arg);
            str = str..tv;
        elseif(arg == nil)     then
            str = str.."nil";
        else
            str = str..arg;
        end
        str = str..",   ";
    end
    str = str .. "\n";
    --Util.wLog(str);
end

function wprintc(...)
    local str = "";
    for i = 1, select('#', ...) do
        
        local arg = select(i, ...)
        if(type(arg) == "table")    then
            local tv = getTableStr(arg);
            str = str..tv;
        elseif(arg == nil)     then
            str = str.."nil";
        else
            str = str..arg;
        end
        str = str..",   ";
    end
    str = str.."\n"..debug.traceback().."\n";

    --Util.wLog(str);
end

--警告日志--
function warn(str) 
  
    if str == nil then 
    	str = "内容为空！！！！"
    end	

    if type(str)=="userdata" then
        print("---------------打印 C#对象----------------->>>>")
        --Util.Log(str);
        print("--------------------------------<<<<\n")
    elseif type(str)=="table" then
        print_r(str);    
    else    
        --Util.LogWarning("lua --> " .."打印内容 :" .. tostring(str) .."\n\n\n" .. debug.traceback() );
    end

end

--警告日志,str
function warnning(str) 
    
    --Util.LogWarning(str)
end

-- table 拷贝  
function table_copy_table(ori_tab)  
    if (type(ori_tab) ~= "table") then  
        return nil  
    end  
    local new_tab = {}  
    for i,v in pairs(ori_tab) do  
        local vtyp = type(v)  
        if (vtyp == "table") then  
            new_tab[i] = table_copy_table(v)  
        elseif (vtyp == "thread") then  
            new_tab[i] = v  
        elseif (vtyp == "userdata") then  
            new_tab[i] = v  
        else  
            new_tab[i] = v  
        end  
    end  
    return new_tab  
end  

--查找对象--
function find(str)
	return GameObject.Find(str);
end

function destroy(obj)
	GameObject.Destroy(obj);
end

function newobject(prefab)
	return GameObject.Instantiate(prefab);
end

--创建面板--
function createPanel(name)
	PanelManager:CreatePanel(name);
end

function child(str)
	return transform:FindChild(str);
end

function subGet(childNode, typeName)		
	return child(childNode):GetComponent(typeName);
end

function findPanel(str) 
	local obj = find(str);
	if obj == nil then
		error(str.." is null");
		return nil;
	end
	return obj:GetComponent("BaseLua");
end

function getMatchineRight(curNum,num)
	for i = 1,num do
		curNum = math.floor(curNum/2);
	end
	return curNum;
end
function getMatchineLeft(curNum,num)
	for i = 1,num do
		curNum = curNum*2;
	end
	return curNum;
end

function checkCAnd(bl1,bl2)
	return (bl1 or false) and bl2;
end

function checkCOr(bl1,bl2)
	return (bl1 or false) or bl2;
end

function printTable(t,prefix)
  for k,v in pairs(t) do
    if type(v) == "string" then
      print(string.format("%s => %s", prefix .. "." .. k,v))
    else
      print(prefix .. "." .. k..type(v))
    end
    if type(v) == "table" and k ~= "_G" and k ~= "_G._G" and k ~= "package" then
      printTable(v, "  " .. prefix .. "." .. k)
    end
  end
end

--[[function typeof(var)
    local _type = type(var);
    if(_type ~= "table" and _type ~= "userdata") then
    	return _type;
    end
    local _meta = getmetatable(var);
    if(_meta ~= nil and _meta._NAME ~= nil) then
        return _meta._NAME;
    else
        return _type;
    end
end
]]
--lua 字符串分割函数   
-------------------------------------------------------   
-- 参数:待分割的字符串,分割字符   
-- 返回:子串表.(含有空串)   
function StrSplit(str, delimiter)       
	if str==nil or str=='' or delimiter==nil then
		return nil
	end
	
    local result = {}
    for match in (str..delimiter):gmatch("(.-)"..delimiter) do
        table.insert(result, match)
    end
    return result
end

local function __TRACKBACK__(errmsg)
    local track_text = debug.traceback(tostring(errmsg), 6);
    print("---------------------------------------- TRACKBACK ----------------------------------------");
    print(track_text, "LUA ERROR");
    print("---------------------------------------- TRACKBACK ----------------------------------------");
    local exception_text = "LUA EXCEPTION\n" .. track_text;
    return false;
end

--[[ 尝试调一个function 这个function可以带可变参数
如果被调用的函数有异常 返回false，退出此方法继续执行其他代码并打印出异常信息；]]
function trycall(func, ...)
    local args = { ... };
    return xpcall(function() func(unpack(args)) end, __TRACKBACK__);
end

function replace(str , pat , repl) return string.gsub(str, pat, repl) end

function trim (s) 
    return s:match "^%s*(.-)%s*$";
end

function GetGameObject(trans , path)

    if trans == nil then
        
        warn("根Obj == nil " .. path);

        return nil;
    end    

    local pathTrans = trans.transform:FindChild(path);
    if pathTrans==nil then pathTrans = trans.transform:Find(path); end

    if pathTrans == nil then
        warn("显示对象路径错误:" .. path);

        return nil;
    end    

    -- return trans.transform:Find(path).gameObject;
    return pathTrans.gameObject;
end    

function GetPathComponent(trans , path , componentName)
    return GetComponent(GetGameObject(trans , path) , componentName);
end    

function GetComponent(trans , componentName)


    if trans == nil then
        
        warn("根Obj == nil " .. componentName);

        return nil;
    end    

    local component = trans:GetComponent(componentName);

    if component == nil then

        warn("显示对象路径错误:" .. componentName);

        return nil;
    end    

    return component;
end   

-- CJT 修改
--[Comment]
-- str : 待分割的字符串
-- split ：分割字符   
-- return : string []
function StringSplit(str, split)
    local rt= {}
    string.gsub(str, '[^'.. split ..']+', function(w) table.insert(rt, w); end );
    return rt;
end

-- CJT 修改
--[Comment]
-- 查找子对象并添加组件
-- _transform 查找的对象
-- _typeName 需要添加的组件名字
function AddComponent(_transform, _typeName)
    if _transform == nil then
        warn("_transform == nil ");
        return nil;
    end

    return _transform.gameObject:AddComponent(_typeName);
end

-- CJT 修改
--[Comment]
-- 查找子对象并添加组件
-- _transform 查找的对象
-- _childPath 子对象的路径
-- _typeName 需要添加的组件名字
function AddPathComponent(_transform, _childPath, _typeName)
    return AddComponent(Child(_transform, _childPath), _typeName);
end

-- CJT 修改
--[Comment]
-- 获取实例组件，如果组件存在就返回，不存在就创建并返回
-- _transform 查找的对象
-- _typeName 需要添加的组件名字
function GetInstanceComponent(_transform, _typeName)
    local tempCom = GetComponent(_transform, _typeName);
    if tempCom ~= nil then
        return tempCom;
    end

    return AddComponent(_transform, _typeName);
end

-- CJT 修改
--[Comment]
-- 根据路径来获取实例组件，如果组件存在就返回，不存在就创建并返回
-- _transform 查找的对象
-- _childPath 子对象的路径
-- _typeName 需要添加的组件名字
function GetPathInstanceComponent(_transform, _childPath, _typeName)
    local tempCom = GetPathComponent(_transform, _childPath, _typeName);
    if tempCom ~= nil then
        return tempCom;
    end

    return AddPathComponent(_transform, _childPath, _typeName);
end

--CF 修改
--将自定义脚本附加到_path路径下的transform上，并返回。
--_class 需要附加的实例对象
--_className 实例对象类名
--_path 需要附加在obj上的路径，相比于transform
--_go 需要传递给实例对象的obj
function ForBindPanelScript(_tran,_class,_className,_path,_go,_paramTable)
	local go=GetGameObject(_tran,_path);
	_go=_go or go;
	--local component=GetComponent(go.transform,"LuaBehaviour"):findBindScript(_className);
	--if(component==nil)then
		PanelManager:BindPanelScript(_class , _className ,go, _createFun~=nil and _createFun or function() _class:Awake(_go,_paramTable); end , function() end  , function() end , function() end);
	--end
end

-- CJT 修改
--[Comment]
-- 查找子对象
-- _transform 查找的对象
-- _childPath 子对象的路径
function Child(_transform, _childPath)
    if _transform == nil then
        warn("根Obj == nil " .. _childPath);
        return nil;
    end

    if IsNilOrEmpty(_childPath) then
        warn("路径 == nil");
        return nil;
    end

    return _transform.transform:FindChild(_childPath);
end

-- CJT 修改
--[Comment]
-- 给Label的组件设置值
-- _transform 查找的对象
-- _text 设置的文本内容值
function SetLabelText(_transform, _text)
    if(_transform == nil) then
        error("********** function SetLabelText _transform value nil **********");
        return;
    end

    local tempLabel = GetComponent(_transform, "UILabel");
    if tempLabel == nil then
        error("********** function SetLabelText " .. _transform.name .. "not UILabel **********");
        return;
    end

    tempLabel.text = _text;
end

-- CJT 修改
--[Comment]
-- 给Label的组件设置值
-- _transform 查找的对象
-- _text 设置的文本内容值
function SetPathLabelText(_transform, _path, _text)
    if(_transform == nil) then
        error("********** function SetLabelText _transform value nil **********");
        return;
    end

    local tempLabel = GetPathComponent(_transform, _path, "UILabel");
    if tempLabel == nil then
        error("********** function SetLabelText " .. _transform.name .. "not UILabel **********");
        return;
    end

    tempLabel.text = _text;
end

-- CJT 修改
--[Comment]
-- 设置对象关闭与启用
-- _obj 需要操作的对象
-- _active 是否激活，
function SetActive(_obj, _active)
    if(_obj == nil) then
        error("***** function SetActive _obj value nil *****");
        return;
    end

    if _active == nil then
        _active = true;
    end
    
    _obj.gameObject:SetActive(_active);
end

-- CJT 修改
--[Comment]
-- 设置对象关闭与启用
-- _obj 需要操作的对象
-- _path 子对象路径
-- _active 是否激活，
function SetPathActive(_obj, _path, _active)
    if(_obj == nil) then
        error("***** function SetActive _obj value nil *****");
        return;
    end

    SetActive(GetGameObject(_obj, _path), _active);
end

-- CJT 修改
--[Comment]
-- 从 LuaBehaviour 返回对应的组件
-- _obj 要查找的对象
-- _name 组件名字（一般是Lua的脚本名）
function GetLuaComponent(_obj, _name)
    if _obj == nil then
        error("function GetLuaComponent _obj value nil");
        return nil;
    end

    if IsNilOrEmpty(_name) then
       error("function GetLuaComponent _name value nil");
       return nil;
    end

    local temp_luaBehaviour = GetComponent(_obj, "LuaBehaviour");
    if temp_luaBehaviour == nil then
        --error("function GetLuaComponent " .. _obj.name .. " LuaBehaviour Component value nil");
        return nil;
    end

    return temp_luaBehaviour:findBindScript(_name);
end

-- CJT 修改
--[Comment]
-- 从 LuaBehaviour 返回对应的组件
-- _obj 要查找的对象
-- _path 对象的子对象路径
-- _name 组件名字（一般是Lua的脚本名）
function GetLuaPathComponent(_obj, _path, _name)
    if IsNilOrEmpty(_path) then
        error("function GetLuaPathComponent _path value nil");
        return nil;
    end

    return GetLuaComponent(GetGameObject(_obj, _path), _name);
end

-- CJT 修改
--[Comment]
-- SessionData类型，转换成 Lua 能使用的格式
-- _sessionData : SessionData
-- _respName : 直接写 proto 的名字就行，格式：mnba.proto.pb. + _respName
function GetProtobufDecode(_sessionData, _respName)
    if _sessionData == nil then
        error("function GetProtobufDecode _sessionData value nil");
        return nil;
    end

    if IsNilOrEmpty(_respName) then
        error("function GetProtobufDecode _respName value nil");
        return nil;
    end

    local luaData = _sessionData.luaReceiveData:ReadBuffer();
    return protobuf.decode("mnba.proto.pb." .. _respName, luaData);
end

-- CJT 修改
--[Comment]
-- 进一步封装 Util.getLuaBufferData
-- 使用 SendData 发送数据的话，不用写那么多代码
-- _bufferData : bufferData
-- _respName : 直接写 proto 的名字就行，格式：mnba.proto.pb. + _respName
function GetProtoBufferData(_bufferData, _respName)
    if _bufferData == nil then
        error("function GetProtoBufferData _bufferData value nil");
        return;
    end

    if IsNilOrEmpty(_respName) then
        error("function GetProtoBufferData _respName value nil");
        return nil;
    end

    local code = protobuf.encode("mnba.proto.pb." .. _respName, _bufferData);
    return Util.getLuaBufferData(code)
end

--取一个数的整数部分
function GetIntPart(x)
    if x <= 0 then
       return math.ceil(x);
    end

    if math.ceil(x) == x then
       x = math.ceil(x);
    else
       x = math.ceil(x) - 1;
    end

    return x;
end

function IsNumber(v)
    local t = type(v);
    if t == "number" then
        return true;
    end

    return false;
end    

function IsString(v)
    local t = type(v);
    if t == "string" then
        return true;
    end

    return false;
end  

function IsBoolean(v)
    local t = type(v);
    if t == "boolean" then
        return true;
    end

    return false;
end  

function IsNilOrEmpty( s )
    if s==nil or s=="" or s=="nil" or s=="null" then return true; end
    return false;
end
--[[
    ToCharArray(“abcdefg”) == {a,b,c,d,e,f,g} 
]]
function ToCharArray( s )
    local tab = {}
    for i=1,#s do
        tab[i] = string.sub(s,i,i);
    end
    return tab;
end


--[[
    空函数
]]

function doNthFunc()
end

--判断table是否为空
function isEmpty(tbl)
    if not tbl then return true end
    assert(type(tbl)=="table", "Function[isEmpty] needs table!")
    if not next(tbl) then
        return true
    end
    return false
end

function castToBool(v)
    if IsNumber(v) then
        if v == 0 then
            return false;
        else
            return true;
        end
    elseif IsString(v) then
        if v == "TRUE" or v=="true" then
            return true;
        elseif v == "FALSE" or v=="false" then    
            return false;
        end 
    elseif IsBoolean(v) then
        return v;          
    end 

    return false; 
end    

--[[
    library 2016-5-12 11:48:37
    SendMessage(string methodName, object params)的替代方法，
    使用限制：1.methodName为非单例lua类的方法时；2.XXXView除外（XXXView中包含methodName方法时，直接用XXXView:methodName(unpack(params))）
    _go GameObject 当前LauTable持有的GameObject
    _funcName string 需要执行的方法名称。如League模块中方法名："SetLeagueLogoId"
    _luaTab Table 拥有_funcName的LuaTable。如League模块LuaTab：{"LeagueView", "MyLeagueInfo"}
    _params Table 执行_funcName方法需要的参数
    _maxLoopCnt int 最大的查找次数，默认100，最大1000.防止死循环
    return int 执行方法的次数
]]
function luaSendMessage(_go, _funcName, _luaTab, _params, _maxLoopCnt)
    local luaBhvr;--LuaBehaviour
    local goTmp;--GameObject
    local luaTab;--LuaTable
    local cnt = 0;

    if (nil == _go) then
        error("functions.lua luaSendMessage() _go is nil");
        return ;
    end
    if (nil == _funcName) then
        error("functions.lua luaSendMessage() _funcName is nil");
        return ;
    end

    _luaTab = _luaTab or {};
    _params = _params or {};
    _maxLoopCnt = _maxLoopCnt or 100;
    _maxLoopCnt = _maxLoopCnt > 1000 and 1000 or _maxLoopCnt;

    luaBhvr = GetComponent(goTmp, "LuaBehaviour");
    if (nil ~= luaBhvr) then
        for i = 1, #_luaTab do
            cnt = cnt + 1;
            if (cnt > _maxLoopCnt) then
                break ;
            end
            luaTab = luaBhvr:findBindScript(_luaTab[i]);
            if (nil ~= luaTab) then
                if (nil ~= luaTab[_funcName]) then
                    luaTab[_funcName](luaTab, unpack(_params));
                end
            end
        end
    end
end

--[[
    library 2016-5-13 17:36:05
    BroadcastMessage(string methodName, object params, SendMessageOptions options = SendMessageOptions.RequireReceiver)的替代方法，
    使用限制：1.methodName为非单例lua类的方法时；2.XXXView除外（XXXView中包含methodName方法时，直接用XXXView:methodName(unpack(params))）
    _go GameObject 当前LauTable持有的GameObject
    _topGo GameObject 顶层GameObject，查找完改层后解除
    _funcName string 需要执行的方法名称。如League模块中方法名："SetLeagueLogoId"
    _luaTab Table 拥有_funcName的LuaTable。如League模块LuaTab：{"LeagueView", "MyLeagueInfo"}
    _params Table 执行_funcName方法需要的参数
    _maxLoopCnt int 最大的查找次数，默认100，最大1000.防止死循环
    return int 执行方法的次数
]]
function luaBroadcastMessage(_go, _funcName, _luaTab, _params, _maxLoopCnt)
    -- local luaBhvr;--LuaBehaviour
    -- local luaTab;--LuaTable
    local goTmp;--GameObject
    local cnt = 0;
    local childCnt = 0;
    local totalCnt = 0;

    if (nil == _go) then
        error("functions.lua luaSendMessage() _go is nil");
        return ;
    end
    
    if (nil == _funcName) then
        error("functions.lua luaSendMessage() _funcName is nil");
        return ;
    end

    _luaTab = _luaTab or {};
    _params = _params or {};
    _maxLoopCnt = _maxLoopCnt or 100;
    _maxLoopCnt = _maxLoopCnt > 1000 and 1000 or _maxLoopCnt;
    totalCnt = _go.transform.childCount;

    -- repeat
    --     cnt = cnt + 1;

    --     if (1 == cnt) then
    --         goTmp = _go;
    --     else
    --         if (childCnt > totalCnt - 1) then
    --             break ;
    --         end
    --         goTmp = _go.transform:GetChild(childCnt).gameObject;
    --         childCnt = childCnt + 1;
    --     end
    -- until(cnt > _maxLoopCnt)

    local function callTabFun(_go)
        local luaBhvr;--LuaBehaviour
        local luaTab;--LuaTable

        luaBhvr = GetComponent(_go, "LuaBehaviour");
        if (nil ~= luaBhvr) then
            for i = 1, #_luaTab do
                luaTab = luaBhvr:findBindScript(_luaTab[i]);
                if (nil ~= luaTab) then
                    if (nil ~= luaTab[_funcName]) then
                        luaTab[_funcName](luaTab, unpack(_params));
                    end
                end
            end
        end
    end

    local function dealChild(_go)
        local _childCnt, _totalCnt = 0, 0;
        if (cnt > _maxLoopCnt) then
            return ;
        end

        _childCnt = 0;
        _totalCnt = _go.transform.childCount;

        callTabFun(_go);

        cnt = cnt + 1;
        for i = 0, _totalCnt - 1 do
            dealChild(_go.transform:GetChild(i).gameObject);
        end
    end

    dealChild(_go);
end

--[[
    library 2016-5-13 17:27:09
    SendMessageUpwards(string methodName, object params)的替代方法，
    使用限制：1.methodName为非单例lua类的方法时；2.XXXView除外（XXXView中包含methodName方法时，直接用XXXView:methodName(unpack(params))）
    _go GameObject 当前LauTable持有的GameObject
    _topGo GameObject 顶层GameObject，查找完改层后解除
    _funcName string 需要执行的方法名称。如League模块中方法名："SetLeagueLogoId"
    _luaTab Table 拥有_funcName的LuaTable。如League模块LuaTab：{"LeagueView", "MyLeagueInfo"}
    _params Table 执行_funcName方法需要的参数
    _maxLoopCnt int 最大的查找次数，默认100，最大1000.防止死循环
    return int 执行方法的次数
]]
function luaSendMessageUpwards(_go, _topGo, _funcName, _luaTab, _params, _maxLoopCnt)
    local luaBhvr;--LuaBehaviour
    local goTmp;--GameObject
    local luaTab;--LuaTable
    local cnt = 0;

    if (nil == _go) then
        error("functions.lua luaSendMessage() _go is nil");
        return ;
    end
    if (nil == _topGo) then
        error("functions.lua luaSendMessage() _topGo is nil");
        return ;
    end
    if (nil == _funcName) then
        error("functions.lua luaSendMessage() _funcName is nil");
        return ;
    end

    _luaTab = _luaTab or {};
    _params = _params or {};
    _maxLoopCnt = _maxLoopCnt or 100;
    _maxLoopCnt = _maxLoopCnt > 1000 and 1000 or _maxLoopCnt;

    repeat
        cnt = cnt + 1;
        if (1 == cnt) then
            goTmp = _go;
        else
            goTmp = goTmp.transform.parent.gameObject;
        end
        luaBhvr = GetComponent(goTmp, "LuaBehaviour");
        if (nil ~= luaBhvr) then
            for i = 1, #_luaTab do
                luaTab = luaBhvr:findBindScript(_luaTab[i]);
                if (nil ~= luaTab) then
                    if (nil ~= luaTab[_funcName]) then
                        luaTab[_funcName](luaTab, unpack(_params));
                    end
                end
            end
        end
    until(cnt > _maxLoopCnt or goTmp == _topGo)
end

--edit by cf
--反正就是没事你就别用了，用了还不注销的直接打死，扔坑，倒垃圾.....踩两脚，放个屁.......
--function writeLog(content,fileName)
--	local teamID=TeamDataProxy.teamId() or 0;

--	if(fileName==nil)then fileName="pvpNeedLog_"..teamID;end
--	local fileW = io.open("D:\\pvp\\"..fileName..".txt",'a');
--	if(fileW~=nil)then
--		fileW:write("\n"..tostring(content));
--		fileW:close()
--	else
--		os.execute("mkdir D:\\pvp\\");
--		writeLog(content,fileName);
--	end
--end


----------------------------slggame---------------------

-------------清理transform下面的所有child
function deleteAllChild(tran_parent)
    
    local len = tran_parent.childCount;
    for k = 1, len  do
        GameObject.Destroy(tran_parent:GetChild(len - k).gameObject);
    end
end

function deleteAllChildExcept(tran_parent, tran_ex)
    
    local len = tran_parent.childCount;
    for k = 1, len  do
        local tran_child = tran_parent:GetChild(len - k)
        if tran_ex ~= tran_child    then
            GameObject.Destroy(tran_child.gameObject);
        end
    end
end

------------------设置父子节点关系----------
function setParent(tran_parent, tran_child)
    tran_child:SetParent(tran_parent, false);
end

--------------------设置父子节点关系、子节点transform reset
function setParentLocal(tran_parent, tran_child)
    
    tran_child:SetParent(tran_parent, false);
    tran_child.localPosition = Vector3.New(0, 0, 0);
    tran_child.localScale = Vector3.New(1, 1, 1);
    tran_child.localEulerAngles = Vector3.New(0, 0, 0);
end

----------------隐藏transform
function hideTransform(tran)
    
    tran.localPosition = Vector3.New(99999, 99999, 99999);
end

function tranToLocalposZero(tran)
    tran.localPosition = Vector3.New(0, 0, 0);
end

-- 数字补0字符串
function zeroNumber(i_num)
    if i_num < 10 then
        i_num = "0" .. i_num;
    end
    return i_num;
end

--数量转换单位显示,保留小数点后两位
function numberToStringUnits(i_number)
    
    if i_number / 10^9 >= 1 then
        i_number = math.floor(i_number / 10^6);
        return (string.format("%.2f", i_number / 10^2).."B");
    elseif i_number / 10^6 >= 1 then
        i_number = math.floor(i_number / 10^4);
        return (string.format("%.2f", i_number / 10^2).."M");
    elseif i_number / 10^3 >= 1 then
        i_number = math.floor(i_number / 10);
        return (string.format("%.2f", i_number / 10^2).."K");
    end 

    return tostring(i_number)
end

--[Comment]
-- 添加延迟调用
-- callBack 回调
-- luaObj 回调接受对象
-- seconds 延迟几秒
-- 这个函数讲返回一个coroutine
function addDelayCall(callBack,luaObj,seconds)
    local coroutine = LuaCallManager.invokeCallWithObj(LuaCallManager,callBack,luaObj,seconds);
    return coroutine;
end

--[Comment]
-- 取消延迟调用
-- coroutine 添加时保存的延迟索引 
function removeDelayCall(coroutine)
    if coroutine~=nil then
        LuaCallManager.cancelInvokeCall(LuaCallManager,coroutine);
    end
end

--[Comment]
-- 添加延迟重复调用
-- callBack 回调
-- luaObj 回调接受对象
-- seconds 延迟几秒
-- 这个函数讲返回一个coroutine
function addDelayLoopCall(callBack,luaObj,seconds)
    local coroutine = LuaCallManager.invokeCallLoopWithObj(LuaCallManager,callBack,luaObj,seconds);
    return coroutine;
end

--[Comment]
-- 取消延迟重复调用
-- coroutine 添加时保存的延迟索引 
function removeDelayLoopCall(coroutine)
    if coroutine~=nil then
        LuaCallManager.cancelInvokeCall(LuaCallManager,coroutine);
    end
end

---四舍五入
function roundOff(num, n)
    if n > 0 then
        local scale = math.pow(10, n-1)
        return math.floor(num / scale + 0.5) * scale
    elseif n < 0 then
        local scale = math.pow(10, n)
        return math.floor(num / scale + 0.5) * scale
    elseif n == 0 then
        return num
    end
end
--判断对象是否是某个lua类 Alex
function typeOfLua(obj , strClassName)
    if type(obj) == "table" and obj.ClassName == strClassName then
        return true;
    end
    return false; 
end 
function getTopSuper(obj)
    if obj ~= nil then
        local _superPre = obj;
        local _super = _superPre.super;
        while _super ~= nil do
            if _super.super ~= nil then
                _superPre = _super;
                _super = _superPre.super;
             else 
                return _superPre;
             end   
        end    
        return obj;
    end
    return nil;    
end    

--判断一个GameObject是否还可以使用
function isDestory(gameObject)
    return gameObject == nil or (gameObject~=nil and gameObject:Equals(nil))  
end  

--[Comment]
--map的values()转化成list
--有感于经常要这么干，麻烦，就写了这个玩意。我感觉会有很多人用。
function MapValuesToList(mapValues)
	local list=List.new();
    for _,v in ipairs(mapValues) do
		list:add(v);
	end
	return list;
end


--[Comment]
--获取子物体或者获取子物体组件或者自身的组件，否则返回nil
--哥很烦每次都要写self.transform:FindChild("dfs"):GetComment("fdsf");
function FindChild(tra,path,component)
	if(tra==nil)then
		error("FindChild Error tra==nil");
		return nil;
	end
	if(IsNilOrEmpty(path))then
		if(IsNilOrEmpty(component))then
			error("FindChild Error path==nil and component==nil");
			return nil;
		else
			return tra:GetComponent(component);
		end
	else
		if(IsNilOrEmpty(component))then
			return tra:FindChild(path);
		else
			return tra:FindChild(path):GetComponent(component);
		end
	end
	error("FindChild Error do nothing path:"..tostring(path).." component:"..tostring(component));
	return nil;
end