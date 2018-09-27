AiConfigManager = {};

local this = AiConfigManager;

AiConfigManager.configMap = nil;

function AiConfigManager.init(configSystem)
		 
	local config_string = configSystem:getConfigContent(SLG_ResourceName.ai , false);  
	 
	local config_table = ConfigUtil.warpNormalConfig(config_string);
	 
	AiConfigManager.configMap = Map.new();
	
	for i, v in pairs(config_table)  do
		
		local model = AiConfigModel.new(v);

		this.configMap:put(model.id,model);
	end

end

function AiConfigManager.getConfig(id)
	print("get aiid " , id)
	return this.configMap:value(id);
end