---------------------------------------------
----rtsType
---------------------------------------------

RtsTypeConfigManager = {};

local this = RtsTypeConfigManager;

RtsTypeConfigManager.configMap = nil;

function RtsTypeConfigManager.init(configSystem)

	local config_string =  configSystem:getConfigContent(SLG_ResourceName.rtsType , false);     
	
	local config_table = ConfigUtil.warpNormalConfig(config_string);
	
	RtsTypeConfigManager.configMap = Map.new();

	for i, v in pairs(config_table)  do
		
		local model = RtsTypeConfigModel.new(v);
	print("CCCCCCCCCVVVVVVVVVVVCCCCCCCCC  " , model.id)
		this.configMap:put(model.id,model);
		
	end

end

----获取一条配置
function RtsTypeConfigManager.getConfig(id)

	return this.configMap:value(id);

end
 