WeaponPJCtrlConfig = class(Object);

function WeaponPJCtrlConfig:ctor(arr)

	self.rotationAxis = tonumber(arr[1]);						
	
	self.maxRotation = tonumber(arr[2]);							
	
	self.minRotation = tonumber(arr[3]);		
	
	self.cherckRotationAngle = tonumber(arr[4]);

	self.pzAxis = tonumber(arr[5]);	

	self.subList = nil;
	if #arr > 5 then
		self.subList = {};
		for i = 6 , #arr do
			local nameStr = arr[i];
		    local arr = {};
		    for w in string.gmatch(nameStr,"%S+") do
		      table.insert(arr,w);
		    end
		    nameStr = "";
		    for i = 1, #arr do
		      if arr[i] ~= "" then
		        nameStr = nameStr..arr[i];
		      end
		    end
			table.insert(self.subList, nameStr);
		end	

	end

end