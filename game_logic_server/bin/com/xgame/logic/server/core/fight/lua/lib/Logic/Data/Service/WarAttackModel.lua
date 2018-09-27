--------------------------------------------
----防守方数据
--------------------------------------------
WarAttackModel = class(Object);

function  WarAttackModel:ctor(data)
	
	self.uid = data.attackUid;			-- 进攻方ID
	
	self.oilNum = data.oilNum;			-- 石油数量
	
	----------------------------------------------------------
	self.soldiers = {};				-- 士兵列表

	print("士兵数量                   " , #data.soldiers)					

	for i=1 , #data.soldiers do
		
		local soldier = WarSoldierModel.new(data.soldiers[i]);
		-- self.soldiers[soldier.index] = soldier;
		table.insert(self.soldiers , soldier);
		
	end
	----------------------------------------------------------
	
end
