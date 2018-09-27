-------------------------------------------------------------
--global 表 数据管理器
-------------------------------------------------------------

-------------------------------------------------------------
 

-------------------------------------------------------------


GlobalConfigManager = {};

local this = GlobalConfigManager;

GlobalConfigManager.configDataMap = nil;

GlobalConfigManager.configMap = nil;

function GlobalConfigManager.init(configSystem)
	this.configMap = Map.new();
	
	this.configDataMap = Map.new();

	local config_string = configSystem:getConfigContent(SLG_ResourceName.globalConfig , false);  
	
	local config_table = ConfigUtil.warpNormalConfig(config_string);

	for i, v in pairs(config_table)  do
		this.configDataMap:put(tonumber(i),v);	
	end
 

end

----------------------------------------------------------------------------------------
-----公用方法---------------------------------------------------------------------------
----------------------------------------------------------------------------------------

--单条属性直接取 string
function GlobalConfigManager.getString(id)

	local value = this.configDataMap:value(id)["value"];
	return tostring(value);

end


--单条属性直接取 number
function GlobalConfigManager.getNumber(id)
	
	local value = this.configDataMap:value(id)["value"];
	return tonumber(value);

end

--根据分隔符 获取相应数据数组
function GlobalConfigManager.getTable(id,str)
	
	local value = this.configDataMap:value(id)["value"];

	local data_table = StrSplit(value , str);

	return data_table;

end