ResBuildingEntity = class(BuildingEntity)
 
 --建筑类型(type) == 2 的 资源型建筑
function ResBuildingEntity:ctor()
	self.ClassName = "ResBuildingEntity";
	self.leftResNum = 0;
	self.reduceResNum = 0;
end

function ResBuildingEntity:setHealth(v , attackerEntity)

	if v < 0 then
		v = 0;
	end
		
	if self.leftResNum > 0 and v < self:getHealth() then
      		local rate = (self:getHealth() - v) / self:getHealth();
      		self.reduceResNum = Mathf.Round(self.leftResNum * rate);
      		self.leftResNum = self.leftResNum  - self.reduceResNum;
		self:SetBit(EntitySign.BIT_REDUCERESNUM);
	end

	local topSuper = getTopSuper(self);

	if topSuper ~= nil then
		topSuper.setHealth(self , v , attackerEntity);
	end
      	

end