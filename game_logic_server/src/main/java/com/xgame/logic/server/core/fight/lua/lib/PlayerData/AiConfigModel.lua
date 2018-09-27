AiConfigModel = class(Object);

function AiConfigModel:ctor(t_data)
	self.id = tonumber(t_data.id);

	--是否反击
	local _isBack = tonumber(t_data.isBack);

	if _isBack == 0 then
		self.isBack = false;
	else
		self.isBack = true;
	end		
 

	--是否是飞行单位
	local _isFly = tonumber(t_data.isFly);

	if _isFly == 0 then
		self.isFly = false;
	else
		self.isFly = true;
	end		
	--释放技能目标的阵营（1.敌方；2.自方）
	self.targetCamp = tonumber(t_data.targetCamp);

	--可攻击的兵种（填入小兵种id_伤害加成,多个用分号分隔）
	self.attackTroops = t_data.attackTroops;

	--可攻击的建筑（建筑表type字段)(0行政大楼，1军事建筑，2资源建筑，3地雷，4空中炸弹，5围墙，6防御塔,7障碍物，8其他)
	self.attackBuildings = t_data.attackBuildings;

	--优先攻击兵种
	self.priorityTroops = t_data.priorityTroops;

	--优先攻击建筑
	self.priorityBuildings = t_data.priorityBuildings;

	--是否跃墙攻击
	local _canShootOverWall = tonumber(t_data.canShootOverWall);

	if _canShootOverWall == 0 then
		self.canShootOverWall = false;
	else
		self.canShootOverWall = true;
	end		

	--是否能打墙
	local _canShootWall = tonumber(t_data.canShootWall);

	if _canShootWall == 0 then
		self.canShootWall = false;
	else
		self.canShootWall = true;
	end		

end	