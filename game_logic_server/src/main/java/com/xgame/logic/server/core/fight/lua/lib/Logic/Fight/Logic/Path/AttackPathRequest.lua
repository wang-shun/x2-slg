AttackPathRequest = class(Object)

function AttackPathRequest:ctor()
            self.ClassName = "AttackPathRequest";
	self.pathType = MathConst.Zero;
	self.pathingTroop = nil;
	self.target = nil;
	self.simFrameRequested = MathConst.Zero;
	self.simLastPathfind = MathConst.Zero;
	self.pathRequested = false;
	self.pathAvailable = false;
	self.parkingForWait = false;
	self.location = FVector3.new(0,0,0);
	self.pathData = AttackPathData.new();
end	

function AttackPathRequest:clear(owner)
        self.parkingForWait = false;
        self.pathType = PathType.None;
        self.pathingTroop = owner;
        self.target = nil;
        self.simFrameRequested = 0;
        if owner.currentBattle ~= nil then
            self.simFrameRequested = owner.currentBattle.simTimestep;
        end
        self.pathRequested = false;
        self.pathAvailable = false;
        self.pathData:clear();
end

function AttackPathRequest:needsPath()
        return self.pathRequested;
end

function AttackPathRequest:simFrame()
        local _simFrameRequested = self.simFrameRequested;

        local _simLastPathfind = self.simLastPathfind;

        if _simLastPathfind > _simFrameRequested then
            _simFrameRequested = _simLastPathfind;
        end

        return _simFrameRequested;
end

function AttackPathRequest:didPathfind(frame)
        self.simLastPathfind = frame;
        if self.pathData.noParking then
            self.simLastPathfind = self.simLastPathfind  - 5;
        end
end