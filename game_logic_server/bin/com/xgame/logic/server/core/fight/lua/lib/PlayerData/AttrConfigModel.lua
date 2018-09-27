
---------------------------------------------
----属性配置
---------------------------------------------
AttrConfigModel = class(Object);


function AttrConfigModel:ctor(id,target,value)
	
	self.id = id;			--属性ID
	
	self.target = target;	--属性目标
	
	self.value = value;		--属性值

end