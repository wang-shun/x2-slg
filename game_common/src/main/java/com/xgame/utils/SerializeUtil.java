package com.xgame.utils;


public class SerializeUtil {

//
//	// -----------------------stuff-------------------------------
//	/**
//	 * 序列化
//	 * @param object
//	 * @return
//	 */
//	public static <T> byte[] stuffSerialize(T object) {
//		try {
//			byte[] bytes = JsonUtil.toJSONString(object).getBytes("UTF-8");
//			return bytes;
//		} catch(Exception e) {
//			log.error("序列化失败:",e);
//		}
//		return null;
//	}
//	
//	/**
//	 * 反序列化stuff
//	 * @param bytes
//	 * @param targetClass
//	 * @return
//	 */
//	public static <T> T simpleDeSerialize(byte[] bytes, Class<T> targetClass) {
//		try {
//			T object = JsonUtil.parseObject(new String(bytes, "UTF-8"), targetClass);
//			return object;
//		}catch(Exception e) {
//			log.error("序列化异常:", e);
//		}
//		
//		return null;
//	}
//	
//	
//	public static <T> T simpleDeSerialize(ByteBuffer buffer, Class<T> targetClass) {
//		try {
//			byte[] bytes = buffer.array();
//			if (buffer.position() > 0) {
//				bytes = new byte[buffer.remaining()];
//				buffer.get(bytes, 0, bytes.length);
//			}
//			return simpleDeSerialize(bytes, targetClass);
//		}catch(Exception e) {
//			log.error("序列化异常:", e);
//		}
//		
//		return null;
//	}
	
}
