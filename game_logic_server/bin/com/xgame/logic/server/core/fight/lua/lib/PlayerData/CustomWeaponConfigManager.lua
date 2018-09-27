---------------------------------------------------------
--peijian.txt
---------------------------------------------------------
CustomWeaponConfigManager = {};

local this = CustomWeaponConfigManager;

CustomWeaponConfigManager.configMap = nil;

--两两排序后的全部列表
CustomWeaponConfigManager.allConfigList = nil;

--两两排序后的全部列表
CustomWeaponConfigManager.allConfigGroupMap = Map.new();

--组组名字典
CustomWeaponConfigManager.groupNameMap = Map.new();

function CustomWeaponConfigManager.init(configSystem)

    this.configMap = Map.new();

    local config = {};

	local s_cfg = configSystem:getConfigContent(SLG_ResourceName.peijianConfig , false); 

	local arr_s_dataArr = StrSplit(s_cfg , "\n");
    
    local nameRow = StrSplit(arr_s_dataArr[2], "\t");

    local s_last = nameRow[#nameRow]
    
    nameRow[#nameRow] = string.sub(s_last, 0, string.len(s_last) - 1)

    for i = 4, #arr_s_dataArr   do
        
        local s_rowData = arr_s_dataArr[i];
        
        if s_rowData ~= ""  then

            local arr_s_columnDataArr = StrSplit(s_rowData , "\t");
            
            local key_1 = tonumber(arr_s_columnDataArr[1]);
            
            local table_1 = config[key_1]
            
            if(table_1 == nil)  then

                config[key_1] = {};
            
            end

            local data = {};
			
            for k = 1, #arr_s_columnDataArr do

				local rowKey = nameRow[k];
				
				rowKey = replace(rowKey , "\13" , "");
				
                data[rowKey] = replace(arr_s_columnDataArr[k], "\13" , "");
            
            end
			
            config[key_1] = data;
        
        end
    end

    for i, v in pairs(config)  do
        
        local model = CustomWeaponConfigModel.new(v);        
        this.configMap:put(model.id,model);

        CustomWeaponConfigManager.groupNameMap:put(model.type6,model.type6Name);
    end
    this.allConfigList = this.configMap:values();
    table.sort(this.allConfigList, CustomWeaponConfigManager.sortItems);
end

function CustomWeaponConfigManager.getConfig(id)

    return this.configMap:value(tonumber(id));

end
--获取配件列表，两两分组
function CustomWeaponConfigManager.getWeaponListByTwo(peiType)

    if this.allConfigGroupMap:containsKey(peiType) then
        return this.allConfigGroupMap:value(peiType);
    end
    local configList = {};
    this.allConfigGroupMap:put(peiType,configList);
    local list = this.allConfigList;
    local index = 1;
    local tempList = List:new();
    for i = 1, #list do

        if peiType == 0 or peiType == list[i].type5 then

            tempList:add(list[i]);
            index = index + 1;

            if i ~= #list then
                if index > 2 then
                    index = 1;
                    table.insert(configList,tempList);
                    tempList = List:new();
                end
            else
                table.insert(configList,tempList);
            end
        end

    end

    return configList;
end

function CustomWeaponConfigManager.sortItems(itemA, itemB)      

     -- if itemA.type6 == itemB.type6 then
        
     --    return itemA.quality > itemB.quality;

     -- else

        return itemA.id < itemB.id;

     -- end
end

----刷新对应语言名字,描述
function CustomWeaponConfigManager.resetLanguage()

    for i = 1 , this.configMap:size() do

        local model  = this.configMap:valueIndex(i);

        if model then
            model:changeLanguage();
        end

    end

end