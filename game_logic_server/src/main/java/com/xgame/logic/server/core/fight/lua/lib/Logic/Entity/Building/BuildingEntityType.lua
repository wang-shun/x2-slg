local this = BuildingEntityType;
--building 中的 type 字段
--建筑类型（5围墙,7障碍物 不能变，主要做AI找目标权重值）(0行政大楼，1军事建筑，2资源建筑，3地雷，4空中炸弹，5围墙，6防御塔,7障碍物，8其他)
BuildingEntityType =
{
	Wall = 5,
	Obstacles = 7,
	Other = 0,
	Tower_DianCi = 65,
	Tower_Fire = 64,
	Tower_Missile = 63,
	Res = 2,
}