package com.xgame.data.server;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import com.xgame.framework.rpc.DataServerService;

@Slf4j
@Component
public class DataServerServiceImpl implements DataServerService.Iface {

	@Value("${xgame.data.server.dbhost}")
	private String ip;

	@Value("${xgame.data.server.dbport}")
	private int port;

	@Value("${xgame.data.server.dbpass}")
	private String pass;

	private Jedis dbClient;

	private static Pipeline p;

	@PostConstruct
	void init() {
		dbClient = new Jedis(ip, port, 60 * 1000);

		dbClient.auth(pass);

		log.debug("redis Server linsten  {}:{}", ip, port);

		p = dbClient.pipelined();
	}

	@Override
	public boolean isAvailable() throws TException {

		if (dbClient != null && dbClient.isConnected()) {
			return true;
		}

		return false;
	}

	@Override
	public void saveBatch(List<String> key, List<ByteBuffer> datas)
			throws TException {

		log.debug("db saveBatch ");

		if (!isAvailable()) {
			log.error("db is unAvailable!!!!");
			return;
		}

		for (int i = 0; i < datas.size(); i++) {
			p.set(key.get(i).getBytes(), datas.get(i).array());
		}
		p.sync();
	}

	@Override
	public long incr(String key) throws TException {

		log.debug("db incr :{}", key);

		if (!isAvailable()) {
			log.error("db is unAvailable!!!!");
			return -1;
		}

		return dbClient.incr(key);
	}

	@Override
	public long incrBy(String key, long value) throws TException {

		log.debug("db incr :{}", key);

		if (!isAvailable()) {
			log.error("db is unAvailable!!!!");
			return -1L;
		}

		return dbClient.incrBy(key, value);
	}

	@Override
	public void save(String key, ByteBuffer data) throws TException {

		if (!isAvailable()) {
			log.error("db is unAvailable!!!!");
			return;
		}

		byte[] keyByte = key.getBytes();

		dbClient.set(keyByte, data.array());
	}

	@Override
	public ByteBuffer quary(String key) throws TException {

		// log.debug("db quary :{}", key);

		if (!isAvailable()) {
			log.error("db is unAvailable!!!!");
			return null;
		}

		byte[] bytes = dbClient.get(key.getBytes());

		ByteBuffer buffer = null;

		if (bytes != null) {
			buffer = ByteBuffer.wrap(bytes);
			return buffer;
		} else {
			buffer = ByteBuffer.wrap(new byte[0]);
		}

		return buffer;
	}
	
	@Override
	public void hset(String key, String filed, ByteBuffer data) throws TException {
		
		if (!isAvailable()) {
			log.error("db is unAvailable!!!!");
			return;
		}
		
		dbClient.hsetnx(key.getBytes(), filed.getBytes(), data.array());
	}
	
	@Override
	public ByteBuffer hget(String key, String filed) throws TException {
		
		if (!isAvailable()) {
			log.error("db is unAvailable!!!!");
			return null;
		}
		
		
		byte[] bytes = dbClient.hget(key.getBytes(), filed.getBytes());
		
		ByteBuffer buffer = null;

		if (bytes != null) {
			buffer = ByteBuffer.wrap(bytes);
			return buffer;
		} else {
			buffer = ByteBuffer.wrap(new byte[0]);
		}

		return buffer;
	}
	
	@Override
	public List<ByteBuffer> hvals(String key) throws TException {
		if (!isAvailable()) {
			log.error("db is unAvailable!!!!");
			return null;
		}
		
		
		List<byte[]> bytes = dbClient.hvals(key.getBytes());
		
		
		List<ByteBuffer> buffers = new ArrayList<ByteBuffer>();
		if(bytes != null && !bytes.isEmpty()) {
			for(byte[] byt : bytes) {
				ByteBuffer byteBuffer = ByteBuffer.wrap(byt);
				buffers.add(byteBuffer);
			}
		}
		
		return buffers;
	}
	
	@Override
	public List<ByteBuffer> queryRoleInfoList() throws TException {
		if (!isAvailable()) {
			log.error("db is unAvailable!!!!");
			return null;
		}
		
		List<ByteBuffer> list = new ArrayList<>();
//		HashMap<String, Long> userNameMap = new HashMap<>();
//		HashMap<String, Long> playerNameMap = new HashMap<>();
//		Set<String> keys = dbClient.keys(StringUtils.join(DBKey.KEY_ROLE, ":*"));
//		if(keys != null && !keys.isEmpty()) {
//			for(String key : keys) {
//				byte[] bytes = dbClient.get(key.getBytes());
//				if (bytes != null) {
//					RoleInfo  roleInfo = SerializeUtil.simpleDeSerialize(bytes, RoleInfo.class);
//					userNameMap.put(roleInfo.basics.userName, roleInfo.basics.roleId);
//					playerNameMap.put(roleInfo.basics.roleName, roleInfo.basics.roleId);
//				} 
//			}
//		}
//		
//		LoadRoleInfo loadRoleInfo = new LoadRoleInfo();
//		loadRoleInfo.userNameMap.putAll(userNameMap);
//		loadRoleInfo.roleNameMap.putAll(playerNameMap);
//		
//		// 序列化
//		ByteBuffer byteBuffer = SerializeUtil.simpleSerializeByteBuffer(loadRoleInfo);
//		list.add(byteBuffer);
		return list;
	}

	@Override
	public void remove(String key) throws TException {
		if (!isAvailable()) {
			log.error("db is unAvailable!!!!");
			return;
		}
		
		dbClient.del(key);
	}
}
