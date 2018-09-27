-------------------------------------------
--配置中的物品  用于 奖励、消耗、等等等等
-------------------------------------------
ConfigGoodsModel = class(Object);

ConfigGoodsModelType = 
{
	Equipment = 5,	--植入体 Equipment 表5位数
	Item = 6,		--道具Item 表 6位数
	Peijian = 7,	--配件 Peijian表7位数。
}


function ConfigGoodsModel:ctor(id,num)
	
	self.id = tonumber(id);	--道具ID  

	self.type = #tostring(id);	--ConfigGoodsModelType 中定义

	self.num = tonumber(num);	--道具数量

end
