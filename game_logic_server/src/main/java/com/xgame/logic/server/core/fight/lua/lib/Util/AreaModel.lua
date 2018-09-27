-----------------------------------------
----区域描述模型
-----------------------------------------
AreaModel = class(Object);

function AreaModel:ctor()

end

--初始化数据
--x     	x坐标
--y     	y坐标
--width 	宽度
--height 	高度
function AreaModel:setData(x,y,width,height)

	self.x = x;

	self.y = y;

	self.width = width;

	self.height = height;
	
end