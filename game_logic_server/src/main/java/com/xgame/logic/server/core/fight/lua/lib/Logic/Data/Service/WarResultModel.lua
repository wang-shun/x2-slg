---------------------------------------
----战报数据
---------------------------------------
WarResultModel = class(Object);

function WarResultModel:ctor(data)

	self.winUid = data.winUid;				--获胜一方id
	
	self.destroyLevel = data.destroyLevel;	--摧毁程度：0-100
	
	self.moneyNum = data.moneyNum;			--获得金币数量
	
	self.earthNum = data.earthNum;			--获得稀土数量
	
	self.oilNum = data.oilNum;				--获得石油数量
	
	self.steelNum = data.steelNum;			--获得钢材数量
	
	----------------------------------------------------------
	
	self.soldiers = {};						--进攻方伤兵情况

	for i=1 , #data.attackSoldierBean do

		local soldier = WarSoldierModel.new(data.attackSoldierBean[i]);
		
		table.insert(self.soldiers , soldier);

	end
	
	----------------------------------------------------------

end