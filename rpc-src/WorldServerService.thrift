namespace java com.xgame.framework.rpc
 
struct RPC_PhysicalServerInfo {  
    1:string ip,  
    2:i32 port
}  

struct RPC_Position {  
    1:i32 x,  
    2:i32 y
}  

struct RPC_LoginRole {
    1:i64 uniqueId,
    2:i32 tx,
    3:i32 ty,
    4:string roleName,
    5:i32 level
}


struct RPC_ViewSprites {
	1:i64 seftUid
    2:list<RPC_Sprite> spritesList
}

struct RPC_Sprite {
    1:i64 uniqueId,
    2:string spriteName,
    3:i32 tx,
    4:i32 ty,
    5:i32 level,
    6:i32 spriteType,
    7:bool block,
    8:i32 serverKey,
    9:i32 showType  
}

struct RPC_TaskRunData {
    1:i64 taskId,
    2:i64 roleId,
    3:i32 eType,
    4:i32 queueId,
	5:map<string,string> data,
	6:i32 serverKey,
	7:i32 taskTime,
	8:i32 triggerTime, 
	9:i32 startTime,
	10:i32 count
}

struct RPC_UpdateTime {
    1:i64 taskId,
	2:i64 index, 
	3:RPC_TaskTime time
}


struct RPC_TaskTime{
	1:i32 taskTime,
	2:i32 triggerTime,
	3:i32 startTime,
	4:i32 index,
	5:map<string,string> data
}


struct L2WResourceSimpleSprite{
	1:i64 resourceNum,
	2:i64 uid,
	3:string useName,
	4:i32 sumTime,
	5:i32 startTime,
	6:i32 limitNum,
}

struct L2WConflictResult{
	1:i64 taskId,
	2:i32 index,
	3:i32 result,
	4:list<string> destroySoldier
	5:list<string> modSoldier
}

struct L2WDestroySoldier{
	1:i64 soldierId,
	2:i32 num,
}

struct L2WModSoldier{
	1:i64 soldierId,
	2:i32 num,
}


 
service  WorldServerService {

	#逻辑服向世界服注册,当注册的数量达到配置值,则世界服执行部分初始化操作,比如快速执行所有定时任务
	void regiest(1:string ip , 2:i32 port , 3:i32 callPort , 4:i32 group , 5:i32 serverKey)

	void logicInitSuccess(1:i32 serverKey)

	#向WorldServer请求一个合适的物理逻辑服 , worldserver在返回物理服信息之前,将玩家角色数据放到即将返回的物理服上, worldserver会读取这个user上次使用的角色
	RPC_PhysicalServerInfo getPhysicalServer(1:i32 group , 2:i64 roleid  )

	#logicserver 的某个角色登录 worldserver(增加sprite) 
	RPC_Position loginWorld(1:RPC_LoginRole roleinfo , 2:i32 serverKey)

	#通知world移除sprite
	void removeSprite(1:i64 spriteId)
 
	#增加定时任务
	i64 addTimerTask(1:RPC_TaskRunData taskData)

	bool resetTimerTask(1:RPC_UpdateTime taskData)

	#移除定时任务sp
	bool removeTimerTask(1:i64 id)

	#请求周围精灵 
	RPC_ViewSprites requestViewSprites(1:i32 tx , 2:i32 ty)
	
	#点击精灵请求精灵简单信息
	L2WResourceSimpleSprite reqSpritSimpleInfo(1:i64 uid)

	#更新sprite信息,world会广播给周围玩家
	void updateSpriteInfo(1:RPC_Sprite sprite)

	#通知world sprite的位置改变
	void updateSpritePosition(1:i64 spriteid, 2:i32 newX , 3:i32 newY ,4:i32 oldX , 5:i32 oldY)

	#logic 通知 world 某个玩家退出老服进入新服
	void playerExit(1:i64 roleid , 2:i32 newServerKey)

	#logic 通知 world 某个玩家退出成功
	void playerExitSuccess(1:i64 roleid , 2:i32 newServerKey)

	#将某个角色踢下线
	void offlineRole(1:i64 roleId , 2:i64 sessionid , 3:string gateKey)

	#将所有角色踢下线
	void offlineAllRole()

	#获得精灵详细信息
	void requestSpriteDetail(1:i64 spriteid , 2:i64 requestRoleid , 3:i32 serverKey)

	#获得精灵简单信息
	L2WResourceSimpleSprite reqResourceSimpleInfo(1:i64 spriteid)
	
	void resolveConflictResule(1:L2WConflictResult l2wConflictResult)

}
 