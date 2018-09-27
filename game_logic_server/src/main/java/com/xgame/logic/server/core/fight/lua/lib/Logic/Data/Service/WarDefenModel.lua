--------------------------------------------
----防守方数据
--------------------------------------------
WarDefenModel = class(Object);

function  WarDefenModel:ctor(data)
	
	self.uid = data.defendUid;				--防守方Uid

	----------------------------------------------------------------------
	self.buildings = {};					--防守方建筑数据 WarBuildingModel...

	for i=1,#data.buildings do

		local buildingModel = WarBuildingModel.new(data.buildings[i]);

		table.insert(self.buildings , buildingModel);

	end
	----------------------------------------------------------------------

	self.soldiers = {};						--敌方防守数据

	for i=1 , #data.soldiers do

		local soldier = WarDefenSoldierModel.new(data.soldiers[i]);

		table.insert(self.soldiers , soldier);

	end

end
