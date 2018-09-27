------------------------------------------------
--配置表中 资源配置数据  
------------------------------------------------

ConfigResModel = class(Object);

--id 枚举
--资源类型

--typeCash = 1, 	金币
--typeEarth = 2, 	稀土
--typeOil = 3,		石油
--typeSteel = 4,	钢材
--typeDiamond = 5, 	钻石

function ConfigResModel:ctor(id,num)
	self.type = id;	--资源类型
	self.num = num;	--资源数量
end
