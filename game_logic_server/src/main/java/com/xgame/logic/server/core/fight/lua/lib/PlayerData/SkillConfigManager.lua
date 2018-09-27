SkillConfigManager = {};

local this = SkillConfigManager;

SkillConfigManager.configMap = nil;

function SkillConfigManager.init(configSystem)
		 
	local config_string = configSystem:getConfigContent(SLG_ResourceName.skill , false); 
	 
	local config_table = ConfigUtil.warpNormalConfig(config_string);
	 
	SkillConfigManager.configMap = Map.new();
	
	for i, v in pairs(config_table)  do
		
		local model = SkillConfigModel.new(v);
	 
		this.configMap:put(model.id,model);
	end
end

function SkillConfigManager.getConfig(id)
	return this.configMap:value(id);
end