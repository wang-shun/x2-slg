--------------------------------------------
--客户端配置数据入口
--------------------------------------------

require "lib/PlayerData/ConfigUtil";

--parser
require "lib/PlayerData/BuildingConfigParser";

--defines
require "lib/PlayerData/BuildingDefines";
 
require "lib/PlayerData/ConfigResModel";

require "lib/PlayerData/ConfigGoodsModel";

require "lib/PlayerData/RequireBuildingModel";

require "lib/PlayerData/AttrConfigModel";

require "lib/PlayerData/BuildingEffectModel";

 

--manager

 
require "lib/PlayerData/BuildingConfigManager";
 
require "lib/PlayerData/GlobalConfigManager";
 
require "lib/PlayerData/CustomWeaponConfigManager";
  
require "lib/PlayerData/BuildingConfigModel";
  
require "lib/PlayerData/CustomWeaponConfigModel";
 
require "lib/PlayerData/WeaponPJCtrlConfig";
  
require "lib/PlayerData/SkillConfigManager";

require "lib/PlayerData/BuffConfigManager";

require "lib/PlayerData/AiConfigManager";

require "lib/PlayerData/SkillConfigModel";

require "lib/PlayerData/BuffConfigModel";

require "lib/PlayerData/AiConfigModel";

require "lib/PlayerData/RtsTypeConfigModel";

require "lib/PlayerData/RtsTypeConfigManager";


--------------------------------------------
Config = {};

local this = Config;

function Config.init(configSystem , pjConfig)
	
	this.pjConfig = pjConfig;
	
	Config.customWeaponModel = {};

	Config.customWeaponModel.configMap = Map.new();
	
	print("[battle_init_config] : 初始化建筑配置...");
	--建筑配置
	Config.building = BuildingConfigManager;
	Config.building.init(configSystem);
	
	print("[battle_init_config] : 初始化global...");
	--Global 表
	Config.global = GlobalConfigManager;
	Config.global.init(configSystem);
	
	print("[battle_init_config] : 初始化配件表...");
	--配件
	Config.customWeapon = CustomWeaponConfigManager;
	Config.customWeapon.init(configSystem);
	
	print("[battle_init_config] : 初始化技能...");
	--技能
	Config.skill = SkillConfigManager;
	Config.skill.init(configSystem);

	print("[battle_init_config] : 初始化buff...");
	--buff
	Config.buff = BuffConfigManager;
	Config.buff.init(configSystem);
	
	print("[battle_init_config] : 初始化ai...");
	--ai
	Config.ai = AiConfigManager;
	Config.ai.init(configSystem);
	
	--rtsType
	Config.rtsType = RtsTypeConfigManager;
	Config.rtsType.init(configSystem);
	
	print("[battle_init_config] : 配置文件初始化完毕!!!");
end