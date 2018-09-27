require "lib/Common/Object"
require "lib/Common/print_r"
require "lib/Common/Bit"
require "lib/Common/functions"
require "lib/Common/List"
require "lib/Common/List0"
require "lib/Common/Map"
require "lib/Common/Mathf"
require "Config"

require "lib/PlayerData/AttrDefine"
require "lib/PlayerData/ResType"
require "lib/PlayerData/SLG_ResourceDefine"
require "lib/PlayerData/WeaponPJCtrlConfig"
require "lib/PlayerData/ArmorType"
require "lib/PlayerData/BuildInfo"
require "lib/PlayerData/BuildingType"
require "lib/PlayerData/CustomWeapon"
require "lib/PlayerData/SoldierModel"
require "lib/PlayerData/DesignMapModel"
require "lib/PlayerData/AttrValueModel"
require "lib/PlayerData/PartModel"

 
 



require "lib/EngineImport"
require "ServerFightManager"

--WarSoldierBean
function convertJaveWarSoldierBeanToLua(javaWarSoldierBean)

	local javaSoldierBean = javaWarSoldierBean.soldier;--java FullSoldierBean

	local warSoldierModel = {};
		
	warSoldierModel.ClassName = "WarSoldierModel";
	
	warSoldierModel.index = javaWarSoldierBean.index;
	
	local soldierData = SoldierModel.new();
	
	warSoldierModel.soldier = soldierData;
	
	print("aaaaaaaaaaaaaaaaaaaaaa111" , javaSoldierBean , javaSoldierBean.soldier.num)
	local simFullBean = {};
		print("aaaaaaaaaaaaaaaaaaaaaa222" , javaSoldierBean.soldier)
	simFullBean["soldier"] = {soldierId = javaSoldierBean.soldier.soldierId , num = javaSoldierBean.soldier.num};
		print("aaaaaaaaaaaaaaaaaaaaaa333")
	simFullBean["designMap"] = {};
	
	simFullBean["designMap"].id = javaSoldierBean.designMap.id;
	
	simFullBean["designMap"].type = javaSoldierBean.designMap.type;
	
	simFullBean["designMap"].systemIndex = javaSoldierBean.designMap.systemIndex;
	
	simFullBean["designMap"].index = javaSoldierBean.designMap.index;
	
	simFullBean["designMap"].buildIndex = javaSoldierBean.designMap.buildIndex;
	
	simFullBean["designMap"].version = javaSoldierBean.designMap.version;
	
	simFullBean["designMap"].name = javaSoldierBean.designMap.name;
	
	simFullBean["designMap"].unlock = javaSoldierBean.designMap.unlock;
	
	simFullBean["designMap"].partList = {};
	
	print("AAAAAAAAAAAAAAAAAAAAAAAA1111  designMap   " , javaSoldierBean.designMap.partList:size())
	
	for i1 = 0 , javaSoldierBean.designMap.partList:size() - 1, 1 do
		
		print("BBBBBBBBBBBBBBBBBB  " ,javaSoldierBean.designMap.partList:get(i1) )
		table.insert(simFullBean["designMap"].partList , javaSoldierBean.designMap.partList:get(i1));  
	
	end
	
	simFullBean.attrList = {};
	
	for i1 = 0 , javaSoldierBean.attrList:size() - 1, 1 do
		
		local attr = javaSoldierBean.attrList:get(i1);
		
		table.insert(simFullBean.attrList , {attributeId=attr.attributeId , value=attr.value});  
	
	end
	
	soldierData:setFullSoldierBean(simFullBean);
	
	warSoldierModel.warAttr = WarAttrModel.new(javaWarSoldierBean.warAttr);
	
	warSoldierModel.fightPower = javaWarSoldierBean.fightPower;						--伤亡士兵的战力(rts无用)
	
	warSoldierModel.deadNum = javaWarSoldierBean.deadNum;						--死亡士兵数量(rts无用)
	
	warSoldierModel.playerId = javaWarSoldierBean.playerId;							--玩家id(实时 rts 无用)
	
	warSoldierModel.position = javaWarSoldierBean.position;							--位置(实时 rts 无用)
	

	
	print("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^22222222  convertJaveWarSoldierBeanToLua" )
	
	return warSoldierModel;

