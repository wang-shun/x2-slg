----------------------------------------------
----建筑表解析器 （实在没办法，这鸟玩意太多了，影响美观）
----------------------------------------------

BuildingConfigParser = {};

----解析原表数据
function BuildingConfigParser.warpData(model,t_data)
 
	model.configID = tonumber(t_data["id"]);				--建筑ID	[int] (id)

	model.nameId = tonumber(t_data["name"]);
	
	model.name = "";--Config.chLanguage.getText(model.nameId).."";	--建筑名称 	[string] (name)

	model.type = tonumber(t_data["type"]);				--建筑类型 	[int] (type)

	model.descId = tonumber(t_data["description"]);
	
	model.desc = "";--Config.chLanguage.getText(model.descId).."";		--建筑描述 	[string] (description)
 
	model.cd = tonumber(t_data["CD"]);					--再生时间	[int] 秒 (CD)

	model.blasting = tonumber(t_data["blasting"])>0;		--是否可被爆破 [bool] (blasting) --0,1

	model.maxLevel = tonumber(t_data["max_lv"]);			--建筑最高等级 [int] (max_lv)

	model.maxCount = tonumber(t_data["max_num"]);		--最大建筑数量	(max_num)

	model.size = tonumber(t_data["size"]);				--占地格子正方形边长  (size)

	model.sortIndex = tonumber(t_data["index"]);			--排序用 (index)

	model.destroyParam = tonumber(t_data["para"]);		--	基地摧毁参数

	model.sound = tostring(t_data["sound"]);			--音效

	model.clearTime = tonumber(t_data["cost_time1"]);	--清理需要时间（单位秒）(cost_time1)

	model.tabType = tonumber(t_data["tab"]);			--创建界面的所属标签页

	model.selectType = tonumber(t_data["type2"])		--建筑选中类型（1不可移动非战斗建筑,2可移动非战斗建筑,3可移动战斗建筑）

-------------------需要通过接口访问的数据-------------------------------------------

---------------每级主基地对应可以建筑的最大数量 访问方式：getMaxCreateNumByHomeLevel	(main_num)
	model.main_num_table = {};
	local main_num_arr = StrSplit(t_data["main_num"], ";");
	if main_num_arr ~= nil and main_num_arr ~= "" then
		for i=1,#main_num_arr do
			model.main_num_table[i] = tonumber(main_num_arr[i]);
		end
	end
--------------升级建筑要求前置建筑物id（可空）	getRequireBuildByLevel (require_id)
	model.requireTable ={};	

	local require_id_str = t_data["require_id"];

    if require_id_str ~=nil and require_id_str ~= ""   then
        
        local require_id_str_level = StrSplit(require_id_str , ";");

        for i=1,#require_id_str_level do

        	local require_level = require_id_str_level[i];
        	
        	if require_level ~= "0" then --0代表此等级没有前置条件

        		local reqList = StrSplit(require_level , ",");

        		model.requireTable[i] = {};

        		for j=1,#reqList do

        			local reqItem = StrSplit(reqList[j],"_");

        			local requireBuildId = tonumber(reqItem[1]);
        			
        			local requireBuildLevel = tonumber(reqItem[2]);
        			
        			
        			local requieBuildingModel = RequireBuildingModel.new(requireBuildId,requireBuildLevel);

        			model.requireTable[i][j] = requieBuildingModel;

        		end

        	end
        end

    end

------------图标信息 (icon)

	model.iconMap = Map.new();		

	local data_icon = t_data["icon"];
	
	if data_icon ~= nil then
		
		local data_icon1 = StrSplit(data_icon , ";");
		
		for i=1,#data_icon1 do
		
			local data_icon2 = data_icon1[i];

			local data_icon3 = StrSplit(data_icon2 , ",");

			local data_icon_level = tonumber(data_icon3[1]);

			local data_icon_name = tostring(data_icon3[2]);

			model.iconMap:put(data_icon_level,data_icon_name);

		end

	end
