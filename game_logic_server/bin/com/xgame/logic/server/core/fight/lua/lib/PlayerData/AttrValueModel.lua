--------------------------------------------------
-----AttrbutePro.proto
--------------------------------------------------
AttrValueModel = class(Object);

function AttrValueModel:ctor(data,nodeId)
	
	self.id = data.attributeId;

	self.value = data.value;

	self.nodeId = nodeId;	--
	
	 print("-----------------attr---------------------->>>>>id:"..self.id.." value:"..self.value);

end


function AttrValueModel:toString()
    
    print("id:"..self.id.." value:"..self.value);
    
end