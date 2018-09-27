namespace java com.xgame.framework.rpc
 


struct W2L_TaskRunData{
    1:i64 taskId,
	2:i32 taskTime,
	3:i32 triggerTime,
	4:i32 startTime,
    5:i32 eType,
    6:i32 queueId,
	7:i64 roleId
	8:i32 count
}

struct RPC_ViewSprites {
    1:list<RPC_Sprite> spritesList
}

struct RPC_Sprite {
    1:i64 uniqueId,
    2:string spriteName,
    3:i32 tx,
    4:i32 ty,
    5:i32 level,
    6:i32 spriteType,
    7:bool isShow,
    8:i32 serverKey,
	9:i64 gangId,
	10:i64 useUid
}

struct W2lPlayerInfo {
	1:i64 uid,
	2:i64 selfUid,
	3:string spriteName,
	4:string gangName,
	5:i32 x,
	6:i32 y,
	7:i32 startTime,
	8:i32 time,
	9:i32 triggerTime,
	10:string soldierJson
}

struct W2lSoldierInfo{
	1:i32 sid,
	2:bool isSystem,
	3:i32 type,
	4:i32 num,
	5:i32 state,
	6:string name,
	7:list<W2lPeijianInfo> peijians
}

struct W2lPeijianInfo{
	1:i32 sid,
	2:i32 location
 }

 struct W2lVectorInfo{
	1:string spriteName,
	2:i64 uid,
	3:i32 x,
	4:i32 y,
	5:i32 tx,
	6:i32 ty,
	7:i32 startTime,
	8:i32 endTime,
	9:i64 selfUid
}

struct W2lResolveConflict{
	1:RPC_Sprite passier,
	2:RPC_Sprite activer,
	3:string passierSoldierJson
	4:string activerSoldierJson
}

struct W2lConflictGoback{
	1:i64 uid,
	2:i32 type,
	3:i32 resourceNum,
	4:string soldierJson,
}

struct W2lSpaceInfo{
	1:i32 viewLineSize,
	2:i32 viewSize
}

service  LogicServerService {

	#world向logic 中sprite 周围的玩家sprite通知增加sprite
	void addSprite(1:RPC_Sprite sprite , 2:i64 noticeSpriteId)

	#移除sprite
	void removeSprite(1:i64 spriteId , 2:i64 noticeSpriteId)

	#执行定时任务
	void runTask(1:W2L_TaskRunData task)

	#world 推送更新sprite信息 , world会广播给这个精灵周围的sprite
	void updateSpriteInfo(1:RPC_Sprite sprite , 2:i64 spriteId)

	#移除定时任务
	void removeTimerTask(1:W2L_TaskRunData task)

	#world 通知 logic 某个玩家退出老服进入新服
	void playerEnterNewServer(1:i64 roleid , 2:i32 newServerKey)

	#world 通知 logic 某个玩家退出成功
	void playerExitSuccess(1:i64 roleid)

	#world 通知 logic 初始化roleinfo
	void initPlayer(1:binary roleList)

	#将某个角色踢下线
	void offlineRole(1:i64 roleId)

	#world向logic请求sprite详细信息(player , monster等world上没有的sprite) , 返回的数据可能是RoleInfo 或 monster
	binary requestSpriteDetail(1:i64 spriteid , 2:i32 spritetype)

	#world向logic发送sprite详细信息(res , player , monster) ,  
	void responseSpriteDetail(1:i64 requestRoleid , 2:i64 spriteid , 3:i32 spritetype , 4:binary spriteData)
	
	void destinationForMe(1:W2lPlayerInfo w2lPlayerInfo )
		
	void resVectorInfo(1:W2lVectorInfo w2lVectorInfo)
	
	void resolveConflict(1:W2lResolveConflict w2lResolveConflict)
	
	void conflictGoback(1:W2lConflictGoback w2lConflictGoback)
	
	void updataSprite(1:RPC_Sprite viewSprites)
	
	void deleteSprite(1:i64 uid)
	
	void worldRegisterSuccess(1:W2lSpaceInfo w2lSpaceInfo)
	
	#心跳
	void ping()
}
 