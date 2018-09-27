BuffConfigManager = {};

local this = BuffConfigManager;

BuffConfigManager.configMap = nil;

function BuffConfigManager.init(configSystem)
		 
	local config_string = configSystem:getConfigContent(SLG_ResourceName.buff , false); 
	 
	local config_table = ConfigUtil.warpNormalConfig(config_string);
	 
	BuffConfigManager.configMap = Map.new();
	
	for i, v in pairs(config_table)  do
		
		local model = BuffConfigModel.new(v);
		
		this.configMap:put(model.buffId,model);

		--print("buff------------------------" , model.buffId)
	end

end

function BuffConfigManager.getConfig(id)
	return this.configMap:value(id);
end