---------------------------------
----围墙逻辑处理工具
---------------------------------
WallUtil = {};

local this = WallUtil;

----自动创建围墙的位置计算
function WallUtil.getCreatePosition(pos1 , pos2)

	--连续创建不足两面墙 或者两面墙不相邻
	if pos1 == nil or not this.isConnectted(pos1 , pos2) then
		return this.getConnectCell(pos2);
	end

	--已经两面墙而且相邻
	local pos = Vector2(pos2.x,pos2.y);
	pos.x = pos.x + (pos2.x - pos1.x);
	pos.y = pos.y + (pos2.y - pos1.y);
	
	if BoardBlockManager.isEmpty(pos.x , pos.y , 1 , 1) then
		return pos;
	end
	local pp = this.getConnectCell(pos2);
	return pp;
end

--判断两面墙是否四方连续
function WallUtil.getConnectCell(pos)
	local aroundList = {};
	aroundList[1] = Vector2(pos.x-1 , pos.y);
	aroundList[2] = Vector2(pos.x , pos.y+1);
	aroundList[3] = Vector2(pos.x+1 , pos.y);
	aroundList[4] = Vector2(pos.x , pos.y-1);

	aroundList[5] = Vector2(pos.x-1 , pos.y+1);
	aroundList[6] = Vector2(pos.x+1 , pos.y+1);
	aroundList[7] = Vector2(pos.x+1 , pos.y-1);
	aroundList[8] = Vector2(pos.x-1 , pos.y+1);
	for i=1,8 do
		local pos = aroundList[i];
		if BoardBlockManager.isEmpty(pos.x , pos.y , 1 , 1) then
			return pos;
		end 
	end
	return nil;
end


--判断两面墙是否四方连续
function WallUtil.isConnectted(pos1 , pos2)
	if pos1==nil or pos2==nil then
		return false;
	end
	return Vector2.Distance(pos1 , pos2) == 1;
end