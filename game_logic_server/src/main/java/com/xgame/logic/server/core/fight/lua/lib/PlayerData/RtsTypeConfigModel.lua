------------------------------------------------------
----rtstype
------------------------------------------------------
RtsTypeConfigModel = class(Object);

function RtsTypeConfigModel:ctor(t_data)
	
	self.id = tonumber(t_data["id"]);										 
	self.deathRateAtk = tonumber(t_data["deathRateAtk"]);
	self.campRateAtk = tonumber(t_data["campRateAtk"]);				 
 	self.ignoreHospitalLimitAtk = tonumber(t_data["IgnoreHospitalLimitAtk"]);	
 	self.deathRateDef = tonumber(t_data["deathRateDef"]);	
 	self.campRateDef = tonumber(t_data["campRateDef"]);	
 	self.ignoreHospitalLimitDef = tonumber(t_data["IgnoreHospitalLimitDef"]);		
 	self.time = tonumber(t_data["time"]);	
 	
 	print("CCCCCCCCCVVV2222VVVVVVVVCCCCCCCCC  " , self.id , t_data["time"] , self.time , self)
end

 