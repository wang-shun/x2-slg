namespace java com.xgame.framework.rpc
 
 
service GateServerService {

	void regiest(1:string ip , 2:i32 port , 3:i32 area)

	void offlineRoleSuccess(1:i64 sessionid)
}
 