------------模型数据 (model)
	
	model.modelMap = Map.new();	

	local data_model = t_data["model"];
	
	if data_model ~= nil then
		
		local data_model1 = StrSplit(data_model , ";");
		
		for i=1,#data_model1 do
		
			local data_model2 = data_model1[i];

			local data_model3 = StrSplit(data_model2 , ",");

			local data_model_level = tonumber(data_model3[1]);

			local data_model_name = tostring(data_model3[2]);

			model.modelMap:put(data_model_level,data_model_name);

		end

	end

	model.cost_cash_table = ConfigUtil.parseSimpleIntList(t_data["cost_cash"],";");	--升级消耗钞票(cost_cash)

	model.cost_earth_table = ConfigUtil.parseSimpleIntList(t_data["cost_earth"],";");	--升级消耗稀土(cost_earth)

	model.cost_steel_table = ConfigUtil.parseSimpleIntList(t_data["cost_steel"],";");	--升级消耗钢材(cost_steel)

	model.cost_oil_table = ConfigUtil.parseSimpleIntList(t_data["cost_oil"],";");		--升级消耗石油(cost_oil)

	model.cost_time_table = ConfigUtil.parseSimpleIntList(t_data["cost_time"],";");	--升级需要时间（单位秒）(cost_time)

	model.cost_item_map = ConfigUtil.parseItemListByLevel(t_data["cost_item"]);	--升级消耗道具(道具ID，数量)(cost_item)

	model.clear_cost_res_table = ConfigUtil.parseConfigResModelTable(t_data["cost_type"]);	--清理消耗资源 (cost_type);

	model.awardStrengthList = ConfigUtil.parseSimpleIntList(t_data["strength"],";");	--升级战斗力奖励 (strength)

	model.awardExp = ConfigUtil.parseItemListByLevel(t_data["exp"]);				--升级道具奖励？ (exp)

	model.clearAwardTable = ConfigUtil.parseItemTable(t_data["clearance"]);		--清理奖励(clearance)

	model.attrMap = ConfigUtil.parseAttrLevelMap(t_data["attr"]);				--属性(attr)

	model.functionList = ConfigUtil.parseSimpleIntList(t_data["function"],",");	--点击建筑的菜单

	model.infoList =  ConfigUtil.parseSimpleIntList(t_data["info"],",");		--建筑信息界面所显示的属性列表
	
	model.effectList = {};

	--行政大楼（v1:援建次数，v2:全体攻击%）
	if model.configID == BuildingType.B_XingZhengDaLou then
		
		model.help_build_table =  ConfigUtil.parseSimpleIntList(t_data["v1"],";");

		--204,1,2,3:.01,.02,.03,.04,.05,.06,.07,.08,.09,.1,.11,.12,.13,.14,.15,.16,.17,.18,.19,.2,.21,.22,.23,.24,.25,.26,.27,.28,.29,.3
		local v2str = t_data["v2"];
		local v2strList = StrSplit(t_data["v2"], ":");
		local strv2 = v2strList[2];
		if strv2 ~= nil then
			model.all_attack_table = ConfigUtil.parseSimpleIntList(strv2,",");
		end 

		local em1 = BuildingConfigParser.createBuildingEffect("援建次数：",model.help_build_table);

		local em2 = BuildingConfigParser.createBuildingEffect("全体攻击：",model.all_attack_table,true);

		table.insert(model.effectList , em1 );
		
		table.insert(model.effectList , em2 );

		return;
	end

	--科研大楼（v1：科研加速）
	if model.configID == BuildingType.B_KeXueYuan then

		model.scientific_add_speed_table = ConfigUtil.parseSimpleIntList(t_data["v1"],";");

		local em1 = BuildingConfigParser.createBuildingEffect("科研加速：",model.scientific_add_speed_table,true);

		table.insert(model.effectList , em1 );

		return;
	end

	--生物实验室（v1：植入体生产加速）
	if model.configID == BuildingType.B_ShenWuShiYanShi then

		model.equipment_produce_speed_table = ConfigUtil.parseSimpleIntList(t_data["v1"],";");

		local em1 = BuildingConfigParser.createBuildingEffect("植入体生产加速：",model.equipment_produce_speed_table,true);

		table.insert(model.effectList , em1 );


		return;
	end

	--外事联络处（V1：集结军队数量）
	if model.configID == BuildingType.B_WaiShiLianLuoChu then

		model.mass_troop_count_table = ConfigUtil.parseSimpleIntList(t_data["v1"],";");

		local em = BuildingConfigParser.createBuildingEffect("驻军容量：",model.mass_troop_count_table);

		table.insert(model.effectList , em );


		return;
	end

	--电磁雷达(给别人加的属性)
	if model.configID == BuildingType.B_DianCiLeiDa then
		model.attrToOhterMap = ConfigUtil.parseAttrLevelMap(t_data["v1"]);
		-- 侦察行军速度提高
		-- 侦察行军时间减少

		for i=1 , #model.attrToOhterMap:keys() do
			local key =  model.attrToOhterMap:keys()[i];
			local value = model.attrToOhterMap:value(key);
			local attrName = "";

			if i==1 then
				attrName = "侦察行军速度提高：";
			else
				attrName = "侦察行军时间减少：";
			end

			local valueList = {};

			for j=1,#value do
				table.insert(valueList , value[j].value);
			end

			local em = BuildingConfigParser.createBuildingEffect(attrName,valueList,true);

			table.insert(model.effectList , em );

		end

		return;
	end

	--监狱（v1:关押数量）
	if model.configID == BuildingType.B_JianYu then  

		model.prison_lock_count_table = ConfigUtil.parseSimpleIntList(t_data["v1"],";");

		local em = BuildingConfigParser.createBuildingEffect("关押数量：",model.prison_lock_count_table);

		table.insert(model.effectList ,  em);

		return;
	end

	--贸易站（v1:贸易税率，v2:贸易最大负重）
	if model.configID == BuildingType.B_MaoYiZhan then

		model.trade_rate_table = ConfigUtil.parseSimpleIntList(t_data["v1"],";");

		model.trade_weight_table = ConfigUtil.parseSimpleIntList(t_data["v2"],";");

		local em1 =  BuildingConfigParser.createBuildingEffect("贸易税率：",model.trade_rate_table, true);

		local em2 =  BuildingConfigParser.createBuildingEffect("贸易最大负重：",model.trade_weight_table);

		table.insert(model.effectList , em1 );
		
		table.insert(model.effectList ,  em2 );

		return;
	end

	--军营（v1:出征数量）
	if model.configID == BuildingType.B_JunYing then  	
		
		model.ride_count_table = ConfigUtil.parseSimpleIntList(t_data["v1"],";");

		local em = BuildingConfigParser.createBuildingEffect("出征数量：",model.ride_count_table);

		table.insert(model.effectList ,  em );

		return;
	end

	--旋翼战机工厂、履带战车工厂、轮式战车工厂（v1:造兵数量，v2:解锁系统兵种等级）
	if model.configID == BuildingType.B_ZhiShengJi or model.configID == BuildingType.B_TanKe  or model.configID == BuildingType.B_ZhanChe then  
		
		model.produce_soldier_count_table = ConfigUtil.parseSimpleIntList(t_data["v1"],";");

		model.open_soldier_level_map = Map.new();	--{level, id(global表中的id 对应一个系统兵种)}
		
		local open_soldier_level_arr = StrSplit(t_data["v2"], ";");

		local open_soldier_table = {};
				
		for i=1,#open_soldier_level_arr do

			if open_soldier_level_arr[i] ~= nil and open_soldier_level_arr[i] ~= "" then

				local open_soldier_level_item = StrSplit(open_soldier_level_arr[i],":");
				local id = tonumber(open_soldier_level_item[1]);
				local level = tonumber(open_soldier_level_item[2]);
				model.open_soldier_level_map:put(level,id);
				
				open_soldier_table[level] = id;
				
			end

		end
		
		local em1 = BuildingConfigParser.createBuildingEffect("造兵数量：",model.produce_soldier_count_table);
		
		local em2 = BuildingConfigParser.createBuildingEffect("解锁兵种：",open_soldier_table , false , true);

		table.insert(model.effectList ,  em1 );

		table.insert(model.effectList ,  em2 );
		return;
	end

	 --修理厂（v1:修理空位数，v2:改造空位数）
	if model.configID == BuildingType.B_XiuLiChang then 
		
		model.repair_count_table = ConfigUtil.parseSimpleIntList(t_data["v1"],";");
		
		model.reform_count_table = ConfigUtil.parseSimpleIntList(t_data["v2"],";");
		
		local em1 = BuildingConfigParser.createBuildingEffect("修理空位数：",model.repair_count_table);

		local em2 = BuildingConfigParser.createBuildingEffect("改造空位数：",model.reform_count_table);

		table.insert(model.effectList ,  em1);

		table.insert(model.effectList ,  em2);
		
		return;

	end

	--防御驻地（v1:防守士兵数，v2:警戒范围）
	if model.configID == BuildingType.B_FangShouZhuFang then  
		
		model.defen_soldier_table = ConfigUtil.parseSimpleIntList(t_data["v1"],";");
		
		model.warn_range_table = ConfigUtil.parseSimpleIntList(t_data["v2"],";");
		
		local em1 = BuildingConfigParser.createBuildingEffect("防守士兵数：",model.defen_soldier_table);

		local em2 = BuildingConfigParser.createBuildingEffect("警戒范围：",model.warn_range_table);

		table.insert(model.effectList , em1 );
		
		table.insert(model.effectList , em2 );

		return;
	end

	----歼击炮塔 v2 警戒范围
	if model.configID == BuildingType.B_JianJiPaoTa then  
		model.warn_range_table = ConfigUtil.parseSimpleIntList(t_data["v2"],";");
		local em = BuildingConfigParser.createBuildingEffect("攻击范围：",model.warn_range_table);
		table.insert(model.effectList , em );
	end

	--近防炮塔 v2 警戒范围
	if model.configID == BuildingType.B_JingFangPaoTa then  
		model.warn_range_table = ConfigUtil.parseSimpleIntList(t_data["v2"],";");
		local em = BuildingConfigParser.createBuildingEffect("攻击范围：",model.warn_range_table);
		table.insert(model.effectList , em );
	end

	--防空弹塔 v2 警戒范围
	if model.configID == BuildingType.B_FangKongPaoTa then  
		model.warn_range_table = ConfigUtil.parseSimpleIntList(t_data["v2"],";");
		local em = BuildingConfigParser.createBuildingEffect("攻击范围：",model.warn_range_table);
		table.insert(model.effectList , em );
	end

	--火焰炮 v2 警戒范围
	if model.configID == BuildingType.B_HuoYanPao then  
		model.warn_range_table = ConfigUtil.parseSimpleIntList(t_data["v2"],";");
		local em = BuildingConfigParser.createBuildingEffect("攻击范围：",model.warn_range_table);
		table.insert(model.effectList , em );
	end

	--电磁塔 v2 警戒范围
	if model.configID == BuildingType.B_DianCiTa then  
		model.warn_range_table = ConfigUtil.parseSimpleIntList(t_data["v2"],";");
		local em = BuildingConfigParser.createBuildingEffect("攻击范围：",model.warn_range_table);
		table.insert(model.effectList , em );
	end

	--电磁塔 v2 警戒范围
	if model.configID == BuildingType.B_YingXingZhaDan then  
		model.warn_range_table = ConfigUtil.parseSimpleIntList(t_data["v2"],";");
		local em = BuildingConfigParser.createBuildingEffect("攻击范围：",model.warn_range_table);
		table.insert(model.effectList , em );
	end

	----银行-稀土仓库-石油仓库-钢材仓库（v1:仓库容量）
	if model.configID == BuildingType.B_YinHang or model.configID == BuildingType.B_XiTuCangKu or model.configID == BuildingType.B_ShiYouCangKu or model.configID == BuildingType.B_GangCaiCangKu then  

		model.store_capacity_table = ConfigUtil.parseSimpleIntList(t_data["v1"],";");

		local em = BuildingConfigParser.createBuildingEffect("仓库容量：" , model.store_capacity_table);

		table.insert( model.effectList ,  em);

		return;
	end

	--勘探开发院（v1:矿车数量，v2:采集负重）
	if model.configID == BuildingType.B_Prospect then  
		--1;2;3;4;5;6;7;8;9;10;11;12;13;14;15;16;17;18;19;20;21;22;23;24;25;26;27;28;29;30
		--36000;72000;108000;144000;180000;432000;504000;576000;648000;720000;1188000;1296000;1404000;1512000;1620000;2304000;2448000;2592000;2736000;2880000;3780000;3960000;4140000;4320000;4500000;5616000;5832000;6048000;6264000;6480000;
		
		model.miner_truck_table = ConfigUtil.parseSimpleIntList(t_data["v1"],";");

		model.collect_weight_table = ConfigUtil.parseSimpleIntList(t_data["v2"],";");

		local em1 = BuildingConfigParser.createBuildingEffect("矿车数量：",model.miner_truck_table);

		local em2 = BuildingConfigParser.createBuildingEffect("采集负重：",model.collect_weight_table);

		table.insert(model.effectList ,  em1 );

		table.insert(model.effectList ,  em2 );

		return;
	end

	--采矿车（v1:不同资源的采集速度（每秒））
	if model.configID == BuildingType.B_MiningVehicle then

		--1:3,3.2,3.4,3.6,3.8,4,4.2,4.4,4.6,4.8,5,5.2,5.4,5.6,5.8,6,6.2,6.4,6.6,6.8,7,7.2,7.4,7.6,7.8,8,8.2,8.4,8.6,8.8;
		--2:60,64,68,72,76,80,84,88,92,96,100,104,108,112,116,120,124,128,132,136,140,144,148,152,156,160,164,168,172,176;
		--3:300,320,340,360,380,400,420,440,460,480,500,520,540,560,580,600,620,640,660,680,700,720,740,760,780,800,820,840,860,880;
		--4:300,320,340,360,380,400,420,440,460,480,500,520,540,560,580,600,620,640,660,680,700,720,740,760,780,800,820,840,860,880
		
		model.collect_speed_map = Map.new();

		local collect_arr = StrSplit(t_data["v1"], ";");

		for i=1,#collect_arr do

			local collect_arr_item = collect_arr[i];
			
			if collect_arr_item ~=nil and collect_arr_item ~= "" then

				local collect_data = StrSplit(collect_arr_item, ":");

				if #collect_data == 2 then

					local collect_level = tonumber(collect_data[1]);

					local collect_level_table = ConfigUtil.parseSimpleIntList(collect_data[2],",");

					model.collect_speed_map:put(collect_level,collect_level_table);


					----------------------------------------------------------------
					local effectName = "采集金矿速度：";

					if collect_level==1 then
						effectName = "采集金矿速度："
					elseif collect_level==2 then
						effectName = "采集石油速度："
					elseif collect_level == 3 then
						effectName = "采集稀土速度："
					else
						effectName = "采集钢材速度："
					end

					local em = BuildingConfigParser.createBuildingEffect(effectName,collect_level_table);

					table.insert(model.effectList , em );
					----------------------------------------------------------------

				end

			end

		end

		return;
		
	end

	--add by Alex 2017-2-20
	if not IsNilOrEmpty(t_data["skill_id"]) then
		model.skillArr = List.new();
		local _skillArr = StrSplit(t_data["skill_id"] , ";");
		for i = 1 , #_skillArr , 1 do
			model.skillArr:add(tonumber(_skillArr[i]));
		end	
	end

	if not IsNilOrEmpty(t_data["ai"]) then
		model.ai = tonumber(t_data["ai"]);
	end
end



function BuildingConfigParser.createBuildingEffect(name,valueList,isPrecent , isOpenSoldier)
	
	local  vlist = {};

	if isPrecent ~= nil and isPrecent==true then
		
		for i = 1 , #valueList do

			table.insert( vlist , (tonumber(valueList[i])*100).."%" );

		end

	else

		vlist = valueList;

	end

	local effect = BuildingEffectModel.new(name,vlist,isOpenSoldier);

	return effect;

end