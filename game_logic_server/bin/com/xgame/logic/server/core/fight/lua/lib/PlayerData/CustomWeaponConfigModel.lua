CustomWeaponConfigModel = class(Object);

function CustomWeaponConfigModel:ctor(t_data)

	--ID	名字	名字	描述	描述	图标	品质（1白2绿3蓝4紫5橙6红）	模型ID	装饰动作脚本ID（无用）	大兵种（1履带 2 轮式 3 战机）	小兵种ID（1 坦克2 自行火炮3 巡航导弹车4 无人机战车5 空对地小型空中单位6 空对空小型空中单位7 工程抢修车8 地对空导弹车9 地对地火箭车10 导弹拦截车11 步兵战车12 机枪步兵13 防空步兵14 电子战车15 对空单点直升机16 对地单点直升机17 对地面伤直升机18 特种直升机)	地面or空中1地面 2空中	单位大小(1小2中3大)	所属槽位（0底盘 1 行动槽位 2 炮塔槽位 3主武器槽位 4副武器槽位 5 弹药槽位 6 炮塔内部 7 车体外部 8 车体内部 ）	配件组	技能ID	行动部分槽位	炮塔槽位	主武器槽位	副武器槽位	弹药槽位	炮塔内部槽位	车体外部槽位	车体内部槽位	水平旋转	垂直旋转	配件属性	生产士兵时间(1个N秒)	生产士兵消耗资源1	生产士兵消耗资源2	生产士兵消耗资源3	生产士兵消耗资源4	修理士兵时间(1个N秒)	修理士兵消耗资源	修理士兵消耗资源	修理士兵消耗资源	修理士兵消耗资源	军营中的士兵消耗石油（每秒）	战斗消耗石油（每秒）	战斗模型缩放	UI缩放比例	UI偏移	配件等级（用于衰减排序）	属性衰减（装配相同配置组会产生属性衰减，填入0表示不衰减）（第一个衰减比例%；第二个衰减比例%；第三个衰减比例%）(按照配件等级进行降序排列)	属性是否加入车体中（装配小兵种，小兵种的属性数值不加入车体中，小兵种被释放后属性才生效）	饼图_结构	饼图_机动	饼图_装甲	饼图_索敌	饼图_火力	饼图_对地|防空（1表示对地，2表示防空，分号后数值表示比例）	战力	水平最大旋转角度	水平角度角速度	负仰角最大角度	正仰角最大角度	仰角角速度	拼接方向（顺时针1-8）
	--id	name	#	desc	#	icon	quality	showId	ACTId	type1	type2	type3	type4	type5	type6	SkillId	cao1	cao2	cao3	cao4	cao5	cao6	cao7	cao8	shuiPing	chuiZhi	attr	time	cost_cash	cost_earth	cost_steel	cost_oil	fix_time	fix_cost_cash	fix_cost_earth	fix_cost_steel	fix_cost_oil	camp_cost_oil	fight_cost_oil	model1	UI1	UI2	lv	decay	is_attr_count	pie_type_1	pie_type_2	pie_type_3	pie_type_4	pie_type_5	pie_type_6	gs_bonus	YawMax	YawSpeed	NegativePitch	PositivePitch	PitchSpeed	fangXiang


	self.id = tonumber(t_data["id"]);											--ID
		
	self.quality = tonumber(t_data["quality"]);

	self.nameId = tonumber(t_data["name"]);
	self.name = ""--Config.chLanguage.getText(self.nameId).."";						--名称

	self.colorName = self.name;--SpriteUtil.getQuilityString(self.name,self.quality)		--

	self.descriptionId = tonumber(t_data["desc"]);
	self.desc = ""--Config.chLanguage.getText( self.descriptionId ).."";	--描述

	self.icon = tostring(t_data["icon"]);										--

	self.showId = tostring(t_data["showId"]);

	self.ACTId = tostring(t_data["ACTId"]);

	self.type1 = tonumber(t_data["type1"]);

	self.type2 = tonumber(t_data["type2"]);

	self.type3 = tonumber(t_data["type3"]);
	
	self.type4 = tonumber(t_data["type4"]);
	
	self.type5 = tonumber(t_data["type5"]);
	
	self.type6 = tonumber(t_data["type6"]);

	self.SkillId = t_data["SkillId"];

	self.cao1 = tonumber(t_data["cao1"]);
	
	self.cao2 = tonumber(t_data["cao2"]);
	
	self.cao3 = tostring(t_data["cao3"]);
	
	self.cao4 = tonumber(t_data["cao4"]);
	
	self.cao5 = tostring(t_data["cao5"]);
	
	self.cao6 = tonumber(t_data["cao6"]);
	
	self.cao7 = tonumber(t_data["cao7"]);
	
	self.cao8 = tonumber(t_data["cao8"]);

	self.shuiPing = tonumber(t_data["shuiPing"]);

	self.chuiZhi = tonumber(t_data["chuiZhi"]);

	--属性 {AttrConfigModel...}
	self.attr = ConfigUtil.parseAttrTable(t_data["attr"]);

	self.time = tonumber(t_data["time"]);

	self.cost_cash = tonumber(t_data["cost_cash"]);

	self.cost_earth = tonumber(t_data["cost_earth"]);
	
	self.cost_steel = tonumber(t_data["cost_steel"]);

	self.cost_oil = tonumber(t_data["cost_oil"]);


	self.fix_time = tonumber(t_data["fix_time"]);

	self.fix_cost_cash = tonumber(t_data["fix_cost_cash"]);

	self.fix_cost_earth = tonumber(t_data["fix_cost_earth"]);

	self.fix_cost_steel = tonumber(t_data["fix_cost_steel"]);

	self.fix_cost_oil = tonumber(t_data["fix_cost_oil"]);

	self.camp_cost_oil = tonumber(t_data["camp_cost_oil"]);

	self.fight_cost_oil = tonumber(t_data["fight_cost_oil"]);

	self.model1 = tonumber(t_data["model1"]);

	self.UI1 = tonumber(t_data["UI1"]);

	self.UI2 = tonumber(t_data["UI2"]);
	
	self.lv = tonumber(t_data["lv"]);

	self.decay = tonumber(t_data["decay"]);

	self.is_attr_count = tonumber(t_data["is_attr_count"]);

	self.pie_type_1 = tonumber(t_data["pie_type_1"]);

	self.pie_type_2 = tonumber(t_data["pie_type_2"]);
	
	self.pie_type_3 = tonumber(t_data["pie_type_3"]);
	
	self.pie_type_4 = tonumber(t_data["pie_type_4"]);
	
	self.pie_type_5 = tonumber(t_data["pie_type_5"]);
	
	self.pie_type_6 = tonumber(t_data["pie_type_6"]);

	self.gs_bonus = tonumber(t_data["gs_bonus"]);

	self.YawMax = tonumber(t_data["YawMax"]);

	self.YawSpeed = tonumber(t_data["YawSpeed"]);

	self.NegativePitch = tonumber(t_data["NegativePitch"]);

	self.PositivePitch = tonumber(t_data["PositivePitch"]);
	
	self.PitchSpeed = tonumber(t_data["PitchSpeed"]);

	self.fangXiang = tonumber(t_data["fangXiang"]);

	--new
	--饼图_结构	饼图_机动	饼图_装甲	饼图_索敌	饼图_火力	对地	对空
	--pie_type_1	pie_type_2	pie_type_3	pie_type_4	pie_type_5	pie_type_6	pie_type_7
	self.pie_type_1 = tonumber(t_data["pie_type_1"]);
	self.pie_type_2 = tonumber(t_data["pie_type_2"]);
	self.pie_type_3 = tonumber(t_data["pie_type_3"]);
	self.pie_type_4 = tonumber(t_data["pie_type_4"]);
	self.pie_type_5 = tonumber(t_data["pie_type_5"]);
	self.pie_type_6 = tonumber(t_data["pie_type_6"]);
	self.pie_type_7 = tonumber(t_data["pie_type_7"]);

	self.type6Id = tonumber(t_data["type6Name"]);
	self.type6Name = "";--Config.chLanguage.getText(self.type6Id);	

	local _aiid = t_data["ai"];
	if not IsNilOrEmpty(_aiid) then
		self.ai = tonumber(_aiid);
	end	

end

function CustomWeaponConfigModel:changeLanguage()
    --self.name = Config.chLanguage.getText(self.nameId).."";		

    --self.desc = Config.chLanguage.getText( self.descriptionId ).."";	
    
	--self.type6Name = Config.chLanguage.getText(self.type6Id);	
end