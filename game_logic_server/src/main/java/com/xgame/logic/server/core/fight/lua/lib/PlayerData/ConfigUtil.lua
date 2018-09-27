ConfigUtil = {};


function ConfigUtil.warpNormalConfig(s_cfg , isDebug)
	if(isDebug)then print(s_cfg);end

    local arr_s_dataArr = StrSplit(s_cfg , "\n");

    local nameRow = StrSplit(arr_s_dataArr[2], "\t");
    local s_last = nameRow[#nameRow] --最后一列会多出一个换行
    
    local rawData = {};

    nameRow[#nameRow] = string.sub(s_last, 0, string.len(s_last) - 1)
    for i = 4, #arr_s_dataArr   do
        
        local s_rowData = arr_s_dataArr[i];
        if s_rowData~=nil and s_rowData ~= ""  then
            local arr_s_columnDataArr = StrSplit(s_rowData , "\t");
            local key_1 = tonumber(arr_s_columnDataArr[1]);

            if key_1 ~=nil and key_1~="" then

            	local table_1 = rawData[key_1];

            	if(table_1 == nil)  then
            	    rawData[key_1] = {};
           		end

            	local data = {};
            
            	for i = 1, #arr_s_columnDataArr    do
            		
            		if nameRow[i]~= "#" then
				if isDebug then
					print("item",i , nameRow[i]);
				end	

				--trim add by Alex 不管策划是否加空格，　程序都按照无空格来
				local tmpData = replace(arr_s_columnDataArr[i] , "\n" , "");
				local tmpData = replace(tmpData , "\r" , "");
		    		data[trim(nameRow[i])] = tmpData;
            		end
            
            	end
            
            	rawData[key_1] = data;

            end

        end

    end

    return rawData; 

end


function ConfigUtil.warpListConfig(s_cfg)

    local arr_s_dataArr = StrSplit(s_cfg , "\n");
    local nameRow = StrSplit(arr_s_dataArr[2], "\t");
    local s_last = nameRow[#nameRow] --最后一列会多出一个换行
    
    local rawData = {};

    nameRow[#nameRow] = string.sub(s_last, 0, string.len(s_last) - 1)
    
    for i = 4, #arr_s_dataArr   do
        
        local s_rowData = arr_s_dataArr[i];
        
        if s_rowData~=nil and s_rowData ~= ""  then
            
            local arr_s_columnDataArr = StrSplit(s_rowData , "\t");
            local key_1 = i;

            if key_1 ~=nil and key_1~="" then

            	local table_1 = rawData[key_1];

            	if(table_1 == nil)  then
            	    rawData[key_1] = {};
           		end

            	local data = {};
            
            	for i = 1, #arr_s_columnDataArr    do
            		
            		if nameRow[i]~= "#" then
            			 data[nameRow[i]] = arr_s_columnDataArr[i];
            		end
            
            	end
            
            	rawData[key_1] = data;

            end

        end

    end

    return rawData; 

end



--s_dataSource 原数据字符串
--break_str 分隔符
function ConfigUtil.parseSimpleIntList(s_dataSource,break_str)
	
	if s_dataSource == nil or s_dataSource=="" then
		return {};
	end

	local data_arr = StrSplit(s_dataSource , break_str);
	local data_table = {};
	for i=1,#data_arr do
		if data_arr[i]~=nil and data_arr[i]~="" then
			data_table[i] = tonumber(data_arr[i]);
		end
	end
	return data_table;
end

--s_dataSource 原数据字符串
--break_str 分隔符
function ConfigUtil.parseSimpleStringList(s_dataSource,break_str)
	
	if s_dataSource == nil or s_dataSource=="" then
		return {};
	end

	local data_arr = StrSplit(s_dataSource , break_str);
	local data_table = {};
	for i=1,#data_arr do
		if data_arr[i]~=nil and data_arr[i] ~= "" then
			data_table[i] = tostring(data_arr[i]);
		end
	end
	return data_table;
end

function ConfigUtil.parseConfigResModelTable(s_dataSource)
	
	if s_dataSource==nil or s_dataSource=="" then
		return {};
	end

	local data_arr = StrSplit(s_dataSource , ";");
	local data_table = {};
	for i=1,#data_arr do
		if data_arr[i]~=nil and data_arr[i] ~= "" then
			local data_arr_item = StrSplit(data_arr[i],",");
			data_table[i] = ConfigResModel.new( tonumber( data_arr_item[1] ) , tonumber( data_arr_item[2] ) );
		end
	end
	return data_table;
end

--106,4,3,3:50,100,150;106,4:50,100,150
function ConfigUtil.parseAttrLevelMap(s_dataSource)
	
	local map = Map.new();

	if s_dataSource == nil or s_dataSource=="" then
		return map;
	end

	local data_list = StrSplit(s_dataSource , ";");

	for i=1,#data_list do
		
		local line1 = data_list[i];
		
		if line1~=nil and line1~="" then

			local line2 = StrSplit(line1 , ":");

			if line2~=nil and #line2==2 then

				line3 = line2[1];

				line4 = line2[2];

				local line5 = StrSplit(line3 , ",");

				local id = tonumber(line5[1]);

				local target = tonumber(line5[2]);

				local values = StrSplit(line4 , ",");

				local linetable = {};

				for j=1,#values do

					local value = values[j];

					if value ~=nil and value ~= "" then

						local model = AttrConfigModel.new(id,target,tonumber(value));

						table.insert(linetable , model);

					end

				end

				map:put(id,linetable);

			end

		end

	end

	return map;

end

----example:204,1,2,3:0.05,305,4:0.5;306,4:0.5;307,4:0.5;308,4:0.5
function ConfigUtil.parseAttrTable(s_dataSource)
	
	local data ={};

	if s_dataSource == nil or s_dataSource=="" then
		return data;
	end

	local data_list = StrSplit(s_dataSource , ";");

	for i=1,#data_list do
		
		local line1 = data_list[i];
		
		if line1~=nil and line1~="" then

			local line2 = StrSplit(line1 , ":");


			if line2~=nil and #line2==2 then

				line3 = line2[1];

				line4 = line2[2];

				local line5 = StrSplit(line3 , ",");

				local id = tonumber(line5[1]);

				local value = tonumber(line4);

				--204,1,2,3:0.05 处理多目标情况（如一条属性给所有兵种加战力等等）
				
				for j=2 , #line5 do
					
					local target = tonumber(line5[2]);

					local model = AttrConfigModel.new(id,target,value);

					table.insert(data,model);

				end

			end

		end

	end

	return data;

end


function ConfigUtil.parseItemTable(s_dataSource)
	if s_dataSource==nil or s_dataSource=="" then
		return {};
	end

	local data_list = StrSplit(s_dataSource , ";");
	
	local data_table = {};

	for i=1,#data_list do
		
		local data_list_item = data_list[i];

		if data_list_item ~= nil and data_list_item ~= "" then
			
			local data_list_item_item = StrSplit(data_list_item , "_");
			
			if #data_list_item_item == 2 then

				local itemModel = ConfigGoodsModel.new( tonumber(data_list_item_item[1]) , tonumber(data_list_item_item[2]) );

				table.insert(data_table,itemModel);

			end

		end

	end

	return data_table;

end




--1,100006_1;2,100006_2;3,100006_3,100006_3;
--1,100006_1;2,100006_2;3,100006_3;4,100006_4;5,100006_5;6,100006_6;7,100006_7;8,100006_8;9,100006_9;10,100006_10
--返回Map
function ConfigUtil.parseItemListByLevel(s_dataSource)
	if s_dataSource==nil or s_dataSource=="" then
		return Map.new();
	end
	
	local level_arr = StrSplit(s_dataSource , ";");
	local data_map = Map.new();

	for i=1,#level_arr do
		
		if level_arr[i] ~= nil and level_arr[i]~="" then

			local item_arr = StrSplit(level_arr[i] , ",");

			local level = tonumber(item_arr[1]);
		
			local list_table = {};
		
			for i=2,#item_arr do 

				if item_arr[i] ~=nil and item_arr[i]~="" then

					local item_arr_item = StrSplit(item_arr[i],"_");
					
					local id = tonumber(item_arr_item[1]);
					
					local num = tonumber(item_arr_item[2]);

					local itemModel = ConfigGoodsModel.new(id,num);
					
					table.insert(list_table,itemModel);

				end
				
			end
		
			data_map:put(level,list_table);

		end

	end

	return data_map;

end