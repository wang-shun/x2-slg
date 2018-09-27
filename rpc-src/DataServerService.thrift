namespace java com.xgame.data.server.message.s2s
 
 
service  DataServerService {

	bool isAvailable()

	void saveBatch(1:list<string> key , 2:list<binary> datas)

	i64 incr(1:string key)

	i64 incrBy(1:string key , 2:i64 value)

	void save(1:string key , 2:binary data)

	binary quary(1:string key)
	
	void hset(1:string key, 2:string filed, 3:binary data)
	
	binary hget(1:string key, 2:string filed)
	
	List<binary> hvals(1:string key)
	
}
 