end



function convertJaveWarBuildingModelToLua(JavaBuildingData)
	 
	local warBuildingModel = {};
	
	warBuildingModel.buildInfo = BuildInfo.new();			--建筑信息
	
	warBuildingModel.buildInfo:warpData(JavaBuildingData.building);

	warBuildingModel.cellx = JavaBuildingData.transform.vector2Bean.x;	--建筑坐标x
	
	warBuildingModel.celly = JavaBuildingData.transform.vector2Bean.y;	--建筑坐标y

	warBuildingModel.warAttr = WarAttrModel.new(JavaBuildingData.warAttr);	--建筑战斗属性
	
	warBuildingModel.leftResNum = JavaBuildingData.resourceNum;
	
	return warBuildingModel;
end

function convertJavaDefendSoldierBeanToWarDefenSoldierModel(javaDefendSoldierBean)
	local warDefenSoldierModel = {};
	
	warDefenSoldierModel.buildingUid = javaDefendSoldierBean.buildingUid;
	
	warDefenSoldierModel.soldier = convertJaveWarSoldierBeanToLua(javaDefendSoldierBean.soldier);
	
	return warDefenSoldierModel;
end

function convertJaveWarDataToLua(java_resWarDataMessage)

	print("[battle_init_j2l] : 将java战斗对象转化成lua对象..." , java_resWarDataMessage.battleType)

	local warDataModel = {};
	
	warDataModel.battleType = java_resWarDataMessage.battleType;
	
	warDataModel.battleId = java_resWarDataMessage.battleId;
	
	local warAttackModel = {};
	
	local warDefenModel = {};
	
	warDataModel.attackData = warAttackModel;
	
	warDataModel.defenData = warDefenModel;
	
	
	print("[battle_init_j2l] : 转换 attacker 数据...")
	---------------------------转换 attacker 数据
	
	local attackData = java_resWarDataMessage.attackData;
	
	warAttackModel.uid = attackData.attackUid;
	
	warAttackModel.oilNum = attackData.oilNum;
	
	warAttackModel.soldiers = {};
	
	for i = 0 , attackData.soldiers:size() - 1, 1 do
		
		local javaWarSoldierBean = attackData.soldiers:get(i);--WarSoldierBean(java)
		
		local warSoldierModel = convertJaveWarSoldierBeanToLua(javaWarSoldierBean);

		table.insert(warAttackModel.soldiers , warSoldierModel);  
	end
	
	
	print("[battle_init_j2l] : 转换  防御者  数据...")
	---------------------------转换  防御者  数据
	
	local defenData = java_resWarDataMessage.defenData;
	
	warDefenModel.uid = defenData.defendUid;
	
	warDefenModel.buildings = {};
	
	warDefenModel.soldiers = {};
	
	for i = 0 , defenData.buildings:size() - 1, 1 do
		local building = convertJaveWarBuildingModelToLua(defenData.buildings:get(i));
		
		table.insert(warDefenModel.buildings , building);  
	end
	
	for i = 0 , defenData.soldiers:size() - 1, 1 do
		
		local javaDefendSoldierBean = defenData.soldiers:get(i);--DefendSoldierBean(java)
		
		local warDefenSoldierModel = convertJavaDefendSoldierBeanToWarDefenSoldierModel(javaDefendSoldierBean);

		table.insert(warDefenModel.soldiers , warDefenSoldierModel);  
	end
	
	print("[battle_init_j2l] : 转换完毕!!!");
	
	return warDataModel;
end

function battleStart(java_resWarDataMessage , systemConfig , pjConfig , warReport , fps , calcTime)

	Board.init();
	
	print("[battle_init] : 战斗计算开始 battleId =" , java_resWarDataMessage.battleId);
	
	Config.init(systemConfig , pjConfig)
	
	local luaWarData = convertJaveWarDataToLua(java_resWarDataMessage);
	
	printDetail(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" ,luaWarData)
	printDetail(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" ,luaWarData.defenData)
	printDetail(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" ,luaWarData.attackData)
	
	ServerFightManager.FightCalc(luaWarData , warReport , fps , calcTime);
end
